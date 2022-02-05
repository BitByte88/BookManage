package net.order.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.member.db.MemberDAO;
import net.cart.db.CartDAO;
import net.member.db.MemberBean;

public class OrderStartAction implements Action{
	public ActionForward execute(HttpServletRequest request,HttpServletResponse response) 
	throws Exception{
		ActionForward forward=new ActionForward();
		
		HttpSession session=request.getSession();
		String id=(String)session.getAttribute("id");
		if(id==null){
			String referer = request.getHeader("Referer");
			request.getSession().setAttribute("redirectURI", referer);
			forward.setRedirect(true);
			forward.setPath("./MemberLogin.member");
			return forward;		
		}
		
		request.setCharacterEncoding("UTF-8");
		
		List orderinfo=new ArrayList();
		String order=request.getParameter("order");
		
		if(order.equals("book")){
			orderinfo.add(Integer.parseInt(request.getParameter("bookno")));
			orderinfo.add(request.getParameter("bookname"));
			orderinfo.add(Integer.parseInt(request.getParameter("amount")));
			orderinfo.add(Integer.parseInt(request.getParameter("price")));
			orderinfo.add(request.getParameter("bookimage"));
			
			request.setAttribute("ordertype", "book");
			request.setAttribute("orderinfo", orderinfo);
		}else{
			CartDAO cartdao=new CartDAO();
			Vector vector=cartdao.getCartList(id);
			List cartlist=(ArrayList)vector.get(0);
			List booklist=(ArrayList)vector.get(1);
			
			request.setAttribute("ordertype", "cart");
			request.setAttribute("cartlist", cartlist);
			request.setAttribute("booklist", booklist);
		}
		
		MemberDAO memberdao=new MemberDAO();
		MemberBean member=(MemberBean)memberdao.getMember(id);
		request.setAttribute("member", member);
		
		forward.setRedirect(false);
		forward.setPath("./book_order/book_buy.jsp");
		return forward;
	}
}