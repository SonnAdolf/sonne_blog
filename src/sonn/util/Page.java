package sonn.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 无名
 * @date 2016.04.22
 * @description：分页类
 */
public class Page<T> 
{
	/** 内容 */
	private List<T> content = new ArrayList<T>();
	
	/** 总记录数 */
	private final long total;
	
	/** 分页信息 */
	private final PageInfo pageInfo;
	
	/**
	 * 初始化一个新创建的Page对象
	 */
	public Page(List<T> content, long total, PageInfo pageInfo)
	{
		if (null != content)
		{
		    this.content.addAll(content);
		}
		this.total = total;
		this.pageInfo = pageInfo;
	}

	public List<T> getContent() 
	{
		return content;
	}
	
	public void setContent(List<T> list)
	{
		this.content = list;
	}

	public PageInfo getPageInfo() 
	{
		return pageInfo;
	}

	public long getTotal() 
	{
		return total;
	}
	
}
