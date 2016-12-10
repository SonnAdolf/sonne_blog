package sonn.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
* @ClassName: IOUtill 
* @Description: I/O utils
* @author sonn
* @date 2016-5-20 21:00:18
*       2016-07-31 debug,loop wrong when read article by url.  
*       2016-12-06 use wangEditor, to correctly read and show codes.
*                  change the function readByUrl(String url)
* @version 1.0
 */
public final class IOUtill 
{
	private IOUtill(){}
	
	public static void writeByUrl(String url,String content)
	{
	    File file = new File(url);
		if (!file.getParentFile().exists())
		{
		   file.getParentFile().mkdirs();
		}
		try 
		{
		   file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();	
		}
		try {
		   FileWriter fw = new FileWriter(file, true);
		   BufferedWriter bw = new BufferedWriter(fw);
		   bw.write(content);
		   bw.flush();
		   bw.close();
		   fw.close();
		} catch (IOException e) {
		   e.printStackTrace();
		}
	}
	
	public static String readByUrl(String url)
	{
	    File file = new File(url);
		if (!file.getParentFile().exists())
		{
		   file.getParentFile().mkdirs();
		}
		String content = "";
		try 
		{
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			try
			{
				String line = br.readLine();
				while(line != null)
				{
					content += line;
					// 2016-12-06
					content += "\n";
					line = br.readLine();
				} 
				// 13 Nov
				br.close();
				fr.close();
			}catch (IOException ex) {
				ex.printStackTrace();
			}
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}
		return content;
	}
	

	public static boolean deleteFile(String fileName) 
	{
		File file = new File(fileName);
		if (file.exists() && file.isFile()) 
		{
			if (file.delete()) 
			{
				return true;
			} 
			else 
			{
				return false;
			}
		} 
		else
		{
			return false;
		}
	}

	public static boolean deleteDirectory(String dir) 
	{
		if (!dir.endsWith(File.separator))
			dir = dir + File.separator;
		File dirFile = new File(dir);
		if ((!dirFile.exists()) || (!dirFile.isDirectory())) 
		{
			return false;
		}
		boolean flag = true;
		File[] files = dirFile.listFiles();
		for (int i = 0; i < files.length; i++)
		{
			if (files[i].isFile()) {
				flag = deleteFile(files[i].getAbsolutePath());
				if (!flag)
					break;
			}
			else if (files[i].isDirectory()) 
			{
				flag = deleteDirectory(files[i].getAbsolutePath());
				if (!flag)
					break;
			}
		}
		if (!flag) 
		{
			return false;
		}
		if (dirFile.delete()) 
		{
			return true;
		} 
		else 
		{
			return false;
		}
	}

	public static boolean delete(String fileName)
	{
		if (fileName == null || fileName.equals(""))
		{
			return true;
		}
		File file = new File(fileName);
		if (!file.exists()) 
		{
			return false;
		} 
		else
		{
			if (file.isFile())
				return deleteFile(fileName);
			else
				return deleteDirectory(fileName);
		}
	}

   public static void delFolder(String folderPath) {
	   try {
		   delAllFile(folderPath);
		   String filePath = folderPath;
		   filePath = filePath.toString();
		   java.io.File myFilePath = new java.io.File(filePath);
		   myFilePath.delete(); //É¾³ý¿ÕÎÄ¼þ¼Ð
		   } catch (Exception e) {
			   e.printStackTrace(); 
			}
	   }

	 /*
	  * delet all files from a directory,but not the directory
	  */
	 public static boolean delAllFile(String path) {
		 boolean flag = false;
	     File file = new File(path);
	     if (!file.exists()) {
	        return flag;
	     }
	     if (!file.isDirectory()) {
	        return flag;
	     }
	     String[] tempList = file.list();
	     File temp = null;
	     for (int i = 0; i < tempList.length; i++) {
	         if (path.endsWith(File.separator)) {
	            temp = new File(path + tempList[i]);
	         } else {
	            temp = new File(path + File.separator + tempList[i]);
	         }
	        if (temp.isFile()) {
	            temp.delete();
	        }
	        if (temp.isDirectory()) {
	            delAllFile(path + "/" + tempList[i]);
	            delFolder(path + "/" + tempList[i]);
	            flag = true;
	        }
	     }
	     return flag;
	  }
}
