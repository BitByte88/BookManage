package net.member.db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class MemberDAO {
	DataSource ds;
	Connection con=null;
	PreparedStatement pstmt=null;
	ResultSet rs=null;
	
	public MemberDAO() {
		try{
			Context initCtx=new InitialContext();
			Context envCtx=(Context)initCtx.lookup("java:comp/env");
			ds=(DataSource)envCtx.lookup("jdbc/mysql");
		}catch(Exception ex){
			System.out.println("DB接続エラー：　" + ex);
			return;
		}
	}
	
	public boolean insertMember(MemberBean mb) throws SQLException{
		String sql=null;
		boolean result = false;
		try{
			Long date = System.currentTimeMillis();
			con = ds.getConnection();
			sql="insert into member values "+
				"(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, mb.getMEMBER_ID());
			pstmt.setString(2, mb.getMEMBER_PW());
			pstmt.setString(3, mb.getMEMBER_NAME());
			pstmt.setString(4, mb.getMEMBER_NAME_KANA());
			pstmt.setString(5, mb.getMEMBER_TEL());
			pstmt.setString(6, mb.getMEMBER_MAIL());
			pstmt.setString(7, mb.getMEMBER_ZIPCODE());
			pstmt.setString(8, mb.getMEMBER_ADD_1());
			pstmt.setString(9, mb.getMEMBER_ADD_2());
			pstmt.setString(10, mb.getMEMBER_ADD_3());
			pstmt.setInt(11, 1); //1:一般ユーザー
			pstmt.setInt(12, 0); //0:未削除
			pstmt.setInt(13, 1); //1:一般ユーザー
			pstmt.setDate(14, new Date(date));
			pstmt.setInt(15, 1); //1:一般ユーザー
			pstmt.setDate(16, new Date(date));
			pstmt.executeUpdate();
			result = true;
		}catch(Exception e){
			e.printStackTrace();
		}	finally{
			try{
				if(pstmt!=null)pstmt.close();
				if(con!=null)con.close();
			}catch(Exception ex) {}
		}
		
		return result;
	}
	
	public int userCheck(String id, String pw) throws SQLException{
		String sql=null;
		int x=-1;
		
		try{
			con = ds.getConnection();
			sql="select MEMBER_PW from member where MEMBER_ID=? and DELETE_FLAG=0";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs=pstmt.executeQuery();
			if(rs.next()){
				String memberpw=rs.getString("MEMBER_PW");
				
				if(memberpw.equals(pw)){
					x=1;
				}else{
					x=0;
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}	finally{
			try{
				if(rs!=null)rs.close();
				if(pstmt!=null)pstmt.close();
				if(con!=null)con.close();
			}catch(Exception ex) {}
		}
		return x;
	}
	
	public int confirmId(String id) throws SQLException{
		String sql=null;
		int x=-1;
		
		try{
			con = ds.getConnection();
			sql="select MEMBER_ID from member where MEMBER_ID=? and DELETE_FLAG=0";
			
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs=pstmt.executeQuery();
			
			if(rs.next()){
				x=1;
			}
		}catch(Exception e){
			e.printStackTrace();
		}	finally{
			try{
				if(rs!=null)rs.close();
				if(pstmt!=null)pstmt.close();
				if(con!=null)con.close();
			}catch(Exception ex) {}
		}
		return x;
	}
	
	public MemberBean getMember(String id) throws SQLException{
		MemberBean member=null;
		String sql=null;
		
		try{
			con = ds.getConnection();
			sql="select * from member where MEMBER_ID=? and DELETE_FLAG=0";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs=pstmt.executeQuery();
			
			if(rs.next()){
				member=new MemberBean();
				
				member.setMEMBER_ID(rs.getString("MEMBER_ID"));
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
		}catch(Exception e){
			e.printStackTrace();
		}	finally{
			try{
				if(rs!=null)rs.close();
				if(pstmt!=null)pstmt.close();
				if(con!=null)con.close();
			}catch(Exception ex) {}
		}
		return member;
	}
	
	public void updateMember(MemberBean mb) throws SQLException{
		String sql=null;
		
		try{
			con = ds.getConnection();
			sql="update member set MEMBER_PW=?,MEMBER_NAME=?,"+
			"MEMBER_NAME_KANA=?,MEMBER_TEL=?,MEMBER_MAIL=?,"+
			"MEMBER_ZIPCODE=?,MEMBER_ADD_1=?,MEMBER_ADD_2=?,"+
			"MEMBER_ADD_3=? where MEMBER_ID=? and DELETE_FLAG=0";
			
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, mb.getMEMBER_PW());
			pstmt.setString(2, mb.getMEMBER_NAME());
			pstmt.setString(3, mb.getMEMBER_NAME_KANA());
			pstmt.setString(4, mb.getMEMBER_TEL());
			pstmt.setString(5, mb.getMEMBER_MAIL());
			pstmt.setString(6, mb.getMEMBER_ZIPCODE());
			pstmt.setString(7, mb.getMEMBER_ADD_1());
			pstmt.setString(8, mb.getMEMBER_ADD_2());
			pstmt.setString(9, mb.getMEMBER_ADD_3());
			pstmt.setString(10, mb.getMEMBER_ID());
			pstmt.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
		}	finally{
			try{
				if(rs!=null)rs.close();
				if(pstmt!=null)pstmt.close();
				if(con!=null)con.close();
			}catch(Exception ex) {}
		}
	}
	
	public int deleteMember(String id, String pw) throws SQLException{
		String sql=null;
		int x=-1;
		
		try{
			con = ds.getConnection();
			sql="select MEMBER_PW from member where MEMBER_ID=? and DELETE_FLAG=0";
			
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs=pstmt.executeQuery();
			
			if(rs.next()){
				String memberpw=rs.getString("MEMBER_PW");
				if(memberpw.equals(pw)){
					sql="delete from member where MEMBER_ID=?";
					pstmt=con.prepareStatement(sql);
					pstmt.setString(1, id);
					pstmt.executeUpdate();
					x=1;
				}else{
					x=0;
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}	finally{
			try{
				if(rs!=null)rs.close();
				if(pstmt!=null)pstmt.close();
				if(con!=null)con.close();
			}catch(Exception ex) {}
		}
		return x;
	}	
	
	public MemberBean findId(String name, String nameKana, String tel, String mail)
	throws SQLException{
		String sql=null;
		MemberBean member=null;
		
		try{
			con = ds.getConnection();
			sql="select MEMBER_ID, MEMBER_PW "+
				"from MEMBER where MEMBER_NAME=? and MEMBER_NAME_KANA=? and MEMBER_TEL=? and MEMBER_MAIL=? and DELETE_FLAG=0";
			
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, name);
			pstmt.setString(2, nameKana);
			pstmt.setString(3, tel);
			pstmt.setString(4, mail);
			rs=pstmt.executeQuery();
			
			if(rs.next()){
				member = new MemberBean();
				member.setMEMBER_ID(rs.getString("MEMBER_ID"));
				member.setMEMBER_PW(rs.getString("MEMBER_PW"));
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
		}	finally{
			try{
				if(rs!=null)rs.close();
				if(pstmt!=null)pstmt.close();
				if(con!=null)con.close();
			}catch(Exception ex) {}
		}
		return member;
	}
	
	public boolean isAdmin(String id){
		String sql="select MEMBER_TYPE from MEMBER where MEMBER_ID=? and DELETE_FLAG=0";
		int member_admin=1;
		boolean result = false;
		try {
			con = ds.getConnection();
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs=pstmt.executeQuery();
			rs.next();
			
			member_admin=rs.getInt("MEMBER_TYPE");
			
			if(member_admin==0){
				result = true;
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
		return result;
	}
	
	public List<String> searchZipcode(String zipcode){
		String sql="select * from zipcode where ZIPCODE like ? and DELETE_FLAG=0";
		List<String> zipcodeList=new ArrayList<>();
		
		try{
			con = ds.getConnection();
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, "%"+zipcode+"%");
			rs=pstmt.executeQuery();
			
			while(rs.next()){
				String add_1=rs.getString("ADD_1");
				String add_2=rs.getString("ADD_2");
				String add_3=rs.getString("ADD_3");  
				
				String zipcodeResult=rs.getString("ZIPCODE");
				
				zipcodeList.add(zipcodeResult+","+add_1+"-"+add_2+"-"+add_3);
			}
			
			
		} catch (SQLException e){
			e.printStackTrace();
		}
		finally{
			try{
				if(rs!=null)rs.close();
				if(pstmt!=null)pstmt.close();
				if(con!=null)con.close();
			}catch(Exception ex) {}
		}
		return zipcodeList;
	}
}