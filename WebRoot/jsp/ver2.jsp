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
      		  <div id = "title">
              <h4 class = "title">断 剑 重 铸 之 日<br><br>骑 士 归 来 之 时
                    <br></h4>

              </div>
              <div id="col_left">
                    <div id="menu">
                          <h2>日   向</h2>
                          <ul>
                              <c:if test="${empty userName}"><li><a href ="/RiXiang_blog/login/show.form">登录</a></li></c:if>
                              <c:if test="${empty userName}"><li><a href ="/RiXiang_blog/register/show.form">注册</a></li></c:if>
                              <li><a href ="">主页</a></li>
                              <c:if test="${!empty userName}"><li><a href ="/RiXiang_blog/space/list.form">个人主页 - ${userName}</a></li></c:if>
                              <c:if test="${!empty userName}"><li><a href ="/RiXiang_blog/mine/show.form">个人空间 - ${userName}</a></li></c:if>
                              <li><a href ="/RiXiang_blog/game/snake.form">贪吃蛇</a></li>
                              <li><a href ="/RiXiang_blog/sonne/sonne.form">作者-博客开发记录</a></li>
                          </ul>
                    </div>
              </div>
              <div id = "article_list">
                          <c:forEach items="${page.content}" var="article" >
                              <div id = "article_block">
                                  <span class = "title"><a href="<%=basePath %>article/showFromMainPage.form?id=${article.id}">${article.title}</a></span>&nbsp
                                  <span class = "author">作者：${article.authorName}</span>&nbsp&nbsp<span class = "date">日期：${fn:substring(article.date,0,16)}</span><br>
                                  <div id="h_pic">

                                  </div> 
                                  <p> 
                                  	    <c:choose>
										     <c:when test="${empty article.author || empty article.author.h_pic_path }">
										             <img class="h_pic" src="<%=basePath %>h_pics/default.jpg" alt>
										     </c:when>
		                                     <c:otherwise>
			                                        <img class="h_pic" src="<%=basePath %>${article.author.h_pic_path }" alt>
	                                         </c:otherwise>
	                                    </c:choose>
										${article.summary}
								  </p>
                               </div>
                               <p>------------------------------------------------------------------------------------- </p>
                          </c:forEach>     
                                                                    共${page.pageInfo.totalCount}条纪录，当前第${page.pageInfo.currentPage}/${page.pageInfo.totalPage}页，每页${page.pageInfo.everyPage}条纪录
                          <c:choose>
                                    <c:when test = "${page.pageInfo.hasPrePage}">
                                     			<a href="<%=basePath %>article/list.form?currentPage=1">首页</a>
				                                <a href="<%=basePath %>article/list.form?currentPage=${page.pageInfo.currentPage-1}">上一页</a>
                                    </c:when>
                                    <c:otherwise>
                                           		   首页
				                                                                                          上一页
                                    </c:otherwise>
                          </c:choose>
                          <c:choose>
                                     <c:when test = "${page.pageInfo.hasNextPage}">
                                                <a href="<%=basePath %>article/list.form?currentPage=${page.pageInfo.currentPage+1}">下一页</a> 
				                                <a href="<%=basePath %>article/list.form?currentPage=${page.pageInfo.totalPage}">尾页</a>
                                     </c:when>
                                     <c:otherwise>
                                                                                                                                          下一页
                                                                                                                                            尾页
                                     </c:otherwise>
                          </c:choose>
			  </div>

      </body>
</html>