package net.order.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.book.db.BookBean;
import net.cart.db.CartBean;
import net.cart.db.CartDAO;
import net.order.db.OrderBean;
import net.order.db.OrderDAO;

public class OrderAddAction implements Action{
	public ActionForward execute(HttpServletRequest request,HttpServletResponse response) 
	throws Exception{
		request.setCharacterEncoding("UTF-8");
		
		HttpSession session=request.getSession();
		String id=(String)session.getAttribute("id");
		
		if(id==null){
			String referer = request.getHeader("Referer");
			request.getSession().setAttribute("redirectURI", referer);
			ActionForward forward=new ActionForward();
			forward.setRedirect(true);
			forward.setPath("./MemberLogin.member");
			return forward;
		}
		
		Vector bookvector=new Vector();
		
		OrderDAO orderdao=new OrderDAO();
		OrderBean order=new OrderBean();
		
		List cartlist=new ArrayList();
		List booklist=new ArrayList();
		BookBean book=new BookBean();
		CartDAO cartdao=new CartDAO();
		CartBean cart=new CartBean();
		
		order.setORDER_MEMBER_ID(request.getParameter("memberid"));
		order.setORDER_RECEIVE_NAME(request.getParameter("ORDER_RECEIVE_NAME"));
		order.setORDER_RECEIVE_NAME_KANA(request.getParameter("ORDER_RECEIVE_NAME_KANA"));
		order.setORDER_RECEIVE_EMAIL(request.getParameter("ORDER_RECEIVE_EMAIL"));
		order.setORDER_RECEIVE_TEL(request.getParameter("ORDER_RECEIVE_TEL"));
		order.setORDER_RECEIVE_ZIPCODE(request.getParameter("ORDER_RECEIVE_ZIPCODE"));
		order.setORDER_RECEIVE_ADD_1(request.getParameter("ORDER_RECEIVE_ADD_1"));
		order.setORDER_RECEIVE_ADD_2(request.getParameter("ORDER_RECEIVE_ADD_2"));
		order.setORDER_RECEIVE_ADD_3(request.getParameter("ORDER_RECEIVE_ADD_3"));
		order.setORDER_MEMO(request.getParameter("ORDER_MEMO"));
		
		order.setORDER_TRADE_TYPE("オンライン入金");
		
		String ordertype=request.getParameter("ordertype");
		if(ordertype.equals("book")){
			cart.setCART_BOOK_NO(Integer.parseInt(request.getParameter("bookno")));
			cart.setCART_COUNT(Integer.parseInt(request.getParameter("amount")));
			book.setBOOK_NAME(request.getParameter("bookname"));
			book.setBOOK_PRICE(Integer.parseInt(request.getParameter("price")));
			cartlist.add(cart);
			booklist.add(book);
			bookvector.add(cartlist);
			bookvector.add(booklist);
		}else{
			bookvector=cartdao.getCartList(id);
		}
		
		orderdao.addOrder(order, bookvector);
		cartdao.cartClear(id);
		ActionForward forward=new ActionForward();
		forward.setRedirect(true);
		forward.setPath("./OrderOk.order");
		return forward;
	}
}