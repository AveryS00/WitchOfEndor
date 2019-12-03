function handleAppendClick(e) {
	var form = document.appendForm;
	
	var data = {};
	data["playlistName"] = form.playlistName.value;
	data["videoLocation"] = form.videoLocation.value;
	
	var js = JSON.stringify(data);
	console.log("JS:" + js);
	var xhr = new XMLHttpRequest();
	xhr.open("POST", append_url, true);
	
	xhr.send(js);
	
	xhr.onloadend = function () {
		console.log(xhr);
		console.log(xhr.request);
		if (xhr.readyState == XMLHttpRequest.DONE) {
			if (xhr.status == 200) {
				console.log("XHR: " + xhr.responseText);
				//processAppendResponse(xhr.repsonseText);
				//refreshPlaylistSegmentsList();
			} else {
				console.log("actual:" + xhr.responseText);
				var js = JSON.parse(xhr.responseText);
				var err = js["response"];
				alert(err);
			}
		} else {
			processCreateResponse("N/A");
		}
	};
}