
function handleSearchForSegment() {
	var character = document.getElementById("characterSearch").value;
	var phrase = document.getElementById("phraseSearch").value;
	
	
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
	list.innerHTML = '';
	var resultLabel = document.createElement('h4');
	resultLabel.innerHTML = "Results";
	list.appendChild(resultLabel)
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
		list.appendChild(li);
	}
}

