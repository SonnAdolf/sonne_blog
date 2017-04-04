<%@ page pageEncoding="utf-8" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
   <head>
     <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
     <link rel="shortcut icon" type="image/x-icon" href="http://118.89.29.170/RiXiang_blog/favicon.ico">
     <link type="text/css" rel="stylesheet" href="<%=basePath %>css/error.css" media="all" />
     <title>日向博客</title>
     <style>
           body {
               color:white;
               background-color:black;
           }
     </style>
   </head>
   <body>
         <br><br><br>网站错误发生，请逃离地球!<a href = "/RiXiang_blog/article/list.form" style="color:#90ee90">返回主页</a><br>
                         穿越宇宙的广阔黑幕，奔向远方孤独的星球。<br>
                         渺小愚蠢的人类必将灭亡，永恒不灭的唯有神的光。<br>
                         那是人类永远无法理解的谜题。<br>               
         <img src="<%=basePath %>image/error.jpg" style=""/>
   </body>
</html>
