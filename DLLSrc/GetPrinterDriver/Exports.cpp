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

#include "stdafx.h"
#include "GetPrinterDriverAPI.h"
#include <winspool.h>
#define _UNICODE
// returns a DEVMODE and DEVNAMES for the printer name specified
int GetPrinterDevice(LPTSTR pszPrinterName, HGLOBAL* phDevNames, HGLOBAL* phDevMode, int dmOrientation, int dmPaperSize)
{
	AFX_MANAGE_STATE(AfxGetStaticModuleState());

    // if NULL is passed, then assume we are setting app object's
    // devmode and devnames
    if (phDevMode == NULL || phDevNames == NULL || (dmOrientation != DMORIENT_PORTRAIT && dmOrientation !=DMORIENT_LANDSCAPE)
		|| (dmPaperSize < DMPAPER_LETTER || dmPaperSize>DMPAPER_FANFOLD_LGL_GERMAN))
        return -1;

    // Open printer
    HANDLE hPrinter;
    if (OpenPrinter(pszPrinterName, &hPrinter, NULL) == FALSE)
        return -2;

    // obtain PRINTER_INFO_2 structure and close printer
    DWORD dwBytesReturned, dwBytesNeeded;
    GetPrinterW(hPrinter, 2, NULL, 0, &dwBytesNeeded);
    PRINTER_INFO_2* p2 = (PRINTER_INFO_2*)GlobalAlloc(GPTR,
        dwBytesNeeded);
    if (GetPrinterW(hPrinter, 2, (LPBYTE)p2, dwBytesNeeded,
       &dwBytesReturned) == 0) {
       GlobalFree(p2);
       ClosePrinter(hPrinter);
       return -3;
    }
    ClosePrinter(hPrinter);

    // Allocate a global handle for DEVMODE
    HGLOBAL  hDevMode = GlobalAlloc(GHND, sizeof(*p2->pDevMode) +
       p2->pDevMode->dmDriverExtra);
    ASSERT(hDevMode);
    DEVMODEW* pDevMode = (DEVMODEW*)GlobalLock(hDevMode);
    ASSERT(pDevMode);

    // copy DEVMODE data from PRINTER_INFO_2::pDevMode
    memcpy(pDevMode, p2->pDevMode, sizeof(*p2->pDevMode) +
       p2->pDevMode->dmDriverExtra);
    GlobalUnlock(hDevMode);

    // Compute size of DEVNAMES structure from PRINTER_INFO_2's data
    DWORD drvNameLen = lstrlen(p2->pDriverName)+1;  // driver name
    DWORD ptrNameLen = lstrlen(p2->pPrinterName)+1; // printer name
    DWORD porNameLen = lstrlen(p2->pPortName)+1;    // port name

    // Allocate a global handle big enough to hold DEVNAMES.
    HGLOBAL hDevNames = GlobalAlloc(GHND,
        sizeof(DEVNAMES) +
        (drvNameLen + ptrNameLen + porNameLen)*sizeof(TCHAR));
    ASSERT(hDevNames);
    DEVNAMES* pDevNames = (DEVNAMES*)GlobalLock(hDevNames);
    ASSERT(pDevNames);

    // Copy the DEVNAMES information from PRINTER_INFO_2
    // tcOffset = TCHAR Offset into structure
    int tcOffset = sizeof(DEVNAMES)/sizeof(TCHAR);
    ASSERT(sizeof(DEVNAMES) == tcOffset*sizeof(TCHAR));

    pDevNames->wDriverOffset = tcOffset;
    memcpy((LPTSTR)pDevNames + tcOffset, p2->pDriverName,
        drvNameLen*sizeof(TCHAR));
    tcOffset += drvNameLen;

    pDevNames->wDeviceOffset = tcOffset;
    memcpy((LPTSTR)pDevNames + tcOffset, p2->pPrinterName,
        ptrNameLen*sizeof(TCHAR));
    tcOffset += ptrNameLen;

    pDevNames->wOutputOffset = tcOffset;
    memcpy((LPTSTR)pDevNames + tcOffset, p2->pPortName,
        porNameLen*sizeof(TCHAR));
    pDevNames->wDefault = 0;

    GlobalUnlock(hDevNames);
    GlobalFree(p2);   // free PRINTER_INFO_2

    


	// Set value for the pDevMode
	if (OpenPrinter(pszPrinterName, &hPrinter, NULL) == FALSE)
        return -2;
	pDevMode = (DEVMODEW*)GlobalLock(hDevMode);
    ASSERT(pDevMode);
	
	//set Orientation
	pDevMode->dmOrientation = dmOrientation;

	//set PaperSize
	pDevMode->dmPaperSize = dmPaperSize;

	GlobalUnlock(hDevMode);
    ClosePrinter(hPrinter);

	// set the new hDevMode and hDevNames
    *phDevMode = hDevMode;
    *phDevNames = hDevNames;

    return 0;
}

