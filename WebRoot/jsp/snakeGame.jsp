<%@page pageEncoding="utf-8" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String imgPath = basePath + "image/";
%>
<html>
<head>
    <title>日向贪吃蛇</title>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<style>
    body{
        font-size:9pt;
    }
    table{
        border-collapse: collapse;
        border:solid #333 1px;
    }
    td{
        height: 20px;
        width: 20px;
        font-size: 0px;
    }
    .filled{
        background-color:blue;
    }
</style>
<script>
    function $(id){return document.getElementById(id);}
	var Snake = {
        tbl: null,
        /**
        * body: 蛇身，数组放蛇的每一节，
        * 数据结构{x:x0, y:y0, color:color0},
        * x,y表示坐标,color表示颜色
        **/
        body: [],
        //当前移动的方向,取值0,1,2,3, 分别表示向上,右,下,左, 按键盘方向键可以改变它
        direction: 0,
        //定时器
        timer: null,
        //速度
        speed: 250,
        //是否已经暂停
        paused: true,
        //行数
        rowCount: 25,
        //列数
        colCount: 25,
        init: function(){
            var colors = ['red','orange','yellow','green','blue','purple','#ccc'];
            this.tbl = $("main");
            var x = 0;
            var y = 0;
            var colorIndex = 0;
            //产生初始移动方向
            this.direction = Math.floor(Math.random()*4);
            //构造table
            for(var row=0;row<this.rowCount;row++){
                var tr=this.tbl.insertRow(-1);
                for(var col=0;col<this.colCount;col++) {
                    var td=tr.insertCell(-1);
                }
            }
            //产生猎物
            x = Math.floor(Math.random()*this.colCount);
            y = Math.floor(Math.random()*this.rowCount);
            colorIndex = Math.floor(Math.random()*7);
            if(!this.isCellFilled(x,y)){
                 this.tbl.rows[y].cells[x].style.backgroundColor = colors[colorIndex];
            }
            //产生蛇头
            while(true){
                x = Math.floor(Math.random()*this.colCount);
                y = Math.floor(Math.random()*this.rowCount);
                if(!this.isCellFilled(x,y)){
                    this.tbl.rows[y].cells[x].style.backgroundColor = "black";
                    this.body.push({x:x,y:y,color:'black'});
                    break;
                }
            }
            this.paused = true;
            //添加键盘事件
            document.onkeydown= function(e){
                if (!e)e=window.event;
                switch(e.keyCode | e.which | e.charCode){
                  case 13: {
                    if(Snake.paused){
                      Snake.move();
                      Snake.paused = false;
                    }
                    else{
                       //如果没有暂停，则停止移动
                      Snake.pause();
                      Snake.paused = true;
                    }
                    break;
                  }
                    case 37:{//left
                        //阻止蛇倒退走
                        if(Snake.direction==1){
                            break;
                        }
                        Snake.direction = 3;
                        break;
                    }
                    case 38:{//up
                       //快捷键在这里起作用
                      if(event.ctrlKey){
                         Snake.speedUp(-20);
                        break;
                      }
                        if(Snake.direction==2){//阻止蛇倒退走
                            break;
                        }
                        Snake.direction = 0;
                        break;
                    }
                    case 39:{//right
                        if(Snake.direction==3){//阻止蛇倒退走
                            break;
                        }
                        Snake.direction = 1;
                        break;
                    }
                    case 40:{//down
                      if(event.ctrlKey){
                         Snake.speedUp(20);
                        break;
                      }
                        if(Snake.direction==0){//阻止蛇倒退走
                            break;
                        }
                        Snake.direction = 2;
                        break;
                    }
                }
            }
        },
        //检查将要移动到的下一步是什么
        checkNextStep: function(){
            var point = this.getNextPos();
            var x = point.x;
            var y = point.y;
            if(x<0||x>=this.colCount||y<0||y>=this.rowCount){
                return -1;//触边界，游戏结束
            }
            for(var i=0; i<this.body.length; i++){
                if(this.body[i].x==x&&this.body[i].y==y){
                    return -1;//碰到自己的身体,游戏结束
                }
            }
            if(this.isCellFilled(x,y)){
                return 1;//有东西
            }
            return 0;//空地
        },
        //擦除蛇身
        erase: function(){
            for(var i=0; i<this.body.length; i++){
                this.eraseDot(this.body[i].x, this.body[i].y);
            }
        },
        //绘制蛇身
        paint: function(){
            for(var i=0; i<this.body.length; i++){
                this.paintDot(this.body[i].x, this.body[i].y,'black');
            }
        },
        //擦除一节
        eraseDot: function(x,y){
            this.tbl.rows[y].cells[x].style.backgroundColor = "";
        },
        paintDot: function(x,y,color){
            this.tbl.rows[y].cells[x].style.backgroundColor = color;
        },
        //移动
        move: function(){
            this.timer = setInterval(function(){
                Snake.erase();
                Snake.moveOneStep();
                Snake.paint();
            }, this.speed);
        },
        //移动一节身体
        moveOneStep: function(){
            if(this.checkNextStep()==-1){
                clearInterval(this.timer);
                alert("Game over!/nPress Restart to continue.");
                return;
            }
            if(this.checkNextStep()==1){
                var _point = this.getNextPos();
                var _x = _point.x;
                var _y = _point.y;
                this.body.unshift({x:_x,y:_y,color:"black"});
                //因为吃了一个食物，所以再产生一个食物
                this.generateDood();
                return;
            }
            var point = this.getNextPos();
            //蛇尾减一节， 蛇尾加一节，呈现蛇前进的效果
            this.body.pop();
            this.body.unshift({x:point.x,y:point.y,color:"black"});
            //window.status = this.toString();
        },
        getNextPos: function(){
            var x = this.body[0].x;
            var y = this.body[0].y;
            //向上
            if(this.direction==0){
                y--;
            }
            //向右
            else if(this.direction==1){
                x++;
            }
            //向下
            else if(this.direction==2){
                y++;
            }
            //向左
            else{
                x--;
            }
            //返回一个坐标
            return {x:x,y:y};
        },
        //检查一个坐标点有没有被填充
        isCellFilled: function(x,y){
            if(this.tbl.rows[y].cells[x].style.backgroundColor == ""){
                return false;
            }
            return true;
        },
        //重新开始
        restart: function(){
            if(this.timer){
                clearInterval(this.timer);
            }
            for(var i=0; i<this.rowCount;i++){
              this.tbl.deleteRow(0);
            }
            this.body = [];
            this.init();
            this.speed = 250;
        },
        //产生食物。
        generateDood: function(){
            var colors = ['red','orange','yellow','green','blue','purple','#ccc'];
			var x = Math.floor(Math.random()*this.colCount);
            var y = Math.floor(Math.random()*this.rowCount);
            var colorIndex = Math.floor(Math.random()*7);
            if(!this.isCellFilled(x,y)){
                this.tbl.rows[y].cells[x].style.backgroundColor = colors[colorIndex];
            }
        }
	};
</script>
</head>

<body onload="Snake.init();">
<div id = 'main_title'>日向贪吃蛇</div>
<table id="main" border="1" cellspacing="0" cellpadding="0"></table>
<input type="button" id="btn" value="开始/暂停" />
<input type="button" id="reset" value="重新开始" /><br />
<!-- <div id="music" align="right" style="margin-top:0px"> -->
    <audio controls="controls" autoplay="autoplay" height="100" width="100">
  		  <source src="<%=basePath %>music/breaking_bad.mp3" type="audio/mp3" />
          <source src="<%=basePath %>music/breaking_bad.ogg" type="audio/ogg" />
          <embed height="100" width="100" src="<%=basePath %>music/breaking_bad.mp3" />
     </audio>
<!-- </div>  -->
<script>
  $('btn').onclick = function(){
    if(Snake.paused){
      Snake.move();
      Snake.paused = false;
    }
    else{
      Snake.pause();
      Snake.paused = true;
    }
  };
  $("reset").onclick = function(){
    Snake.restart();
    this.blur();
  };
  $("upSpeed").onclick = function(){
    Snake.speedUp(-20);
  };
  $("downSpeed").onclick = function(){
    Snake.speedUp(20);
  };
</script>
</body>
</html>