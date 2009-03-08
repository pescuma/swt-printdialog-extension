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

// GetPrinterDriver.h : main header file for the GETPRINTERDRIVER DLL
//

#if !defined(AFX_GETPRINTERDRIVER_H__501D5B69_C9B9_49CD_8E9E_B48B986AF9FF__INCLUDED_)
#define AFX_GETPRINTERDRIVER_H__501D5B69_C9B9_49CD_8E9E_B48B986AF9FF__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#ifndef __AFXWIN_H__
	#error include 'stdafx.h' before including this file for PCH
#endif

#include "resource.h"		// main symbols

/////////////////////////////////////////////////////////////////////////////
// CGetPrinterDriverApp
// See GetPrinterDriver.cpp for the implementation of this class
//

class CGetPrinterDriverApp : public CWinApp
{
public:
	CGetPrinterDriverApp();

// Overrides
	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(CGetPrinterDriverApp)
	//}}AFX_VIRTUAL

	//{{AFX_MSG(CGetPrinterDriverApp)
		// NOTE - the ClassWizard will add and remove member functions here.
		//    DO NOT EDIT what you see in these blocks of generated code !
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
};


/////////////////////////////////////////////////////////////////////////////

//{{AFX_INSERT_LOCATION}}
// Microsoft Visual C++ will insert additional declarations immediately before the previous line.

#endif // !defined(AFX_GETPRINTERDRIVER_H__501D5B69_C9B9_49CD_8E9E_B48B986AF9FF__INCLUDED_)
