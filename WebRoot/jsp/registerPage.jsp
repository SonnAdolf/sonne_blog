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
             <link rel="stylesheet" href="<%=basePath %>bootstrap-3.3.0-dist/dist/css/bootstrap.min.css"/> 
             <link type="text/css" rel="stylesheet" href="<%=basePath %>css/register.css" media="all" />
              <script type="text/javascript" src="<%=basePath %>Jquery/jquery-2.2.3.min.js"></script>
              <script type="text/javascript" src="<%=basePath %>Jquery/jquery-form.js"></script>  
              <script type="text/javascript" src="<%=basePath %>bootstrap-3.3.0-dist/js/bootstrap.min.js"></script>               
              <script type="text/javascript">
                  $().ready(function()
                  {     
                         $('#registerForm').ajaxForm(function(data)
                         {  
                                if(data.success)
                               {
                                     alert("Register SUCCESS"+" " + data.returnMessage);
                                     location.href = "/RiXiang_blog/space/list.form";
                               }
                               else
                               {
                                     alert("Register FAIL"+" " + data.returnMessage);
                                }
                          });   
                 });  
               </script>
      </head>
      <body>
       		<div class="container">
            	<div class="container">
        			<h1>Hello Sonne Blog</h1>
    			</div>
    			<nav class="navbar navbar-inverse">
   				  <div class="navbar-header">
    			    <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar-menu" aria-expanded="false">
       				     <span class="sr-only">Toggle navigation</span>
         				 <span class="icon-bar"></span>
            			 <span class="icon-bar"></span>
            			 <span class="icon-bar"></span>
        			</button>
       				 <a class="navbar-brand" href="/RiXiang_blog/article/list.form">主页</a>
    			</div>
            		<div class="navbar-collapse">
                		<ul class="nav navbar-nav">
                    		<li class="active"><a href="#">注册</a></li>
                    		<li><a href="/RiXiang_blog/login/show.form">登录</a></li>
                    		<li><a href ="/RiXiang_blog/game/snake.form">贪吃蛇</a></li>
                    		<li><a href ="/RiXiang_blog/sonne/sonne.form">作者-博客开发记录</a></li>
                		</ul>
            		</div>
        		</nav>
        		
        		   <div id="content" class="row-fluid">
        			   <div id="register" class="col-md-9">
            			   <h4>欢迎来到日向博客，在这里，除了孤独，你还有文字相伴。</h4>
	
						   <form id="registerForm" action="submit.form" method="post">
   							     <div class="form-group">
   						  		   <span class="glyphicon glyphicon-user"></span>
    					            <input type="text" id="username" name="username" placeholder="Enter email">
    				            </div>
  				               <div class="form-group">
      				             <span class="glyphicon glyphicon-lock"></span>
                                  <input type="password" id="password" name="password" placeholder="Password">
                               </div>
  				               <div class="form-group">
      				             <span class="glyphicon glyphicon-lock"></span>
                                  <input type="password" id="repassword" name="repassword" placeholder="Password">
                               </div>
    
                                <button type="submit" class="btn btn-default">Submit</button>
                             </form>
                        </div>
                         <div id="poem" class="col-md-3">
                                 <br/>
                                 <br/>
                                 <br/>
                                 <p>我要告诉他们</p>
                                 <p>精神如何变成骆驼</p>
                                  <p>骆驼如何变成狮子</p>
                                  <p>最后，狮子又为何变成小孩</p>
                                  <p>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp----《查拉图斯特拉如是说》</p>
                        </div>
            </div>
       </div>
      </body>
</html>