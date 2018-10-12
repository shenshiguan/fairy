package org.ssg.utils;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

public class ConvertUtil {

	
	public static Dispatch getDispatch(){
        ComThread.InitSTA();
        ActiveXComponent com = new ActiveXComponent(ConfigUtil.get("com.convert")); //"FileConvert.FileConvertMain"
        Dispatch disp = com.getObject();
        return disp;
	}
	
	public static String ComWorking(Dispatch disp,String filePath,String fileName,String fileType){
        Variant var = Dispatch.call(disp, "dowork",filePath,fileName,fileType);
        String res = var.toString();
        return res;	
	}
	
    //结束进程
	public static void Release(){
		if(ComThread.haveSTA)
		 ComThread.Release();
	}
	

	
	
}
