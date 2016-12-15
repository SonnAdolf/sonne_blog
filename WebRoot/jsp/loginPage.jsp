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
             <link type="text/css" rel="stylesheet" href="<%=basePath %>css/login.css" media="all" />
             <script type="text/javascript" src="<%=basePath %>Jquery/jquery-1.3.1.js"></script>
              <script type="text/javascript" src="<%=basePath %>Jquery/jquery.form.js"></script>   
             <script type="text/javascript" src="<%=basePath %>bootstrap-3.3.0-dist/js/bootstrap.min.js"></script>
             <script type="text/javascript" src="<%=basePath %>js/jsencrypt.min.js"></script>              
             <script type="text/javascript">
  			  $(document).ready(function() { 
				  $('#loginForm').ajaxForm({ 
			             dataType:      'json',
						 beforeSubmit:  validate,   
						 success:       successFunc
			   	  });
                     $("#submitbtn").click(function() {  
                         var encrypt = new JSEncrypt();
                         keyValue=$("#pubkey").val();
                         encrypt.setPublicKey(keyValue);
                         var password = encrypt.encrypt($("#password").val());  
                         $("#password").val(password);  
                         document.loginForm.submit();  
                     });  
                 // 更换验证码
	              $('#captchaImage').click(function() {
		              $('#captchaImage').attr("src", "captcha.form?timestamp=" + (new Date()).valueOf());
	              }); 
		       });
			   function validate(formData, jqForm, options) {
			        if (!formData[0].value) {
			        	   alert("用户名不能为空！！(;¬_¬) ( ´◔ ‸◔`) (눈_눈) ( ∙̆ .̯ ∙̆ ) (;￢д￢) (“▔□▔)");
			        	   return false;
			        }
			        if (!formData[1].value) {
			               alert("密码不能为空！！π__π T.T ε(┬┬＿┬┬)3 ╥﹏╥ ┬＿┬ (╥╯^╰╥)");
			               return false;
			        }
			        if (!formData[2].value) {
			               alert("验证码不能为空！！_(:з」∠)_ _(:qゝ∠)_ _(?ω?｣ ∠)_");
			               return false;
			        }
			        var queryString = $.param(formData);
                    return true; 
				}
				function successFunc(data) {
					if (data.success) {
				        alert("登录成功："+" " + data.returnMessage);
						location.href = "/RiXiang_blog/space/list.form"; 
					}
					else {
					    $("#password").val("");
						alert("登录失败："+" " + data.returnMessage);
					}
				}
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
                    		<li class="active"><a href="#">登录</a></li>
                    		<li><a href="/RiXiang_blog/register/show.form">注册</a></li>
                    		<li><a href ="/RiXiang_blog/game/snake.form">贪吃蛇</a></li>
                    		<li><a href ="/RiXiang_blog/sonne/blog.form">作者-博客开发记录</a></li>
                		</ul>
            		</div>
        		</nav>
        		
        		   <div id="content" class="row-fluid">
        			   <div id="login" class="col-md-9">
            			   <h4>日向博客，你的精神家园</h4>
	
						   <form id="loginForm" action="login.form" method="post">
   							     <div class="form-group">
   						  		   <span class="glyphicon glyphicon-user"></span>
    					            <input type="text" id="username" name="username" placeholder="Enter email">
    				            </div>
  				               <div class="form-group">
      				             <span class="glyphicon glyphicon-lock"></span>
                                  <input type="password" id="password" name="password" placeholder="Password">
                               </div>
           					  <textarea  style="display:none" id="pubkey" rows="15" cols="65">${publicKey}</textarea>
                              <div class="form-group">
                                 <span class="glyphicon glyphicon-check"></span>
                                   <input type="text" id="captcha" name="captcha" placeholder="Enter captcha">
                                  <img id="captchaImage"  src="captcha.form"/>
                               </div>
    
                                <button id="submitbtn" name="submitbtn" class="btn btn-default">Submit</button>
                             </form>
                        </div>
                         <div id="poem" class="col-md-3">
                                 <br/>
                                 <br/>
                                 <br/>
                                 <p> 我们生活在漫漫寒夜 </p>
                                 <p>人生好似长途旅行</p>
                                 <p>仰望天空寻找方向</p>
                                 <p>天际却无引路的明星</p>
                                 <p>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp----《茫茫黑夜漫游》</p>
                        </div>
            </div>
       </div>
      </body>
</html>