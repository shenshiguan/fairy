package servlets;
import org.apache.commons.lang3.StringUtils;
import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import org.apache.log4j.Logger;
import com.jacob.com.Dispatch;
import utils.HttpClientUtil;
import utils.ConfigUtil;
import utils.ConvertUtil;
import java.util.Queue;
import java.util.Map;


@WebServlet(urlPatterns={"/mainServlet"},loadOnStartup=1)
public class MainServelt extends HttpServlet {

	Logger logger = Logger.getLogger(MainServelt.class);
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Worker worker = null;

	
	@Override
	public void init() throws ServletException {
		logger.info("...................init.....................");
		if(worker==null){
			worker = new Worker();
		}
		logger.info("...............start...............");
		new Thread(worker).start();
		logger.info("...............finish...............");
	}
	
	class Worker implements Runnable{
		String fileId ="";

		@SuppressWarnings("unchecked")
		public void run() {
			String sendpdf_url 		= ConfigUtil.get("java.service.url.sendpdf");
			String javaconvert_url 	= ConfigUtil.get("java.service.url.convert");
			logger.info(sendpdf_url);
			try{
				String filePath = ConfigUtil.get("file.path");
				logger.info(filePath);
				Dispatch disp = ConvertUtil.getDispatch();
				while(true){
					if(disp==null){
						disp = ConvertUtil.getDispatch();
					}
					Queue<Map<String,String>> fileslist = null;
					String filename ="";
					try {
						Object object = getServletContext().getAttribute("fileList");
						if(object==null){
						
						}else{
							try {
								fileslist = (Queue<Map<String, String>>) object;
							} catch (ClassCastException e) {
								logger.error("全局变量转换异常。");
							}
							if(fileslist.size()>0){
								Map<String, String> fileInfo = fileslist.element();
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
								        logger.info("dll转码结果："+res);
//									    String res = ConvertUtil.convertPdf(filePath, filename,fileType, fileId);
								        if(!"1".equals(res)){
									    	if("-2000".equals(res)){
										    	//JAVA重新转码  /javaConvert
									    		String pdfFile = filename.substring(0,filename.lastIndexOf("."))+".pdf";
										    	HttpClientUtil.sendPost(javaconvert_url+"?fileId="+fileId+"&officeFile="+filename+"&pdfFile="+pdfFile, null);
										    }else{
										    	logger.info("文件转换失败,文件名称："+filename+",失败原因："+res);
										    	//发送通知
										    	HttpClientUtil.sendPost(sendpdf_url+"?guid="+fileId+"&filePath=-1", null);
										    }
									    }
									    else{
									    	//发送通知
									    	filename = filename.substring(0, filename.lastIndexOf("."))+".pdf";
									    	HttpClientUtil.sendPost(sendpdf_url+"?guid="+fileId+"&filePath="+filename, null);
									    }
							        }
								}
							    fileslist.poll();
							    getServletContext().setAttribute("fileList",fileslist);
								logger.info(System.currentTimeMillis());
							}
						}
						Thread.sleep(1000);
					} catch (Exception e) {
						e.printStackTrace();
						logger.error("全局变量转换异常。");
					}
				}
			}catch(Exception all){
				all.printStackTrace();
				logger.error("线程异常结束。");
		    	//发送通知
		    	HttpClientUtil.sendPost(sendpdf_url+"?guid="+fileId+"&filePath=-1", null);
				
			}finally{
				ConvertUtil.Release();
				logger.error("关闭转码线程。");
			}
			
		} //========run==========
	}	//========class==========
	
}
