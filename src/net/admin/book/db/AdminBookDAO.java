package net.admin.book.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
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
	public List getBookList() {
		List booklist = new ArrayList();
		try {
			con = ds.getConnection();
			String sql="select * from book where delete_flag = 0 order by book_no";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				BookBean bookbean = new BookBean();
				bookbean.setBOOK_NO(rs.getInt("book_no"));
				bookbean.setBOOK_CATEGORY(rs.getString("book_category"));
				bookbean.setBOOK_NAME(rs.getString("book_name"));
				bookbean.setBOOK_WRITER(rs.getString("book_writer"));
				bookbean.setBOOK_PUBLISHER(rs.getString("book_publisher"));
				bookbean.setBOOK_PUBLISHING_DATE(rs.getDate("book_publishing_date"));
				bookbean.setBOOK_CONTENT(rs.getString("book_content"));
				bookbean.setBOOK_PRICE(rs.getBigDecimal("book_price"));
				bookbean.setBOOK_IMAGE(rs.getString("book_image"));
				bookbean.setBOOK_ISBN(rs.getString("book_isbn"));
				booklist.add(bookbean);
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
	public BookBean getBook(int num) {
		BookBean abb = null;
		try {
			con = ds.getConnection();
			String sql="select * from book where book_no=? and delete_flag=0";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1,num);
			rs=pstmt.executeQuery();
			
			if(rs.next()){
				abb=new BookBean();
				abb.setBOOK_NO(rs.getInt("book_no"));
				abb.setBOOK_CATEGORY(rs.getString("book_category"));
				abb.setBOOK_NAME(rs.getString("book_name"));
				abb.setBOOK_WRITER(rs.getString("book_writer"));
				abb.setBOOK_PUBLISHER(rs.getString("book_publisher"));
				abb.setBOOK_PUBLISHING_DATE(rs.getDate("book_publishing_date"));
				abb.setBOOK_CONTENT(rs.getString("book_content"));
				abb.setBOOK_PRICE(rs.getBigDecimal("book_price"));
				abb.setBOOK_IMAGE(rs.getString("book_image"));
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
//	public int insertBook(BookBean abb) {
//		int result = 0;
//		int num=0;
//		String sql="select max(book_no) from book";
//		try {
//			con = ds.getConnection();
//			pstmt=con.prepareStatement(sql);
//			rs=pstmt.executeQuery();
//			rs.next();
//			num=rs.getInt(1)+1;
//			sql="insert into book values "+
//			"(?,?,?,?,?,?,?,?,?,?,DATE(SYSDATE()))";
//			pstmt = con.prepareStatement(sql);
//			pstmt.setInt(1, num);
//			pstmt.setString(2, abb.getBOOK_CATEGORY());
//			pstmt.setString(3, abb.getBOOK_NAME());
//			pstmt.setString(4, abb.getBOOK_CONTENT());
//			pstmt.setString(5, abb.getBOOK_SIZE());
//			pstmt.setString(6, abb.getBOOK_COLOR());
//			pstmt.setInt(7, abb.getBOOK_AMOUNT());
//			pstmt.setInt(8, abb.getBOOK_PRICE());
//			pstmt.setString(9, abb.getBOOK_IMAGE());
//			pstmt.setInt(10, abb.getBOOK_BEST());
//			result = pstmt.executeUpdate();
//			} catch (Exception e) {
//			e.printStackTrace();
//		}
//		finally{
//			try{
//				if(rs!=null)rs.close();
//				if(pstmt!=null)pstmt.close();
//				if(con!=null)con.close();
//			}catch(Exception ex) {}
//		}
//		return result;
//	}
//	public int deleteBook(BookBean abb){
//		int result = 0;
//		try {
//			con = ds.getConnection();
//			String sql="delete from book where book_num=?";
//			pstmt = con.prepareStatement(sql);
//			pstmt.setInt(1, abb.getBOOK_NUM());
//			result = pstmt.executeUpdate();
//		} catch (Exception e) {
//			e.getStackTrace();
//		}
//		finally{
//			try{
//				if(pstmt!=null)pstmt.close();
//				if(con!=null)con.close();
//			}catch(Exception ex) {}
//		}
//		return result;
//	}
//	public int modifyBook(BookBean abb) {
//		int result = 0;
//		try {
//			con = ds.getConnection();
//			String sql="update book set "+
//			"book_category=?, book_name=?, book_price=? ,"+
//			"book_color=? ,book_amount=? ,book_size=? ,"+
//			"book_content=?,book_best=? where book_num=?";
//			pstmt = con.prepareStatement(sql);
//			pstmt.setString(1, abb.getBOOK_CATEGORY());
//			pstmt.setString(2, abb.getBOOK_NAME());
//			pstmt.setInt(3, abb.getBOOK_PRICE());
//			pstmt.setString(4, abb.getBOOK_COLOR());
//			pstmt.setInt(5, abb.getBOOK_AMOUNT());
//			pstmt.setString(6, abb.getBOOK_SIZE());
//			pstmt.setString(7, abb.getBOOK_CONTENT());
//			pstmt.setInt(8, abb.getBOOK_BEST());
//			pstmt.setInt(9, abb.getBOOK_NUM());
//			result = pstmt.executeUpdate();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		finally{
//			try{
//				if(pstmt!=null)pstmt.close();
//				if(con!=null)con.close();
//			}catch(Exception ex) {}
//		}
//		return result;
//	}
}