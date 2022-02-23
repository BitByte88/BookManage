<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="net.member.db.*"%>
<%@ page import="java.text.*"%>
<%  
	MemberBean member=(MemberBean)request.getAttribute("member");
	DecimalFormat format = new DecimalFormat("0");
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
	document.querySelector('#member_type').value = "<%=member.getMEMBER_TYPE()%>";
	document.querySelector('#member_add_1').value = "<%=member.getMEMBER_ADD_1()%>";
}
function check(){
	var name=memberform.MEMBER_NAME.value;
	var nameKana=memberform.MEMBER_NAME_KANA.value;
	var tel=memberform.MEMBER_TEL.value;
	var mail=memberform.MEMBER_MAIL.value;
	var zipcode=memberform.MEMBER_ZIPCODE.value;
	var add1=memberform.MEMBER_ADD_1.value;
	var add2=memberform.MEMBER_ADD_2.value;
	var add3=memberform.MEMBER_ADD_3.value;
	var memberType=memberform.MEMBER_TYPE.value;
	
	var forms = document.getElementById("memberform");

	if(name.length == 0){
		alert("氏名を入力してください。");
		memberform.MEMBER_NAME.focus();
		return false;
	}
	if(nameKana.length == 0){
		alert("氏名（カナ）を入力してください。");
		memberform.MEMBER_NAME_KANA.focus();
		return false;
	}
	if(tel.length == 0){
		alert("TELを入力してください。");
		memberform.MEMBER_TEL.focus();
		return false;
	}
	if(mail.length == 0){
		alert("メールアドレスを入力してください。");
		memberform.MEMBER_MAIL.focus();
		return false;
	}
	if((zipcode =="")||(zipcode < 6)){
		alert("郵便番号を入力してください。");
		memberform.MEMBER_ZIPCODE.focus();
        return false;
 	} 
	if(add_1.length == 0){
		alert("都道府県を選択してください。");
		memberform.MEMBER_ADD_1.focus();
		return false;
	} 
	if(add_2.length == 0){
		alert("市区町村を入力してください。");
		memberform.MEMBER_ADD_2.focus();
		return false;
	}
	if(add_3.length == 0){
		alert("丁目、番地、建物名を入力してください。");
		memberform.MEMBER_ADD_3.focus();
		return false;
	}
	if(memberType.length == 0){
		alert("会員種別を選択してください。");
		memberform.MEMBER_TYPE.focus();
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

</script>
<style>
p{
margin:auto;
}

</style>
</head>
<body>
<div style="margin:30 0 50 0">
<table style="margin:auto; width:960">
	<tr>
		<td colspan=2>
		<!-- 会員情報変更 -->
		<form name="memberform" action="./AdminMembereModify.admember?id=<%=member.getMEMBER_ID()%>" method="post" onsubmit="return check()">
			<input type="hidden" name="id" value="<%=member.getMEMBER_ID() %>">
		<table style="width:100%">
			<tr>
				<td height="26" bgcolor="94d7e7">
					<p align="center">
						<font size=4>会員情報変更</font>
					</p>
				</td>
			</tr>
			<tr height="26"></tr>
			<tr>
				<td height="331">
				<table style="margin:auto" style="width:960">
					<tr>
						<td width="20%"></td>
						<td width="20%" bgcolor="#EFEFEF">
							<p><font size=2>書名</font></p>
						</td>
						<td width="40%" colspan="2"><input type="text" name="MEMBER_NAME" value=<%=member.getMEMBER_NAME() %> style="width:390"></td>
						<td width="20%"></td>
					</tr>
					<tr>
						<td width="20%"></td>
						<td width="20%" bgcolor="#EFEFEF">
							<p><font size=2>書名（カナ）</font></p>
						</td>
						<td width="40%" colspan="2"><input type="text" name="MEMBER_NAME_KANA" value=<%=member.getMEMBER_NAME_KANA() %> style="width:390"></td>
						<td width="20%"></td>
					</tr>
					<tr>
						<td width="20%"></td>
						<td width="20%" bgcolor="#EFEFEF">
							<p><font size=2>電話番号</font></p>
						</td>
						<td width="40%" colspan="2"><input type="text" name="MEMBER_TEL" value=<%=member.getMEMBER_TEL() %> style="width:390"></td>
						<td width="20%"></td>
					</tr>
					<tr>
						<td width="20%"></td>
						<td width="20%" bgcolor="#EFEFEF">
							<p><font size=2>メールアドレス</font></p>
						</td>
						<td width="40%" colspan="2"><input type="text" name="MEMBER_MAIL" value=<%=member.getMEMBER_MAIL() %> style="width:390"></td>
						<td width="20%"></td>
					</tr>
					<tr>
						<td width="20%"></td>
						<td width="20%" bgcolor="#EFEFEF">
							<p><font size=2>郵便番号</font></p>
						</td>
						<td width="40%" colspan="2">
							<input type="text" name="MEMBER_ZIPCODE" size="7" onkeypress="gNumCheck()" maxlength="7" value=<%=member.getMEMBER_ZIPCODE() %> />&nbsp;&nbsp;
							<input type="button" name="zipcode" value="郵便番号検索" onclick="openzipcode(this.form)" />									
						</td>
						<td width="20%"></td>
					</tr>
					<tr>
						<td width="20%"></td>
						<td width="20%" bgcolor="#EFEFEF">
							<p><font size=2>都道府県</font></p>
						</td>
						<td width="40%" colspan="2">
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
						<td width="20%"></td>
					</tr>
					<tr>
						<td width="20%"></td>
						<td width="20%" bgcolor="#EFEFEF">
							<p><font size=2>市区町村</font></p>
						</td>
						<td width="40%" colspan="2"><input type="text" name="MEMBER_ADD_2" value=<%=member.getMEMBER_ADD_2() %> style="width:390"></td>
						<td width="20%"></td>
					</tr>
					<tr>
						<td width="20%"></td>
						<td width="20%" bgcolor="#EFEFEF">
							<p><font size=2>丁目、番地、建物名</font></p>
						</td>
						<td width="40%" colspan="2"><input type="text" name="MEMBER_ADD_3" value=<%=member.getMEMBER_ADD_3() %> style="width:390"></td>
						<td width="20%"></td>
					</tr>
					<tr>
						<td width="20%"></td>
						<td width="20%" height="30" bgcolor="#EFEFEF">
							<p><font size=2>会員種別</font></p>
						</td>
						<td colspan="2" width="40%" height="30">
							<select id="member_type"name="MEMBER_TYPE" size="1">
								<option value="0">管理者</option>
								<option value="1">会員</option>
							</select>
						</td>
						<td width="20%"></td>
					</tr>
				</table>
				</td>
			</tr>
			<tr>
				<td height="75">
				<p align="center">
					<input type="submit" value="修正">&nbsp;
					<input type=button onclick="javascript:history.back();" value="戻る">
				</p>
				</td>
			</tr>
		</table>
		</form>
		<!-- 会員修正 -->
		</td>
	</tr>
</table>
</div>
</body>
</html>