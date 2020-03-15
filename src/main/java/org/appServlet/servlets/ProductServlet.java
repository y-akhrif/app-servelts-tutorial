package org.appServlet.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.appServlet.model.Product;


@WebServlet(name = "ProductServlet", urlPatterns = { "/ProductServlet" })
public class ProductServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	static Logger logger = Logger.getLogger(ProductServlet.class);
	
	
	public void doGet( HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Connection connection = (Connection) getServletContext().getAttribute("DBConnection");
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement("select * from product");
			ResultSet  result = statement.executeQuery();
			List<Product> listProduct = new ArrayList<>();
			while(result.next()) {
				Product product = new Product();
				product.setId(result.getInt("id"));
				product.setName(result.getString("name"));
				product.setDescription(result.getString("description"));
				product.setPrice(result.getFloat("price"));
				product.setStock(result.getInt("stock"));
				listProduct.add(product);
				
			}
			request.setAttribute("products", listProduct);
			request.getRequestDispatcher("/products.jsp").forward(request, response);
			
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("Database connection problem");
			throw new ServletException("DB Connection problem.");
		}finally{
			try {
				if(statement!=null) statement.close();
			} catch (SQLException e) {
				logger.error("SQLException in closing PreparedStatement");
			}
		}
		
	}
	
}
