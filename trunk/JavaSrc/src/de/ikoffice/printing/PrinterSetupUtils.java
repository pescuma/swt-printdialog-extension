/**
 *        (c) 2007-2008 IKOffice GmbH
 *
 *        http://www.ikoffice.de
 *        
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * @author 
 * Jun Huang, (huangjun78@gmail.com, junhuang@ikoffice.de) 
 */
package de.ikoffice.printing;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.Properties;

import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.TCHAR;
import org.eclipse.swt.printing.PrintDialog;
import org.eclipse.swt.printing.PrinterData;
import org.eclipse.swt.widgets.Shell;
import org.xvolks.jnative.JNative;
import org.xvolks.jnative.Type;
import org.xvolks.jnative.misc.basicStructures.LONG;
import org.xvolks.jnative.pointers.Pointer;
import org.xvolks.jnative.pointers.memory.MemoryBlockFactory;

import de.ikoffice.printing.intern.PRINTSETUP;
import de.ikoffice.printing.intern.RECT;

/**
 * Utils Class
 * @author Jun Huang
 */
public class PrinterSetupUtils {
	
	public static final String DLL_NAME = System.getProperty("user.dir")+"\\GetPrinterDriver.dll";
	
	/**
	 * Open Printer Dialog by custom printer setup.
	 * @param setup
	 * @return printerData
	 */
	public static PrinterData createPrinterData(PrinterSetup setup) {
		String printerName = setup.getPrinterName();
		PrinterData data = new PrinterData();
		if (printerName!=null && !"".equals(printerName)) {
			data = new PrinterData("winspool", printerName);
			
			JNative pd;
			try {
				pd = new JNative(DLL_NAME, "GetPrinterDevice");
				pd.setRetVal(Type.INT);
				
				Pointer hDevMode = new Pointer(MemoryBlockFactory.createMemoryBlock(4));
				Pointer hDevNames = new Pointer(MemoryBlockFactory.createMemoryBlock(4));
				
				pd.setParameter(0, printerName);
				pd.setParameter(1, hDevNames);
				pd.setParameter(2, hDevMode);
				pd.setParameter(3, setup.getOrient());
				pd.setParameter(4, setup.getPaperSize());
				
				pd.invoke();
		        if (pd.getRetValAsInt() == 0) {
			        /* Bulk-save the printer-specific settings in the DEVMODE struct */
			        int hMem = hDevMode.getAsInt(0);
			        int size = OS.GlobalSize(hMem);
			        int ptr = OS.GlobalLock(hMem);
			        byte[] otherData = new byte[size];
			        OS.MoveMemory(otherData, ptr, size);
			        OS.GlobalUnlock(hMem);
			        data = injectIntoObject(data, otherData);
			        return data;
			        
		        }
				
			} catch (Exception e) {
				throw new RuntimeException("Error while create a PrinterData by PrinterSetup", e);
			}
	
		}
		
		return data; 
	}
	
	public static PrinterData openPrinterDLG(PrinterSetup setup) {
		PrinterData data = createPrinterData(setup);
		PrintDialog printDlg = new PrintDialog(new Shell());
		printDlg.setPrinterData(data);
		data = printDlg.open();
		if (data != null) {
			byte[] otherData = extractFromObject(data);
			setup.setPrinterName(data.name);
			setup.setOrient(otherData[76]);
			setup.setPaperSize(otherData[78]);
			
			return data;
		}
		
		return null;
		
	}
	
	public static PrinterSetup openPageSetupDLG(PrinterSetup setup) {
		try {
			JNative pd = new JNative(DLL_NAME, "OpenPageSetupDLG");
			//BOOL is in fact an INT
			pd.setRetVal(Type.INT);
			
			String name = setup.getPrinterName();
			PRINTSETUP ps = new PRINTSETUP();
			ps.setDmOrientation(new LONG(setup.getOrient()));
			ps.setDmPaperSize(new LONG(setup.getPaperSize()));
			
			RECT rect = new RECT();
			rect.setLeft(new LONG(setup.getLeftMargin()));
			rect.setTop(new LONG(setup.getTopMargin()));
			rect.setBottom(new LONG(setup.getBottomMargin()));
			rect.setRight(new LONG(setup.getRightMargin()));
			ps.setRtMargin(rect);
			
			pd.setParameter(0, name);
			pd.setParameter(1, ps.getPointer());
			
			pd.invoke();
			
			if (pd.getRetValAsInt() == 0) {
				setup.setOrient(ps.getValueFromPointer().dmOrientation.getValueFromPointer());
				setup.setPaperSize(ps.getValueFromPointer().dmPaperSize.getValueFromPointer());
				setup.setLeftMargin(ps.getValueFromPointer().rtMargin.getValueFromPointer().left.getValueFromPointer());
				setup.setTopMargin(ps.getValueFromPointer().rtMargin.getValueFromPointer().top.getValueFromPointer());
				setup.setRightMargin(ps.getValueFromPointer().rtMargin.getValueFromPointer().right.getValueFromPointer());
				setup.setBottomMargin(ps.getValueFromPointer().rtMargin.getValueFromPointer().bottom.getValueFromPointer());
				
				int /*long*/ hMem = ps.getValueFromPointer().hDevNames.getValueFromPointer();
				/* Ensure size is a multiple of 2 bytes on UNICODE platforms */
				int size = OS.GlobalSize(hMem) / TCHAR.sizeof * TCHAR.sizeof;
				int /*long*/ ptr = OS.GlobalLock(hMem);
				short[] offsets = new short[4];
				OS.MoveMemory(offsets, ptr, 2 * offsets.length);
				TCHAR buffer = new TCHAR(0, size);
				OS.MoveMemory(buffer, ptr, size);	
				OS.GlobalUnlock(hMem);


				int deviceOffset = offsets[1];
				int i = 0;
				while (deviceOffset + i < size) {
					if (buffer.tcharAt(deviceOffset + i) == 0) break;
					i++;
				}
				setup.setPrinterName(buffer.toString(deviceOffset, i));
			
			
			}
		} catch (Exception e) {
			throw new RuntimeException("Error while open pagesetup dialog", e);
		}
		
		return setup;
		
	}
	
