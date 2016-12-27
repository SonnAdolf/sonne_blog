#!/bin/bash
# This shell script is used to back up my blog's files
# ------------by sonne 2016-12-27--------------------

var $DATE
DATE=$(date +%Y%m%d)
mkdir /root/.back_up/$DATE

zip -r /root/.back_up/$DATE/article.zip /root/article/*
zip -r /root/.back_up/$DATE/summary.zip /root/summary/*
zip -r /root/.back_up/$DATE/h_pics.zip /root/apache-tomcat-8.5.9/webapps/RiXiang_blog/h_pics/*
zip -r /root/.back_up/$DATE/article_pics.zip /root/apache-tomcat-8.5.9/webapps/RiXiang_blog/article_pics/*
