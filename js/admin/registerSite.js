
function handleRegisterURL() {
	var url = document.getElementById("registerURLInput").value;
	alert("Registering Site: " + url)
	
	var xhr = new XMLHttpRequest();
	xhr.open("POST", remote_site_url, true);
	
	json = createRegisterUrlRequest(url)
	console.log("JSON to send: " + json)
	
	xhr.send(json);
	
	 xhr.onloadend = function () {
		 console.log(xhr);
		 console.log(xhr.request);

		 if (xhr.readyState == XMLHttpRequest.DONE) {
			 console.log ("XHR:" + xhr.responseText);
			 handleRegisterURLResponse(xhr.responseText)
		 } else {
			 alert("error");
		 }
	 };
	
}

function handleRegisterURLResponse(txt) {
	var json = JSON.parse(txt)
	if(json.statusCode != 200) {
		alert("Error " + json.statusCode + ": " + json.error)
	}
	location.reload();
}

function createRegisterUrlRequest(url) {
	var data = {}
	data["url"] = url
	return JSON.stringify(data);
}