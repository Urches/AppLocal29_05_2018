class RunWorriesView {
    constructor(){
        this.component = null;
        this.container = document.querySelector('.execution-worrise');
        this.appendWorrings(["Элемент не выбран"]);
    }

    setComponent(component){
        this.component = component;
    }

    show(){
        this.container.display = 'flex';
        this.update();
    }

    close(){
        this.container.display = 'none';
    }

    update(){
        let warrings = '';
        if(this.component){
            warrings = this.getWorrings();
        } else {
            warrings =  ["Элемент не выбран"];
        }

        this.cleanContainer();
        this.appendWorrings(warrings);
    }

    getWorrings(){
        let warrings = this.component.getNotConnectedInfo().filter(info => info.ports && info.ports.length > 0)
            .map(info => "Элемент с номером " + info.element.number + " \nимеет несоединённые порты: " +
        info.ports.map(p => p.number).join(", "));
        console.log(warrings);
        return warrings;
    }

    appendWorrings(warrings){
        warrings.forEach(warring => {
            this.container.innerHTML += '<label>' + warring + '</label>';
        });
        this.container.display = 'flex';
    }

    cleanContainer(){
        HtmlUtiles.prototype.clean(this.container);
    }
}
