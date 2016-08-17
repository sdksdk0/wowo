var flag1=false;  //用户编号确，则返回true
var flag2=false;  //邮箱确，则返回true
var flag3=false;  //邮箱验证码正确，则返回true
var code=null;
var flag4=false;
var flag5=false;

$(function() {
	
	//移动
	$("#wrapper").moveDiv();
	
	//用户编号
	$("#username").blur(function(){
		var username=$.trim($("#username").val());
		var reg1=/^\d/; 
		
		if(username.length<=0 ){
			$("#username").css("border-color","red");
			$("#userspan").text("用户编号不能为空");
			$("#userspan").css("color","red");
		}else  if(!reg1.test(username)){
			$("#username").css("border-color","red");
			$("#userspan").text("请输入数字");
			$("#userspan").css("color","red");
		}else {
			$.post("../servlet/adminInfoServlet",{op:"checkUsername",username:username},function(data){
				
				data=$.trim(data);
				if(data==1){
					$("#username").css("border-color","green");
					flag1=true;
					
				}else{
					$("#username").css("border-color","red");
					$("#userspan").text("该用户编号不存在");
					$("#userspan").css("color","red");
				}	
			});
		}
		
	});
	
	
	/*邮箱*/
	$("#email").blur(function(){
		$(".rcodeInfo strong").text($(this).val());
		
		var username=$.trim($("#username").val());
		var email=$.trim($("#email").val());
		
		if(email.length<=0 ){
			$("#email").css("border-color","red");
			$("#emailspan").text("邮箱不能为空");
			$("#emailspan").css("color","red");
		}
		
		var reg=/^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/; 
		if(reg.test(email)){
			
			$.post("../servlet/adminInfoServlet",{op:"checkEmail",username:username,email:email},function(data){
				
				data=$.trim(data);
				if(data==1){
					$("#email").css("border-color","green");
					flag2=true;
					
				}else{
					$("#email").css("border-color","red");
					$("#emailspan").text("该邮箱不存在");
					$("#emailspan").css("color","red");
				}	
			});
		}else{
			$("#email").css("border-color","red");
			$("#emailspan").text("邮箱格式不正确");
			$("#emailspan").css("color","red");
		}	
	});
	
	//邮箱验证码
	
	$("#rcode").blur(function(){
		var rcode=$.trim($("#rcode").val());
		
		
		if(rcode.length<=0){
			$("#rcode").css("border-color","red");
			$("#rcodespan").text("验证码不能为空");
			$("#rcodespan").css("color","red");
			
		}else {
			$.post("../servlet/adminInfoServlet",{op:"chenkoutDate",rcode:rcode,time:time},function(){
				
				alert(data);
				data=$.trim(data);
				if(data==1){
					$("#rcode").css("border-color","green");
					flag3=true;	
				}else  if(data=2){
					$("#rcode").css("border-color","red");
					$("#rcodespan").text("验证码超时，请重新发送");
					$("#rcodespan").css("color","red");
				}else{
					$("#rcode").css("border-color","red");
					$("#rcodespan").text("验证码错误");
					$("#rcodespan").css("color","red");
				}
				
			});				
		}
		
	});
	
	
	//输入密码验证
	$("#newpwd").blur(function(){
		var newpwd=$.trim($("#newpwd").val());

		var reg=/^\w{6,16}$/;
		if(reg.test(newpwd)){
			$("#newpwd").css("border-color","green");
			flag4=true;
		}else{
			$("#newpwd").css("border-color","red");
			$("#newpwdspan").text("密码必须由6-16位的字母、数字和下划线组成");
			$("#newpwdspan").css("color","red");
		}
		
	});
	
	
	$("#rpwds").blur(function(){
		var newpwd=$.trim($("#newpwd").val());
		var rpwds=$.trim($("#rpwds").val());

		
		if(rpwds.length<=0){
			$("#rpwds").css("border-color","red");
			$("#rpwdsspan").text("不能为空");
			$("#rpwdsspan").css("color","red");	
		}else  if(newpwd==rpwds){
			$("#rpwds").css("border-color","green");
			flag5=true;
		}else{
			$("#rpwds").css("border-color","red");
			$("#rpwdsspan").text("两次密码不一致");
			$("#rpwdsspan").css("color","red");	
		}
		
	});
	
	
	
	$('#navigation').show();
    $('#navigation a').bind('click',function(e){
		var $this=$(this);
		if($this.attr("href")!="#"){
			return;
		}else{
			$this.closest('ul').find('li').removeClass('selected');
			$this.parent().addClass('selected');
			$this.parent().find("span").addClass("checked").removeClass("error");
			
			var num=$this.data("type-name");
			$('#steps').stop().animate({marginLeft:'-'+num*600+'px'},500);
		}
    });
    
    $("#username").focus(function(){
		$("#username").css("border-color","#eee");
		$("#userspan").text("");
	});
    
	$("#email").focus(function(){
		$("#email").css("border-color","#eee");
		$("#emailspan").text("");
	});
    
	$("#rcode").focus(function(){
		$("#rcode").css("border-color","#eee");
		$("#rcodespan").text("");
	});
	
	$("#newpwd").focus(function(){
		$("#newpwd").css("border-color","#eee");
		$("#newpwdspan").text("");
	});
	
	$("#rpwds").focus(function(){
		$("#rpwds").css("border-color","#eee");
		$("#rpwdsspan").text("");
	});
	
	
    
});



/*获取验证码*/
function getCodeInfo(){
	var username=$.trim($("#username").val());
	var email=$.trim($("#email").val());

			if( flag1==true && flag2==true ){
				
				$("#navigation li").removeClass("selected");
				$("#navigation li:eq(1)").addClass("selected");
				$("#navigation li span:eq(1)").addClass("checked").removeClass("error");
				$('#steps').stop().animate({marginLeft:'-600px'},500);
				
				$.post("../servlet/adminInfoServlet",{op:"sendEmail",rid:username,email:email},function(data){
						//code=$.trim(data);
						
						$("#username").val();
						$("#email").val();
				});	
			}	
		}


/*输入验证码后的下一步，即新密码设置*/
function nextstep(){
		if(flag3==true){
			$("#navigation li").removeClass("selected");
			$("#newpwdli").addClass("selected");
			$("#newpwdli a").attr("href","#");
			$("#newpwdli span").addClass("checked").removeClass("error");
			$('#steps').stop().animate({marginLeft:'-1200px'},500);
			
		}else{
			$("#rcode").css("border-color","red");
		}
}

//修改密码
function restPassword(){
	var username=$.trim($("#username").val());
	var newpwd=hex_md5($.trim($("#newpwd").val()));
	
	
	if(flag4==true && flag5==true){
			$.post("../servlet/adminInfoServlet",{op:"restPassword",username:username,newpwd:newpwd},function(data){
				data=$.trim(data);
				if(data==1){
					//修改成功，跳回登录页
					alert("修改成功");
					location.href="login.html";	
				}else{
					alert("修改失败,请重新修改");
				}
			});	
		
	}else{
		$("#newpwd").css("border-color","red");
	}

}



