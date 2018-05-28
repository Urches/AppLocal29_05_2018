class NeuronHtmlRender extends ElementHtmlRender {
    constructor(componentHtmlRender) {
        super();
        this.portRender = new PortHtmlRender();
        this.componentHtmlRender = componentHtmlRender;
        this.neuron = null;
    }

    setElement(neuron){
        this.neuron = neuron;
        this._generateHtml();
    }

    //Override
    _generateHtml() {
        let activatedBlockStylesMap = new Map();
        activatedBlockStylesMap.set('LINEAR', ' linear-af-neuron');
        activatedBlockStylesMap.set('SIGMOID', ' sigmoid-af-neuron');
        activatedBlockStylesMap.set('THRESHOLD', ' threshold-af-neuron');
        activatedBlockStylesMap.set('LINEAR_BOUNDER', ' linear_bounder-af-neuron');

        let neuronHtml = document.createElement('div');
        this.componentHtmlRender.setElementType(neuronHtml, this.neuron);
        this.componentHtmlRender.setElementNumber(neuronHtml, this.neuron);
        neuronHtml.className = 'neuron-node draggable ported-element';
        neuronHtml.dataset.content = this.neuron.number;
        neuronHtml.className += activatedBlockStylesMap.get(this.neuron.activatedBlock.toUpperCase());

        var htmlInPortsContainer = document.createElement('div');
        htmlInPortsContainer.className = 'in-ports';
        let inPorts = this._getHtmlPorts('IN');
        HtmlUtiles.prototype.appendAllChilds(inPorts, htmlInPortsContainer);
        neuronHtml.appendChild(htmlInPortsContainer);

        
        let outPorts = this._getHtmlPorts('OUT');
        HtmlUtiles.prototype.appendAllChilds(outPorts, neuronHtml);

        this.htmlView = neuronHtml;
    }

    _getHtmlPorts(position){
        let ports = this.neuron.getPortsByPosition(position);
        let portsHtml = ports.map(port => {
            this.portRender.setElement(port);
            return this.portRender.getHtml();
        });
        return portsHtml;
    }

    //Override
    getHtml() {
        return this.htmlView;
    }
};