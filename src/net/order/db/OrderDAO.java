package net.order.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Vector;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import net.book.db.BookBean;
import net.cart.db.CartBean;

public class OrderDAO {
	DataSource ds;
	Connection conn;
	PreparedStatement pstmt;
	ResultSet rs, rs1;
	
	public OrderDAO(){
		try{
			Context initCtx=new InitialContext();
			Context envCtx=(Context)initCtx.lookup("java:comp/env");
			ds=(DataSource)envCtx.lookup("jdbc/mysql");
		}catch(Exception ex){
			System.out.println("DB接続エラー：　" + ex);
			return;
		}
	}
	
	public int getOrderCount(String id) throws SQLException {
		String sql="select count(*) from book_order where ORDER_MEMBER_ID=?";
		
		try{
			conn = ds.getConnection();
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs=pstmt.executeQuery();
			rs.next();			
			return rs.getInt(1);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		finally{
			try{
				if(rs!=null)rs.close();
				if(pstmt!=null)pstmt.close();
				if(conn!=null)conn.close();
			}catch(Exception ex) {}
		}
		return 0;
	}
	
	public int getOrderSumMoney(String id) throws SQLException{
		String sql="select sum(BOR.ORDER_COUNT * B.BOOK_PRICE) from book_order as BOR join book as B on BOR.ORDER_BOOK_NO = B.BOOK_NO "+
				   "where ORDER_MEMBER_ID=?";
		try{
			conn = ds.getConnection();
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs=pstmt.executeQuery();
			rs.next();
			
			return rs.getInt(1);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		finally{
			try{
				if(rs!=null)rs.close();
				if(pstmt!=null)pstmt.close();
				if(conn!=null)conn.close();
			}catch(Exception ex) {}
		}
		return 0;
	}
	
	public List getOrderList(int page,int limit,String id) throws SQLException {
		String sql="select * from (select ROW_NUMBER() OVER (order by ORDER_DATE desc) AS rnum, "+
				"BOR.ORDER_BOOK_NO, B.BOOK_NAME,B.BOOK_PRICE, BOR.ORDER_COUNT, "+
				"BOR.ORDER_COUNT * B.BOOK_PRICE AS TOTAL_PRICE, ORDER_DATE, ORDER_STATUS "+
				"from book_order AS BOR join book as B on BOR.ORDER_BOOK_NO = B.BOOK_NO " +
				"WHERE ORDER_MEMBER_ID = 1 order by ORDER_DATE desc) AS OL "+
				"where OL.rnum>? and OL.rnum<?";
		List book_order_list=new ArrayList();
		
		int startrow=(page-1)*10+1;
		int endrow=startrow+limit-1;
		
		try{
			conn = ds.getConnection();
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1,id);
			pstmt.setInt(2,startrow);
			pstmt.setInt(3,endrow);
			rs=pstmt.executeQuery();
			
			while(rs.next()){
				OrderBean order=new OrderBean();
				order.setORDER_BOOK_NO(rs.getInt("ORDER_BOOK_NO"));
				order.setBOOK_NAME(rs.getString("BOOK_NAME"));
				order.setBOOK_PRICE(rs.getInt("BOOK_PRICE"));
				order.setORDER_COUNT(rs.getInt("ORDER_COUNT"));
				order.setTOTAL_PRICE(rs.getInt("TOTAL_PRICE"));
				order.setORDER_DATE(rs.getDate("ORDER_DATE"));
				order.setORDER_STATUS(rs.getInt("ORDER_STATUS"));
				
				book_order_list.add(order);
			}
			
			return book_order_list;
		}catch(Exception ex){
			ex.printStackTrace();
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
	
	public int addOrder(OrderBean order, Vector bookvector){
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
		
		int ordernum=0;
		String sql="select max(ORDER_NO) from book_order";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			rs.next();
			ordernum=rs.getInt(1);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		
		List cartlist=(ArrayList)bookvector.get(0);
		List booklist=(ArrayList)bookvector.get(1);
		
		for (int i = 0; i < cartlist.size(); i++) {
			CartBean cart=(CartBean)cartlist.get(i);
			BookBean book=(BookBean)booklist.get(i);
			
			try {
				conn = ds.getConnection();
				++ordernum;
				
				sql = "insert into book_order values(?,?,?,?,?,"+
					"?,?,?,?,?,?,?,?,?,?,SYSDATE(),SYSDATE(),0,0,0,SYSDATE(),0,SYSDATE())";
				
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, ordernum);
				pstmt.setString(2,sdf.format(cal.getTime()).toString()+ "-" + ordernum);
				pstmt.setInt(3, book.getBOOK_NO());
				pstmt.setInt(4, cart.getCART_COUNT());
				pstmt.setString(5, order.getORDER_MEMBER_ID());
				pstmt.setString(6, order.getORDER_RECEIVE_NAME());
				pstmt.setString(7, order.getORDER_RECEIVE_NAME_KANA());
				pstmt.setString(8, order.getORDER_RECEIVE_EMAIL());
				pstmt.setString(9, order.getORDER_RECEIVE_TEL());
				pstmt.setString(10, order.getORDER_RECEIVE_ZIPCODE());
				pstmt.setString(11, order.getORDER_RECEIVE_ADD_1());
				pstmt.setString(12, order.getORDER_RECEIVE_ADD_2());
				pstmt.setString(13, order.getORDER_RECEIVE_ADD_3());
				pstmt.setString(14, order.getORDER_MEMO());
				pstmt.setString(15, order.getORDER_TRADE_TYPE());
      			
      			pstmt.executeUpdate();
      			
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
      	}
		
		return ordernum;
	}
}