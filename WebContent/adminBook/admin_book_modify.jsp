<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="main.java.jp.co.bookmanage.dto.BookDTO"%>
<%@ page import="java.text.*"%>
<%
BookDTO abb=(BookDTO)request.getAttribute("abb");
	@SuppressWarnings("unchecked") HashMap<String, String> imageMap = (HashMap<String,String>)request.getAttribute("imageMap");
	DecimalFormat format = new DecimalFormat("0");
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
<script>
window.onload = function() {
	document.querySelector('#book_category').value = "${abb.getBOOK_CATEGORY()}";
}
function check(){
	var category=bookform.book_category.value;
	var name=bookform.book_name.value;
	var writer=bookform.book_writer.value;
	var publisher=bookform.book_publisher.value;
	var publishingDate=bookform.book_publishing_date.value;
	var price=bookform.book_price.value;
	var isbn=bookform.book_isbn.value;
	var content=bookform.book_content.value;
	
	
	if(category.length == 0){//必須チェック（カテゴリー）
		alert("カテゴリーを入力してください。");
		bookform.book_category.focus();
		return false;
	} else if (category.length > 32){//桁数チェック（カテゴリー）
		alert("カテゴリーは32文字以内まで入力してください。");
		bookform.book_category.focus();
		return false;		
	}
	
	if(name.length == 0){//必須チェック（書名）
		alert("書名を入力してください。");
		bookform.book_name.focus();
		return false;
	} else if (name.length > 32){//桁数チェック（書名）
		alert("書名は32文字以内まで入力してください。");
		bookform.book_name.focus();
		return false;		
	} 
	
	if(writer.length == 0){//必須チェック（著者）
		alert("著者を入力してください。");
		bookform.book_writer.focus();
		return false;
	} else if (writer.length > 32){//桁数チェック（著者）
		alert("著者は32文字以内まで入力してください。");
		bookform.book_writer.focus();
		return false;		
	} 
	
	if(publisher.length == 0){//必須チェック（出版社）
		alert("出版社を入力してください。");
		bookform.book_publisher.focus();
		return false;
	} else if (writer.length > 32){//桁数チェック（出版社）
		alert("出版社は32文字以内まで入力してください。");
		bookform.book_publisher.focus();
		return false;		
	} 
	
	if(publishingDate.length == 0){//必須チェック（発行日）
		alert("発行日を入力してください。");
		bookform.book_publishing_date.focus();
		return false;
	} else {
		var regex = RegExp(/^\d{4}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$/);
		if(!regex.test(publishingDate)){//形式チェック（発行日）
			alert("発行日の形式を確認してください。");
			bookform.book_publishing_date.focus();
			return false;			
		} else {//入力する発行日が今日以後の日時の場合
			var today = new Date();
			var date = new Date(publishingDate);
			if(date > today){
				alert("発行日は今日からそれ以前の日付のみ入力できます。");
				bookform.book_publishing_date.focus();
				return false;			
			}
		}
	}
	
	if(price.length == 0){//必須チェック（販売価格）
		alert("販売価格を入力してください。");
		bookform.book_price.focus();
		return false;
	} else if (isNaN(price)){//形式チェック（販売価格）
		alert("販売価格は数字を入力してください。");
		bookform.book_price.focus();
		return false;		
	}
	
	if(isbn.length == 0){//必須チェック（ISBN）
		alert("ISBNコードを入力してください。");
		bookform.book_isbn.focus();
		return false;
	}else if(isbn.length != 10 && isbn.length != 13){//桁数チェック（ISBN）
		alert("ISBNコードは１０桁または１３桁で入力してください。");
		bookform.book_isbn.focus();
		return false;
	}
	
	if(content.length > 128){//桁数チェック（図書内容）
		alert("図書内容は128文字以内まで入力してください。");
		bookform.book_content.focus();
		return false;		
	}
	
	return true;
}
</script>
<style>
p{
margin:auto;
}

input[type="number"]::-webkit-outer-spin-button,
input[type="number"]::-webkit-inner-spin-button {
    -webkit-appearance: none;
    margin: 0;
}

