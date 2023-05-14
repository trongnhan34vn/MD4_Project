
// Nav index
let header = document.getElementById('header');
let navbar = document.getElementById('navbar');
let navPos = navbar.getBoundingClientRect().top;
window.addEventListener("scroll", function (e) {
    let scrollPos = window.scrollY;
    if(scrollPos > navPos) {
        navbar.classList.add('sticky');
        header.classList.add("navbarOffsetMargin");
    } else {
        navbar.classList.remove('sticky');
        header.classList.remove("navbarOffsetMargin");
    }
})