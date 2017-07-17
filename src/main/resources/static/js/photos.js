$.urlParam = function (name, url) {
    if (!url) {
        url = window.location.href;
    }
    var results = new RegExp('[\\?&]' + name + '=([^&#]*)').exec(url);
    if (!results) {
        return undefined;
    }
    return results[1] || undefined;
}

$(document).ready(function () {
    changePageAndSize();
    var category = $.urlParam('category');
    $('.' + category).addClass('current');
});

function changePageAndSize() {
    $('#pageSizeSelect').change(function (evt) {
        window.location.replace("/photos/view?pageSize=" + this.value + "&page=1");
    });
}

