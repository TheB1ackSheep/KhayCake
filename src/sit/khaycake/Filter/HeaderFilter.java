package sit.khaycake.Filter;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import sit.khaycake.util.ErrorMessage;
import sit.khaycake.util.JsonMessage;
import sit.khaycake.util.SuccessMessage;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Falook Glico on 4/26/2015.
 */
public class HeaderFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;


        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, UPDATE");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setHeader("Access-Control-Max-Age", "86400");

        chain.doFilter(request, response);

        if (!response.isCommitted()) {

            PrintWriter out = response.getWriter();
            HttpSession session = request.getSession();

            if (session != null) {
                JsonMessage msg = JsonMessage.getMessage(session);
                Gson gson = new Gson();
                JsonElement json = null;

                if (msg != null) {
                    msg.setSessionId(session.getId());
                    if (msg instanceof ErrorMessage) {
                        ErrorMessage error = (ErrorMessage) msg;
                        json = gson.toJsonTree(error);
                    } else if (msg instanceof SuccessMessage) {
                        SuccessMessage success = (SuccessMessage) msg;
                        json = gson.toJsonTree(success);
                    }
                    msg.clear();
                    out.print(json.toString());
                }

            }
        }


    }

    public void init(FilterConfig config) throws ServletException {

    }

}
