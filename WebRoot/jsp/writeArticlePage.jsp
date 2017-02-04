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
             <link type="text/css" rel="stylesheet" href="<%=basePath %>css/fixed_background.css" media="all" />
             <link rel="stylesheet" type="text/css" href="<%=basePath %>wangEditor/dist/css/wangEditor.min.css"/>
             <link type="text/css" rel="stylesheet" href="<%=basePath %>css/xcConfirm/xcConfirm.css" media="all"/>
      </head>
      <body>
             <div id="scene"> 
                  <img src="<%=imgPath%>mainPageBanner.png" ALT=""/> 
             </div>
              <div id = "article_list">
                   <br>
                   <form id="articleForm" action="writeArticle.form" method="post">
                               <label>标题：</label>
                              <input type="text" name="title" style="height:25px;width:150px;"/><br>
                               <br>
  				               <div id="editor-container" class="container">
                                   <textarea id="editor-trigger" name="articleContent">
                                   </textarea> 
                               </div>
                               &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
                               <input name="提交" type="submit" class="button" style="height:30px;width:100px;background:black;color:white" value="写完了（￣ c￣）y" />
                  </form>
			  </div>

              <div id="col_right">
                    <div id="menu">
                          <h2>主页导航</h2>
                          <ul>
                              <li><a href ="/RiXiang_blog/article/list.form">主页</a></li>
                              <li><c:if test="${!empty userName}"><a href ="/RiXiang_blog/space/list.form">文章管理</a></c:if></li>
                              <li><a href ="/RiXiang_blog/game/snake.form">游戏</a></li>
                              <li><a href ="/RiXiang_blog/sonne/blog.form">日向技术</a></li>
                          </ul>
                    </div>
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
			        	        window.wxc.xcConfirm("标题不能为空", window.wxc.xcConfirm.typeEnum.warning);
			        	    }
			        	    if (i==1) {
			        	        window.wxc.xcConfirm("内容不能为空", window.wxc.xcConfirm.typeEnum.warning);
			        	    }
			        		return false;
			        	}
			        } 
			        
			        var queryString = $.param(formData);
                    return true; 
				}
				function successFunc(data) {
					if (data.success) {
						location.href = "/RiXiang_blog/space/list.form";
					}
					else {
						window.wxc.xcConfirm(data.info, window.wxc.xcConfirm.typeEnum.warning);
					}
				}
               </script>
      </body>
</html>