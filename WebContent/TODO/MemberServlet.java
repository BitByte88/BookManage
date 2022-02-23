package la.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.RandomStringUtils;

import com.ibm.icu.text.Transliterator;

import la.dao.Common;
import la.dao.DAOException;
import la.dao.MemberDAO;
import la.dao.NotFoundException;
import la.dto.MemberBean;

@WebServlet("/MemberServlet")
public class MemberServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");

		String action = request.getParameter("action");
		String mode = request.getParameter("mode");

		HttpSession session = request.getSession(false);

		// セッション管理------------------------------------------------
		// ログインしているかどうか確認
		// ------------------------------------------------------------
		// セッションオブジェクトなし
		if (session == null) {
			request.setAttribute("errMsg", "セッションが切れています。もう一度ログインしてください。");
			gotoPage(request, response, "/login.jsp");
			return;
		}
		// sessionあり
		MemberBean loginMember = (MemberBean) session.getAttribute("loginMember");
		if (loginMember == null) {
			// loginしてない
			request.setAttribute("errMsg", "ログインしてください。");
			gotoPage(request, response, "/menu.jsp");
			return;
		}

		// 会員区分の確認をする
		if (!loginMember.getMembership().equals("0")) {
			request.setAttribute("errMsg", "閲覧権限がありません。メニューを選びなおしてください。");
			gotoPage(request, response, "/menu.jsp");
			return;
		}

		// メニューに戻るリンク押下 セッションを切る
		if (action.equals("backToMenu")) {
			session.removeAttribute("member");
			gotoPage(request, response, "/menu.jsp");
		}

		// ------------------------------------------------------------
		// 会員登録内容入力ページで[確認]ボタン押下
		// -> 入力データをチェックして、OKなら memberRegistConfirm.jspへ遷移する
		// ------------------------------------------------------------
		if (action.equals("確認") && mode.equals("Regist")) {
			chkRegistInput(request, response, session);
			return;
		}

		// ------------------------------------------------------------
		// 新規 [登録]ボタン押下
		// -> memberレコード追加し、memberRegistDone.jspへ遷移する
		// ------------------------------------------------------------
		if (action.equals("登録") && mode.equals("Regist")) {
			confirmInsertMem(request, response, session);
			return;
		}

		// ------------------------------------------------------------
		// [修正]ボタン押下
		// -> 入力内容を保持し、memberRegist.jspへ遷移する
		// ------------------------------------------------------------
		if (action.equals("修正") && mode.equals("Regist")) {
			gotoPage(request, response, "/memberRegist.jsp");
			return;
		}

		// ------------------------------------------------------------
		// [検索]ボタン押下
		// -> テキストボックスの名前またはメールアドレスを含む検索
		// ------------------------------------------------------------
		if (action.equals("検索") && mode.equals("search")) {
			Common.searchMem(request, response, session);
			gotoPage(request, response, "/memberSearchResult.jsp");
			return;
		}
		
		
		// 貸出で検索
		if (action.equals("検索") && mode.equals("Rental")) {
			Common.searchMem(request, response, session);
			gotoPage(request, response, "/rentalInput.jsp");
			return;
		}

		// ------------------------------------------------------------
		// 検索結果(memberSearchResult.jsp)で[変更]ボタン押下
		// -> memberUpdate.jspへ遷移する
		// ------------------------------------------------------------
		if (action.equals("変更")) {
			updtInputMem(request, response, session);
			return;
		}

		// ------------------------------------------------------------
		// 変更内容入力(memberUpdate.jsp)で[確認]ボタン押下
		// -> 入力データをチェックして、OKなら memberUpdateConfirm.jspへ遷移する
		// ------------------------------------------------------------
		if (action.equals("確認") && mode.equals("Update")) {
			checkUpdtMem(request, response, session);
			return;
		}

		// ------------------------------------------------------------
		// 更新 [登録]ボタン押下
		// -> memberレコード上書し、memberUpdateDone.jspへ遷移する
		// ------------------------------------------------------------
		if (action.equals("登録") && mode.equals("Update")) {
			confirmUpdtMem(request, response, session);
			return;
		}

		// ------------------------------------------------------------
		// [修正]ボタン押下
		// -> 入力内容を保持し、memberRegist.jspへ遷移する
		// ------------------------------------------------------------
		if (action.equals("修正") && mode.equals("Update")) {
			gotoPage(request, response, "/memberUpdate.jsp");
			return;
		}

		// ------------------------------------------------------------
		// 検索結果 [退会]ボタン押下
		// memberLeave.jspへ遷移
		// ------------------------------------------------------------
		if (action.equals("退会") && mode.equals("SearchResult")) {
			checkWithdrawMem(request, response, session);
			return;
		}

		// ------------------------------------------------------------
		// [退会]ボタン押下 memberテーブルへ退会年月日を追加
		// ->memberLeaveDone.jspへ遷移
		// ------------------------------------------------------------
		if ( action.equals("退会") && mode.equals("Leave")) {
			confirmWithdrawMem(request, response, session);
			return;
		}

		// ------------------------------------------------------------
		// [キャンセル]ボタン押下
		// memberSearchResult.jspへ遷移する
		// ------------------------------------------------------------
		if ( action.equals("キャンセル") && mode.equals("Leave")) {
			gotoPage(request, response, "/memberSearch.jsp");
			return;
		}
		
		else {
			// actionの値が不正
			request.setAttribute("error", "正しく操作してください。(action=" + action + ")");
			gotoPage(request, response, "/rentalInput.jsp");
		}
	}

	/**
	 * 1 登録時 - emailがmemberに登録されていなければtrueを返す<br>
	 * 更新時 - emailがmemberに登録されていない or 自分のemailならtrueを返す<br>
	 *
	 * @param email
	 * @param isUpdate
	 * @param id
	 * @return
	 */
