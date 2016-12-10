<%@page pageEncoding="utf-8" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
    </script>
  </head>

<body>
  <div id = "choose_label">
      <p>
                            个人空间 ｡:.ﾟヽ(｡◕‿◕｡)ﾉﾟ.:｡+ﾟ
      </p>
  </div>
  <div id = "<%=basePath %>h_pic">
	  <!--if the pic's path is empty,use the default path. -->
	  <c:choose>
		<c:when test="${!empty h_pic_path}">
			<img src="<%=basePath %>${h_pic_path }" style="height:100px;width:100px;"/>
		</c:when>
		<c:otherwise>
			<img src="<%=basePath %>${defulat_path}" style="height:100px;width:100px;"/>
		</c:otherwise>
	  </c:choose>
	  
      <a href = "/RiXiang_blog/head/show.form">点击这里，上传头像</a><br>
	  <a href = "/RiXiang_blog/article/list.form">返回主页</a>
  </div>
</body>
</html>
