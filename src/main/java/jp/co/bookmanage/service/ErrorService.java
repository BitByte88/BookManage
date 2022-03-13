package main.java.jp.co.bookmanage.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import main.java.jp.co.bookmanage.common.Action;
import main.java.jp.co.bookmanage.common.ForwardService;

public class ErrorService implements Action {
	public ForwardService execute(HttpServletRequest request, HttpServletResponse response) {
		ForwardService forward = new ForwardService();
		forward.setPath("./error/error.jsp");
		forward.setRedirect(true);
		return forward;
	}
}