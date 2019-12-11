
function handleSearchForSegment() {
	var character = document.getElementById("characterSearch").value;
	var phrase = document.getElementById("phraseSearch").value;
	
	document.getElementById('searchList').innerHTML = '';
	
	getRemoteSiteSegmentsToSearch(character, phrase);
	
	var xhr = new XMLHttpRequest();
	
	xhr.open("POST", search_url, true);
	
	data = {}
	data['character'] = character
	data['phrase'] = phrase
	
	json = JSON.stringify(data)
	console.log("Searching for: " + json)
	
	// send the collected data as JSON
	xhr.send(json);
	
	// This will process results and update HTML as appropriate. 
	xhr.onloadend = function () {
		console.log(xhr);
		console.log(xhr.request);
		if (xhr.readyState == XMLHttpRequest.DONE) {
			processSearchResponse(xhr.responseText);
		} else {
			processSearchResponse("N/A");
		}
	};
}

function processSearchResponse(txt) {
	console.log("result: " + txt)
	var json = JSON.parse(txt)
	var list = document.getElementById('searchList')
	var resultLabel = document.createElement('h4');
	resultLabel.innerHTML = "Results";
	var line = document.createElement("hr");
	list.appendChild(resultLabel)
	list.appendChild(line)
	// if error show error
	if(json.statusCode != 200) {
		var errorElement = document.createElement('p');
		errorElement.innerHTML = "Error " + json.statusCode +  ": " + json.error;
		list.appendChild(errorElement);
		return;
	}
	// create list of elements
	generateVideoList(json, list)
	
}


function generateVideoList(json, list) {
	for(segment in json.segments) {
		segment = json.segments[segment];
		if(!segment.isLocal) {
			continue;
		}
		var li = document.createElement('li');
		var title = document.createElement('p');
		title.innerHTML = "Sentence: " + segment['name'];
		var character = document.createElement('p')
		character.innerHTML = "Character: " + segment['character'];
		var video = document.createElement('video');
		var source = document.createElement('source');
		source.type = 'video/ogg';
		source.src = segment['location'];
		video.width = 320;
		video.height = 240;
		video.controls = true;
		video.appendChild(source);
		li.appendChild(title);
		li.appendChild(character);
		li.appendChild(video);
		li.appendChild(createAddToPlaylistButton(segment, li));
		list.appendChild(li);
	}
}


function getRemoteSiteSegmentsToSearch(character, phrase) {

	var xhr = new XMLHttpRequest();
	
	xhr.open("GET", remote_site_url, true);
	
	// send the collected data as JSON
	xhr.send("");
	
	// This will process results and update HTML as appropriate. 
	xhr.onloadend = function () {
		console.log(xhr);
		console.log(xhr.request);
		if (xhr.readyState == XMLHttpRequest.DONE) {
			proccessSearchRemoteSitesResponse(xhr.responseText, character, phrase);
		} else {
			proccessSearchRemoteSitesResponse("N/A", "..", "..");
		}
	};
}

function proccessSearchRemoteSitesResponse(txt, character, phrase) {
	json = JSON.parse(txt);
	console.log("Got " + json.list.length + " remote sites to search");
	for(i in json.list) {
		site = json.list[i];
		searchRemoteSite(site, character, phrase);
	}
}

function searchRemoteSite(raw_url, character, phrase) {
	var parts = raw_url.split("?apikey=");
	
	var url = parts[0]
	var key = parts[1]
	console.log("Searching site: " + url + " with key: " + key)
	
	var xhr = new XMLHttpRequest();
	
	xhr.open("GET", url, true);
	
	xhr.setRequestHeader('x-api-key', key)
	
	xhr.send();
	
	// This will process results and update HTML as appropriate. 
	xhr.onloadend = function () {
		console.log(xhr);
		console.log(xhr.request);
		if (xhr.readyState == XMLHttpRequest.DONE) {
			proccessRemoteSearchResponse(xhr.responseText, character, phrase);
		} else {
			proccessRemoteSearchResponse("N/A");
		}
	};
}

function proccessRemoteSearchResponse(txt, character, phrase) {
	var json = JSON.parse(txt);
	var list = []
	for(i in json.segments) {
		segment = json.segments[i];
		if(character && segment.character.toLowerCase().includes(character.toLowerCase())) {
			console.log("Segment " + segment.text + " matches character");
			list.push(segment)
		} else if(phrase && segment.text.toLowerCase().includes(phrase.toLowerCase())) {
			console.log("Segment " + segment.text + " matches text");
			list.push(segment)
		}
	}
	generateRemoteVideoList(list);
}

function generateRemoteVideoList(segments) {
	var list = document.getElementById('searchList')
	for(segment in segments) {
		segment = segments[segment];
		var li = document.createElement('li');
		var remote = document.createElement('h4');
		remote.innerHTML = "Remote Segment";
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
		li.appendChild(remote);
		li.appendChild(title);
		li.appendChild(character);
		li.appendChild(video);
		li.appendChild(createAddToPlaylistButton(segment, li));
		list.appendChild(li);
	}
}
