class ComponentHtmlRender extends ElementHtmlRender {
    constructor(componentView) {
        super();
        this.componentView = componentView;
        this.neuronHtmlRender = new NeuronHtmlRender(this);
        this.signalHtmlRender = new SignalHtmlRender(this);
        this.portRender = new PortHtmlRender();
        this.component = null;
        this.ELEMENT_TYPE_ATTR_NAME = 'element-type';
    }

    setElement(component) {
        this.component = component;
    }

    _getNeuronsHtml() {
        let neurons = this.component.getNeurons();
        let htmlNeurons = neurons.map(neuron => {
            this.neuronHtmlRender.setElement(neuron);
            return this.neuronHtmlRender.getHtml();
        });
        return htmlNeurons;
    }

    _getSignalsHtml() {
        let signals = this.component.signals;
        let signalsHtml = signals.map(signal => {
            this.signalHtmlRender.setElement(signal);
            return this.signalHtmlRender.getHtml();
        });
        return signalsHtml;
    }

    _getForeignComponentsHtml() {
        let foreignComponents = this.component.getForeignComponents();
        let foreignComponentsHtml = foreignComponents.map(foreignComponent => {
            let div = document.createElement('div');
            this.setElementType(div, foreignComponent);
            this.setElementNumber(div, foreignComponent);
            div.className = foreignComponent.type + '-foreignComponent draggable ported-element';
            div.dataset.content = foreignComponent.number;

            let htmlInPortsContainer = document.createElement('div');
            htmlInPortsContainer.className = 'in-ports';
            div.appendChild(htmlInPortsContainer);

            foreignComponent.getPorts().forEach(port => {
                this.portRender.setElement(port);
                if(port.position.toUpperCase() === 'IN'){
                    htmlInPortsContainer.appendChild(this.portRender.getHtml());
                } else div.appendChild(this.portRender.getHtml());
            });
            return div;
        });
        return foreignComponentsHtml;
    }

    _getGeneratorsHtml() {
        let generators = this.component.getGenerators();
        let generatorsHtml = generators.map(gen => {
            let div = document.createElement('div');
            this.setElementType(div, gen);
            this.setElementNumber(div, gen);
            div.className = gen.properties.type + '-generator draggable ported-element';
            div.dataset.content = gen.number;
            this.portRender.setElement(gen.getPorts()[0]);
            div.appendChild(this.portRender.getHtml());
            return div;
        });
        return generatorsHtml;
    }

    _getDelaysHtml(){
        let delays = this.component.getDelais();
        let delaysHtml = delays.map(delay => {
            let div = document.createElement('div');
            this.setElementType(div, delay);
            this.setElementNumber(div, delay);
            div.className = delay.type + '-delay draggable ported-element';
            div.dataset.content = delay.number;

            let htmlInPortsContainer = document.createElement('div');
            htmlInPortsContainer.className = 'in-ports';
            div.appendChild(htmlInPortsContainer);

            delay.getPorts().forEach(port => {
                this.portRender.setElement(port);
                if(port.position.toUpperCase() === 'IN'){
                    htmlInPortsContainer.appendChild(this.portRender.getHtml());
                } else div.appendChild(this.portRender.getHtml());
            });
            if(delay.sameSideView){
                //smell!
                div.className += ' sameSideView';
                div.setAttribute('style', "justify-content: space-around;\n" +
                    "    /* align-items: center; */\n" +
                    "    flex-direction: column;");
            }
            return div;
        });
        return delaysHtml;
    }


    showHtml(container) {
        let htmlNeurons = this._getNeuronsHtml();
        let htmlGenerators = this._getGeneratorsHtml();
        let htmlForeignComponents = this._getForeignComponentsHtml();
        let htmlDelays = this._getDelaysHtml();
        //add style and coordinates

        this.setCoordinates(htmlNeurons);
        this.setCoordinates(htmlGenerators);
        this.setCoordinates(htmlDelays);
        this.setCoordinates(htmlForeignComponents);

        HtmlUtiles.prototype.appendAllChilds(htmlNeurons, container);
        HtmlUtiles.prototype.appendAllChilds(htmlGenerators, container);
        HtmlUtiles.prototype.appendAllChilds(htmlDelays, container);
        HtmlUtiles.prototype.appendAllChilds(htmlForeignComponents, container);
        let htmlSignals = this._getSignalsHtml();

        HtmlUtiles.prototype.appendAllChilds(htmlSignals, container);
    }

    setCoordinates(htmlComponents) {
        htmlComponents.forEach(htmlComponent => {
            let defaultStyle = new Properties().defaultNeuronStyle;
            for (var key in defaultStyle) {
                htmlComponent.style[key] = defaultStyle[key];
            }

            /**
             * Fixme smell!
             */
            let coord = this.componentView.getCoordinates(this.getElementTypeAndNumber(htmlComponent))[0];
            if(!coord){
                this.componentView.putCoordinates(
                    this.getElementNumber(htmlComponent),
                    400,
                    400
                );
                coord = this.componentView.getCoordinates(this.getElementTypeAndNumber(htmlComponent))[0];
            }


            htmlComponent.style.left = coord.xAxis;
            htmlComponent.style.top = coord.yAxis
        });
    }

    getElement(number){
        //console.log(`${number}-${type}`);
        return document.getElementById(`${number}-element`);
    }
    /**
     * @param htmlContainer take a ported-element html elememnt or child elememnt
     * @returns {number, type: *} of the chosen neuron
     */
    getElementTypeAndNumber(htmlContainer) {
        let type;
        while (!(type = htmlContainer.getAttribute(this.ELEMENT_TYPE_ATTR_NAME)))
            htmlContainer = htmlContainer.parentElement;
        return {number: parseInt(htmlContainer.id), type};
    }

    setElementNumber(htmlContainer, element) {
        htmlContainer.id = `${element.number}-element`;
    }

    getElementNumber(htmlContainer) {
        while (!htmlContainer.getAttribute(this.ELEMENT_TYPE_ATTR_NAME))
            htmlContainer = htmlContainer.parentElement;
        return parseInt(htmlContainer.id);
    }


    getElementType(htmlContainer) {
        console.log(htmlContainer);
        return htmlContainer.getAttribute(this.ELEMENT_TYPE_ATTR_NAME);
    }

    setElementType(htmlContainer, element) {
        htmlContainer.setAttribute(this.ELEMENT_TYPE_ATTR_NAME, element.type);
    }

    getGeneratorNumber(htmlContainer) {
        while (!htmlContainer.className.includes('generator'))
            htmlContainer = htmlContainer.parentElement;
        return parseInt(htmlContainer.id);
    }
}

ComponentHtmlRender.prototype.getPortNumber = function (htmlContainer) {
    return parseInt(htmlContainer.className)
}