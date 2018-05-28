class PortedElement {
    constructor(ports) {
        this.ports = ports;
    }

    /** 
     * @returns array of ports
    */
    getPorts(){
        return this.ports;
    }
    /**
     * @param position like: OUT, IN
     */
    getPortsByPosition(position){
        return this.getPorts().filter(port => port.position.toUpperCase() === position.toUpperCase());
    }

    getPortByNumber(number) {
        return this.getPorts().find(port => port.number == number);
    }


    addPort(portInfoObj){
        if (portInfoObj.number) {
            this.deletePort(portInfoObj.number);
        } else {
            portInfoObj.number = this.getMaxPortNumber() + 1;
        }
        let port = new Port(Object.assign({},portInfoObj));
        this.ports.push(port);
    }

    deletePort(portNum){
        ArrayUtils.prototype.remove(this.ports, [this.getPortByNumber(portNum)]);
    }

    getMaxPortNumber(){
        return Math.max.apply(Math, this.ports.map(p => p.number));
    }
}