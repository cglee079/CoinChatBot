$(document).ready(function(){
	onClickMenu(3);
	fn_onInitDataGrid();
})

function fn_onInitDataGrid(){
	$('#dg').datagrid({
		url: getContextPath() + '/client-suggests/records',
		title : "사용자 문의/건의 리스트",
		method: 'get',
		singleSelect: true,
		remoteSort: true,
		fitColumns: false,
		pagination: true,
		checkOnSelect: false,
		selectOnCheck: false,
		emptyMsg: '데이터가 없습니다.',
		columns:[[
			{field:'row', title:'번호', width:'5%', halign:'center' , styler : alignCenter, formatter: function (value, row, index){
				var option = $("#dg").datagrid("options");
				return (option.pageNumber - 1) * option.pageSize + index + 1;
				}
			},
			{field:'coinId', title:'코인ID', width:'5%', halign:'center', styler : alignCenter},
			{field:'userId', title:'유저ID', width:'10%', halign:'center', styler : alignCenter, formatter : toUserIdStr },
			{field:'username', title:'유저명', width:'15%', halign:'center', styler : alignCenter, formatter : toUsernameStr},
			{field:'contents', title:'내용', width:'45%', halign:'center', styler : alignLeft},
			{field:'date', title:'날짜', width:'15%', halign:'center', styler : alignCenter},
			{field:'action', title: '편집', width:'70px', halign:'center' , styler : alignCenter, formatter:function(value,row,index){
                var d = '<a href="javascript:void(0)" onclick="deleterow(this)" class="grid-btn-delete">삭제</a>';
                return d;
			}},
		]],
	});
	
	$('#dg').datagrid({
		pageList: [20, 40, 60, 80, 100],
	  	pageSize: 20,
	});
	
	var pager = $('#dg').datagrid('getPager');    // get the pager of datagrid
	pager.pagination({ 
		displayMsg : '{total} 중 {from}-{to} 제안',
		beforePageText : '',
		afterPageText : '페이지 ',
	});
}

function deleterow(target){
	var row = $("#dg").datagrid('getRows')[getRowIndex(target)];
    var c = confirm("정말로 삭제 하시곘습니까?");
    if (c){
    	$.ajax({
			type: "DELETE",
			url: getContextPath() + "/client-suggests/" + row.seq,
			dataType: 'JSON',
			async : false,
			success:function(json){
				$('#dg').datagrid('deleteRow', getRowIndex(target));
				alert("삭제 완료하였습니다.");
			},
			error:function(request,status,error){
		        alert("code:"  + request.status + "\n"+"message:" + request.responseText + "\n"+"error:" + error);
			}
		});
    }
}

