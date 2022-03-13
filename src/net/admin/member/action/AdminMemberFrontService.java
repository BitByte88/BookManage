package net.admin.member.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.admin.member.db.AdminMemberDAO;
import net.member.db.MemberBean;

public class AdminMemberFrontService {
	//会員リスト画面表示
	public ForwardService AdminMemberListAction(HttpServletRequest request, HttpServletResponse response) {
		AdminMemberDAO memberdao = new AdminMemberDAO();
		List<MemberBean> memberlist = new ArrayList<MemberBean>();

		int page = 1;
		// 1ページに表示する会員数
		int limit = 50;

		if (request.getParameter("page") != null) {
			page = Integer.parseInt(request.getParameter("page"));
		}
		// すべての会員情報件数取得
		int membercount = memberdao.getMemberCount();
		// 会員リスト取得
		memberlist = memberdao.getMemberList(page, limit);
		// ページング
		int maxpage = (int) ((double) membercount / limit + 0.95);
		int startpage = (((int) ((double) page / 10 + 0.9)) - 1) * 10 + 1;
		int endpage = maxpage;
		if (endpage > startpage + 10 - 1)
			endpage = startpage + 10 - 1;
		// 現在ページ
		request.setAttribute("page", page);
		// 最後のページ
		request.setAttribute("maxpage", maxpage);
		// ページングに最初に表示されるページ番号
		request.setAttribute("startpage", startpage);
		// ページングに最後に表示されるページ番号
		request.setAttribute("endpage", endpage);
		// すべての会員情報件数
		request.setAttribute("membercount", membercount);
		// 会員リスト
		request.setAttribute("memberlist", memberlist);
		// 会員リスト画面に遷移する。
		ForwardService forward = new ForwardService();
		forward.setPath("./adminMember/admin_member_list.jsp");
		return forward;
	}
	
	//会員情報修正画面を表示する。
	public ForwardService AdminMemberDetailAction(HttpServletRequest request, HttpServletResponse response) {
		AdminMemberDAO memberdao = new AdminMemberDAO();
		MemberBean member = new MemberBean();

		String id = request.getParameter("id");
		// 会員情報を取得する。
		member = memberdao.getMemberDetail(id);
		request.setAttribute("member", member);
		// 会員情報修正画面に遷移する。
		ForwardService forward = new ForwardService();
		forward.setPath("./adminMember/admin_member_modify.jsp");
		return forward;
	}

	
	//会員情報を修正する。
	public ForwardService AdminMemberModifyAction(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		AdminMemberDAO memberdao = new AdminMemberDAO();
		MemberBean member = new MemberBean();

		// パラメータチェック
		List<String> errorMsg = memberParameterCheck(request);

		// チェック結果が「エラー」の場合
		if (errorMsg.size() != 0) {
			String error = "";
			for (String msg : errorMsg) {
				error = error + msg + "\\n";
			}

			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('" + error + "')");
			out.println("history.back()");
			out.println("</script>");
			return null;
		}

		boolean result = false;
		// アカウント
		member.setMEMBER_ID(request.getParameter("id"));
		// 氏名
		member.setMEMBER_NAME(request.getParameter("MEMBER_NAME"));
		// 氏名（カナ）
		member.setMEMBER_NAME_KANA(request.getParameter("MEMBER_NAME_KANA"));
		// 電話番号
		member.setMEMBER_TEL(request.getParameter("MEMBER_TEL"));
		// メールアドレス
		member.setMEMBER_MAIL(request.getParameter("MEMBER_MAIL"));
		// 郵便番号
		member.setMEMBER_ZIPCODE(request.getParameter("MEMBER_ZIPCODE"));
		// 都道府県
		member.setMEMBER_ADD_1(request.getParameter("MEMBER_ADD_1"));
		// 市区町村
		member.setMEMBER_ADD_2(request.getParameter("MEMBER_ADD_2"));
		// 丁目、番地、建物名
		member.setMEMBER_ADD_3(request.getParameter("MEMBER_ADD_3"));
		// 会員種別
		member.setMEMBER_TYPE(Integer.parseInt(request.getParameter("MEMBER_TYPE")));
		// 会員情報修正
		result = memberdao.modifyMember(member);
		// 会員情報修正結果が「失敗」場合
		if (result == false) {
			System.out.println("会員情報修正エラー");
			return null;
		}
		// 会員リスト画面に遷移する。
		ForwardService forward = new ForwardService();
		forward.setRedirect(true);
		forward.setPath("./AdminMemberList.admember");
		return forward;
	}

