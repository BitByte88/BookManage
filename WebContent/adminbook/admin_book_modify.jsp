<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="net.admin.book.db.*"%>
<%  
	BookBean abb=(BookBean)request.getAttribute("abb");
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
	bordercolor="gray" align="center">
<tr>
	<td colspan=2>
	<!-- 図書情報変更 -->
	<table border="0" width="80%">
	<form name="bookform" action="./BookModifyAction.adbook" method="post">
	<input type="hidden" name="book_num" value=<%=abb.getBOOK_NO() %>>
	<tr>
		<td>
		<p align="center"><span style="font-size: 26pt;">図書情報変更</span></p>
		</td>
	</tr>
		
	<tr>
		<td height="331">
		<table border="1" align="center" width="558">
		<tr>
			<td width="196" height="30">
			<p align="center"><font size=2>カテゴリー</font></p>
			</td>
			
			<td width="346" height="30">
			<select name="book_category" size="1" 
					value=<%=abb.getBOOK_CATEGORY() %>>
				<option value="文学・評論">文学・評論</option>
				<option value="人文・思想">人文・思想</option>
				<option value="社会・政治">社会・政治</option>
				<option value="ノンフィクション">ノンフィクション</option>
				<option value="歴史・地理">歴史・地理</option>
				<option value="ビジネス・経済">ビジネス・経済</option>
				<option value="投資・金融・会社経営">投資・金融・会社経営</option>
				<option value="科学・テクノロジー">科学・テクノロジー</option>
				<option value="医学・薬学・看護学・歯科学">医学・薬学・看護学・歯科学</option>
				<option value="コンピュータ・IT">コンピュータ・IT</option>
				<option value="アート・建築・デザイン">アート・建築・デザイン</option>
				<option value="趣味・実用">趣味・実用</option>
				<option value="スポーツ・アウトドア">スポーツ・アウトドア</option>
				<option value="資格・検定・就職">資格・検定・就職</option>
				<option value="暮らし・健康・子育て">暮らし・健康・子育て</option>
				<option value="旅行ガイド・マップ">旅行ガイド・マップ</option>
				<option value="語学・辞事典・年鑑">語学・辞事典・年鑑</option>
				<option value="教育・学参・受験">教育・学参・受験</option>
				<option value="絵本・児童書">絵本・児童書</option>
				<option value="コミック・ラノベ・BL">コミック・ラノベ・BL</option>
				<option value="ライトノベル">ライトノベル</option>
				<option value="タレント写真集">タレント写真集</option>
				<option value="ゲーム攻略・ゲームブック">ゲーム攻略・ゲームブック</option>
				<option value="エンターテイメント">エンターテイメント</option>
				<option value="雑誌">雑誌</option>
				<option value="楽譜・スコア・音楽書">楽譜・スコア・音楽書</option>
				<option value="古書">古書</option>
				<option value="カレンダー">カレンダー</option>
				<option value="ポスター">ポスター</option>
				<option value="アダルト">アダルト</option>
			</select>
			</td>
		</tr>
		<tr>
			<td>
			<p align="center"><font size=2>書名</font></p>
			</td>
			<td><input type="text" name="book_name" 
					value=<%=abb.getBOOK_NAME() %>></td>
		</tr>
		<tr>
			<td>
			<p align="center"><font size=2>판매가</font></p>
			</td>
			<td><input type="text" name="book_price" 
					value=<%=abb.getBOOK_PRICE() %>></td>
		</tr>

		<tr>
			<td width="196">
			<p align="center"><font size=2>제품정보</font></p>
			</td>
			<td width="346">
				<textarea name="book_content" cols=50 rows=15><%=abb.getBOOK_CONTENT() %></textarea>
			</td>
		</tr>
		</table>
		</td>
	</tr>
	<tr>
		<td height="75">
		<p align="center"><input type="submit" value="수정">&nbsp;
		<input type="reset" value="다시쓰기"></p>
		</td>
	</tr>
	</form>
	</table>
	<!-- 상품 수정 -->
	</td>
</tr>
</table>
</body>
</html>