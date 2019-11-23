

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