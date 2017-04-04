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
     <link type="text/css" rel="stylesheet" href="<%=basePath %>css/error.css" media="all" />
     <link rel="shortcut icon" type="image/x-icon" href="http://118.89.29.170/RiXiang_blog/favicon.ico">
     <title>日向博客</title>
   </head>
   <body>
         <br><br><br>404，您输入的网址无法找到。<a href = "/RiXiang_blog/article/list.form">返回主页</a><br>
         <img src="<%=basePath %>image/404.jpg" style=""/>
   </body>
</html>
