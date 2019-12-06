function handleCreateNewPlaylist() {
	var form = document.createPlaylistForm;
	
	var data = {};
	data["playlistName"] = form.playlistName.value;
	
	var js = JSON.stringify(data);
	console.log("JS:" + js);
	var xhr = new XMLHttpRequest();
	xhr.open("POST", playlist_url, true);
	
	xhr.send(js);
	
	xhr.onloadend = function () {
		console.log(xhr);
		console.log(xhr.request);
		if (xhr.readyState == XMLHttpRequest.DONE) {
			if (xhr.status == 200) {
				console.log("XHR:" + xhr.responseText);
				var playlistInput = document.getElementById('textInput').value = "";
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