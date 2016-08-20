
			
			  $(window).scroll(function () {
				 var t=$(this).scrollTop();

				if(t>300){
					$(".gotopBtn").css("visibility","visible");
				}else{
					$(".gotopBtn").css("visibility","none");
				}
				
			  });
			  
			  
			  
			  
			  $(function() {
				  
				//得到用户登录信息
					$.post("servlet/UserInfoServlet", {
						op : "getLoginInfo"
					}, function(data) {
						if (data == "" || data == null) {
							$("#index_loginuser").html("您好！请 [<a class=\"yellowd1\" rel=\"nofollow\" href=\"login.html\" target=\"_blank\">登录</a>]  <b class=\"borderdc\">|</b>   [<a class=\"yellowd1\" rel=\"nofollow\" href=\"reg.html\" target=\"_blank\">注册</a>]   <b class=\"borderdc p_0_10\">|</b>");
						} else {
							$("#index_loginuser").text("欢迎您"+data.uname);
						}

					}, "json");
					
					
					
					
					$.post("servlet/goodsInfoServlet",{op:"findGoodsIndex"},function(data){
						
						if(data!=null){
							var list=data;
							for(var i=0;i<=list.length;i++){
								
								$("#findGoodsIndex").html(" <ul class=\"goods-allInd clearfix\">" +
										"<li class=\"good-list\"> "
										+"<h2 class=\"good-title\">"
										+"<a class=\"biaoa\"  target=\"_blank\" href=\"#\">"
										+"<strong>["+list[i].area+"],"+list[i].gname+"</strong>"
										+list[i].des+"</a></h2>"
										+"<div class=\"index-smalllogo\">"
										+"<a class=\"yuy\" target=\"_blank\" href=\"#\">免预约</a></div>"
										+"<a class=\"picture\" target=\"_blank\" href=\"#\">"
										+"<img width=\"348\" height=\"232\" rel=\"nofollow\" src=\""+list[i].pic+"\"></a>"
										+"<div class=\"buy-boxInd clearfix\">"
										+"<a class=\"bh buy_a\" rel=\"nofollow\" href=\"details.html\" target=\"_blank\" status=\"0\">去看看</a>"
										+"<span class=\"num\">¥"+list[i].price+"</span>"
										+"<span class=\"past\">价值 ¥196</span></div></li>");
							}

						}else{
							alert("网络异常,加载失败");
						}
						
						
					},"json");
					
			  });
			  
			  
			/*  
			  <li class="good-list">
				<h2 class="good-title">
					<a class="biaoa" title="【石鼓区】仅98元，享价值196元『熊猫餐谋环球海鲜自助餐厅』周一至周五双人自助午餐1份！提供免费WIFI，提供免费停车位！必含女士1名，提供免费专车接送，尽享环球美食！" target="_blank" href="#">
						<strong>【石鼓区】熊猫餐谋环球海鲜自助餐厅</strong>
						周一至周五双人自助午餐1份！提供免费WIFI，提供免费停车位！
					</a>
				</h2>
				<div class="index-smalllogo">
					<a class="yuy" target="_blank" href="#">免预约</a>
				</div>
				<a class="picture" target="_blank" href="#">
					<img width="348" height="232" rel="nofollow" src="images/show1.jpg">
				</a>
				<div class="buy-boxInd clearfix">
					<a class="bh buy_a" rel="nofollow" href="details.html" target="_blank" status="0">去看看</a>
					<span class="num">¥98</span>
					<span class="past">价值 ¥196</span>
				</div>
			</li>*/
			
			
			  
			
			  
			
			  
			  
			  
			  
			  
			  
			  
			  
