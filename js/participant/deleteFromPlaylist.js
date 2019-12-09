
function handleDeleteFromPlaylistRequest() {
	
	var playlistName = document.getElementById('deleteFromPlaylistName').value;
	var location = document.getElementById('deleteFromPlaylistSegmentName').value;
	
	var data = {}
	data['playlistName'] = playlistName;
	data['location'] = location;
	
	var json = JSON.stringify(data);
	
	console.log("Delete Request: " + json)
	
	var xhr = new XMLHttpRequest();
		
		xhr.open("POST", remove_from_playlist_url, true);
		
		// send the collected data as JSON
		xhr.send(json);
		
		// This will process results and update HTML as appropriate. 
		xhr.onloadend = function () {
			console.log(xhr);
			console.log(xhr.request);
			if (xhr.readyState == XMLHttpRequest.DONE) {
				processDeleteFromPlaylistResponse(xhr.responseText);
			} else {
				processDeleteFromPlaylistResponse("N/A");
			}
		};
}

function processDeleteFromPlaylistResponse(txt) {
	json = JSON.parse(txt);
	if(json.statusCode != 200) {
		alert("Error " + json.statusCode + ": " + json.error)
	}
	location.reload();
}




