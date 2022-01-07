package net.cart.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

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
			System.out.println("DB ���� ���� : " + ex);
			return;
		}
	}
	
	public Vector getCartList(String id) {
		Vector vector=new Vector();
		List cartlist = new ArrayList();
		List booklist = new ArrayList();
		
		String sql = "select * from cart where " +
				"CART_MEMBER_ID=?";
		
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				CartBean dto = new CartBean();
				BookBean book = new BookBean();
				
				dto.setCART_NUM(rs.getInt("CART_NUM"));
				dto.setCART_MEMBER_ID(
						rs.getString("CART_MEMBER_ID"));
				dto.setCART_BOOK_NUM(
						rs.getInt("CART_BOOK_NUM"));
				dto.setCART_BOOK_AMOUNT(
						rs.getInt("CART_BOOK_AMOUNT"));
				dto.setCART_BOOK_SIZE(
						rs.getString("CART_BOOK_SIZE"));
				dto.setCART_BOOK_COLOR(
						rs.getString("CART_BOOK_COLOR"));
				dto.setCART_DATE(
						rs.getDate("CART_DATE"));
				
				sql = "select * from book where book_num=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, dto.getCART_BOOK_NUM());
				rs1 = pstmt.executeQuery();
				
				if(rs1.next()){
					book.setBOOK_NAME(
							rs1.getString("book_name"));
					book.setBOOK_PRICE(
							rs1.getInt("book_price"));
					book.setBOOK_IMAGE(
							rs1.getString("BOOK_IMAGE"));
				}else{
					return null;
				}
				
				cartlist.add(dto);
				booklist.add(book);
			}
			
			vector.add(cartlist);
			vector.add(booklist);
			
			return vector;
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
	
	public void cartAdd(String id,int booknum,
			int amount,String size,String color){
		String sql="select max(cart_num) from cart";
		int num=0;
		
		try{
			conn = ds.getConnection();
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			rs.next();
			num=rs.getInt(1)+1;
			
			sql="insert into cart values "+
				"(?,?,?,?,?,?,sysdate)";
			
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			pstmt.setString(2, id);
			pstmt.setInt(3, booknum);
			pstmt.setInt(4, amount);
			pstmt.setString(5,size);
			pstmt.setString(6, color);
			
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
		String sql = "delete from CART where CART_NUM=?";
		
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
}