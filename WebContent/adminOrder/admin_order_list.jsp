<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="main.java.jp.co.bookmanage.dto.OrderDTO"%>
<%@ page import="java.util.*" %>
<%
@SuppressWarnings("unchecked") List<OrderDTO> orderlist=(List<OrderDTO>)request.getAttribute("orderlist");
	int ordercount=((Integer)request.getAttribute("ordercount")).intValue();
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
			<font size=4>注文リスト</font>
		</p>
	</td>
</tr>
<tr>
<td colspan=2 align=center>
<table style="width:100%; border-spacing:1">
	<tr>
	<td align=right colspan=10 height=25 colspan=2 style=font-family:Tahoma;font-size:10pt;>
	すべての注文数 : <b><%=ordercount%></b> 件&nbsp;&nbsp;&nbsp;
	</td>
	</tr>
	<tr height="26"></tr>
	<tr align=center height=20 bgcolor="#EFEFEF">
	  <td style=font-family:Tahoma;font-size:10pt;font-weight:bold;>注文番号</td>
	  <td style=font-family:Tahoma;font-size:10pt;font-weight:bold;>注文者</td>
	  <td style=font-family:Tahoma;font-size:10pt;font-weight:bold;>決済方法</td>
	  <td style=font-family:Tahoma;font-size:10pt;font-weight:bold;>注文金額</td>
	  <td style=font-family:Tahoma;font-size:10pt;font-weight:bold;>注文ステータス</td>
	  <td style=font-family:Tahoma;font-size:10pt;font-weight:bold;>注文日時</td>
	  <td style=font-family:Tahoma;font-size:10pt;font-weight:bold;>変更/削除</td>
	</tr>
	<tr>
		<td style="background-color:#EFEFEF; height:5px;" colspan=7>
	</tr>
	<%
	for(int i=0;i<orderlist.size();i++){ 
			OrderDTO order=new OrderDTO();
			order=(OrderDTO)orderlist.get(i);
	%>
	<tr align=center height=20>
	<td style=font-family:Tahoma;font-size:7pt;><%=String.format("%08d", order.getORDER_NO())%></td>
	<td style=font-family:Tahoma;font-size:10pt;><%=order.getORDER_MEMBER_ID()%></td>
	<td style=font-family:Tahoma;font-size:10pt;><%=order.getORDER_TRADE_TYPE()%></td>
	<td style=font-family:Tahoma;font-size:10pt;><%=order.getTOTAL_PRICE()%>円</td>
	<td style=font-family:Tahoma;font-size:10pt;>
		<%if (order.getORDER_STATUS() == 0) {%>注文受付
		<%}else if (order.getORDER_STATUS() == 1){%>発送準備中
		<%}else if (order.getORDER_STATUS() == 2){%>発送済み
		<%}else if (order.getORDER_STATUS() == 3){%>配送中
		<%}else if (order.getORDER_STATUS() == 4){%>配送済み
		<%}else if (order.getORDER_STATUS() == 5){%>注文キャンセル
		<%}%>
   	</td>
   	<td style=font-family:Tahoma;font-size:10pt;><%=order.getORDER_DATE()%></td>
   	<td style=font-family:Tahoma;font-size:10pt;>
   	<a href="./AdminOrderDetail.adorder?num=<%=order.getORDER_NO() %>">
   	変更</a>/
   	<a href="./AdminOrderDelete.adorder?num=<%=order.getORDER_NO() %>" 
   		onclick="return confirm('削除しますか')">削除</a>
   	</td>
	</tr>
	<tr>
		<td style="background-color:#F0F0F0; height:1px;" colspan=7>
	</tr>
	<%} %>
	<tr align=center height=20>
		<td colspan=7 style=font-family:Tahoma;font-size:10pt;>
			<%if(nowpage<=1){ %>[前へ]&nbsp;
			<%}else{ %>
			<a href="./AdminOrderList.adorder?page=<%=nowpage-1 %>">
			[前へ]</a>&nbsp;
			<%}%>
			<%for(int a=startpage;a<=endpage;a++){
				if(a==nowpage){%>[<%=a%>]
				<%}else{ %>
					<a href="./AdminOrderList.adorder?page=<%=a %>">
					[<%=a %>]
					</a>&nbsp;
				<%} %>
			<%} %>
			<%if(nowpage>=maxpage){ %>[次へ]
			<%}else{ %>
			<a href="./AdminOrderList.adorder?page=<%=nowpage+1 %>">[次へ]</a>
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