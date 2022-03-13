package main.java.jp.co.bookmanage.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import main.java.jp.co.bookmanage.common.ForwardService;
import main.java.jp.co.bookmanage.service.AdminOrderService;

public class AdminOrderController extends HttpServlet {
	private static final long serialVersionUID = -4826023681180085537L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String RequestURI = request.getRequestURI();
		String contextPath = request.getContextPath();
		String command = RequestURI.substring(contextPath.length());
		ForwardService forward = null;
		AdminOrderService adminOrderService = new AdminOrderService();
		// 注文リスト画面
		if (command.equals("/AdminOrderList.adorder")) {
			forward = adminOrderService.AdminOrderListAction(request,response);
		}
		// 注文情報修正画面
		else if (command.equals("/AdminOrderDetail.adorder")) {
			try {
				forward = adminOrderService.AdminOrderDetailAction(request,response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// 注文情報修正処理
		else if (command.equals("/AdminOrderModify.adorder")) {
			try {
				forward = adminOrderService.AdminOrderModifyAction(request,response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// 注文情報削除処理
		else if (command.equals("/AdminOrderDelete.adorder")) {
			forward = adminOrderService.AdminOrderDeleteAction(request,response);
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