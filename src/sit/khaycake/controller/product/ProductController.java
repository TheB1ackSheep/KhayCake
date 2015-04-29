package sit.khaycake.controller.product;

import com.google.gson.Gson;
import sit.khaycake.database.SQL;
import sit.khaycake.model.*;
import sit.khaycake.util.ErrorHandler;
import sit.khaycake.util.Util;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Falook Glico on 4/26/2015.
 */
@WebServlet(name = "ProductController", urlPatterns = "/product/*")
public class ProductController extends HttpServlet {


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ErrorHandler error = new ErrorHandler(request.getSession());

        String[] urlFragment = request.getRequestURI().split("/");
        String id = null;
        if(urlFragment.length > 3)
            id = urlFragment[3];

        boolean isUpdate = (id != null && Util.isInteger(id));

        if(!error.hasError()){
            if(id != null && id.equals("delete")){
                    String[] pic_id = request.getParameterValues("p_id");
                    if(pic_id != null){
                        for(int i=0;i<pic_id.length;i++){
                            try {
                                Product p = (Product)SQL.findById(Product.class,pic_id[i]);
                                if(p != null)
                                    p.delete();
                            } catch (Exception e) {
                                error.addException(e);
                            }
                        }
                        request.setAttribute("print", (new Gson()).toJson("success"));
                    }

            }else{

                String name = request.getParameter("name");
                String detail = request.getParameter("detail");
                String[] pic_id = request.getParameterValues("pic_id");
                String unit_id = request.getParameter("unit");
                String cat_id = request.getParameter("category");
                String cost = request.getParameter("cost");
                String price = request.getParameter("price");
                String[] qty_sale = request.getParameterValues("qty_sale");
                String[] price_sale = request.getParameterValues("price_sale");

                try {
                    Product product = new Product();
                    if(isUpdate)
                        product = (Product)SQL.findById(Product.class,id);
                    product.setName(name);
                    product.setDetail(detail);
                    product.setUnit((Unit) SQL.findById(Unit.class, unit_id));
                    product.setCategory((Category) SQL.findById(Category.class, cat_id));
                    product.setCost(Double.parseDouble(cost));
                    product.setPrice(Double.parseDouble(price));
                    if(pic_id != null) {
                        product.setPictures(new ArrayList<>());
                        for (int i = 0; i < pic_id.length; i++)
                            product.addPicture((Picture) SQL.findById(Picture.class, pic_id[i]));
                    }
                    if(qty_sale != null && price_sale != null){
                        product.setSales(new ArrayList<>());
                        for(int i=0;i<qty_sale.length;i++){
                            if(Util.isInteger(qty_sale[i]) && Util.isInteger(price_sale[i])){
                                ProductSale ps = new ProductSale();
                                ps.setQty(Integer.parseInt(qty_sale[i]));
                                ps.setPrice(Double.parseDouble(price_sale[i]));
                                product.addSales(ps);
                            }

                        }
                    }

                    product.save(isUpdate);
                    request.setAttribute("print", product);
                } catch (Exception e) {
                    error.addException(e);
                }
            }

        }


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ErrorHandler error = new ErrorHandler(request.getSession());

        PrintWriter out = response.getWriter();
        String[] urlFragment = request.getRequestURI().split("/");
        String id = null;
        if(urlFragment.length > 3)
            id = urlFragment[3];

        try {
            if (id != null) {
                Product product = (Product) SQL.findById(Product.class, id);
                if (product != null)
                    request.setAttribute("print",product);
                else
                    response.sendError(404);
            } else {
                SQL sql = new SQL();
                List<Product> products = null;
                if(request.getParameter("all")!=null){
                    products = sql.select()
                            .from(Product.TABLE_NAME).order(Product.COLUMN_ID, SQL.OrderClause.Operator.DESC)
                            .fetch(Product.class);
                }else if(request.getParameter("q")!=null){
                    products = (List<Product>) SQL.findByKeyword(Product.class,request.getParameter("q"));
                }else{
                    products = sql.select()
                            .from(Product.TABLE_NAME).order(Product.COLUMN_ID, SQL.OrderClause.Operator.DESC)
                            .chunk(10)
                            .fetch(Product.class);
                }

                request.setAttribute("print", products);
            }

            }catch(Exception e){

                error.addException(e);
            }



        }


    }