	public static void main(String[] args) {
		String configFilePath = new File("").getAbsolutePath()+"\\printer.properties";
		String printername = "";
		int orient = PrinterSetup.DMORIENT_PORTRAIT;
		int paperSize = PrinterSetup.DMPAPER_A4;
		int leftMargin = 2500;
		int topMargin = 2500;
		int rightMargin = 2500;
		int bottomMargin = 2500;
		
		Properties props = new Properties();
		try {
			InputStream in = new BufferedInputStream(new FileInputStream(
					configFilePath));
			props.load(in);
			printername = props.getProperty("printer");
			orient = Integer.valueOf(props.getProperty("orient"));
            paperSize = Integer.valueOf(props.getProperty("paper"));
            leftMargin = Integer.valueOf(props.getProperty("left"));
            rightMargin = Integer.valueOf(props.getProperty("right"));
            topMargin = Integer.valueOf(props.getProperty("top"));
            bottomMargin = Integer.valueOf(props.getProperty("bottom"));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		PrinterSetup ps = new PrinterSetup();
		ps.setPrinterName(printername);
		ps.setOrient(orient);
		ps.setPaperSize(paperSize);
		ps.setLeftMargin(leftMargin);
		ps.setRightMargin(rightMargin);
		ps.setTopMargin(topMargin);
		ps.setBottomMargin(bottomMargin);
		ps = openPageSetupDLG(ps);
		
		openPrinterDLG(ps);
		
		Properties prop = new Properties();
		try {
			InputStream fis = new FileInputStream(configFilePath);
			prop.load(fis);

			OutputStream fos = new FileOutputStream(configFilePath);
			prop.setProperty("printer", ps.getPrinterName());
			prop.setProperty("orient", String.valueOf(ps.getOrient()));
			prop.setProperty("paper", String.valueOf(ps.getPaperSize()));
			prop.setProperty("left", String.valueOf(ps.getLeftMargin()));
			prop.setProperty("right", String.valueOf(ps.getRightMargin()));
			prop.setProperty("top", String.valueOf(ps.getTopMargin()));
			prop.setProperty("bottom", String.valueOf(ps.getBottomMargin()));

			prop.store(fos, "Update");
		} catch (IOException e) {
			System.err.println("Visit " + configFilePath + " for updating value error");
		}
		
		
	}
	
	private static PrinterData injectIntoObject(PrinterData printerData, byte[] otherData) {
		Field field = null;
		
		Field[] fields = PrinterData.class.getDeclaredFields();
		for (Field f : fields) {
			if (f.getName().equals("otherData")) {
				field = f;
			}
			
		}
		
		if (field != null) {
			 try {
				 
				 	field.set(printerData, otherData);
		        } catch (IllegalArgumentException e) {
		            throw new RuntimeException("Illegal argument while injecting property",e);
		        } catch (IllegalAccessException e) {
		        	field.setAccessible(true);
		            try {
		            	field.set(printerData, otherData);
		            } catch (IllegalArgumentException e1) {
		                throw new RuntimeException("Illegal argument while injecting property",e);
		            } catch (IllegalAccessException e1) {
		                throw new RuntimeException("Access exception while injecting property",e);
		            }
		        }
		}
		return printerData;
		
	}
	
	private static byte[] extractFromObject(PrinterData printerData) {
		if (printerData == null) {
			return null;
		}
		Field field = null;
		Field[] fields = PrinterData.class.getDeclaredFields();
		for (Field f : fields) {
			if (f.getName().equals("otherData")) {
				field = f;
			}
			
		}
		try {
	           return (byte[]) field.get(printerData);
	        } catch (IllegalArgumentException e) {
	            throw new RuntimeException("Illegal argument while read property",e);
	        } catch (IllegalAccessException e) {
	            field.setAccessible(true);
	            try {
	            	return (byte[]) field.get(printerData);
	            } catch (IllegalArgumentException e1) {
	                throw new RuntimeException("Illegal argument while read property",e);
	            } catch (IllegalAccessException e1) {
	                throw new RuntimeException("Access exception while read property",e);
	            }
	        }
	}

}
