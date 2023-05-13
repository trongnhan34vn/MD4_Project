let checkCart = document.getElementById('check-cart');
let checkPos = checkCart.getBoundingClientRect().height;
window.addEventListener("scroll", function (e) {
    let scrollPos = window.scrollY;
    console.log(scrollPos, checkPos);
    if(scrollPos >= checkPos*2.4 ) {
        checkCart.style.transform = "translateY(100px)";
    } else {
        checkCart.style.transform = "translateY(0)";
        checkCart.style.transition = "transform 0.3s"
    }
})

