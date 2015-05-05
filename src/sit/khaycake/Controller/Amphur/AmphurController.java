package sit.khaycake.Controller.Amphur;

import sit.khaycake.database.SQL;
import sit.khaycake.model.Amphur;
import sit.khaycake.model.Tumbon;
import sit.khaycake.util.ErrorMessage;
import sit.khaycake.util.SuccessMessage;
import sit.khaycake.util.Util;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Falook Glico on 5/5/2015.
 */
@WebServlet(name = "AmphurController", urlPatterns = "/amphur/*")
public class AmphurController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

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
                //request URL is /amphur
                String keyword = req.getParameter("q");

                if (keyword != null) {
                    success.setMessage(Amphur.find(keyword));
                } else {
                    success.setMessage(Amphur.find(""));
                }

            }else{
                //request URL is /amphur/*
                if(Util.isInteger(id)){
                    //get amphur by id
                    Amphur amp = SQL.findById(Amphur.class, id);
                    if(amp != null)
                        success.setMessage(amp);
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