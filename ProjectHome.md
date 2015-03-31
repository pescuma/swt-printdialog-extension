A utility to open printer dialog and page setup dialog using SWT-win32.
user can setup printer and page before open the printer dialog and page setup dialog.

## User Guide: ##

  1. open a Printer Dialog with printer name, paper size and orient and save the new Setup in properties file:
```
     //Create new printerSetup
     PrinterSetup ps = new PrinterSetup();
     ps.setPrinterName("Brother MFC-9420CN Printer");
     ps.setOrient(PrinterSetup.DMORIENT_PORTRAIT);
     ps.setPaperSize(PrinterSetup.DMPAPER_A4);
     
     //open PrinterDialog with the printerSetup
     PrinterData userPrinterData = PrinterSetupUtils.openPrinterDLG(ps);

     //Save the ps as new Printer setup.
     Properties prop = new Properties();
     try {
	  InputStream fis = new FileInputStream("c:\\nextPrinter.properties");
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
	  System.err.println("Visit nextPrinter.properties for updating value error");
      }

```

  1. open a PageSetupDialog by PrinterSetup:
```
     PrinterSetup ps = new PrinterSetup();
     ps.setPrinterName("Brother MFC-9420CN Printer");
     ps.setOrient(PrinterSetup.DMORIENT_LANDSCAPE);
     ps.setPaperSize(PrinterSetup.DMPAPER_LETTER);
     //100 == 1mm
     ps.setLeftMargin(1000);
     ps.setRightMargin(1000);
     ps.setTopMargin(2500);
     ps.setBottomMargin(2500);
     PrinterSetup userSetup = PrinterSetupUtils.openPageSetupDLG(ps);
```

  1. create a printerData object by PrinterSetup
```
   PrinterSetup ps = new PrinterSetup();
   ps.setPrinterName("Brother MFC-9420CN Printer");
   ps.setOrient(PrinterSetup.DMORIENT_LANDSCAPE);
   ps.setPaperSize(PrinterSetup.DMPAPER_LETTER);
   PrinterData printerData = PrinterSetupUtils.createPrinterData(ps);
```

## Install ##
  * add JNative.jar, SWTPrintDlgEx\_1.0.jar, swt.jar in your ClassPath.
  * add GetPrinterDriver.dll, JNativeCpp.dll in your project root path.

## Screenshot ##
  * Page setup dialog
> > <img src='http://swt-printdialog-extension.googlecode.com/svn/trunk/img/pagesetupdialog.png' />

  * printer dialog
> > <img src='http://swt-printdialog-extension.googlecode.com/svn/trunk/img/printerdialog.png' />


---


SWT print dialog extension 是一个扩展swt printing 的工具包。

它的主要功能是以下3点：
  1. 可以是用户在打开打印对话框前设置打印对话框， 比如选择打印机，纸张大小，横向纵向打印。
  1. 在打印对话框关闭之后得到打印对话框中的设置，以便于将设置储存在注册表或配置文件中，下次使用。
  1. 可以打开并设置PageSetup对话框，以便配置打印页的边距。
