<%@page pageEncoding="utf-8" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String imgPath = basePath + "image/";
%>
<!DOCTYPE html>
<html>
      <head>
             <title>日向blog</title>
             <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
             <link type="text/css" rel="stylesheet" href="<%=basePath %>css/sonn.css" media="all" />
      </head>
      <body>              
		    <div id="header">
		      	 <div id="navigator">
		      		<ul id="navList">
		                 <li><font>日   向  博  客&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp</font></li>
		                 <c:if test="${empty userName}"><li><a href ="/RiXiang_blog/login/show.form">登录</a></li></c:if>
		                 <c:if test="${empty userName}"><li><a href ="/RiXiang_blog/register/show.form">注册</a></li></c:if>
		                 <li><a href ="">主页</a></li>
		         		 <c:if test="${!empty userName}"><li><a href ="/RiXiang_blog/passwd/show.form">修改密码</a></li></c:if>
		                 <c:if test="${!empty userName}"><li><a href ="/RiXiang_blog/space/list.form">个人主页 - ${userName}</a></li></c:if>
		                 <c:if test="${!empty userName}">
		                      <li>
		                             <a href ="/RiXiang_blog/mine/show.form">
		                                                                                               个人空间 - ${userName}
		                                  <c:if test="${!empty has_new_msg}">
		                 	                   <span id="new_msg_txt">【新消息】</span>
		                 	             </c:if>
		                            </a>
		                 	 </li>
		                </c:if>
		                 <c:if test="${!empty userName}"><li><a href ="/RiXiang_blog/article/writeArticlePage.form">写博客</a></li></c:if>
		                 <li><a href ="/RiXiang_blog/game/snake.form">贪吃蛇</a></li>
		                 <li><a href ="/RiXiang_blog/sonne/blog.form">作者-博客开发记录</a></li>
		           	</ul>
		         </div>
		     </div>
			 <div id="poem_left">
				  <p>
				       <poem>
					          <br>
							  <img id="pic_left" src="<%=basePath %>image/walker.jpg" alt/>
							  <br><br>
							  我是否该写一首悲伤的歌<br>
							  在你睡觉的时候唱给你听<br>
							  我是否该写一首哀怨的歌<br>
							  在你伤心的时候唱给你听<br>

					   </poem>
				  </p>
			 </div>
              <div id = "article_list">
                          <c:forEach items="${page.content}" var="article" >
                              <div id = "article_block">
                                  <p class="article_content"> 
							       	   <span class = "title"><a href="<%=basePath %>article/show.form?id=${article.id}">${article.title}</a></span><br>
                                  	    <c:choose>
										     <c:when test="${empty article.author || empty article.author.h_pic_path }">
										             <a href="<%=basePath %>space/other_space.form?usr_name=${article.authorName}" target="_blank"><img class="h_pic" src="<%=basePath %>h_pics/default.jpg" alt></a>
										     </c:when>
		                                     <c:otherwise>
			                                         <a href="<%=basePath %>space/other_space.form?usr_name=${article.authorName}" target="_blank"><img class="h_pic" src="<%=basePath %>${article.author.h_pic_path }" alt></a>
	                                         </c:otherwise>
	                                    </c:choose>
										${article.summary}<br>
										 <div id = "author_date"> by <a href="<%=basePath %>space/other_space.form?usr_name=${article.authorName}">${article.authorName}</a>&nbsp&nbsp${fn:substring(article.date,0,16)}&nbsp阅读${article.read_times}&nbsp评论${fn:length(article.comments)}</div>
								  </p>
                               </div>
                         </c:forEach>     
						 <div id="footer">
                                <font class="page_txt">
										          共${page.pageInfo.totalCount}条纪录，当前第${page.pageInfo.currentPage}/${page.pageInfo.totalPage}页，每页${page.pageInfo.everyPage}条纪录</font>
                                <c:choose>
                                          <c:when test = "${page.pageInfo.hasPrePage}">
                                           			<a class="page_txt" href="<%=basePath %>article/list.form?currentPage=1">首页</a>
				                                      <a class="page_txt" href="<%=basePath %>article/list.form?currentPage=${page.pageInfo.currentPage-1}">上一页</a>
                                          </c:when>
                                          <c:otherwise>
                                                 		   <font class="page_txt">首页
				                                                                 上一页</font>
                                          </c:otherwise>
                                </c:choose>
                                <c:choose>
                                           <c:when test = "${page.pageInfo.hasNextPage}">
                                                      <a class="page_txt" href="<%=basePath %>article/list.form?currentPage=${page.pageInfo.currentPage+1}">下一页</a> 
				                                      <a class="page_txt" href="<%=basePath %>article/list.form?currentPage=${page.pageInfo.totalPage}">尾页</a>
                                           </c:when>
                                           <c:otherwise>
                                                      <font class="page_txt">下一页
                                                        尾页</font>
                                           </c:otherwise>
                                </c:choose>
						</div>
            </div>

      </body>
</html>