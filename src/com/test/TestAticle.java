package com.test;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author 无名
 * @date 2016.04.21
 * @description:文章测试类
 */
public class TestAticle 
{
	@Test
	public void testArticleFun()
	{
        String conf = "spring-common.xml";
        ApplicationContext ac = new ClassPathXmlApplicationContext(conf);
        System.out.println(ac);
	}
}
