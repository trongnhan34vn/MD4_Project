window.addEventListener("load", function () {
    const slider = document.querySelector('.slider');
    const sliderMain = document.querySelector('.slider-main');
    const nextBtn = document.querySelector('.slider-next');
    const prevBtn = document.querySelector('.slider-prev');
    const sliderItems = document.querySelectorAll('.slider-item');
    const sliderItemWidth = window.innerWidth;
    const slideLength = sliderItems.length;
    let positionX = 0;
    let index = 0;
    nextBtn.addEventListener('click', function () {
        handleChange(1)
    });

    prevBtn.addEventListener('click', function () {
        handleChange(-1)
    });

    function autoSlide() {
        setInterval(function () {
            index++;
            // console.log(index)
            if (index > slideLength - 1) {
                index = -1;
                positionX = 0;
            } else {
                positionX = positionX - sliderItemWidth;
            }
            sliderMain.style = `transform: translateX(${positionX}px)`;
        }, 5000)
    }

    autoSlide();

    // autoSlide()

    function handleChange(direction) {
        if (direction == 1) {
            // console.log(index)
            // console.log(slideLength)
            if (index >= slideLength - 1) {
                index = -1;
                positionX = 0;
            } else {
                positionX = positionX - sliderItemWidth;
            }
            // console.log(positionX)
            sliderMain.style = `transform: translateX(${positionX}px)`;
            index++;
        } else if (direction == -1) {
            // console.log(index);
            if (index <= 0) {
                index = slideLength;
                positionX = -2840;
            } else {
                positionX = positionX + sliderItemWidth;
            }
            sliderMain.style = `transform: translateX(${positionX}px)`;
            index--;
        }
    }
})