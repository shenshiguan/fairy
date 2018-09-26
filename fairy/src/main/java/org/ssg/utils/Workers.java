package org.ssg.utils;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.jacob.com.Dispatch;

public class Workers implements Runnable{

	Logger logger = Logger.getLogger(Workers.class);
	Map<String, String> fileInfo = null;
	public Workers(Map<String, String> fileInfo ){
		this.fileInfo = fileInfo;
	}
	String filePath 	=  ConfigUtil.get("file.path");
	String sendpdf_url 		= ConfigUtil.get("java.service.url.sendpdf");
	String javaconvert_url 	= ConfigUtil.get("java.service.url.post");
	String fileId ="";
	public void run() {
		try{
			Dispatch disp = ConvertUtil.getDispatch();
			while(true){
				if(disp==null){
					disp = ConvertUtil.getDispatch();
				}
				String filename ="";

				fileId = fileInfo.get("fileId")==null?null:fileInfo.get("fileId").toString();
				filename = fileInfo.get("filename")==null?null:fileInfo.get("filename").toString();
				if(StringUtils.isBlank(fileId)||StringUtils.isBlank(filename)){

					return ;
				}else{
					//执行转换  
			        String fileType = filename.substring(filename.lastIndexOf(".")+1);
			        if(fileType!=null){
				        if(fileType.toLowerCase().endsWith("x")){
				        	fileType = fileType.substring(0, fileType.length()-1);
				        }
			        
				        String res = ConvertUtil.ComWorking(disp, filePath, filename, fileType);
				    
				        if(!"1".equals(res)){
					    	if("-2000".equals(res)){
						    	//JAVA重新转码  /javaConvert
					    		String pdfFile = filename.substring(0,filename.lastIndexOf("."))+".pdf";
						    	HttpClientUtil.sendPost(javaconvert_url+"?fileId="+fileId+"&officeFile="+filename+"&pdfFile="+pdfFile, null);
						    }else{
						    	//发送通知
						    	HttpClientUtil.sendPost(sendpdf_url+"?guid="+fileId+"&filePath=-1", null);
						    }
					    }
					    else{
					    	//发送通知
					    	filename = filename.substring(0, filename.lastIndexOf("."))+".pdf";
					    	HttpClientUtil.sendPost(sendpdf_url+"?guid="+fileId+"&filePath="+filePath+filename, null);
					    }
			        }
				}
			}
		}catch(Exception all){
			all.printStackTrace();
	    	//发送通知
	    	HttpClientUtil.sendPost(sendpdf_url+"?guid="+fileId+"&filePath=-1", null);	
		}finally{
			ConvertUtil.Release();
		}
	} //========run==========
}	//========class==========
