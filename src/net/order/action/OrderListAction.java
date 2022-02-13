package net.order.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.order.db.OrderDAO;
import net.order.db.OrderBean;

public class OrderListAction implements Action{
	public ActionForward execute(HttpServletRequest request,HttpServletResponse response){
		ActionForward forward=new ActionForward();
		
		HttpSession session = request.getSession();
		String id =(String)session.getAttribute("id");
		
		if(id==null){			
			String referer = request.getHeader("Referer");
			request.getSession().setAttribute("redirectURI", referer);
			forward.setRedirect(true);
			forward.setPath("./MemberLogin.member");
			return forward;		
		}
		
		int page=1;
		int getOrderNo = 0;
		int totalmoney= 0;
		OrderDAO orderdao=new OrderDAO();
		List<OrderBean> book_order_list=new ArrayList<OrderBean>();	
		if(request.getParameter("page")!=null){
			page=Integer.parseInt(request.getParameter("page"));
		}
		try{
			int ordercount=orderdao.getOrderCount(id);

			book_order_list = orderdao.getOrderList(page,id);
			if(book_order_list != null && book_order_list.size() != 0) {
				getOrderNo = book_order_list.get(0).getORDER_NO();
				totalmoney=orderdao.getOrderSumMoney(id,getOrderNo);
			}
			
			
			int startpage = (((int) ((double)page / 10 + 0.9)) - 1) * 10 + 1;
			int endpage = ordercount;
			if (endpage>startpage+10-1) endpage=startpage+10-1;
			
			request.setAttribute("page", page);
			request.setAttribute("maxpage", ordercount);
			request.setAttribute("startpage", startpage);
			request.setAttribute("endpage", endpage);
			request.setAttribute("ordercount", ordercount);
			request.setAttribute("totalmoney", totalmoney);
			request.setAttribute("book_order_list", book_order_list);
			
			forward.setRedirect(false);
			forward.setPath("./book_order/book_my_order.jsp");
			return forward;
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	 } 
}