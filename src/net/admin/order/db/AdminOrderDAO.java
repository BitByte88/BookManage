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
	//注文情報件数取得
	public int getOrderCount(){
		String order_count_sql="select count(distinct ORDER_NO) from BOOK_ORDER";		
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
	//注文リスト取得
	public List<OrderBean> getOrderList(int page,int limit){
		String order_list_sql="select * from (select ROW_NUMBER() OVER (order by ORDER_DATE desc) AS rnum, "+ 
				"BOR.ORDER_NO, BOR.ORDER_STATUS, BOR.ORDER_MEMBER_ID, BOR.ORDER_TRADE_TYPE, "+ 
				"BOR.ORDER_DATE from book_order AS BOR group by BOR.ORDER_NO) as orderList where rnum>=? and rnum<=?";
		List<OrderBean> orderlist=new ArrayList<OrderBean>();
		
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
				//注文NO
				order.setORDER_NO(rs.getInt("ORDER_NO"));
				//決済方法
				order.setORDER_TRADE_TYPE(rs.getString("ORDER_TRADE_TYPE"));
				//注文者
				order.setORDER_MEMBER_ID(rs.getString("ORDER_MEMBER_ID"));
				//注文日時
				order.setORDER_DATE(rs.getDate("ORDER_DATE"));
				//注文ステータス
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
	//注文情報取得
	public List<OrderBean> getOrderDetail(int ordernum){
		List<OrderBean> orderlist=new ArrayList<OrderBean>();
		String order_detail_sql="select BOR.ORDER_NO, BOR.ORDER_ITEM_NO, BOR.ORDER_BOOK_NO, B.BOOK_NAME,B.BOOK_PRICE, BOR.ORDER_COUNT, " + 
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
			
			while(rs.next()){
			OrderBean order=new OrderBean();
			//注文NO
			order.setORDER_NO(rs.getInt("ORDER_NO"));
			//アイテムNo
			order.setORDER_ITEM_NO(rs.getInt("ORDER_ITEM_NO"));
			//決済方法
			order.setORDER_TRADE_TYPE(rs.getString("ORDER_TRADE_TYPE"));
			//図書NO
			order.setORDER_BOOK_NO(rs.getInt("ORDER_BOOK_NO"));
			//書名
			order.setBOOK_NAME(rs.getString("BOOK_NAME"));
			//単価
			order.setBOOK_PRICE(rs.getInt("BOOK_PRICE"));
			//数量
			order.setORDER_COUNT(rs.getInt("ORDER_COUNT"));
			//注文合計金額
			order.setTOTAL_PRICE(rs.getInt("TOTAL_PRICE"));			
			//注文日時
			order.setORDER_DATE(rs.getDate("ORDER_DATE"));
			//注文ステータス
			order.setORDER_STATUS(rs.getInt("ORDER_STATUS"));
			//アカウント
			order.setORDER_MEMBER_ID(rs.getString("ORDER_MEMBER_ID"));
			//届け先_氏名
			order.setORDER_RECEIVE_NAME(rs.getString("ORDER_RECEIVE_NAME"));		
			//届け先_氏名（カナ）
			order.setORDER_RECEIVE_NAME_KANA(rs.getString("ORDER_RECEIVE_NAME_KANA"));	
			//届け先_メールアドレス
			order.setORDER_RECEIVE_EMAIL(rs.getString("ORDER_RECEIVE_EMAIL"));
			//届け先_TEL
			order.setORDER_RECEIVE_TEL(rs.getString("ORDER_RECEIVE_TEL"));
			//届け先_郵便番号
			order.setORDER_RECEIVE_ZIPCODE(rs.getString("ORDER_RECEIVE_ZIPCODE"));
			//届け先_都道府県
			order.setORDER_RECEIVE_ADD_1(rs.getString("ORDER_RECEIVE_ADD_1"));
			//届け先_市区町村
			order.setORDER_RECEIVE_ADD_2(rs.getString("ORDER_RECEIVE_ADD_2"));
			//届け先_丁目、番地、建物名
			order.setORDER_RECEIVE_ADD_3(rs.getString("ORDER_RECEIVE_ADD_3"));
			//メモー
			order.setORDER_MEMO(rs.getString("ORDER_MEMO"));
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
	//注文情報修正
	public boolean modifyOrder(OrderBean order){
		String order_modify_sql=
			"update BOOK_ORDER set ORDER_MEMO=?,ORDER_STATUS=? where ORDER_NO=?";
		int result=0;
		
		try{
			conn = ds.getConnection();
			pstmt=conn.prepareStatement(order_modify_sql);
			//メモ‐
			pstmt.setString(1, order.getORDER_MEMO());
			//注文ステータス
			pstmt.setInt(2, order.getORDER_STATUS());
			//注文NO
			pstmt.setInt(3, order.getORDER_NO());
			result=pstmt.executeUpdate();
			
			if(result>=1){
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
	//注文情報削除
	public boolean deleteOrder(int ordernum){
		String order_delete_sql="delete from BOOK_ORDER where ORDER_NO=?";
		int result=0;
		
		try{
			conn = ds.getConnection();
			pstmt=conn.prepareStatement(order_delete_sql);
			pstmt.setInt(1, ordernum);
			result=pstmt.executeUpdate();
			
			if(result>=1){
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
	//合計金額取得
	public int getTotalPrice(int orderNo){
		String order_list_sql="select sum(sub_total) as TOTAL_PRICE from (select BOR.ORDER_NO, BOR.ORDER_COUNT * B.BOOK_PRICE AS sub_total from book_order as BOR join book as B on BOR.ORDER_BOOK_NO = B.BOOK_NO WHERE BOR.ORDER_NO = ?) as ST";
		try {
			conn = ds.getConnection();
			pstmt=conn.prepareStatement(order_list_sql);
			pstmt.setInt(1, orderNo);
			rs=pstmt.executeQuery();
			rs.next();
			return rs.getInt(1);
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
		return 0;
	}
}