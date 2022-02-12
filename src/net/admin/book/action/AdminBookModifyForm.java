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
		String num=request.getParameter("book_no");		
		bookbean=abookdao.getBook(Integer.parseInt(num));
		//DetailImage2-5.jpg,DetailImage1-5.jpg,MainImage-5.jpg,Thumbnail-5.jpg
		String[] imageStr = bookbean.getBOOK_IMAGE().split(",");
		HashMap<String, String> imageMap = new HashMap<String, String>();
		for(String imageName : imageStr) {
			if(imageName.contains("Thumbnail")) {
				imageMap.put("thumbnail","./upload/" + imageName);
			}
			if(imageName.contains("MainImage")) {
				imageMap.put("mainImage","./upload/" + imageName);
			}
			if(imageName.contains("DetailImage1")) {
				imageMap.put("detailImage1","./upload/" + imageName);
			}
			if(imageName.contains("DetailImage2")) {
				imageMap.put("detailImage2","./upload/" + imageName);
			}
		}
		request.setAttribute("abb", bookbean);	
		request.setAttribute("imageMap", imageMap);
		forward.setPath("./adminbook/admin_book_modify.jsp");
		return forward;
	}
}