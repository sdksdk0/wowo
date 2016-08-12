$(function() {
	/*邮箱*/
	$("#email").blur(function(){
		$(".rcodeInfo strong").text($(this).val());
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
	});
    
	$("#email").focus(function(){
		$("#email").css("border-color","#eee");
	});
    
    
});

/*输入验证码后的下一步，即新密码设置*/
function nextstep(){
	$("#navigation li").removeClass("selected");
	$("#newpwdli").addClass("selected");
	$("#newpwdli a").attr("href","#");
	$("#newpwdli span").addClass("checked").removeClass("error");
	$('#steps').stop().animate({marginLeft:'-1200px'},500);
}

/*获取验证码*/
function getCodeInfo(){

	
	var username=$.trim($("#username").val());
	var email=$.trim($("#email").val());
	var rcode=$.trim($("#rcode").val());
	
	if(username.length>0 ){
		
		var reg=/^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/; 
		if(reg.test(email)){
			if( email.length>0){
				$("#navigation li").removeClass("selected");
				$("#navigation li:eq(1)").addClass("selected");
				$("#navigation li span:eq(1)").addClass("checked").removeClass("error");
				$('#steps').stop().animate({marginLeft:'-600px'},500);
				
				$.post("../servlet/adminInfoServlet",{op:"sendEmail",email:email},function(data){
						data=$.trim(data);
						
						if(data==rcode){
							alert("验证成功");
						}else{
							alert("验证码错误");
						}
					
					
				});
				
			}	
		}else{
			$("#email").css("border-color","red");
		}
		
	}else{
		$("#username").css("border-color","red");
	}
	
	
	
	
	
	
	
}