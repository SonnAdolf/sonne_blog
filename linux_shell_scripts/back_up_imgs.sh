#!/bin/bash
#This shell script is used to back up myblog's pictures when reboot the tomcat
#-----------------by sonne 2017-01-03------------------

if [ -d "/root/.back_up/imgs/h_pics" ]
   then
      echo 'h_pics path exits'
else
   mkdir -p /root/.back_up/imgs/h_pics
fi
if [ -d "/root/.back_up/imgs/article_pics" ]
   then
      echo 'article_pics path exits'
else
   mkdir -p /root/.back_up/imgs/article_pics
fi
cp -Rf /root/apache-tomcat-8.5.9/webapps/RiXiang_blog/h_pics/* /root/.back_up/imgs/h_pics/
cp -Rf /root/apache-tomcat-8.5.9/webapps/RiXiang_blog/article_pics/* /root/.back_up/imgs/article_pics/
