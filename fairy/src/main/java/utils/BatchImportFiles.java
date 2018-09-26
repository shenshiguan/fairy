package utils;

public class BatchImportFiles {

	public static void main(String[] args) {
		
		/***
		 * 把所有 word 转换成为 pdf
		 */
		String filePath = "E:\\资料包\\无水印系统上传_10811\\上海交通大学\\医学、生物学基础_642\\试卷\\2017_回忆\\901804_2017年上海交通大学医学生物学基础642真题.docx";
		
		
		
		
		String infile =  filePath.substring(0, filePath.lastIndexOf("."));
		
		WordConvertPdf.wordToPDF(filePath, infile+".pdf");
		
	}

}
