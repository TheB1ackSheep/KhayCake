package sit.khaycake.Controller.Picture;

import com.google.gson.Gson;
import sit.khaycake.database.SQL;
import sit.khaycake.model.Category;
import sit.khaycake.model.Picture;
import sit.khaycake.model.Picture;
import sit.khaycake.util.ErrorMessage;
import sit.khaycake.util.SuccessMessage;
import sit.khaycake.util.Util;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Created bys Pasuth on 19/4/2558.
 */
public class PatternPictureServlet extends HttpServlet {
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
                Picture picture = (Picture)SQL.findById(Picture.class,resource);
                String appPath = request.getServletContext().getRealPath("");
                File file = new File/*(appPath+"\\images\\"+picture.getFilename());*/("/usr/share/glassfish4/glassfish/domains/jsp.falook.me/applications/khaycake/images/"+picture.getFilename());
                if(file.delete()) {
                    Picture.delete(picture.getId());
                    succes.setMessage(picture);
                }else{
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            } catch (Exception ex) {
                error.setMessage(ex.getMessage());
            }

        } else {
            Gson gson = new Gson();
            try {
                    Picture picture = null;
                    picture = (Picture) SQL.findById(Picture.class, Integer.parseInt(resource));
                    if (picture != null) {
                        succes.setMessage(picture);
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
        HttpSession session = request.getSession();
        SuccessMessage succes = new SuccessMessage(session);
        ErrorMessage error = new ErrorMessage(session);
        String resource = request.getRequestURI().substring(request.getRequestURI().indexOf("/", 1));
        Picture picture = null;
        try {
            picture = (Picture) SQL.findById(Picture.class, Integer.parseInt(resource));
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
        if (picture != null) {
            try{
                picture.setFilename(request.getParameter("FILENAME"));
                picture.update();
                succes.setMessage(picture);
            } catch (Exception ex) {
                error.setMessage(ex.getMessage());
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }

    }

}
