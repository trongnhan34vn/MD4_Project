
var modal = document.getElementById("modal");

// When the page is loaded, show the modal
window.onload = function() {
    modal.style.display = "block";
    document.getElementById('overlay').classList.add('is-visible');
    document.getElementById('modal').classList.add('is-visible');
}

// Get the close button
var closeBtnn = document.getElementById("close-btn");

// When the user clicks on <span> (x), close the modal
closeBtnn.onclick = function() {
    modal.style.display = "none";
    document.getElementById('overlay').classList.remove('is-visible');
    document.getElementById('modal').classList.remove('is-visible');
}
var okBtn = document.getElementById("okBtn")
// When the user clicks on the close button, close the modal
okBtn.onclick = function() {
    modal.style.display = "none";
    document.getElementById('overlay').classList.remove('is-visible');
    document.getElementById('modal').classList.remove('is-visible');
}
var overlay = document.getElementById("overlay");
// When the user clicks anywhere outside of the modal, close it
overlay.onclick = function(event) {
    if (event.target == modal) {
        modal.style.display = "none";
        document.getElementById('overlay').classList.remove('is-visible');
        document.getElementById('modal').classList.remove('is-visible');
    }
}


  