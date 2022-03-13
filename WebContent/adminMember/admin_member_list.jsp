<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="main.java.jp.co.bookmanage.dto.MemberDTO"%>
<%@ page import="java.util.*" %>
<%
@SuppressWarnings("unchecked") List<MemberDTO> memberlist=(List<MemberDTO>)request.getAttribute("memberlist");
	int membercount=((Integer)request.getAttribute("membercount")).intValue();
	int nowpage=((Integer)request.getAttribute("page")).intValue();
	int maxpage=((Integer)request.getAttribute("maxpage")).intValue();
	int startpage=((Integer)request.getAttribute("startpage")).intValue();
	int endpage=((Integer)request.getAttribute("endpage")).intValue();
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
</head>
<body>
<div style="margin:30 0 50 0">
<table style="width:1000; margin:auto">
<tr>
	<td height="26" bgcolor="94d7e7">
		<p align="center">
			<font size=4>会員リスト</font>
		</p>
	</td>
</tr>
<tr>
<td colspan=2 align=center>
<table style="width:100%; border-spacing:1">
	<tr>
	<td align=right colspan=10 height=25 colspan=2 style=font-family:Tahoma;font-size:10pt;>
	すべての会員数 : <b><%=membercount%></b>名&nbsp;&nbsp;&nbsp;
	</td>
	</tr>
	<tr height="26"></tr>
	<tr align=center height=20 bgcolor="#EFEFEF">
	  <td style=font-family:Tahoma;font-size:10pt;font-weight:bold;>アカウント</td>
	  <td style=font-family:Tahoma;font-size:10pt;font-weight:bold;>氏名</td>
	  <td style=font-family:Tahoma;font-size:10pt;font-weight:bold;>氏名(カナ)</td>
	  <td style=font-family:Tahoma;font-size:10pt;font-weight:bold;>TEL</td>
	  <td style=font-family:Tahoma;font-size:10pt;font-weight:bold;>メールアドレス</td>
	  <td style=font-family:Tahoma;font-size:10pt;font-weight:bold;>会員種別</td>
	  <td style=font-family:Tahoma;font-size:10pt;font-weight:bold;>加入日時</td>
	  <td style=font-family:Tahoma;font-size:10pt;font-weight:bold;>変更/削除</td>
	</tr>
	<tr>
		<td style="background-color:#EFEFEF; height:5px;" colspan=8>
	</tr>
	<%
	for(int i=0;i<memberlist.size();i++){ 
			MemberDTO member=new MemberDTO();
			member=(MemberDTO)memberlist.get(i);
	%>
	<tr align=center height=20>
	<td style=font-family:Tahoma;font-size:7pt;><%=member.getMEMBER_ID()%></td>
	<td style=font-family:Tahoma;font-size:10pt;><%=member.getMEMBER_NAME()%></td>
	<td style=font-family:Tahoma;font-size:10pt;><%=member.getMEMBER_NAME_KANA()%></td>
	<td style=font-family:Tahoma;font-size:10pt;><%=member.getMEMBER_TEL()%></td>
	<td style=font-family:Tahoma;font-size:10pt;><%=member.getMEMBER_MAIL()%></td>
	<td style=font-family:Tahoma;font-size:10pt;>
		<%if (member.getMEMBER_TYPE() == 0) {%>管理者
		<%}else if (member.getMEMBER_TYPE() == 1){%>会員
		<%}%>
   	</td>
	<td style=font-family:Tahoma;font-size:10pt;><%=member.getCREATE_DATE()%></td>
   	<td style=font-family:Tahoma;font-size:10pt;>
   	<a href="./AdminMemberDetail.admember?id=<%=member.getMEMBER_ID() %>">
   	変更</a>/
   	<a href="./AdminMemberDelete.admember?id=<%=member.getMEMBER_ID() %>" 
   		onclick="return confirm('削除しますか')">削除</a>
   	</td>
	</tr>
	<tr>
		<td style="background-color:#F0F0F0; height:1px;" colspan=8>
	</tr>
	<%} %>
	<tr align=center height=20>
		<td colspan=8 style=font-family:Tahoma;font-size:10pt;>
			<%if(nowpage<=1){ %>[前へ]&nbsp;
			<%}else{ %>
			<a href="./AdminMemberList.admember?page=<%=nowpage-1 %>">
			[前へ]</a>&nbsp;
			<%}%>
			<%for(int a=startpage;a<=endpage;a++){
				if(a==nowpage){%>[<%=a%>]
				<%}else{ %>
					<a href="./AdminMemberList.admember?page=<%=a %>">
					[<%=a %>]
					</a>&nbsp;
				<%} %>
			<%} %>
			<%if(nowpage>=maxpage){ %>[次へ]
			<%}else{ %>
			<a href="./AdminMemberList.admember?page=<%=nowpage+1 %>">[次へ]</a>
			<%} %>
		</td>
	</tr>
</table>
</td>
</tr>	
</table>
</div>
</body>
</html>