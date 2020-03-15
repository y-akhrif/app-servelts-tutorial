package org.appServlet.servlets;

import org.apache.log4j.Logger;
import org.appServlet.model.Product;
import org.appServlet.service.ProductService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.List;

@WebServlet("/DeleteProductServlet")
public class DeleteProductServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    static Logger logger = Logger.getLogger(DeleteProductServlet.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        logger.info("--DeleteProdductServlet: deleting the product id="+id);

        try {
           Connection connection = (Connection) getServletContext().getAttribute("DBConnection");
           ProductService productService = new ProductService(connection);
           boolean result = productService.deleteProduct(id);
           List<Product> listProduct = productService.listAllProduct();
           request.setAttribute("products", listProduct);

           RequestDispatcher rd = getServletContext().getRequestDispatcher("/products.jsp");
           PrintWriter out= response.getWriter();
           if(result){
               logger.info("Product ID="+id+" has been delete successfully!");
               out.println("<font color=green>Product ID="+id+" has been updated successfully!</font>");
           }else {
               logger.info("Cannot save the Product ID="+id);
               out.println("<font color=red>Cannot delete the Product ID="+id+" !</font>");
           }
           rd.include(request, response);

       }catch (Exception ex){
           ex.printStackTrace();
           logger.error(" Error found during executio the servelet");
           throw new ServletException("Error found during executio the servelet");
       }


    }
}
