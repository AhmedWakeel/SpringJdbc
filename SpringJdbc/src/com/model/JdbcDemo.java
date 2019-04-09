package com.model;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class JdbcDemo {

	public static void main(String[] args)
	{
		/*JdbcDaoImpl daoImpl = new JdbcDaoImpl();
		Circle circle = daoImpl.getCircle(1);
		System.out.println(circle.getName()+" "+circle.getId());*/
		
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("SprinAop.xml");
		JdbcDaoImpl bean = applicationContext.getBean("jdbcDaoImpl",JdbcDaoImpl.class);
		
//		bean.createTable();
		
		bean.updateTable(new Circle("seven",7));
		
		int circleCount = bean.circleCount();
		System.out.println(circleCount);
		
		System.out.println(bean.getCircleName(1));
		
		Circle circleForId = bean.getCircleForId(2);
		System.out.println(circleForId.getName()+" "+circleForId.getId());
    
		List<Circle> allCircle = bean.getAllCircle();
		System.out.println(allCircle.size());
		
		((AbstractApplicationContext)applicationContext).close();
		
	}
}
	