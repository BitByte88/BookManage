package net.admin.member.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import net.member.db.MemberBean;

public class AdminMemberDAO {
	DataSource ds;
	Connection conn;
	PreparedStatement pstmt;
	ResultSet rs;
	
	public AdminMemberDAO(){
		try{
			Context initCtx=new InitialContext();
			   Context envCtx=(Context)initCtx.lookup("java:comp/env");
			   ds=(DataSource)envCtx.lookup("jdbc/mysql");
			  
		}catch(Exception ex){
			System.out.println("DB接続エラー：　" + ex);
			return;
		}
	}
	
	public int getMemberCount(){
		String member_count_sql="select count(MEMBER_ID) from MEMBER where DELETE_FLAG =0";		
		try{
			conn = ds.getConnection();
			pstmt=conn.prepareStatement(member_count_sql);
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
	
	public List<MemberBean> getMemberList(int page,int limit){
		String member_list_sql="select * from (select ROW_NUMBER() OVER (order by CREATE_DATE desc) AS rnum, "+ 
				"MB.MEMBER_ID, MB.MEMBER_PW, MB.MEMBER_NAME, MB.MEMBER_NAME_KANA, "+ 
				"MB.MEMBER_TEL, MB.MEMBER_MAIL, MB.MEMBER_ZIPCODE, MB.MEMBER_ADD_1, "+
				"MB.MEMBER_ADD_2, MB.MEMBER_ADD_3, MB.MEMBER_TYPE, MB.CREATE_DATE from member AS MB where DELETE_FLAG =0) as memberList where rnum>=? and rnum<=?";
		List<MemberBean> memberlist=new ArrayList<MemberBean>();
		
		int startrow=(page-1)*limit+1;
		int endrow=startrow+limit-1;
		try {
			conn = ds.getConnection();
			pstmt=conn.prepareStatement(member_list_sql);
			pstmt.setInt(1, startrow);
			pstmt.setInt(2, endrow);
			rs=pstmt.executeQuery();
			while(rs.next()){
				MemberBean member=new MemberBean();
				member.setMEMBER_ID(rs.getString("MEMBER_ID"));
				member.setMEMBER_PW(rs.getString("MEMBER_PW"));
				member.setMEMBER_NAME(rs.getString("MEMBER_NAME"));
				member.setMEMBER_NAME_KANA(rs.getString("MEMBER_NAME_KANA"));
				member.setMEMBER_TEL(rs.getString("MEMBER_TEL"));
				member.setMEMBER_MAIL(rs.getString("MEMBER_MAIL"));
				member.setMEMBER_ZIPCODE(rs.getString("MEMBER_ZIPCODE"));
				member.setMEMBER_ADD_1(rs.getString("MEMBER_ADD_1"));
				member.setMEMBER_ADD_2(rs.getString("MEMBER_ADD_2"));
				member.setMEMBER_ADD_3(rs.getString("MEMBER_ADD_3"));
				member.setMEMBER_TYPE(rs.getInt("MEMBER_TYPE"));
				member.setCREATE_DATE(rs.getDate("CREATE_DATE"));
				memberlist.add(member);
			}
			
			return memberlist;
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
	
	public MemberBean getMemberDetail(String id){
		MemberBean member = null;
		try {
			conn = ds.getConnection();
			String sql="select * from member where MEMBER_ID = ? and delete_flag = 0";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,id);
			rs=pstmt.executeQuery();
			
			if(rs.next()){
				member=new MemberBean();
				member.setMEMBER_ID(rs.getString("MEMBER_ID"));
				member.setMEMBER_PW(rs.getString("MEMBER_PW"));
				member.setMEMBER_NAME(rs.getString("MEMBER_NAME"));
				member.setMEMBER_NAME_KANA(rs.getString("MEMBER_NAME_KANA"));
				member.setMEMBER_TEL(rs.getString("MEMBER_TEL"));
				member.setMEMBER_MAIL(rs.getString("MEMBER_MAIL"));
				member.setMEMBER_ZIPCODE(rs.getString("MEMBER_ZIPCODE"));
				member.setMEMBER_ADD_1(rs.getString("MEMBER_ADD_1"));
				member.setMEMBER_ADD_2(rs.getString("MEMBER_ADD_2"));
				member.setMEMBER_ADD_3(rs.getString("MEMBER_ADD_3"));
				member.setMEMBER_TYPE(rs.getInt("MEMBER_TYPE"));				
			}
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

		return member;
	}
	
	public boolean modifyMember(MemberBean member){
		String order_modify_sql=
			"update MEMBER set MEMBER_NAME=?, MEMBER_NAME_KANA=?, MEMBER_TEL=?, MEMBER_MAIl=?, "+ 
			"MEMBER_ZIPCODE=?, MEMBER_ADD_1=?, MEMBER_ADD_2=?, MEMBER_ADD_3=?, "+
			"MEMBER_TYPE=?, UPDATE_USER=1, UPDATE_DATE=NOW() where MEMBER_ID =? AND DELETE_FLAG=0";
		int result=0;
		
		try{
			conn = ds.getConnection();
			pstmt=conn.prepareStatement(order_modify_sql);
			pstmt.setString(1, member.getMEMBER_NAME());
			pstmt.setString(2, member.getMEMBER_NAME_KANA());
			pstmt.setString(3, member.getMEMBER_TEL());
			pstmt.setString(4, member.getMEMBER_MAIL());
			pstmt.setString(5, member.getMEMBER_ZIPCODE());
			pstmt.setString(6, member.getMEMBER_ADD_1());
			pstmt.setString(7, member.getMEMBER_ADD_2());
			pstmt.setString(8, member.getMEMBER_ADD_3());
			pstmt.setInt(9, member.getMEMBER_TYPE());
			pstmt.setString(10, member.getMEMBER_ID());
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
	
	public boolean deleteMember(String id){
		String order_delete_sql="delete from MEMBER where MEMBER_ID=?";
		int result=0;
		
		try{
			conn = ds.getConnection();
			pstmt=conn.prepareStatement(order_delete_sql);
			pstmt.setString(1, id);
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