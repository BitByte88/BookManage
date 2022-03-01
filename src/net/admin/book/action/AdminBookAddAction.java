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
		//最大サイズ指定
		int maxSize = 5 * 1024 * 1024;
		//アップロードファイルの指定ファイル名Mapを設定
		HashMap<String, String> map = new HashMap<String, String>();
		//サムネイル用画像
		map.put("file4", "Thumbnail");
		//メイン画像
		map.put("file3", "MainImage");
		//詳細画像1
		map.put("file2", "DetailImage1");
		//詳細画像2
		map.put("file1", "DetailImage2");
		//サーバ上の物理的なアップロードパスを取得
		realPath = request.getServletContext().getRealPath(savePath);
		List<String> savefiles=new ArrayList<>();
		try {
			MultipartRequest multi = null;
			//画面から登録された情報を取得（ファイルアップロード含め）
			multi = new MultipartRequest(request, realPath, maxSize, "UTF-8",
					new DefaultFileRenamePolicy());
			//アップロードファイル取得
			Enumeration<?> files=multi.getFileNames();
			//アップロードファイル件数分、以下の処理を行う。
			while(files.hasMoreElements()){
				String element = (String)files.nextElement();
				//ファイル名取得
				String fileName = multi.getFilesystemName(element);
				if(fileName == null) {
					continue;
				}
				//ファイルパス設定
	            String filepath  = realPath +"/" + fileName ; 
	            Path f = Paths.get(filepath);
	    		String Extension = "";
	    		//拡張子取得
	    		int i = fileName.lastIndexOf('.');
	    		if (i > 0) {
	    			Extension = fileName.substring(i+1);
	    		}
	    		//指定ファイル名取得
	    		String imageName = map.get(element);
	    		String newImage = imageName + "-" + bookNo + "." + Extension;
                //アップロードファイルを指定ファイル名に変換
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
			//カテゴリー
			bookbean.setBOOK_CATEGORY(multi.getParameter("book_category"));
			//書名
			bookbean.setBOOK_NAME(multi.getParameter("book_name"));
			//著者
			bookbean.setBOOK_WRITER(multi.getParameter("book_writer"));
			//出版社
			bookbean.setBOOK_PUBLISHER(multi.getParameter("book_publisher"));
			//発行日
			bookbean.setBOOK_PUBLISHING_DATE(Date.valueOf(multi.getParameter("book_publishing_date")));
			//販売価格
			bookbean.setBOOK_PRICE(new BigDecimal(multi.getParameter("book_price")));
			//ISBNコード
			bookbean.setBOOK_ISBN(multi.getParameter("book_isbn"));
			//図書内容
			bookbean.setBOOK_CONTENT(multi.getParameter("book_content"));
			//イメージ
			bookbean.setBOOK_IMAGE(fl.toString());
			//図書情報登録
			int result = abookdao.insertBook(bookbean, bookNo);
			//図書情報登録が「失敗」の場合、エラーメッセージを図書登録画面に戻って表示する。
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
		//図書情報登録が「成功」の場合、図書リスト画面に遷移する。
		forward.setRedirect(true);
		forward.setPath("BookList.adbook");
		return forward;
	}
}