<%@page pageEncoding="utf-8" contentType="text/html;charset=utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
    <title>日向博客</title>
    <link rel="stylesheet" type="text/css" media="screen" href="<%=basePath %>css/basic.css" />
  </head>
  
<body>
  <div id="header">
    <img src="<%=basePath %>image/logo.gif" alt="Jay Skript and the Domsters" />
  </div>
  <div id="navigation">
    <ul>
      <li><a href="/RiXiang_blog/article/list.form">主页</a></li>
      <li><a href="/RiXiang_blog/sonne/sonne.form">关于Sonne</a></li>
      <li><a href="/RiXiang_blog/sonne/blog.form">关于日向博客</a></li>
      <li><a href="/RiXiang_blog/game/snake.form">游戏</a></li>
    </ul>
  </div>
  <div id="content">
    <h1>Sonne</h1>
      <p>我是Sonne，一名普通程序员。这是我创作的博客网站，我将之命名为Sonne Blog。</p>
      <p>我是从大学开始学编程的，但我不是计算机专业的学生，我只是**大学的一名日语专业文科生。
                当时学习编程，纯属兴趣。记得那时常常感到迷茫，有一天在图书馆闲逛，不经意间翻起一本又厚又旧的C语言书（大胡子鬼佬写的）。
                 当时就觉得，woow，cool~ 然后就这样学起了编程。</p>
      <p>所以，我学的第一门编程语言是C语言~除此之外，我还学了些现在基本用不上的东西，例如算法和数据结构还有汇编语言。
                  总体上，我大学学到的并不多。因为自学艰难，还走了很多弯路，大三时又遇到些挫折，堕落了两年。
                  整个大学学的东西和计算机专业的学生相比，真是差距不小。但也算是入门了，并打下了一点基础。</p>
       <p>我是快毕业时才开始学java，当时靠着撇脚的技术，勉强找到一份工作，写了许多现在想起来相当丑陋的程序。
                 总之，我的技术大部分还是在工作中摸索到的。在实践和挫折中成长到现在已经快一年半了。</p>
       <p>现在，我已经进步了很多，java web方面，这个网站就是我做的，这就算是进步了。此外，我学习了Linux和Python。
                  业余实践方面，我准备做这样四个项目：一是Sonne Blog（基于java的本博客网站），二是Ciel（基于网络编程的聊天软件），三是Stone（自制操作系统），
                         四是，Aeu（自制编程语言）。这四个项目涉及java、python、C、汇编、网络、操作系统、编译原理、算法等多方面技术，
                         是我自我修炼的四个训练场。</p>
        <p>我的github:https://github.com/SonnAdolf</p>
        <p>我的博客园:http://www.cnblogs.com/rixiang/</p>
		<img src="<%=basePath %>image/blog_history/books.jpg" alt="Sonne Blog" /><br>
    </div>
    <audio controls="controls" autoplay="autoplay" height="100" width="100">
  		  <source src="<%=basePath %>music/Main Title.mp3" type="audio/mp3" />
          <source src="<%=basePath %>music/Main Title.ogg" type="audio/ogg" />
          <embed height="100" width="100" src="<%=basePath %>music/Main Title.mp3" />
     </audio>
     <script type="text/javascript" src="<%=basePath %>js/global.js"></script>
</body>
</html>
