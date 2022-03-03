package net.member.action;
import java.io.PrintWriter;
import javax.servlet.http.*;
import net.member.db.MemberDAO;
public class MemberDeleteAction implements Action{
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
	throws Exception{
		ActionForward forward=new ActionForward();
		HttpSession session=request.getSession();
		String id=(String)session.getAttribute("id");
		//セッションにログイン情報が存在しない場合、ログイン画面に遷移する。
		if(id==null){
			forward.setRedirect(true);
			forward.setPath("./MemberLogin.member");
			return forward;
		}
		MemberDAO memberdao=new MemberDAO();
		String pass=request.getParameter("MEMBER_PW");
		
		try{
			//会員情報削除
			int check=memberdao.deleteMember(id, pass);
			//会員情報削除成功
			if(check == 1){
				session.invalidate();
				forward.setPath("./member/member_out_ok.jsp"); 
			}
			//会員情報削除失敗、エラーメッセージを表示
			else{
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('パスワードが一致していません。');");
				out.println("history.back();");
				out.println("</script>");
				out.close();
				forward=null;
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return forward;
	}
}