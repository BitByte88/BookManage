<%@ page contentType="text/html; charset=UTF-8"%>
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
	var id=loginform.MEMBER_ID.value;
	var pass=loginform.MEMBER_PW.value;
	
	if(id.length == 0){
		alert("アカウントを入力してください。");
		loginform.MEMBER_ID.focus();
		return false;
	}
	if(pass.length == 0){
		alert("パスワードを入力してください。");
		loginform.MEMBER_PW.focus();
		return false;
	}
	
	return true;
}
function openConfirmId(loginform){	
	var url="./MemberFind.member";
	open(url, "confirm", "toolbar=no,location=no,status=no,menubar=no,"+
						 "scrollbars=no,resizable=no,width=600px,height=350");
}
</script>
</head>
<body>
<div name="main" style="margin:30 0 50 0">
<table width="960" cellspacing="0" cellpadding="0" border="0"
	align="center">
<tr>
<td colspan=2 align=center>
<table border="0" cellpadding="0" cellspacing="0" width="550">
<!--会員ログイン -->
<tr>
<form action="./MemberLoginAction.member" method=post name=loginform
	onsubmit="return check()">
<td><br>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td bgcolor="f6f6f6">
		<table width="100%" border="0" cellspacing="4" cellpadding="0">
		<tr>
		<td valign="top" bgcolor="#FFFFFF">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
		<td align="center">
		<table cellpadding=0 cellspacing=0 border=0>
			<tr>
			<td width=90>アカウント</td>
			<td width=9>:</td>
			<td width=103>
				<input type=text name="MEMBER_ID" size=12 maxlength=20>
			</td>
			<td width=9></td>
			<td width=66 rowspan=3><input type="submit" value="ログイン">
			</td>
			<td width=26 rowspan=3></td>
			</tr>
			<tr>
			<td height=10 colspan=3></td>
			</tr>
			<tr>
			<td width=73>パスワード</td>
			<td width=9>:</td>
			<td width=103>
			<input type=password name="MEMBER_PW" size=12 maxlength=12>
			</td>
			</tr>
			<tr>
			<td height=50 colspan=6 align="center">
			<input
				type="button" value="会員登録"
				onclick="javascript:window.location='./MemberJoin.member'">
			<a href="#">
			<input type="button" value="アカウント／パスワ―ドを探す"
				onclick="openConfirmId(this.form)">
			</a>
			</td>
			</tr>
		</table>
		</td>
		</tr>
		<tr>
			<td style="padding: 15 0 15 70;">
			<table width="400" border="0" cellspacing="0" cellpadding="0">
				<tr>
				<td width="8"><img src="#" width="8" height="7">
				</td>
				<td width="392">
				<font size=2 color="565656">
				アカウントを持っていない場合、「会員登録」をお願い致します。
				</font>
				</td>
				</tr>
				<tr>
				<td><img src="#" width="8" height="7"></td>
				<td>
				<font size=2 color="565656">
				アカウント及びパスワードを忘れた場合、「アカウント／パスワードを探す」をご利用してください。
				</font>
				</td>
				</tr>
			</table>
			</td>
		</tr>
		</table>
		</td>
		</tr>
		</table>
		</td>
	</tr>
</table>
</td>
</form>
</tr>
</table>
<!-- 会員ログイン -->
</td>
</tr>
</table>
</div>
</body>
</html>