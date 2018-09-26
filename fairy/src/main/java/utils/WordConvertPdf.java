package utils;

import java.io.File;

import org.apache.log4j.Logger;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

public class WordConvertPdf {
	private static Logger logger =  Logger.getLogger(WordConvertPdf.class);
	
	
	//将World格式的文件转换为PDF格式的文件
	public static  boolean wordToPDF(String docfile, String toFile) {
		logger.info("doc文档转pdf："+docfile);		
		logger.info("pdf文档转pdf："+toFile);
		ActiveXComponent app = null;
		Dispatch doc = null;
		try{
			ComThread.InitSTA(true);
			app = new ActiveXComponent("Word.Application"); // 启动word
			
			logger.info(app == null);
			//设置word程序非可视化运行
			app.setProperty("Visible", new Variant(false));
			//打开world文件
			Dispatch docs = app.getProperty("Documents").toDispatch();
			//打开word文件，注意这里第三个参数要设为false，这个参数表示是否以只读方式打开，因为我们要保存原文件，所以以可写方式打开 
			doc = Dispatch.invoke(docs,"Open",Dispatch.Method,new Object[] {docfile, new Variant(false),new Variant(false)},new int[1]).toDispatch();
			//new Variant(type)，这里面的type的决定另存为什么类型的文件 17代表为PDF类型  
			//作为PDF格式保存文件
			File file_tmp = new File(toFile);
			if(file_tmp.exists()){
				file_tmp.delete();
			}
			Dispatch.invoke(doc, "SaveAs", Dispatch.Method, new Object[] {toFile, new Variant(17)}, new int[1]);
//			Variant f = new Variant(false);
			//关闭doc文件
//			Dispatch.call(doc, "Close", f);
			logger.info("转码完成！");
			return true;
		} catch (Exception e) {
			logger.error("转码失败,"+e.getMessage());
			e.printStackTrace();
			return false;
		} finally {
			if(doc!=null)
				Dispatch.call(doc, "Close");
			//退出word程序
			if(app!=null)
				app.invoke("Quit", new Variant[] {});
			ComThread.Release();  
			ComThread.quitMainSTA();
		}

	}	
	
	public static boolean ppt2PDF(String inputFile, String pdfFile) {
		logger.info("ppt文档转pdf："+inputFile);
		ActiveXComponent app = null;
		Dispatch ppt = null;
		try {
			ComThread.InitSTA(true);
			app = new ActiveXComponent("PowerPoint.Application");
			app.setProperty("AutomationSecurity", new Variant(3)); // 禁用宏
			Dispatch ppts = app.getProperty("Presentations").toDispatch();// 获取文挡属性
			// 调用Documents对象中Open方法打开文档，并返回打开的文档对象Document    
            ppt = Dispatch.call(ppts, "Open", inputFile,     
                    true,// ReadOnly    
                    false,// Untitled指定文件是否有标题    
                    false// WithWindow指定文件是否可见    
                    ).toDispatch();
            Dispatch.call(ppt, "SaveAs", pdfFile, 32);
//			ppt = Dispatch.call(ppts, "Open", inputFile, true, // ReadOnly
//													false, // Untitled指定文件是否有标题
//													false// WithWindow指定文件是否可见
//										).toDispatch();
//			Dispatch.invoke(ppt, "SaveAs", Dispatch.Method, new Object[]{pdfFile,new Variant(32)},new int[1]);
			logger.info("转码完成！");
			return true;
		} catch (Exception e) {
			logger.error("转码失败,"+e.getMessage());
			e.printStackTrace();
			return false;
		}finally{
			if(ppt!=null)
				Dispatch.call(ppt, "Close");
			if(app!=null)
				app.invoke("Quit");
			ComThread.Release();  
			ComThread.quitMainSTA(); 
		}
	    
	}	
	
	public static void main(String[] args) {
		
//		word.wordToPDF("D:/2222.doc", "D:/2017ppp.pdf", 17);
//		word.PowerPointToPDF("D:/888.ppt", "D:/2017ppp.pdf", 32);
//		//压力测试
//		for(int i=0;i<1;i++){
//			WordConvertPdf.ppt2PDF("D:/files/0d365d7f-bba3-490f-bebc-d6c3b4ad7d5d.pptx", "D:/0d365d7f-bba3-490f-bebc-d6c3b4ad7d5d.pdf");
////		WordConvertPdf.wordToPDF("D:/555.docx", "D:/555.pdf");
//		}
		logger.info(".........................");
		if(args!=null&&args.length==2){
			logger.info("................start.................");
			String pix = args[1].toLowerCase();
			String filePath = args[0].toLowerCase();
			String infile =  filePath.substring(0, args[0].lastIndexOf("."));
			if("pptx".equals(pix)||"ppt".equals(pix)){
				WordConvertPdf.ppt2PDF(filePath, infile+".pdf");
			}else if("docx".equals(pix)||"doc".equals(pix)){
				WordConvertPdf.wordToPDF(filePath, infile+".pdf");
			}else{
				logger.info("无效的文件后缀名。");
			}
			logger.info("...............fination..............");
		}
		
		
	}
}
