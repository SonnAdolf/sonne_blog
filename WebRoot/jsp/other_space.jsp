<%@page pageEncoding="utf-8" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String imgPath = basePath + "image/";
%>
<!DOCTYPE html>
<html>
      <head>
             <title>日向博客</title>
             <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
             <link type="text/css" rel="stylesheet" href="<%=basePath %>css/myspace.css" media="all" /> 
      </head>
      <body>
			  <div id="sonn_title">
					<p id="main_word">断剑重铸之日，骑士归来之时</p>   
			  </div>

		    <div id="header">
		      	 <div id="navigator">
		      		<ul id="navList">
		                 <li><font>日   向  博  客&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp</font></li>
		                 <c:if test="${empty my_name}"><li><a href ="/RiXiang_blog/login/show.form">登录</a></li></c:if>
		                 <c:if test="${empty my_name}"><li><a href ="/RiXiang_blog/register/show.form">注册</a></li></c:if>
		                 <li><a href ="/RiXiang_blog/article/list.form">主页</a></li>
		                 <c:if test="${!empty my_name}"><li><a href ="/RiXiang_blog/passwd/show.form">修改密码</a></li></c:if>
		                 <c:if test="${!empty my_name}"><li><a href ="/RiXiang_blog/space/list.form">个人主页 </a></li></c:if>
		                 <c:if test="${!empty my_name}">
		                       <li>
		                            <a href ="/RiXiang_blog/mine/show.form">
		                                                                                                个人空间	
		                                   <c:if test="${!empty has_new_msg}">
		                 	                    <span id="new_msg_txt">【新消息】</span>
		                 	               </c:if>
		                 	        </a>
		                 	   </li>
		                 </c:if>
		                 <c:if test="${!empty my_name}"><li><a href ="/RiXiang_blog/article/writeArticlePage.form">写博客</a></li></c:if>
		                 <li><a href ="/RiXiang_blog/game/snake.form">游戏</a></li>
		                 <li><a href ="/RiXiang_blog/sonne/blog.form">日向技术</a></li>
		           	</ul>
		         </div>
		     </div>
              <div id = "h_pic">
                   <!--if the pic's path is empty,use the default path. -->
                   <c:choose>
  	                    <c:when test="${!empty h_pic_path}">
  	           	              <img src="<%=basePath %>${h_pic_path }" alt/>
  	                    </c:when>
  	                    <c:otherwise>
  	           	              <img src="<%=basePath %>${defulat_path}" alt/>
  	                     </c:otherwise>
                    </c:choose>
					<br>
                    <!-- &nbsp&nbsp&nbsp<a href = "/RiXiang_blog/head/show.form">上传头像</a><br> -->
                    &nbsp&nbsp&nbsp博名:${userName}<br>
                    &nbsp&nbsp&nbsp博龄:${blog_age}
              </div>
              <div id = "main_page" class="main_page">
                       <div id="article_lst">
			                 <c:forEach items="${page.content}" var="article" >
						            <div id="article_block" class="article_block">
                                        <span id = "article_title"><a href = "/RiXiang_blog/article/show.form?id=${article.id}">${article.title}</a></span>
							        </div>
                             </c:forEach>
                         </div>							 
                    <div id = "page_select">   
                           共${page.pageInfo.totalCount}条纪录，当前第${page.pageInfo.currentPage}/${page.pageInfo.totalPage}页，每页${page.pageInfo.everyPage}条纪录
                          <c:choose>
                                    <c:when test = "${page.pageInfo.hasPrePage}">
                                     			<a href="<%=basePath %>space/other_space.form?currentPage=1&usr_name=${userName}">首页</a>
				                                <a href="<%=basePath %>space/other_space.form?currentPage=${page.pageInfo.currentPage-1}&usr_name=${userName}">上一页</a>
                                    </c:when>
                                    <c:otherwise>
                                           		   首页
				                                                                                          上一页
                                    </c:otherwise>
                          </c:choose>
                          <c:choose>
                                     <c:when test = "${page.pageInfo.hasNextPage}">
                                                <a href="<%=basePath %>space/other_space.form?currentPage=${page.pageInfo.currentPage+1}&usr_name=${userName}">下一页</a> 
				                                <a href="<%=basePath %>space/other_space.form?currentPage=${page.pageInfo.totalPage}&usr_name=${userName}">尾页</a>
                                     </c:when>
                                     <c:otherwise>
                                                                                                                                          下一页
                                                                                                                                            尾页
                                     </c:otherwise>
                          </c:choose>
                     </div>
			  </div>
      </body>
</html>