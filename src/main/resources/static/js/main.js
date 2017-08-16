;(function () {

	'use strict';



	var isMobile = {
		Android: function() {
			return navigator.userAgent.match(/Android/i);
		},
			BlackBerry: function() {
			return navigator.userAgent.match(/BlackBerry/i);
		},
			iOS: function() {
			return navigator.userAgent.match(/iPhone|iPad|iPod/i);
		},
			Opera: function() {
			return navigator.userAgent.match(/Opera Mini/i);
		},
			Windows: function() {
			return navigator.userAgent.match(/IEMobile/i);
		},
			any: function() {
			return (isMobile.Android() || isMobile.BlackBerry() || isMobile.iOS() || isMobile.Opera() || isMobile.Windows());
		}
	};


	var contentWayPoint = function() {
		var i = 0;
		$('.animate-box').waypoint( function( direction ) {

			if( direction === 'down' && !$(this.element).hasClass('animated-fast') ) {

				i++;

				$(this.element).addClass('item-animate');
				setTimeout(function(){

					$('body .animate-box.item-animate').each(function(k){
						var el = $(this);
						setTimeout( function () {
							var effect = el.data('animate-effect');
							if ( effect === 'fadeIn') {
								el.addClass('fadeIn animated-fast');
							} else if ( effect === 'fadeInLeft') {
								el.addClass('fadeInLeft animated-fast');
							} else if ( effect === 'fadeInRight') {
								el.addClass('fadeInRight animated-fast');
							} else {
								el.addClass('fadeInUp animated-fast');
							}

							el.removeClass('item-animate');
						},  k * 50, 'easeInOutExpo' );
					});

				}, 50);

			}

		} , { offset: '90%' } );
	};


	var toggleAside = function() {

		$('.aside-toggle').click(function(event){

			event.preventDefault();
			var aside = $('#fh5co-aside'),
				 grid = $('#fh5co-image-grid, .aside-toggle'),
				 bio = $('#fh5co-bio'),
				 imgBg = $('.image-bg');


			if (aside.hasClass('show')) {


				if ($(window).width() <= 480 ) {
					TweenLite.to(aside, -1, {
						left: '-85%',
						ease: Power1.easeNone
					});
				} else {
					TweenLite.to(aside, -1, {
						left: '-50%',
						ease: Power1.easeNone
					});
				}

				TweenLite.to(grid, -1, { css: {
						"-webkit-transform" : "translate3d(0%, 0px, 0px)",
						"-moz-transform" : "translate3d(0%, 0px, 0px)",
						"-ms-transform" : "translate3d(0%, 0px, 0px)",
						"-o-transform" : "translate3d(0%, 0px, 0px)",
						"transform" : "translate3d(0%, 0px, 0px)"
					},
					ease: Power1.easeNone
				});

				TweenLite.to(bio, 1, { opacity: 0, delay: 0.2, ease: Power1.easeNone});
				TweenLite.to(imgBg, 1, { opacity: 0, delay: 0.2, ease: Power1.easeNone});


				aside.removeClass('show');
			} else {

				TweenLite.to(aside, -1, {
					left: '0%',
					ease: Power1.easeNone
				});

				if ($(window).width() <= 480 ) {
					TweenLite.to(grid, -1, { css: {
							"-webkit-transform" : "translate3d(85%, 0px, 0px)",
							"-moz-transform" : "translate3d(85%, 0px, 0px)",
							"-ms-transform" : "translate3d(85%, 0px, 0px)",
							"-o-transform" : "translate3d(85%, 0px, 0px)",
							"transform" : "translate3d(85%, 0px, 0px)"
						},
						ease: Power1.easeNone
					})
				} else {
					TweenLite.to(grid, -1, { css: {
							"-webkit-transform" : "translate3d(50%, 0px, 0px)",
							"-moz-transform" : "translate3d(50%, 0px, 0px)",
							"-ms-transform" : "translate3d(50%, 0px, 0px)",
							"-o-transform" : "translate3d(50%, 0px, 0px)",
							"transform" : "translate3d(50%, 0px, 0px)"
						},
						ease: Power1.easeNone
					});
				}

				TweenLite.to(bio, 1, { opacity: 1, delay: 0.3, ease: Power1.easeNone});
				TweenLite.to(imgBg, 1, { opacity: 1, delay: 0.6, ease: Power1.easeNone});
				aside.addClass('show');
			}
		});

		$(document).click(function (e) {
	    var container = $(".aside-toggle, #fh5co-aside");
	    if (!container.is(e.target) && container.has(e.target).length === 0) {
	      if ( $('#fh5co-aside').hasClass('show') ) {
	      	container.trigger('click');
	      }
	    }
		});

	}

	var buttonsCustom = function() {
		$('.btn-circle a').each(function(){
			var $this = $(this),
				span = $this.find('> span'),
				em = $this.find('> em');

			span.text(em.text());

		})
	}

	var categories = ['All', 'Nature', 'Portrait', 'Activity', 'Other'];
	//collapased, expanding, expanded,collapsing,
	var state = 'collapased';

	var gap = 10;
	var top;
	var filterHeight;
	var categoryHeight;

	function calculateExpandTop(index) {
		var rst = top + filterHeight + gap + (categoryHeight + gap)*index ;
		console.log(`expand index:${index}  rst: ${rst} top: ${top}  filterHeight: ${filterHeight}  gap: ${gap}  categoryHeight: ${categoryHeight}` );
		return rst;
	}

	function calculateCollapaseTop(index) {
		var rst;
		if (index == 0) {
			rst = top;
		}else {
			rst = top + filterHeight + gap + (categoryHeight + gap)*index - gap - categoryHeight;
			console.log(`collapse index:${index}  rst: ${rst} top: ${top}  filterHeight: ${filterHeight}  gap: ${gap}  categoryHeight: ${categoryHeight}` );
		}
		return rst;
	}

	var expandAnimation = function() {
		var elements = $('.category_wrapper').toArray();
		var duration = 100;
		$(elements.slice(0)).animate({top: calculateExpandTop(0)},	{duration: duration}).promise().then(function(){
			console.log('expand pos1');
			return $(elements.slice(1)).animate({top: calculateExpandTop(1)},	{duration: duration}).promise();
		}).then(function(){
			console.log('expand pos2');
			return $(elements.slice(2)).animate({top: calculateExpandTop(2)},	{duration: duration}).promise();
		}).then(function(){
			console.log('expand pos3');
			return $(elements.slice(3)).animate({top: calculateExpandTop(3)},	{duration: duration}).promise();
		}).then(function(){
			console.log('expand pos4');
			return $(elements.slice(4)).animate({top: calculateExpandTop(4)},	{duration: duration}).promise();
		}).done(function(){
			state = 'expanded';
			console.log('expandAnimation completed!');
		})
	}

	var collapseAnimation = function() {
		var elements = $('.category_wrapper').toArray();
		var duration = 100;
		$(elements.slice(4)).animate({top: calculateCollapaseTop(4)},	{duration: duration}).promise().then(function(){
			console.log('collapse pos4');
			return $(elements.slice(3)).animate({top: calculateCollapaseTop(3)},	{duration: duration}).promise();
		}).then(function(){
			console.log('collapse pos3');
			return $(elements.slice(2)).animate({top: calculateCollapaseTop(2)},	{duration: duration}).promise();
		}).then(function(){
			console.log('collapse pos2');
			return $(elements.slice(1)).animate({top: calculateCollapaseTop(1)},	{duration: duration}).promise();
		}).then(function(){
			console.log('collapse pos1');
			return $(elements.slice(0)).animate({top: calculateCollapaseTop(0)},	{duration: duration}).promise();
		}).done(function(){
			state = 'collapased';
			console.log('collapseAnimation completed!');
		})

	}


	var initCategories = function() {

		//页面首次加载时调用，生成分类列表
		categories.map(function(cur, idx) {
			var currentCategory = 'All';
			var css = cur == currentCategory ? 'currentCategory' : 'otherCategory';
			$('#categoryContainer').append(
				"<div class='category_wrapper' style='z-index:" + (99 - idx) +" ; top: 20px;'><div class='category btn-circle " + css + "'><a href='#'><span></span><em>" + cur + "</em></a></div></div>"
			);
		});

		top = parseInt($('.filter').css('top'));
		filterHeight = parseInt($('.filter a').css('height'));
		categoryHeight = parseInt($('.category a').first().css('height'));
		if(isMobile.any()){ //移动端gap
			gap = 5;
		}
		// $('.otherCategory').css('opacity', 0);

		//注册点击处理函数
		$('#categoryContainer .filter a').click(function(event) {
			event.preventDefault();
			if (state == 'expanding' || state == 'collapsing')
				return;
			if (state == 'collapased') { //收起状态
				state = 'expanding';
				expandAnimation();
				return;
			}
			if (state == 'expanded') {//打开状态
				state = 'collapsing';
				collapseAnimation();
				return;
			}
		})

		$('#categoryContainer .category a').click(function(event) {
			switch(state){
				case 'expanding':
				case 'collapsing':
						return;
				case 'collapased'://这种情况不应该发生
						console.log('should not happen');
						return;
				case 'expanded':
						state = 'collapsing';
						var parent = $(this).parent();
						if(parent.hasClass('currentCategory')) {//直接收起
							collapseAnimation();
						}
						else {//先增删class，再收起
							$('.currentCategory').removeClass('currentCategory').addClass('otherCategory');
							$(this).parent().removeClass('otherCategory').addClass('currentCategory');
							collapseAnimation();
							changePhoto($('.currentCategory a span').text());
						}
						break;
			}
		})
	}

	function sleep (time) {
	  return new Promise((resolve) => setTimeout(resolve, time));
	}

	function changePhoto(status) {
			$("#Menu").click();
			$('body .grid-photo').each(function (k) {
					var el = $(this);
					var effect = el.data('photo');
					if (effect == status) {
							el.removeClass('fadeIn animated-fast');
					} else {
							el.removeClass('fadeIn animated-fast');
					}
			});
			setTimeout(function () {
					if (status == "all") {
							$grid.isotope({filter: "*"});
					} else {
							$grid.isotope({filter: "." + status});
					}
					setTimeout(function () {
							$('body .grid-photo').each(function (k) {
									var el = $(this);
									var effect = el.data('photo');
									if (effect == status || status == "all") {
											setTimeout(function () {
													el.addClass('fadeIn animated-fast');
											}, k * 50, 'easeInOutExpo');
									} else {

									}
							});
					}, 50);
			}, 50);
	}

	function getPhotoDiv(category, smallUrl, largeUrl, name, description) {
		return "\
		<div class='grid-photo grid-item item animate-box fadeIn animated-fast " + category + "' data-animate-effect='fadeIn' data-photo='" + category + "'>\
					    <a href='" + largeUrl + "' class='image-popup' title='" + name + "'>\
					        <div class='img-wrap'>\
					            <img src='" + smallUrl + "' alt='' class='img-responsive'>\
					        </div>\
									<div class='text-wrap'>\
											<div class='text-inner popup'>\
													<div>\
															<h2>" + name + "</h2>\
															<span>" + description + "</span>\
													</div>\
											</div>\
									</div>\
							</a>\
						</div>\
		"
		// return `
		// <div class='grid-photo grid-item item animate-box fadeIn animated-fast ${category}' data-animate-effect='fadeIn' data-photo='${category}'>
		// 			    <a href='${largeUrl}' class='image-popup' title='${name}'>
		// 			        <div class='img-wrap'>
		// 			            <img src='${smallUrl}' alt='' class='img-responsive'>
		// 			        </div>
		// 							<div class='text-wrap'>
		// 									<div class='text-inner popup'>
		// 											<div>
		// 													<h2>${name}</h2>
		// 													<span>${description}</span>
		// 											</div>
		// 									</div>
		// 							</div>
		// 					</a>
		// 				</div>
		// `
	}


	  // MagnificPopup
		var magnifPopup = function() {
			$('.image-popup').magnificPopup({
				type: 'image',
				removalDelay: 300,
				mainClass: 'mfp-with-zoom',
				gallery:{
					enabled:true
				},
				zoom: {
					enabled: true, // By default it's false, so don't forget to enable it

					duration: 300, // duration of the effect, in milliseconds
					easing: 'ease-in-out', // CSS transition easing function

					// The "opener" function should return the element from which popup will be zoomed in
					// and to which popup will be scaled down
					// By defailt it looks for an image tag:
					opener: function(openerElement) {
					// openerElement is the element on which popup was initialized, in this case its <a> tag
					// you don't need to add "opener" option if this code matches your needs, it's defailt one.
					return openerElement.is('img') ? openerElement : openerElement.find('img');
					}
				}
			});
		};

		var magnifVideo = function() {
			$('.popup-youtube, .popup-vimeo, .popup-gmaps').magnificPopup({
	        disableOn: 700,
	        type: 'iframe',
	        mainClass: 'mfp-fade',
	        removalDelay: 160,
	        preloader: false,

	        fixedContentPos: false
	    });
		};

	var currentPageIdx = -1;
	var hasMore = true;
	var pageSize = 12; //pc端pageSize

	function fetchPage(pageIdx) {
		$.ajax({
      method: 'GET',
      url: "api/photos?page=" + pageIdx + "&size=" + pageSize + "",
    }).done(function(json){
			var contents = json['content'];
			for(var i=0; i< contents.length; i++) {
				var content = contents[i];
				var divStr = getPhotoDiv(content.category, content.url_index, content.url, content.name, content.description);
				$('.grid').append(divStr);
			}
			currentPageIdx += 1;
			hasMore = !json.last;
			isFetching = false;
			magnifPopup();//对新增的元素设置popup
			changePhoto($('.currentCategory a span').text());//过滤新增的元素
			// $('.grid a').magnificPopup({
	    //   type: 'image',
	    //   gallery: {
	    //     enable: true,
	    //     navigateByImgClick: true,
			// 		preload: [0, 1]
	    //   }
	    // }
	    // );
    });
	}

  var isFetching = false;
	function initWaypoint() {
		if(isMobile.any()){ //移动端pageSize
			pageSize = 12;
		}
		$('.bottom').waypoint({
			handler: function(direction) {
				console.log('bottom reached!');
				if(hasMore && !isFetching) {
					isFetching = true;
					fetchPage(currentPageIdx + 1);
				}
			},
			offset: 'bottom-in-view'
		})
	}

	$(function(){
		contentWayPoint();
		//isotopeImageLoaded();
		toggleAside();
		initCategories();
		buttonsCustom();
		initWaypoint();
		//请求照片
		// fetchPage(0);
	});


}());
