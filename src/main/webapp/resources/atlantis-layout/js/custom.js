/*! custom - v0.0.1 - 2017*/
$(document).ready(function () {
    var handler = function (event) {
        if (event.which == 13) {
            var el = event.target;
            if (/textarea/i.test(el.tagName) || /button/i.test(el.tagName))
                return true;
            if (/input/i.test(el.tagName) && /file/i.test(el.type))
                return true;
            if (_handler)
                setTimeout(_handler, 1);
            event.stopPropagation();
            return false;
        }
        return true;
    };
    $('conteudo').keyup(handler).keypress(handler)
});
