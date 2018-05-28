class Port {
    constructor(portInfoObj) {
        Object.assign(this, portInfoObj);
        // this.position = portInfoObj.position;
       // this.number = portInfoObj.number;
        // this.type = portInfoObj.type;
        // this.generator = portInfoObj.generator;

        if(portInfoObj.observed != 'undefined'){
            this.observed = portInfoObj.observed;
        } else if(this.position.toUpperCase()  === 'OUT'){
            this.observed = true;
        } else this.observed = false;
    }

    //Override
    // getInfoObj() {
    //     let newInfoObj = {
    //         position:this.position,
    //         portNumber:this.number,
    //         type:this.type,
    //         observed:this.observed,
    //     };
    //     return newInfoObj;
    // }
}