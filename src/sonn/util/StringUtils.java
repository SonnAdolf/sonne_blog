package sonn.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.safety.Whitelist;

/**
 * @ClassName: StringUtil
 * @Description: 字符串处理工具类
 * @author 无名
 * @date 2016-4-30 下午10:26:48
 *       2017-02-02 replace & with &amp; function
 * @version 1.0
 */
public final class StringUtils {
	private StringUtils() {
	}

	public static boolean isStringEmpty(String str) {
		if (null == str || "".equals(str)) {
			return true;
		}
		return false;
	}

	public static boolean isTheSameStr(String str1, String str2) {
		if (isStringEmpty(str1) || isStringEmpty(str2) || !str1.equals(str2)) {
			return false;
		}
		return true;
	}

	public static boolean isContainsChinese(String str) {
		String regEx = "[\u4e00-\u9fa5]";
		Pattern pat = Pattern.compile(regEx);
		Matcher matcher = pat.matcher(str);
		boolean flg = false;
		if (matcher.find()) {
			flg = true;
		}
		return flg;
	}
	
	/*
	 * check if the string contains html tags,and replace
	 *  to prevent xss attacks.
	 */
	public static String replace_html_tags(String str_input) {
		str_input = str_input.replaceAll("<", "&lt;");
		str_input = str_input.replaceAll(">", "&gt;");
		return str_input;
	}
	
	/*
	 * replace & with &amp;
	 */
	public static String replace_and_tags(String str_input) {
		str_input = str_input.replaceAll("&", "&amp;");
		return str_input;
	}
	
	/*
	 * replace &amp; with &
	 */
	public static String replace_and_tags2(String str_input) {
		str_input = str_input.replaceAll("&amp;", "&");
		return str_input;
	}


	public static boolean contains_sqlinject_illegal_ch(String str_input) {
		// "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]"
		String regEx = "['=<>;\"]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str_input);
		if (m.find()) {
			return true;
		} else {
			return false;
		}
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

	
	/*
	 * Cross-site scripting (XSS) is a type of computer security vulnerability
	 * typically found in web applications. XSS enables attackers to inject
	 * client-side scripts into web pages viewed by other users. A cross-site
	 * scripting vulnerability may be used by attackers to bypass access
	 * controls such as the same-origin policy. Cross-site scripting carried out
	 * on websites accounted for roughly 84% of all security vulnerabilities
	 * documented by Symantec as of 2007. Their effect may range from a petty
	 * nuisance to a significant security risk, depending on the sensitivity of
	 * the data handled by the vulnerable site and the nature of any security
	 * mitigation implemented by the site's owner.(From en.wikipedia.org)
	 * 
	 * 	@deprecated
	 */
	@Deprecated
	public static boolean contains_xss_illegal_str(String str_input) {
		final String REGULAR_EXPRESSION = 
				"<(?!a )(?!p )(?!img )(?!img=)(?!code )(?!span )(?!div )(?!pre )(?!font )(?!!--)(?!/)[^>]*>";
		final Pattern PATTERN = Pattern.compile(REGULAR_EXPRESSION);
		final Matcher MATCHER = PATTERN.matcher(str_input);
		List<String> str_lst = new ArrayList<String>();
		while (MATCHER.find()) {
			str_lst.add(MATCHER.group());
		}
		final String  LEGAL_TAGS = "<a><img><div><p><span><pre><br><code>" +
				"<b><u><i><strike><font><blockquote><ul><li><ol><table><tr><td>";
		for (String str:str_lst) {
			if (!LEGAL_TAGS.contains(str)) {
				return true;
			}
		}
		return false;
	}
	
    /**
    <p>
    This whitelist allows a fuller range of text nodes: <code>a, b, blockquote, br, cite, code, dd, dl, dt, em, i, li,
    ol, p, pre, q, small, span, strike, strong, sub, sup, u, ul</code>, and appropriate attributes.
    </p>
    <p>
    Links (<code>a</code> elements) can point to <code>http, https, ftp, mailto</code>, and have an enforced
    <code>rel=nofollow</code> attribute.
    </p>
    <p>
    Does not allow images.
    </p>

    @return whitelist
    */
   public static Whitelist basic() {
       return new Whitelist()
               .addTags(
                       "a", "b", "blockquote", "br", "cite", "code", "dd", "dl", "dt", "em",
                       "i", "li", "ol", "p", "pre", "q", "small", "span", "strike", "strong", "sub",
                       "sup", "u", "ul")

               .addAttributes("a", "href")
               .addAttributes("blockquote", "cite")
               .addAttributes("q", "cite")
               .addAttributes("code", "class", "codemark")
               .addAttributes("span", "class")
               .addAttributes("pre", "style")

               .addProtocols("a", "href", "ftp", "http", "https", "mailto")
               .addProtocols("blockquote", "cite", "http", "https")
               .addProtocols("cite", "cite", "http", "https")

               .addEnforcedAttribute("a", "rel", "nofollow")
               ;

   }
   
   /**
   This whitelist allows the same text tags as {@link #basic}, and also allows <code>img</code> tags, with appropriate
   attributes, with <code>src</code> pointing to <code>http</code> or <code>https</code>.

   @return whitelist
   */
  public static Whitelist basicWithImages() {
      return basic()
              .addTags("img")
              .addAttributes("img", "align", "alt", "height", "src", "title", "width")
              .addProtocols("img", "src", "http", "https")
              ;
  }
}