</style>
</head>
<body>
<div style="margin:30 0 50 0">
<table style="margin:auto; width:960">
	<tr>
		<td colspan=2>
		<!-- 図書情報変更 -->
		<form name="bookform" action="./BookModifyAction.adbook?bookNo=<%=abb.getBOOK_NO()%>" method="post"
		enctype="multipart/form-data" onsubmit="return check()">
		<table style="width:100%">
			<tr>
				<td height="26" bgcolor="94d7e7">
					<p align="center">
						<font size=4>図書情報変更</font>
					</p>
				</td>
			</tr>
			<tr height="26"></tr>
			<tr>
				<td height="331">
				<table style="margin:auto" style="width:960">
					<tr>
						<td width="20%"></td>
						<td width="20%" height="30" bgcolor="#EFEFEF">
							<p align="center"><font size=2>カテゴリー</font></p>
						</td>
						<td colspan="2" width="40%" height="30">
							<select id="book_category"name="book_category" size="1">
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
						<td width="20%"></td>
					</tr>
					<tr>
						<td width="20%"></td>
						<td width="20%" bgcolor="#EFEFEF">
							<p align="center"><font size=2>書名</font></p>
						</td>
						<td width="40%" colspan="2"><input type="text" name="book_name" value=<%=abb.getBOOK_NAME() %> style="width:390"></td>
						<td width="20%"></td>
					</tr>
					<tr>
						<td width="20%"></td>
						<td width="20%" bgcolor="#EFEFEF">
							<p align="center"><font size=2>著者</font></p>
						</td>
						<td width="40%" colspan="2"><input type="text" name="book_writer" value=<%=abb.getBOOK_WRITER() %> style="width:390"></td>
						<td width="20%"></td>
					</tr>
					<tr>
						<td width="20%"></td>
						<td width="20%" bgcolor="#EFEFEF">
							<p align="center"><font size=2>出版社</font></p>
						</td>
						<td width="40%" colspan="2"><input type="text" name="book_publisher" value=<%=abb.getBOOK_PUBLISHER() %> style="width:390"></td>
						<td width="20%"></td>
					</tr>
					<tr>
						<td width="20%"></td>
						<td width="20%" bgcolor="#EFEFEF">
							<p align="center"><font size=2>発行日</font></p>
						</td>
						<td width="40%" colspan="2"><input type="text" name="book_publishing_date" value=<%=abb.getBOOK_PUBLISHING_DATE() %> style="width:390"></td>
						<td width="20%"></td>
					</tr>
					<tr>
						<td width="20%"></td>
						<td width="20%" bgcolor="#EFEFEF">
							<p align="center"><font size=2>販売価格</font></p>
						</td>
						<td width="40%" colspan="2"><input type="number" min="0" name="book_price" value=<%=format.format(abb.getBOOK_PRICE())%> style="width:390"></td>
						<td width="20%"></td>
					</tr>
					<tr>
						<td width="20%"></td>
						<td width="20%" bgcolor="#EFEFEF">
							<p align="center"><font size=2>ISBNコード</font></p>
						</td>
						<td width="40%" colspan="2"><input type="text" name="book_isbn" value=<%=abb.getBOOK_ISBN() %> style="width:390"></td>
						<td width="20%"></td>
					</tr>
					<tr>
						<td width="20%"></td>
						<td width="20%" bgcolor="#EFEFEF">
							<p align="center"><font size=2>図書内容</font></p>
						</td>
						<td width="40%" colspan="2">
							<textarea name="book_content" cols=50 rows=15 ><%=abb.getBOOK_CONTENT() %></textarea>
						</td>
						<td width="20%"></td>
					</tr>
					<tr>
						<td width="20%"></td>
						<td width="20%" bgcolor="#EFEFEF">
							<p align="center"><font size=2>サムネイル用画像</font></p>
						</td>
						<td width="10%" height="100">
							<% if(imageMap.get("thumbnail") != null) {%>
							<img src="<%=imageMap.get("thumbnail")%>" width="100" height="100" border="0">
							<%} %>
						</td>
						<td width="30%"><input type="file" name="file4"></td>
						<td width="20%"></td>
					</tr>
					<tr>
						<td width="20%"></td>
						<td width="20%" bgcolor="#EFEFEF">
							<p align="center"><font size=2>メイン画像</font></p>
						</td>
						<td width="10%" height="100">
							<% if(imageMap.get("mainImage") != null) {%>
							<img src="<%=imageMap.get("mainImage")%>" width="100" height="100" border="0">
							<%} %>
						</td>
						<td width="30%" ><input type="file" name="file3"></td>
						<td width="20%"></td>
					</tr>
					<tr>
						<td width="20%"></td>
						<td width="20%" bgcolor="#EFEFEF">
							<p align="center"><font size=2>詳細画像1</font></p>
						</td>
						<td width="10%" height="100">
							<% if(imageMap.get("detailImage1") != null) {%>
							<img src="<%=imageMap.get("detailImage1")%>" width="100" height="100" border="0">
							<%} %>
						</td>
						<td width="30%" ><input type="file" name="file2"></td>
						<td width="20%"></td>
					</tr>
					<tr>
						<td width="20%"></td>
						<td width="20%" bgcolor="#EFEFEF">
							<p align="center"><font size=2>詳細画像2</font></p>
						</td>
						<td width="10%" height="100">
							<% if(imageMap.get("detailImage2") != null) {%>
							<img src="<%=imageMap.get("detailImage2")%>" width="100" height="100" border="0">
							<%} %>
						</td>
						<td width="30%" ><input type="file" name="file1"></td>
						<td width="20%"></td>
					</tr>
				</table>
				</td>
			</tr>
			<tr>
				<td height="75">
				<p align="center"><input type="submit" value="修正">&nbsp;
				<input type="reset" value="クリア"></p>
				</td>
			</tr>
		</table>
		</form>
		<!-- 図書修正 -->
		</td>
	</tr>
</table>
</div>
</body>
</html>