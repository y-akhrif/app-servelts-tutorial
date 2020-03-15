package org.appServlet.listeners;

import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.annotation.WebListener;

/**
 * A simple implementation listener to log the event when attribute is added,
 *  removed or replaced in servlet context.
 */
@WebListener
public class AppContextAttributeListener implements ServletContextAttributeListener {

    public void attributeAdded(ServletContextAttributeEvent event) {
        System.out.println("ServletContext attribute added::{"+event.getName()+","+event.getValue()+"}");

    }

    public void attributeRemoved(ServletContextAttributeEvent event) {
        System.out.println("ServletContext attribute added::{"+event.getName()+","+event.getValue()+"}");
    }

    public void attributeReplaced(ServletContextAttributeEvent event) {
        System.out.println("ServletContext attribute added::{"+event.getName()+","+event.getValue()+"}");
    }
}
