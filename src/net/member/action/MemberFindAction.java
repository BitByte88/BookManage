package net.member.action;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

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
		
		//パラメータチェック
		List<String> errorMsg = memberFindParameterCheck(request);	
		
		//チェック結果が「エラー」の場合
		if(errorMsg.size() != 0) {
			String error = "";
			for(String msg : errorMsg) {
				error = error + msg + "\\n";
			}
			
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('"+ error +"')");
			out.println("history.back()");
			out.println("</script>");
			return null;				
		}
		
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
	
	public List<String> memberFindParameterCheck(HttpServletRequest request) {
		List<String> errorMsg = new ArrayList<String>();
		String name = request.getParameter("MEMBER_NAME");
		String nameKana = request.getParameter("MEMBER_NAME_KANA");
		String tel = request.getParameter("MEMBER_TEL");
		String mail1 = request.getParameter("MEMBER_MAIL1");
		String mail2 = request.getParameter("MEMBER_MAIL2");
		
		if(name == null || name.isEmpty()){//必須チェック（氏名
			errorMsg.add("氏名を入力してください。");
		}
		
		if(nameKana == null || nameKana.isEmpty()){//必須チェック（氏名（カナ））
			errorMsg.add("氏名（カナ）を入力してください。");
		}
		
		if(tel == null || tel.isEmpty()){//必須チェック（電話番号）
			errorMsg.add("TELを入力してください。");
		}
		
		if(mail1 == null || mail1.isEmpty() || mail2 == null || mail2.isEmpty() ){//必須チェック（メールアドレス）
			errorMsg.add("メールアドレスを入力してください。");
		}
		
		return errorMsg;
	}
}