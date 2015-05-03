package sit.khaycake.Controller.Product;


import sit.khaycake.database.SQL;
import sit.khaycake.model.*;
import sit.khaycake.util.ErrorMessage;
import sit.khaycake.util.SuccessMessage;
import sit.khaycake.util.Util;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Created by Pasuth on 19/4/2558.
 */
public class ProductServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        SuccessMessage success = new SuccessMessage(session);
        ErrorMessage error = new ErrorMessage(session);

        String cat_id = request.getParameter("cat");
        try {
            if(cat_id != null && Util.isInteger(cat_id)){
                List<Product> products = Product.findByCategory(Integer.parseInt(cat_id));
                success.setMessage(products);
            }else{
                List<Product> products = (List<Product>)SQL.findAll(Product.class);
                success.setMessage(products);
            }
        } catch (Exception ex) {
            error.setMessage(ex.getMessage());
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        SuccessMessage success = new SuccessMessage(session);
        ErrorMessage error = new ErrorMessage(session);
        try {
            Product product = new Product();
            product.setName(request.getParameter("NAME"));
            product.setDetail(request.getParameter("DETAIL"));
            product.setCost(Double.parseDouble(request.getParameter("COST")));
            product.setCategory((Category) SQL.findById(Category.class
                    , Integer.parseInt(request.getParameter("CAT_ID"))));
            product.save();

            String[] productSales = request.getParameterValues("QTY");
            for (String productSaleStr : productSales) {
                ProductSale productSale = new ProductSale();
                productSale.setProdId(product.getId());
                productSale.setQty(Integer.parseInt(productSaleStr));
                productSale.save();
            }

            String[] pictures = request.getParameterValues("PIC_ID");
            for(String picStr : pictures){
                PicProduct picProduct = new PicProduct();
                Picture picture = (Picture)SQL.findById(Picture.class, picStr);
                if(picture!= null){
                    picProduct.setProdId(product.getId());
                    picProduct.setPicture(picture);
                    picProduct.save();
                }

            }
            success.setMessage(product);
        } catch (Exception ex) {
            error.setMessage(ex.getMessage());
        }
    }
}

