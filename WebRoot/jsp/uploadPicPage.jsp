<%@page pageEncoding="utf-8" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK"%> 
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
    <script type="text/javascript" src="<%=basePath %>Jquery/jquery-1.3.1.js"></script>
    <script type="text/javascript" src="<%=basePath %>Jquery/jquery.form.js"></script>    
    <title>Sonn Blog</title>
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
  </head>

<body>
  <div id = "choose_label">
      <p>
                            为你的blog选一个漂亮的头像๑乛◡乛๑ (●´∀｀●)
      </p>
  </div>
  <form id="pic_form" action="uploadPic.form" enctype="multipart/form-data" method="post">
  	     上传头像：<br>
      <input name="pic" type="file">
      <input type="submit" value="submit">
  </form>
</body>
</html>
