
var flag1=false;
var flag2=false;
var flag3=false;

			  $(window).scroll(function () {
				 var t=$(this).scrollTop();

				if(t>100){
					$(".gotopBtn").css("visibility","visible");
				}else{
					$(".gotopBtn").css("visibility","none");
				}
				
			  });
			  
			  
			  $(function(){
					//checkInfo();
					
					$("#logsafecode").focus(function(){
						$("#logsafecode").css("border-color","#eee");
					});
					$("#uname").focus(function(){
						$("#uname").css("border-color","#eee");
					});
					$("#pwd").focus(function(){
						$("#pwd").css("border-color","#eee");
					});
					
					
					
				})
				
				function checkInfo(){	
					$("<label></label>").appendTo($("#uname").parent());
					$("#uname").next().css("color","red");
					$("#uname").next().css("font-size","12px");
					
					$("<label></label>").appendTo($("#pwd").parent());
					$("#pwd").next().css("color","red");
					$("#pwd").next().css("font-size","12px");
					
					$("#uname").bind({
						blur:function(){
							var val=$("#uname").val();
							var reg=/^\d{4}$/;
							
							var tdInfo="";
					
							if(val==""){
								tdInfo="*请输入用户名";
								flag1=false;
							}else if(!reg.test(val)){
								tdInfo="*请输入正确的用户名";	
								flag1=false;
							}else{
								tdInfo="";	
								flag1=true;
							}
							
							$("#uname").next().text(tdInfo);
						},
						focus:function(){
							$("#uname").next().text("");
						}
					})	
					
					$("#pwd").bind({
						blur:function(){
							var val=$("#pwd").val();
							var reg=/^\w{6,16}$/;
							
							var tdInfo="";
					
							if(val==""){
								tdInfo="*请输入密码";
								flag2=false;
							}else if(!reg.test(val)){
								tdInfo="*密码格式不正确";	
								flag2=false;
							}else{
								tdInfo="";	
								flag2=true;
							}
							
							$("#pwd").next().text(tdInfo);
						},
						focus:function(){
							$("#pwd").next().text("");
						}
					});
				}
			  
			  
			  
				function changeCode(obj){
					obj.src="back/valiCodeImg.jsp?="+new Date().getTime();
				} 
			  
			  
				function userLogin(){
					var name=$.trim($("#uname").val());
					var pwd=hex_md5($.trim($("#pwd").val()));
					var code=$.trim($("#logsafecode").val());
			
				
					if(name==""){
						$("#uname").css("border-color","red");
						return false;
						flag1=false;
					}
					if(pwd==""){
						$("#pwd").css("border-color","red");
						return false;
						flag2=false;
					}
					if(code==""){
						$("#logsafecode").css("border-color","red");
						return false;
					}

				//	if(flag1==true && flag2==true ){

						$.post("servlet/UserInfoServlet",{op:"userLogin",name:name,pwd:pwd,code:code},function(data){
							data=parseInt($.trim(data));
							if(data==1){
								$("#logsafecode").css("border-color","red");
								
							}else if(data==2){
								alert("用户名或密码错误");
							}else if(data==3){
								location.href="index.html";
							}
						});
						
				//	}
					
					return false;
					
				}
			  
			  
			  