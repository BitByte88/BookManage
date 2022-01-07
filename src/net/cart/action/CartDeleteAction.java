package net.cart.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.cart.db.CartDAO;

public class CartDeleteAction implements Action{
	public ActionForward execute(HttpServletRequest request,HttpServletResponse response)
	throws Exception{
		CartDAO cartdao=new CartDAO();
		HttpSession session = request.getSession();
		String id=(String)session.getAttribute("id");
		
		if(id==null){ //�α��ε��� �ʾ��� ���.
			PrintWriter out=response.getWriter();
			out.println("<script>");
			out.println("history.book(-1);");
			out.println("</script>");
			out.close();
		}
		String num=request.getParameter("num");
		if(num==null){
			return null;
		}
		
		cartdao.cartRemove(Integer.parseInt(num));
		
		ActionForward forward=new ActionForward();
		forward.setRedirect(true);
		forward.setPath("./CartList.cart");
		return forward;
	}
}