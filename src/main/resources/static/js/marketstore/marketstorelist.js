

function clickRead(obj) {
	//参照ボタンを押下
	$("#read").val(obj.getAttribute("value"));
	$("#read").trigger("click");
}
function clickUpdate(obj) {
	//更新ボタンを押下
	$("#update").val(obj.getAttribute("value"));
	$("#update").trigger("click");
}
function clickDelete(obj) {
	if (confirm("削除してもよろしいでしょうか")) {
		//削除ボタンを押下
		$("#delete").val(obj.getAttribute("value"));
		$("#delete").trigger("click");
		return true;
	} else {
		return false;
	}

}


//Jquery
$(function() {

	// 全選択,全解除
	$('#all').on('click', function() {
		$("input[name='checkBoxId']").prop('checked', this.checked);
	});


	// 全削除
	$('#deleteAll').on('click', function() {
		var selCount = 0;
		var selStoreIds = "";
		$("input[name='checkBoxId']:checked").each(function(i, obj) {
			selStoreIds += obj.value + ","
		});

		selCount = $("input[name='checkBoxId']:checked").length;

		if (selCount > 0) {
			$("#deletedItemIds").val(selStoreIds);
			return true;
		} else {
			alert("明細をご選択ください。");
			return false;
		}
	});
});


$(document).ready(function() {
	commonLoad();
});
