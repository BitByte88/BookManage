package main.java.jp.co.bookmanage.common;

import javax.servlet.http.*;

public interface Action {
	public ForwardService execute(HttpServletRequest request,HttpServletResponse response) 
	throws Exception;
}
