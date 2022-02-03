<%@ page contentType="text/html; charset=UTF-8" %>
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
function check(){
	var id=joinform.MEMBER_ID.value;
	var password1=joinform.MEMBER_PW1.value;
	var password2=joinform.MEMBER_PW2.value;
	var tel=joinform.MEMBER_TEL.value;
	var mail1=joinform.MEMBER_MAIL1.value;
	var mail2=joinform.MEMBER_MAIL2.value;
	var add_1=joinform.MEMBER_ADD_1.value;
	var add_2=joinform.MEMBER_ADD_2.value;
	var add_3=joinform.MEMBER_ADD_3.value;
	
	var forms = document.getElementById("joinform");

	if(id.length == 0){
		alert("アカウントを入力してください。");
		joinform.MEMBER_ID.focus();
		return false;
	}
	if(password1.length == 0){
		alert("パスワードを入力してください。");
		joinform.MEMBER_PW1.focus();
		return false;
	}
	if(password2.length == 0){
		alert("パスワード確認を入力してください。");
		joinform.MEMBER_PW2.focus();
		return false;
	} 
	if(password1 != password2){
		alert("パスワードが一致していません。");
		joinform.MEMBER_PW1.value="";
		joinform.MEMBER_PW2.value="";
		joinform.MEMBER_PW1.focus();
		joinform.MEMBER_PW2.focus();
		return false;
	}
	if ((forms.MEMBER_NAME.value=="")||(forms.MEMBER_NAME.value.length<=1)){
		alert("氏名を入力してください。");
		forms.MEMBER_NAME.focus();
		return false;
	}
	if ((forms.MEMBER_NAME_KANA.value=="")||(forms.MEMBER_NAME_KANA.value.length<=1)){
		alert("氏名（カナ）を入力してください。");
		forms.MEMBER_NAME_KANA.focus();
		return false;
	}
	if(tel.length == 0){
		alert("電話番号を入力してください。");
		joinform.MEMBER_TEL.focus();
		return false;
	}
	if(mail1.length == 0 || mail2.length ==0){
		alert("メールアドレスを入力してください。");
		joinform.MEMBER_MAIL1.focus();
		joinform.MEMBER_MAIL2.focus();
		return false;
	} 
	if((forms.MEMBER_ZIPCODE.value=="")||(forms.MEMBER_ZIPCODE.value.length<6)){
		alert("郵便番号を入力してください。");
      	forms.MEMBER_ZIPCODE.focus();
        return false;
 	} 
	if(addr_1.length == 0){
		alert("都道府県を選択してください。");
		joinform.MEMBER_ADD_1.focus();
		return false;
	} 
	if(add_2.length == 0){
		alert("市区町村を入力してください。");
		joinform.MEMBER_ADD_2.focus();
		return false;
	}
	if(add_3.length == 0){
		alert("丁目、番地、建物名を入力してください。");
		joinform.MEMBER_ADD_3.focus();
		return false;
	}
	
	return true;
}

function openConfirmId(joinform){			
	var id=joinform.MEMBER_ID.value;
	var url="./MemberIDCheckAction.member?MEMBER_ID="+joinform.MEMBER_ID.value;
	
	if(id.length == 0){
		alert("アカウントを入力してください。");
		joinform.MEMBER_ID.focus();
		return false;
	}
	open(url, "confirm", "toolbar=no,location=no,status=no,menubar=no,"+
						 "scrollbars=no,resizable=no,width=500,height=200");
}

function openZipcode(joinform){			
	var url="./Zipcode.member"
	open(url, "confirm", "toolbar=no,location=no,"
						+"status=no,menubar=no,"
						+"scrollbars=yes,resizable=no,"
						+"width=500,height=400");
}	

