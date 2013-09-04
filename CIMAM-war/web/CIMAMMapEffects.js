for (var Z in CIMAMMapVectors) {
    for (var vector in CIMAMMapVectors[Z]) {
        (function(Vector, Name, Description, UID, CurrentFloor) {
            Vector[0].style.cursor = "pointer";
            Name[0].style.cursor = "pointer";
            $(Vector.node).mouseover(function() {
                if (CIMAMMapCurrentFloor == CurrentFloor) {
                    Vector.attr({
                        opacity: 1
                    });
                }
            });

            $(Vector.node).mouseout(function() {
                if (CIMAMMapCurrentFloor == CurrentFloor) {
                    Vector.attr({
                        opacity: 0.5
                    });
                }
            });

            $(Vector.node).click(function(Event) {
                if (CIMAMMapCurrentFloor == CurrentFloor) {
                    if (CIMAMMapDragged) {
                        CIMAMMapDragged = false;
                    } else {
                        $('#CIMAM-Map-Description').clearQueue().fadeOut(100, function() {
                            $('#CIMAM-Map-Description-Content').html(Description);
                            $('#CIMAM-Map-Description-Edit-Link').attr("href", "EditForm?uid=" + UID);
                            $('#CIMAM-Map-Description').css({
                                top: (Event.pageY - $('#CIMAM-Map-Container').offset().top + 10),
                                left: (Event.pageX - $('#CIMAM-Map-Container').offset().left + 10)
                            }).fadeTo(300, 1);
                        });
                    }
                }
            });

            $(Name.node).click(function(Event) {
                if (CIMAMMapCurrentFloor == CurrentFloor) {
                    if (CIMAMMapDragged) {
                        CIMAMMapDragged = false;
                    } else {
                        $('#CIMAM-Map-Description').clearQueue().fadeOut(100, function() {
                            $('#CIMAM-Map-Description-Content').html(Description);
                            $('#CIMAM-Map-Description-Edit-Link').attr("href", "EditForm?uid=" + UID);
                            $('#CIMAM-Map-Description').css({
                                top: (Event.pageY - $('#CIMAM-Map-Container').offset().top + 10),
                                left: (Event.pageX - $('#CIMAM-Map-Container').offset().left + 10)
                            }).fadeTo(300, 1);
                        });
                    }
                }
            });

            $(Name.node).mouseover(function() {
                if (CIMAMMapCurrentFloor == CurrentFloor) {
                    Vector.attr({
                        opacity: 1
                    });
                }
            });

            $(Name.node).mouseout(function() {
                if (CIMAMMapCurrentFloor == CurrentFloor) {
                    Vector.attr({
                        opacity: 0.5
                    });
                }
            });

        })(CIMAMMapVectors[Z][vector], CIMAMMapNames[Z][vector], CIMAMMapDescriptions[Z][vector], CIMAMMapUIDs[Z][vector], Z);
    }
}

$('#CIMAM-Map-Description-Close').click(function() {
    $('#CIMAM-Map-Description').clearQueue().fadeOut(300);
});

$('#CIMAM-Map-Pan-Right').mouseover(function() {
    $('#CIMAM-Map-Pan-Right').css({
        "background-position": "0 -15px"
    });
});
$('#CIMAM-Map-Pan-Right').mouseout(function() {
    $('#CIMAM-Map-Pan-Right').css({
        "background-position": "0 0"
    });
});

$('#CIMAM-Map-Pan-Up').mouseover(function() {
    $('#CIMAM-Map-Pan-Up').css({
        "background-position": "0 -15px"
    });
});
$('#CIMAM-Map-Pan-Up').mouseout(function() {
    $('#CIMAM-Map-Pan-Up').css({
        "background-position": "0 0"
    });
});

$('#CIMAM-Map-Pan-Left').mouseover(function() {
    $('#CIMAM-Map-Pan-Left').css({
        "background-position": "0 -15px"
    });
});
$('#CIMAM-Map-Pan-Left').mouseout(function() {
    $('#CIMAM-Map-Pan-Left').css({
        "background-position": "0 0"
    });
});

$('#CIMAM-Map-Pan-Down').mouseover(function() {
    $('#CIMAM-Map-Pan-Down').css({
        "background-position": "0 -15px"
    });
});
$('#CIMAM-Map-Pan-Down').mouseout(function() {
    $('#CIMAM-Map-Pan-Down').css({
        "background-position": "0 0"
    });
});

