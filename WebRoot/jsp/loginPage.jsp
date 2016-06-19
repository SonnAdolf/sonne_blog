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
             <link type="text/css" rel="stylesheet" href="<%=basePath %>css/main.css" media="all" />
              <script type="text/javascript" src="<%=basePath %>Jquery/jquery-2.2.3.min.js"></script>
              <script type="text/javascript" src="<%=basePath %>Jquery/jquery-form.js"></script>              
              <script type="text/javascript">
               $().ready(function()
               {     
                         $('#loginForm').ajaxForm(function(data)
                         {  
                               if(data.success)
                               {
                                     alert("Login SUCCESS"+" " + data.returnMessage);
                                     location.href = "/RiXiang_blog/space/list.form"; 
                               }
                               else
                               {
                                     alert("Login FAIL"+" " + data.returnMessage);
                                }
                          });  
                          
                         // 更换验证码
	                     $('#captchaImage').click(function() 
	                     {
		                     $('#captchaImage').attr("src", "captcha.form?timestamp=" + (new Date()).valueOf());
	                     }); 
                 });  
               </script>
      </head>
      <body>
             <div id="scene"> 
                  <img src="<%=imgPath%>mainPageBanner.png" ALT=""/> 
             </div>
              <div id = "login_page">
                     <form id="loginForm" action="login.form" method="post">
                            Welcome to 日向blog！
                            <table>
                                   <tr>
				                        <th>username</th>
				                        <td>
				                             <input type="text" name="username" class="text" maxlength="20" />
				                        </td>
			                       </tr>
			                        <tr>
			                             <th>password</th>
				                         <td>
					                          <input type="password" id="password" name="password" class="text" maxlength="20" />
				                        </td>
		                        	</tr>
		                        	 <tr>
			                              <th>captcha</th>
			                              <td>
			                                    <input type="text" id="captcha" name="captcha" class="text" maxlength="10" />
			                                    <img id="captchaImage"  src="captcha.form"/>
			                              </td>
			                       </tr>
		                        	<tr>
				                         <th>&nbsp;</th>
				                          <td>
					                           <input type="submit" id="loginButton" class="button" value="LOGIN" />
				                         </td>
			                       </tr>
                            </table>
                     </form>       
			  </div>

              <div id="col_right">
                    <div id="menu">
                          <h2>主页导航</h2>
                          <ul>
                              <li><a href ="/RiXiang_blog/login/show.form">Login</a></li>
                              <li><a href ="/RiXiang_blog/register/show.form">Register</a></li>
                              <li><a href ="/RiXiang_blog/article/list.form">Blog List</a></li>
                          </ul>
                    </div>
              </div>
      </body>
</html>