private boolean isEmailExists(String email, boolean isUpdate, int id) {
	Connection con = null;
	boolean bErrFlag = false;
	try {
		con = Common.getConnection();
		MemberDAO memberDAO = new MemberDAO();
		// メールアドレスのレコード数をfindByEmailにて検索
		MemberBean memberBean = memberDAO.findByEmail(con, email);
		// 該当するemailがあった場合
		if (isUpdate) {
			// 更新
			if (memberBean.getId() == id) {
				// IDが自身と一致
				bErrFlag = false;
			} else {
				// IDが自身と一致しない
				bErrFlag = true;
			}

		} else {
			// 登録済みならエラー
			bErrFlag = true;
		}

	} catch (NotFoundException e) {
		// 該当するemail無し
		bErrFlag = false;

	} catch (DAOException | SQLException e) {
		e.printStackTrace();
	}
	return bErrFlag;
}

/**
 * 日付文字列が正しい日付かチェック
 *
 * @param b
 *
 * @param str ymd
 * @return true:正しい日付 false:不正な日付
 */
public static boolean isYmd(String ymd) {
	try {
		// 日付チェック
		SimpleDateFormat birthdate = new SimpleDateFormat("yyyy/MM/dd");
		birthdate.setLenient(false);
		birthdate.parse(ymd);

		return true;

	} catch (Exception e) {
		return false;
	}
}

	// ページを送る
private void gotoPage(HttpServletRequest request, HttpServletResponse response, String page)
			throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher(page);
		rd.forward(request, response);
	}

protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
}

