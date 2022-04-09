<%@ page contentType="text/html; charset=UTF-8" %>
<% 
	String id=(String)request.getAttribute("id"); 
	String passwd=(String)request.getAttribute("passwd"); 
%>

<html>
<head>
	<link rel="icon" href="favicon/favicon.ico" type="image/x-icon" />
	<link rel="shortcut icon" href="favicon/favicon.ico" type="image/x-icon"> 
	<link rel="apple-touch-icon-precomposed" sizes="144x144" href="favicon/favicon-144x144.png">
	<link rel="apple-touch-icon-precomposed" sizes="72x72" href="favicon/favicon-72x72.png">
	<link rel="apple-touch-icon-precomposed" href="favicon/favicon-54x54.png">
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/member_find_ok.js"></script>
</head>
<body>
<table style="width:450px; height:35px">
	<tr><td align="left">
	<b>アカウント／パスワードを探す</b>
	</td></tr>
</table>
	
<table style="width:440px;">
	<thead>
	<tr><td><label style="font-size:12;color:red">アカウント／パスワードを忘れないように注意してください。</label><br/><br/><br/></td></tr></thead>
	<tr><td align="left">アカウント　： <%=id %></td></tr>
	<tr><td align="left">パスワード　： <%=passwd %></td></tr>
</table>

<table style="width:450px">
	<tr>
		<td align="center">	
			<hr/><br/><input type="button" value="閉じる" onclick="windowclose()"/>
		</td>
	</tr>
</table>
</body>
</html>