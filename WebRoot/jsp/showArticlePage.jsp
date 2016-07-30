<%@page pageEncoding="utf-8" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
             <link type="text/css" rel="stylesheet" href="<%=basePath %>css/main.css" media="all" />
      </head>
      <body>
             <div id="scene"> 
                  <img src="<%=imgPath%>mainPageBanner.png" ALT=""/> 
             </div>
              <div id = "article_list">
                                  <span id = "title">${article.title}</span>
                                  <span id = "author">author：${article.authorName}</span><br> 
                                     <p>文章内容： ${article.content}</p>
			  </div>

              <div id="col_right">
                    <div id="menu">
                          <h2>主页导航</h2>
                          <ul>
                              <li><a href ="/RiXiang_blog/login/show.form">Login</a></li>
                              <li><a href ="/RiXiang_blog/register/show.form">Register</a></li>
                              <li><a href ="">Blog List</a></li>
                              <li><c:if test="${!empty userName}"><a href ="/RiXiang_blog/space/list.form">Myspace - ${userName}</a></c:if></li>
                              <li><c:if test="${!empty userName}"><a href ="/RiXiang_blog/article/writeArticlePage.form">Write Article</a></c:if></li>
                          </ul>
                    </div>
              </div>
      </body>
</html>