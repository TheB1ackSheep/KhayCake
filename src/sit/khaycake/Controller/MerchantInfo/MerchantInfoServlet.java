package sit.khaycake.Controller.MerchantInfo;

import com.google.gson.Gson;
import sit.khaycake.database.SQL;
import sit.khaycake.model.MerchantInfo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by Pasuth on 19/4/2558.
 */
public class MerchantInfoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List merchantInfo = SQL.findAll(MerchantInfo.class);
            Gson gson = new Gson();
            String result = gson.toJson(merchantInfo, MerchantInfo.class);
            response.getWriter().print(result);
        } catch (Exception ex) {

        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            MerchantInfo merchantInfo = new MerchantInfo();
            merchantInfo.setName(request.getParameter("name"));
            merchantInfo.setVatId(request.getParameter("VatId"));
            merchantInfo.setPhone(request.getParameter("phone"));
            merchantInfo.setFax(request.getParameter("fax"));
            merchantInfo.setVatValue(Double.parseDouble(request.getParameter("vatValue")));

            SQL sql = new SQL();
            int id = sql
                    .insert()
                    .into(MerchantInfo.TABLE_NAME, MerchantInfo.COLUMN_NAME, MerchantInfo.COLUMN_VAT_ID, MerchantInfo.COLUMN_PHONE,
                            MerchantInfo.COLUMN_FAX, MerchantInfo.COLUMN_VAT_VALUE, MerchantInfo.COLUMN_ADDR_ID)
                    .values(merchantInfo.getName(), merchantInfo.getVatId(), merchantInfo.getPhone(), merchantInfo.getFax(),
                            merchantInfo.getVatValue(), request.getParameter("addressId"))
                    .exec();
            sql.clear();
            merchantInfo.setId(id);
            Gson gson = new Gson();
            response.getWriter().print(gson.toJson(merchantInfo));
        } catch (Exception ex) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }

    }
}
