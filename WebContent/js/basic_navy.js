$.fn.moveDiv = function() {
	var obj = $(this); // 获取给定的对象
	var isDown = false;
	var cx = 0;
	var cy = 0;

	var nowx=0;
	var nowy=0;

	// 获取当前对象的位置
	var y = obj.css("top");
	var x = obj.css("left");

	if (y == "auto") {
		y = 0;
	}
	if (x == "auto") {
		x = ($(window).width() - obj.outerWidth()) / 2;
	}

	obj.css({
		"position" : "absolute",
		"cursor" : "move",
		"top" : y,
		"left" : x
	});

	obj.bind({
		mousedown : function(e) {
			isDown = true;

			// 记录当前鼠标点击的位置
			cx = e.clientX;
			cy = e.clientY;

		},
		mouseup : function() {
			isDown = false;

			x = nowx;
			y = nowy;
		},
		mousemove : function(e) {
			// 如果鼠标按下去，说明此对象要跟着鼠标移动
			if (isDown) {
				nowx = x + e.clientX - cx;
				nowy = y + e.clientY - cy;
				$(this).css({
					left : nowx,
					top : nowy
				});
			}
		}

	});

}
