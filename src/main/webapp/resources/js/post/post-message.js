$(document).ready(function(){
	
	var msg;
	switch(caseId){
	case "all" 	: msg = "공지사항 #";break;
	case "one"	: msg = "개발자 메세지 #"; break;
	}
	
	$("#message-area").val(msg + new Date().format("yyyy-MM-dd HH:mm:ss"));
	
	$("#marketId").val('${marketId}');
	$("#enabled").val('${enabled}');
	
	fn_onInitDataGrid();
})

function fn_onInitDataGrid(){
	$('#dg').datagrid({
		"data": data,
		title : "메시지를 전송할 사용자 리스트",
		singleSelect: true,
		remoteSort: true,
		fitColumns: false,
		checkOnSelect: false,
		selectOnCheck: false,
		emptyMsg: '데이터가 없습니다.',
		columns:[[
			{field:'row', title:'번호', width:'5%', halign:'center' , styler : alignCenter, formatter: function (value, row, index){
				return index + 1;
				}
			},
			{field:'coinId', title:'코인ID', width:'5%', halign:'center', styler : alignCenter},
			{field:'userId', title:'유저ID', width:'10%', halign:'center', styler : alignCenter},
			{field:'response', title:'결과', width:'45%', halign:'center', styler : alignLeft}
		]]
	});
}

function send(index, row, content){
	return new Promise(function (resolve, reject) {
		$.ajax({
			type: "POST",
			url: getContextPath() + "/messages/post",
			dataType: 'JSON',
			data: {
				"coinId" 	: row.coinId,
				"userId" 	: row.userId,
				"content" 	: content
			},
			success:function(json){
				console.log(json);
				json["index"] = index;
				resolve(json);
			}
		});
	  });
}

function sendMessage(){
	var c =  confirm("정말로 전송하시겠습니까?");
	if(c){
		var grid = $("#dg");
		var data = grid.datagrid("getData");
		var content = $("#message-area").val();  
		var rows = data.rows;
		
		for(var i = 0; i < count; i++){
			var row = rows[i];
			send(i, row, content).then(function (json) {
				grid.datagrid('updateRow', {
		            "index": json.index,
		            "row": {
		                "response"	: json.response
		            }
		        });
			});
		}
	}
}