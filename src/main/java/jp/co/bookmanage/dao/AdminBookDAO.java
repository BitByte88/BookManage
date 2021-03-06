package main.java.jp.co.bookmanage.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import main.java.jp.co.bookmanage.dto.BookDTO;

public class AdminBookDAO {
	DataSource ds;
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	public AdminBookDAO(){
		try{
			Context initCtx=new InitialContext();
			   Context envCtx=(Context)initCtx.lookup("java:comp/env");
			   ds=(DataSource)envCtx.lookup("jdbc/mysql");
			  
		}catch(Exception ex){
			System.out.println("DB接続エラー：　" + ex);
			return;
		}
	}
	//図書リスト取得
	public List<BookDTO> getBookList() {
		List<BookDTO> booklist = new ArrayList<>();
		try {
			con = ds.getConnection();
			String sql="select * from book where delete_flag = 0 order by book_no";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				BookDTO bookdto = new BookDTO();
				//図書NO
				bookdto.setBOOK_NO(rs.getInt("book_no"));
				//カテゴリー
				bookdto.setBOOK_CATEGORY(rs.getString("book_category"));
				//書名
				bookdto.setBOOK_NAME(rs.getString("book_name"));
				//著者
				bookdto.setBOOK_WRITER(rs.getString("book_writer"));
				//出版社
				bookdto.setBOOK_PUBLISHER(rs.getString("book_publisher"));
				//発行日
				bookdto.setBOOK_PUBLISHING_DATE(rs.getDate("book_publishing_date"));
				//図書内容
				bookdto.setBOOK_CONTENT(rs.getString("book_content"));
				//販売価格
				bookdto.setBOOK_PRICE(rs.getInt("book_price"));
				//イメージ
				bookdto.setBOOK_IMAGE(rs.getString("book_image"));
				//ISBNコード
				bookdto.setBOOK_ISBN(rs.getString("book_isbn"));
				booklist.add(bookdto);
			}
		} catch (Exception e) {
			e.getStackTrace();
		}
		finally{
			try{
				if(rs!=null)rs.close();
				if(pstmt!=null)pstmt.close();
				if(con!=null)con.close();
			}catch(Exception ex) {}
		}
		return booklist;
	}
	//図書情報取得
	public BookDTO getBook(int num) {
		BookDTO abb = null;
		try {
			con = ds.getConnection();
			String sql="select * from book where book_no=? and delete_flag=0";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1,num);
			rs=pstmt.executeQuery();
			
			if(rs.next()){
				abb=new BookDTO();
				//図書NO
				abb.setBOOK_NO(rs.getInt("book_no"));
				//カテゴリー
				abb.setBOOK_CATEGORY(rs.getString("book_category"));
				//書名
				abb.setBOOK_NAME(rs.getString("book_name"));
				//著者
				abb.setBOOK_WRITER(rs.getString("book_writer"));
				//出版社
				abb.setBOOK_PUBLISHER(rs.getString("book_publisher"));
				//発行日
				abb.setBOOK_PUBLISHING_DATE(rs.getDate("book_publishing_date"));
				//図書内容
				abb.setBOOK_CONTENT(rs.getString("book_content"));
				//販売価格
				abb.setBOOK_PRICE(rs.getInt("book_price"));
				//イメージ
				abb.setBOOK_IMAGE(rs.getString("book_image"));
				//ISBNコード
				abb.setBOOK_ISBN(rs.getString("book_isbn"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			try{
				if(rs!=null)rs.close();
				if(pstmt!=null)pstmt.close();
				if(con!=null)con.close();
			}catch(Exception ex) {}
		}

		return abb;
	}
	//最後の図書NO取得
	public int getBookNo() {
		int num = 0;
		String sql="select max(book_no) from book";
		try {
		con = ds.getConnection();
		pstmt=con.prepareStatement(sql);
		rs=pstmt.executeQuery();
		rs.next();
		//最後の図書NO+1
		num=rs.getInt(1)+1;	
		} catch (Exception e) {
			e.printStackTrace();
		} 
		finally{
			try{
				if(rs!=null)rs.close();
				if(pstmt!=null)pstmt.close();
				if(con!=null)con.close();
			}catch(Exception ex) {}
		}
		return num;
	}
	//図書登録
	public int insertBook(BookDTO abb, int num) {
		int result = 0;
		try {
			con = ds.getConnection();
			String sql="insert into book values "+
			"(?,?,?,?,?,?,?,?,?,?,0,1,NOW(),1,NOW())";
			pstmt = con.prepareStatement(sql);
			//図書NO
			pstmt.setInt(1, num);
			//カテゴリー
			pstmt.setString(2, abb.getBOOK_CATEGORY());
			//書名			
			pstmt.setString(3, abb.getBOOK_NAME());
			//著者
			pstmt.setString(4, abb.getBOOK_WRITER());
			//出版社
			pstmt.setString(5, abb.getBOOK_PUBLISHER());
			//発行日
			pstmt.setDate(6, abb.getBOOK_PUBLISHING_DATE());
			//図書内容
			pstmt.setString(7, abb.getBOOK_CONTENT());
			//販売価格
			pstmt.setInt(8, abb.getBOOK_PRICE());
			//イメージ
			pstmt.setString(9, abb.getBOOK_IMAGE());
			//ISBNコード
			pstmt.setString(10, abb.getBOOK_ISBN());
			result = pstmt.executeUpdate();
			} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			try{
				if(pstmt!=null)pstmt.close();
				if(con!=null)con.close();
			}catch(Exception ex) {}
		}
		return result;
	}
	//図書情報削除
	public int deleteBook(BookDTO abb){
		int result = 0;
		try {
			con = ds.getConnection();
			String sql="update book set delete_flag=1 where book_no=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, abb.getBOOK_NO());
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.getStackTrace();
		}
		finally{
			try{
				if(pstmt!=null)pstmt.close();
				if(con!=null)con.close();
			}catch(Exception ex) {}
		}
		return result;
	}
	//図書情報変更
	public int modifyBook(BookDTO abb, int num) {
		int result = 0;
		try {
			con = ds.getConnection();
			String sql="update book set "+
			"book_category=?, book_name=?, book_writer=?, book_publisher=?, " + 
			"book_publishing_date=?, book_content=?, book_price=?, " +
			"book_image=? ,book_isbn=?, update_date=NOW() " +
			"where book_no=? and delete_flag=0";
			pstmt = con.prepareStatement(sql);
			//カテゴリー
			pstmt.setString(1, abb.getBOOK_CATEGORY());
			//書名
			pstmt.setString(2, abb.getBOOK_NAME());
			//著者
			pstmt.setString(3, abb.getBOOK_WRITER());
			//出版社
			pstmt.setString(4, abb.getBOOK_PUBLISHER());
			//発行日
			pstmt.setDate(5, abb.getBOOK_PUBLISHING_DATE());
			//図書内容
			pstmt.setString(6, abb.getBOOK_CONTENT());
			//販売価格
			pstmt.setInt(7, abb.getBOOK_PRICE());
			//イメージ
			pstmt.setString(8, abb.getBOOK_IMAGE());
			//ISBNコード
			pstmt.setString(9, abb.getBOOK_ISBN());
			//図書NO
			pstmt.setInt(10, num);
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			try{
				if(pstmt!=null)pstmt.close();
				if(con!=null)con.close();
			}catch(Exception ex) {}
		}
		return result;
	}
}