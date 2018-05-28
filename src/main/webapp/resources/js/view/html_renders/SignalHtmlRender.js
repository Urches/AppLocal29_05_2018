class SignalHtmlRender extends ElementHtmlRender {
    constructor(componentHtmlRender) {
        super();
        this.signal = null;
        this.componentHtmlRender = componentHtmlRender;
    }
    _createLineElement(x, y, length, angle) {
        var line = document.createElement("div");
        var styles = 'border: 1px solid black; ' +
            'width: ' + length + 'px; ' +
            'height: 0px; ' +
            '-moz-transform: rotate(' + angle + 'rad); ' +
            '-webkit-transform: rotate(' + angle + 'rad); ' +
            '-o-transform: rotate(' + angle + 'rad); ' +
            '-ms-transform: rotate(' + angle + 'rad); ' +
            'position: absolute; ' +
            'top: ' + y + 'px; ' +
            'left: ' + x + 'px; ';
        line.setAttribute('style', styles);
        return line;
    }

    _createLineBetweenCoordinates(x1, y1, x2, y2) {
        var a = x1 - x2,
            b = y1 - y2,
            c = Math.sqrt(a * a + b * b);

        var sx = (x1 + x2) / 2,
            sy = (y1 + y2) / 2;

        var x = sx - c / 2,
            y = sy;

        var alpha = Math.PI - Math.atan2(-b, a);

        return this._createLineElement(x, y, c, alpha);
    }

    setElement(signal) {
        this.signal = signal;
    }

    //Override
    getHtml() {
        console.log(this.signal);
        let fromCoord = this._getPortCoordinates(this.signal.fromElement, this.signal.fromPort);
        let toCoord = this._getPortCoordinates(this.signal.toElement, this.signal.toPort);
        let htmlView = this._createLineBetweenCoordinates(fromCoord.xAxis, fromCoord.yAxis, toCoord.xAxis, toCoord.yAxis);
        htmlView.className += ' ' + this.signal.signalNumber + '-signal';
        return htmlView;
    }

    _getPortCoordinates(elementNum, portNum) {
        let port;
        let elem = this.componentHtmlRender.getElement(elementNum);
        if(elem){
            port = elem.getElementsByClassName(portNum + '-port')[0];
        }

        //let port = this.component.getElementByNumber(elementNum).getPortByNumber(portNum);
        console.log(port);
        let coord = HtmlUtiles.prototype.getViewElementCenterCoordinates(port);
        return coord;
    }
}