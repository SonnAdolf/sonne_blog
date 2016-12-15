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
             
     	     <meta name="viewport" content="width=device-width,height=device-height,inital-scale=1.0,maximum-scale=1.0,user-scalable=no;">
             <meta name="apple-mobile-web-app-capable" content="yes">
             <meta name="apple-mobile-web-app-status-bar-style" content="black">
             <meta name="format-detection" content="telephone=no">
             
             
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
                 <div id="back_way">
            	    <a href ="/RiXiang_blog/article/list.form">返回主页</a>
                 </div>
        		   <div id="content" class="row-fluid">
        		             <img src="<%=basePath %>image/cat.jpg" alt/>
        			          <h4>Sign in to SonneBlog</h4>

            			   <h4>日向博客，你的精神家园</h4>
						   <form id="loginForm" action="login.form" method="post">
   							     <div id = "usr_name"  class="form-group">
    					                 用户名 <input type="text" id="username" name="username" placeholder="Username">
    				            </div>
  				               <div id = "passwd"  class="form-group">
                                         密码 <input type="password" id="password" name="password" placeholder="Password">
                               </div>
           					  <textarea  style="display:none" id="pubkey" rows="15" cols="65">${publicKey}</textarea>
                              <div id = "captcha"  class="form-group">
                                          验证码 <input type="text" id="captcha" name="captcha" placeholder="Enter captcha">
                               </div>
    						  <div id = "captcha_img"  class="form-group">
							         <img id="captchaImage"  src="captcha.form"/>
							  </div>
	                                
	                          <div id="submit_btn"  class="form-group">
                                <button id="submitbtn" name="submitbtn" class="btn-primary btn-block">Submit</button>
							  </div>
                           </form>
				    <!--
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
					-->
            </div>

       </div>
       
      </body>
</html>