	//会員情報を削除する。
	public ForwardService AdminMemberDeleteAction(HttpServletRequest request, HttpServletResponse response) {
		AdminMemberDAO memberdao = new AdminMemberDAO();

		boolean result = false;
		String id = request.getParameter("id");
		// 会員情報削除
		result = memberdao.deleteMember(id);
		// 会員情報削除結果が「失敗」の場合
		if (result == false) {
			System.out.println("会員情報削除エラー");
			return null;
		}
		// 会員リスト画面に遷移する。
		ForwardService forward = new ForwardService();
		forward.setRedirect(true);
		forward.setPath("./AdminMemberList.admember");
		return forward;
	}
	//パラメータチェック（会員情報修正）
	public List<String> memberParameterCheck(HttpServletRequest request) {
		List<String> errorMsg = new ArrayList<String>();
		String name = request.getParameter("MEMBER_NAME");
		String nameKana = request.getParameter("MEMBER_NAME_KANA");
		String tel = request.getParameter("MEMBER_TEL");
		String mail = request.getParameter("MEMBER_MAIL");
		String zipCode = request.getParameter("MEMBER_ZIPCODE");
		String add1 = (request.getParameter("MEMBER_ADD_1"));
		String add2 = request.getParameter("MEMBER_ADD_2");
		String add3 = request.getParameter("MEMBER_ADD_3");
		String memberType = request.getParameter("MEMBER_TYPE");

		if (name == null || name.isEmpty()) {// 必須チェック（氏名）
			errorMsg.add("氏名を入力してください。");
		} else if (name.length() > 32) {// 桁数チェック（氏名）
			errorMsg.add("氏名は32文字以内まで入力してください。");
		}

		if (nameKana == null || nameKana.isEmpty()) {// 必須チェック（氏名（カナ））
			errorMsg.add("氏名（カナ）を入力してください。");
		} else if (nameKana.length() > 32) {// 桁数チェック（氏名（カナ））
			errorMsg.add("氏名（カナ）は32文字以内まで入力してください。");
		}

		if (tel == null || tel.isEmpty()) {// 必須チェック（TEL）
			errorMsg.add("TELを入力してください。");
		} else if (tel.length() > 16) {// 桁数チェック（TEL）
			errorMsg.add("TELは16文字以内まで入力してください。");
		}

		if (mail == null || mail.isEmpty()) {// 必須チェック（メールアドレス）
			errorMsg.add("メールアドレスを入力してください。");
		} else if (mail.length() > 32) {// 桁数チェック（メールアドレス）
			errorMsg.add("メールアドレスは128文字以内まで入力してください。");
		}

		if (zipCode == null || zipCode.length() <= 6) {// 必須チェック（郵便番号）
			errorMsg.add("郵便番号を入力してください。");
		}

		if (add1 == null || add1.isEmpty()) {// 必須チェック（都道府県）
			errorMsg.add("都道府県を選択してください。");
		} else if (add1.length() > 128) {// 桁数チェック（都道府県）
			errorMsg.add("都道府県は128文字以内まで入力してください。");
		}

		if (add2 == null || add2.isEmpty()) {// 必須チェック（市区町村）
			errorMsg.add("市区町村を選択してください。");
		} else if (add2.length() > 128) {// 桁数チェック（市区町村）
			errorMsg.add("市区町村は128文字以内まで入力してください。");
		}

		if (add3 == null || add3.isEmpty()) {// 必須チェック（丁目、番地、建物名）
			errorMsg.add("丁目、番地、建物名を選択してください。");
		} else if (add3.length() > 128) {// 桁数チェック（丁目、番地、建物名）
			errorMsg.add("丁目、番地、建物名は128文字以内まで入力してください。");
		}

		if (memberType == null || memberType.isEmpty()) {// 必須チェック（会員種別）
			errorMsg.add("会員種別を選択してください。");
		}

		return errorMsg;
	}

}