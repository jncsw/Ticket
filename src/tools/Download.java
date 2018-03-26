package tools;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class Download {
	public static String relPath;
	
	public static String picture(String picUrl,String fileName) {
		try {
			if(relPath==null)
				return picUrl;
			URL url=new URL(picUrl);
			URLConnection uc = url.openConnection();
			InputStream is = uc.getInputStream();
			String returnpath ="imgs/"+fileName+".jpg";
			String path=relPath+returnpath;
			File file = new File(path);
			System.out.println( path);
			if(!file.exists()){
				file.createNewFile();
			}
			FileOutputStream out = new FileOutputStream(file);
			int i = 0;
			while ((i = is.read()) != -1) {
				out.write(i);
			}
			is.close();
			return returnpath;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return picUrl;
	}
}
