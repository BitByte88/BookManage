function check(){
	var title=searchform.title.value;
	var publisher=searchform.publisher.value;
	var startDate=searchform.startDate.value;
	var endDate=searchform.endDate.value;
	
	if(title.length == 0 && publisher.length == 0 && startDate.length == 0 && endDate.length == 0){
		alert("入力内容が存在しません。");
		return false;
	}
	
	return true;
}