// 登録用データチェック
private void chkRegistInput(	HttpServletRequest request, 
								HttpServletResponse response, 
								HttpSession session) throws ServletException, IOException
{
	// 入力フォーム変数（登録用に抽出する）------------------------------------------
	String membership = request.getParameter("membership");
	String name = request.getParameter("name");
	String birthdate = request.getParameter("birthdate");
	String postalL = request.getParameter("postalL");
	String postalR = request.getParameter("postalR");
	String address = request.getParameter("address");
	String telL = request.getParameter("telL");
	String telM = request.getParameter("telM");
	String telR = request.getParameter("telR");
	String email = request.getParameter("email");

	// 会員登録フォームの入力チェック---------------------------------
	// -> memberRegist.jspへ処理を返す
	// ------------------------------------------------------------
	boolean bRet = true;
	bRet = isRegistInputValid(request, response, session);
	
	if (bRet != true) {
		return;
	}


	// MemberBeanへ変換-----------------------------------------
	// -> テーブル登録準備 文字変換・ランダムパスワード生成
	// ------------------------------------------------------------
	MemberBean member = new MemberBean();
	// 名前
	member.setName(name);
	// 郵便番号
	member.setPostal(postalL + postalR);
	// 住所
	member.setAddress(address);
	// 電話番号(全角だった場合は、外部ライブラリオブジェクトのTransliteratorを使い半角に変換する)
	Transliterator fullToHalf = Transliterator.getInstance("Fullwidth-Halfwidth");
	String strTel = fullToHalf.transliterate(telL) + "-" + 
					fullToHalf.transliterate(telM) + "-" + 
					fullToHalf.transliterate(telR);
	member.setTel(strTel);
	// メールアドレス
	member.setEmail(email);
	// 生年月日
	member.setBirthday(java.sql.Date.valueOf(birthdate.replaceAll("/", "-")));
	// 登録年月日
	member.setSubscribeDate(new java.sql.Date(System.currentTimeMillis()));
	// 退会年月日
	member.setUnsubscribeDate(null);
	// ランダムパスワード
	member.setPassword(RandomStringUtils.randomAlphanumeric(8).toUpperCase());
	// 会員区分
	member.setMembership(membership);

	// 呼び出し
	session.setAttribute("member", member);
	gotoPage(request, response, "/memberRegistConfirm.jsp");

	return;
}


