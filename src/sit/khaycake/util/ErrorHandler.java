package sit.khaycake.util;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by INT303 on 24/4/2558.
 */
public class ErrorHandler {
    private boolean error;
    private List<String> message;
    private transient List<Exception> exceptions;
    private transient HttpSession session;

    public List<Exception> getExceptions() {
        return exceptions;
    }

    public List<String> getMessage(){
        List<String> msg = null;
        if(this.exceptions != null)
            msg = new ArrayList<>();
        for(Exception m : exceptions)
            msg.add(m.getMessage());
        return msg;
    }

    public ErrorHandler(HttpSession session) {
        this.session = session;
        ErrorHandler error = ((ErrorHandler)session.getAttribute("error"));
        if(error != null)
            this.exceptions = error.getExceptions();
    }

    public void addException(Exception exception) {
        error = true;
        if(this.exceptions == null)
            this.exceptions = new ArrayList<>();
        if(this.message == null)
            this.message = new ArrayList<>();
        this.exceptions.add(exception);
        this.message.add(exception.getMessage());
        this.save();
    }

    public boolean hasError(){
        ErrorHandler error = (ErrorHandler)this.session.getAttribute("error");
        return error != null && error.getExceptions() != null && error.getExceptions().size() > 0;
    }

    public void save(){
        this.session.setAttribute("error",this);
    }

    public void clear(){
        this.session.removeAttribute("error");
    }

}
