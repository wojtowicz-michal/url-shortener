$.fn.datetimepicker.Constructor.Default = $.extend({}, $.fn.datetimepicker.Constructor.Default, {
    icons: {
        time: 'far fa-clock',
        date: 'far fa-calendar',
        up: 'fas fa-arrow-up',
        down: 'fas fa-arrow-down',
        previous: 'fas fa-chevron-left',
        next: 'fas fa-chevron-right',
        today: 'far fa-calendar-check-o',
        clear: 'far fa-trash',
        close: 'far fa-times'
    }
});

$('#dtp').datetimepicker({
    format: 'DD/MM/YYYY HH:mm',
    minDate: new Date()
});

function showHide() {
    d = document.getElementById("table-container");
    d.style.display = (d.style.display !== "none") ? "none" : "block";
}

function validateForm() {
    return validateUrl() && validateExpireDate();
}

function isUrlValid(url) {
    var pattern = new RegExp('^(https?:\\/\\/)?'
        + '((([a-z\\d]([a-z\\d-]*[a-z\\d])*)\\.)+[a-z]{2,}|'
        + '((\\d{1,3}\\.){3}\\d{1,3}))'
        + '(\\:\\d+)?(\\/[-a-z\\d%_.~+]*)*'
        + '(\\?[;&a-z\\d%_.~+=-]*)?'
        + '(\\#[-a-z\\d_]*)?$', 'i');
    return !pattern.test(url);
}

function validateUrl() {
    let url = $('#url').val();
    if (url === "") {
        swal({
            title: "Enter URL",
            text: "",
            icon: "warning",
            button: "OK",
        });
        return false;
    }

    if (isUrlValid(url)) {
        swal({
            title: "Invalid URL",
            text: "",
            icon: "warning",
            button: "OK",
        });
        return false;
    }
    return true;
}

function validateExpireDate() {
    var strDate = $('#date').val();
    if (strDate === "") return true;

    var date = strDate.substring(0, strDate.indexOf(" ")).split("/");
    var h = strDate.substring(strDate.indexOf(" "), strDate.indexOf(":"));
    var m = strDate.substring(strDate.indexOf(":") + 1);
    var expireDate = new Date(date[2], date[1] - 1, date[0], h, m, 0, 0);

    var today = new Date();
    today.setSeconds(0);
    today.setMilliseconds(0);

    if (expireDate < today) {
        swal({
            title: "Provide future date",
            text: "",
            icon: "warning",
            button: "OK",
        });
        return false;
    }
    return true;
}