private boolean isRegistInputValid(	HttpServletRequest request, 
									HttpServletResponse response, 
									HttpSession session) throws ServletException, IOException {
	// 入力フォーム変数（エラーチェック用に抽出する）------------------------------------------
	String membership = request.getParameter("membership");
	String name = request.getParameter("name");
	String birthdate = request.getParameter("birthdate");
	String postalL = request.getParameter("postalL");
	String postalR = request.getParameter("postalR");
	String address = request.getParameter("address");
	String telL = request.getParameter("telL");
	String telM = request.getParameter("telM");
	String telR = request.getParameter("telR");
	String email = request.getParameter("email");

	int iErrFlag = 0;

	// 会員区分
	if (membership == null) {
		String errMsg = "";
		errMsg = " ※会員区分が選択されていません";
		request.setAttribute("registErr1", errMsg);
		iErrFlag += 1;
	}
	// 名前
	if (name == null || name.length() == 0) {
		String errMsg = "";
		errMsg = " ※名前が入力されていません";
		request.setAttribute("registErr2", errMsg);
		iErrFlag += 1;
	}
	if (name.contains(" ") || name.contains("　")) {
		String errMsg = "";
		errMsg = " ※名前に空白が含まれています。";
		request.setAttribute("registErr2", errMsg);
		iErrFlag += 1;
	}

	// 生年月日
	if (birthdate == null || birthdate.length() == 0) {
		String errMsg = "";
		errMsg = " ※生年月日が入力されていません";
		request.setAttribute("registErr3", errMsg);
		iErrFlag += 1;
	} else if (birthdate.length() > 10) {
		String errMsg = "";
		errMsg = " ※文字数オーバーです";
		request.setAttribute("registErr3", errMsg);
		iErrFlag += 1;
	} else if (!isYmd(birthdate)) {
		String errMsg = "";
		errMsg = " ※yyyy/mm/dd形式で入力してください";
		request.setAttribute("registErr3", errMsg);
		iErrFlag += 1;
	}

	// 郵便番号
	if (postalL == null || postalL.length() == 0 || postalR == null || postalR.length() == 0) {
		String errMsg = "";
		errMsg = " ※郵便番号が入力されていません";
		request.setAttribute("registErr4", errMsg);
		iErrFlag += 1;
	} else if (postalL.length() + postalR.length() > 7) {
		String errMsg = "";
		errMsg = " ※文字数オーバーです";
		request.setAttribute("registErr4", errMsg);
		iErrFlag += 1;
	}

	// 住所
	if (address == null || address.length() == 0) {
		String errMsg = "";
		errMsg = " ※住所が入力されていません";
		request.setAttribute("registErr5", errMsg);
		iErrFlag += 1;
	}

	// 電話番号
	if (telL == null || telL.length() == 0 || telM == null || telM.length() == 0 || telR == null
			|| telR.length() == 0) {
		String errMsg = "";
		errMsg = " ※電話番号が入力されていません";
		request.setAttribute("registErr6", errMsg);
		iErrFlag += 1;
	} else if (telL.length() + telM.length() + telR.length() > 11) {
		String errMsg = "";
		errMsg = " ※文字数オーバーです";
		request.setAttribute("registErr6", errMsg);
		iErrFlag += 1;
	}

	// メールアドレス
	if (email == null || email.length() == 0) {
		String errMsg = "";
		errMsg = " ※メールアドレスが入力されていません";
		request.setAttribute("registErr7", errMsg);
		iErrFlag += 1;
	} else if (email.length() > 50) {
		String errMsg = "";
		errMsg = " ※文字数オーバーです";
		request.setAttribute("registErr7", errMsg);
		iErrFlag += 1;
	} else if (!(email.contains("@"))) {
		String errMsg = "";
		errMsg = " ※@が入力されていません";
		request.setAttribute("registErr7", errMsg);
		iErrFlag += 1;
	} else if (isEmailExists(email, false, 0)) {
		String errMsg = "";
		errMsg = " ※このアドレスは登録済みです。";
		request.setAttribute("registErr7", errMsg);
		iErrFlag += 1;
	}
	// memberRegist.jspへ処理を返す
	if (iErrFlag != 0) {
		gotoPage(request, response, "/memberRegist.jsp");
		return false;
	} else {
		return true;
	}
}


// 登録確定
private void confirmInsertMem(HttpServletRequest request,
								HttpServletResponse response, 
		HttpSession session) throws ServletException, IOException
{
	MemberBean member = (MemberBean) session.getAttribute("member");
	Connection con = null;

	try {
		con = Common.getConnection();
		MemberDAO dao = new MemberDAO();
		dao.create(con, member);

	} catch (DAOException | NotFoundException | SQLException e) {
		e.printStackTrace();
	}

	gotoPage(request, response, "/memberRegistDone.jsp");
	return;
}


private void updtInputMem(	HttpServletRequest request, 
								HttpServletResponse response, 
								HttpSession session) throws ServletException, IOException 
{
	// サーバーから値を取得
	String idw = (String) request.getParameter("idw");

	try {
		MemberDAO memberDAO = new MemberDAO();
		MemberBean member = memberDAO.findByPKey(Integer.parseInt(idw));

		session.setAttribute("member", member);

		// memberUpdate.jspへ値を返す
		gotoPage(request, response, "/memberUpdate.jsp");
		return;

	} catch (DAOException | NotFoundException | SQLException e) {
		e.printStackTrace();
	}	
}