// returns a DEVMODE and DEVNAMES for the printer name specified
int OpenPageSetupDLG(LPTSTR pszPrinterName, LPPRINTSETUP lpps)
{
	AFX_MANAGE_STATE(AfxGetStaticModuleState());
	
	// Open printer
    HANDLE hPrinter;
    if (OpenPrinter(pszPrinterName, &hPrinter, NULL) == FALSE)
        return -1;

    // obtain PRINTER_INFO_2 structure and close printer
    DWORD dwBytesReturned, dwBytesNeeded;
    GetPrinterW(hPrinter, 2, NULL, 0, &dwBytesNeeded);
    PRINTER_INFO_2* p2 = (PRINTER_INFO_2*)GlobalAlloc(GPTR,
        dwBytesNeeded);
    if (GetPrinterW(hPrinter, 2, (LPBYTE)p2, dwBytesNeeded,
       &dwBytesReturned) == 0) {
       GlobalFree(p2);
       ClosePrinter(hPrinter);
       return -2;
    }
    ClosePrinter(hPrinter);

    // Allocate a global handle for DEVMODE
    HGLOBAL  hDevMode = GlobalAlloc(GHND, sizeof(*p2->pDevMode) +
       p2->pDevMode->dmDriverExtra);
    ASSERT(hDevMode);
    DEVMODEW* pDevMode = (DEVMODEW*)GlobalLock(hDevMode);
    ASSERT(pDevMode);

    // copy DEVMODE data from PRINTER_INFO_2::pDevMode
    memcpy(pDevMode, p2->pDevMode, sizeof(*p2->pDevMode) +
       p2->pDevMode->dmDriverExtra);
    GlobalUnlock(hDevMode);

  	// Set value for the pDevMode
	if (OpenPrinter(pszPrinterName, &hPrinter, NULL) == FALSE)
        return -3;
	pDevMode = (DEVMODEW*)GlobalLock(hDevMode);
    ASSERT(pDevMode);
	
	//set Orientation
	pDevMode->dmOrientation = lpps->dmOrientation;

	//set PaperSize
	pDevMode->dmPaperSize = lpps->dmPaperSize;

	GlobalUnlock(hDevMode);
    ClosePrinter(hPrinter);
	
	PAGESETUPDLGW psd;

	ZeroMemory(&psd, sizeof(PAGESETUPDLGW));    

	psd.lStructSize=sizeof(PAGESETUPDLGW); 
	psd.hDevMode = pDevMode;
	psd.Flags=PSD_INHUNDREDTHSOFMILLIMETERS | PSD_MARGINS;
;
	psd.rtMargin = lpps->rtMargin;

	if (PageSetupDlgW(&psd)) {
		pDevMode = (DEVMODEW*)GlobalLock(psd.hDevMode);
		lpps->dmOrientation = pDevMode->dmOrientation;
		lpps->dmPaperSize = pDevMode->dmPaperSize;
		lpps->rtMargin = psd.rtMargin;
		lpps->hDevNames = psd.hDevNames;
		return 0;
		
	}
	



	return -4;


}

