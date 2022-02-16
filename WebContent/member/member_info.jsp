<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="net.member.db.MemberBean" %>
<%
	MemberBean member=(MemberBean)request.getAttribute("member");
	String MEMBER_MAIL=member.getMEMBER_MAIL();
	String[] email=MEMBER_MAIL.split("@");
%>
<html>
<head>
	<link rel="icon" href="favicon/favicon.ico" type="image/x-icon" />
	<link rel="shortcut icon" href="favicon/favicon.ico" type="image/x-icon"> 
	<link rel="apple-touch-icon-precomposed" sizes="144x144" href="favicon/favicon-144x144.png">
	<link rel="apple-touch-icon-precomposed" sizes="72x72" href="favicon/favicon-72x72.png">
	<link rel="apple-touch-icon-precomposed" href="favicon/favicon-54x54.png">
	<jsp:include page="/menu/menu.jsp" />
	
<title>図書管理システム</title>
<script>
window.onload = function() {
	document.querySelector('#MEMBER_ADD_1').value = "${member.MEMBER_ADD_1 }";
}

function check(form){
	var password1=form.MEMBER_PW1.value;
	var password2=form.MEMBER_PW2.value;
	var tel=form.MEMBER_TEL.value;
	var mail1=form.MEMBER_MAIL1.value;
	var mail2=form.MEMBER_MAIL2.value;
	var add_1=form.MEMBER_ADD_1.value;
	var add_2=form.MEMBER_ADD_2.value;
	var add_3=form.MEMBER_ADD_3.value;

	if(password1.length == 0){
		alert("パスワードを入力してください。");
		form.MEMBER_PW1.focus();
		return false;
	}
	if(password2.length == 0){
		alert("パスワード確認を入力してください。");
		form.MEMBER_PW2.focus();
		return false;
	}
	if(password1 != password2){
		alert("パスワードが一致していません。");
		form.MEMBER_PW1.value="";
		form.MEMBER_PW2.value="";
		form.MEMBER_PW1.focus();
		form.MEMBER_PW2.focus();
		return false;
	}
	if ((form.MEMBER_NAME.value=="")||(form.MEMBER_NAME.value.length<=1)){
		alert("氏名を入力してください。");
		form.MEMBER_NAME.focus();
		return false;
	}
	if ((form.MEMBER_NAME_KANA.value=="")||(form.MEMBER_NAME_KANA.value.length<=1)){
		alert("氏名（カナ）を入力してください。");
		form.MEMBER_NAME_KANA.focus();
		return false;
	}
	if(tel.length == 0){
		alert("電話番号を入力してください。");
		form.MEMBER_TEL.focus();
		return false;
	}
	if(mail1.length == 0 || mail2.length ==0){
		alert("メールアドレスを入力してください。");
		form.MEMBER_MAIL1.focus();
		form.MEMBER_MAIL2.focus();
		return false;
	} 
	if((form.MEMBER_ZIPCODE.value=="")||(form.MEMBER_ZIPCODE.value.length<6)){
		alert("郵便番号を入力してください。");
      	form.MEMBER_ZIPCODE.focus();
        return false;
 	} 
	if(add_1.length == 0){
		alert("都道府県を選択してください。");
		form.MEMBER_ADD_1.focus();
		return false;
	} 
	if(add_2.length == 0){
		alert("市区町村を入力してください。");
		form.MEMBER_ADD_2.focus();
		return false;
	}
	if(add_3.length == 0){
		alert("丁目、番地、建物名を入力してください。");
		form.MEMBER_ADD_3.focus();
		return false;
	}
	
	return true;
}

function openzipcode(joinform){			
	var url="./Zipcode.member";	
	open(url, "confirm", "toolbar=no,location=no,status=no,menubar=no,"+
						 "scrollbars=yes,resizable=no,width=500,height=400");
}

function gNumCheck(){
	if(event.keyCode >=48 && event.keyCode <=57) {
		return true;
	}else{
		event.returnValue=false;	
	}
}

function out(){
	var Answer = confirm("退会しますか");
 		if (Answer == true){ 
 		location.href = "./MemberOut.member";
 	}
 }
</script>
</head>
<body>
<div style="margin:30 0 50 0">
<table style="width:960; margin:auto">
<tr>
<td colspan=2>
<!-- 会員情報変更 -->
<p align="center">
<form action="./MemberModifyAction_2.member" method="post" name="infoform" 
		onsubmit="return check(this)">
