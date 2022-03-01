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
		//図書リスト取得
		List<BookBean> list=abookdao.getBookList();
		//取得した図書リストを設定
		request.setAttribute("list",list);
		//図書リスト画面に遷移する。
		forward.setPath("./adminBook/admin_book_list.jsp");
		return forward;
	}
}