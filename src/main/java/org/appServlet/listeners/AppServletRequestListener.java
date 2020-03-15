package org.appServlet.listeners;

import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;

/**
 * Implementation of ServletRequestListener interface to log the ServletRequest IP address when request is initialized and destroyed.
 */
@WebListener
public class AppServletRequestListener implements ServletRequestListener {

    public void requestDestroyed(ServletRequestEvent servletRequestEvent) {
        ServletRequest request  = servletRequestEvent.getServletRequest();
        System.out.println("ServletRequest destroyed. Remote IP="+request.getRemoteAddr());

    }

    public void requestInitialized(ServletRequestEvent servletRequestEvent) {
        ServletRequest request = servletRequestEvent.getServletRequest();
        System.out.println("ServletRequest initialized. Remote IP="+request.getRemoteAddr());
    }
}
