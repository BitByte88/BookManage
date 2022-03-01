package net.admin.book.action;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.admin.book.db.*;
public class AdminBookDeleteAction implements Action {
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) {	
		ActionForward forward=new ActionForward();
		AdminBookDAO abookdao=new AdminBookDAO();
		BookBean bookbean= new BookBean();	
		bookbean.setBOOK_NO(
				Integer.parseInt(request.getParameter("book_no")));		
		//図書情報削除
		int check=abookdao.deleteBook(bookbean);
		//図書情報削除が「成功」の場合、図書リスト画面に遷移する。
		if(check>0){
			forward.setRedirect(true);
			forward.setPath("BookList.adbook");
		}		
		return forward;
	}
}