function handleViewPlaylist(segmentsList, list) {
	for (segment in segmentsList) {
		var video = document.createElement('video');
		var source = document.createElement('source');
		source.type = 'video/ogg';
		source.src = segment;
		video.width = 320;
		video.height = 240;
		video.controls = true;
		video.appendChild(source);
		list.appendChild(video);
	}
}

function toggleViewButton(button) {
	button.disabled = true;
}