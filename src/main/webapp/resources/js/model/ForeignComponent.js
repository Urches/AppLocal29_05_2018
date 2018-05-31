class ForeignComponent extends PortedElement{
    constructor(foreignInfoObj){
        super(foreignInfoObj.ports);
        this.number = foreignInfoObj.number;
        this.type = 'foreignComponent';
        this.description = foreignInfoObj.component.description;
        this.component = foreignInfoObj.component;
        this.ports = [];
        let index = 1;
        this.component.getNotConnectedInfo().forEach(value => {
            value.ports.forEach(port => {
                let innerPort = Object.assign({}, port);

                let outterPort = Object.assign({}, innerPort);
                outterPort.number = index;
                outterPort.innerPort = innerPort;
                outterPort.innerPort.element = value.element.number;
                this.ports.push(outterPort);
                index++;
            });
        });
        console.log(this.ports);
    }
}