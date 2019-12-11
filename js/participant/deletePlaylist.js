function handleDeletePlaylist(playlist) {	
	var data = {};
	data["playlistName"] = playlist;
	
	var js = JSON.stringify(data);
	console.log("JS:" + js);
	var xhr = new XMLHttpRequest();
	xhr.open("POST", delete_playlist_url, true);
	
	xhr.send(js);
	
	xhr.onloadend = function () {
		console.log(xhr);
		if (xhr.readyState == XMLHttpRequest.DONE) {
			if (xhr.httpCode == 200) {
				console.log("XHR: " + xhr.responseText);
				listPlaylist();
			} else {
				console.log("actual:" + xhr.responseText);
				var js = JSON.parse(xhr.responseText);
				var err = js["response"];
				alert(err);
			}
		}
	};
}