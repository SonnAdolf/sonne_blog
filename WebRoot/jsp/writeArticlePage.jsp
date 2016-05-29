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
             <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
             <link type="text/css" rel="stylesheet" href="<%=basePath %>css/main.css" media="all" />
                           <script type="text/javascript" src="<%=basePath %>Jquery/jquery-2.2.3.min.js"></script>
              <script type="text/javascript" src="<%=basePath %>Jquery/jquery-form.js"></script>              
              <script type="text/javascript">
                /*  $().ready(function()
                  {     
                         $('#articleForm').ajaxForm(function(data)
                         {  
                               if(data.success)
                               {
                                     alert("add article SUCCESS");
                               }
                               else
                               {
                                     alert("add article FAIL");
                                }
                          });   
                 });  */
               </script>
      </head>
      <body>
             <div id="scene"> 
                  <img src="<%=imgPath%>mainPageBanner.png" ALT=""/> 
             </div>
              <div id = "article_list">
                   <h2>添加文章</h2>
                   <form id="articleForm" action="writeArticle.form" method="post">
                               <label>标题：</label>
                              <input type="text" name="title" /><br>
                               <label>内容：</label>
                               <FCK:editor instanceName="articleContent" basePath="/fckeditor" toolbarSet="myToolbar" height="400"></FCK:editor>
                              <input name="提交" type="submit" class="button" value="提交" />
                  </form>
			  </div>

              <div id="col_right">
                    <div id="menu">
                          <h2>主页导航</h2>
                          <ul>
                              <li><a href ="/RiXiang_blog/login/show.form">Login</a></li>
                              <li><a href ="/RiXiang_blog/register/show.form">Register</a></li>
                              <li><a href ="/RiXiang_blog/article/list.form">Blog List</a></li>
                          </ul>
                    </div>
              </div>
      </body>
</html>