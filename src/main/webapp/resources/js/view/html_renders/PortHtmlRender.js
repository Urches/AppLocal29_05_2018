class PortHtmlRender extends ElementHtmlRender{
    constructor(){
        super();
        this.port = null;
    }

    setElement(port){
        this.port = port;
        this._generateHtml();
    }

    //Override
    _generateHtml(){
        let htmlView = document.createElement('div');
        htmlView.className = this.port.number + '-port'  + ' ' +  'port-' + this.port.position;
        htmlView.dataset.content = this.port.number;
        this.htmlView = htmlView;
    }

    //Override
    getHtml(){
        return this.htmlView;
    }
}

