package net.cart.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import net.book.db.BookBean;

public class CartDAO {
	DataSource ds;
	Connection conn;
	PreparedStatement pstmt;
	ResultSet rs, rs1;
	
	public CartDAO(){
		try{
			Context initCtx=new InitialContext();
			Context envCtx=(Context)initCtx.lookup("java:comp/env");
			ds=(DataSource)envCtx.lookup("jdbc/mysql");
		}catch(Exception ex){
			System.out.println("DB接続エラー：　" + ex);
			return;
		}
	}
	
	public HashMap<String, Object> getCartList(String id) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<CartBean> cartlist = new ArrayList<>();
		List<BookBean> booklist = new ArrayList<>();
		
		String sql = "select * from cart where " +
				"CART_MEMBER_ID=? AND DELETE_FLAG = 0";
		
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				CartBean dto = new CartBean();
				BookBean book = new BookBean();
				
				dto.setCART_NO(rs.getInt("CART_NO"));
				dto.setCART_MEMBER_ID(
						rs.getString("CART_MEMBER_ID"));
				dto.setCART_BOOK_NO(
						rs.getInt("CART_BOOK_NO"));
				dto.setCART_COUNT(
						rs.getInt("CART_COUNT"));
				dto.setCART_GENERATE_DATE(
						rs.getTimestamp("CART_GENERATE_DATE"));
				
				sql = "select * from book where book_no=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, dto.getCART_BOOK_NO());
				rs1 = pstmt.executeQuery();
				
				if(rs1.next()){
					book.setBOOK_NO(rs1.getInt("BOOK_NO"));
					book.setBOOK_NAME(
							rs1.getString("BOOK_NAME"));
					book.setBOOK_PRICE(
							rs1.getInt("BOOK_PRICE"));
					book.setBOOK_IMAGE(
							rs1.getString("BOOK_IMAGE"));
				}else{
					return null;
				}
				
				cartlist.add(dto);
				booklist.add(book);
			}
			
			map.put("cartlist", cartlist);
			map.put("booklist", booklist);
			
			return map;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			try{
				if(rs!=null)rs.close();
				if(pstmt!=null)pstmt.close();
				if(conn!=null)conn.close();
			}catch(Exception ex) {}
		}
		
		return null;
	}
	
	public void cartAdd(String id,int bookno, int amount){
		String sql="select max(cart_no) from cart";
		int num=0;
		
		try{
			conn = ds.getConnection();
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			rs.next();
			num=rs.getInt(1)+1;
			
			sql="insert into cart values "+
				"(?,?,?,?,NOW(),0,0,NOW(),0,NOW())";
			
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			pstmt.setString(2, id);
			pstmt.setInt(3, bookno);
			pstmt.setInt(4, amount);
			pstmt.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
		}
		finally{
			try{
				if(rs!=null)rs.close();
				if(pstmt!=null)pstmt.close();
				if(conn!=null)conn.close();
			}catch(Exception ex) {}
		}
	}
	
	public boolean cartRemove(int num) {
		String sql = "update CART set DELETE_FLAG=1 where CART_NO=?";
		
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			if(pstmt.executeUpdate()!=0){
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			try{
				if(pstmt!=null)pstmt.close();
				if(conn!=null)conn.close();
			}catch(Exception ex) {}
		}
		return false;
	}
	
	public boolean cartClear(String id) {
		String sql = "update CART set DELETE_FLAG=1 WHERE CART_MEMBER_ID=?";
		
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			if(pstmt.executeUpdate()!=0){
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			try{
				if(pstmt!=null)pstmt.close();
				if(conn!=null)conn.close();
			}catch(Exception ex) {}
		}
		return false;
	}
}