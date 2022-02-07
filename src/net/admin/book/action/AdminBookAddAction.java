package net.admin.book.action;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
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
		String realPath = "";
		String savePath = "upload";
		int maxSize = 5 * 1024 * 1024;
		realPath = request.getRealPath(savePath);
		//���� ���� �������� ���ε� ��θ� ��� ��
		List savefiles=new ArrayList();
		try {
			MultipartRequest multi = null;
			multi = new MultipartRequest(request, realPath, maxSize, "UTF-8",
					new DefaultFileRenamePolicy());
			//
			Enumeration files=multi.getFileNames();
			while(files.hasMoreElements()){
				String name=(String)files.nextElement();
				//file input box �� �̸����� �ϳ��� ������
				if(files.hasMoreElements()){
					//���� ������ �̸� ���� �̸��� �� ������
					//��, ���� enumeration���� ������ �̸��� ������ ��Ұ� �ƴϸ�
					savefiles.add(multi.getFilesystemName(name)+",");
				}else{
					savefiles.add(multi.getFilesystemName(name));
				}
				//������ ������ �ƴϸ� ���ϸ� �ڿ� , �� �ٿ��� �߰��ϰ�
				//������ �����̸� , �� ������ �ʰ� �߰�
			}
			StringBuffer fl=new StringBuffer();
			for(int i=0;i<savefiles.size();i++){
				fl.append(savefiles.get(i));	
			}
			//main.jpg,image1-1.jpg,image1-2.jpg,image1-3.jpg
			bookbean.setBOOK_CATEGORY(multi.getParameter("book_category"));
			bookbean.setBOOK_NAME(multi.getParameter("book_name"));
			bookbean.setBOOK_CONTENT(multi.getParameter("book_content"));
//			bookbean.setBOOK_SIZE(multi.getParameter("book_size"));
//			bookbean.setBOOK_COLOR(multi.getParameter("book_color"));
//			bookbean.setBOOK_AMOUNT(
//					Integer.parseInt(multi.getParameter("book_amount")));
//			bookbean.setBOOK_PRICE(
//					Integer.parseInt(multi.getParameter("book_price")));
//			bookbean.setBOOK_IMAGE(fl.toString());
//			bookbean.setBOOK_BEST(
//					Integer.parseInt(multi.getParameter("book_best")));
//			
//			int result = abookdao.insertBook(bookbean);
			//MultiPartRequest ��ü ������ �� �̹� ���ε� ó�� �Ǿ���
			//���ε� �� ���Ͽ� ���õ� ���� �Ӽ����� TO ��ü�� ��Ƽ� �Ķ���ͷ� �����ϸ鼭
			//DB�� �Է� �۾��� ��
//			if (result <= 0){
//				response.setContentType("text/html;charset=UTF-8");
//				PrintWriter out = response.getWriter();
//				out.println("<script>");
//				out.println("alert('��Ͻ���')");
//				out.println("history.back()");
//				out.println("</script>");
//				return null;
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}		
		forward.setRedirect(true);
		forward.setPath("BookList.adbook");
		return forward;
	}
}