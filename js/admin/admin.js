
function onLoad() {
	listVideoSegments();
	listRemoteSites();
}

window.onload = onLoad;

function toggleSegments() {
	var div = document.getElementById("allSegments")
	div.hidden = !div.hidden;
	var button =  document.getElementById("segmentToggle")
	if(button.innerHTML == "Show Segments") {
		button.innerHTML = "Hide Segments"
	} else {
		button.innerHTML = "Show Segments"
	}
}

function handleRegisterURL()
{
	
}

function showRemoteSites()
{
	var div = document.getElementById("allRemoteSites")
	div.hidden = !div.hidden;
	var button =  document.getElementById("siteToggle")
	if(button.innerHTML == "Show Remote Sites") {
		button.innerHTML = "Hide Remote Sites"
	} else {
		button.innerHTML = "Show Remote Sites"
	}
}