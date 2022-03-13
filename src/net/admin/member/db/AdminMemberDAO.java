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

import net.member.db.MemberDTO;

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
	//会員情報件数取得
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
	//会員リスト取得
	public List<MemberDTO> getMemberList(int page,int limit){
		String member_list_sql="select * from (select ROW_NUMBER() OVER (order by CREATE_DATE desc) AS rnum, "+ 
				"MB.MEMBER_ID, MB.MEMBER_PW, MB.MEMBER_NAME, MB.MEMBER_NAME_KANA, "+ 
				"MB.MEMBER_TEL, MB.MEMBER_MAIL, MB.MEMBER_ZIPCODE, MB.MEMBER_ADD_1, "+
				"MB.MEMBER_ADD_2, MB.MEMBER_ADD_3, MB.MEMBER_TYPE, MB.CREATE_DATE from member AS MB where DELETE_FLAG =0) as memberList where rnum>=? and rnum<=?";
		List<MemberDTO> memberlist=new ArrayList<MemberDTO>();
		
		int startrow=(page-1)*limit+1;
		int endrow=startrow+limit-1;
		try {
			conn = ds.getConnection();
			pstmt=conn.prepareStatement(member_list_sql);
			pstmt.setInt(1, startrow);
			pstmt.setInt(2, endrow);
			rs=pstmt.executeQuery();
			while(rs.next()){
				MemberDTO member=new MemberDTO();
				//アカウント
				member.setMEMBER_ID(rs.getString("MEMBER_ID"));
				//パスワード
				member.setMEMBER_PW(rs.getString("MEMBER_PW"));
				//氏名
				member.setMEMBER_NAME(rs.getString("MEMBER_NAME"));
				//氏名（カナ）
				member.setMEMBER_NAME_KANA(rs.getString("MEMBER_NAME_KANA"));
				//電話番号
				member.setMEMBER_TEL(rs.getString("MEMBER_TEL"));
				//メールアドレス
				member.setMEMBER_MAIL(rs.getString("MEMBER_MAIL"));
				//郵便番号
				member.setMEMBER_ZIPCODE(rs.getString("MEMBER_ZIPCODE"));
				//都道府県
				member.setMEMBER_ADD_1(rs.getString("MEMBER_ADD_1"));
				//市区町村
				member.setMEMBER_ADD_2(rs.getString("MEMBER_ADD_2"));
				//丁目、番地、建物名
				member.setMEMBER_ADD_3(rs.getString("MEMBER_ADD_3"));
				//会員種別
				member.setMEMBER_TYPE(rs.getInt("MEMBER_TYPE"));
				//作成日時
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
	//会員情報取得
	public MemberDTO getMemberDetail(String id){
		MemberDTO member = null;
		try {
			conn = ds.getConnection();
			String sql="select * from member where MEMBER_ID = ? and delete_flag = 0";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,id);
			rs=pstmt.executeQuery();
			
			if(rs.next()){
				member=new MemberDTO();
				//アカウント
				member.setMEMBER_ID(rs.getString("MEMBER_ID"));
				//パスワード
				member.setMEMBER_PW(rs.getString("MEMBER_PW"));
				//氏名
				member.setMEMBER_NAME(rs.getString("MEMBER_NAME"));
				//氏名（カナ）
				member.setMEMBER_NAME_KANA(rs.getString("MEMBER_NAME_KANA"));
				//電話番号
				member.setMEMBER_TEL(rs.getString("MEMBER_TEL"));
				//メールアドレス
				member.setMEMBER_MAIL(rs.getString("MEMBER_MAIL"));
				//郵便番号
				member.setMEMBER_ZIPCODE(rs.getString("MEMBER_ZIPCODE"));
				//都道府県
				member.setMEMBER_ADD_1(rs.getString("MEMBER_ADD_1"));
				//市区町村
				member.setMEMBER_ADD_2(rs.getString("MEMBER_ADD_2"));
				//丁目、番地、建物名
				member.setMEMBER_ADD_3(rs.getString("MEMBER_ADD_3"));
				//会員種別
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
	//会員情報修正
	public boolean modifyMember(MemberDTO member){
		String order_modify_sql=
			"update MEMBER set MEMBER_NAME=?, MEMBER_NAME_KANA=?, MEMBER_TEL=?, MEMBER_MAIl=?, "+ 
			"MEMBER_ZIPCODE=?, MEMBER_ADD_1=?, MEMBER_ADD_2=?, MEMBER_ADD_3=?, "+
			"MEMBER_TYPE=?, UPDATE_USER=1, UPDATE_DATE=NOW() where MEMBER_ID =? AND DELETE_FLAG=0";
		int result=0;
		
		try{
			conn = ds.getConnection();
			pstmt=conn.prepareStatement(order_modify_sql);
			//氏名
			pstmt.setString(1, member.getMEMBER_NAME());
			//氏名（カナ）
			pstmt.setString(2, member.getMEMBER_NAME_KANA());
			//電話番号
			pstmt.setString(3, member.getMEMBER_TEL());
			//メールアドレス
			pstmt.setString(4, member.getMEMBER_MAIL());
			//郵便番号
			pstmt.setString(5, member.getMEMBER_ZIPCODE());
			//都道府県
			pstmt.setString(6, member.getMEMBER_ADD_1());
			//市区町村
			pstmt.setString(7, member.getMEMBER_ADD_2());
			//丁目、番地、建物名
			pstmt.setString(8, member.getMEMBER_ADD_3());
			//会員種別
			pstmt.setInt(9, member.getMEMBER_TYPE());
			//アカウント
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
	
	//会員情報削除
	public boolean deleteMember(String id){
		int result = 0;
		try {
			conn = ds.getConnection();
			String sql="update MEMBER set delete_flag=1 where member_id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			result = pstmt.executeUpdate();

			if(result>=1){
				return true;
			}
			
		} catch (Exception e) {
			e.getStackTrace();
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