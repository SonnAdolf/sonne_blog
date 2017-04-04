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
             <title>日向博客</title>
             <meta http-equiv="X-UA-Compatible" content="IE=edge">
             <meta name="viewport" content="width=device-width, initial-scale=1">
             <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
             <link rel="shortcut icon" type="image/x-icon" href="http://118.89.29.170/RiXiang_blog/favicon.ico">
             <link rel="stylesheet" href="<%=basePath %>bootstrap-3.3.0-dist/dist/css/bootstrap.min.css"/> 
             <link type="text/css" rel="stylesheet" href="<%=basePath %>css/passwd.css" media="all" />
             <link type="text/css" rel="stylesheet" href="<%=basePath %>css/toastr.min.css" media="all" />
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
                    		<li class="active"><a href="#">修改密码</a></li>
                    		<li><a href ="/RiXiang_blog/space/list.form">文章管理</a></li>
                    		<li><a href ="/RiXiang_blog/game/snake.form">游戏</a></li>
                    		<li><a href ="/RiXiang_blog/sonne/blog.form">日向技术</a></li>
                		</ul>
            		</div>
        		</nav>
        		
        		   <div id="content">
        			   <div id="passwd" class="col-xs-12 col-md-9">
            			   <h4>日向博客，你的精神家园</h4>
	
						   <form id="passwdForm" class="form-horizontal" action="change.form" method="post">
  				               <div class="form-group">
      				             <!--  <span class="glyphicon glyphicon-lock"></span> -->
                                      <label class="col-xs-4 col-md-5 control-label">旧密码：</label>
                                      <div class="col-xs-8 col-md-4">
                                           <input type="password" id="password" name="password" placeholder="旧密码">
                                      </div>
                               </div>
  				               <div class="form-group">
                                      <label class="col-xs-4 col-md-5 control-label"> 新密码：</label>
                                      <div class="col-xs-8 col-md-4">
                                            <input type="password" id="newPassword" name="newPassword" placeholder="新密码">
                                      </div>
                               </div>
  				               <div class="form-group">
                                     <label class="col-xs-4 col-md-5 control-label">重新输入</label>
                                     <div class="col-xs-8 col-md-4">
                                          <input type="password" id="rePassword" name="rePassword" placeholder="确认输入">
                                     </div>
                               </div>      
                                <textarea  style="display:none" id="pubkey" rows="15" cols="65">${publicKey}</textarea>
                                <button type="submit" class="btn btn-primary">提交</button>
                             </form>
                        </div>
                         <div id="poem" class="col-md-3">
                                 <br/>
                                 <br/>
                                 <br/>
                                 <p>  千万不要忘记： </p>
                                 <p> 我们飞翔得越高</p>
                                  <p>我们在那些不能飞翔的人眼中的形象越是渺小</p>
                                  <p>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp----尼采</p>
                        </div>
            </div>
       </div>
       
       
             <script type="text/javascript" src="<%=basePath %>Jquery/jquery-2.2.3.min.js"></script>
             <script type="text/javascript" src="<%=basePath %>Jquery/jquery-form.js"></script>
             <script type="text/javascript" src="<%=basePath %>bootstrap-3.3.0-dist/js/bootstrap.min.js"></script>    
             <script type="text/javascript" src="<%=basePath %>js/jsencrypt.min.js"></script> 
             <script type="text/javascript" src="<%=basePath %>Jquery/toastr.min.js"></script>         
             <script type="text/javascript">
   			  $(document).ready(function() { 
   			 	 toastr.options = {  
        			positionClass: "toast-bottom-full-width"
   			  	 };  
				  $('#passwdForm').ajaxForm({ 
			             dataType:      'json',
						 beforeSubmit:  validate,   
						 success:       successFunc
			   	  });
		       });
			   function validate(formData, jqForm, options) {
			        if (!formData[0].value) {
			        	   toastr.warning("旧密码不能为空(;¬_¬) ( ´◔ ‸◔`) (눈_눈) ( ∙̆ .̯ ∙̆ ) (;￢д￢) (“▔□▔)。");
			        	   return false;
			        }
			        if (!formData[1].value) {
			       	       toastr.warning("新密码不能为空！！π__π T.T ε(┬┬＿┬┬)3 ╥﹏╥ ┬＿┬ (╥╯^╰╥)");
			               return false;
			        }
			        if (!formData[2].value) {
			               toastr.warning("请再次输入密码！！ (╯#-_-)╯~~~~~~~~~~~~~~~~~╧═╧ ");
			               return false;
			        }
			        if (formData[1].value != formData[2].value) {
			               toastr.warning("两次密码输入不一致!!(,,•́ . •̀,,) (๑•́ ₃•̀๑) (๑•́ ₃ •̀),,Ծ‸Ծ,,");
			               return false;
			        }
			        if (formData[1].value.length < 6) {
			               toastr.warning("密码长度至少6位!!  (:3[▓▓] (:3[▓▓▓▓▓▓▓▓▓] (¦3[▓▓]");
			               return false;
			        }
			        // 加密密码
			        var oldPassword = formData[0].value;
                    var newPassword = formData[1].value; 
                    var rePassword = formData[2].value; 
			        var encrypt = new JSEncrypt();
                    keyValue=$("#pubkey").val();
                    encrypt.setPublicKey(keyValue); 
                    var oldPassword = encrypt.encrypt(oldPassword);
                    var newPassword = encrypt.encrypt(newPassword); 
                    var rePassword = encrypt.encrypt(rePassword); 
                    formData[0].value = oldPassword;  
                    formData[1].value = newPassword;
                    formData[2].value = rePassword;
			        var queryString = $.param(formData);
                    return true; 
				}
				function successFunc(data) {
					if (data.success) {
                         toastr.success("修改密码成功"+" " + data.returnMessage);
                         location.href = "/RiXiang_blog/space/list.form"; 
					}
					else {
				      	$("#oldPassword").val("");
				      	$("#newPassword").val("");
				      	$("#rePassword").val("");
				      	toastr.warning("修改密码失败"+" " + data.returnMessage);
					}
				} 
               </script>    		
      </body>
</html>