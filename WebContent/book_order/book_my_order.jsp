<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="net.order.db.*"%>
<%
	String id=(String)session.getAttribute("id");
	List book_order_list=(List)request.getAttribute("book_order_list");
	int ordercount=((Integer)request.getAttribute("ordercount")).intValue();
	int nowpage=((Integer)request.getAttribute("page")).intValue();
	int maxpage=((Integer)request.getAttribute("maxpage")).intValue();
	int startpage=((Integer)request.getAttribute("startpage")).intValue();
	int endpage=((Integer)request.getAttribute("endpage")).intValue();
	int totalmoney=((Integer)request.getAttribute("totalmoney")).intValue();
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
<table width="960" cellspacing="0" cellpadding="0" border="0"
	align="center">
<tr>
<td colspan=2>
<!-- 会員の注文履歴-->
<table width="700" border="0" align="center">
<tr>
	<td>現在 (<%=id%>)お客様から 注文した図書は(<%=ordercount%>)個あります。</td>
</tr>
<tr>
	<td height="62" align="center" valign="middle">
	<table width="700" border="1" cellspacing="0" cellpadding="0"
		bordercolor="#CCCCCC">
		<tr>
			<td height="20"><div align="center">図書名</div></td>
			<td><div align="center">本数</div></td>
			<td><div align="center">小計</div></td>
			<td><div align="center">注文ステータス</div></td>
			<td><div align="center">注文日時</div></td>
		</tr>
		<%
		if (book_order_list.size() == 0) {
		%>
		<td align=center colspan=6>注文する本がありません。</td>
		<%
		}
		
		for (int i = 0; i < book_order_list.size(); i++) {
			OrderBean order = new OrderBean();
			order = (OrderBean) book_order_list.get(i);
		%>
		<tr align=center>
			<td height="20"><%=order.getBOOK_NAME()%></td>
			<td><%=order.getORDER_COUNT()%></td>
			<td><%=order.getTOTAL_PRICE()%></td>
			<td>
			<%if (order.getORDER_STATUS() == 0) {%>注文受付
			<%}else if (order.getORDER_STATUS() == 1){%>発送準備中
			<%}else if (order.getORDER_STATUS() == 2){%>発送済み
			<%}else if (order.getORDER_STATUS() == 3){%>配送中
			<%}else if (order.getORDER_STATUS() == 4){%>配送済み
			<%}else if (order.getORDER_STATUS() == 5){%>注文キャンセル
			<%}%>
			</td>
			<td><%=order.getORDER_DATE()%></td>
		</tr>
		<%
			}
		%>
		<tr align=center height=20>
			<td colspan=7 style="font-family: Tahoma; font-size: 10pt;">
			<%if (nowpage <= 1) {%>
			[前へ]&nbsp;
			<%}else{%>
			<a href="./OrderList.order?page=<%=nowpage-1 %>">[前へ]</a>&nbsp;
			<%}%>
			<%
			for (int a = startpage; a <= endpage; a++) {
				if (a == nowpage) {
			%>
			[<%=a%>]
			<%
				}else{
			%>
			<a href="./OrderList.order?page=<%=a %>">[<%=a%>]</a>&nbsp;
			<%	}
			}
			%>
			<%if (nowpage >= maxpage) {%>
			[次へ]
			<%}else{%>
			<a href="./OrderList.order?page=<%=nowpage+1 %>">[次へ]</a>
			<%}%>
			</td>
		</tr>
	</table>
	</td>
</tr>
<tr>
	<td height="28">
	<div align="right">合計金額 : <%=totalmoney%>円</div>
	</td>
</tr>
</table>
<!-- 会員の注文履歴 -->
</td>
</tr>
</table>
</body>
</html>