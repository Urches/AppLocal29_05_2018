class ConnectionInspector{
    constructor(componentView){
        this.componentView = componentView;
        this.preparedConnection = this._getDefaultPreparingConnection();
    }

    /**
     * @param {*} flag IN or OUT
     * @param {*} fromElement number
     * @param {*} fromPort number
     */
    setTarget(flag, elementNum, portNum){
        if (flag === 'OUT'){
            this.preparedConnection.fromElement = elementNum;
            this.preparedConnection.fromPort = portNum;
            this.preparedConnection.isFromPrepared = true;
        } else if(flag === 'IN'){
            this.preparedConnection.toElement = elementNum;
            this.preparedConnection.toPort = portNum;
            this.preparedConnection.isToPrepared = true;
        } else throw new Exception('undefined flag ' + flag);

        if (this.isConnectAvaliable()){
            this.createConnection();
        } 
    }

    setTargets(fromElementNum, fromPortNum, toElementNum, toPortNum){
        setTarget('OUT', fromElementNum, fromPortNum);
        setTarget('IN', toElementNum, toPortNum);
    }

    /**
     * Create connection between two elements ports
     * based on prepaerdConnectionObj
     */
    createConnection(){
        console.log('add signal!');
        console.log(this.preparedConnection);
        this.componentView.controller.addSignal(this.preparedConnection);
        this.preparedConnection = this._getDefaultPreparingConnection();
        console.log(this.componentView.component.signals);
    }

    isConnectAvaliable(){
        return (this.preparedConnection.isToPrepared == true &&
        this.preparedConnection.isFromPrepared == true &&
        this.isInPortFree() && this.isDifferentElement());
    }

    isInPortFree(){

        let signals = this.componentView.component.getElementSignals(this.preparedConnection.toElement);
        if(signals.length == 0) {
            console.log('no linked signal found!');
            return true;
        } else {
            console.log('linked signal found!');
        }
        let inPortSignal = signals.find(s => s.toPort === this.preparedConnection.toPort && s.toElement === this.preparedConnection.toElement);

        if(!inPortSignal) {
            console.log('no port linked signals found!');
            return true;
        } else {
            console.log('linked port found!');
        }

        return false;
    }

    isDifferentElement(){
        return this.preparedConnection.fromElement !== this.preparedConnection.toElement;
    }

    _getDefaultPreparingConnection(){
        return {
            fromElement: 0,
            fromPort :0,
            toElement: 0,
            toPort: 0,
            isFromPrepared: false,
            isToPrepared: false,
        };
    }
}