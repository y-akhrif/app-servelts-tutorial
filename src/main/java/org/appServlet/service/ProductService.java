package org.appServlet.service;

import org.apache.log4j.Logger;
import org.appServlet.model.Product;

import javax.servlet.ServletException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductService {
    private static final long serialVersionUID = 1L;

    static Logger logger = Logger.getLogger(ProductService.class);

    Connection connection;

    public ProductService(Connection connection){
        this.connection = connection;
    }

    public List<Product> listAllProduct() throws ServletException {
        List<Product> products = new ArrayList<>();

        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("select * from product");
            ResultSet result = statement.executeQuery();
            while(result.next()) {
                Product product = new Product();
                product.setId(result.getInt("id"));
                product.setName(result.getString("name"));
                product.setDescription(result.getString("description"));
                product.setPrice(result.getFloat("price"));
                product.setStock(result.getInt("stock"));
                products.add(product);

            }
        } catch (SQLException e) {
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
            return products;
    }

    public boolean updateProduct(int id, String name, String description, float price, int stock) throws ServletException {
        PreparedStatement statement = null;
        boolean result = false;
        try {
            statement = connection.prepareStatement("update product set id=?, name=?, description=?, price=?, stock=? where id=?");
            statement.setInt(1, id);
            statement.setString(2, name);
            statement.setString(3, description);
            statement.setFloat(4, price);
            statement.setInt(5, stock);
            statement.setInt(6, id);
            statement.execute();
            result =  true;

        } catch (SQLException  e) {
            e.printStackTrace();
            logger.error("Database connection problem");
            throw new ServletException("DB Connection problem.");
        }finally {
            try {
                if(statement!=null) statement.close();

            } catch (SQLException e) {
                logger.error("SQLException in closing PreparedStatement");
            }
            return  result;
        }
    }

    public boolean addProduct(String name, String description, float price, int stock) throws ServletException {
        PreparedStatement statement = null;
        boolean result = false;
        try {
            statement = connection.prepareStatement("insert into product (name, description, price, stock) values (?, ?, ?, ?)");
            statement.setString(1, name);
            statement.setString(2, description);
            statement.setFloat(3, price);
            statement.setInt(4, stock);
            statement.execute();
            result =  true;

        } catch (SQLException  e) {
            e.printStackTrace();
            logger.error("Database connection problem");
            throw new ServletException("DB Connection problem.");
        }finally {
            try {
                if(statement!=null) statement.close();

            } catch (SQLException e) {
                logger.error("SQLException in closing PreparedStatement");
            }
            return  result;
        }
    }

    public boolean deleteProduct(int id) throws ServletException {
        PreparedStatement statement = null;
        boolean result = false;
        try {
            statement = connection.prepareStatement("delete from product where id=?");
            statement.setInt(1, id);
            statement.execute();
            result =  true;

        } catch (SQLException  e) {
            e.printStackTrace();
            logger.error("Database connection problem");
            throw new ServletException("DB Connection problem.");
        }finally {
            try {
                if(statement!=null) statement.close();

            } catch (SQLException e) {
                logger.error("SQLException in closing PreparedStatement");
            }
            return  result;
        }
    }
}
