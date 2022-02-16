package net.cart.action;


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
		
		int num=Integer.parseInt(request.getParameter("bookno"));
		int amount=Integer.parseInt(request.getParameter("amount"));
		
		cartdao.cartAdd(id,num,amount);
		
		ActionForward forward=new ActionForward();
		forward.setRedirect(true);
		forward.setPath("./CartList.cart");
		return forward;
	}
}