package net.member.action;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.member.db.MemberBean;
import net.member.db.MemberDAO;
public class MemberModifyAction_1 implements Action{
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
	throws Exception{
		ActionForward forward=new ActionForward();
		HttpSession sesseion=request.getSession(true);
		String id=(String)sesseion.getAttribute("id");
		//セッションにログイン情報が存在しない場合、ログイン画面に遷移する。
		if(id==null){
			forward.setRedirect(true);
			forward.setPath("./MemberLogin.member");
			return forward;
		}
		
		MemberDAO memberdao=new MemberDAO();
		//会員情報取得
		MemberBean dto=memberdao.getMember(id);
		//会員情報修正画面に遷移する。
		request.setAttribute("member", dto);
		forward.setPath("./member/member_info.jsp"); 			
		return forward;
	}
}