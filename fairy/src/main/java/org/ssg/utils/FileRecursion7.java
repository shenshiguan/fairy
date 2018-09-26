package org.ssg.utils;

import java.io.File;
import java.io.IOException;

import org.ssg.utils.WordConvertPdf;

public class FileRecursion7 {
	static LogOut logout = new LogOut();
	public static void main(String[] args) {
//		String filePath = "E:/资料包/无水印手动整理_19031/";
		String filePath = "E:/资料包/";
		File file = new File(filePath);
		if(file!=null){
			recursionDirectory(file);
		}
		try {
			logout.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void recursionDirectory(File file_1){
		String file_suffix = "";
		try {
			if(file_1.isDirectory()){
				File[] files = file_1.listFiles();
				for(File file :files){
					recursionDirectory(file);
				}
			}else{
				String fileName = file_1.getName().toLowerCase();
				file_suffix = fileName.substring(fileName.indexOf("."), fileName.length());
				if(".doc".equals(file_suffix)||".docx".equals(file_suffix)){
					String fileName_new =  fileName.substring(0, fileName.indexOf("."))+".pdf";
					String directory = file_1.getParentFile().getAbsolutePath();
					WordConvertPdf.wordToPDF(file_1.getAbsolutePath().trim(), directory+"\\"+fileName_new);
					logout.fileChaseFW(file_1.getAbsolutePath());
					Thread.sleep(5000);
					file_1.delete();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
}