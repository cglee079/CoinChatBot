<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">


<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-3.2.1.min.js"></script>

<script>

/* context Path */
var contextPath = "${pageContext.request.contextPath}";
function getContextPath(){ return contextPath; }

</script>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/jquery-easyui-1.5.5/themes/default/easyui.css"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/jquery-easyui-1.5.5/themes/icon.css"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/jquery-easyui-1.5.5/themes/color.css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/jquery-easyui-1.5.5/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/jquery-easyui-1.5.5/datagrid-detailview.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/jquery-easyui-1.5.5/datagrid-groupview.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/jquery-easyui-1.5.5/datagrid-cellediting.js"></script>


<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/common.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/included/included-nav.css" />

<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/common.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/common-datagrid.js"></script>

<title>Coin.CHATBOT</title>

<script>
function onClickMenu(index){
	var menus = $(".nav-menu .nav-menu-item");
	menus.removeClass("on");
	menus.eq(index).addClass("on");
}
</script>


