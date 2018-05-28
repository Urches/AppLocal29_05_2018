class Component {
    constructor(componentInfoObj) {
        this.elements = [];
        this.signals = [];
        this.coordinates = [];
        this.description;

        if(componentInfoObj.number)
            this.number = componentInfoObj.number;

        if(componentInfoObj.isNew)
            this.isNew = componentInfoObj.isNew;

        if(componentInfoObj.coordinates)
            this.coordinates = componentInfoObj.coordinates;

        if(componentInfoObj.description)
            this.description = componentInfoObj.description;

        if (componentInfoObj.elements)
            componentInfoObj.elements.forEach(elemObj => {
                this.addElement(elemObj);
            });

        if (componentInfoObj.signals)
            componentInfoObj.signals.forEach(signalObj => {
                this.addSignal(signalObj);
            });
    }

    addElement(elemObj) {
        if (elemObj.type == 'neuron') {
            this.addNeuron(elemObj);
        } else if (elemObj.type == 'generator') {
            this.addGenerator(elemObj)
        } else if (elemObj.type == 'foreignComponent') {
            return this.addForeingComponent(elemObj);
        } else if(elemObj.type == 'delay') {
            return this.addDelay(elemObj);
        }
    }

    getElement(data) {
        if (data.type == 'neuron') {
            return this.getNeurons().filter(neuron => data.number == neuron.number)[0];
        } else if (data.type == 'foreignComponent') {
            return this.getForeignComponents().filter(comp => data.number == comp.number)[0];
        } else if (data.type == 'generator') {
            return this.getGenerators().filter(gen => data.number == gen.number)[0];
        }else if(data.type == 'delay') {
            return this.getDelais().filter(gen => data.number == gen.number)[0];
        }
    }


    //Add logic
    /**
     * @param {*} neuronInfoObj
     * @returns number of added neuron
     */
    addNeuron(neuronInfoObj) {
        //clone check!
        let infoObject = $.extend(true, {}, neuronInfoObj);
        //if neuron element number is undefined set number like max number + 1
        if (!infoObject.number)
            infoObject.number = this._getMaxElementNumber() + 1;

        let neuron = new Neuron(infoObject);
        this.elements.push(neuron);
        return neuron.number;
    }

    addDelay(delayObj){
        //clone check!
        let infoObject = Object.assign({}, delayObj);
        //if neuron element number is undefined set number like max number + 1
        if (!delayObj.number)
            delayObj.number = this._getMaxElementNumber() + 1;

        let delay = new Delay(delayObj);
        this.elements.push(delay);
        return delay.number;
    }

    updateDelay(elementNum, delayObj) {
        let oldDelay = this.getElementByNumber(elementNum);
        ArrayUtils.prototype.remove(this.elements, [oldDelay]);
        delayObj.number = elementNum;
        return this.addDelay(delayObj);
    }


    addSignal(signalObj){
        this.signals.push(new Signal(signalObj));
    }

    getSignal(number){
        let signal = this.signals.find(s => s.signalNumber == number)[0];
        return signal;
    }

    addForeingComponent(foreignComp) {
        //clone check!
        let infoObject = Object.assign({}, foreignComp);
        let obj ={
            component:new Component(infoObject.component),
            type:'foreignComponent',
            number: infoObject.number && infoObject.number > 0 ? infoObject.number : this._getMaxElementNumber() + 1
        };
        this.elements.push(new ForeignComponent(obj));
    }

    getForeignComponents() {
        return this.elements.filter(el => el.type == 'foreignComponent');
    }

    addGenerator(genObj) {
        //clone check!
        let infoObject = Object.assign({}, genObj);
        //if neuron element number is undefined set number like max number + 1
        if (!infoObject.number)
            infoObject.number = this._getMaxElementNumber() + 1;

        let generator = new Generator(infoObject);
        this.elements.push(generator);
        return generator.number;
    }

    updateGen(genObj) {
        let oldGen = this.getElementByNumber(genObj.number);
        ArrayUtils.prototype.remove(this.elements, [oldGen]);
        return this.addGenerator(genObj);
    }


    /**
     * @param {*} number element number
     */
    // getFreePorts(number) {
    //     return this.getElementByNumber(number)
    // }

    /**
     * @returns array of linked signals
     * @param number element number
     */
    getAllLinkedSignals(number) {
        return this.signals.filter(signal => signal.toElement === number || signal.fromElement === number);
    }

    /**
     * @returns array of element
     */
    getElements() {
        return this.elements;
    }

    /**
     * @returns array of neurons
     */
    getNeurons() {
        return this.elements.filter(el => el.type == 'neuron');
    }

    getGenerators() {
        return this.elements.filter(el => el.type == 'generator');
    }

    getDelais(){
        return this.elements.filter(el => el.type == 'delay');
    }

    getNotConnectedInfo() {
        let elements = this.getNotFullConnectedElements();
        let result = new Array();
        if (elements) {
            elements.forEach(element => {
                let ports = this.getNotConnectedPorts(element);
                result.push({
                    element,
                    ports
                });
            });
        }
        return result;
    }

    isPortConnected(neuronNumber, portNumber) {
        return (this.signals.find(s => s.fromElement == neuronNumber && s.fromPort == portNumber));
    }

    getNotConnectedPorts(neuron) {
        let linkedSiganls = this.getElementSignals(neuron.number);
        let ports = new Array();

        neuron.getPorts().forEach(port => {

            if (port.position.toUpperCase() == "IN") {
                if (!linkedSiganls.find(signal => signal.toPort == port.number && signal.toElement == neuron.number)) {
                    ports.push(port);
                }
            }
            if (port.position.toUpperCase() == "OUT") {
                if (!linkedSiganls.find(signal => signal.fromPort == port.number && signal.fromElement == neuron.number)) {
                    ports.push(port);
                }
            }
        });
        return ports;
    }

    getNotFullConnectedElements() {
        let elements = this.getElements().filter(element => {

            let toSignals = this.getAllLinkedSignals(element.number).filter(s => s.toElement === element.number);

            /**
             * TODO Check it!
             * @type {Array}
             */
            let ports = element.getPorts();
            if(ports && ports.length > 0){
                let results = ports.map(inPort => (!toSignals.find(signal => signal.toPort === inPort.number)));
                if (results.find(r => r == true)) return true;
            }
            return false;
        });
        return elements;
    }

    /**
     * @returns all out ports
     */
    // getOutPorts() {
    //     return this.getNeurons().map(neuron => neuron.getOutPort());
    // }

    /**
     * @param {*} number element number
     * @returns array of element signals
     */
    getElementSignals(number) {
        return this.signals.filter(s => s.fromElement == number
            || s.toElement == number);
    }

    updateNeuron(elementNum, neuronInfoObj) {
        let oldNeuron = this.getElementByNumber(elementNum);
        ArrayUtils.prototype.remove(this.elements, [oldNeuron]);
        neuronInfoObj.number = elementNum;
        return this.addNeuron(neuronInfoObj);
    }

    deleteElement(element) {
        //let neuron = this.getElementByNumber(number);
        console.log(element);
        ArrayUtils.prototype.remove(this.elements, [element]);
        this.deleteLinkedSignals(element.number);
    }

    deleteNeuron(neuron) {
        //let neuron = this.getElementByNumber(number);
        console.log(neuron);
        ArrayUtils.prototype.remove(this.elements, [neuron]);
        this.deleteLinkedSignals(neuron.number);
    }

    deleteDelay(delay) {
        console.log(delay);
        ArrayUtils.prototype.remove(this.elements, [delay]);
        this.deleteLinkedSignals(delay.number);
    }

    deleteForeignComponent(foreignComp){
        console.log(foreignComp);
        ArrayUtils.prototype.remove(this.elements, [foreignComp]);
        this.deleteLinkedSignals(foreignComp.number);
    }

    deleteSignal(number) {
        let signal = this.getSignalByNumber(number);
        ArrayUtils.prototype.remove(this.signals, [signal]);
    }

    deleteLinkedSignals(elementNum) {
        let removes = this.getElementSignals(elementNum);
        ArrayUtils.prototype.remove(this.signals, removes);
    }

    _getMaxElementNumber() {
        if(this.elements.length != 0){
            let maximum = this.elements.map(el => el.number).reduce((max, current) => current > max ? current : max);
            return maximum;
        } else return 0;
    }

    getElementByNumber(number) {
        return this.elements.filter(el => el.number == number)[0];
    }

    getSignalByNumber(number) {
        return this.signals.filter(s => s.number == number)[0];
    }

    //Override
    // getInfoObj() {
    //     let newInfoObj = {
    //         neurons: Array.from(this.neuronsMap.values()).map(p => p.getInfoObj()),
    //         signals: this.signals.map(p => p.getInfoObj())
    //     };
    //     return newInfoObj;
    // }
}

Component.prototype.fromJsonObj = function (json) {
    return new Component();
};