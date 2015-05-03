package sit.khaycake.Controller.Product;

import com.google.gson.Gson;
import sit.khaycake.database.SQL;
import sit.khaycake.model.*;
import sit.khaycake.util.ErrorMessage;
import sit.khaycake.util.JsonMessage;
import sit.khaycake.util.SuccessMessage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        String keyword = request.getParameter("q");
        try {
            if(cat_id != null && keyword != null){
                Set<Product> byCat = new HashSet<>(Product.findByCategory(cat_id));
                Set<Product> byQ = new HashSet<>((List<Product>)SQL.findByKeyword(Product.class,keyword));
                success.setMessage(byCat.retainAll(byQ));
            }else if(cat_id != null || keyword != null){
                if(cat_id != null){
                    List<Product> products = Product.findByCategory(cat_id);
                    success.setMessage(products);
                }else if(keyword != null) {
                    List<Product> products = (List<Product>) SQL.findByKeyword(Product.class, keyword);
                    success.setMessage(products);
                }
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
            product.setName(request.getParameter("name"));
            product.setDetail(request.getParameter("detail"));
            product.setCost(Double.parseDouble(request.getParameter("cost")));
            product.setPrice(Double.parseDouble(request.getParameter("price")));
            product.setCategory((Category) SQL.findById(Category.class
                    , Integer.parseInt(request.getParameter("cat_id"))));
            product.setUnit((Unit) SQL.findById(Unit.class
                    , Integer.parseInt(request.getParameter("unit_id"))));
            product.save();

            String[] saleQty = request.getParameterValues("sale_qty");
            String[] salePrice = request.getParameterValues("sale_price");
            if(saleQty != null && salePrice != null)
                for(int i=0;i<saleQty.length;i++){
                    ProductSale productSale = new ProductSale();
                    productSale.setProdId(product.getId());
                    productSale.setQty(Integer.parseInt(saleQty[i]));
                    productSale.setPrice(Double.parseDouble(salePrice[i]));
                    productSale.save();
                }

            String[] picId = request.getParameterValues("pic_id");
            for(int i=0;i<picId.length;i++){
                PicProduct picProduct = new PicProduct();
                picProduct.setProdId(product.getId());
                picProduct.setPicId(Integer.parseInt(picId[i]));
                picProduct.save();
            }
            success.setMessage(product);
        } catch (Exception ex) {
            error.setMessage(ex.getMessage());
        }
    }
}
