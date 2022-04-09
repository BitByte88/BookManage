<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
	<link rel="icon" href="favicon/favicon.ico" type="image/x-icon" />
	<link rel="shortcut icon" href="favicon/favicon.ico" type="image/x-icon"> 
	<link rel="apple-touch-icon-precomposed" sizes="144x144" href="favicon/favicon-144x144.png">
	<link rel="apple-touch-icon-precomposed" sizes="72x72" href="favicon/favicon-72x72.png">
	<link rel="apple-touch-icon-precomposed" href="favicon/favicon-54x54.png">
	<jsp:include page="/menu/menu.jsp" />
<title>図書管理システム</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/admin_book_write.js"></script>
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
		<!-- 図書登録 -->
		<form name="bookform" action="./BookAddAction.adbook" method="post"
			enctype="multipart/form-data" onsubmit="return check()">
		<table style="width:100%">
			<tr>
				<td height="26" bgcolor="94d7e7">
					<p align="center">
						<font size=4>図書登録</font>
					</p>
				</td>
			</tr>
			<tr height="26"></tr>
			<tr>
				<td height="331">
				<table style="width:960; margin:auto">
					<tr>
						<td width="20%"></td>
						<td width="20%" height="30" bgcolor="#EFEFEF">
							<p align="center">
								<font size=2>カテゴリー</font>
							</p>
						</td>
						<td width="40%" height="30">
							<select name="book_category" size="1">
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
						<td width="20%"bgcolor="#EFEFEF">
							<p align="center"><font size=2>書名</font></p>
						</td>
						<td width="40%"><input type="text" name="book_name" style="width:390"></td>
						<td width="20%"></td>
					</tr>
					<tr>
						<td width="20%"></td>
						<td width="20%"bgcolor="#EFEFEF">
							<p align="center"><font size=2>著者</font></p>
						</td>
						<td width="40%"><input type="text" name="book_writer" style="width:390"></td>
						<td width="20%"></td>
					</tr>
					<tr>
						<td width="20%"></td>
						<td width="20%" bgcolor="#EFEFEF">
							<p align="center"><font size=2>出版社</font></p>
						</td>
						<td width="40%"><input type="text" name="book_publisher" style="width:390"></td>
						<td width="20%"></td>
					</tr>
					<tr>
						<td width="20%"></td>
						<td width="20%" bgcolor="#EFEFEF">
							<p align="center"><font size=2>発行日</font></p>
						</td>
						<td width="40%"><input type="text" name="book_publishing_date" placeholder="yyyy-MM-dd" style="width:390"></td>
						<td width="20%"></td>
					</tr>
					<tr>
						<td width="20%"></td>
						<td width="20%" bgcolor="#EFEFEF">
							<p align="center"><font size=2>販売価格</font></p>
						</td>
						<td width="40%"><input type="number" min="0" name="book_price" style="width:390"></td>
						<td width="20%"></td>
					</tr>
					<tr>
						<td width="20%"></td>
						<td width="20%" bgcolor="#EFEFEF">
							<p align="center"><font size=2>ISBNコード</font></p>
						</td>
						<td width="40%"><input type="text" name="book_isbn" style="width:390"></td>
						<td width="20%"></td>
					</tr>
					<tr>
						<td width="20%"></td>
						<td width="20%" bgcolor="#EFEFEF">
							<p align="center"><font size=2>図書内容</font></p>
						</td>
						<td width="40%">
							<textarea 
							name="book_content" cols=50 rows=15></textarea>
						</td>
						<td width="20%"></td>
					</tr>
					<tr>
						<td width="20%"></td>
						<td width="20%" bgcolor="#EFEFEF">
						<p align="center"><font size=2>サムネイル用画像</font></p>
						</td>
						<td width="40%"><input type="file" name="file4"></td>
						<td width="20%"></td>
					</tr>
					<tr>
						<td width="20%"></td>
						<td width="20%" bgcolor="#EFEFEF">
						<p align="center"><font size=2>メイン画像</font></p>
						</td>
						<td width="40%"><input type="file" name="file3"></td>
						<td width="20%"></td>
					</tr>
					<tr>
						<td width="20%"></td>
						<td width="20%" bgcolor="#EFEFEF">
						<p align="center"><font size=2>詳細画像1</font></p>
						</td>
						<td width="40%"><input type="file" name="file2"></td>
						<td width="20%"></td>
					</tr>
					<tr>
						<td width="20%"></td>
						<td width="20%" bgcolor="#EFEFEF">
						<p align="center"><font size=2>詳細画像2</font></p>
						</td>
						<td width="40%"><input type="file" name="file1"></td>
						<td width="20%"></td>
					</tr>
				</table>
				</td>
			</tr>
			<tr>
				<td height="75">
				<p align="center"><input type="submit" value="登録">&nbsp;
				<input type="reset" value="クリア"></p>
				</td>
			</tr>
		</table>
		</form>
		<!-- 商品登録 -->
		</td>
	</tr>
</table>
</div>
</body>
</html>