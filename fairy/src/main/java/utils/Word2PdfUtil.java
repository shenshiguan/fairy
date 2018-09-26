package utils;


import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
 
import org.aspectj.weaver.ast.Test;
 
import com.aspose.words.Document;
import com.aspose.words.License;
import com.aspose.words.SaveFormat;

public class Word2PdfUtil {

	
	public static void main(String[] args) {

//		try {
//			Document doc;
//			doc = new Document("C:/bbb/空军工程大学808数字电路逻辑设计（A卷）2014年考研真题.doc");
//			doc.save("C:/bbb/空军工程大学808数字电路逻辑设计（A卷）2014年考研真题____.pdf", SaveFormat.PDF);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

		
		
			StringBuffer sb = new StringBuffer();
			sb.append("");
			sb.append("c5768fe3-4e88-4e28-8544-4e9095826805.doc	,	https://ekuakao.oss-cn-beijing.aliyuncs.com/kuakao_front/ambFiles/3acc547bd0ba401bb9d510156217e23c_%E7%A9%BA%E5%86%9B%E5%B7%A5%E7%A8%8B%E5%A4%A7%E5%AD%A6801%E7%94%B5%E6%9C%BA%E5%AD%A6%EF%BC%88A%E5%8D%B7%EF%BC%892014%E5%B9%B4%E8%80%83%E7%A0%94%E7%9C%9F%E9%A2%98.doc?Expires=3110855827&OSSAccessKeyId=LTAI3o1OZHRkwtpw&Signature=o8c%2BhxPEkKnySnZ2UKdQ16/MLDU%3D&code=1534055827267	|||");
			sb.append("1735c9b6-2a36-49d6-b1ff-46a12f86e482.doc	,	https://ekuakao.oss-cn-beijing.aliyuncs.com/kuakao_front/ambFiles/e0d2e74e8694430580ea211cedeb3d89_%E7%A9%BA%E5%86%9B%E5%B7%A5%E7%A8%8B%E5%A4%A7%E5%AD%A6801%E7%94%B5%E6%9C%BA%E5%AD%A6%EF%BC%88A%E5%8D%B7%EF%BC%892016%E5%B9%B4%E8%80%83%E7%A0%94%E7%9C%9F%E9%A2%98.doc?Expires=3110855859&OSSAccessKeyId=LTAI3o1OZHRkwtpw&Signature=riHn3K1LgschORBUfbq9yse2KX4%3D&code=1534055859715	|||");
			sb.append("2b8a6fa2-8075-4df8-b929-e2b406d7ae85.doc	,	https://ekuakao.oss-cn-beijing.aliyuncs.com/kuakao_front/ambFiles/fa2dfc3c9f874d6e8af699445655141c_%E7%A9%BA%E5%86%9B%E5%B7%A5%E7%A8%8B%E5%A4%A7%E5%AD%A6807%E6%95%B0%E5%AD%97%E7%94%B5%E8%B7%AF%E9%80%BB%E8%BE%91%E8%AE%BE%E8%AE%A1%EF%BC%88A%E5%8D%B7%EF%BC%892016%E5%B9%B4%E8%80%83%E7%A0%94%E7%9C%9F%E9%A2%98.doc?Expires=3110856470&OSSAccessKeyId=LTAI3o1OZHRkwtpw&Signature=EW7lt95lbFrUE/OOWyFlLcPvhDE%3D&code=1534056470941	|||");
			sb.append("3ca22a06-8ea5-488b-8f78-fb28e4bae255.doc	,	https://ekuakao.oss-cn-beijing.aliyuncs.com/kuakao_front/ambFiles/b5c74a1a8f0b4d08b6ad304a66eb5974_%E7%A9%BA%E5%86%9B%E5%B7%A5%E7%A8%8B%E5%A4%A7%E5%AD%A6808%E6%95%B0%E5%AD%97%E7%94%B5%E8%B7%AF%E9%80%BB%E8%BE%91%E8%AE%BE%E8%AE%A1%EF%BC%88A%E5%8D%B7%EF%BC%892014%E5%B9%B4%E8%80%83%E7%A0%94%E7%9C%9F%E9%A2%98.doc?Expires=3110856557&OSSAccessKeyId=LTAI3o1OZHRkwtpw&Signature=FxHMSg240K1Q9DJAJh2At97mvNY%3D&code=1534056557010	|||");
			sb.append("314ad69a-f9dd-40df-aa40-6001d35cced4.doc	,	https://ekuakao.oss-cn-beijing.aliyuncs.com/kuakao_front/ambFiles/a5ad7135c6bd4d7eb91e256afe0f92b9_%E7%A9%BA%E5%86%9B%E5%B7%A5%E7%A8%8B%E5%A4%A7%E5%AD%A6837%E5%B7%A5%E7%A8%8B%E7%83%AD%E5%8A%9B%E5%AD%A6%EF%BC%88A%E5%8D%B7%EF%BC%892015%E5%B9%B4%E8%80%83%E7%A0%94%E7%9C%9F%E9%A2%98.doc?Expires=3110857427&OSSAccessKeyId=LTAI3o1OZHRkwtpw&Signature=RMKZjlabvPbfxIIAi3TmF9UaJxk%3D&code=1534057427053	|||");
			sb.append("9d8978f5-ed43-4c32-8573-ec52e7feac10.doc	,	https://ekuakao.oss-cn-beijing.aliyuncs.com/kuakao_front/ambFiles/40d855301a3746a29802801a977a6b92_%E7%A9%BA%E5%86%9B%E5%B7%A5%E7%A8%8B%E5%A4%A7%E5%AD%A6841%E5%8F%AF%E9%9D%A0%E6%80%A7%E4%B8%8E%E7%BB%B4%E4%BF%AE%E6%80%A7%E5%B7%A5%E7%A8%8B%EF%BC%88A%E5%8D%B7%EF%BC%892016%E5%B9%B4%E8%80%83%E7%A0%94%E7%9C%9F%E9%A2%98.doc?Expires=3110857629&OSSAccessKeyId=LTAI3o1OZHRkwtpw&Signature=YeSnD2JNwgdCqr5Gk8a20rTks8Q%3D&code=1534057629350	|||");
			sb.append("485fff20-568d-4e94-98fc-94946e2d70e4.doc	,	https://ekuakao.oss-cn-beijing.aliyuncs.com/kuakao_front/ambFiles/eed0d1d5f8144672bbb51b1ebed9b7e7_%E6%9D%AD%E5%B7%9E%E5%B8%88%E8%8C%83%E5%A4%A7%E5%AD%A6820%E6%9C%89%E6%9C%BA%E5%8C%96%E5%AD%A6%EF%BC%88%E4%B8%80%EF%BC%892018%E5%B9%B4%E8%80%83%E7%A0%94%E7%9C%9F%E9%A2%98.doc?Expires=3110970767&OSSAccessKeyId=LTAI3o1OZHRkwtpw&Signature=ba9WfBp75vhdiRfaW6dUGEU6xnE%3D&code=1534170767292	|||");
			
			String[] ars = sb.toString().trim().split("\\|\\|\\|");
			for(String tmp : ars){
				System.out.println(tmp);
				String[] args_ = tmp.trim().split(",");
				System.out.println(args_[0]);
				System.out.println(args_[1]);
				
				doc2pdf(args_[1].trim(), "C:/bbb/"+(args_[0].trim()));
			}
		
		/**
		 * 去水印
		 */
		// doc2pdf("C:/bbb/空军工程大学837工程热力学（A卷）2015年考研真题.doc", "C:/bbb/空军工程大学837工程热力学（A卷）2015年考研真题_.pdf");
		
		
	}

    public static boolean getLicense() {
        boolean result = false;
        try {
            InputStream is = Test.class.getClassLoader().getResourceAsStream("license.xml"); // license.xml应放在..\WebRoot\WEB-INF\classes路径下
            License aposeLic = new License();
            aposeLic.setLicense(is);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
 
    public static void doc2pdf(String inPath, String outPath) {
        if (!getLicense()) { // 验证License 若不验证则转化出的pdf文档会有水印产生
            return;
        }
        try {
            long old = System.currentTimeMillis();
            File file = new File(outPath); // 新建一个空白pdf文档
            FileOutputStream os = new FileOutputStream(file);
            Document doc = new Document(inPath); // Address是将要被转化的word文档
            doc.save(os, SaveFormat.PDF);// 全面支持DOC, DOCX, OOXML, RTF HTML, OpenDocument, PDF,
                                         // EPUB, XPS, SWF 相互转换
            long now = System.currentTimeMillis();
            System.out.println("共耗时：" + ((now - old) / 1000.0) + "秒"); // 转化用时
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	
}
