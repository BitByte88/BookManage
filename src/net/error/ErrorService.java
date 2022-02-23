package net.error;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.error.ForwardService;


public class ErrorService implements Action {
	public ForwardService execute(HttpServletRequest request, HttpServletResponse response) {
		ForwardService forward = new ForwardService();
		forward.setPath("./error/error.jsp");
		forward.setRedirect(true);
		return forward;
	}
}