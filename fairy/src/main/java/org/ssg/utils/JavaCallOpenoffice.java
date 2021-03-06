package org.ssg.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


public class JavaCallOpenoffice {
	

    private Process process=null;
	private Scanner in;
    
    
	  /**
     * 将启动程序定义在构造函数中，直接生成该类对象，即可启动openoffice服务
     */
    public JavaCallOpenoffice() {
		Runtime rn = Runtime.getRuntime();
		try {
			File file=new File(ConfigUtil.get("java.OpenOffice.bat"));
			if (false==file.exists()) {
			    FileWriter writer = new FileWriter(ConfigUtil.get("java.OpenOffice.bat"));
				writer.write("@echo   off ");
				writer.write("\r\n ");
				writer.write("C:");
				writer.write("\r\n ");
				//D:\\Program Files\\OpenOffice 4\\program： openoffice的安装路径路径
				writer.write("cd "+ConfigUtil.get("java.OpenOffice.path"));
				writer.write("\r\n ");
				writer.write("soffice -headless -accept="+"socket,host=127.0.0.1,port=8100;urp;"+" -nofirststartwizard");
				writer.write("\r\n ");
				writer.write("@echo   on ");
				writer.close();
			}
			rn.exec("cmd.exe /C "+ConfigUtil.get("java.OpenOffice.bat"));
		}
		catch (Exception e1) {
	       e1.printStackTrace();
		}
	}
    
    /**
     * 构造方法，实现关闭soffice进程
     * @return 
     */
    public void distorySoffice() {
        try {
            //显示进程
            process=Runtime.getRuntime().exec("tasklist");
            in = new Scanner(process.getInputStream());
            while (in.hasNextLine()) {
                String processString=in.nextLine();
                if (processString.contains("soffice.exe")) {
                    //关闭soffice进程的命令
                    String cmd="taskkill /f /im soffice.exe";
                    process=Runtime.getRuntime().exec(cmd);
                    System.out.println("openoffice正常关闭.......");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }    
    
}
