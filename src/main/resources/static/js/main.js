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

	var categories = ['all', 'Landscape', 'Portrait', 'Animal', 'Other'	];
	//collapased, expanding, expanded,collapsing,
	var state = 'collapased';
	var gap = 110;
	var expandQueue = [];
	var collapseQueue = [];

	var initCategories = function() {

		//页面首次加载时调用，生成分类列表
		categories.map(function(cur, idx) {
			var currentCategory = 'all';
			var css = cur == currentCategory ? 'currentCategory' : 'otherCategory';
			//var top = cur == currentCategory ? 20 : 20 + gap*(idx - 1);
			var top = 20;
			$('#categoryContainer').append(
				`<div class="category btn-circle ${css}" style="z-index: ${100 - idx}; top: ${top}px;"><a href="#"><span></span><em>${cur}</em></a></div>`
			);
		});
		$('.otherCategory').css('opacity', 0);

		//初始化动画队列
		var top = 20;
		var oterhCategoryLength = $('.otherCategory').length;
		var elements = $('.otherCategory').toArray();
		var length  = elements.length;
		for(let i=0; i<length; i++)
		{
			expandQueue.push(function(){
				$(elements.slice(i)).delay(400).animate(
					{
						top: top + gap*(i + 1),
						opacity: 1
					},
					{
						duration: 10
					}
				).promise().done(function(){
					if(i == oterhCategoryLength - 1 )
						state = 'expanded';
					$(document).dequeue("filter");
				});
			});
		}

		for(let i = elements.length - 1; i >= 0; i--){
			collapseQueue.push(function(){
				$(elements.slice(i)).delay(300).animate(
					{
						top: top + gap*i,
						opacity: 0
					},
					{
						duration: 10,
						easing: 'linear'
					}
				).promise().done(function(){
					if(i == 0)
						state = 'collapased';
					$(document).dequeue("filter");
				});
			});
		}

		//注册点击处理函数
		$('#categoryContainer .category a').click(function(event) {
			event.preventDefault();
			if (state == 'expanding' || state == 'collapsing')
				return;
			if (state == 'collapased') { //收起状态
				state = 'expanding';
				$(document).queue("filter", expandQueue); //展开分类
				$(document).dequeue("filter");
				// expandCategoryItem(0, $('.otherCategory').toArray());
				return;
			}
			if (state == 'expanded') {//打开状态
				state = 'collapsing';

				var selectedCategory = $(this).children('span').text();
				var currentCategory = $('.currentCategory a span').text();
				var elements = $('.otherCategory').toArray();
				$('.currentCategory a span').text(selectedCategory);
				$('.currentCategory a em').text(selectedCategory);
				$(this).children('span').text(currentCategory);
				$(this).children('em').text(currentCategory);

				$(document).queue("filter", collapseQueue); //收起分类
				$(document).dequeue("filter");
				// collapase(elements.length - 1, elements); //收起分类
				changePhoto(selectedCategory);
				return;
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

	function getPhotoDiv(category, imageSrc, name, description) {
		return `
		<div class='grid-photo grid-item item animate-box fadeIn animated-fast ${category}' data-animate-effect='fadeIn' data-photo='${category}'>
					    <a href='${imageSrc}' class='image-popup' title='${name}'>
					        <div class='img-wrap'>
					            <img src='${imageSrc}' alt='' class='img-responsive'>
					        </div>
									<div class='text-wrap'>
											<div class='text-inner popup'>
													<div>
															<h2>${name}</h2>
															<span>${description}</span>
													</div>
											</div>
									</div>
							</a>
						</div>
		`
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

	let currentPageIdx = -1;
	let hasMore = true;
	function fetchPage(pageIdx) {
		$.ajax({
      method: 'GET',
      url: `http://localhost:8080/api/photos?page=${pageIdx}`,
    }).done(function(json){

			let contents = json['content'];
			for(let i=0; i< contents.length; i++) {
				let content = contents[i];
				let divStr = getPhotoDiv(content.category, content.url, content.name, content.description);
				$('.grid').append(divStr);
			}
			currentPageIdx += 1;
			hasMore = !json.last;
			isFetching = false;
			magnifPopup();
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
