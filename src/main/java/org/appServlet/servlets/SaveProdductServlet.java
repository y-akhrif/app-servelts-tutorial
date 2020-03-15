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

@WebServlet("/SaveProdductServlet")
public class SaveProdductServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    static Logger logger = Logger.getLogger(SaveProdductServlet.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String price = request.getParameter("price");
        String stock = request.getParameter("stock");
        logger.info("--SaveProdductServlet: updating the product id="+id);
        String errorMsg = null;
        if(name == null || name.equals("")){
            errorMsg = "Name can't be null or empty.";
        }
        if(description == null || description.equals("")){
            errorMsg = "Description can't be null or empty.";
        }
        if(price == null || price.equals("")){
            errorMsg = "Price can't be null or empty.";
        }
        if(stock == null || stock.equals("")){
            errorMsg = "Stock can't be null or empty.";
        }
        if(errorMsg != null){
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/products.html");
            PrintWriter out= response.getWriter();
            out.println("<font color=red>"+errorMsg+"</font>");
            rd.include(request, response);
        }else {
            try {
                Connection connection = (Connection) getServletContext().getAttribute("DBConnection");
                ProductService productService = new ProductService(connection);
                boolean result = false;
                if(id!=null){
                    result = productService.updateProduct(Integer.parseInt(id), name, description, Float.parseFloat(price), Integer.parseInt(stock));

                }else {
                    result = productService.addProduct( name, description, Float.parseFloat(price), Integer.parseInt(stock));

                }

                List<Product> listProduct = productService.listAllProduct();
                request.setAttribute("products", listProduct);

                RequestDispatcher rd = getServletContext().getRequestDispatcher("/products.jsp");
                PrintWriter out= response.getWriter();
                if(result && id!=null){
                    logger.info("Product ID="+id+" has been updated successfully!");
                    out.println("<font color=green>Product ID="+id+" has been updated successfully!</font>");
                }else if(result && id==null){
                    logger.info("Product  has been added successfully!");
                    out.println("<font color=green>Product has been updated successfully!</font>");
                }else {
                    logger.info("Cannot save the Product ID="+id);
                    out.println("<font color=red>Cannot save the Product ID="+id+" !</font>");
                }
                rd.include(request, response);

            }catch (Exception ex){
                ex.printStackTrace();
                logger.error(" Error found during executio the servelet");
                throw new ServletException("Error found during executio the servelet");
            }
        }
    }
}
