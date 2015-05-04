package sit.khaycake.Controller.SubDistrict;

import com.google.gson.Gson;
import sit.khaycake.Filter.request.SubDistrictRequest;
import sit.khaycake.database.SQL;
import sit.khaycake.model.District;
import sit.khaycake.model.Province;
import sit.khaycake.model.SubDistrict;
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
public class PatternSubDistrictServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String resource = request.getPathInfo().substring(request.getPathInfo().indexOf("/", 0)+1);
        HttpSession session = request.getSession();
        SuccessMessage success = new SuccessMessage(session);
        ErrorMessage error = new ErrorMessage(session);
        if (resource.indexOf("delete") >= 0) {
            resource = resource.substring(0, resource.indexOf("/", 1));
            try {
                SubDistrict subDistrict = (SubDistrict)SQL.findById(SubDistrict.class,resource);

                if (subDistrict!=null) {
                    SubDistrict.delete(subDistrict.getId());
                    success.setMessage(subDistrict);
                }else{
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            } catch (Exception ex) {
                error.setMessage(ex.getMessage());
            }

        } else if (resource.indexOf("district") >= 0) {
            resource = resource.substring(0, resource.indexOf("/", 1));
            try {
                SubDistrict subDistrict = (SubDistrict)SQL.findById(SubDistrict.class,resource);

                if (subDistrict!=null) {
                    District district = (District)SQL.findById(District.class,subDistrict.getDistrictId());
                    success.setMessage(district);
                }else{
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            } catch (Exception ex) {
                error.setMessage(ex.getMessage());
            }

        } else if (resource.indexOf("district") >= 0) {
            resource = resource.substring(0, resource.indexOf("/", 1));
            try {
                SubDistrict subDistrict = (SubDistrict)SQL.findById(SubDistrict.class,resource);

                if (subDistrict!=null) {
                    District district = (District)SQL.findById(District.class,subDistrict.getDistrictId());
                    Province province = (Province)SQL.findById(Province.class,district.getProvinceId());
                    success.setMessage(province);
                }else{
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            } catch (Exception ex) {
                error.setMessage(ex.getMessage());
            }

        }else {
            try {
                SubDistrict subDistrict = (SubDistrict) SQL.findById(SubDistrict.class, Integer.parseInt(resource));
                if (subDistrict != null) {
                    success.setMessage(subDistrict);
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
        String resource = request.getPathInfo().substring(request.getPathInfo().indexOf("/", 0) + 1);
               HttpSession session = request.getSession();
        SuccessMessage success = new SuccessMessage(session);
        ErrorMessage error = new ErrorMessage(session);
        SubDistrictRequest subDistrictRequest = new SubDistrictRequest(request);
        if(subDistrictRequest.validate()) {
            try {
                SubDistrict subDistrict = (SubDistrict) SQL.findById(SubDistrict.class, Integer.parseInt(resource));
                if (subDistrict != null) {
                    subDistrict.setName(request.getParameter("name"));
                    subDistrict.setZipCode(request.getParameter("zipcode"));
                    subDistrict.setDistrictId(Integer.parseInt(request.getParameter("dist_id")));
                    subDistrict.update();
                    success.setMessage(subDistrict);
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }

            } catch (Exception ex) {
                error.setMessage(ex.getMessage());
            }
        }
    }

}
