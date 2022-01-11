package net.admin.book.action;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.admin.book.db.*;
public class AdminBookListAction implements Action {
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) {
		AdminBookDAO abookdao=new AdminBookDAO();
		BookBean bookbean=new BookBean();
		
		ActionForward forward=new ActionForward();
		
		ArrayList list=(ArrayList)abookdao.getBookList();
		
		request.setAttribute("list",list);
		
		forward.setPath("./adminbook/admin_book_list.jsp");
		return forward;
	}
}