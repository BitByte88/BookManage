package net.admin.book.action;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.admin.book.db.*;
public class AdminBookModifyForm implements Action {
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) {
		ActionForward forward=new ActionForward();		
		AdminBookDAO abookdao=new AdminBookDAO();
		BookBean bookbean=new BookBean();	
		//図書NO取得
		String num=request.getParameter("book_no");
		//図書情報取得
		bookbean=abookdao.getBook(Integer.parseInt(num));
		//イメージ名区分（区分字「,」）
		String[] imageStr = bookbean.getBOOK_IMAGE().split(",");
		HashMap<String, String> imageMap = new HashMap<String, String>();
		for(String imageName : imageStr) {
			//サムネイル用画像を設定
			if(imageName.contains("Thumbnail")) {
				imageMap.put("thumbnail","./upload/" + imageName);
			}
			//メイン画像を設定
			if(imageName.contains("MainImage")) {
				imageMap.put("mainImage","./upload/" + imageName);
			}
			//詳細画像1を設定
			if(imageName.contains("DetailImage1")) {
				imageMap.put("detailImage1","./upload/" + imageName);
			}
			//詳細画像2を設定
			if(imageName.contains("DetailImage2")) {
				imageMap.put("detailImage2","./upload/" + imageName);
			}
		}
		request.setAttribute("abb", bookbean);	
		request.setAttribute("imageMap", imageMap);
		//図書情報変更画面に遷移する。
		forward.setPath("./adminBook/admin_book_modify.jsp");
		return forward;
	}
}