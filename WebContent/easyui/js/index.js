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
			var title="指令汇科技";
			var href="welcome.html";
			
			
			
			if (node.id == "index_role") {

				if (tabs.tabs("exists", "角色管理")) {
					// 如果存在则选中
					tabs.tabs("select", "角色管理");
					return;	
				} else {
					title="角色管理";
					href="roles.html";
				}
			} else if (node.id == "index_admin") {

				if (tabs.tabs("exists", "管理员信息")) {
					// 如果存在则选中
					tabs.tabs("select", "管理员信息");
					return;
				} else {
					title="管理员信息";
					href="admin.html";
				}

			}else if(node.id=="index_shopping3"){
				if (tabs.tabs("exists", "店铺信息管理")) {
					// 如果存在则选中
					tabs.tabs("select", "店铺信息管理");
					return;
				} else {
					title="店铺信息管理";
					href="manaagershopping.html";
				}

				
			}
			
			tabs.tabs("add", {
				title : title,
				closable : true,
				fit : true,
				href : href
			});
			

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
