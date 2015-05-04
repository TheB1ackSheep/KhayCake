package sit.khaycake.util;


import javax.servlet.http.HttpSession;

/**
 * Created by INT303 on 24/4/2558.
 */
public class SuccessMessage extends JsonMessage {
    private boolean success;

    @Override
    public void setMessage(Object msg) {
        super.setMessage(msg);
        success = true;
    }

    public SuccessMessage(HttpSession session) {
        super(session);
    }

    public boolean getSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}