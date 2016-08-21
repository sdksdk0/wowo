
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
