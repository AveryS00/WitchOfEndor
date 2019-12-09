function handleViewPlaylist(segmentsList, list) {
	var shown = list.getAttribute("shown") == "true";
	if(shown) {
		var toRemove = [];
		list.setAttribute("shown", "false");
		for(i in list.childNodes) {
			var node = list.childNodes[i];
			if(node.tagName == "VIDEO") {
				toRemove.push(node);
			}
		}
		for(i in toRemove) {
			var node = toRemove[i];
			list.removeChild(node);
		}
	} else {
		list.setAttribute("shown", "true");
		for (var i = 0; i < segmentsList.length; i++) {
			var video = document.createElement('video');
			var source = document.createElement('source');
			source.type = 'video/ogg';
			source.src = segmentsList[i].location;
			video.width = 320;
			video.height = 240;
			video.controls = true;
			video.appendChild(source);
			list.appendChild(video);
		}
	}
}



function toggleViewButton(button) {
	if (button.innerHTML == 'View Playlist') {
		button.innerHTML = 'Hide Playlist';
	} else {
		button.innerHTML = 'View Playlist';
	}
}