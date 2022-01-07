package net.cart.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.cart.action.Action;
import net.cart.action.ActionForward;
import net.cart.db.CartDAO;

public class CartListAction implements Action{
	public ActionForward execute(HttpServletRequest request,HttpServletResponse response)
	throws Exception{
		CartDAO cartdao=new CartDAO();
		HttpSession session=request.getSession();
		String id=(String)session.getAttribute("id");
		
		if(id==null){
			PrintWriter out=response.getWriter();
			out.println("<script>");
			out.println("history.book(-1);");
			out.println("</script>");
			out.close();
		}
		
		Vector vector = cartdao.getCartList(id);
		List cartlist=(ArrayList)vector.get(0);
		List booklist=(ArrayList)vector.get(1);
		
		request.setAttribute("cartlist", cartlist);
		request.setAttribute("booklist", booklist);
		
		ActionForward forward=new ActionForward();
		forward.setRedirect(false);
		forward.setPath("./book_order/book_cart.jsp");
		
		return forward;
	}
}
