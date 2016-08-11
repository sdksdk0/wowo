$(function() {
	$.post("../../servlet/adminInfoServlet", {
		op : "getLoginInfo"
	}, function(data) {
		if (data == "" || data == null) {
			location.href = "../login.html";
		} else {
			$("#index_loginuser").text(data.aname);
			$("#photopic").attr("src", "../../" + data.photos);
		}

	}, "json");

	$('#index_content_info').tabs('add', {
		title : '指令汇科技',
		closable : true,
		fit : true,
		href : "welcome.html"

	});

	$('#index_menu_tree').tree({
		onClick : function(node) {
			var tabs = $('#index_content_info');
			if (node.id == "index_role") {

				if (tabs.tabs("exists", "角色管理")) {
					// 如果存在则选中
					tabs.tabs("select", "角色管理");

				} else {

					tabs.tabs("add", {
						title : '角色管理',
						closable : true,
						fit : true,
						href : "roles.html"
					});

				}
			} else if (node.id == "index_admin") {

				if (tabs.tabs("exists", "管理员信息")) {
					// 如果存在则选中
					tabs.tabs("select", "管理员信息");

				} else {
					tabs.tabs("add", {
						title : '管理员信息',
						closable : true,
						fit : true,
						href : "admin.html"
					});

				}

			}
		}
	});

});

function headexit() {

	$.post("../../servlet/adminInfoServlet", {
		op : "LoginOut"
	}, function(data) {
		if (data!=null){
			location.href="../login.html";	
		}
	});
}
