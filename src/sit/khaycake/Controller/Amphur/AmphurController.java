package sit.khaycake.Controller.Amphur;

import sit.khaycake.model.Amphur;
import sit.khaycake.model.Tumbon;
import sit.khaycake.util.ErrorMessage;
import sit.khaycake.util.SuccessMessage;

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
@WebServlet(name = "AmphurController", urlPatterns = "/amphur")
public class AmphurController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();
        SuccessMessage success = new SuccessMessage(session);
        ErrorMessage error = new ErrorMessage(session);
        String keyword = req.getParameter("q");

        try {
            if (keyword != null) {
                success.setMessage(Amphur.find(keyword));
            } else {
                success.setMessage(Amphur.find(""));
            }
        }catch (Exception ex){
            error.setMessage(ex.getMessage());
        }
    }
}