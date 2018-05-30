class Delay extends PortedElement{
    constructor(delayObj){
        super(delayObj.ports);
        this.type = 'delay';
        this.number = delayObj.number;
        this.delayTime = delayObj.delayTime;
        this.sameSideView = false;
    }
}