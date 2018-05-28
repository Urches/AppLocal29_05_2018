class Neuron extends PortedElement {
    constructor(neuronInfoObj) {
        super(neuronInfoObj.ports);
        this.type = 'neuron';
        this.number = neuronInfoObj.number;
        this.activatedBlock = neuronInfoObj.activatedBlock;  
    }
    
    setActivatedBlock(activatedBlock){
        this.activatedBlock = activatedBlock;
    }

    getOutPort(){
        return this.getPorts().find(p=>p.position.toUpperCase() === 'OUT');
    }

    getInPorts(){
        return this.getPorts().find(p=>p.position.toUpperCase() === 'IN');
    }
}

