package org.appServlet.listeners;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.xml.DOMConfigurator;
import org.appServlet.db.DBConnectionManager;

import java.io.File;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Listner to read servlet context init parameters
 * and create the DBConnectionManager object
 * and set it as attribute to the ServletContext object.
 */
@WebListener
public class AppContextListener implements ServletContextListener  {
	
    public void contextInitialized(ServletContextEvent servletContextEvent) {
    	
    	ServletContext context = servletContextEvent.getServletContext();
    	//initialize DB Connection
    	String dbUrl = context.getInitParameter("dbURL");
    	String dbUser = context.getInitParameter("dbUser");
    	String dbPassword = context.getInitParameter("dbUserPwd");

		//create database connection from init parameters and set it to context
		try {
			DBConnectionManager dbManager;
			dbManager = new DBConnectionManager(dbUrl, dbUser, dbPassword);
			context.setAttribute("DBConnection", dbManager.getConnection());
			System.out.println("Database connection initialized for Application.");
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//initialize log4j
    	String log4jConfig = context.getInitParameter("log4j-config");
    	if(log4jConfig == null){
    		System.err.println("No log4j-config init param, initializing log4j with BasicConfigurator");
			BasicConfigurator.configure();
    	}else {
			String webAppPath = context.getRealPath("/");
			String log4jProp = webAppPath + log4jConfig;
			File log4jConfigFile = new File(log4jProp);
			if (log4jConfigFile.exists()) {
				System.out.println("Initializing log4j with: " + log4jProp);
				DOMConfigurator.configure(log4jProp);
			} else {
				System.err.println(log4jProp + " file not found, initializing log4j with BasicConfigurator");
				BasicConfigurator.configure();
			}
		}
    	System.out.println("log4j configured properly");

		
    }

	public void contextDestroyed(ServletContextEvent servletContextEvent) {

		ServletContext context = servletContextEvent.getServletContext();
		try {
			DBConnectionManager dbManager = (DBConnectionManager) context.getAttribute("DBManager");
			dbManager.closeConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Database connection closed for Application.");

	}

}