function gNumCheck(){
	if(event.keyCode >=48 && event.keyCode <=57) {
		return true;
	}else{
		event.returnValue=false;	
	}
}		
</script>
</head>
<body>
<div name="main" style="margin:30 0 50 0">
<table width="990" cellspacing="0" cellpadding="0" border="0" align="center">
	<tr>
	<td colspan=2>
	<!-- 会員登録 -->
	<form id="joinform" name="joinform" action="./MemberJoinAction.member" method="post" 
		onsubmit="return check()">		
	<p align="center">	
	<table width="100%" height="80%">
	<tr>
		<td bgcolor="#f5f5f5">
			<font size="2">&nbsp;&nbsp;&nbsp;&nbsp;アカウント</font>
		</td>
		<td>
			&nbsp;&nbsp;&nbsp;
			<input type="text" name="MEMBER_ID" size="15" maxlength=15/>
			<input type="button" name="confirm_id" value="アカウント確認" 
				onclick="openConfirmId(this.form)" />
		</td>
	</tr>
	<tr>
		<td bgcolor="#f5f5f5">
			<font size="2">&nbsp;&nbsp;&nbsp;&nbsp;パスワード</font>
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
		<font size="2">&nbsp;&nbsp;&nbsp;
		（アカウント／パスワードは英字、数字を組み合わせ２～１２桁で作成お願いします。）</font>
		</td>
	</tr>
	<tr>
		<td width="17%" bgcolor="#f5f5f5">
			<font size="2">&nbsp;&nbsp;&nbsp;&nbsp;氏名</font>
		</td>
		<td>
			&nbsp;&nbsp;&nbsp;
			<input type="text" name="MEMBER_NAME" size="20" placeholder="図書　太郎"/>
		</td>
	</tr>
	<tr>
		<td bgcolor="#f5f5f5">
			<font size="2">&nbsp;&nbsp;&nbsp;&nbsp;氏名（カナ）</font>
		</td>
		<td>
			&nbsp;&nbsp;&nbsp;
			<input type="text" name="MEMBER_NAME_KANA" size="20" placeholder="トショ　タロウ"/>
		</td>
	</tr>
	<tr>
		<td bgcolor="#f5f5f5">
			<font size="2">&nbsp;&nbsp;&nbsp;&nbsp;電話番号</font>
		</td>
		<td>
			&nbsp;&nbsp;&nbsp;
			<input type="text" name="MEMBER_TEL" size="11" placeholder="0120000000"/>
		</td>
	</tr>
	<tr>
		<td bgcolor="#f5f5f5">
			<font size="2">&nbsp;&nbsp;&nbsp;&nbsp;メールアドレス</font>
		</td>
		<td>
			&nbsp;&nbsp;&nbsp;
			<input type="text" name="MEMBER_MAIL1" size="15" placeholder="tosyo"/>@
			<input type="text" name="MEMBER_MAIL2" size="20" placeholder="nuvo.co.jp"/> 
		</td>
		</tr>
		<tr>
			<td bgcolor="#f5f5f5">
				<font size="2">&nbsp;&nbsp;&nbsp;&nbsp;郵便番号</font>
			</td>
			<td>
				&nbsp;&nbsp;&nbsp;
				<input type="text" name="MEMBER_ZIPCODE" size="7" onkeypress="gNumCheck()" maxlength="7" placeholder="1230123"/>&nbsp;&nbsp;
				<input type="button" name="zipcode" value="郵便番号検索" onclick="openZipcode(this.form)" />
			</td>
		</tr>
		<tr>
			<td bgcolor="#f5f5f5">
				<font size="2">&nbsp;&nbsp;&nbsp;&nbsp;都道府県</font>
			</td>
			<td>
				&nbsp;&nbsp;&nbsp;
				<select name="MEMBER_ADD_1">
               		<option value="">　　　　　　　</option>
					<option value="北海道">北海道</option>
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
				<input type="text" name="MEMBER_ADD_2" size="50" />
			</td>
		</tr>
		<tr>
			<td bgcolor="#f5f5f5">
				<font size="2">&nbsp;&nbsp;&nbsp;&nbsp;丁目、番地、建物名</font>
			</td>
			<td>
				&nbsp;&nbsp;&nbsp;
				<input type="text" name="MEMBER_ADD_3" size="80" />
			</td>
		</tr>
		</table>
		<table width="80%">
			<tr>
				<td align="center">
					<br/><input type="submit" value="確認" />
				</td>
			</tr>
		</table>
		</form>
		<!-- 会員登録 -->	
		</td>
	</tr>
</table>
</div>
</body>
</html>