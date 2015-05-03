package sit.khaycake.Controller.Product;

import sit.khaycake.database.SQL;
import sit.khaycake.model.*;

import sit.khaycake.util.ErrorMessage;
import sit.khaycake.util.SuccessMessage;
import sit.khaycake.util.Util;

import javax.imageio.ImageIO;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.List;

/**
 * Created by Pasuth on 19/4/2558.
 */
public class PatternProductServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String resource = request.getPathInfo().substring(request.getPathInfo().indexOf("/", 0) + 1);
        HttpSession session = request.getSession();
        SuccessMessage succes = new SuccessMessage(session);
        ErrorMessage error = new ErrorMessage(session);
        try {

            if (resource.indexOf("delete") >= 0) {
                resource = resource.substring(0, resource.indexOf("/", 1));

                int a = Product.delete(Integer.parseInt(resource));
                if (a < 0) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            } else if (resource.indexOf("picture") >= 0 && resource.indexOf("pictures") == -1) {
                resource = resource.substring(0, resource.indexOf("/", 1));

                if(Util.isInteger(resource)){
                    List<Picture> pictures = Picture.getPicturesByProductId(resource);
                    if(pictures.size()>0)
                    {

                        Picture picture = pictures.get(0);
                        response.sendRedirect( request.getContextPath()+"/images/"+picture.getFilename());
                    }else
                        response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }

            } else if (resource.indexOf("pictures") >= 0) {
                resource = resource.substring(0, resource.indexOf("/", 1));

                if (Util.isInteger(resource)) {
                    List<Picture> pictures = Picture.getPicturesByProductId(resource);
                    if (pictures.isEmpty())
                        response.sendError(HttpServletResponse.SC_NOT_FOUND);
                    succes.setMessage(pictures);
                }

            } else if (resource.indexOf("sale") >= 0) {
                resource = resource.substring(0, resource.indexOf("/", 1));

                if (Util.isInteger(resource)) {
                    List<ProductSale> productSales = ProductSale.findByProdId(
                            Integer.parseInt(resource));
                    if (productSales.isEmpty())
                        response.sendError(HttpServletResponse.SC_NOT_FOUND);
                    succes.setMessage(productSales);
                }


            } else {

                if (Util.isInteger(resource)) {
                    Product product = (Product) SQL.findById(Product.class, Integer.parseInt(resource));
                    if (product == null)
                        response.sendError(HttpServletResponse.SC_NOT_FOUND);
                    succes.setMessage(product);
                } else {
                    List<Product> products = (List<Product>) SQL.findByKeyword(Product.class, resource);
                    if (products.isEmpty())
                        response.sendError(HttpServletResponse.SC_NOT_FOUND);
                    succes.setMessage(products);
                }


            }
        } catch (Exception e) {
            error.setMessage(e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String resource = request.getPathInfo().substring(request.getPathInfo().indexOf("/", 0) + 1);
        HttpSession session = request.getSession();
        SuccessMessage succes = new SuccessMessage(session);
        ErrorMessage error = new ErrorMessage(session);

        try {
            if(resource == null) {
                Product product = (Product) SQL.findById(Product.class, Integer.parseInt(resource));

                if (product != null) {
                    product.setName(request.getParameter("name"));
                    product.setDetail(request.getParameter("detail"));
                    product.setCost(Double.parseDouble(request.getParameter("cost")));
                    product.setPrice(Double.parseDouble(request.getParameter("price")));
                    product.setCategory((Category) SQL.findById(Category.class
                            , Integer.parseInt(request.getParameter("cat_id"))));
                    product.setUnit((Unit) SQL.findById(Unit.class
                            , Integer.parseInt(request.getParameter("unit_id"))));
                    product.update();

                    String[] saleQty = request.getParameterValues("sale_qty");
                    String[] salePrice = request.getParameterValues("sale_price");
                    if (saleQty != null && salePrice != null)
                        for (int i = 0; i < saleQty.length; i++) {
                            ProductSale productSale = new ProductSale();
                            productSale.setProdId(product.getId());
                            productSale.setQty(Integer.parseInt(saleQty[i]));
                            productSale.setPrice(Double.parseDouble(salePrice[i]));
                            productSale.save();
                        }

                    String[] picId = request.getParameterValues("pic_id");
                    for (int i = 0; i < picId.length; i++) {
                        PicProduct picProduct = new PicProduct();
                        picProduct.setProdId(product.getId());
                        picProduct.setPicId(Integer.parseInt(picId[i]));
                        picProduct.save();
                    }
                    succes.setMessage(product);
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            }else{
                if(resource.equalsIgnoreCase("delete")){
                    String[] prodId = request.getParameterValues("p_id");
                    if(prodId != null){
                        for(int i=0;i<prodId.length;i++)
                            Product.delete(Integer.parseInt(prodId[i]));
                        succes.setMessage("success");
                    }
                }
            }
        } catch (Exception e) {
            error.setMessage(e.getMessage());
        }
    }

}
