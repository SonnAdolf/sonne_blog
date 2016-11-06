<%@page pageEncoding="utf-8" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String imgPath = basePath + "image/";
%>
<!DOCTYPE html>
<html>
      <head>
             <title>日向blog</title>
             <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
             <link type="text/css" rel="stylesheet" href="<%=basePath %>css/myspace.css" media="all" />
             <script type="text/javascript" src="<%=basePath %>Jquery/jquery-2.2.3.min.js"></script>
             <script type="text/javascript" src="<%=basePath %>Jquery/jquery-form.js"></script>              
             <script type="text/javascript">
				    var oAjax = null;
					if(window.XMLHttpRequest){
   				         oAjax = new XMLHttpRequest();
                    }else{
                         oAjax = new ActiveXObject('Microsoft.XMLHTTP');
                    }
                    
                    function button_Click_1(btn){
	  					var delete_id = btn.id;
	  					alert(delete_id);
	  					url = "http://localhost:8888/RiXiang_blog/article/delete.form?id=" + delete_id 
	  					oAjax.open('POST', url, true);
	  					oAjax.send();
   					    oAjax.onreadystatechange = function(){  
       						 if(oAjax.readyState == 4){  
       						     if(oAjax.status == 200){    
        				             alert("delete successfully.");
        					         location.reload(); 
        						 }else{
          			    		      alert("delete failed");
          						  }
       						 }
   						 };
                     } 
               </script>
      </head>
      <body>
              <div id="col_left">
                    <div id="menu">
                          <h2>Sonne Blog</h2>
                          <ul>
                              <li><a href ="/RiXiang_blog/login/show.form">Login</a></li>
                              <li><a href ="/RiXiang_blog/register/show.form">Register</a></li>
                              <li><a href ="/RiXiang_blog/article/list.form">Blog List</a></li>
                              <li><c:if test="${!empty userName}"><a href ="">Myspace - ${userName}</a></c:if></li>
                              <li><c:if test="${!empty userName}"><a href ="/RiXiang_blog/article/writeArticlePage.form">Write Article</a></c:if></li>
                          </ul>
                    </div>
              </div>
              <div id = "article_list">
                          <c:forEach items="${page.content}" var="article" >
                                  <span id = "article_title"><a href = "/RiXiang_blog/article/show.form?id=${article.id}">${article.title}</a></span>
                                  <button type="button" id=${article.id} onclick="button_Click_1(this)">delete</button><br> 
                                  <!-- <p>文章内容： ${article.content}</p> -->   
                          </c:forEach>  
                    <div id = "page_select">   
                                                                    共${page.pageInfo.totalCount}条纪录，当前第${page.pageInfo.currentPage}/${page.pageInfo.totalPage}页，每页${page.pageInfo.everyPage}条纪录
                          <c:choose>
                                    <c:when test = "${page.pageInfo.hasPrePage}">
                                     			<a href="list.form?currentPage=1">首页</a>
				                                <a href="list.form?currentPage=${page.pageInfo.currentPage-1}">上一页</a>
                                    </c:when>
                                    <c:otherwise>
                                           		   首页
				                                                                                          上一页
                                    </c:otherwise>
                          </c:choose>
                          <c:choose>
                                     <c:when test = "${page.pageInfo.hasNextPage}">
                                                <a href="list.form?currentPage=${page.pageInfo.currentPage+1}">下一页</a> 
				                                <a href="list.form?currentPage=${page.pageInfo.totalPage}">尾页</a>
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