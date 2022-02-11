package net.admin.order.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import net.order.db.OrderBean;

public class AdminOrderDAO {
	DataSource ds;
	Connection conn;
	PreparedStatement pstmt;
	ResultSet rs;
	
	public AdminOrderDAO(){
		try{
			Context initCtx=new InitialContext();
			   Context envCtx=(Context)initCtx.lookup("java:comp/env");
			   ds=(DataSource)envCtx.lookup("jdbc/mysql");
			  
		}catch(Exception ex){
			System.out.println("DB接続エラー：　" + ex);
			return;
		}
	}
	
	public int getOrderCount(){
		String order_count_sql="select count(*) from BOOK_ORDER";		
		try{
			conn = ds.getConnection();
			pstmt=conn.prepareStatement(order_count_sql);
			rs=pstmt.executeQuery();
			rs.next();
			
			return rs.getInt(1);
		}catch(SQLException ex){
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
	
	public List getOrderList(int page,int limit){
		String order_list_sql="select * from (select ROW_NUMBER() OVER (order by ORDER_NO desc) AS rnum, "+
				"BOR.ORDER_NO, BOR.ORDER_TRANS_NO, ORDER_TRADE_TYPE, BOR.ORDER_BOOK_NO, BOR.ORDER_MEMBER_ID, " + 
				"BOR.ORDER_COUNT * B.BOOK_PRICE AS TOTAL_PRICE, ORDER_DATE, ORDER_STATUS " +
				"from book_order AS BOR join book as B on BOR.ORDER_BOOK_NO = B.BOOK_NO order by ORDER_NO desc) AS OL "+
				"where OL.rnum>=? and OL.rnum<=?";
		
		List orderlist=new ArrayList();
		
		int startrow=(page-1)*10+1;
		int endrow=startrow+limit-1;
		try {
			conn = ds.getConnection();
			pstmt=conn.prepareStatement(order_list_sql);
			pstmt.setInt(1, startrow);
			pstmt.setInt(2, endrow);
			rs=pstmt.executeQuery();
			while(rs.next()){
				OrderBean order=new OrderBean();
				order.setORDER_NO(rs.getInt("ORDER_NO"));
				order.setORDER_TRANS_NO(rs.getString("ORDER_TRANS_NO"));
				order.setORDER_TRADE_TYPE(rs.getString("ORDER_TRADE_TYPE"));
				order.setORDER_BOOK_NO(rs.getInt("ORDER_BOOK_NO"));
				order.setORDER_MEMBER_ID(rs.getString("ORDER_MEMBER_ID"));
				order.setTOTAL_PRICE(rs.getInt("TOTAL_PRICE"));
				order.setORDER_DATE(rs.getDate("ORDER_DATE"));
				order.setORDER_STATUS(rs.getInt("ORDER_STATUS"));
				orderlist.add(order);
			}
			
			return orderlist;
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
	
	public OrderBean getOrderDetail(int ordernum){	
		String order_detail_sql="select BOR.ORDER_NO, BOR.ORDER_TRANS_NO, BOR.ORDER_BOOK_NO, B.BOOK_NAME,B.BOOK_PRICE, BOR.ORDER_COUNT, " + 
				"BOR.ORDER_TRADE_TYPE, BOR.ORDER_COUNT * B.BOOK_PRICE AS TOTAL_PRICE, ORDER_DATE, ORDER_STATUS, "	+ 			
				"BOR.ORDER_MEMBER_ID, BOR.ORDER_RECEIVE_NAME, BOR.ORDER_RECEIVE_NAME_KANA, " + 
				"BOR.ORDER_RECEIVE_EMAIL, BOR.ORDER_RECEIVE_TEL, BOR.ORDER_RECEIVE_ZIPCODE, " + 
				"BOR.ORDER_RECEIVE_ADD_1, BOR.ORDER_RECEIVE_ADD_2, BOR.ORDER_RECEIVE_ADD_3, BOR.ORDER_MEMO " + 
				"from BOOK_ORDER AS BOR join book as B on BOR.ORDER_BOOK_NO = B.BOOK_NO where ORDER_NO=?";
		try {
			conn = ds.getConnection();
			pstmt=conn.prepareStatement(order_detail_sql);
			pstmt.setInt(1, ordernum);
			rs=pstmt.executeQuery();
			rs.next();
			
			OrderBean order=new OrderBean();
			order.setORDER_NO(rs.getInt("ORDER_NO"));
			order.setORDER_TRANS_NO(rs.getString("ORDER_TRANS_NO"));
			order.setORDER_TRADE_TYPE(rs.getString("ORDER_TRADE_TYPE"));
			order.setORDER_BOOK_NO(rs.getInt("ORDER_BOOK_NO"));
			order.setBOOK_NAME(rs.getString("BOOK_NAME"));
			order.setBOOK_PRICE(rs.getInt("BOOK_PRICE"));
			order.setORDER_COUNT(rs.getInt("ORDER_COUNT"));
			order.setTOTAL_PRICE(rs.getInt("TOTAL_PRICE"));			
			order.setORDER_DATE(rs.getDate("ORDER_DATE"));
			order.setORDER_STATUS(rs.getInt("ORDER_STATUS"));
			order.setORDER_MEMBER_ID(rs.getString("ORDER_MEMBER_ID"));
			order.setORDER_RECEIVE_NAME(rs.getString("ORDER_RECEIVE_NAME"));		
			order.setORDER_RECEIVE_NAME_KANA(rs.getString("ORDER_RECEIVE_NAME_KANA"));	
			order.setORDER_RECEIVE_EMAIL(rs.getString("ORDER_RECEIVE_EMAIL"));
			order.setORDER_RECEIVE_TEL(rs.getString("ORDER_RECEIVE_TEL"));
			order.setORDER_RECEIVE_ZIPCODE(rs.getString("ORDER_RECEIVE_ZIPCODE"));
			order.setORDER_RECEIVE_ADD_1(rs.getString("ORDER_RECEIVE_ADD_1"));
			order.setORDER_RECEIVE_ADD_2(rs.getString("ORDER_RECEIVE_ADD_2"));
			order.setORDER_RECEIVE_ADD_3(rs.getString("ORDER_RECEIVE_ADD_3"));
			order.setORDER_MEMO(rs.getString("ORDER_MEMO"));

			return order;
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
	
	public boolean modifyOrder(OrderBean order){
		String order_modify_sql=
			"update BOOK_ORDER set ORDER_MEMO=?,ORDER_STATUS=? where ORDER_NO=?";
		int result=0;
		
		try{
			conn = ds.getConnection();
			pstmt=conn.prepareStatement(order_modify_sql);
			pstmt.setString(1, order.getORDER_MEMO());
			pstmt.setInt(2, order.getORDER_STATUS());
			pstmt.setInt(3, order.getORDER_NO());
			result=pstmt.executeUpdate();
			
			if(result==1){
				return true;
			}
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
		return false;
	}
	
	public boolean deleteOrder(int ordernum){
		String order_delete_sql="delete from BOOK_ORDER where ORDER_NO=?";
		int result=0;
		
		try{
			conn = ds.getConnection();
			pstmt=conn.prepareStatement(order_delete_sql);
			pstmt.setInt(1, ordernum);
			result=pstmt.executeUpdate();
			
			if(result==1){
				return true;
			}
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
		return false;
	}
}