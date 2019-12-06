

function listRemoteSites() {
	var xhr = new XMLHttpRequest();
	
	xhr.open("GET", remote_site_url, true);
	
	// send the collected data as JSON
	xhr.send("");
	
	// This will process results and update HTML as appropriate. 
	xhr.onloadend = function () {
		console.log(xhr);
		console.log(xhr.request);
		if (xhr.readyState == XMLHttpRequest.DONE) {
			processListRemoteSitesResponse(xhr.responseText);
		} else {
			processListRemoteSitesResponse("N/A");
		}
	};
}

function processListRemoteSitesResponse(txt) {
	console.log("result: " + txt)
	var json = JSON.parse(txt)
	var list = document.getElementById('remoteSiteList')
	// if error show error
	if(json.statusCode != 200) {
		var errorElement = document.createElement('p');
		errorElement.innerHTML = "Error " + json.statusCode +  ": " + json.error;
		list.appendChild(errorElement);
		return;
	}
	// create list of elements
	generateRemoteSiteList(json, list)
	
}

function generateRemoteSiteList(json, list) {
	for(site in json.list) {
		site = json.list[site];
		var li = document.createElement("li");
		var title = document.createElement("P");
		title.innerHTML = "Url: " + site;
		li.appendChild(title);
		li.appendChild(createUnregisterButton(site));
		list.appendChild(li);
	}
}

function createUnregisterButton(url) {
	var button = document.createElement('button');
	button.innerHTML = "Delete " + url;
	button.onclick = function ()
	{
		handleUnregisterUrl(url)
	};
	return button;
}

function handleUnregisterUrl()
{
	alert("Unregister URL Called");
}



