function handleUploadVideoSegment() {
	var form = document.uploadSegmentForm;
	
	var data = {};
	data["character"] = form.characterName.value;
	data["text"] = form.text.value;
	
	var segments = form.base64Encoding.value.split(',');
	data["base64Encoded"] = segments[1];
	
	var js = JSON.stringify(data);
	console.log("JS:" + js);
	var xhr = new XMLHttpRequest();
	xhr.open("POST", segment_url, true);
	
	
	xhr.send(js);
	
	xhr.onloadend = function () {
		console.log(xhr);
		console.log(xhr.request);
		if (xhr.readyState == XMLHttpRequest.DONE) {
			if (xhr.status == 200) {
				console.log("XHR:" + xhr.responseText);
				listVideoSegments();
			} else {
				console.log("actual:" + xhr.responseText);
				var js = JSON.parse(xhr.responseText);
				var err = js["response"];
				alert(err);
			}
		}
	};
}

function getBase64(file) {
	var reader = new FileReader();
	reader.readAsDataURL(file);
	
	reader.onload = function () {
		document.uploadSegmentForm.base64Encoding.value = reader.result;
		document.uploadSegmentForm.uploadButton.disabled = false;
	}
}

function handleFileSelect(evt) {
	var files = evt.target.files;
	if (files[0].size > 700000) {
		document.createForm.base64Encoding.value = "";
		alert("File size too large to use" + files[0].size + " bytes");
	} else {
		getBase64(files[0]);
	}
}
