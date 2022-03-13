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
	//会員情報登録
	public boolean insertMember(MemberDTO mb) throws SQLException{
		String sql=null;
		boolean result = false;
		try{
			Long date = System.currentTimeMillis();
			con = ds.getConnection();
			sql="insert into member values "+
				"(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			
			pstmt=con.prepareStatement(sql);
			//アカウント
			pstmt.setString(1, mb.getMEMBER_ID());
			//パスワード
			pstmt.setString(2, mb.getMEMBER_PW());
			//氏名
			pstmt.setString(3, mb.getMEMBER_NAME());
			//氏名（カナ）
			pstmt.setString(4, mb.getMEMBER_NAME_KANA());
			//電話番号
			pstmt.setString(5, mb.getMEMBER_TEL());
			//メールアドレス
			pstmt.setString(6, mb.getMEMBER_MAIL());
			//郵便番号
			pstmt.setString(7, mb.getMEMBER_ZIPCODE());
			//都道府県
			pstmt.setString(8, mb.getMEMBER_ADD_1());
			//市区町村
			pstmt.setString(9, mb.getMEMBER_ADD_2());
			//丁目、番地、建物名
			pstmt.setString(10, mb.getMEMBER_ADD_3());
			//会員種別
			pstmt.setInt(11, 1); //1:一般ユーザー
			//削除フラグ
			pstmt.setInt(12, 0); //0:未削除
			//作成者
			pstmt.setInt(13, 1); //1:一般ユーザー
			//作成日時
			pstmt.setDate(14, new Date(date));
			//更新者
			pstmt.setInt(15, 1); //1:一般ユーザー
			//更新日時
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
	//会員情報チェック
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
				//取得した会員情報とパスワードが一致する場合、1を戻り値で設定
				if(memberpw.equals(pw)){
					x=1;
				}
				//取得した会員情報とパスワードが一致ではない場合、0を戻り値で設定
				else{
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
		//アカウント存在しない場合、-1を戻り値で設定
		return x;
	}
	//ID存在チェック
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
				//一致するIDが存在する場合、1を戻り値で設定
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
		//一致するIDが存在しない場合、-1を戻り値で設定
		return x;
	}
	//会員情報取得
	public MemberDTO getMember(String id) throws SQLException{
		MemberDTO member=null;
		String sql=null;
		
		try{
			con = ds.getConnection();
			sql="select * from member where MEMBER_ID=? and DELETE_FLAG=0";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs=pstmt.executeQuery();
			
			if(rs.next()){
				member=new MemberDTO();
				//アカウント
				member.setMEMBER_ID(rs.getString("MEMBER_ID"));
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
	//会員情報修正
	public void updateMember(MemberDTO mb) throws SQLException{
		String sql=null;
		
		try{
			con = ds.getConnection();
			sql="update member set MEMBER_PW=?,MEMBER_NAME=?,"+
			"MEMBER_NAME_KANA=?,MEMBER_TEL=?,MEMBER_MAIL=?,"+
			"MEMBER_ZIPCODE=?,MEMBER_ADD_1=?,MEMBER_ADD_2=?,"+
			"MEMBER_ADD_3=? where MEMBER_ID=? and DELETE_FLAG=0";
			
			pstmt=con.prepareStatement(sql);
			//パスワード
			pstmt.setString(1, mb.getMEMBER_PW());
			//氏名
			pstmt.setString(2, mb.getMEMBER_NAME());
			//氏名（カナ）
			pstmt.setString(3, mb.getMEMBER_NAME_KANA());
			//電話番号
			pstmt.setString(4, mb.getMEMBER_TEL());
			//メールアドレス
			pstmt.setString(5, mb.getMEMBER_MAIL());
			//郵便番号
			pstmt.setString(6, mb.getMEMBER_ZIPCODE());
			//都道府県
			pstmt.setString(7, mb.getMEMBER_ADD_1());
			//市区町村
			pstmt.setString(8, mb.getMEMBER_ADD_2());
			//丁目、番地、建物名
			pstmt.setString(9, mb.getMEMBER_ADD_3());
			//アカウント
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
	//会員情報削除
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
					sql="update MEMBER set DELETE_FLAG=1 where MEMBER_ID=?";
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
	//アカウント情報探す
	public MemberDTO findId(String name, String nameKana, String tel, String mail)
	throws SQLException{
		String sql=null;
		MemberDTO member=null;
		
		try{
			con = ds.getConnection();
			sql="select MEMBER_ID, MEMBER_PW "+
				"from MEMBER where MEMBER_NAME=? and MEMBER_NAME_KANA=? and MEMBER_TEL=? and MEMBER_MAIL=? and DELETE_FLAG=0";
			
			pstmt=con.prepareStatement(sql);
			//氏名
			pstmt.setString(1, name);
			//氏名（カナ）
			pstmt.setString(2, nameKana);
			//電話番号
			pstmt.setString(3, tel);
			//メールアドレス
			pstmt.setString(4, mail);
			rs=pstmt.executeQuery();
			
			if(rs.next()){
				member = new MemberDTO();
				//アカウント
				member.setMEMBER_ID(rs.getString("MEMBER_ID"));
				//パスワード
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
	//会員種別チェック
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
			//チェック結果が「0：管理者」の場合
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
		//チェック結果が「1：一般ユーザ」の場合
		return result;
	}
	//郵便番号検索
	public List<String> searchZipcode(String zipcode){
		String sql="select * from zipcode where ZIPCODE like ? and DELETE_FLAG=0";
		List<String> zipcodeList=new ArrayList<>();
		
		try{
			con = ds.getConnection();
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, "%"+zipcode+"%");
			rs=pstmt.executeQuery();
			
			while(rs.next()){
				//都道府県
				String add_1=rs.getString("ADD_1");
				//市区町村
				String add_2=rs.getString("ADD_2");
				//丁目、番地、建物名
				String add_3=rs.getString("ADD_3");  
				//郵便番号
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