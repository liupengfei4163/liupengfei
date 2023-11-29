//■社員選択MODEL画面Js
$("#searchStore").click(function() {
	var url = location.href + '/searchStore';
	$.ajax({
		type: "POST",
		url: url,
		//        data: JSON.stringify(search),
		data: {name:$("#param_storeName").val()},
		dataType: 'json',
		cache: false,
		timeout: 600000,
        success: function (data) {
            var outputContent = "";
            
            outputContent='<table class="providerTable" th:if="${form != null}">'+
						  '<tr class="firstTr">'+
						  '	<th style="width:20%;text-align: center;">販売店ID</th>'+
						  '	<th style="width:20%;text-align: center;">販売店名</th>'+
						  '	<th style="width:20%;text-align: center;">住所</th>'+
						  '	<th style="width:20%;text-align: center;">電話</th>'+
						  '	<th style="width:20%;text-align: center;">アクション</th>'+
						  '</tr>'
							
            $.each(data, function (index, item) {
                outputContent += '<tr><td><span>' + item.storeId + '</span></td>'
                                    +'<td style="text-align: left;"><span>' + item.storeName    +      '</span></td>'
                                    +'<td style="text-align: left;"><span>' + item.address +      '</span></td>'
                                    +'<td><span>' + item.phone   +      '</span></td>'
                                    +'<td><button type="button" id="selectedButton" value="'+item.storeId+"_"+item.storeName+'" onclick="clickSelect(this);" class="btn btn-secondary" style="padding:1px;">選択</button></td></tr>';
            });
            
            outputContent += "</table>"
            
            $('#outputdiv').html(outputContent);
        },
        error: function (data) {
            console.log(data);
        }
	});

});


function clickSelect(obj) {
	
	//社員ID＋社員名
	var selectedVal = obj.getAttribute("value");
	var valArrary = selectedVal.split("_");
	
	//分割後の配列を一覧画面の社員IDに設定する
	$("#storeId").val(valArrary[0]);
	//分割後の配列を一覧画面の社員名に設定する
	$("#storeName").val(valArrary[1]);
	
	$("#closeButton").trigger("click");
    $('#outputdiv').html("");
	
}

$("#batuButton").click(function() {
	//社員選択画面の一覧をクリアする
    $('#outputdiv').html("");
});

$("#closeButton").click(function() {
	//社員選択画面の一覧をクリアする
    $('#outputdiv').html("");
});

$("#openSelectStoreScreen").click(function() {
	//費用一覧画面の社員名を社員選択画面へ渡す
    $('#param_storeName').val($('#storeName').val());
});

