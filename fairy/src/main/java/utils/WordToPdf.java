package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import com.artofsolving.jodconverter.DefaultDocumentFormatRegistry;
import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.DocumentFormat;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;

public class WordToPdf {
    public static int DEFAULT_PORT = 8100;  
    
    public static String DEFAULT_HOST = "127.0.0.1";  
      
    /** 
     * 执行前，请启动openoffice服务 
     * 进入$OO_HOME\program下 
     * 执行soffice -headless -accept="socket,host=127.0.0.1,port=8100;urp;" -nofirststartwizard  
     * @param xlsfile   
     * @param targetfile 
     * @throws Exception 
     */  
    public static int SaveAs(String xlsfile, String targetfile)  
            throws Exception {
        OpenOfficeConnection connection = null;
        try {
            // 获得文件格式  
            DefaultDocumentFormatRegistry formatReg = new DefaultDocumentFormatRegistry();  
            DocumentFormat pdfFormat = formatReg.getFormatByFileExtension("pdf");
            DocumentFormat docFormat = formatReg.getFormatByFileExtension("doc");
            connection = new SocketOpenOfficeConnection(8100);  
            connection.connect();
	        File xlsf = new File(xlsfile);  
	        File targetF = new File(targetfile);
	        // stream 流的形式  
	        InputStream inputStream = new FileInputStream(xlsf);  
	        OutputStream outputStream = new FileOutputStream(targetF);
            DocumentConverter converter = new OpenOfficeDocumentConverter(connection); 
            converter.convert(inputStream, docFormat, outputStream, pdfFormat);  
            return 1;
        } catch (Exception e) {
        	e.printStackTrace();
            return -1;
        } finally {
            if (connection != null) {  
                connection.disconnect();  
                connection = null;  
            }
        }  
    }  
   
    
    public static boolean dowork(String path1,String path2,String path3){
        try {
            JavaCallOpenoffice javaCallOpenoffice = new JavaCallOpenoffice();
			SaveAs(path1, path2);
	        javaCallOpenoffice.distorySoffice();
//			PDF_First_JPG.generateBookIamge(path2,path3) ;
	        return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} 
    }
    public static boolean dowork(String path1,String path2){
        JavaCallOpenoffice javaCallOpenoffice = new JavaCallOpenoffice();
        try {          
			int res = SaveAs(path1, path2);
	        if(res==1){
	        	return true;
	        }else{
	        	return false;
	        }
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally{
			javaCallOpenoffice.distorySoffice();
		}
    }
    
}
