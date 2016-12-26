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
             <link type="text/css" rel="stylesheet" href="<%=basePath %>css/register.css" media="all" />
              <script type="text/javascript" src="<%=basePath %>Jquery/jquery-2.2.3.min.js"></script>
              <script type="text/javascript" src="<%=basePath %>Jquery/jquery-form.js"></script>  
              <script type="text/javascript" src="<%=basePath %>bootstrap-3.3.0-dist/js/bootstrap.min.js"></script> 
              <script type="text/javascript" src="<%=basePath %>js/jsencrypt.min.js"></script>               
              <script type="text/javascript"> 
  			  $(document).ready(function() { 
				  $('#registerForm').ajaxForm({ 
			             dataType:      'json',
						 beforeSubmit:  validate,   
						 success:       successFunc
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
			         	   alert("请再次输入密码！！ ԅ(¯﹃¯ԅ) （¯﹃¯）");
			               return false;
			        }
			        if (formData[1].value != formData[2].value) {
			               alert("两次密码输入不一致!!(,,•́ . •̀,,) (๑•́ ₃•̀๑) (๑•́ ₃ •̀),,Ծ‸Ծ,,");
			               return false;
			        }
			        if (formData[1].value.length < 6) {
			               alert("密码长度至少6位!!  (:3[▓▓] (:3[▓▓▓▓▓▓▓▓▓] (¦3[▓▓]");
			               return false;
			        }
			        // 加密密码
			        var password = formData[1].value;
                    var repassword = formData[2].value; 
			        var encrypt = new JSEncrypt();
                    keyValue=$("#pubkey").val();
                    encrypt.setPublicKey(keyValue); 
                    var password = encrypt.encrypt(password);
                    var repassword = encrypt.encrypt(repassword); 
                    formData[1].value = password;  
                    formData[2].value = repassword;
			        var queryString = $.param(formData);
                    return true; 
				}
				function successFunc(data) {
					if (data.success) {
				        alert("注册成功："+" " + data.returnMessage);
						location.href = "/RiXiang_blog/space/list.form"; 
					}
					else {
				      	$("#password").val("");
				      	$("#repassword").val("");
						alert("注册失败："+" " + data.returnMessage);
					}
				} 
               </script>
      </head>
      <body>
       		<div class="container">
       		
       		
       		    <!-- 
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
                    		<li><a href ="/RiXiang_blog/sonne/blog.form">作者-博客开发记录</a></li>
                		</ul>
            		</div>
        		</nav>
        		-->
        	      <div id="back_way">
            	    <a href ="/RiXiang_blog/article/list.form">返回主页</a>
                 </div>
        		
        		   <div id="content" class="row-fluid">
        		          <img src="<%=basePath %>image/bike.png" alt/>
        			       <h4>Sign up to SonneBlog</h4>
            			   <h4>欢迎来到日向博客，在这里，除了孤独，你还有文字相伴。</h4>
	
						   <form id="registerForm" action="submit.form" method="post">
   							    <div class="form-group">
    					                                   用户名:<input type="text" id="username" name="username" placeholder="">
    				            </div>
  				                <div class="form-group">
                                                                                                  密码:<input type="password" id="password" name="password" placeholder="Password">
                                </div>
  				                <div class="form-group">
                                                                                                  确认密码:<input type="password" id="repassword" name="repassword" placeholder="Password">
                                </div>
                                <textarea  style="display:none" id="pubkey" rows="15" cols="65">${publicKey}</textarea>
                                <button type="submit" id="submitbtn" class="btn btn-default">注册</button>
                           </form>
                   </div>
                        <!--
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
                        -->
            </div>
       </div>
      </body>
</html>