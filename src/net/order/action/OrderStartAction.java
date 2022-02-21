package net.order.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.book.db.BookBean;
import net.cart.db.CartBean;
import net.cart.db.CartDAO;
import net.member.db.MemberBean;
import net.member.db.MemberDAO;

public class OrderStartAction implements Action{
	@SuppressWarnings("unchecked")
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
		
		List<Object> orderinfo=new ArrayList<>();
		String orderType=request.getParameter("orderType");
		
		if(orderType.equals("fromBookDetail")){
			orderinfo.add(Integer.parseInt(request.getParameter("bookno")));
			orderinfo.add(request.getParameter("bookimage"));
			orderinfo.add(request.getParameter("bookname"));
			orderinfo.add(Integer.parseInt(request.getParameter("amount")));
			orderinfo.add(Integer.parseInt(request.getParameter("price")));
			
			request.setAttribute("orderType", "fromBookDetail");
			request.setAttribute("orderinfo", orderinfo);
			request.setAttribute("totalPrice", request.getParameter("price"));
		}else{
			CartDAO cartdao=new CartDAO();
			HashMap<String, Object> map = new HashMap<String, Object>();
			map = cartdao.getCartList(id);
			List<CartBean> cartlist=(ArrayList<CartBean>)map.get("cartlist");
			List<BookBean> booklist=(ArrayList<BookBean>)map.get("booklist");
			
			request.setAttribute("orderType", "fromCart");
			request.setAttribute("cartlist", cartlist);
			request.setAttribute("booklist", booklist);
			int totlaPrice = 0;
			for(BookBean bookbean : booklist) {
				totlaPrice += bookbean.getBOOK_PRICE();
			}
			request.setAttribute("totalPrice", totlaPrice);
		}
		
		MemberDAO memberdao=new MemberDAO();
		MemberBean member=(MemberBean)memberdao.getMember(id);
		request.setAttribute("member", member);
		
		forward.setRedirect(false);
		forward.setPath("./book_order/book_buy.jsp");
		return forward;
	}
}