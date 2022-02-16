package net.admin.book.action;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.sql.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import net.admin.book.db.*;
public class AdminBookAddAction implements Action {
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) {
		ActionForward forward = new ActionForward();
		AdminBookDAO abookdao= new AdminBookDAO();
		BookBean bookbean = new BookBean();
		int bookNo = abookdao.getBookNo();
		String realPath = "";
		String savePath = "upload";
		int maxSize = 5 * 1024 * 1024;
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("file4", "Thumbnail");
		map.put("file3", "MainImage");
		map.put("file2", "DetailImage1");
		map.put("file1", "DetailImage2");
		realPath = request.getServletContext().getRealPath(savePath);
		//サーバ上の物理的なアップロードパスを取得
		List<String> savefiles=new ArrayList<>();
		try {
			MultipartRequest multi = null;
			multi = new MultipartRequest(request, realPath, maxSize, "UTF-8",
					new DefaultFileRenamePolicy());
			Enumeration<?> files=multi.getFileNames();
			
			while(files.hasMoreElements()){
				String element = (String)files.nextElement();
				String fileName = multi.getFilesystemName(element);
				if(fileName == null) {
					continue;
				}
	            String filepath  = realPath +"/" + fileName ; 
	            Path f = Paths.get(filepath);
	    		String Extension = "";
	    		int i = fileName.lastIndexOf('.');
	    		if (i > 0) {
	    			Extension = fileName.substring(i+1);
	    		}
	    		String imageName = map.get(element);
                String newImage = imageName + "-" + bookNo + "." + Extension;
                Files.move(f, f.resolveSibling(realPath + "/" + imageName + "-" + bookNo + "." + Extension ),StandardCopyOption.REPLACE_EXISTING);                  
				if(files.hasMoreElements()){
					//取得した要素が最後ではない場合
					savefiles.add(newImage+",");
				}else{
					//取得した要素が最後の場合
					savefiles.add(newImage);
				}
			}
			StringBuffer fl=new StringBuffer();
			for(int i=0;i<savefiles.size();i++){
				fl.append(savefiles.get(i));	
			}
			//Thumbnail.jpg,MainImage.jpg,DetailImage1.jpg,DetailImage2.jpg
			bookbean.setBOOK_CATEGORY(multi.getParameter("book_category"));
			bookbean.setBOOK_NAME(multi.getParameter("book_name"));
			bookbean.setBOOK_WRITER(multi.getParameter("book_writer"));
			bookbean.setBOOK_PUBLISHER(multi.getParameter("book_publisher"));
			bookbean.setBOOK_PUBLISHING_DATE(Date.valueOf(multi.getParameter("book_publishing_date")));
			bookbean.setBOOK_PRICE(new BigDecimal(multi.getParameter("book_price")));
			bookbean.setBOOK_ISBN(multi.getParameter("book_isbn"));
			bookbean.setBOOK_CONTENT(multi.getParameter("book_content"));
			bookbean.setBOOK_IMAGE(fl.toString());
			
			int result = abookdao.insertBook(bookbean, bookNo);
			if (result <= 0){
				response.setContentType("text/html;charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('登録を失敗しました。')");
				out.println("history.back()");
				out.println("</script>");
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}		
		forward.setRedirect(true);
		forward.setPath("BookList.adbook");
		return forward;
	}
}