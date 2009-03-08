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


/**
 * Printer Setup Model
 * @author Jun Huang
 *         
 */
public class PrinterSetup {
	
	public static final int DMORIENT_PORTRAIT = 1;
	public static final int DMORIENT_LANDSCAPE = 2;
	
	public static final int DMPAPER_LETTER =             1;  /* Letter 8 1/2 x 11 in               */
	public static final int DMPAPER_LETTERSMALL =        2;  /* Letter Small 8 1/2 x 11 in         */
	public static final int DMPAPER_TABLOID =            3;  /* Tabloid 11 x 17 in                 */
	public static final int DMPAPER_LEDGER =             4;  /* Ledger 17 x 11 in                  */
	public static final int DMPAPER_LEGAL =              5;  /* Legal 8 1/2 x 14 in                */
	public static final int DMPAPER_STATEMENT =          6;  /* Statement 5 1/2 x 8 1/2 in         */
	public static final int DMPAPER_EXECUTIVE =          7;  /* Executive 7 1/4 x 10 1/2 in        */
	public static final int DMPAPER_A3 =                 8;  /* A3 297 x 420 mm                    */
	public static final int DMPAPER_A4 =                 9;  /* A4 210 x 297 mm                    */
	public static final int DMPAPER_A4SMALL =            10;  /* A4 Small 210 x 297 mm              */
	public static final int DMPAPER_A5 =                 11;  /* A5 148 x 210 mm                    */
	public static final int DMPAPER_B4 =                 12;  /* B4 (JIS) 250 x 354                 */
	public static final int DMPAPER_B5 =                 13;  /* B5 (JIS) 182 x 257 mm              */
	public static final int DMPAPER_FOLIO =              14;  /* Folio 8 1/2 x 13 in                */
	public static final int DMPAPER_QUARTO =             15;  /* Quarto 215 x 275 mm                */
	public static final int DMPAPER_10X14 =              16;  /* 10x14 in                           */
	public static final int DMPAPER_11X17 =              17;  /* 11x17 in                           */
	public static final int DMPAPER_NOTE =               18;  /* Note 8 1/2 x 11 in                 */
	public static final int DMPAPER_ENV_9 =              19;  /* Envelope #9 3 7/8 x 8 7/8          */
	public static final int DMPAPER_ENV_10 =             20;  /* Envelope #10 4 1/8 x 9 1/2         */
	public static final int DMPAPER_ENV_11 =             21;  /* Envelope #11 4 1/2 x 10 3/8        */
	public static final int DMPAPER_ENV_12 =             22;  /* Envelope #12 4 \276 x 11           */
	public static final int DMPAPER_ENV_14 =             23;  /* Envelope #14 5 x 11 1/2            */
	public static final int DMPAPER_CSHEET =             24;  /* C size sheet                       */
	public static final int DMPAPER_DSHEET =             25;  /* D size sheet                       */
	public static final int DMPAPER_ESHEET =             26;  /* E size sheet                       */
	public static final int DMPAPER_ENV_DL =             27;  /* Envelope DL 110 x 220mm            */
	public static final int DMPAPER_ENV_C5 =             28;  /* Envelope C5 162 x 229 mm           */
	public static final int DMPAPER_ENV_C3 =             29;  /* Envelope C3  324 x 458 mm          */
	public static final int DMPAPER_ENV_C4 =             30;  /* Envelope C4  229 x 324 mm          */
	public static final int DMPAPER_ENV_C6 =             31;  /* Envelope C6  114 x 162 mm          */
	public static final int DMPAPER_ENV_C65 =            32;  /* Envelope C65 114 x 229 mm          */
	public static final int DMPAPER_ENV_B4 =             33;  /* Envelope B4  250 x 353 mm          */
	public static final int DMPAPER_ENV_B5 =             34;  /* Envelope B5  176 x 250 mm          */
	public static final int DMPAPER_ENV_B6 =             35;  /* Envelope B6  176 x 125 mm          */
	public static final int DMPAPER_ENV_ITALY =          36;  /* Envelope 110 x 230 mm              */
	public static final int DMPAPER_ENV_MONARCH =        37;  /* Envelope Monarch 3.875 x 7.5 in    */
	public static final int DMPAPER_ENV_PERSONAL =       38;  /* 6 3/4 Envelope 3 5/8 x 6 1/2 in    */
	public static final int DMPAPER_FANFOLD_US =         39;  /* US Std Fanfold 14 7/8 x 11 in      */
	public static final int DMPAPER_FANFOLD_STD_GERMAN = 40;  /* German Std Fanfold 8 1/2 x 12 in   */
	public static final int DMPAPER_FANFOLD_LGL_GERMAN = 41;  /* German Legal Fanfold 8 1/2 x 13 in */

	private String printerName;
	private int orient = DMORIENT_PORTRAIT;
	private int paperSize = DMPAPER_A4;
	private int leftMargin = 2500;
	private int rightMargin = 2500;
	private int topMargin = 2500;
	private int bottomMargin = 2500;
	
	public String getPrinterName() {
		return printerName;
	}
	public int getOrient() {
		return orient;
	}
	public int getPaperSize() {
		return paperSize;
	}
	public int getLeftMargin() {
		return leftMargin;
	}
	public int getRightMargin() {
		return rightMargin;
	}
	public int getTopMargin() {
		return topMargin;
	}
	public int getBottomMargin() {
		return bottomMargin;
	}

	public void setPrinterName(String printerName) {
		this.printerName = printerName;
	}
	public void setOrient(int orient) {
		this.orient = orient;
	}
	public void setPaperSize(int paperSize) {
		this.paperSize = paperSize;
	}
	public void setLeftMargin(int leftMargin) {
		this.leftMargin = leftMargin;
	}
	public void setRightMargin(int rightMargin) {
		this.rightMargin = rightMargin;
	}
	public void setTopMargin(int topMargin) {
		this.topMargin = topMargin;
	}
	public void setBottomMargin(int bottomMargin) {
		this.bottomMargin = bottomMargin;
	}
}
