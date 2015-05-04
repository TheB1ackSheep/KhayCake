package sit.khaycake.Controller.Province;

import com.google.gson.Gson;
import sit.khaycake.Filter.request.ProvinceRequest;
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
import java.util.List;

/**
 * Created by Pasuth on 19/4/2558.
 */
public class ProvinceServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        SuccessMessage success = new SuccessMessage(session);
        ErrorMessage error = new ErrorMessage(session);
        try {
            success.setMessage((Province) SQL.findAll(Province.class));
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
        ProvinceRequest provinceRequest = new ProvinceRequest(request);
        if(provinceRequest.validate()) {
            try {
                Province province = new Province();
                province.setName(request.getParameter("name"));

                success.setMessage(province);
            } catch (Exception ex) {
                error.setMessage(ex.getMessage());
            }
        }

    }
}
