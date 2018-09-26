package org.ssg.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 虚拟浏览器 支持https请求
 */
public class HttpClientUtil {
	private static Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);
	private static Integer TIME_OUT = 60 * 1000;

	private HttpClientUtil() {
		Protocol https = new Protocol("https", new HTTPSSecureProtocolSocketFactory(), 443);
		Protocol.registerProtocol("https", https);
	}

	/**
	 * 下载文件
	 * 
	 * @param fileUrl
	 * @param filePath
	 */
	public static void downloadFile(String fileUrl, String filePath) {
		try {
			URL url = new URL(fileUrl);
			FileUtils.copyURLToFile(url, new File(filePath));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取url内容
	 *
	 * @param url
	 * @return
	 */
	public static String get(String url) {
		long startTime = System.currentTimeMillis();
		logger.info("request url:" + url);
		byte[] file = getByte(url);
		if (file != null) {
			String result = new String(file);
			logger.info(" url reponse:" + result + " --> tmies: " + (System.currentTimeMillis() - startTime));
			return result;
		}
		return "";
	}

	/**
	 * 获取url返回的二进制形式
	 *
	 * @param url
	 * @return
	 */
	public static byte[] getByte(String url) {
		GetMethod getMethod = new GetMethod(url);
		getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
		getMethod.setRequestHeader("X-Requested-With", "XMLHttpRequest");
		getMethod.setRequestHeader("User-Agent", "Mozilla/5.0");
		getMethod.setRequestHeader("Content-Type", "application/json");
		int statusCode = 0;
		try {
			HttpClient httpClient = new HttpClient();
			statusCode = httpClient.executeMethod(getMethod);
			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("Method failed: " + getMethod.getStatusLine());
			}
			return getMethod.getResponseBody();
		} catch (Exception e) {
			logger.error("https link error", e);
		} finally {
			getMethod.releaseConnection();
		}
		return null;
	}

	/**
	 * 只请求Get连接 不用返回
	 * 
	 * @param url
	 */
	public static void sendGet(String url) {
		GetMethod getMethod = new GetMethod(url);
		getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
		getMethod.setRequestHeader("X-Requested-With", "XMLHttpRequest");
		getMethod.setRequestHeader("User-Agent", "Mozilla/5.0");
		try {
			HttpClient httpClient = new HttpClient();
			httpClient.executeMethod(getMethod);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * post提交数据到url并返回字符串
	 *
	 * @param url
	 * @param params
	 * @return
	 */
	public static String post(String url, NameValuePair[] params) {
		PostMethod postMethod = new PostMethod(url);
		postMethod.setRequestHeader("X-Requested-With", "XMLHttpRequest");
		postMethod.setRequestHeader("User-Agent", "Mozilla/5.0");
		postMethod.setRequestBody(params);
		int statusCode = 0;
		try {
			HttpClient httpClient = new HttpClient();
			statusCode = httpClient.executeMethod(postMethod);
			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("Method failed: " + postMethod.getStatusLine());
			}
			return postMethod.getResponseBodyAsString();
		} catch (Exception e) {
			logger.error("https link error", e);
		} finally {
			postMethod.releaseConnection();
		}
		return null;
	}

	/**
	 * post数据
	 *
	 * @param url
	 * @param data
	 */
	@SuppressWarnings("deprecation")
	public static String post(String url, String data) {
		PostMethod postMethod = new PostMethod(url);
		postMethod.setRequestHeader("X-Requested-With", "XMLHttpRequest");
		postMethod.setRequestHeader("User-Agent", "Mozilla/5.0");
		postMethod.setRequestHeader("Content-Type", "application/json");
		try {
			HttpClient httpClient = new HttpClient();
			RequestEntity requestEntity = new StringRequestEntity(data);
			postMethod.setRequestEntity(requestEntity);
			httpClient.executeMethod(postMethod);
			return postMethod.getResponseBodyAsString();
		} catch (Exception e) {
			logger.error("https link error", e);
		} finally {
			postMethod.releaseConnection();
		}
		return "";
	}

	/**
	 * 系统对接的方法
	 *
	 * @param url
	 * @param data
	 */
	public static String sendPost(String url, String param) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			logger.info("请求路径："+url);
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			logger.info("发送 POST 请求出现异常！" + e);
			e.printStackTrace();
		} finally {// 使用finally块来关闭输出流、输入流
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		try {
			result = new String(result.getBytes("ISO-8859-1"), "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static String post(String url, String sign, long timestamp) {
		PostMethod postMethod = new PostMethod(url);
		postMethod.setRequestHeader("X-Requested-With", "XMLHttpRequest");
		postMethod.setRequestHeader("User-Agent", "Mozilla/5.0");
		postMethod.setRequestHeader("sign", sign);
		try {
			HttpClient httpClient = new HttpClient();
			httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(TIME_OUT);
			httpClient.executeMethod(postMethod);
			return postMethod.getResponseBodyAsString();
		} catch (Exception e) {
			logger.error("https link error", e);
		} finally {
			postMethod.releaseConnection();
		}
		return "";
	}

	public static String post(String url, String data, String sign, long timestamp) {
		PostMethod postMethod = new PostMethod(url);
		postMethod.setRequestHeader("X-Requested-With", "XMLHttpRequest");
		postMethod.setRequestHeader("User-Agent", "Mozilla/5.0");
		postMethod.setRequestHeader("sign", sign);
		try {
			HttpClient httpClient = new HttpClient();
			RequestEntity requestEntity = new StringRequestEntity(data, "application/json", "UTF-8");
			postMethod.setRequestEntity(requestEntity);
			httpClient.executeMethod(postMethod);
			return postMethod.getResponseBodyAsString();
		} catch (Exception e) {
			logger.error("https link error", e);
		} finally {
			postMethod.releaseConnection();
		}
		return "";
	}

	public static String get(String url, String sign, long timestamp) {
		GetMethod getMethod = new GetMethod(url);
		getMethod.setRequestHeader("X-Requested-With", "XMLHttpRequest");
		getMethod.setRequestHeader("User-Agent", "Mozilla/5.0");
		getMethod.setRequestHeader("sign", sign);
		try {
			HttpClient httpClient = new HttpClient();
			httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(TIME_OUT);
			httpClient.executeMethod(getMethod);
			return getMethod.getResponseBodyAsString();
		} catch (Exception e) {
			logger.error("https link error", e);
		} finally {
			getMethod.releaseConnection();
		}
		return "";
	}

	public static String postByToken(String url, String token) {
		PostMethod postMethod = new PostMethod(url);
		postMethod.setRequestHeader("X-Requested-With", "XMLHttpRequest");
		postMethod.setRequestHeader("User-Agent", "Mozilla/5.0");
		postMethod.setRequestHeader("token", token);
		try {
			HttpClient httpClient = new HttpClient();
			httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(TIME_OUT);
			httpClient.executeMethod(postMethod);
			return postMethod.getResponseBodyAsString();
		} catch (Exception e) {
			logger.error("https link error", e);
		} finally {
			postMethod.releaseConnection();
		}
		return "";
	}
	
	/** 
	 * 上传文件 
	 *  
	 * @param serverUrl 
	 *            服务器地址 
	 * @param localFilePath 
	 *            本地文件路径 
	 * @param serverFieldName 
	 * @param params 
	 * @return 
	 * @throws Exception 
	 */  
	public static String uploadFileImpl(String serverUrl, String localFilePath,  
	        String serverFieldName, Map<String, String> params)  
	        throws Exception {  
	    String respStr = null;  
	    CloseableHttpClient httpclient = HttpClients.createDefault();  
	    try {  
	        HttpPost httppost = new HttpPost(serverUrl);  
	        FileBody binFileBody = new FileBody(new File(localFilePath));  
	        logger.info("++++++++++++++"+new File(localFilePath).getName());
	        MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();  
	        // add the file params  
	        multipartEntityBuilder.addPart(serverFieldName, binFileBody);  
	        // 设置上传的其他参数  
	        setUploadParams(multipartEntityBuilder, params);  
	
	        HttpEntity reqEntity = multipartEntityBuilder.build();  
	        httppost.setEntity(reqEntity);  
	
	        CloseableHttpResponse response = httpclient.execute(httppost);  
	        try {  
//	            logger.info(response.getStatusLine());  
	            HttpEntity resEntity = response.getEntity();  
	            respStr = getRespString(resEntity);  
	            EntityUtils.consume(resEntity);  
	        } finally {  
	            response.close();  
	        }  
	    } finally {  
	        httpclient.close();  
	    }  
	    logger.info("resp=" + respStr);  
	    return respStr;  
	}  
	
	/** 
	 * 设置上传文件时所附带的其他参数 
	 *  
	 * @param multipartEntityBuilder 
	 * @param params 
	 */  
	private static void setUploadParams(MultipartEntityBuilder multipartEntityBuilder,  
	        Map<String, String> params) {  
	    if (params != null && params.size() > 0) {  
	        Set<String> keys = params.keySet();  
	        for (String key : keys) {  
	            multipartEntityBuilder.addPart(key, new StringBody(params.get(key),ContentType.MULTIPART_FORM_DATA));  
	        }  
	    }  
	}  
	
	/** 
	 * 将返回结果转化为String 
	 *  
	 * @param entity 
	 * @return 
	 * @throws Exception 
	 */  
	private static String getRespString(HttpEntity entity) throws Exception {  
	    if (entity == null) {  
	        return null;  
	    }  
	    InputStream is = entity.getContent();  
	    StringBuffer strBuf = new StringBuffer();  
	    byte[] buffer = new byte[10240];  
	    int r = 0;  
	    while ((r = is.read(buffer)) > 0) {  
	        strBuf.append(new String(buffer, 0, r, "UTF-8"));  
	    }  
	    return strBuf.toString();  
	}
	
	public static String postFile(String guid,String filePath){
		try {
			String result =  uploadFileImpl(ConfigUtil.get("fb.convert.server")+"?fileId="+guid,filePath,"filename", null);
			return result;
		} catch (Exception e) {
			e.printStackTrace();  
		    return null;
		}
	}
	public static void main(String[] args) {
//		String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=tIiXkB_cjKsNu22JD2oZX8slWAPFDxDhWbn7cl68no0PlUwVTfD56NpeGqedoosBkTElcsnIu3h6lRcETaoi-nO8ktwsuG1fF_ByEzIRQq654Cd1KH38hvF8d9DcYF3sBDLiAIATVD";
//		String result = HttpClientUtil.post(url, "");
//		logger.info(result);
        // 上传文件 POST 同步方法  
        try {  
            Map<String,String> uploadParams = new LinkedHashMap<String, String>();  
            uploadParams.put("guid", "8855222");  
            HttpClientUtil.uploadFileImpl("http://10.1.19.195:8080/fairy/UploadServlet", "C:\\888.ppt","filename", uploadParams);
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
	}
}
