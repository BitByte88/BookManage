function windowclose(){
	opener.document.joinform.MEMBER_ID.value="<%=id %>";
	self.close();
}