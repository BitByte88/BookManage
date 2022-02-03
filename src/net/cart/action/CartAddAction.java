package net.cart.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.cart.db.CartDAO;

public class CartAddAction implements Action{
	public ActionForward execute(HttpServletRequest request,HttpServletResponse response) 
	throws Exception{
		CartDAO cartdao=new CartDAO();
		HttpSession session = request.getSession();
		String id=(String)session.getAttribute("id");
		
		if(id==null){
			String referer = request.getHeader("Referer");
			request.getSession().setAttribute("redirectURI", referer);
			ActionForward forward=new ActionForward();
			forward.setRedirect(true);
			forward.setPath("./MemberLogin.member");
			return forward;			
		}
		
		int num=Integer.parseInt(request.getParameter("booknum"));
		int amount=Integer.parseInt(request.getParameter("amount"));
		String size=request.getParameter("size");
		String color=request.getParameter("color");
		
		String item=request.getParameter("item");
		String gr_book_num=request.getParameter("gr_book_num");
		String isitem=request.getParameter("isitem");
		
		cartdao.cartAdd(id,num,amount,size,color);
		
		ActionForward forward=new ActionForward();
		forward.setRedirect(true);
		forward.setPath("./CartList.cart?item="+item+
				"&gr_book_num="+gr_book_num+"&isitem="+isitem);
		return forward;
	}
}