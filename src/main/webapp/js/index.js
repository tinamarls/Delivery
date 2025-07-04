let previewContainer = document.querySelector('.food-preview-container');
let previewBox = previewContainer.querySelectorAll('.food-preview');

document.querySelectorAll('.meals .meal .op').forEach(op =>{
    op.onclick = () =>{
        previewContainer.style.display = 'flex';
        let name = op.getAttribute('data-name');
        previewBox.forEach(preveiw =>{
            let target = preveiw.getAttribute('data-target');
            if(name === target){
                preveiw.classList.add('active');
            }
        });
    };
});

previewContainer.querySelector('#close-preview').onclick = () =>{
    previewContainer.style.display = 'none';
    previewBox.forEach(close =>{
        close.classList.remove('active');
    });
};




var slideIndex = 1;
showSlides(slideIndex);

// Next/previous controls
function plusSlides(n) {
    showSlides(slideIndex += n);
}

// Thumbnail image controls
function currentSlide(n) {
    showSlides(slideIndex = n);
}

function showSlides(n) {
    var i;
    var slides = document.getElementsByClassName("swiper-slide");
    if (n > slides.length) {
        slideIndex = 1
    }
    if (n < 1) {
        slideIndex = slides.length
    }
    for (i = 0; i < slides.length; i++) {
        slides[i].style.display = "none";
    }
    slides[slideIndex - 1].style.display = "block";
}

