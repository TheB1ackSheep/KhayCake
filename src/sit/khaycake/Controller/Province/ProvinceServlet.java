package sit.khaycake.Controller.Province;

import com.google.gson.Gson;
import sit.khaycake.database.SQL;
import sit.khaycake.model.Province;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by Pasuth on 19/4/2558.
 */
public class ProvinceServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List provinces = SQL.findAll(Province.class);
            Gson gson = new Gson();
            String result = gson.toJson(provinces, Province.class);
            response.getWriter().print(result);
        } catch (Exception ex) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }

    }

    /*@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            SQL sql = new SQL();
            Province province = new Province();
            province.setName(request.getParameter("name"));

            int addId = sql
                    .insert()
                    .into(Province.TABLE_NAME, Province.COLUMN_NAME)
                    .values(province.getName())
                    .exec();
            sql.clear();
            province.setId(addId);

            Gson gson = new Gson();
            response.getWriter().print(gson.toJson(province));
        } catch (Exception ex) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }

    }*/
}
