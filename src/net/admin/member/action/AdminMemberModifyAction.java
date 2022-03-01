package net.admin.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.admin.member.db.AdminMemberDAO;
import net.member.db.MemberBean;

public class AdminMemberModifyAction implements Action{
	public ActionForward execute(HttpServletRequest request,HttpServletResponse response) 
	throws Exception{
		AdminMemberDAO memberdao=new AdminMemberDAO();
		MemberBean member=new MemberBean();
		
		boolean result=false;
		request.setCharacterEncoding("UTF-8");
		//アカウント
		member.setMEMBER_ID(request.getParameter("id"));
		//氏名
		member.setMEMBER_NAME(request.getParameter("MEMBER_NAME"));
		//氏名（カナ）
		member.setMEMBER_NAME_KANA(request.getParameter("MEMBER_NAME_KANA"));
		//電話番号
		member.setMEMBER_TEL(request.getParameter("MEMBER_TEL"));
		//メールアドレス
		member.setMEMBER_MAIL(request.getParameter("MEMBER_MAIL"));
		//郵便番号
		member.setMEMBER_ZIPCODE(request.getParameter("MEMBER_ZIPCODE"));
		//都道府県
		member.setMEMBER_ADD_1(request.getParameter("MEMBER_ADD_1"));
		//市区町村
		member.setMEMBER_ADD_2(request.getParameter("MEMBER_ADD_2"));
		//丁目、番地、建物名
		member.setMEMBER_ADD_3(request.getParameter("MEMBER_ADD_3"));
		//会員種別
		member.setMEMBER_TYPE(Integer.parseInt(request.getParameter("MEMBER_TYPE")));
		//会員情報修正
		result=memberdao.modifyMember(member);
		//会員情報修正結果が「失敗」場合
		if(result==false){
			System.out.println("会員情報修正エラー");
			return null;
		}
		//会員リスト画面に遷移する。
		ActionForward forward=new ActionForward();
		forward.setRedirect(true);
		forward.setPath("./AdminMemberList.admember");
		return forward;
	 } 
}