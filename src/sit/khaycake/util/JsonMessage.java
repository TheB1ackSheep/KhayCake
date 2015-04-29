package sit.khaycake.util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by INT303 on 24/4/2558.
 */
public class JsonMessage {    
	
	public static final String SESSION_NAME = "output";
    protected Object message;
	protected String sessionId;
	protected transient HttpSession session;

	public HttpSession getSession(){
		return this.session;
	}

	public void setSession(HttpSession session){
		this.session = session;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public JsonMessage(HttpSession session) {
        this.session = session;
        JsonMessage message = ((JsonMessage)session.getAttribute("message"));
    }

	public Object getMessage(){
		return this.message;
	}

	public void setMessage(Object message) {
		Gson gson = new Gson();
		JsonObject json = new JsonObject();
		json.add((message instanceof List?((List) message).get(0).getClass().getSimpleName()+"List":
		message.getClass().getSimpleName()),gson.toJsonTree(message));
		this.message = json;
		this.save();
	}

	public static JsonMessage getMessage(HttpSession session){
		return (JsonMessage)session.getAttribute(SESSION_NAME);
	}

	public void save(){
		this.session.setAttribute(SESSION_NAME,this);
	}
	
	public void clear(){
		this.session.removeAttribute(SESSION_NAME);
	}
  
}
