package sonn.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
* @ClassName: StringUtil 
* @Description: 字符串处理工具类
* @author 无名
* @date 2016-4-30 下午10:26:48 
* @version 1.0
 */
public final class StringUtill
{
	private StringUtill()
	{
	}
	
	public static boolean isStringEmpty(String str)
	{
		if(null == str|| "".equals(str))
		{
			return true;
		}
		return false;
	}
	
	public static boolean isTheSameStr(String str1,String str2)
	{
		if(isStringEmpty(str1) || isStringEmpty(str2)||!str1.equals(str2))
		{
			return false;
		}
		return true;
	}
	
	/*
	 * clean all the tags of HTML
	 */
	public static String removeTag(String htmlStr) {
		String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; // script
		String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; // style
		String regEx_html = "<[^>]+>"; // HTML tag
		String regEx_space = "\\s+|\t|\r|\n";// other characters

		Pattern p_script = Pattern.compile(regEx_script,
			Pattern.CASE_INSENSITIVE);
		Matcher m_script = p_script.matcher(htmlStr);
		htmlStr = m_script.replaceAll("");
		Pattern p_style = Pattern
			.compile(regEx_style, Pattern.CASE_INSENSITIVE);
		Matcher m_style = p_style.matcher(htmlStr);
		htmlStr = m_style.replaceAll("");
		Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
		Matcher m_html = p_html.matcher(htmlStr);
		htmlStr = m_html.replaceAll("");
		Pattern p_space = Pattern
			.compile(regEx_space, Pattern.CASE_INSENSITIVE);
		Matcher m_space = p_space.matcher(htmlStr);
		htmlStr = m_space.replaceAll(" ");
		return htmlStr;
	}
	
}
