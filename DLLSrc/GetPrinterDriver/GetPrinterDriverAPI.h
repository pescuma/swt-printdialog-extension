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

#ifdef __cplusplus
extern "C" {
#endif  /* __cplusplus */

#define _UNICODE

typedef struct printSetup {
    LONG dmOrientation;
    LONG dmPaperSize;
    RECT rtMargin;
	HGLOBAL hDevNames;
} PRINTSETUP, *LPPRINTSETUP;

int GetPrinterDevice(LPTSTR pszPrinterName, HGLOBAL* phDevNames, HGLOBAL* phDevMode, int dmOrientation, int dmPaperSize);
int OpenPageSetupDLG(LPTSTR pszPrinterName, LPPRINTSETUP lpps);
#ifdef __cplusplus
}
#endif
