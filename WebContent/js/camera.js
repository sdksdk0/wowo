function init(t) {
	accessLocalWebCam("navy_video");
}

window.URL = window.URL || window.webkitURL || window.msURL || window.oURL;
navigator.getUserMedia = navigator.getUserMedia|| navigator.webkitGetUserMedia || navigator.mozGetUserMedia|| navigator.msGetUserMedia;

function isChromiumVersionLower() {
	var ua = navigator.userAgent;
	var testChromium = ua
	.match(/AppleWebKit\/.* Chrome\/([\d.]+).* Safari\//);
	return (testChromium && (parseInt(testChromium[1].split('.')[0]) < 19));
}

function successsCallback(stream) {
	document.getElementById('navy_video').src = (window.URL && window.URL.createObjectURL) ? window.URL.createObjectURL(stream): stream;
	playPhoto();
}

function errorCallback(err) {

}

function accessLocalWebCam(id) {
	try {
		navigator.getUserMedia({
			video : true
		}, successsCallback, errorCallback);
	} catch (err) {
		navigator.getUserMedia('video', successsCallback, errorCallback);
	}
}

function playPhoto(){
	var canvas = document.getElementById("canvas"),
	context = canvas.getContext("2d"), video = document.getElementById("navy_video"), videoObj = {
		"video" : true
	}, errBack = function(error) {
		console.log("相机调用失败...", error.code);
	};
	
	document.getElementById("snap").addEventListener("click", function() {
		context.drawImage(video, 0, 0,$("#canvas").width(), $("#canvas").height());
	});
};