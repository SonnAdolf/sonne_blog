package sonn.test;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

public class Sonn_Test {

	@Test
	public void test_Xss_check() {
		String str_input = "<a ss><script><img dsds=>sds<body><a></adsd><img= dsdas><d/s><!--dsds><p dsd><pp><a><dsds>dsdas<font ds><fontdsdsd><font>das<oooioacc><pp sds><script><code ><br><code><ccc><abug>";
		System.out.println("String inputed:" + str_input);
		final String REGULAR_EXPRESSION = 
				"<(?!a )(?!p )(?!img )(?!img=)(?!code )(?!span )(?!div )(?!pre )(?!font )(?!!--)(?!/)[^>]*>";
		final Pattern PATTERN = Pattern.compile(REGULAR_EXPRESSION);
		final Matcher MATCHER = PATTERN.matcher(str_input);
		List<String> str_lst = new ArrayList<String>();
		while (MATCHER.find()) {
			str_lst.add(MATCHER.group());
		}
		final String  LEGAL_TAGS = "<a><img><div><p><span><pre><br><code><h1><h2><h3><h4><h5>" +
				"<b><u><i><strike><font><blockquote><ul><li><ol><table><tr><td>";
		for (String str:str_lst) {
			if (!LEGAL_TAGS.contains(str)) {
					System.out.println(str + " is illegal");
			}
		}
	}
}











// final String REGULAR_EXPRESSION =
// "<(((?!p )(?!a )[^>]*)((?!p)(?!a))(?!strike))>";
// final String REGULAR_EXPRESSION = "<((?!p )(?!a )[^>]*)>";
// /(a\\s+[^>]*)(p\\s+[^>]*)(img\\s+[^>]*)(span\\s+[^>]*)(pre\\s+[^>]*)(code\\s+[^>]*)(font\\s+[^>]*)
// final String REGULAR_EXPRESSION = "<(?!p[a-z])(?!a[a-z])>";
