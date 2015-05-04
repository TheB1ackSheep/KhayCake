package sit.khaycake.Controller.District;

import com.google.gson.Gson;
import sit.khaycake.Filter.request.DistrictRequest;
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
import java.util.List;

/**
 * Created by Pasuth on 19/4/2558.
 */
public class PatternDistrictServlet extends HttpServlet {
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
                District district = (District)SQL.findById(District.class,resource);
                if (district != null) {
                    District.delete(Integer.parseInt(resource));
                    succes.setMessage(district);
                }else{
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            } catch (Exception ex) {
                error.setMessage(ex.getMessage());
            }

        }else if (resource.indexOf("province") >= 0) {
            resource = resource.substring(0, resource.indexOf("/", 1));
            try {
                District district = (District)SQL.findById(District.class,resource);
                if (district != null) {
                    Province province = (Province)SQL.findById(Province.class, district.getProvinceId());
                    succes.setMessage(province);
                }else{
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            } catch (Exception ex) {
                error.setMessage(ex.getMessage());
            }

        }  else if(resource.indexOf("subdistrictlist") >= 0){
            resource = resource.substring(0, resource.indexOf("/", 1));
            try {
                District district = (District)SQL.findById(District.class,resource);
                if (district != null) {
                    succes.setMessage(district.getSubDistrictList());
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            } catch (Exception ex) {
                error.setMessage(ex.getMessage());
            }

        }else {
            try {
                District district = (District) SQL.findById(District.class, Integer.parseInt(resource));
                if (district != null) {
                    succes.setMessage(district);
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
        SuccessMessage succes = new SuccessMessage(session);
        ErrorMessage error = new ErrorMessage(session);
        DistrictRequest districtRequest = new DistrictRequest(request);
        if(districtRequest.validate()) {
            try {
                District district = (District) SQL.findById(District.class, Integer.parseInt(resource));
                if (district != null) {
                    district.setName(request.getParameter("NAME"));
                    district.setProvinceId(Integer.parseInt(request.getParameter("PROV_ID")));
                    district.update();
                    succes.setMessage(district);
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            } catch (Exception ex) {
                error.setMessage(ex.getMessage());
            }
        }

    }

}
