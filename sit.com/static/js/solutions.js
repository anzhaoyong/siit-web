$(function() {
    new Swiper(".swiper-container", {

        //默认功能属性
        speed: 250,
        slidesPerView: 3,
        spaceBetween: 0,
        slidesPerGroup: 3,
        loop: true,
        autoplay: true,

        //分页索引
        pagination: {
            el: ".swiper-pagination",
            clickable: true
        },

        //分页按钮
        navigation: {
            nextEl: ".swiper-button-next",
            prevEl: ".swiper-button-prev"
        }

    });
})