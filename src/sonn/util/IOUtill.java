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
					line = br.readLine();
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
