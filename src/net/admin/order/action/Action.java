package net.admin.order.action;

import javax.servlet.http.*;

public interface Action {
	public ForwardService execute(HttpServletRequest request,HttpServletResponse response) 
	throws Exception;
}
