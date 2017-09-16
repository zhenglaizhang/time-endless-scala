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
		return rst;
	}

	function calculateCollapaseTop(index) {
		var rst;
		if (index == 0) {
			rst = top;
		}else {
			rst = top + filterHeight + gap + (categoryHeight + gap)*index - gap - categoryHeight;
		}
		return rst;
	}

	var expandAnimation = function() {
		var elements = $('.category_wrapper').toArray();
		var duration = 100;
		$(elements.slice(0)).animate({top: calculateExpandTop(0)},	{duration: duration}).promise().then(function(){
			return $(elements.slice(1)).animate({top: calculateExpandTop(1)},	{duration: duration}).promise();
		}).then(function(){
			return $(elements.slice(2)).animate({top: calculateExpandTop(2)},	{duration: duration}).promise();
		}).then(function(){
			return $(elements.slice(3)).animate({top: calculateExpandTop(3)},	{duration: duration}).promise();
		}).then(function(){
			return $(elements.slice(4)).animate({top: calculateExpandTop(4)},	{duration: duration}).promise();
		}).done(function(){
			state = 'expanded';
		})
	}

	var collapseAnimation = function() {
		var elements = $('.category_wrapper').toArray();
		var duration = 100;
		$(elements.slice(4)).animate({top: calculateCollapaseTop(4)},	{duration: duration}).promise().then(function(){
			return $(elements.slice(3)).animate({top: calculateCollapaseTop(3)},	{duration: duration}).promise();
		}).then(function(){
			return $(elements.slice(2)).animate({top: calculateCollapaseTop(2)},	{duration: duration}).promise();
		}).then(function(){
			return $(elements.slice(1)).animate({top: calculateCollapaseTop(1)},	{duration: duration}).promise();
		}).then(function(){
			return $(elements.slice(0)).animate({top: calculateCollapaseTop(0)},	{duration: duration}).promise();
		}).done(function(){
			state = 'collapased';
		})

	}

	var initCategories = function() {
		var defaultCategory = $('#redirect').text().trim();
		if(!categories.includes(defaultCategory))
			defaultCategory = 'All';
		//页面首次加载时调用，生成分类列表
		categories.map(function(cur, idx) {
			var css = cur == defaultCategory ? 'currentCategory' : 'otherCategory';
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
			if (state == 'collapased') { //收起状��?
				state = 'expanding';
				expandAnimation();
				return;
			}
			if (state == 'expanded') {//打开状��?
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
				case 'collapased'://这种情况不应该发��?
						console.log('should not happen');
						return;
				case 'expanded':
						state = 'collapsing';
						var parent = $(this).parent();
						if(parent.hasClass('currentCategory') || imageLoaded == false) {//直接收起
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

	function changePhoto(status) {
			$('body .grid-photo').each(function (k) {
					var el = $(this);
					el.removeClass('fadeIn animated-fast');
			});
			setTimeout(function () {
					if (status == "All") {
							$grid.isotope({filter: "*"});
					} else {
							$grid.isotope({filter: "." + status});
					}
					setTimeout(function () {
							$('body .grid-photo').each(function (k) {
									var el = $(this);
									var effect = el.data('photo');
									if (effect.indexOf(status) != -1 || status == "All") {
											setTimeout(function () {
													el.addClass('fadeIn animated-fast');
													magnifPopup();
													Waypoint.refreshAll();
											}, 50, 'easeInOutExpo');
									}
							});
					}, 50);
			}, 50);
	}

	function getPhotoDiv(category, smallUrl, largeUrl, name, description) {
		var categoryValue = category.split(',').join(' ');
		return "\
		<div class='grid-photo grid-item item animate-box fadeIn animated-fast " + categoryValue + "' data-animate-effect='fadeIn' data-photo='" + category + "'>\
					    <a href='" + largeUrl + "' class='image-popup' title='" + name + "'>\
					        <div class='img-wrap'>\
					            <img src='" + smallUrl + "' alt='' class='img-responsive'>\
					        </div>\
									<div class='text-wrap'>\
	                    <div class=''>\
	                        <div>\
	                            <h2></h2>\
	                            <span></span>\
	                        </div>\
	                    </div>\
	                </div>\
							</a>\
						</div>\
		"
	}


	// function copyToClipboard(copyContent) {
	//     var $temp = $("<input>");
	//     $(".mfp-content").append($temp);
	//     $temp.val(copyContent).select();
	//     var succeed =document.execCommand("copy");
	//     $temp.remove();
	// 		return succeed;
	// }

	function setupShareBottom() {
		var $config = {
			image: $('.mfp-img').attr('src'),
			sites: ['wechat', 'weibo', 'facebook', 'twitter']
		};

		var shareBar =
		'<div class="my-social-share" data-mode="prepend">' +
			'<a href="javascript:;" class="social-share-icon icon-envelope" style="font-family: icomoon !important; color: #dc143c"></a>' +
			'<a href="javascript:;" class="social-share-icon icon-link" data-clipboard-target="#currentImgUrl" style="font-family: icomoon !important;"></a>' +
		'</div>';



		$('.mfp-bottom-bar').append(shareBar);
		$('.my-social-share').share($config);

		$('.icon-envelope').click(function(){
			var imgUrl = $(this).parent().parent().parent().find('img').attr('src');
			var subject = 'beautiful image';
			var body = imgUrl;
			var hrefValue = 'mailto:?Subject='+ subject +'&body=' + body;
			$(this).attr('href', hrefValue);
			$(this).click();
		});

		$('.icon-link').click(function(){
			var imgUrl = $('.mfp-img').attr('src');
			var $temp = $("<input id='currentImgUrl'>");
			$temp.attr('value', imgUrl);
			$(".mfp-content").append($temp);

			var clipboard = new Clipboard('.icon-link');
			clipboard.on('success', function(e) {
				alert('already copy image url to clipboard');
				$('#currentImgUrl').remove();
			});
			clipboard.on('error', function(e) {
				alert('failed to copy image url to clipboard ');
				$('#currentImgUrl').remove();
			});
		});

	}

	  // MagnificPopup
		var magnifPopup = function() {
			$('.image-popup').filter(function(index){
				var currentCategory = $('.currentCategory a span').text();
				if(currentCategory == 'All')
					return true;
				else {
					if($(this).parent().hasClass(currentCategory))
						return true;
					else
						return false;
				}
			}).magnificPopup({
				type: 'image',
				fixedContentPos: true,
				fixedBgPos: true,
				overflowY: 'auto',
				tLoading: 'Loading image ...',
				mainClass: 'mfp-img-mobile',
				gallery: {
					enabled: true,
					navigateByImgClick: true,
					preload: [0,1] // Will preload 0 - before current, and 1 after the current image
				},
				image: {
				  markup: '<div class="mfp-figure">'+
				            '<div class="mfp-close"></div>'+
				            '<div class="mfp-img"></div>'+
				            '<div class="mfp-bottom-bar">'+
				              '<div class="mfp-title"></div>'+
				              '<div class="mfp-counter"></div>'+
				            '</div>'+
				          '</div>', // Popup HTML markup. `.mfp-img` div will be replaced with img tag, `.mfp-close` by close button

				  cursor: 'mfp-zoom-out-cur', // Class that adds zoom cursor, will be added to body. Set to null to disable zoom out cursor.

				  titleSrc: function(item) {
						return item.el.attr('title');
					},

				  verticalFit: true, // Fits image in area vertically

				  tError: '<a href="%url%">The image</a> could not be loaded.' // Error message
				},
				callbacks: {
					open: function() {
						setupShareBottom();
					},
					imageLoadComplete: function() {
						$('.my-social-share').remove();
						setupShareBottom();
			  	},
				}
			});
		};


	var currentPageIdx = -1;
	var hasMore = true;
	var pageSize = 24; //pc端pageSize
	var $grid;

	var imageLoaded = false;
	function fetchPage(pageIdx) {
		$.ajax({
      method: 'GET',
      url: "api/photos?page=" + pageIdx + "&size=" + pageSize + "",
    }).done(function(json){
			var contents = json['content'];
			var content, photoDiv, $photoDiv;

			for(var i=0; i< contents.length; i++) {
				content = contents[i];
				photoDiv = getPhotoDiv(content.category, content.url_index, content.url, content.name, content.description);
				$('.grid').append(photoDiv);
			}
			//对所有元素设置popup
			magnifPopup();

			$('.grid').imagesLoaded().progress(function (instance, image) {
				var scrollTopValue= $(document).scrollTop();
				//新数据到达后，重新构建isotope
				$('.grid').isotope('destroy');
				$grid = $('.grid').isotope(isoOptions);

				$(document).scrollTop(scrollTopValue);
	    }).always(function(){
				imageLoaded = true;
				if (getCurrentCategory() == 'All') {
					Waypoint.refreshAll();
				}else {
					changePhoto(getCurrentCategory());
				}

			});

			currentPageIdx += 1;
			hasMore = !json.last;
			isFetching = false;
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
				if(direction == 'down' && hasMore && !isFetching) {
					isFetching = true;
					imageLoaded = false;
					fetchPage(currentPageIdx + 1);
				}
			},
			offset: 'bottom-in-view'
		})
	}


	function getCurrentCategory() {
			return $('.currentCategory a span').text();
	}

	function getFilterName() {
		var currentCategory = $('.currentCategory a span').text();
		var filter;
		if(currentCategory == 'All')
			filter = "*";
		else {
			filter = "." + currentCategory;
		}
		return filter;
	}

	var isoOptions = {
		itemSelector: '.grid-item',
		layoutMode: 'masonry',
		stagger: 30,
		percentPosition: true,
		resizable: false,
		masonry: {
				columnWidth: '.grid-sizer',
		}
	};

	$grid = $('.grid').isotope(isoOptions);


	$(function(){
		contentWayPoint();
		toggleAside();
		initCategories();
		buttonsCustom();
		initWaypoint();
	});
}());
