package com.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
* @ClassName: IOUtill 
* @Description: I/O工具类
* @author 无名
* @date 2016-5-20 下午9:00:18 
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
				content += line;
				while(line != null)
				{
					line = br.readLine();
					content += line;
				} 
			}catch (IOException ex) {
				ex.printStackTrace();
			}
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}
		return content;
	}
}
