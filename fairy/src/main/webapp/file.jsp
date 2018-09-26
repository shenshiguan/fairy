<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
 <style>
	span{
		 padding:2cm 2cm 2cm 2cm;
	}
 </style>
 <body>
  
	<div>
	<span><a href="" id = "links">2017考研真题（英语）</a></span>
	<span><button value="下载" onclick="getFile();">下载</button></span>
	</div>
	<span>文件地址：</span>	
	<input type="text" name = "url"			id="url"			value="http://demo.ziliao.kuakao.com/"			size = "150" maxlength="300"/>
	
	
	<span>文&nbsp;件&nbsp;&nbsp;名：	</span> 
	<input type="text" name = "fileName"	id="fileName"		value="111.doc"									size = "150"/>
	
	
	<span>K&nbsp;&nbsp;&nbsp;E&nbsp;&nbsp;&nbsp;Y：		</span> 
	<input type="text" name = "arg_key"		id="arg_key"		value="11111111112222222222"					size = "150"/>
	
	
	<span>CookieId：</span>	
	<input type="text" name = "COOKIE_uid"	id="COOKIE_uid"		value="999999999999999999999999999999999"		size = "150"/>
	
	
	<span>userAgent:</span>	
	<input type="text" name = "useragent"	id="useragent"		value="Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0)"		size = "150"/>
	
	
	
	</body>
 <script type="text/javascript">
	
 	function getFile(){
		var url = document.getElementById("url").value;
		var fileName = document.getElementById("fileName").value;
		var arg_key = document.getElementById("arg_key").value;
		var COOKIE_uid = document.getElementById("COOKIE_uid").value;
		var arg_t= new Date().getTime();
		var useragent = navigator.userAgent;
		document.getElementById("useragent").value = useragent;
		var path = url+fileName+"?arg_t="+arg_t+"&arg_key="+arg_key+"&COOKIE_uid="+COOKIE_uid+"&useragent="+useragent;
		alert(path);
		window.location.href = path;
	}
	
 </script>
</html>