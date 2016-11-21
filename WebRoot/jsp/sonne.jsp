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
    <script type="text/javascript" src="<%=basePath %>js/photos.js"></script>
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
    <h1>Hi, My name is Sonne</h1>
    <ul id="imagegallery">
      <li>
        <a href="<%=basePath %>image/photos/sonne.jpg" title="A little spark kindles a great fire.">
          <img src="<%=basePath %>image/photos/thumbnail_sonne.jpg" alt="the band in concert" />
        </a>
      </li>
      <li>
        <a href="<%=basePath %>image/photos/sonn_book.jpg" title="Rome was not built in a day.">
          <img src="<%=basePath %>image/photos/thumbnail_sonn_book.jpg" alt="the bassist" />
        </a>
      </li>
      <li>
        <a href="<%=basePath %>image/photos/sonn_smile.jpg" title="Adversity leads to prosperity.">
          <img src="<%=basePath %>image/photos/thumbnail_sonn_smile.jpg" alt="the guitarist" />
        </a>
      </li>
      <li>
        <a href="<%=basePath %>image/photos/sonn_hey.jpg" title="A good horse cannot be of a bad colour.">
          <img src="<%=basePath %>image/photos/thumbnail_sonn_hey.jpg" alt="the audience" />
        </a>
      </li>
    </ul>
  </div>
    <audio controls="controls" autoplay="autoplay" height="100" width="100">
  		  <source src="<%=basePath %>music/Breath and Life.mp3" type="audio/mp3" />
          <source src="<%=basePath %>music/Breath and Life.ogg" type="audio/ogg" />
          <embed height="100" width="100" src="<%=basePath %>music/Breath and Life.mp3" />
     </audio>
</body>
</html>
