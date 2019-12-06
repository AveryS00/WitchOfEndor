
function onLoad() {
	listVideoSegments();
	listPlaylist();
}

window.onload = onLoad();


function toggleSegments() {
	var div = document.getElementById("allSegments")
	div.hidden = !div.hidden;
	var button =  document.getElementById("segmentToggle")
	if(button.innerHTML == "Show Segments") {
		button.innerHTML = "Hide Segments"
	} else {
		button.innerHTML = "Show Segments"
	}
}

function togglePlaylists() {
	var div = document.getElementById("allPlaylists")
	div.hidden = !div.hidden;
	var button =  document.getElementById("playlistToggle")
	if(button.innerHTML == "Show Playlists") {
		button.innerHTML = "Hide Playlists"
	} else {
		button.innerHTML = "Show Playlists"
	}
}

function handleViewPlaylist(playlistName) {
	alert("Attempting to view playlist: " + playlistName);
}
