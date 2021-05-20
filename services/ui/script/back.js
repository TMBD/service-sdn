equipment.onmousedown = function(event) {

    let shiftX = event.clientX - equipment.getBoundingClientRect().left;
    let shiftY = event.clientY - equipment.getBoundingClientRect().top;

    equipment.style.position = 'absolute';
    equipment.style.zIndex = 1000;
    document.body.append(equipment);

    moveAt(event.pageX, event.pageY);

    function moveAt(pageX, pageY) {
    equipment.style.left = pageX - shiftX + 'px';
    equipment.style.top = pageY - shiftY + 'px';
    }

    function onMouseMove(event) {
    moveAt(event.pageX, event.pageY);

    equipment.hidden = true;
    let elemBelow = document.elementFromPoint(event.clientX, event.clientY);
    equipment.hidden = false;

    if (!elemBelow) return;

    let droppableBelow = elemBelow.closest('.droppable');
    if (currentDroppable != droppableBelow) {
        if (currentDroppable) { // null when we were not over a droppable before this event
        leaveDroppable(currentDroppable);
        }
        currentDroppable = droppableBelow;
        if (currentDroppable) { // null if we're not coming over a droppable now
        // (maybe just left the droppable)
        enterDroppable(currentDroppable);
        }
    }
    }

    document.addEventListener('mousemove', onMouseMove);

    equipment.onmouseup = function() {
    document.removeEventListener('mousemove', onMouseMove);
    equipment.onmouseup = null;
    };

};

function enterDroppable(elem) {
    elem.style.background = 'pink';
}

function leaveDroppable(elem) {
    elem.style.background = '';
}

equipment.ondragstart = function() {
    return false;
};