private void checkUpdtMem(HttpServletRequest request, 
							HttpServletResponse response, 
							HttpSession session) throws ServletException, IOException
{
	// 入力フォーム変数------------------------------------------
	String membership = request.getParameter("membership");
	String name = request.getParameter("name");
	String birthdate = request.getParameter("birthdate");
	String postalL = request.getParameter("postalL");
	String postalR = request.getParameter("postalR");
	String address = request.getParameter("address");
	String telL = request.getParameter("telL");
	String telM = request.getParameter("telM");
	String telR = request.getParameter("telR");
	String email = request.getParameter("email");

	// 会員登録フォームの入力チェック---------------------------------
	// -> member.Updatejspへ処理を返す
	// ------------------------------------------------------------
	boolean bRet = true;
	bRet = isUpdtInputValid(request, response, session);

	// memberRegist.jspへ処理を返す
	if (bRet != true) {
		gotoPage(request, response, "/memberUpdate.jsp");
		return;
	}

	// MemberBeanへ変換-----------------------------------------
	// -> テーブル登録準備 文字変換
	// ------------------------------------------------------------
	else {
		MemberBean member = new MemberBean();
		// 名前
		member.setName(name);
		// 郵便番号
		member.setPostal(postalL + postalR);
		// 住所
		member.setAddress(address);
		// 電話番号
		member.setTel(telL + "-" + telM + "-" + telR);
		// メールアドレス
		member.setEmail(email);
		// 生年月日
		member.setBirthday(java.sql.Date.valueOf(birthdate.replaceAll("/", "-")));
		// 退会年月日
		member.setUnsubscribeDate(null);
		// 会員区分
		member.setMembership(membership);

		// 呼び出し
		MemberBean oldMember = (MemberBean) session.getAttribute("member");
		// ID
		member.setId(oldMember.getId());
		// PASSWORD
		member.setPassword(oldMember.getPassword());
		// 登録年月日
		member.setSubscribeDate(oldMember.getSubscribeDate());

		// 要素に属性の設定や属性値の変更をする
		session.setAttribute("member", member);
		// ページ遷移
		gotoPage(request, response, "/memberUpdateConfirm.jsp");
		return;
	}
}

// 更新用情報をチェックする
private boolean isUpdtInputValid(	HttpServletRequest request, 
									HttpServletResponse response, 
									HttpSession session) throws ServletException, IOException
{
	String membership = request.getParameter("membership");
	String name = request.getParameter("name");
	String birthdate = request.getParameter("birthdate");
	String postalL = request.getParameter("postalL");
	String postalR = request.getParameter("postalR");
	String address = request.getParameter("address");
	String telL = request.getParameter("telL");
	String telM = request.getParameter("telM");
	String telR = request.getParameter("telR");
	String email = request.getParameter("email");
	
	int iErrFlag = 0;
	String errMsg = "";

	// 会員区分
	if (membership == null) {
		errMsg = " ※会員区分が選択されていません";
		request.setAttribute("updateErr1", errMsg);
		iErrFlag += 1;
	}
	// 名前
	if (name == null || name.length() == 0) {
		errMsg = " ※名前が入力されていません";
		request.setAttribute("updateErr2", errMsg);
		iErrFlag += 1;
	}
	if (name.contains(" ") || name.contains("　")) {
		errMsg = " ※名前に空白が含まれています。";
		request.setAttribute("updateErr2", errMsg);
		iErrFlag += 1;
	}

	// 生年月日
	if (birthdate == null || birthdate.length() == 0) {
		errMsg = " ※生年月日が入力されていません";
		request.setAttribute("updateErr3", errMsg);
		iErrFlag += 1;
	} else if (birthdate.length() > 10) {
		errMsg = " ※文字数オーバーです";
		request.setAttribute("updateErr3", errMsg);
		iErrFlag += 1;
	} else if (!isYmd(birthdate)) {
		errMsg = " ※yyyy/mm/dd形式で入力してください";
		request.setAttribute("updateErr3", errMsg);
		iErrFlag += 1;
	}

	// 郵便番号
	if (postalL == null || postalL.length() == 0 || postalR == null || postalR.length() == 0) {
		errMsg = " ※郵便番号が入力されていません";
		request.setAttribute("updateErr4", errMsg);
		iErrFlag += 1;
	} else if (postalL.length() + postalR.length() > 7) {
		errMsg = " ※文字数オーバーです";
		request.setAttribute("updateErr4", errMsg);
		iErrFlag += 1;
	}

	// 住所
	if (address == null || address.length() == 0) {
		errMsg = " ※住所が入力されていません";
		request.setAttribute("updateErr5", errMsg);
		iErrFlag += 1;
	}

	// 電話番号
	if (telL == null || telL.length() == 0 || telM == null || telM.length() == 0 || telR == null
			|| telR.length() == 0) {
		errMsg = " ※電話番号が入力されていません";
		request.setAttribute("updateErr6", errMsg);
		iErrFlag += 1;
	} else if (telL.length() + telM.length() + telR.length() > 11) {
		errMsg = " ※文字数オーバーです";
		request.setAttribute("updateErr6", errMsg);
		iErrFlag += 1;
	}

	// メールアドレス
	if (email == null || email.length() == 0) {
		errMsg = " ※メールアドレスが入力されていません";
		request.setAttribute("updateErr7", errMsg);
		iErrFlag += 1;
	} else if (email.length() > 50) {
		errMsg = " ※文字数オーバーです";
		request.setAttribute("updateErr7", errMsg);
		iErrFlag += 1;
	} else if (!(email.contains("@"))) {
		errMsg = " ※@が入力されていません";
		request.setAttribute("updateErr7", errMsg);
		iErrFlag += 1;
	} else {
		// 呼び出し
		// このアドレスは登録済みです。
		MemberBean currentMember = (MemberBean) session.getAttribute("member");
		if (isEmailExists(email, true, currentMember.getId())) {
			errMsg = " ※このアドレスは登録済みです。";
			request.setAttribute("updateErr7", errMsg);
			iErrFlag += 1;
		}
	}
	
	if (iErrFlag != 0) {
		gotoPage(request, response, "/memberRegist.jsp");
		return false;
	} else {
		return true;
	}
	
}

