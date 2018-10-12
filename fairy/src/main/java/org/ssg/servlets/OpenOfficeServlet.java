package org.ssg.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import org.ssg.utils.ConfigUtil;
import org.ssg.utils.HttpClientUtil;
import org.ssg.utils.WordConvertPdf;
import org.ssg.utils.WordToPdf;


@WebServlet(name = "JavaConvert", urlPatterns = {"/javaConvert"})
public class OpenOfficeServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static Logger logger =  Logger.getLogger(OpenOfficeServlet.class);
	
	private String sendpdf_url 		= ConfigUtil.get("java.service.url.sendpdf");
	private String filePath = ConfigUtil.get("file.path");
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.info("............................OpenOfficeServlet......................");
		String officeFile 	= request.getParameter("officeFile");
		String pdfFile		= request.getParameter("pdfFile");
		String fileId		= request.getParameter("fileId");
		logger.info("源文件名称："+officeFile+" 转换后的文件名称："+pdfFile);
		if(StringUtils.isBlank(officeFile)||StringUtils.isBlank(pdfFile)){
			response.getWriter().print("-1");
			response.getWriter().flush();
			response.getWriter().close();
		}
		try {
			boolean res = false;
			officeFile = officeFile.toLowerCase();
			if(officeFile.endsWith("doc")||officeFile.endsWith("docx")){
				res = WordConvertPdf.wordToPDF(filePath+officeFile, filePath+pdfFile);	
			}
			if(officeFile.endsWith("ppt")||officeFile.endsWith("pptx")){
				res = WordConvertPdf.ppt2PDF(filePath+officeFile, filePath+pdfFile);
			}
			if(res){
		    	//发送通知
		    	HttpClientUtil.sendPost(sendpdf_url+"?guid="+fileId+"&filePath="+pdfFile, null);
			}else{
		    	res = WordToPdf.dowork(officeFile,pdfFile);
		    	if(res){
		    		HttpClientUtil.sendPost(sendpdf_url+"?guid="+fileId+"&filePath="+pdfFile, null);
		    		logger.info("转码成功！");
		    	}else{
			    	//发送通知
			    	HttpClientUtil.sendPost(sendpdf_url+"?guid="+fileId+"&filePath=-1", null);
		    	}
			}
		} catch (Exception e) {
			e.printStackTrace();
	    	//发送通知
	    	HttpClientUtil.sendPost(sendpdf_url+"?guid="+fileId+"&filePath=-1", null);
		}finally{
		}
	}
	
}