$('#CIMAM-Map-Floor-Up').mouseover(function() {
    $('#CIMAM-Map-Floor-Up').css({
        "background-position": "0 -15px"
    });
});
$('#CIMAM-Map-Floor-Up').mouseout(function() {
    $('#CIMAM-Map-Floor-Up').css({
        "background-position": "0 0"
    });
});

$('#CIMAM-Map-Floor-Down').mouseover(function() {
    $('#CIMAM-Map-Floor-Down').css({
        "background-position": "0 -15px"
    });
});
$('#CIMAM-Map-Floor-Down').mouseout(function() {
    $('#CIMAM-Map-Floor-Down').css({
        "background-position": "0 0"
    });
});

$('#CIMAM-Map-Zoom-Up').mouseover(function() {
    $('#CIMAM-Map-Zoom-Up').css({
        "background-position": "0 -15px"
    });
});
$('#CIMAM-Map-Zoom-Up').mouseout(function() {
    $('#CIMAM-Map-Zoom-Up').css({
        "background-position": "0 0"
    });
});

$('#CIMAM-Map-Zoom-Down').mouseover(function() {
    $('#CIMAM-Map-Zoom-Down').css({
        "background-position": "0 -15px"
    });
});
$('#CIMAM-Map-Zoom-Down').mouseout(function() {
    $('#CIMAM-Map-Zoom-Down').css({
        "background-position": "0 0"
    });
});

$('#CIMAM-Map-Pan-Up').click(function() {
    CIMAMMapTranslate(0, 0.1 * CIMAMMapCanvasHeight);
});

$('#CIMAM-Map-Pan-Down').click(function() {
    CIMAMMapTranslate(0, -0.1 * CIMAMMapCanvasHeight);
});

$('#CIMAM-Map-Pan-Left').click(function() {
    CIMAMMapTranslate(0.1 * CIMAMMapCanvasWidth, 0);
});

$('#CIMAM-Map-Pan-Right').click(function() {
    CIMAMMapTranslate(-0.1 * CIMAMMapCanvasWidth, 0);
});

$('#CIMAM-Map-Floor-Up').click(function() {
    CIMAMMapCurrentFloor++;
    $('#CIMAM-Map-Floor-Text').fadeOut(100, function() {
        $('#CIMAM-Map-Floor-Text').html(CIMAMMapCurrentFloor).fadeIn(300);
    });
    CIMAMMapDisplayFloor(CIMAMMapCurrentFloor);
});

$('#CIMAM-Map-Floor-Down').click(function() {
    if (CIMAMMapCurrentFloor != 0) {
        CIMAMMapCurrentFloor--;
        $('#CIMAM-Map-Floor-Text').fadeOut(100, function() {
            $('#CIMAM-Map-Floor-Text').html(CIMAMMapCurrentFloor).fadeIn(300);
        });
        CIMAMMapDisplayFloor(CIMAMMapCurrentFloor);
    }
});

$('#CIMAM-Map-Zoom-Up').click(CIMAMMapZoomIn);

$('#CIMAM-Map-Zoom-Down').click(CIMAMMapZoomOut);

document.getElementById('CIMAM-Map-Canvas').onmousedown = function(event) {
    event.preventDefault ? event.preventDefault() : event.returnValue = false;
    CIMAMMapStartX = event.clientX;
    CIMAMMapStartY = event.clientY;
    document.getElementById('CIMAM-Map-Canvas').onmousemove = function(event) {
        event.preventDefault ? event.preventDefault() : event.returnValue = false;
        CIMAMMapTranslate(event.clientX - CIMAMMapStartX, event.clientY - CIMAMMapStartY);
        CIMAMMapStartX = event.clientX;
        CIMAMMapStartY = event.clientY;
        CIMAMMapDragged = true;
    };
};

document.body.onmouseup = function(event) {
    document.getElementById('CIMAM-Map-Canvas').onmousemove = null;
};

document.body.onmousewheel = function(event) {
    event.preventDefault ? event.preventDefault() : event.returnValue = false;
    // cross-browser wheel delta
    var e = window.event || e; // old IE support
    var delta = Math.max(-1, Math.min(1, (e.wheelDelta || -e.detail)));

    if (delta > 0)
        CIMAMMapZoomIn();
    else
        CIMAMMapZoomOut();
}

