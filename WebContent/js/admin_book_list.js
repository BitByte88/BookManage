function bookmodify(book_no){
	document.bookform.action="./BookModify.adbook?book_no="+book_no;
	document.bookform.submit();	
}
function bookdelete(book_no){
	document.bookform.action="./BookDelete.adbook?book_no="+book_no;
	document.bookform.submit();	
}