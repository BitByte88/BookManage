package net.error;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.error.ForwardService;

class ForwardService {
	private boolean isRedirect=false;
	private String path=null;
	
	public boolean isRedirect(){
		return isRedirect;
	}
	
	public String getPath(){
		return path;
	}
	
	public void setRedirect(boolean b){
		isRedirect=b;
	}
	
	public void setPath(String string){
		path=string;
	}
}

public class ErrorService implements Action {
	public ForwardService execute(HttpServletRequest request, HttpServletResponse response) {
		ForwardService forward = new ForwardService();
		forward.setPath("./error/error.jsp");
		forward.setRedirect(true);
		return forward;
	}
}