$(function () {

  // JQUERY MASKING
  $(".date").mask("00/00/0000");
  $(".time").mask("00:00");

  $(".form-find-car .nav-tabs").on("click", ".nav-item", function () {
    $(this).addClass("active").siblings().removeClass("active");
    var index = $(this).index();
    console.log(index);
    if (index == 0) {
      $(".search-form").addClass("border-search");
      $(".search-form").removeClass("border-filter");
      $(".search-form").removeClass("border-location");
    }
    if (index == 1) {
      $(".search-form").removeClass("border-search");
      $(".search-form").addClass("border-filter");
      $(".search-form").removeClass("border-location");
    }
    if (index == 2) {
      $(".search-form").removeClass("border-search");
      $(".search-form").removeClass("border-filter");
      $(".search-form").addClass("border-location");
    }
  });

  $(".btn-group").on("click", ".btn", function () {
    $(this).addClass("active").siblings().removeClass("active");
  });

  // SIDEBAR JS
  $(".navbar-toggler").click(function () {
    $(".sidebar-overlay").toggleClass("show");
  });
  $(".sidebar-overlay").click(function () {
    $(this).removeClass("show");
    $(".navbar-collapse").removeClass("show");
    $(".navbar-toggler").addClass("collapsed");
  });

  $("#mostPopularOwl").owlCarousel({
    loop: true,
    nav: true,
    width: 100,
    itemsDesktop: false,
    itemsMobile: true,
    dots: true,
    mouseDrag: false,
    responsiveClass: true,
    navText: [
      "<i class='fa fa-chevron-left'></i>",
      "<i class='fa fa-chevron-right'></i>",
    ],
    responsive: {
      0: {
        items: 1,
        mouseDrag: true,
      },
      600: {
        items: 1,
        mouseDrag: true,
      },
      1000: {
        items: 2,
        mouseDrag: true,
      },
      1280: {
        items: 3,
        nav: false,
        dots: false,
        loop: false,
      },
    },
  });

  $("#priceOfCar").ionRangeSlider({
    skin: "round ",
    type: "double ",
    //grid: false,
    min: 0,
    max: 1000,
    from: $('#minAmount').val(),
    to: $('#maxAmount').val(),
    prettify_enabled: true,
    prettify_separator: ", ",
    force_edges: true,
    hide_min_max: true,
    hide_from_to: true,
    keyboard: true,
    onStart: calc,
    onChange: calc,
    onFinish: calcAndCall,
    onUpdate: calc,
  });

  $("body .search-detail").mCustomScrollbar({
    autoHideScrollbar: true,
    theme: "rounded",
    mouseWheelPixels: 1000,
  });

});

function calc(data) {

  $("#priceFrom").html(data.from.toFixed(0));
  $("#priceTo").html(data.to.toFixed(0));

  $('#minAmount').val(data.from.toFixed(0));
  $('#maxAmount').val(data.to.toFixed(0));

  console.log(data.from);
}

function calcAndCall(data) {

  $("#priceFrom").html(data.from.toFixed(0));
  $("#priceTo").html(data.to.toFixed(0));

  $('#minAmount').val(data.from.toFixed(0));
  $('#maxAmount').val(data.to.toFixed(0));

  console.log(data.from);

  callAngularFunction();
}
