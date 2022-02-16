package net.admin.book.action;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.admin.book.db.*;
public class AdminBookListAction implements Action {
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) {
		AdminBookDAO abookdao=new AdminBookDAO();
		
		ActionForward forward=new ActionForward();
		
		List<BookBean> list=abookdao.getBookList();
		
		request.setAttribute("list",list);
		
		forward.setPath("./adminbook/admin_book_list.jsp");
		return forward;
	}
}