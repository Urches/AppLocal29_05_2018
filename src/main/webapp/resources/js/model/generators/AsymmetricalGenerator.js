class AsymmetricalGgenerator {
    constructor(){
        this.values = [];
    }

    appendValue(fromTime, toTime, value){
        this.values.push({fromTime, toTime, value});
    }

    getValues(){
        return this.values;
    }
}