$(document).ready(function () {
    changePageAndSize();
});

function changePageAndSize() {
    $('#pageSizeSelect').change(function (evt) {
        window.location.replace("/photos/view?pageSize=" + this.value + "&page=1");
    });
}

