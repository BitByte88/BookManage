package net.order.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class OrderFrontController extends HttpServlet {
	private static final long serialVersionUID = 8291740938003137142L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String RequestURI = request.getRequestURI();
		String contextPath = request.getContextPath();
		String command = RequestURI.substring(contextPath.length());
		ForwardService forward = null;
		OrderFrontService orderFrontService = new OrderFrontService();
		//注文詳細画面
		if (command.equals("/OrderStart.order")) {
			try {
				forward = orderFrontService.OrderStartAction(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		//注文履歴画面
		else if (command.equals("/OrderList.order")) {
			forward = orderFrontService.OrderListAction(request, response);
		}
		//注文登録処理
		else if (command.equals("/OrderAdd.order")) {
			try {
				forward = orderFrontService.OrderAddAction(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		//注文完了画面
		else if (command.equals("/OrderOk.order")){
			forward=new ForwardService();
			forward.setRedirect(false);
			forward.setPath("./bookOrder/book_order_ok.jsp");
		}
       if(forward != null){
		if (forward.isRedirect()) {
			response.sendRedirect(forward.getPath());
		} else {
			RequestDispatcher dispatcher = request.getRequestDispatcher(forward
					.getPath());
			dispatcher.forward(request, response);
		}
       }
	}

}