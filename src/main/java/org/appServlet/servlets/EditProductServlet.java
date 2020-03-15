package org.appServlet.servlets;

import org.apache.log4j.Logger;
import org.appServlet.model.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/EditProductServlet")
public class EditProductServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    static Logger logger = Logger.getLogger(ProductServlet.class);

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {

        int productId = Integer.parseInt(request.getParameter("id"));
        logger.info("Updating the product ID="+productId);

        Connection connection = (Connection) getServletContext().getAttribute("DBConnection");
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement("select * from product where id ='"+productId+"'");
            ResultSet result = statement.executeQuery();
            Product product = new Product();
            System.out.println("result");
            System.out.println(result);
            while (result.next()){
                product.setId(result.getInt("id"));
                product.setName(result.getString("name"));
                product.setDescription(result.getString("description"));
                product.setPrice(result.getFloat("price"));
                product.setStock(result.getInt("stock"));
            }
            request.setAttribute("product",product);
            request.getRequestDispatcher("/editProduct.jsp").forward(request,response);

        } catch (SQLException | IOException e) {
            e.printStackTrace();
            logger.error("Database connection problem");
            throw new ServletException("DB Connection problem.");
        }finally {
            try {
                if(statement!=null) statement.close();
            } catch (SQLException e) {
                logger.error("SQLException in closing PreparedStatement");
            }
        }
    }
}
