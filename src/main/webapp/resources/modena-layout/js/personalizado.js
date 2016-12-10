$(document).ready(function () {
    $("form").keypress(function (e) {
        if (e.which === 13) {
            return false;
        }
    });
});
function dialogTop(formId, buttonId) {
    var selector = $('#$1\\:$2_dlg'.replace('$1', formId).replace('$2', buttonId));
    if (selector.css('top') !== 'auto') {
        $(selector).css({top: '0px'});
    } else {
        setTimeout(function () {
            dialogTop(formId, buttonId);
        }, 50);
    }
}