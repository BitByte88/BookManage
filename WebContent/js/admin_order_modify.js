function check(){
	var memo=memberform.memo.value;
	var status=memberform.status.value;
	
	if(memo.length > 128){//桁数チェック（メモー）
		alert("メモーは128文字以内まで入力してください。");
		bookform.memo.focus();
		return false;		
	}
	
	if(status.length == 0){//必須チェック（注文ステータス）
		alert("注文ステータスを選択してください。");
		memberform.status.focus();
		return false;
	}
	
	return true;
}