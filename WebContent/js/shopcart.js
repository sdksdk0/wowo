
  $(window).scroll(function () {
				 var t=$(this).scrollTop();

				if(t>100){
					$(".gotopBtn").css("visibility","visible");
				}else{
					$(".gotopBtn").css("visibility","none");
				}
				
			  });

  
  
			$(".yahei").bind({
				change:function(){
					getPrice(this);
				}
			});
			
			function getPrice(obj){
				var price=$(obj).parent().parent().prev().children().eq(0).text();
				price=price.substr(1,price.length-1);
				
				price=price*$(obj).val();
				$(obj).parent().parent().next().children().eq(0).text( "￥"+price);
				
				getAllPrice();
			}
			
			
			function getAllPrice(){
					var allPrice=0;
				$("tbody").children().each(function(index, element) {
                   var singlePrice=$(element).children().eq(4).children().eq(0).text();
				   singlePrice=singlePrice.substr(1,singlePrice.length-1);
				   allPrice+= parseInt( singlePrice );
                });
				$("tfoot .price").text("￥"+allPrice);
			}
			
			
			function subNum(obj){
				var num=$(obj).next().val();
				if(num<=0){
					$(obj).next().val(1);
				}else if(num>999){
					$(obj).next().val(999);
				}else{
					num--;
					$(obj).next().val(num);
					getPrice($(obj).next());
				}
			}
			
			function addNum(obj){
				var num=$(obj).prev().val();
				if(num<0){
					$(obj).prev().val(1);
				}else if(num>=999){
					$(obj).prev().val(999);
				}else{
					num++;
					$(obj).prev().val(num);
					getPrice($(obj).prev());
				}
			}
			
			function removeProduct(obj){
				$(obj).parent().parent().remove();
				getAllPrice();
			}
			
			
			
			
			
		function delOneItem(gid){
    		var sure = window.confirm("确定要删除吗？");
    		if(sure){
    			location.href="${pageContext.request.contextPath}/servlet/goodsInfoServlet?op=delOneItem&gid="+gid;
    		}
    	}
			
			
		/*window.onload=function(){
			productCount();	
			
		}
		
		$("#productnum").blur(function(){
			productCount();
			}
		);
		
		function productCount(){
			var total=0;  //总金额
			var price=0;
			var num=0;
			var sum=0;
			
			
			
			var oldprice=$("#wwprice").text();
			price=$.trim(oldprice.replace("¥",""));

			num=$("#productnum").val();
			
		var trs=document.getElementById("mytable").getElementsByTagName("tr");
	
		for(var i=0;i<trs.length;i++){
		
			if(isNaN(num)){
				alert("您输入的数量有误，请重新输入");
				$(this).select();
				return;	
			}
			
			
			tot=price*num;
			total=tot.toFixed(2);
			
			
		}
			sum+=tot;
			
			$("#yprice").text("¥"+total);
			$("#tolprice").text(sum);
			
		}
		
			
		$(".decrNum").click(function(){
				var val1=$("#productnum").val();
				if(val1<=1){
					alert("不能再减了哦");
					return;
				}else{
					val1--;
					$("#productnum").attr("value",val1);
				}
				productCount();
			
		});
		
		$(".addNum").click(function(){
				var val1=$("#productnum").val();
				val1++;
				$("#productnum").attr("value",val1);
				productCount();
			
		});
			*/
			
			
			
			
			
			
			
			
			
			
