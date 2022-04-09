<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%
	String zipcode="";
	String addr="";
	String add_1="";
	String add_2="";
	String add_3="";
	@SuppressWarnings("unchecked") List<String> zipcodeList=(ArrayList<String>)request.getAttribute("zipcodelist");
%>

<html>
<head>
	<link rel="icon" href="favicon/favicon.ico" type="image/x-icon" />
	<link rel="shortcut icon" href="favicon/favicon.ico" type="image/x-icon"> 
	<link rel="apple-touch-icon-precomposed" sizes="144x144" href="favicon/favicon-144x144.png">
	<link rel="apple-touch-icon-precomposed" sizes="72x72" href="favicon/favicon-72x72.png">
	<link rel="apple-touch-icon-precomposed" href="favicon/favicon-54x54.png">
	
<title>郵便番号検索</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/member_zipcode.js"></script>
</head>
<body bgcolor="#f5f5f5" style="margin:auto">
<table style="width:370">
	<tr align="center">
		<td align="center" style="padding:5">
			<font color="#ff4500">郵便番号検索</font><br>
		</td>
	</tr>
</table>
<form action="./MemberZipcodeAction.member" method="post" name="form">
<table style="width:370">
	<tr align="center">
		<td align="center" style="padding:5">
			<font size="2">郵便番号： </font>
			<input type="text" name="zipcode" placeholder="1230123"/>
			<input type="submit" value="検索"><br>
			<font size="2">郵便番号を入力してください。（2桁以上入力）</font>
		</td>
	</tr>
</table>
</form>
<br>
<table style="width:370">
<tr height="35">
	<td align="center" style="padding:5" colspan="2">検索結果</td>
</tr>
<%	
	if(zipcodeList!=null && zipcodeList.size()!=0){	
		for(int i=0;i<zipcodeList.size();i++){
			String data=(String)zipcodeList.get(i);
			
			StringTokenizer st=new StringTokenizer(data,",");
			zipcode=st.nextToken();
			addr=st.nextToken();
			
			add_1=addr.split("-")[0];
			add_2=addr.split("-")[1];
			add_3=addr.split("-")[2];
%>
<tr>
	<td width="20%" style="padding:5">
	<a href="#"	onclick="setZipcode('<%=zipcode%>','<%=add_1 %>','<%=add_2 %>','<%=add_3 %>')">
		<font size="2"><%=zipcode%></font>
	</a>
	</td>
	<td width="80%" style="padding:5"><font size="2"><%=add_1+add_2+add_3 %></font></td>
</tr>
<%		}
	}else{ %>
<tr>
	<td colspan="2" style="padding:5" align="center">検索結果がありません。</td>
</tr>
<%	}%>
</table>
</body>
</html>