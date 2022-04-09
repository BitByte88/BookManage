function check(){
	var id=loginform.MEMBER_ID.value;
	var pass=loginform.MEMBER_PW.value;
	
	if(id.length == 0){
		alert("アカウントを入力してください。");
		loginform.MEMBER_ID.focus();
		return false;
	}
	if(pass.length == 0){
		alert("パスワードを入力してください。");
		loginform.MEMBER_PW.focus();
		return false;
	}
	
	return true;
}
function openConfirmId(loginform){	
	var url="./MemberFind.member";
	open(url, "confirm", "toolbar=no,location=no,status=no,menubar=no,"+
						 "scrollbars=no,resizable=no,width=600px,height=350");
}