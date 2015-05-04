package sit.khaycake.Controller.Province;

import com.google.gson.Gson;
import sit.khaycake.database.SQL;
import sit.khaycake.model.Province;
import sit.khaycake.util.ErrorMessage;
import sit.khaycake.util.SuccessMessage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Pasuth on 19/4/2558.
 */
public class PatternProvinceServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String resource = request.getPathInfo().substring(request.getPathInfo().indexOf("/", 0)+1);
        HttpSession session = request.getSession();
        SuccessMessage succes = new SuccessMessage(session);
        ErrorMessage error = new ErrorMessage(session);

        if (resource.indexOf("delete") >= 0) {
            resource = resource.substring(0, resource.indexOf("/", 1));
            try {
                Province province = (Province)SQL.findById(Province.class,resource);

                if (province!=null) {
                    Province.delete(province.getId());
                    succes.setMessage(province);
                }else{
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            } catch (Exception ex) {
                error.setMessage(ex.getMessage());
            }

        }else if(resource.indexOf("districtlist") >= 0){
            resource = resource.substring(0, resource.indexOf("/", 1));

            try {
                Province province = (Province) SQL.findById(Province.class, Integer.parseInt(resource));
                if (province != null) {
                    succes.setMessage(province.getDistrictList());

                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }

            } catch (Exception ex) {
                error.setMessage(ex.getMessage());
            }


        } else {
            try {
                Province province = (Province) SQL.findById(Province.class, Integer.parseInt(resource));
                if (province != null) {
                    succes.setMessage(province);
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            } catch (Exception ex) {
                error.setMessage(ex.getMessage());
            }

        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String resource = request.getPathInfo().substring(request.getPathInfo().indexOf("/", 0)+1);
        HttpSession session = request.getSession();
        SuccessMessage succes = new SuccessMessage(session);
        ErrorMessage error = new ErrorMessage(session);
        try {
            Province province = (Province) SQL.findById(Province.class, Integer.parseInt(resource));
            if (province != null) {
                province.setName(request.getParameter("name"));
                province.update();
                succes.setMessage(province);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception ex) {
            error.setMessage(ex.getMessage());
        }

    }

}
