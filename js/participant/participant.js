
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


function handleCreateNewPlaylist() {
	var form = document.getElementById("createPlayListForm");
	var name = form.name.value;
	alert("Trying to create new Playlist with name: " + name);
	// need to convert to json and call AWS lambda function for creating playlist
}


function handleSearchForSegment() {
	var form = document.getElementById("searchSegmentsForm");
	var character = form.character.value;
	var phrase = form.phrase.value;
	alert("Trying to search for for segment with character: " + character + "\nand phrase: " 
		+ phrase);
}


function handleDeletePlaylist() {
	alert("Attempting to delete playlist");
}

function handleViewPlaylist() {
	alert("Attempting to view playlist");
}
