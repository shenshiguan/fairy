package org.ssg.utils;

import java.io.FileWriter;
import java.io.IOException;

public class LogOut {
//	public static String filePath = "D:/batch_files/jpg2pdf.log";
	public static String filePath = "D:/batch_files/doc_pdf.log";
	public FileWriter fw = null;
	public static void main(String[] args) throws IOException {
		
	}
	
	/**  
     * 文本内容追加  
     *   
     * @param filePath  
     * @param content  
     */ 
    public void fileChaseFW( String content) {  
		try {  
			if(fw==null){
				fw = new FileWriter(filePath,true);
			}
			fw.write(content+"\r\n");
			fw.flush();
		} catch (IOException e) {  
			System.out.println("文件写入失败！" + e);  
		}  
    }
    
    
    public  void close() throws IOException{
		if(fw != null)
			fw.close();  
    }
}
