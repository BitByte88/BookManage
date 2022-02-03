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
</head>
<body>
<div name="main" style="margin:30 0 50 0">
<table cellspacing="0" cellpadding="0" border="0" align="center">
<tr>
<td colspan=2>
<p align="center">
<form action="./MemberDeleteAction.member" method="post">
<table border="1" width="380" cellpadding="0" cellspacing="0">
	<tr>
		<td align="center" colspan="2">
			<font size="4"><b>退会</b></font>
		</td>
	</tr>
	<tr>
		<td align="center" height="35" width="125">
		<font size="2">パスワード</font></td>
		<td>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<input type="password" name="MEMBER_PW" />
		</td>
	</tr>
	<tr>
		<td align="center" colspan="2" height="35">
			<input type="submit" value="退会" />
			<input type="reset" value="キャンセル" />
		</td>
	</tr>				
</table>
</form>				
</td>
</tr>
</table>
</div>
</body>
</html>