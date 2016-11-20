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
             <link type="text/css" rel="stylesheet" href="<%=basePath %>css/show_article.css" media="all" />
      </head>
      <body>
      		  <div id = "title">
              <h4 class = "title">你是我的半截的诗 <br><br>
                                                                                                  不许别人更改一个字    ---海子<br>
                                                                                             
                    <br></h4>
              </div>
              <div id="col_left">
                    <div id="menu">
                          <h2>日   向</h2>
                          <ul>
                              <li><a href ="/RiXiang_blog/login/show.form">登录</a></li>
                              <li><a href ="/RiXiang_blog/register/show.form">注册</a></li>
                              <li><a href ="/RiXiang_blog/article/list.form">主页</a></li>
                             <c:if test="${!empty userName}"> <li><a href ="/RiXiang_blog/space/list.form">个人空间 - ${userName}</a></li></c:if>
                              <c:if test="${!empty userName}"><li><a href ="/RiXiang_blog/article/writeArticlePage.form">写博客</a></li></c:if>
                              <li><a href ="/RiXiang_blog/game/snake.form">贪吃蛇</a></li>
                          </ul>
                    </div>
              </div>
              <div id = "article_list">
                                  <span id = "article_title">${article.title}</span>
                                  <span id = "author">author：${article.authorName}</span><br> 
                                     <p>文章内容： ${article.content}</p>
			  </div>


      </body>
</html>