package sonn.util;

/**
 * @author 无名
 * @date 2016.04.21 2016-5-1final类并添加私有构造函数
 * @description:分页信息辅助类
 */
public final class PageUtils 
{
	
	private PageUtils(){}
	
	/**
	 * 根据everyPage，total，currentPage创建分页信息对象
	 */
    public static PageInfo createPage(int everyPage,long total,int currentPage)
    {
        everyPage = getEveryPage(everyPage);
        currentPage = getCurrentPage(currentPage);
        long totalPage = getTotalPage(everyPage, total);
        int beginIndex = getBeginIndex(everyPage, currentPage);
        boolean hasPrePage = getHasPrePage(currentPage);
        boolean hasNextPage = getHasNextPage(totalPage, currentPage);
        return new PageInfo(everyPage, total, totalPage, currentPage,
                beginIndex, hasPrePage,  hasNextPage);
    }
    
	/**
	 * 根据everyPage，total，currentPage重新整理分页信息对象
	 */
    public static void setPageInfo(PageInfo pageInfo,int everyPage,long total,int currentPage)
    {
        everyPage = getEveryPage(everyPage);
        currentPage = getCurrentPage(currentPage);
        long totalPage = getTotalPage(everyPage, total);
        int beginIndex = getBeginIndex(everyPage, currentPage);
        boolean hasPrePage = getHasPrePage(currentPage);
        boolean hasNextPage = getHasNextPage(totalPage, currentPage);
        pageInfo.setEveryPage(everyPage);
        pageInfo.setCurrentPage(currentPage);
        pageInfo.setTotalPage(totalPage);
        pageInfo.setBeginIndex(beginIndex);
        pageInfo.setHasNextPage(hasNextPage);
        pageInfo.setHasPrePage(hasPrePage);
        pageInfo.setTotalCount(total);
    }
    
    /**
     * 获得每页显示记录数
     */
    public static int getEveryPage(int everyPage)
    {        
        return everyPage == 0 ? 6: everyPage;
    }
    
    /**
     * 获得当前页
     */
    public static int getCurrentPage(int currentPage)
    {    
        return currentPage == 0 ? 1 : currentPage;
    }
    
    /**
     * 获得总页数
     */
    public static long getTotalPage(int everyPage,long total)
    {
        long totalPage = 0;
        if(total != 0 &&total % everyPage == 0)
        {
            totalPage = total / everyPage;
        }
        else 
        {
            totalPage = total / everyPage + 1;
        }
        return totalPage;
    }
    
    /**
     * 获得起始位置
     */
    public static int getBeginIndex(int everyPage,int currentPage)
    {
        return (currentPage - 1) * everyPage;
    }
    
    /**
     * 获得是否有上一页
     */
    public static boolean getHasPrePage(int currentPage)
    {
        return currentPage == 1 ? false : true;
    }
    
    /**
     * 获得是否有上一页
     */
    public static boolean getHasNextPage(long totalPage, int currentPage) 
    {    
        return currentPage == totalPage || totalPage == 0 ? false : true;
    }
	
}
