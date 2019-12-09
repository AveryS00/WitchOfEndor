

function listRemoteVideoSegments() {
	getRemoteSiteSegments();
}

function getRemoteSiteSegments() {

	var xhr = new XMLHttpRequest();
	
	xhr.open("GET", remote_site_url, true);
	
	// send the collected data as JSON
	xhr.send("");
	
	// This will process results and update HTML as appropriate. 
	xhr.onloadend = function () {
		console.log(xhr);
		console.log(xhr.request);
		if (xhr.readyState == XMLHttpRequest.DONE) {
			proccessGetRemoteSitesResponse(xhr.responseText);
		}
	};
}

function proccessGetRemoteSitesResponse(txt) {
	json = JSON.parse(txt);
	console.log("Got " + json.list.length + " remote sites");
	for(i in json.list) {
		site = json.list[i];
		listRemoteSiteSegments(site);
	}
}

function listRemoteSiteSegments(url) {
	console.log("Getting segments from site: " + url)
	var xhr = new XMLHttpRequest();
	
	xhr.open("GET", url, true);
	
	// send the collected data as JSON
	xhr.send("");
	
	// This will process results and update HTML as appropriate. 
	xhr.onloadend = function () {
		console.log(xhr);
		console.log(xhr.request);
		if (xhr.readyState == XMLHttpRequest.DONE) {
			processListRemoteVideoSegmentsResponse(xhr.responseText);
		}
	};
}

function processListRemoteVideoSegmentsResponse(txt) {
	console.log("result: " + txt)
	var json = JSON.parse(txt)
	var list = document.getElementById('remoteSegmentList')
	// if error show error
	if(json.statusCode != 200) {
		var errorElement = document.createElement('p');
		errorElement.innerHTML = "Error " + json.statusCode +  ": " + json.error;
		list.appendChild(errorElement);
		return;
	}
	// create list of elements
	for(segment in json.segments) {
		segment = json.segments[segment];
		var li = document.createElement('li');
		var title = document.createElement('p');
		title.innerHTML = "Sentence: " + segment['text'];
		var character = document.createElement('p')
		character.innerHTML = "Character: " + segment['character'];
		var video = document.createElement('video');
		var source = document.createElement('source');
		source.type = 'video/ogg';
		source.src = segment['url'];
		video.width = 320;
		video.height = 240;
		video.controls = true;
		video.appendChild(source);
		li.appendChild(title);
		li.appendChild(character);
		li.appendChild(video);
		li.appendChild(createAddToPlaylistButton(segment, list));
		li.appendChild(createDeleteButton(segment));
		list.appendChild(li);
	}	
	
}

function refreshList() {
	var xhr = new XMLHttpRequest();
	xhr.open("GET", segment_url, true);
	xhr.send();

	console.log("sent");

	// This will process results and update HTML as appropriate. 
	xhr.onloadend = function () {
		if (xhr.readyState == XMLHttpRequest.DONE) {
			console.log ("XHR:" + xhr.responseText);
			processListVideoSegmentsResponse(xhr.responseText);
		} else {
			processListVideoSegmentsResponse("N/A");
		}
	};
}
