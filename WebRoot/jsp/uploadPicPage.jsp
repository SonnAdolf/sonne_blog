<%@page pageEncoding="utf-8" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>日向博客</title>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
    
    <meta name="viewport" content="width=device-width,height=device-height,inital-scale=1.0,maximum-scale=1.0,user-scalable=no;">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="format-detection" content="telephone=no">
             
    <link type="text/css" rel="stylesheet" href="<%=basePath %>css/uploadPic.css" media="all" />
  </head>

<body>
  <div id = "choose_label">
      <p>
                            为你的blog选一个漂亮的头像 (●´∀｀●)<br/><a href ="/RiXiang_blog/article/list.form">返回主页</a>
      </p>
  </div>
  
  	<div id = "pic_upload_form">
  	        <form id="pic_form" action="uploadPic.form" enctype="multipart/form-data" method="post">
                  <input name="pic" type="file"/><br/>
                  <input type="submit" value="提交"/>
                  <p>【请选择宽高相等的正方形图片，否则会在图片压缩过程中产生空白。】</p>
            </form>
   </div>

    <script type="text/javascript" src="<%=basePath %>Jquery/jquery-1.3.1.js"></script>
    <script type="text/javascript" src="<%=basePath %>Jquery/jquery.form.js"></script>    
    <script type="text/javascript">
                  $().ready(function()
                  {
                          $('#pic_form').ajaxForm(function(data)
                          {  
                               if(data = true)
                               {
                                     location.href = "/RiXiang_blog/mine/show.form";
                               }
                           });   
                 });
    </script>
</body>
</html>
