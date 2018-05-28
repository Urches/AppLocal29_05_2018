class Signal {
    constructor(signalInfoObj) {
        Object.assign(this, signalInfoObj);
        // this.fromElement = signalInfoObj.fromElement;
        // this.fromPort = signalInfoObj.fromPort;
        // this.toElement = signalInfoObj.toElement;
        // this.toPort = signalInfoObj.toPort;
        // this.type = signalInfoObj.type;

        this.signalNumber = signalInfoObj.fromElement.toString()
        .concat(signalInfoObj.fromPort)
        .concat(signalInfoObj.toElement)
        .concat(signalInfoObj.toPort);
    }

    //Override
    getInfoObj() {
        return Object.assign({}, this);
    }
}