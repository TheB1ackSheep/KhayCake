package sit.khaycake.Controller.Product;

import sit.khaycake.Filter.request.ProductRequest;
import sit.khaycake.database.SQL;
import sit.khaycake.model.*;
import sit.khaycake.util.ErrorMessage;
import sit.khaycake.util.SuccessMessage;

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
public class PatternProductServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String[] resources = request.getRequestURI().split("/");

        HttpSession session = request.getSession();
        SuccessMessage success = new SuccessMessage(session);
        ErrorMessage error = new ErrorMessage(session);

        String id = null, method = null;
        if (resources.length >= 4)
            id = resources[3];
        if (resources.length >= 5)
            method = resources[4];

        try {

            Product product = SQL.findById(Product.class, id);
            if (product != null) {
                if (method != null) {
                    switch (method) {
                        case "picture":
                            response.sendRedirect(request.getContextPath() + "/images/" + product.getPictures().get(0).getFilename());
                            break;
                        case "pictures":
                            List<Picture> pictures = product.getPictures();
                            success.setMessage(pictures);
                            break;
                        case "sales":
                            List<ProductSale> productSales = product.getPrices();
                            success.setMessage(productSales);
                            break;
                        case "delete":
                            product.delete();
                            success.setMessage("deleted");
                            break;
                        default:
                            success.setMessage(product);
                            break;
                    }
                } else {
                    //show product
                    success.setMessage(product);
                }
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }


        } catch (Exception e) {
            error.setMessage(e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String[] resources = request.getRequestURI().split("/");

        HttpSession session = request.getSession();
        SuccessMessage success = new SuccessMessage(session);
        ErrorMessage error = new ErrorMessage(session);

        String id = null;
        if (resources.length >= 4)
            id = resources[3];

        ProductRequest productRequest = new ProductRequest(request);

        if (productRequest.validate()) {
            try {
                Product product = (Product) SQL.findById(Product.class, id);
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
                    if (saleQty != null && salePrice != null) {
                        product.deleteSales();
                        for (int i = 0; i < saleQty.length; i++) {
                            ProductSale productSale = new ProductSale();
                            productSale.setProdId(product.getId());
                            productSale.setQty(Integer.parseInt(saleQty[i]));
                            productSale.setPrice(Double.parseDouble(salePrice[i]));
                            productSale.save();
                        }
                    }

                    String[] picId = request.getParameterValues("pic_id");
                    product.deletePictures();
                    for (int i = 0; i < picId.length; i++) {
                        PicProduct picProduct = new PicProduct();
                        picProduct.setProdId(product.getId());
                        picProduct.setPicId(Integer.parseInt(picId[i]));
                        picProduct.save();
                    }
                    success.setMessage(product);

                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }


            } catch (Exception e) {
                error.setMessage(e.getMessage());
            }
        }


    }

}
