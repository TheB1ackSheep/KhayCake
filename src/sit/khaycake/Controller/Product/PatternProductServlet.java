package sit.khaycake.Controller.Product;

import sit.khaycake.database.SQL;
import sit.khaycake.model.Product;
import sit.khaycake.model.ProductSale;
import sit.khaycake.model.Picture;
import sit.khaycake.model.PicProduct;
import sit.khaycake.model.Category;

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
public class PatternProductServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String resource = request.getPathInfo().substring(request.getPathInfo().indexOf("/", 0) + 1);
        HttpSession session = request.getSession();
        SuccessMessage succes = new SuccessMessage(session);
        ErrorMessage error = new ErrorMessage(session);

        if (resource.indexOf("delete") >= 0) {
            resource = resource.substring(0, resource.indexOf("/", 1));
            try {
                Product product = (Product)SQL.findById(Product.class,resource);

                if (product!=null) {
                    Product.delete(product.getId());
                    succes.setMessage(product);
                }else{
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            } catch (Exception e) {
                error.setMessage(e.getMessage());
            }

        } else if(resource.indexOf("picture") >= 0) {
            resource = resource.substring(0, resource.indexOf("/", 1));
            try {
                if (Util.isInteger(resource)) {
                    List<Picture> pictures = PicProduct.getPictures(
                            PicProduct.findByProdId(Integer.parseInt(resource)));
                    if (pictures.isEmpty())
                        response.sendError(HttpServletResponse.SC_NOT_FOUND);
                    succes.setMessage(pictures);
                }

            } catch (Exception e) {
                error.setMessage(e.getMessage());
            }

        } else if(resource.indexOf("productsale") >= 0) {
            resource = resource.substring(0, resource.indexOf("/", 1));
            try {
                if (Util.isInteger(resource)) {
                    List<ProductSale> productSales = ProductSale.findByProdId(
                            Integer.parseInt(resource));
                    if (productSales.isEmpty())
                        response.sendError(HttpServletResponse.SC_NOT_FOUND);
                    succes.setMessage(productSales);
                }

            } catch (Exception e) {
                error.setMessage(e.getMessage());
            }

        } else {

            try {
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

            } catch (Exception e) {
                error.setMessage(e.getMessage());
            }

        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String resource = request.getRequestURI().substring(request.getRequestURI().indexOf("/", 1));
        HttpSession session = request.getSession();
        SuccessMessage succes = new SuccessMessage(session);
        ErrorMessage error = new ErrorMessage(session);

        try {
            Product product = (Product) SQL.findById(Product.class, Integer.parseInt(resource));

            if (product != null) {
                product.setName(request.getParameter("NAME"));
                product.setDetail(request.getParameter("DETAIL"));
                product.setCost(Double.parseDouble(request.getParameter("COST")));
                product.setCategory(
                        (Category) SQL.findById(Category.class
                                , Integer.parseInt(request.getParameter("CAT_ID"))));
                product.update();
                succes.setMessage(product);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            error.setMessage(e.getMessage());
        }
    }

}
