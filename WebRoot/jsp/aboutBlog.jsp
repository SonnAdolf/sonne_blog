<%@page pageEncoding="utf-8" contentType="text/html;charset=utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
    <title>Jay Skript And The Domsters: Photos</title>
    <link rel="stylesheet" type="text/css" media="screen" href="<%=basePath %>css/basic.css" />
    <script type="text/javascript" src="<%=basePath %>js/global.js"></script>
  </head>
  
<body>
  <div id="header">
    <img src="<%=basePath %>image/logo.gif" alt="Jay Skript and the Domsters" />
  </div>
  <div id="navigation">
    <ul>
      <li><a href="/RiXiang_blog/article/list.form">主页</a></li>
      <li><a href="/RiXiang_blog/sonne/sonne.form">关于Sonne</a></li>
      <li><a href="/RiXiang_blog/sonne/sonne_pic.form">相册</a></li>
      <li><a href="/RiXiang_blog/sonne/blog.form">关于日向博客</a></li>
      <li><a href="/RiXiang_blog/game/snake.form">游戏</a></li>
    </ul>
  </div>
  <div id="content">
    <h1>一点历史关于,Sonne Blog</h1>
        <br>
		2016.03.25<br>
		springmvc + hibernate框架搭建。<br>
		2016.04.21<br>
		日向blog首页。<br>
		2016.04.24<br>
		分页实现。<br>
		2016.04.30<br>
		登录功能，实现jquery控制表单提交，并对返回结果做出响应。<br>
		2016.05.01<br>
		MySQL主键自增长设置。<br>
		2016.05.07<br>
		登录验证码实现。<br>
		2016.05.15<br>
		日向blog，添加了fckeditor。有了写文章的功能。<br>
		2016.05.21<br>
		日向blog做了一番新的总结，第一版完成在即。<br>
		日向blog个人空间功能完成了一部分。<br>
		2016.06.19<br>
		添加登陆跳转个人空间功能，html charset由utf-8改为gb2312。<br>
		个人空间文章列表，点击文章标题跳转到文章页面。<br>
		2016.07.27<br>
		自定义注解实现前后台参数校验技术初步实践。<br>
		2016.07.30<br>
		添加个人空间向写文章页面的跳转，反之亦然。<br>
		设置每页只显示两篇文章。这样更好布局。<br>
		2016.07.31
		修改一个bug，根据url读文章最后一行显示null。while循环写错，导致多读了一行。<br>
		主页，文章list，显示的每个文章设置为一个div。<br>
		2016.10.18<br>
		新的博客主页页面。<br>
		2016.10.19 - 2016.10.26<br>
		服务器部署项目成功。<br>
		加入对aticle表为空的判断。<br>
		2016.10.30 - 2016.10.31<br>
		解决部署服务器后验证码无法显示问题。<br>
		2016.11.01 - 2016.11.04<br>
		解决中文字符乱码问题。涉及数据库和hibernate和fckeditor。<br>
		2016.11.05<br>
		设定注册后存储session，并跳转个人主页。<br>
		加入注册判断，若已经登录不可以注册。<br>
		设定提交文章后跳转个人主页。<br>
		初步写出了登录、注册页面。<br>
		2016.11.08<br>
		研究了下bootstrap，做出了响应式的登录注册页。质的飞跃。<br>
		2016.11.09<br>
		继续修饰页面，把主页坑爹的背景图干掉了。<br>
		将分页相对路径换为绝对路径，免得手机端跳页报404.<br>
		2016.11.11<br>
		实现文章主页显示摘要功能。<br>
		2016.11.13<br>
		实现文章编辑功能。<br>
		2016.11.19<br>
		做出贪吃蛇游戏页面。<br>
    </div>
    <audio controls="controls" autoplay="autoplay" height="100" width="100">
  		  <source src="<%=basePath %>music/Flower Dance.mp3" type="audio/mp3" />
          <source src="<%=basePath %>music/Flower Dance.mp3" type="audio/ogg" />
          <embed height="100" width="100" src="<%=basePath %>music/Flower Dance.mp3" />
     </audio>
</body>
</html>
