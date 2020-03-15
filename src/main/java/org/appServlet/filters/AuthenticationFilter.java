package org.appServlet.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter("/AuthenticationFilter")
public class AuthenticationFilter implements Filter {
	
	ServletContext context;
	
	public void init(FilterConfig fConfig) throws ServletException{
		this.context = fConfig.getServletContext();
		this.context.log("AuthenticationFilter filter initialized");
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest httpRequest = (HttpServletRequest) request; 
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		
		String uri = httpRequest.getRequestURI();
		this.context.log("Requested Resource::"+uri);

		HttpSession session = httpRequest.getSession(false);


		if(session == null && !(uri.endsWith("html") || uri.endsWith("LoginServlet")  || uri.endsWith("RegisterServlet"))){
			this.context.log("Unauthorized access request");
			httpResponse.sendRedirect("login.html");
		}else{
			// pass the request along the filter chain
			chain.doFilter(request, response);
		}

		
	}

}
