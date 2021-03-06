<%@ page contentType="text/html; charset=UTF-8" %>
<%
	String id=(String)request.getAttribute("id");
	int check=((Integer)(request.getAttribute("check"))).intValue();
%>
<html>
<head>
	<link rel="icon" href="favicon/favicon.ico" type="image/x-icon" />
	<link rel="shortcut icon" href="favicon/favicon.ico" type="image/x-icon"> 
	<link rel="apple-touch-icon-precomposed" sizes="144x144" href="favicon/favicon-144x144.png">
	<link rel="apple-touch-icon-precomposed" sizes="72x72" href="favicon/favicon-72x72.png">
	<link rel="apple-touch-icon-precomposed" href="favicon/favicon-54x54.png">
	
<title>アカウント確認</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/member_idchk.js"></script>
</head>
<body bgcolor="#f5f5f5">
<% if(check == 1){ %>
<table style="width:360">
	<tr align="center">
	<td height="30" style="padding:5">
		<font size="2"><%=id %> は既に登録済みのアカウントです。</font>
	</td>
	</tr>
</table>

<form action="./MemberIDCheckAction.member" method="post" name="checkForm" >
<table style="width:360">
	<tr>
	<td align="center" style="padding:5">
		<font size="2">別のアカウントを入力してください。</font><p>
		<input type="text" size="10" maxlength="12" name="MEMBER_ID"/>
		<input type="submit" value="重複確認" />
	</td>					
	</tr>
</table>
</form>
<% }else{ %>
<table style="width:360">
	<tr>
	<td align="center" style="padding:5">
		<font size="2"> <%=id %> は登録可能なアカウントです。</font>
		<br/><br/>
		<input type="button" value="閉じる" onclick="windowclose()" />
		</td>
	</tr>
</table>
<% } %>
</body>
</html>