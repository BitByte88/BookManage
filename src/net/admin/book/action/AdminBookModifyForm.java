package net.admin.book.action;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.admin.book.db.*;
public class AdminBookModifyForm implements Action {
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) {
		ActionForward forward=new ActionForward();		
		AdminBookDAO abookdao=new AdminBookDAO();
		BookBean bookbean=new BookBean();		
		String num=request.getParameter("book_no");		
		bookbean=abookdao.getBook(Integer.parseInt(num));		
		request.setAttribute("abb", bookbean);		
		forward.setPath("./adminbook/admin_book_modify.jsp");
		return forward;
	}
}