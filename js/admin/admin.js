
function onLoad() {
	listVideoSegments();
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