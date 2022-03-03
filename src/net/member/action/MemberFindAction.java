package net.member.action;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.member.db.MemberBean;
import net.member.db.MemberDAO;
public class MemberFindAction implements Action{
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
	throws Exception{
		request.setCharacterEncoding("UTF-8");		
		ActionForward forward=new ActionForward();
		MemberDAO memberdao=new MemberDAO();
		MemberBean member=new MemberBean();
		//氏名
		String name=request.getParameter("MEMBER_NAME");
		//氏名（カナ）
		String nameKana=request.getParameter("MEMBER_NAME_KANA");
		//電話番号
		String tel=request.getParameter("MEMBER_TEL");
		//メールアドレス
		String mail=request.getParameter("MEMBER_MAIL1")+"@"+request.getParameter("MEMBER_MAIL2");
		//会員情報を取得
		member= memberdao.findId(name, nameKana, tel, mail);	
		//会員情報が存在する場合
		if(member!=null){
			//アカウント確認画面に遷移する。
			request.setAttribute("id", member.getMEMBER_ID());
			request.setAttribute("passwd", member.getMEMBER_PW());			
			forward.setRedirect(false);
			forward.setPath("./member/member_find_ok.jsp");
		}else{
			//会員情報が存在しない場合
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('一致するアカウントが存在していません。');");
			out.println("history.book();");
			out.println("</script>");
			out.close();
			forward=null;
		}		 
		return forward;
	}
}