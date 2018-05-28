class HtmlUtiles{
    constructor(){
        
    }
}

HtmlUtiles.prototype.clean = function(fromElem){
    while (fromElem.firstChild)
        fromElem.removeChild(fromElem.firstChild);
};

HtmlUtiles.prototype.appendAllChilds = function(htmlElements, container){
    htmlElements.forEach(htmlElement => container.appendChild(htmlElement));
};

HtmlUtiles.prototype.addShadowOnElement = function(element){
    element.style.boxShadow = '0px 0px 26px 1px rgba(24,69,194,1)';
};

HtmlUtiles.prototype.dropShadowFromElement = function(element){
    element.style.boxShadow = null;
};
HtmlUtiles.prototype.isShadowSet = function(element){
    return (element.style.boxShadow);
};

HtmlUtiles.prototype.getViewElementCenterCoordinates = function(htmlObj) {
    let rectangle = htmlObj.getBoundingClientRect();
    let xAxis = rectangle.left + (rectangle.right - rectangle.left) / 2;
    let yAxis = rectangle.top + (rectangle.bottom - rectangle.top) / 2;
    return {
        xAxis,
        yAxis
    };
}

HtmlUtiles.prototype.insertAfter = function(elem, refElem) {
    return refElem.parentNode.insertBefore(elem, refElem.nextSibling);
}