// 更新を確定する
private void confirmUpdtMem(	HttpServletRequest request, 
								HttpServletResponse response, 
								HttpSession session) throws ServletException, IOException
{
	MemberBean member = (MemberBean) session.getAttribute("member");
	Connection con = null;

	try {
		con = Common.getConnection();
		MemberDAO dao = new MemberDAO();
		dao.save(member);

	} catch (DAOException | NotFoundException | SQLException e) {
		e.printStackTrace();
	}

	gotoPage(request, response, "/memberUpdateDone.jsp");
	return;
}

// ------------------------------------------------------------
// memberLeave.jspにて会員の登録情報を表示
// ->サーバーから値を取得
// ->memberLeave.jspへ値を返す
// ------------------------------------------------------------
private void checkWithdrawMem(	HttpServletRequest request, 
								HttpServletResponse response, 
								HttpSession session) throws ServletException, IOException
{

	String idw = (String) request.getParameter("idw");


	try {
		MemberDAO memberDAO = new MemberDAO();
		MemberBean member = memberDAO.findByPKey(Integer.parseInt(idw));
		session.setAttribute("member", member);
		// memberUpdate.jspへ
		gotoPage(request, response, "/memberLeave.jsp");
		return;

	} catch (DAOException | NotFoundException | SQLException e) {
		e.printStackTrace();
	}

	gotoPage(request, response, "/memberLeave.jsp");
	return;
}

// 会員退会を確定する
private void confirmWithdrawMem(	HttpServletRequest request, 
									HttpServletResponse response, 
									HttpSession session) throws ServletException, IOException
{


	try {
		MemberDAO memberDAO = new MemberDAO();
		MemberBean member = (MemberBean) session.getAttribute("member");
		// 退会年月日を取得
		member.setUnsubscribeDate(new java.sql.Date(System.currentTimeMillis()));
		// DBに書き込み
		memberDAO.save(member);

	} catch (NotFoundException | SQLException | DAOException e) {
		e.printStackTrace();
	}

	gotoPage(request, response, "/memberLeaveDone.jsp");
	return;
}

}