function CIMAMMapTranslate(x, y) {
    //    for(var Z in CIMAMMapVectors) {
    //	for(var Vector in CIMAMMapVectors[Z]){
    //	    CIMAMMapVectors[Z][Vector].translate(x,y);
    //	}
    //    }
    var newWidth = CIMAMMapCanvasWidth;
    var newHeight = CIMAMMapCanvasHeight;
    var newX = CIMAMMapX - x;
    var newY = CIMAMMapY - y;
    CIMAMMapCanvas.setViewBox(newX, newY, newWidth, newHeight, true);
    CIMAMMapCanvasWidth = newWidth;
    CIMAMMapCanvasHeight = newHeight;
    CIMAMMapX = newX;
    CIMAMMapY = newY;
}

function CIMAMMapAdjustNames() {
    for (var Z in CIMAMMapVectors) {
        for (var Vector in CIMAMMapVectors[Z]) {
            CIMAMMapNames[Z][Vector].attr(CIMAMMapTextAttributes);
        }
    }
}

function CIMAMMapZoomIn() {
    if (CIMAMMapZoomLevel < 2) {
        CIMAMMapZoomLevel += 0.1;
        //	for(var Z in CIMAMMapVectors) {
        //	    for(var Vector in CIMAMMapVectors[Z]) {
        //		CIMAMMapVectors[Z][Vector].scale(1.1,1.1, CIMAMMapCanvasWidth/2 ,CIMAMMapCanvasHeight/2);
        //	    }
        //	}
        var newWidth = CIMAMMapCanvasWidth * 0.9;
        var newHeight = CIMAMMapCanvasHeight * 0.9;
        var newX = CIMAMMapX + CIMAMMapCanvasWidth * 0.05;
        var newY = CIMAMMapY + CIMAMMapCanvasHeight * 0.05;
        CIMAMMapCanvas.setViewBox(newX, newY, newWidth, newHeight, true);
        CIMAMMapCanvasWidth = newWidth;
        CIMAMMapCanvasHeight = newHeight;
        CIMAMMapX = newX;
        CIMAMMapY = newY;
        CIMAMMapAdjustNames();
    }
}

function CIMAMMapZoomOut() {
    if (CIMAMMapZoomLevel > 0.2) {
        CIMAMMapZoomLevel -= 0.1;
        //	for(var Z in CIMAMMapVectors) {
        //	    for(var Vector in CIMAMMapVectors[Z]) {
        //		CIMAMMapVectors[Z][Vector].scale(0.9,0.9, CIMAMMapCanvasWidth/2 ,CIMAMMapCanvasHeight/2);
        //	    }
        //	}
        var newWidth = CIMAMMapCanvasWidth * 1.1;
        var newHeight = CIMAMMapCanvasHeight * 1.1;
        var newX = CIMAMMapX - CIMAMMapCanvasWidth * 0.05;
        var newY = CIMAMMapY - CIMAMMapCanvasHeight * 0.05;
        CIMAMMapCanvas.setViewBox(newX, newY, newWidth, newHeight, true);
        CIMAMMapCanvasWidth = newWidth;
        CIMAMMapCanvasHeight = newHeight;
        CIMAMMapX = newX;
        CIMAMMapY = newY;
        CIMAMMapAdjustNames();
    }
}

function CIMAMMapDisplayFloor(floor) {
    for (var Z in CIMAMMapVectors) {
        if (Z != floor) {
            for (var Vector in CIMAMMapVectors[Z]) {
                (function(Z, Vector) {
                    CIMAMMapVectors[Z][Vector].animate({
                        opacity: 0.1
                    }, 300);
                    CIMAMMapNames[Z][Vector].animate({
                        opacity: 0
                    }, 300);
                    CIMAMMapNames[Z][Vector].toBack();
                    CIMAMMapVectors[Z][Vector].toBack();
                    CIMAMMapVectors[Z][Vector].attr({
                        cursor: "default"
                    });
                    CIMAMMapNames[Z][Vector].attr({
                        cursor: "default"
                    });
                })(Z, Vector);
            }
        }
    }
    for (var Vector in CIMAMMapVectors[floor]) {
        (function(floor, Vector) {
            CIMAMMapVectors[floor][Vector].animate({
                opacity: 0.5
            }, 300);
            CIMAMMapNames[floor][Vector].animate({
                opacity: 0.5
            }, 300);
            CIMAMMapVectors[floor][Vector].toFront();
            CIMAMMapNames[floor][Vector].toFront();
            CIMAMMapVectors[floor][Vector].attr({
                cursor: "pointer"
            });
            CIMAMMapNames[floor][Vector].attr({
                cursor: "pointer"
            });
        })(floor, Vector);
    }
}
