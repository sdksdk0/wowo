  
			  $(function() {
					$.post("servlet/UserInfoServlet", {
						op : "getLoginInfo"
					}, function(data) {
						if (data == "" || data == null) {
							$("#index_loginuser").html("您好！请 [<a class=\"yellowd1\" rel=\"nofollow\" href=\"login.html\" target=\"_blank\">登录</a>]  <b class=\"borderdc\">|</b>   [<a class=\"yellowd1\" rel=\"nofollow\" href=\"reg.html\" target=\"_blank\">注册</a>]   <b class=\"borderdc p_0_10\">|</b>");
						} else {
							$("#index_loginuser").html("欢迎您"+data.uname+"&nbsp;&nbsp;    <a class=\"Gray7\" href=\"javascript:loginout()\"  id=\"loginout\">退出</a><b class=\"borderdc p_0_10\">|</b>");
						}

					}, "json");				
			  });
			  
			  
			  function loginout(){
				  $.post("servlet/UserInfoServlet", {op : "loginout"}, function(data) {
						if(data>0){
							location.href="index.html";
						}
						
					});
				  
			  }