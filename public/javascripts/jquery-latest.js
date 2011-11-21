
/*Para que funcione tiene que estar dentro de un fieldset los checbox y ser de clase class="checkall"*/

$(function () {	
	$(".checkall").click(function () {		
	$(this).parents('fieldset:eq(0)').find(':checkbox').attr('checked', this.checked);	
	});
});

