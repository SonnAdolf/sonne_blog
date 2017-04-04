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
             <link rel="shortcut icon" type="image/x-icon" href="http://118.89.29.170/RiXiang_blog/favicon.ico">
             <link type="text/css" rel="stylesheet" href="<%=basePath %>css/writing.css" media="all" />
             <link rel="stylesheet" type="text/css" href="<%=basePath %>wangEditor/dist/css/wangEditor.min.css"/>
             <link type="text/css" rel="stylesheet" href="<%=basePath %>css/xcConfirm/xcConfirm.css" media="all"/>
      </head>
      <body>
		    <div id="header">
		      	 <div id="navigator">
		      		<ul id="navList">
		                 <li><font>日   向  博  客&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp</font></li>
		                 <c:if test="${empty userName}"><li><a href ="/RiXiang_blog/login/show.form">登录</a></li></c:if>
		                 <c:if test="${empty userName}"><li><a href ="/RiXiang_blog/register/show.form">注册</a></li></c:if>
		                 <li><a href ="/RiXiang_blog/article/list.form?currentPage=1">主页</a></li>
		         		 <c:if test="${!empty userName}"><li><a href ="/RiXiang_blog/passwd/show.form">修改密码</a></li></c:if>
		                 <c:if test="${!empty userName}"><li><a href ="/RiXiang_blog/space/list.form">文章管理</a></li></c:if>
		                 <c:if test="${!empty userName}">
		                      <li>
		                             <a href ="/RiXiang_blog/mine/show.form">
		                                  ${userName}                                                             
		                                  <c:if test="${!empty has_new_msg}">
		                 	                   <span id="new_msg_txt">【新消息】</span>
		                 	             </c:if>
		                            </a>
		                 	 </li>
		                </c:if>
		                 <c:if test="${!empty userName}"><li><a href ="/RiXiang_blog/article/writeArticlePage.form">写博客</a></li></c:if>
		                 <li><a href ="/RiXiang_blog/game/snake.form">游戏</a></li>
		                 <li><a href ="/RiXiang_blog/sonne/blog.form">日向技术</a></li>
		                 <c:if test="${!empty userName}"><li><a href="javascript:void(0)" onclick="logout()">退出</a></li></c:if>
		           	</ul>
		         </div>
		     </div>
              <div id = "article_list">
                   <br>
                   <form id="articleForm" action="edit.form" method="post">
                               <label>标题：</label>
                              <input type="text" name="title" value="${article.title}" style="height:25px;width:150px;"/><br>
                              <br>
                               
  				               <div id="editor-container" class="container">
                                   <textarea id="editor-trigger" name="articleContent" style="display:none;">
                                            ${article.content}
                                   </textarea> 
                               </div>
                                &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
                               <input type="text" style="display:none" name="articleAddr" value="${article.articleAddr}">
                               <input type="text" style="display:none" name="summaryAddr" value="${article.summaryAddr}">
                               <input type="text" style="display:none" name="id" value="${article.id}">
                               <input type="text" style="display:none" name="authorName" value="${article.authorName}">
                               <input name="提交" type="submit" class="button" style="height:30px;width:100px;background:black;color:white" value="写完了（￣ c￣）y" />
                  </form>
			  </div>


              <script type="text/javascript" src="<%=basePath %>Jquery/jquery-2.2.3.min.js"></script>
              <script type="text/javascript" src="<%=basePath %>Jquery/jquery-form.js"></script>   
             <script type="text/javascript" src="<%=basePath %>wangEditor/dist/js/wangEditor.js"></script>
             <script type="text/javascript" src="<%=basePath %>Jquery/xcConfirm/js/xcConfirm.js"></script> 
             <script type="text/javascript">
 			  $(document).ready(function() { 
				  $('#articleForm').ajaxForm({ 
			             dataType:      'json',
						 beforeSubmit:  validate,   
						 success:       successFunc
			   	  }); 

       	          var editor = new wangEditor('editor-trigger');

                   // 上传图片
                  editor.config.uploadImgUrl = '/RiXiang_blog/article/article_imgs.form';
                  editor.config.uploadParams = {
                      // token1: 'abcde',
                      // token2: '12345'
                  };
                  editor.config.uploadHeaders = {
                      // 'Accept' : 'text/x-json'
                  }
                  // editor.config.uploadImgFileName = 'myFileName';

                   // 隐藏网络图片
                    editor.config.hideLinkImg = true;

                   // 表情显示项
                   editor.config.emotionsShow = 'value';
                   editor.config.emotions = {
                      'default': {
                          title: '默认',
                          data: './emotions.data'
                       },
                      'weibo': {
                          title: '微博表情',
                      data: [
                         {
                             icon: 'http://img.t.sinajs.cn/t35/style/images/common/face/ext/normal/7a/shenshou_thumb.gif',
                             value: '[草泥马]'    
                          },
                         {
                              icon: 'http://img.t.sinajs.cn/t35/style/images/common/face/ext/normal/60/horse2_thumb.gif',
                              value: '[神马]'    
                         },
                         {
                              icon: 'http://img.t.sinajs.cn/t35/style/images/common/face/ext/normal/bc/fuyun_thumb.gif',
                              value: '[浮云]'    
                         },
                         {
                               icon: 'http://img.t.sinajs.cn/t35/style/images/common/face/ext/normal/c9/geili_thumb.gif',
                               value: '[给力]'    
                         },
                         {
                               icon: 'http://img.t.sinajs.cn/t35/style/images/common/face/ext/normal/f2/wg_thumb.gif',
                               value: '[围观]'    
                          },
                         {
                                icon: 'http://img.t.sinajs.cn/t35/style/images/common/face/ext/normal/70/vw_thumb.gif',
                                value: '[威武]'
                         }
                       ]
                      }
                   };

                  // onchange 事件
                  editor.onchange = function () {
                       console.log(this.$txt.html());
                   };
                   editor.create();
		       });
		       
		       
			   function validate(formData, jqForm, options) {
			       for(var i=0; i < formData.length; i++) {
			        	if(!formData[i].value) {
			        	    if(i==0) {
			        	        window.wxc.xcConfirm("题目不可为空", window.wxc.xcConfirm.typeEnum.warning);
			        	    }
			        	    if (i==1) {
			        	        window.wxc.xcConfirm("内容不可为空", window.wxc.xcConfirm.typeEnum.warning);
			        	    }
			        		return false;
			        	}
			        } 
			        
			        var queryString = $.param(formData);
                    return true; 
				}
				function successFunc(data) {
					if (data.success) {
					    //var txt=  "修改成功";
					    //window.wxc.xcConfirm(txt, window.wxc.xcConfirm.typeEnum.success);
						location.href = "/RiXiang_blog/space/list.form";
					}
					else {
					    window.wxc.xcConfirm(data.info, window.wxc.xcConfirm.typeEnum.warning);
					}
				}
          </script>
      </body>
</html>