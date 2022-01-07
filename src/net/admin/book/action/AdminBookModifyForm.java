package net.admin.book.action;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.admin.book.db.*;
public class AdminBookModifyForm implements Action {
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) {
		ActionForward forward=new ActionForward();		
		AdminBookDAO abookdao=new AdminBookDAO();
		BookBean abb=new BookBean();		
		String num=request.getParameter("book_num");		
		abb=abookdao.getBook(Integer.parseInt(num));		
		request.setAttribute("agb", abb);		
		forward.setPath("./adminbook/admin_book_modify.jsp");
		return forward;
	}
}