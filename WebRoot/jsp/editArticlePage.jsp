<%@page pageEncoding="utf-8" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK"%> 
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
             <link type="text/css" rel="stylesheet" href="<%=basePath %>css/fixed_background.css" media="all" />
              <script type="text/javascript" src="<%=basePath %>Jquery/jquery-2.2.3.min.js"></script>
              <script type="text/javascript" src="<%=basePath %>Jquery/jquery-form.js"></script>  
              <script src="fckeditor/fckeditor.js"></script>  
              <script src="fckeditor/editor/source/fckeditorapi.js"></script>    
              <script type="text/javascript">
                  $().ready(function()
                  {

                  	 $("#submit").click(function() {
                  	 	// alert("click");
                  	    // var fckeditor = FCKeditorAPI.GetInstance('articleContent');
                  	    // var str = fckeditor.GetHTML();
                  	    // alert(str);
                          $('#articleForm').ajaxForm(function(data)
                          {  
                               if(data = true)
                               {
                                     location.href = "/RiXiang_blog/space/list.form";
                               }
                           });   
                       });
                 }); 
               </script>
      </head>
      <body>
             <div id="scene"> 
                  <img src="<%=imgPath%>mainPageBanner.png" ALT=""/> 
             </div>
              <div id = "article_list">
                   <h2>添加文章</h2>
                   <form id="articleForm" action="edit.form" method="post">
                               <label>标题：</label>
                              <input type="text" name="title" value="${article.title}"/><br>
                               <label>内容：</label>
                               <FCK:editor instanceName="articleContent" basePath="/fckeditor" toolbarSet="myToolbar" height="400" value="${article.content }">
                               </FCK:editor>
                               <input type="text" style="display:none" name="articleAddr" value="${article.articleAddr}">
                               <input type="text" style="display:none" name="summaryAddr" value="${article.summaryAddr}">
                               <input type="text" style="display:none" name="id" value="${article.id}">
                               <input type="text" style="display:none" name="authorName" value="${article.authorName}">
                              <input id="submit" name="submit" type="submit" class="button" value="提交" />
                  </form>
			  </div>

              <div id="col_right">
                    <div id="menu">
                          <h2>主页导航</h2>
                          <ul>
                              <li><a href ="/RiXiang_blog/login/show.form">登录</a></li>
                              <li><a href ="/RiXiang_blog/register/show.form">注册</a></li>
                              <li><a href ="/RiXiang_blog/article/list.form">主页</a></li>
                              <li><c:if test="${!empty userName}"><a href ="/RiXiang_blog/space/list.form">个人空间 - ${userName}</a></c:if></li>
                              <li><a href ="/RiXiang_blog/game/snake.form">贪吃蛇</a></li>
                              <li><a href ="/RiXiang_blog/sonne/sonne.form">作者-博客开发记录</a></li>
                          </ul>
                    </div>
              </div>
      </body>
</html>