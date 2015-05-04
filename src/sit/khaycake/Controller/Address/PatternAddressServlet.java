package sit.khaycake.Controller.Address;

import com.google.gson.Gson;
import sit.khaycake.database.SQL;
import sit.khaycake.model.Address;
import sit.khaycake.model.District;
import sit.khaycake.model.Province;
import sit.khaycake.model.SubDistrict;
import sit.khaycake.util.ErrorMessage;
import sit.khaycake.util.SuccessMessage;
import sit.khaycake.util.Util;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Pasuth on 19/4/2558.
 */
public class PatternAddressServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String resource = request.getPathInfo().substring(request.getPathInfo().indexOf("/", 0)+1);
        HttpSession session = request.getSession();
        SuccessMessage succes = new SuccessMessage(session);
        ErrorMessage error = new ErrorMessage(session);

        if (resource.indexOf("delete") >= 0) {
            resource = resource.substring(0,resource.indexOf("/", 1));
            try {
                Address address = (Address)SQL.findById(Address.class, resource);
                if (address != null) {
                    Address.delete(address.getId());
                    succes.setMessage(address);
                }else{
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            } catch (Exception ex) {
                error.setMessage(ex.getMessage());
            }

        } else if(resource.indexOf("subdistrict") >= 0) {
            resource = resource.substring(0, resource.indexOf("/", 1));
            try {
                if (Util.isInteger(resource)) {
                    Address address = (Address) SQL.findById(Address.class, resource);
                    if (address!=null) {
                        SubDistrict subDistrict = (SubDistrict) SQL.findById(SubDistrict.class, address.getSubDistrictId());
                        succes.setMessage(subDistrict);
                    }else {
                        response.sendError(HttpServletResponse.SC_NOT_FOUND);
                    }
                }
            } catch (Exception ex) {
                error.setMessage(ex.getMessage());
            }

        }else if(resource.indexOf("district") >= 0) {
            resource = resource.substring(0, resource.indexOf("/", 1));
            try {
                if (Util.isInteger(resource)) {
                    Address address = (Address) SQL.findById(Address.class, resource);
                    if (address!=null) {
                        SubDistrict subDistrict = (SubDistrict) SQL.findById(SubDistrict.class, address.getSubDistrictId());
                        District district = (District)SQL.findById(District.class, subDistrict.getDistrictId());
                        succes.setMessage(district);
                    }else {
                        response.sendError(HttpServletResponse.SC_NOT_FOUND);
                    }
                }
            } catch (Exception e) {
                error.setMessage(e.getMessage());
            }

        }else if(resource.indexOf("province") >= 0) {
            resource = resource.substring(0,resource.indexOf("/", 1));
            try {
                if (Util.isInteger(resource)) {
                    Address address = (Address) SQL.findById(Address.class, resource);
                    if (address!=null) {
                        SubDistrict subDistrict = (SubDistrict) SQL.findById(SubDistrict.class, address.getSubDistrictId());
                        District district = (District)SQL.findById(District.class, subDistrict.getDistrictId());
                        Province province = (Province)SQL.findById(Province.class, district.getProvinceId());
                        succes.setMessage(province);
                    }else {
                        response.sendError(HttpServletResponse.SC_NOT_FOUND);
                    }
                }
            } catch (Exception e) {
                error.setMessage(e.getMessage());
            }

        } else {
            try {
                Address address = (Address) SQL.findById(Address.class, resource);
                if(address!=null) {
                    succes.setMessage(address);
                }else{
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
        HttpSession session = request.getSession();
        SuccessMessage succes = new SuccessMessage(session);
        ErrorMessage error = new ErrorMessage(session);

        String resource = request.getPathInfo().substring(request.getPathInfo().indexOf("/", 0) + 1);
        try {
            Address address = (Address) SQL.findById(Address.class, resource);
            if (address != null) {
                address.setAddrNo(request.getParameter("ADDR_NO"));
                address.setAddrAdd(request.getParameter("ADDR_ADD"));
                address.setStreet(request.getParameter("STREET"));
                address.setSubDistrictId(Integer.parseInt(request.getParameter("SUDT_ID")));
                address.update();
                succes.setMessage(address);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        }catch (Exception ex){
                error.setMessage(ex.getMessage());
        }


    }

}
