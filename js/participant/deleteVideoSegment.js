/**
 * 
 */

function createDeleteButton(segment)
{
	var button = document.createElement('button');
	button.innerHTML = "Delete this Video Segment";
	button.onclick = function ()
	{
		handleDeleteClick(segment.location)
	};
	return button;
}

function handleDeleteClick(url)
{
	console.log(url);

	var data = {};
	data["location"] = url;

	var js = JSON.stringify(data);
	console.log("JS:" + js);
	var xhr = new XMLHttpRequest();
	xhr.open("POST", delete_segment_url, true);

	xhr.send(js);

	xhr.onloadend = function () {
		console.log(xhr);
		console.log(xhr.request);
		if (xhr.readyState == XMLHttpRequest.DONE) {
			if (xhr.status == 200) {
				console.log("XHR: " + xhr.responseText);
				processDeleteVideoResponse(xhr.responseText);
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

function processDeleteVideoResponse(response)
{
	console.log("deleted :" + response);
	var list = document.getElementById('segmentList');
	list.innerHTML = "";
	refreshList();
}
