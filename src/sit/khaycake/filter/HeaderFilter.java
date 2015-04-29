package sit.khaycake.filter;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import sit.khaycake.database.ORM;
import sit.khaycake.util.ErrorHandler;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by Falook Glico on 4/26/2015.
 */
@WebFilter(filterName = "HeaderFilter",urlPatterns = "/*")
public class HeaderFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest)req;
        HttpServletResponse response = (HttpServletResponse)resp;



        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, UPDATE");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setHeader("Access-Control-Max-Age", "86400");



        chain.doFilter(request, response);

        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();

        if(session != null){
            ErrorHandler error = (ErrorHandler)session.getAttribute("error");

            if(error != null && error.getExceptions().size() > 0){
                //MyResponseWrapper myResp = new MyResponseWrapper(response);


                out.print((new Gson()).toJson(error));
                error.clear();
                //request.getServletContext().getRequestDispatcher(request.getRequestURI()).forward(request,response);
            }else{
                String result = "";
                Object obj = request.getAttribute("print");
                JsonObject json = new JsonObject();
                if(obj != null){
                    json.addProperty("JSESSIONID", request.getSession().getId());
                    if(!(obj instanceof List)){
                        json.add(obj.getClass().getSimpleName(), (new Gson()).toJsonTree(obj));
                    }else{
                        List<ORM> lists = (List<ORM>)obj;
                        json.add(lists.get(0).getClass().getSimpleName()+"List", (new Gson()).toJsonTree(lists));
                    }
                }

                String keyword = request.getParameter("q");
                if(keyword != null)
                    json.add("keyword",(new Gson()).toJsonTree(keyword));



                out.print(json.toString());
            }
        }

    }

    public void init(FilterConfig config) throws ServletException {

    }

}
