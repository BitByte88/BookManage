package net.cart.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CartFrontController extends HttpServlet {
	static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String RequestURI = request.getRequestURI();
		String contextPath = request.getContextPath();
		String command = RequestURI.substring(contextPath.length());
		ForwardService forward = null;
		CartFrontService cartFrontService = new CartFrontService();
		// カート画面
		if (command.equals("/CartList.cart")) {
			forward = cartFrontService.CartListAction(request, response);
		}
		// カート情報登録処理
		else if (command.equals("/CartAdd.cart")) {
			try {
				forward = cartFrontService.CartAddAction(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// カート情報削除処理
		else if (command.equals("/CartDelete.cart")) {
			forward = cartFrontService.CartDeleteAction(request, response);
		}
		if (forward != null) {
			if (forward.isRedirect()) {
				response.sendRedirect(forward.getPath());
			} else {
				RequestDispatcher dispatcher = request.getRequestDispatcher(forward.getPath());
				dispatcher.forward(request, response);
			}
		}
	}

}