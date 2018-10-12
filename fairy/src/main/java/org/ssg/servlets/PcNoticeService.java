package org.ssg.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

@WebServlet(name="pcnotice",urlPatterns={"/pcnotice"})
public class PcNoticeService extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7814740027162381923L;

	private static Logger logger = Logger.getLogger(PcNoticeService.class);
	
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	
	@SuppressWarnings("unchecked")
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		logger.info("...........................pcnotice.......................");
	    String ip = request.getHeader("x-forwarded-for");
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
	        ip = request.getHeader("Proxy-Client-IP");
	    }
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
	        ip = request.getHeader("WL-Proxy-Client-IP");
	    }
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
	        ip = request.getRemoteAddr();
	    }
	    logger.info("来访地址："+(ip.equals("0:0:0:0:0:0:0:1")?"127.0.0.1":ip));
	    
		String fileId = request.getParameter("fileId");				//文件存储Id
		String filename = request.getParameter("filename");			//文件名称
		
		PrintWriter out = response.getWriter();
		if(StringUtils.isBlank(fileId)||StringUtils.isBlank(filename)||filename.length()<36){
			out.println("-1");
			logger.info("缺少参数");
			out.flush();
			out.close();
			return ;
		}
		int point = filename.lastIndexOf(".");
		filename = filename.substring((point-36));
		
		Map<String,String> fileInfo = new HashMap<String,String>();
		fileInfo.put("fileId", fileId);
		fileInfo.put("filename", filename);
		logger.info("请求数据："+fileInfo.toString());
		Queue<Map<String,String>> fileList = null;
		Object object = this.getServletContext().getAttribute("fileList");
		if(object==null){
			fileList =  new LinkedList<Map<String,String>>();
			logger.info(fileList.toString());
		}else{
			try {
				fileList = (Queue<Map<String, String>>) object;
			} catch (ClassCastException e) {
				logger.error("全局变量转换异常。");
			}
		}
		
		//添加到全局
		Set<Map<String,String>> set =  new HashSet<Map<String,String>>(fileList);
		if(set.add(fileInfo)){
			fileList.offer(fileInfo);
		}		
		logger.info(fileList.toString());
		this.getServletContext().setAttribute("fileList", fileList);
		out.println("1");
		out.flush();
		out.close();
		logger.info(".........finish...........");
	}
}
