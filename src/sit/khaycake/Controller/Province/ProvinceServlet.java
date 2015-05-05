package sit.khaycake.Controller.Province;

import com.google.gson.Gson;
import sit.khaycake.database.SQL;
import sit.khaycake.model.Amphur;
import sit.khaycake.model.Province;
import sit.khaycake.model.Tumbon;
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
public class ProvinceServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession();
        SuccessMessage success = new SuccessMessage(session);
        ErrorMessage error = new ErrorMessage(session);

        String[] resources = req.getRequestURI().split("/");

        String id = null, method = null;
        if (resources.length >= 4)
            id = resources[3];
        if (resources.length >= 5)
            method = resources[4];

        try {

            if(id == null){
                //request URL is /province
                String keyword = req.getParameter("q");

                if (keyword != null) {
                    success.setMessage(Province.find(keyword));
                } else {
                    success.setMessage(Province.find(""));
                }

            }else{
                //request URL is /province/*
                if(Util.isInteger(id)){
                    //get province by id
                    Province prov = SQL.findById(Province.class, id);
                    if(prov != null)
                        success.setMessage(prov);
                    else
                        resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                }else{

                }
            }

        }catch (Exception ex){
            error.setMessage(ex.getMessage());
        }


    }

}
