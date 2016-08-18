	
var flag1=false;
var flag2=false;
var flag3=false;
var flag4=false;
var flag5=false;



$(function() {
		$(".container").css("position", "fixed").css("top",
				($(window).height() - $(".container").height()) / 2).css(
				"left", ($(window).width() - $(".container").width()) / 2);

		$('.close-button').click(
				function() {
					$(this).parent().removeClass("slidePageInFromLeft")
							.addClass("slidePageBackLeft");
				});

		$(window).resize(
				function() {
					$(".container").css("position", "fixed")
							.css(
									"top",
									($(window).height() - $(".container")
											.height()) / 2).css(
									"left",
									($(window).width() - $(".container")
											.width()) / 2);
				});
		
		//$("#div1").moveDiv();
		
		$.post("../servlet/RolesServlet",{op:"findAllRoles"},function(data){
			
			var str="";
			var str1="";
			$.each(data,function(index,item){
				str+="<li><a id='"+item.rid+"' href=\"javascript:login('"+item.rid+"','loginrole')\">"+item.rname+"</a></li>";
				str1+="<li><a id='"+item.rid+"' href=\"javascript:login('"+item.rid+"','role')\">"+item.rname+"</a></li>";
				
			});
			
			$("#loginRoles").html("").append($(str));
			$("#registerRoles").html("").append($(str1));
			
		},"json");
		
		
		$("#vcode").focus(function(){
			$("#vcode").css("border-color","#eee");
		});
		$("#uname").focus(function(){
			$("#uname").css("border-color","#eee");
		});
		$("#pwd").focus(function(){
			$("#pwd").css("border-color","#eee");
		});
		

	});
	
	//改变角色的时候触发

	function login(id, role) {
		var flag = $("#" + id).text();
		$("#" + role).val(flag);
		
		$("#roleId").val(id);  //将id存到一个隐藏域中
		
		flag1=true;
	}

	function showRegisterPage() {
		$(".register-page").addClass("slidePageInFromLeft").removeClass(
				"slidePageBackLeft");
	}

	function backlogin() {
		$(".register-page").removeClass("slidePageInFromLeft").addClass(
				"slidePageBackLeft");
	}
	
	function changeCode(obj){
		obj.src="valiCodeImg.jsp?="+new Date().getTime();
	}
	
	function adminLogin(){
		var role=$.trim($("#roleId").val());
		var name=$.trim($("#uname").val());
		var pwd=hex_md5($.trim($("#pwd").val()));
		var code=$.trim($("#vcode").val());
		
		if(role==""){
			return false;
		}
		if(name==""){
			$("#uname").css("border-color","red");
			return false;
		}
		if(pwd==""){
			$("#pwd").css("border-color","red");
			return false;
		}
		if(code==""){
			$("#vcode").css("border-color","red");
			return false;
		}
		
		$.post("../servlet/adminInfoServlet",{op:"adminLogin",role:role,name:name,pwd:pwd,code:code},function(data){
			data=parseInt($.trim(data));
			if(data==1){
				$("#vcode").css("border-color","red");
			}else if(data==2){
				alert("用户名或密码错误");
			}else if(data==3){
				location.href="manager/index.html";
			}
		});
		return false;
	}
	
	
	//用户注册
	
	$(function(){
		
		
		//用户名
		$("#rname").blur(function(){
			var rname=$.trim($("#rname").val());
			
			var reg=/^[a-zA-Z\u4e00-\u9fa5][a-zA-Z0-9_\u4e00-\u9fa5]{2,17}$/;
			
			if(!reg.test(rname)){
				$("#rname").css("border-color","red");
				$("#unamespan").text("由2-12位的中文、字母、数字和下划线组成").css("color","red");
			}else{
				$("#rname").css("border-color","#eee");
				$("#unamespan").text("输入正确").css("color","green");
				flag2=true;
			}
		});
		
		
		//密码
		$("#rpwd").blur(function(){
			var rpwd=$.trim($("#rpwd").val());
			
			var reg=/^\w{6,16}$/;
			
			if(!reg.test(rpwd)){
				$("#rpwd").css("border-color","red");
				$("#rpwdspan").text("由6-16位的字母、数字和下划线组成").css("color","red");
			}else{
				$("#rpwd").css("border-color","#eee");
				$("#rpwdspan").text("输入正确").css("color","green");
				flag3=true;
			}
		});
		
		
		//确认密码
		$("#rpwds").blur(function(){
			var rpwds=$.trim($("#rpwds").val());
			var rpwd=$.trim($("#rpwd").val());
			
			if(rpwds.length<=0){
				$("#rpwds").css("border-color","red");
				$("#rpwdsspan").text("请再输入一次密码，以确认").css("color","red");
			}else  if(rpwd==rpwds){
				$("#rpwds").css("border-color","#eee");
				$("#rpwdsspan").text("输入正确").css("color","green");
				
			}else{
				$("#rpwds").css("border-color","red");
				$("#rpwdsspan").text("请再输入一次密码，以确认").css("color","red");
			}
		});
		
		
		//邮箱后缀自动补齐
	    $('#email').emailComplete({
	        opacity: 1,
		     radius: 4
	    });
	
		
		
		//邮箱
		$("#email").blur(function(){
			var email=$.trim($("#email").val());
			
			var reg=/^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/; 
			
			if(!reg.test(email)){
				$("#email").css("border-color","red");
				$("#emailspan").text("请输入邮箱账号，以便忘记密码时找回").css("color","red");
				
			}else{
				
			
				$.post("../servlet/adminInfoServlet",{op:"checkAllEmail",email:email},function(data){
			
					data=$.trim(data);
					if(data>0){
						$("#email").css("border-color","red");
						$("#emailspan").text("该邮箱已存在");
						$("#emailspan").css("color","red");
					}else{
						$("#email").css("border-color","#eee");
						$("#emailspan").text("该邮箱可以注册").css("color","green");
						flag4=true;
					}	
				});
				
				
				
				
			}
		});
		
		
		//联系方式
		$("#tel").blur(function(){
			var tel=$.trim($("#tel").val());
			
			var reg=/^[0-9]{4}[\-]{0,1}[0-9]{7}$/;; 


			if(!reg.test(tel)){
				$("#tel").css("border-color","red");
				$("#telspan").text("请输入你的电话").css("color","red");

			}else{
				$("#tel").css("border-color","#eee");
				$("#telspan").text("输入正确").css("color","green");
				flag5=true;
				
			}
		});
		
		//获取焦点的时候要把红色警告去掉
		$("#rname").focus(function(){
			$("#rname").css("border-color","#eee");
			$("#unamespan").css("color","green");
		});
	
		
		$("#email").focus(function(){
			$("#email").css("border-color","#eee");
			$("#emailspan").css("color","green");
		});
		$("#rpwd").focus(function(){
			$("#rpwd").css("border-color","#eee");
			$("#rpwdspan").css("color","green");
		});
		$("#rpwds").focus(function(){
			$("#rpwds").css("border-color","#eee");
			$("#rpwdsspan").css("color","green");
		});
		
		$("#tel").focus(function(){
			$("#tel").css("border-color","#eee");
			$("#telspan").css("color","green");
		});
		
	});
	
	
	
	
	
	//点击注册
	function registAdmin(){
		var rid=$.trim($("#roleId").val());
		var uname=$.trim($("#rname").val());
		var rpwd=hex_md5($.trim($("#rpwd").val()));
		var email=$.trim($("#email").val());
		var tel=$.trim($("#tel").val());
	
		
		if(flag1==true && flag2==true  && flag3==true && flag4==true  && flag5==true ){
			$.post("../servlet/adminInfoServlet",{op:"registAdmin",rid:rid,uname:uname,rpwd:rpwd,email:email,tel:tel},function(data){
				data=parseInt($.trim(data));
				if(data>0){
					alert("注册成功");
					location.href="login.html";
				}else{
					alert("注册失败");
				}
			});
		}
		
		
	}