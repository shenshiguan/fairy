package org.ssg.utils;

import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

public class HttpFileUtil {

	private static Logger logger = Logger.getLogger(HttpFileUtil.class);
	/**  
     * Processes requests for both HTTP  
     * <code>GET</code> and  
     * <code>POST</code> methods.  
     *  
     * @param request servlet request  
     * @param response servlet response  
     * @throws ServletException if a servlet-specific error occurs  
     * @throws IOException if an I/O error occurs  
     */
    public String processRequest(HttpServletRequest request)  {  
        try {
			//读取请求Body  
			byte[] body = readBody(request);
			logger.info(body.length);
			String fieldName = "filename";
			String filePath = org.ssg.utils.ConfigUtil.get("file.path");
			//取得所有Body内容的字符串表示  
			String textBody = new String(body, "ISO-8859-1");
			logger.info(textBody.toString());
			//取得上传的文件名称  
			String fileName = getFileName(textBody,fieldName);
			//取得文件开始与结束位置  
			Position p = getFilePosition(request, textBody,fieldName); 
			
			logger.info(p.begin);
			logger.info(p.end);
			
			//输出至文件  
			writeTo(filePath,fileName, body, p);
			return fileName;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "-1";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "-1";
		}  
    }  
  
    //构造类  
    class Position {  
  
        int begin;  
        int end;  
  
        public Position(int begin, int end) {  
            this.begin = begin;  
            this.end = end;  
        }  
    }  
  
    private byte[] readBody(HttpServletRequest request) throws IOException {  
        //获取请求文本字节长度  
        int formDataLength = request.getContentLength();  
        //取得ServletInputStream输入流对象  
        DataInputStream dataStream = new DataInputStream(request.getInputStream());  
        byte body[] = new byte[formDataLength];  
        int totalBytes = 0;  
        while (totalBytes < formDataLength) {  
            int bytes = dataStream.read(body, totalBytes, formDataLength);  
            totalBytes += bytes;  
        }  
        return body;  
    }  
  
    private Position getFilePosition(HttpServletRequest request, String textBody,String field) throws IOException {  
        //取得文件区段边界信息  
        String contentType = request.getContentType();
//      logger.info("contentType:"+contentType);
        String boundaryText = contentType.substring(contentType.lastIndexOf("=") + 1, contentType.length());  
        //取得实际上传文件的起始与结束位置  
        int pos = textBody.indexOf(field+"=\"");  
        pos = textBody.indexOf("\n", pos) + 1;  
        pos = textBody.indexOf("\n", pos) + 1;  
        pos = textBody.indexOf("\n", pos) + 1;  
        int boundaryLoc = textBody.indexOf(boundaryText, pos) - 4;  
        int begin = ((textBody.substring(0, pos)).getBytes("ISO-8859-1")).length;  
        int end = ((textBody.substring(0, boundaryLoc)).getBytes("ISO-8859-1")).length;  
        return new Position(begin, end);  
    }  

    /***
     * 
     * @param requestBody
     * @param fieldName	url中变量的名字
     * @return
     */
    private String getFileName(String requestBody,String fieldName) {  
//      String fileName = requestBody.substring(requestBody.indexOf("filename=\"") + 10);  
        String fileName = requestBody.substring(requestBody.indexOf(fieldName+"=\"") + 10);  
        fileName = fileName.substring(0, fileName.indexOf("\n"));  
        fileName = fileName.substring(fileName.indexOf("\n") + 1, fileName.indexOf("\""));  
        return fileName;  
    }  
  
    /***
     * 
     * @param filePath
     * @param fileName
     * @param body
     * @param p
     * @throws IOException
     */
    private void writeTo(String filePath, String fileName, byte[] body, Position p) throws IOException {
//      FileOutputStream fileOutputStream = new FileOutputStream("D:/files/" + fileName);  
        FileOutputStream fileOutputStream = new FileOutputStream(filePath + fileName);
        fileOutputStream.write(body, p.begin, (p.end - p.begin));  
        fileOutputStream.flush();  
        fileOutputStream.close();  
    }
    
}
