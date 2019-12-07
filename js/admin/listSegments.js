

function listVideoSegments() {
	var xhr = new XMLHttpRequest();
	
	xhr.open("GET", segment_url, true);
	
	// send the collected data as JSON
	xhr.send("");
	
	// This will process results and update HTML as appropriate. 
	xhr.onloadend = function () {
		console.log(xhr);
		console.log(xhr.request);
		if (xhr.readyState == XMLHttpRequest.DONE) {
			processListVideoSegmentsResponse(xhr.responseText);
		} else {
			processListVideoSegmentsResponse("N/A");
		}
	};
}

function processListVideoSegmentsResponse(txt) {
	console.log("result: " + txt)
	var json = JSON.parse(txt)
	var list = document.getElementById('segmentList')
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
	for(segment in json.videoSegments) {
		segment = json.videoSegments[segment];
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
		li.appendChild(createMarkToggleButton(segment))
		li.appendChild(createDeleteButton(segment))
		list.appendChild(li);
	}
}


function createMarkToggleButton(segment) {
	var button = document.createElement('button');
	if(segment.isMarked) {
		button.innerHTML = "Unmark Segment";
	} else {
		button.innerHTML = "Mark Segment";
	}
	button.onclick = function (){handleToggleClick(segment.location)};
	return button;
}

