function isBuy(bookform) {
	var amount= document.bookform.amount.value;
	if (amount.length == 0) {//必須チェック（数量）
		alert("数量を入力してください。");
		return;
	} else if(isNaN(amount)){//形式チェック（数量）
		alert("数量は数字を入力してください。");
		return;
	} else if(parseInt(amount) <= 0){//入力する数量が０以下の場合
		alert("数量は１冊以上を注文してください。");
		return;
	}
	var isbuy=confirm("購入しますか");
	if(isbuy==true) {
		bookform.action="OrderStart.order";
		bookform.submit();
	} else {
		return;
	}
}

function isCart(cartform) {
	var amount = document.bookform.amount.value;
	if (amount.length == 0) {//必須チェック（数量）
		alert("数量を入力してください。");
		return;
	} else if(isNaN(amount)){//形式チェック（数量）
		alert("数量は数字を入力してください。");
		return;
	} else if(parseInt(amount) <= 0){//入力する数量が０以下の場合
		alert("数量は１冊以上を注文してください。");
		return;
	}
	
	var isbuy=confirm("カートに入れますか");
	
	if(isbuy==true) {
		cartform.action="CartAdd.cart";
		cartform.submit();
	} else {
		return;
	}
}

function count_change(temp){
	var test=document.bookform.amount.value;
	if(temp==0){
		test++;
	}else if(temp==1){
		if(test> 1) test--;
	}
	
	document.bookform.amount.value=test;
}