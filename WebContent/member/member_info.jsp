<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="net.member.db.MemberBean" %>
<%
	MemberBean member=(MemberBean)request.getAttribute("member");
	String MEMBER_MAIL=member.getMEMBER_MAIL();
	String[] email=MEMBER_MAIL.split("@");	//"@"로 구분함
%>
<html>
<head>
	<link rel="icon" href="favicon/favicon.ico" type="image/x-icon" />
	<link rel="shortcut icon" href="favicon/favicon.ico" type="image/x-icon"> 
	<link rel="apple-touch-icon-precomposed" sizes="144x144" href="favicon/favicon-144x144.png">
	<link rel="apple-touch-icon-precomposed" sizes="72x72" href="favicon/favicon-72x72.png">
	<link rel="apple-touch-icon-precomposed" href="favicon/favicon-54x54.png">
	
<title>図書管理システム</title>
<script>
document.getElementsByName("MEMBER_ADD_1").querySelector("option[value='${member.MEMBER_NAME_KANA }']").selected = true;

function check(form){
	var pass1=infoform.MEMBER_PW.value;
	var pass2=infoform.MEMBER_PW2.value;
	var email1=infoform.MEMBER_EMAIL1.value;				
	var email2=infoform.MEMBER_EMAIL2.value;
	var tel=infoform.MEMBER_PHONE.value;
	var addr=infoform.MEMBER_ADDR1.value;
	var addr_about=infoform.MEMBER_ADDR2.value;
	var phone=infoform.MEMBER_MOBILE.value;
	
	var forms = document.getElementById("infoform");

	if ((forms.MEMBER_NAME.value=="")||(forms.MEMBER_NAME.value.length<=1)){
		alert("이름을 입력하세요.");
		forms.MEMBER_NAME.focus();
        return false;
	}
	if(pass1.length == 0){
		alert("비밀번호를 입력하세요.");
		infoform.MEMBER_PW.focus();
		return false;
	} 
	if(pass1 != pass2){
		alert("비밀번호가 일치하지 않습니다.");
		infoform.MEMBER_PW.value="";
		infoform.MEMBER_PW2.value="";
		infoform.MEMBER_PW.focus();
		return false;
	} 				 
	if(email1.length == 0 || email2.length == 0){
		alert("이메일 주소를 입력하세요.");
		infoform.MEMBER_EMAIL1.focus();
		return false;
	}
	if(tel.length == 0){
		alert("집 전화를 입력하세요.");
		infoform.MEMBER_PHONE.focus();
		return false;
	} 
	if((forms.MEMBER_ZIPCODE1.value=="")||(forms.MEMBER_ZIPCODE1.value.length<3)){
		alert("우편번호 앞의 3자리를 입력해 주세요.");
      	forms.MEMBER_ZIPCODE1.focus();
         	return false;
 		}
 	if((forms.MEMBER_ZIPCODE2.value=="")||(forms.MEMBER_ZIPCODE2.value.length<3)){
		alert("우편번호 뒤의 3자리 입력해 주세요.");
      	forms.MEMBER_ZIPCODE2.focus();
        return false;
	}   
	if(addr.length == 0){
		alert("집 주소를 입력하세요.");
		infoform.MEMBER_ADDR1.focus();
		return false;
	} 
	if(addr_about.length == 0){
		alert("상세 주소를 입력하세요.");
		infoform.MEMBER_ADDR2.focus();
		return false;
	} 
	if(phone.length == 0){
		alert("전화번호를 입력하세요.");
		infoform.MEMBER_MOBILE.focus();
		return false;
	}
	
	return true;
}

function openzipcode(joinform){			
	var url="./Zipcode.member";	
	open(url, "confirm", "toolbar=no,location=no,status=no,menubar=no,"+
						 "scrollbars=yes,resizable=no,width=410,height=400");
}

function gNumCheck(){
	if(event.keyCode >=48 && event.keyCode <=57) {
		return true;
	}else{
		event.returnValue=false;	
	}
}

function out(){
	var Answer = confirm("회원탈퇴를 하시겠습니까?");
 		if (Answer == true){ 
 		location.href = "./MemberOut.member";
 	}
 }
</script>
</head>
<body>
<table width="960" cellspacing="0" cellpadding="0" border="0" align="center">
<tr>
<td colspan=2>
<!-- アカウント情報変更 -->
<p align="center">
<form action="./MemberModifyAction_2.member" method="post" name="infoform" 
		onsubmit="return check(this)">
<table border="1" width="80%" height="80%">
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
			<input type="password" name="MEMBER_PW" size="15"/>
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
		<td bgcolor="#f5f5f5">&nbsp;</td>
		<td>
		<font size="2">&nbsp;&nbsp;&nbsp;&nbsp;
		(아이디와 비밀번호는 문자와 숫자를 조합하여 2~12자리로 만들어 주세요)</font>
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
		<input type="text" name="MEMBER_MAIL2" size="15" 
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
			<select name="MEMBER_ADD_1">
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
			<input type="text" name="MEMBER_ADD_3" size="100" 
				value="${member.MEMBER_ADD_3 }" />
		</td>
	</tr>
</table>

<table width="80%">
	<tr>
		<td align="center">
			<br/><input type="submit" value="회원정보 수정" />
			<input type="button" value="회원 탈퇴" name="bt" onclick="out()" />
		</td>
	</tr>
</table>
</form>
<!-- 개인정보 수정 -->
</td>
</tr>	
</table>
</body>
</html>