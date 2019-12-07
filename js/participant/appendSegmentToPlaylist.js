function createAddToPlaylistButton(segment, list) {
	var button = document.createElement('button');
	button.innerHTML = 'Add to Playlist';
	button.onclick = function(){handleAddToPlaylistButtons(segment.location, list)};
	return button;
}

function handleAddToPlaylistButtons(location, list) {
	var playlistListChildren = document.getElementById('playlistList').childNodes;
	var li = document.createElement('li');
	for (var i = 0; i < playlistListChildren.length; i++) {
		var playlistName = playlistListChildren[i].childNodes[0].textContent;
		li.appendChild(createPlaylistButton(location, playlistName, list));
	}
	list.append(li);
}

function createPlaylistButton(videoLocation, playlistName, list) {
	var button = document.createElement('button');
	button.innerHTML = playlistName;
	button.onclick = function() {
		list.removeChild(list.childNodes[list.childNodes.length - 1]);
		handleAppendClick(videoLocation, playlistName)
		};
	return button;
}

function handleAppendClick(videoLocation, playlistName) {
	var data = {};
	data["playlistName"] = playlistName;
	data["videoLocation"] = videoLocation;
	
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
				alert("Added to Playlist");
			} else {
				console.log("actual:" + xhr.responseText);
				var js = JSON.parse(xhr.responseText);
				var err = js["response"];
				alert(err);
			}
		}
	};
}