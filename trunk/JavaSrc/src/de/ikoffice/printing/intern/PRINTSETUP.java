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
package de.ikoffice.printing.intern;

import org.xvolks.jnative.exceptions.NativeException;
import org.xvolks.jnative.misc.basicStructures.AbstractBasicData;
import org.xvolks.jnative.misc.basicStructures.LONG;
import org.xvolks.jnative.pointers.Pointer;
import org.xvolks.jnative.pointers.memory.GlobalMemoryBlock;
import org.xvolks.jnative.util.Callback;

/**
 * @author       Jun Huang
 */
public class PRINTSETUP extends AbstractBasicData<PRINTSETUP> {
	
	public LONG dmOrientation;
	public LONG dmPaperSize;
	public RECT rtMargin;
	public LONG hDevNames;
	
	public PRINTSETUP() throws NativeException {
		super(null);
		mValue = this;
		createPointer();
	}
	
	public void removeCallback() throws NativeException {
		//TODO
	}
	
	public void addCallback(Callback lCallback) throws NativeException {
		removeCallback();
		
		//TODO
	}
	
	

	public void setDmOrientation(LONG dmOrientation) throws NativeException {
        this.dmOrientation = dmOrientation;
        pointer.setIntAt(0, dmOrientation.getValue());
    }

    public void setDmPaperSize(LONG dmPaperSize) throws NativeException {
        this.dmPaperSize = dmPaperSize;
        pointer.setIntAt(4, dmPaperSize.getValue());
    }

    public void setRtMargin(RECT rtMargin) throws NativeException {
        
        pointer.setIntAt(8, rtMargin.left.getValue());
        pointer.setIntAt(12, rtMargin.top.getValue());
        pointer.setIntAt(16, rtMargin.right.getValue());
        pointer.setIntAt(20, rtMargin.bottom.getValue());
        
        this.rtMargin = rtMargin;
    
    }

    /* (non-Javadoc)
	 * @see org.xvolks.jnative.misc.basicStructures.BasicData#createPointer()
	 */
	public Pointer createPointer() throws NativeException {
		pointer = new Pointer(new GlobalMemoryBlock(getSizeOf()));

        return pointer;
	}

	/* (non-Javadoc)
	 * @see org.xvolks.jnative.misc.basicStructures.BasicData#getSizeOf()
	 */
	public int getSizeOf() {
		return 28;
	}

	/* (non-Javadoc)
	 * @see org.xvolks.jnative.misc.basicStructures.BasicData#getValueFromPointer()
	 */
	public PRINTSETUP getValueFromPointer() throws NativeException {
		offset = 0;
		dmOrientation = new LONG(getNextInt());      	
		dmPaperSize = new LONG(getNextInt());
        rtMargin = new RECT();
        rtMargin.setLeft(new LONG(getNextInt()));
        rtMargin.setTop(new LONG(getNextInt()));
        rtMargin.setRight(new LONG(getNextInt()));
        rtMargin.setBottom(new LONG(getNextInt()));
        hDevNames = new LONG(getNextInt());
  
        // 28
        
        return getValue();
  
	}

}
