function handleToggleClick(url) {
	console.log(url);

	var data = {};
	data["location"] = url;

	var js = JSON.stringify(data);
	console.log("JS:" + js);
	var xhr = new XMLHttpRequest();
	xhr.open("POST", mark_segment_url, true);

	xhr.send(js);

	xhr.onloadend = function () {
		console.log(xhr);
		console.log(xhr.request);
		if (xhr.readyState == XMLHttpRequest.DONE) {
			if (xhr.status == 200) {
				console.log("XHR: " + xhr.responseText);
				processFlipMarkResponse(xhr.responseText);
				//processAppendResponse(xhr.repsonseText);
				//refreshPlaylistSegmentsList();
			} else {
				console.log("actual:" + xhr.responseText);
				var js = JSON.parse(xhr.responseText);
				var err = js["response"];
				alert(err);
			}
		}
	};
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


function processFlipMarkResponse(response)
{
	console.log("flipped");
	var list = document.getElementById('segmentList');
	list.innerHTML = "";
	refreshList();
}