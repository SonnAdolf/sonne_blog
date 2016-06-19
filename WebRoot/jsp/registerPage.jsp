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
                         $('#registerForm').ajaxForm(function(data)
                         {  
                                if(data.success)
                               {
                                     alert("Register SUCCESS"+" " + data.returnMessage);
                               }
                               else
                               {
                                     alert("Register FAIL"+" " + data.returnMessage);
                                }
                          });   
                 });  

                          /*
                            $.ajax({    
                                   url:"submit.form",
                                   type:"post",
                                   contentType: "application/json",
                                   dataType:"json", 
                                   success:function(data)
                                   {
                                         alert("成功");
                                   },
                                   error:function(data)
                                   {
                                         alert("fuck jquery");
                                   }
                               });
                     */

               </script>
      </head>
      <body>
             <div id="scene"> 
                  <img src="<%=imgPath%>mainPageBanner.png" ALT=""/> 
             </div>
              <div id = "register_page">
                     <form id="registerForm" action="submit.form" method="post">
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
			                             <th>repassword</th>
				                         <td>
					                          <input type="password" id="repassword" name="repassword" class="text" maxlength="20" />
				                        </td>
		                        	</tr>
		                        	<tr>
				                         <th>&nbsp;</th>
				                          <td>
					                           <input type="submit" id="submitButton" class="button" value="SUBMIT" />
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
                              <li><a href ="">Register</a></li>
                              <li><a href ="/RiXiang_blog/article/list.form">Blog List</a></li>
                          </ul>
                    </div>
              </div>
      </body>
</html>