package sonn.util;

/**
 * @author 无名
 * @date 2016.04.21-2016.04.24 
 * @description:分页
 */
public class PageInfo 
{	
	public static final int DEFAULT_EVERYOAGE = 6;
	
	public static final int DEFAULT_CURRENTPAGE = 1;
	
	/**每页显示数量*/
	private int everyPage;
	
	/**总记录数*/
	private long totalCount;
	
	/**总页数*/
	private long totalPage;
	
	/**当前页*/
	private int currentPage;
	
	/**起始点*/
	private int beginIndex;
	
	/**是否有上一页*/
	private boolean hasPrePage;
	
	/**是否有下一页*/
	private boolean hasNextPage;

	public PageInfo(int everyPage, long total, long totalPage, int currentPage,
			int beginIndex, boolean hasPrePage, boolean hasNextPage)
	{
		this.everyPage = everyPage;
		this.totalCount = total;
		this.totalPage = totalPage;
		this.currentPage = currentPage;
		this.beginIndex = beginIndex;
		this.hasPrePage = hasPrePage;
		this.hasNextPage = hasNextPage;
	}

	//构造函数，默认
	public PageInfo()
	{
	}
	
	public PageInfo(int currentPage, int everyPage) 
	{
		this.everyPage = everyPage;
	}
	
	//构造方法，对所有属性进行设置
	public int getEveryPage() 
	{
		return everyPage;
	}

	public void setEveryPage(int everyPage) 
	{
		this.everyPage = everyPage;
	}

	public long getTotalCount() 
	{
		return totalCount;
	}

	public void setTotalCount(long totalCount)
	{
		this.totalCount = totalCount;
	}

	public long getTotalPage()
	{
		return totalPage;
	}

	public void setTotalPage(long totalPage) 
	{
		this.totalPage = totalPage;
	}

	public int getCurrentPage()
	{
		return currentPage;
	}

	public void setCurrentPage(int currentPage)
	{
		this.currentPage = currentPage;
	}

	public int getBeginIndex()
	{
		return beginIndex;
	}

	public void setBeginIndex(int beginIndex)
	{
		this.beginIndex = beginIndex;
	}

	public boolean isHasPrePage() 
	{
		return hasPrePage;
	}

	public void setHasPrePage(boolean hasPrePage)
	{
		this.hasPrePage = hasPrePage;
	}

	public boolean isHasNextPage() 
	{
		return hasNextPage;
	}

	public void setHasNextPage(boolean hasNextPage)
	{
		this.hasNextPage = hasNextPage;
	}

}

