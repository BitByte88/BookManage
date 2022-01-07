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
			System.out.println("DB ���� ���� : " + ex);
			return;
		}
	}
	public List getBookList() {
		List booklist = new ArrayList();
		try {
			con = ds.getConnection();
			String sql="select * from book order by book_num";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				BookBean abb = new BookBean();
				abb.setBOOK_NUM(rs.getInt("book_num"));
				abb.setBOOK_CATEGORY(rs.getString("book_category"));
				abb.setBOOK_NAME(rs.getString("book_name"));
				abb.setBOOK_CONTENT(rs.getString("book_content"));
				abb.setBOOK_SIZE(rs.getString("book_size"));
				abb.setBOOK_COLOR(rs.getString("book_color"));
				abb.setBOOK_AMOUNT(rs.getInt("book_amount"));
				abb.setBOOK_PRICE(rs.getInt("book_price"));
				abb.setBOOK_IMAGE(rs.getString("book_image"));
				abb.setBOOK_BEST(rs.getInt("book_best"));
				abb.setBOOK_DATE(rs.getString("book_date"));
				booklist.add(abb);
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
			String sql="select * from book where book_num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1,num);
			rs=pstmt.executeQuery();
			
			if(rs.next()){
				abb=new BookBean();
				abb.setBOOK_NUM(rs.getInt("book_num"));
				abb.setBOOK_CATEGORY(rs.getString("book_category"));
				abb.setBOOK_NAME(rs.getString("book_name"));
				abb.setBOOK_CONTENT(rs.getString("book_content"));
				abb.setBOOK_SIZE(rs.getString("book_size"));
				abb.setBOOK_COLOR(rs.getString("book_color"));
				abb.setBOOK_AMOUNT(rs.getInt("book_amount"));
				abb.setBOOK_PRICE(rs.getInt("book_price"));
				abb.setBOOK_IMAGE(rs.getString("book_image"));
				abb.setBOOK_BEST(rs.getInt("book_best"));
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
	public int insertBook(BookBean abb) {
		int result = 0;
		int num=0;
		String sql="select max(book_num) from book";
		try {
			con = ds.getConnection();
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			rs.next();
			num=rs.getInt(1)+1;
			sql="insert into book values "+
			"(?,?,?,?,?,?,?,?,?,?,sysdate)";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			pstmt.setString(2, abb.getBOOK_CATEGORY());
			pstmt.setString(3, abb.getBOOK_NAME());
			pstmt.setString(4, abb.getBOOK_CONTENT());
			pstmt.setString(5, abb.getBOOK_SIZE());
			pstmt.setString(6, abb.getBOOK_COLOR());
			pstmt.setInt(7, abb.getBOOK_AMOUNT());
			pstmt.setInt(8, abb.getBOOK_PRICE());
			pstmt.setString(9, abb.getBOOK_IMAGE());
			pstmt.setInt(10, abb.getBOOK_BEST());
			result = pstmt.executeUpdate();
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
		return result;
	}
	public int deleteBook(BookBean abb){
		int result = 0;
		try {
			con = ds.getConnection();
			String sql="delete from book where book_num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, abb.getBOOK_NUM());
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
	public int modifyBook(BookBean abb) {
		int result = 0;
		try {
			con = ds.getConnection();
			String sql="update book set "+
			"book_category=?, book_name=?, book_price=? ,"+
			"book_color=? ,book_amount=? ,book_size=? ,"+
			"book_content=?,book_best=? where book_num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, abb.getBOOK_CATEGORY());
			pstmt.setString(2, abb.getBOOK_NAME());
			pstmt.setInt(3, abb.getBOOK_PRICE());
			pstmt.setString(4, abb.getBOOK_COLOR());
			pstmt.setInt(5, abb.getBOOK_AMOUNT());
			pstmt.setString(6, abb.getBOOK_SIZE());
			pstmt.setString(7, abb.getBOOK_CONTENT());
			pstmt.setInt(8, abb.getBOOK_BEST());
			pstmt.setInt(9, abb.getBOOK_NUM());
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