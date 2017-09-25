package com.niit.onlinebackend.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;

import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.niit.onlinebackend.daoimpl.UserDaoImpl;
import com.niit.onlinebackend.model.User;


@Configuration
@EnableTransactionManagement
@ComponentScan

public class HibernateConfig 
{

	@Autowired 
	SessionFactory sessionFactory;
	
	public DataSource getH2()
	{
		System.out.println("Hibernate initiated.....");
		DriverManagerDataSource dt=new DriverManagerDataSource();
	dt.setDriverClassName("org.h2.Driver");
	dt.setUrl("jdbc:h2:tcp://localhost/~/os");
	dt.setUsername("sa");
	dt.setPassword("");
	System.out.println("Connection established");
	return dt;
	}
	
	private Properties getHiberProps()
	{
		
	Properties p=new Properties();
	
	p.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
	p.put("hibernate.show.sql", "true");		
	p.put("hibernate.hbm2ddl.auto", "update");
	return p;
	}
	
	@Autowired
	@Bean(name="UserDaoImpl")
	public UserDaoImpl getUserData(SessionFactory sessionFac)
	{
		return new UserDaoImpl(sessionFac);
	}
	@Autowired
	@Bean(name="SessionFactory")
	public SessionFactory getSession(DataSource datasource)
	{
		
		LocalSessionFactoryBuilder lsfb =new LocalSessionFactoryBuilder(datasource);
		
		lsfb.addProperties(getHiberProps());
		lsfb.addAnnotatedClass(User.class);
		return lsfb.buildSessionFactory();
		
	}
	@Autowired
	@Bean(name="TransactionManager")
	public  HibernateTransactionManager getTransation(SessionFactory SessionFactory)
	{
		
		HibernateTransactionManager tm=new HibernateTransactionManager(SessionFactory);
		
		return tm;
	}
	
	
	
}
