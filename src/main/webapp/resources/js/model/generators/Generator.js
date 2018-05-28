class Generator extends  PortedElement{
    constructor(genObj){
        super([new Port({
            position: 'out',
            number: 1,
            type: 'digital'
        })]);
        this.number = genObj.number;
        this.type ='generator';
        this.properties = Object.assign({}, genObj.properties);
    }
}