<table style="width:100%; height:80%">
	<tr>
		<td bgcolor="#f5f5f5">
			<font size="2">&nbsp;&nbsp;&nbsp;&nbsp;アカウント</font>
		</td>
		<td>
			&nbsp;&nbsp;&nbsp;&nbsp;${member.MEMBER_ID }
		</td>
	</tr>
	<tr>
		<td bgcolor="#f5f5f5">
			<font size="2">&nbsp;&nbsp;&nbsp;パスワード</font>
		</td>
		<td>
			&nbsp;&nbsp;&nbsp;
			<input type="password" name="MEMBER_PW1" size="15"/>
		</td>
	</tr>
	<tr>
		<td bgcolor="#f5f5f5">
			<font size="2">&nbsp;&nbsp;&nbsp;&nbsp;パスワード確認</font>
		</td>
		<td>
			&nbsp;&nbsp;&nbsp;
			<input type="password" name="MEMBER_PW2" size="15" />
		</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td>
		<font size="2">&nbsp;&nbsp;&nbsp;&nbsp;
		（アカウント／パスワードは英字、数字を組み合わせ２～１２桁で作成お願いします。）</font>
		</td>
	</tr>
	<tr>
		<td width="17%" bgcolor="#f5f5f5">
			<font size="2">&nbsp;&nbsp;&nbsp;&nbsp;氏名</font>
		</td>
		<td>
			&nbsp;&nbsp;&nbsp;
			<input type="text" name="MEMBER_NAME" 
				size="20" value="${member.MEMBER_NAME }" />
		</td>
	</tr>
	<tr>
		<td width="17%" bgcolor="#f5f5f5">
			<font size="2">&nbsp;&nbsp;&nbsp;&nbsp;氏名（カナ）</font>
		</td>
		<td>
			&nbsp;&nbsp;&nbsp;
			<input type="text" name="MEMBER_NAME_KANA" 
				size="20" value="${member.MEMBER_NAME_KANA }" />
		</td>
	</tr>
	<tr>
		<td bgcolor="#f5f5f5">
			<font size="2">&nbsp;&nbsp;&nbsp;&nbsp;電話番号</font>
		</td>
		<td>
			&nbsp;&nbsp;&nbsp;
			<input type="text" name="MEMBER_TEL" size="24" 
				value="${member.MEMBER_TEL }" />
		</td>
	</tr>
	<tr>
		<td bgcolor="#f5f5f5">
			<font size="2">&nbsp;&nbsp;&nbsp;&nbsp;メールアドレス</font>
		</td>
		<td>
		&nbsp;&nbsp;&nbsp;
		<input type="text" name="MEMBER_MAIL1" size="15" 
			value="<%=email[0].trim() %>" /> @ 
		<input type="text" name="MEMBER_MAIL2" size="20" 
			value="<%=email[1].trim() %>" />
		</td>
	</tr>
	<tr>
		<td bgcolor="#f5f5f5">
			<font size="2">&nbsp;&nbsp;&nbsp;&nbsp;郵便番号</font>
		</td>
		<td>
			&nbsp;&nbsp;&nbsp;
			<input type="text" name="MEMBER_ZIPCODE" 
				size="7" onkeypress="gNumCheck()" 
				maxlength="7" value="${member.MEMBER_ZIPCODE }" />&nbsp;&nbsp;
			<input type="button" name="zipcode" 
				value="郵便番号検索" onclick="openzipcode(this.form)" />
		</td>
	</tr>
	<tr>
		<td bgcolor="#f5f5f5">
			<font size="2">&nbsp;&nbsp;&nbsp;&nbsp;都道府県</font>
		</td>
		<td>
			&nbsp;&nbsp;&nbsp;
			<select id="MEMBER_ADD_1" name="MEMBER_ADD_1">
				<option value="北海道" >北海道</option>
				<option value="青森県">青森県</option>
				<option value="岩手県">岩手県</option>
				<option value="宮城県">宮城県</option>
				<option value="秋田県">秋田県</option>
				<option value="山形県">山形県</option>
				<option value="福島県">福島県</option>
				<option value="茨城県">茨城県</option>
				<option value="栃木県">栃木県</option>
				<option value="群馬県">群馬県</option>
				<option value="埼玉県">埼玉県</option>
				<option value="千葉県">千葉県</option>
				<option value="東京都">東京都</option>
				<option value="神奈川県">神奈川県</option>
				<option value="新潟県">新潟県</option>
				<option value="富山県">富山県</option>
				<option value="石川県">石川県</option>
				<option value="福井県">福井県</option>
				<option value="山梨県">山梨県</option>
				<option value="長野県">長野県</option>
				<option value="岐阜県">岐阜県</option>
				<option value="静岡県">静岡県</option>
				<option value="愛知県">愛知県</option>
				<option value="三重県">三重県</option>
				<option value="滋賀県">滋賀県</option>
				<option value="京都府">京都府</option>
				<option value="大阪府">大阪府</option>
				<option value="兵庫県">兵庫県</option>
				<option value="奈良県">奈良県</option>
				<option value="和歌山県">和歌山県</option>
				<option value="鳥取県">鳥取県</option>
				<option value="島根県">島根県</option>
				<option value="岡山県">岡山県</option>
				<option value="広島県">広島県</option>
				<option value="山口県">山口県</option>
				<option value="徳島県">徳島県</option>
				<option value="香川県">香川県</option>
				<option value="愛媛県">愛媛県</option>
				<option value="高知県">高知県</option>
				<option value="福岡県">福岡県</option>
				<option value="佐賀県">佐賀県</option>
				<option value="長崎県">長崎県</option>
				<option value="熊本県">熊本県</option>
				<option value="大分県">大分県</option>
				<option value="宮崎県">宮崎県</option>
				<option value="鹿児島県">鹿児島県</option>
				<option value="沖縄県">沖縄県</option>
            </select>
		</td>
	</tr>
	<tr>
		<td bgcolor="#f5f5f5">
			<font size="2">&nbsp;&nbsp;&nbsp;&nbsp;市区町村</font>
		</td>
		<td>
			&nbsp;&nbsp;&nbsp;
			<input type="text" name="MEMBER_ADD_2" size="50" 
				value="${member.MEMBER_ADD_2 }" />
		</td>
	</tr>
	<tr>
		<td bgcolor="#f5f5f5">
			<font size="2">&nbsp;&nbsp;&nbsp;&nbsp;丁目、番地、建物名</font>
		</td>
		<td>
			&nbsp;&nbsp;&nbsp;
			<input type="text" name="MEMBER_ADD_3" size="80" 
				value="${member.MEMBER_ADD_3 }" />
		</td>
	</tr>
</table>

<table style="width:80%">
	<tr>
		<td align="center">
			<br/><input type="submit" value="会員情報変更" />
			<input type="button" value="退会" name="bt" onclick="out()" />
		</td>
	</tr>
</table>
</form>
<!-- 会員情報変更 -->
</td>
</tr>	
</table>
</div>
</body>
</html>