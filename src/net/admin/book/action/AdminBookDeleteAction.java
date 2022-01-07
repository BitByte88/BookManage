package net.admin.book.action;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.admin.book.db.*;
public class AdminBookDeleteAction implements Action {
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) {	
		ActionForward forward=new ActionForward();
		AdminBookDAO abookdao=new AdminBookDAO();
		BookBean abb= new BookBean();		
		abb.setBOOK_NUM(
				Integer.parseInt(request.getParameter("book_num")));		
		int check=abookdao.deleteBook(abb);
		if(check>0){
			forward.setRedirect(true);
			forward.setPath("BookList.adbook");
		}		
		return forward;
	}
}