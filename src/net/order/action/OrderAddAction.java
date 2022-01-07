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
			PrintWriter out=response.getWriter();
			out.println("<script>");
			out.println("history.book(-1);");
			out.println("</script>");
			out.close();
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
		order.setORDER_RECEIVE_NAME(
				request.getParameter("ORDER_RECEIVE_NAME"));
		order.setORDER_RECEIVE_PHONE(
				request.getParameter("ORDER_RECEIVE_PHONE"));
		order.setORDER_RECEIVE_MOBILE(
				request.getParameter("ORDER_RECEIVE_MOBILE"));
		order.setORDER_RECEIVE_ADDR1(
				request.getParameter("ORDER_RECEIVE_ZIPCODE")+
				" "+request.getParameter("ORDER_RECEIVE_ADDR1"));
		order.setORDER_RECEIVE_ADDR2(
				request.getParameter("ORDER_RECEIVE_ADDR2"));
		order.setORDER_MEMO(request.getParameter("ORDER_MEMO"));
		order.setORDER_TRADE_TYPE("�¶����Ա�");
		order.setORDER_TRADE_PAYER(request.getParameter("ORDER_TRADE_PAYER"));
		
		String ordertype=request.getParameter("ordertype");
		if(ordertype.equals("book")){
			cart.setCART_BOOK_NUM(	Integer.parseInt(
						request.getParameter("booknum")));
			cart.setCART_BOOK_AMOUNT(Integer.parseInt(
						request.getParameter("bookamount")));
			cart.setCART_BOOK_SIZE(
						request.getParameter("booksize"));
			cart.setCART_BOOK_COLOR(
						request.getParameter("bookcolor"));
			
			book.setBOOK_NAME(request.getParameter("bookname"));
			book.setBOOK_PRICE(Integer.parseInt(
						request.getParameter("bookprice")));
			
			cartlist.add(cart);
			booklist.add(book);
			bookvector.add(cartlist);
			bookvector.add(booklist);
		}else{
			bookvector=cartdao.getCartList(id);
		}
		
		orderdao.addOrder(order, bookvector);
		
		ActionForward forward=new ActionForward();
		forward.setRedirect(true);
		forward.setPath("./OrderOk.order");
		return forward;
	}
}