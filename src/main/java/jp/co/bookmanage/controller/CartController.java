package main.java.jp.co.bookmanage.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import main.java.jp.co.bookmanage.common.ForwardService;
import main.java.jp.co.bookmanage.service.CartService;

public class CartController extends HttpServlet {
	private static final long serialVersionUID = -6705331725082999477L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String RequestURI = request.getRequestURI();
		String contextPath = request.getContextPath();
		String command = RequestURI.substring(contextPath.length());
		ForwardService forward = null;
		CartService cartService = new CartService();
		// カート画面
		if (command.equals("/CartList.cart")) {
			forward = cartService.CartListAction(request, response);
		}
		// カート情報登録処理
		else if (command.equals("/CartAdd.cart")) {
			try {
				forward = cartService.CartAddAction(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// カート情報削除処理
		else if (command.equals("/CartDelete.cart")) {
			forward = cartService.CartDeleteAction(request, response);
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