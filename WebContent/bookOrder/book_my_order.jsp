<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="main.java.jp.co.bookmanage.dto.OrderDTO"%>
<%
String id=(String)session.getAttribute("id");
	@SuppressWarnings("unchecked") List<OrderDTO> book_order_list=(List<OrderDTO>)request.getAttribute("book_order_list");
	int ordercount=((Integer)request.getAttribute("ordercount")).intValue();
	int nowpage=((Integer)request.getAttribute("page")).intValue();
	int maxpage=((Integer)request.getAttribute("maxpage")).intValue();
	int startpage=((Integer)request.getAttribute("startpage")).intValue();
	int endpage=((Integer)request.getAttribute("endpage")).intValue();
	@SuppressWarnings("unchecked") HashMap<Integer, Integer> totalPriceMap = (HashMap<Integer,Integer>)request.getAttribute("totalPriceMap");
	@SuppressWarnings("unchecked") HashMap<Integer, Integer> itemCountMap = (HashMap<Integer,Integer>)request.getAttribute("itemCountMap");
	int tempOrderNo = 0;
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
<table style="width:960; margin:auto">
<tr>
<td colspan=2>
<!-- 会員の注文履歴-->
<table style="width:700; margin:auto">
<tr>
<td>
	<p align=left><b><font size=4>注文履歴</font></b></p>
</td>
</tr>
<tr>
	<td>現在、 <%=id%>様からの注文件数は<%=ordercount%>件でございます。</td>
</tr>
<tr>
	<td height="62" align="center" valign="middle">
	<table style="width:800">
		<tr height=13 bgcolor="94d7e7">
			<td height="3" colspan="7" align=right></td>
		</tr>
		<tr>
			<td bgcolor="f7f7f7"><div align="center">注文番号</div></td>
			<td height="20" bgcolor="f7f7f7"><div align="center">書名</div></td>
			<td bgcolor="f7f7f7"><div align="center">数量</div></td>
			<td bgcolor="f7f7f7"><div align="center">小計</div></td>
			<td bgcolor="f7f7f7"><div align="center">注文ステータス</div></td>
			<td bgcolor="f7f7f7"><div align="center">注文日時</div></td>
			<td bgcolor="f7f7f7"><div align="center">合計金額</div></td>
		
		</tr>
		<%
		if (book_order_list.size() == 0) {
		%>
		<tr>
		<td align=center colspan=6>注文する本がありません。</td>
		</tr>
		<%
		}
				
				for (int i = 0; i < book_order_list.size(); i++) {
			OrderDTO order = new OrderDTO();
			OrderDTO nextOrder = new OrderDTO();
			order = (OrderDTO) book_order_list.get(i);
			if(i != book_order_list.size()-1){
				nextOrder = (OrderDTO) book_order_list.get(i+1);
			}
		%>
		<tr align=center>
			<%
			if (tempOrderNo != order.getORDER_NO()) {
			%>
			<td rowspan="<%=2*itemCountMap.get(order.getORDER_NO())-1%>" style="border-right:2px solid #94d7e7"><%=String.format("%08d", order.getORDER_NO())%></td>			
			<%
			}
			%>	
			<td height="20" style="text-align:left;padding-left:40"><%=order.getBOOK_NAME()%></td>
			<td><%=order.getORDER_COUNT()%></td>
			<td><%=order.getTOTAL_PRICE()%>円</td>
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
			<%
			if (tempOrderNo != order.getORDER_NO()) {
			%>
			<td rowspan="<%=2*itemCountMap.get(order.getORDER_NO())-1%>" style="border-left:2px solid #94d7e7"><%=totalPriceMap.get(order.getORDER_NO())%>円</td>			
			<%
			tempOrderNo = order.getORDER_NO();
			} 
			%>	
		</tr>
		<tr>
			<td height="1" bgcolor="94d7e7"></td>		
			<td height="1" bgcolor="94d7e7"></td>
			<td height="1" bgcolor="94d7e7"></td>
			<td height="1" bgcolor="94d7e7"></td>			
			<td height="1" bgcolor="94d7e7"></td>
		<%
			if(order.getORDER_NO() != nextOrder.getORDER_NO()){
		%>
			<td height="1" bgcolor="94d7e7"></td>			
			<td height="1" bgcolor="94d7e7"></td>				
		<%
			} 
		%>
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
</table>
<!-- 会員の注文履歴 -->
</td>
</tr>
</table>
</div>
</body>
</html>