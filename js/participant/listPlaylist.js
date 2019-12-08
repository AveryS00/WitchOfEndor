function listPlaylist() {
	var xhr = new XMLHttpRequest();
	
	xhr.open("GET", playlist_url, true);
	
	// send the collected data as JSON
	xhr.send("");
	
	// This will process results and update HTML as appropriate. 
	xhr.onloadend = function () {
		console.log(xhr);
		console.log(xhr.request);
		if (xhr.readyState == XMLHttpRequest.DONE) {
			processPlaylistResponse(xhr.responseText);
		} else {
			processPlaylistResponse("N/A");
		}
	};
}

function processPlaylistResponse(txt) {
	console.log("result: " + txt)
	var json = JSON.parse(txt)
	var list = document.getElementById('playlistList')
	// if error show error
	if(json.statusCode != 200) {
		var errorElement = document.createElement('p');
		errorElement.innerHTML = "Error " + json.statusCode +  ": " + json.error;
		list.appendChild(errorElement);
		return;
	}
	list.innerHTML = "";
	// create list of elements
	for(playlist in json.list) {
		playlist = json.list[playlist];
		var li = document.createElement('li');
		var title = document.createElement('p');
		title.innerHTML = playlist['name'];
		var viewButton = document.createElement('button');
		viewButton.innerHTML = 'View Playlist';
		viewButton.onclick = handleViewPlaylist;
		li.appendChild(title);
		li.appendChild(createViewPlaylistButton(playlist.videos, li));
		li.appendChild(createDeletePlaylistButton(playlist.name));
		list.appendChild(li);
	}
}

function createDeletePlaylistButton(playlistName) {
	var button = document.createElement('button');
	button.innerHTML = 'Delete Playlist';
	button.onclick = function(){handleDeletePlaylist(playlistName)};
	return button;
}

function createViewPlaylistButton(segmentsList, list) {
	var button = document.createElement('button');
	button.innerHTML = 'View Playlist';
	button.onclick = function(){
		handleViewPlaylist(segmentsList, list)
		toggleViewButton(button)
		};
	return button;
}
