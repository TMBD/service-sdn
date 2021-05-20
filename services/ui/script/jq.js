function line(x, y, x1, y1, parentId) {
    var l = $("<div class='line'>");
    var w = 0;
    l.css({
        top: y+w,
        left: x+w,
        width: Math.sqrt((x1-x)*(x1-x) + (y1 - y)*(y1 - y)),
        transform: 'rotate('+Math.atan2((y1-y),(x1-x))+'rad)'
    });
    $('#'+parentId).append(l);
}