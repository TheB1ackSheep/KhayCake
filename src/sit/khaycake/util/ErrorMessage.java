package sit.khaycake.util;


import javax.servlet.http.HttpSession;

/**
 * Created by INT303 on 24/4/2558.
 */
public class ErrorMessage extends JsonMessage{    
	private boolean error;

	@Override
	public void setMessage(Object msg) {
		super.setMessage(msg);
		error = true;
	}

	public boolean hasError(){
		return error;
	}



	public ErrorMessage(HttpSession session) {
		super(session);
	}

	public boolean getError(){
		return this.error;
	}
	
	public void setError(boolean error){
		this.error = error;
	}
}