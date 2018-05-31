class Controller{
    constructor(){
        this.component = null;
        this.page = null;
        this.chartProperties = new Properties().chartProperties;
        this.onComponentLoad = new EventEmitter();
        this.onComponentUpdated = new EventEmitter();
        this.onDiagramLoad = new EventEmitter();
        this.onDiagramCancel = new EventEmitter();
    }

    //init method
    start(){    
        this.page = new Page(this); 
    }

    getComponent(){
        return this.component;
    }

    //Component logic
    //rework!
    initFrontComponent(obj){
        this.component = new Component(obj);
        //this.loadComponent(obj);
        this.onComponentLoad.notify(this.component);
    }

    //smell!
    getComponentAjax(number, callback){
        $.get({
            url: `/controller/component/${number}`,
            dataType : "text",
            timeout: 10000,
            success: (json) => {
                console.log('component getted!');
                console.log(json);
                if(json && callback){
                    callback(JSON.parse(json));
                }
            },
            failure: function(errMsg) {
                console.log(errMsg);
            }
        });
    }

    getComponents(collback){
        //Test
        // console.log(new Test().foreignCOmponentsTestObj);
        // collback(JSON.stringify(new Test().foreignCOmponentsTestObj));
        console.log('go ajax get component');
        $.get({
            url: '/controller/components',
            dataType : "text",
            timeout: 10000,
            success: function(msg) {
                console.log(msg);
                collback(msg);
            },
            failure: function(errMsg) {
                console.log(errMsg);
            }
        });
    }

    createNewComponent(){
        //Test
        console.log("new component");
        this.initFrontComponent({isNew:true});
    }

    saveComponent(component, callback){
        $.ajax({
            type: "POST",
            url: "/controller/component/save",
            // The key needs to match your method's input parameter (case-sensitive).
            data: JSON.stringify(component),
            contentType: "application/json; charset=utf-8",
            dataType: "text",
            timeout: 10000,
            success: function(data) {
                //bad :(
                component.number = JSON.parse(data).number;
                if(callback){
                    callback(data);
                } else {
                    console.log(data);
                    component.number = JSON.parse(data).number;
                    console.log('component saved');
                }
            },
            failure: function(errMsg) {
                console.log(errMsg);
            }
        });
    }

    setChartProperties(){
        let self = this;
        $.ajax({
            type: "POST",
            url: "/controller/diagram/properties",
            // The key needs to match your method's input parameter (case-sensitive).
            data: JSON.stringify(self.chartProperties),
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            timeout: 10000,
            success: function(msg) {
                console.log(msg);
                self.getDiagram(self.component);
            },
            failure: function() {
                self.onDiagramCancel.notify("Неудалось установить модельное время!");
            },
            error: function () {
                self.onDiagramCancel.notify("Неудалось установить модельное время!!");
            }
        });
    }

    getDiagram(component) {
        //Test
        //this.onDiagramLoad.notify(new Test().testDiagramObj);
        let self = this;
        $.ajax({
            type: "POST",
            url: "/controller/diagram",
            // The key needs to match your method's input parameter (case-sensitive).
            data: JSON.stringify(component),
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            timeout: 10000,
            success: function(msg) {
                console.log(msg);
                self.page.showChart();
                self.onDiagramLoad.notify(msg);
            },
            failure: function() {
                console.log("Неудалось загрузить или построить диаграмму!");
                self.onDiagramCancel.notify("Неудалось загрузить или построить диаграмму!");
            },
            error: function (msg) {
                console.log(msg);
                console.log("Неудалось загрузить или построить диаграмму!");
                self.onDiagramCancel.notify("Неудалось загрузить или построить диаграмму!");
            }
        });
    }


    loadStructure(component = this.component){
        this.saveComponent(component, ()=>{
            location.href = `/controller/${component.number}/structure/vhdl`;
        });
    }

    //Local model control
    deleteElement(element){
        console.log(element);
        this.component.deleteElement(element);
        this.onComponentUpdated.notify();
    }


    addForeingComponent(foreignComp) {
        console.log('foreign comp added!');
        if(!foreignComp.type || foreignComp.type != 'foreignComponent'){
            foreignComp = {
                component:foreignComp,
                type: 'foreignComponent'
            }
        }
        this.component.addForeingComponent(foreignComp);
        this.onComponentUpdated.notify();
    }

    //Neuron logic
    addNeuron(neuronInfoObj){
        console.log('neuron added!');
        this.component.addNeuron(neuronInfoObj);
        this.onComponentUpdated.notify();
    }

    deleteNeuron(number){
        //this.component.
        this.component.deleteNeuron(number);
        this.onComponentUpdated.notify();
    }

    deleteGenerator(number) {
        this.component.deleteGenerator(number);
        this.onComponentUpdated.notify();
    }

    deleteForeignComponent(number) {
        this.component.deleteForeignComponent(number);
        this.onComponentUpdated.notify();
    }

    updateNeuron(number, neuronInfoObj){
        console.log('neuron updated!');
        this.component.updateNeuron(number, neuronInfoObj);
        this.onComponentUpdated.notify();
    }

    addOrUpdateNeuron(number, neuronInfoObj){
        if(this.component.getElementByNumber(number)){
            this.updateNeuron(number, neuronInfoObj);
        } else this.addNeuron(neuronInfoObj);
        this.onComponentUpdated.notify();
    }

    //Delay logic
    addOrUpdateDelay(number, delayObj){
        if(this.component.getElementByNumber(number)){
            this.updateDelay(number, delayObj);
        } else this.addDelay(delayObj);
        this.onComponentUpdated.notify();
    }

    updateDelay(number, delayObj){
        console.log('neuron updated!');
        this.component.updateDelay(number, delayObj);
        this.onComponentUpdated.notify();
    }

    addDelay(delayObj){
        console.log('neuron added!');
        this.component.addDelay(delayObj);
        this.onComponentUpdated.notify();
    }

    //Signal logic
    addSignal(infoObjSignal){
        this.component.addSignal(infoObjSignal);
        this.onComponentUpdated.notify();
    }

    deleteSignalByNumber(number){
        this.component.deleteSignalByNumber(number);
        this.onComponentUpdated.notify();
    }

    deleteSignal(signal){
        this.component.deleteSignal(signal);
        this.onComponentUpdated.notify();
    }

    //Port logic
    addPort(elementNum, portInfoObj){
        let element = this.component.getElementByNumber(elementNum);
        element.addPort($.extend(true, {}, portInfoObj));
        this.onComponentUpdated.notify();
    }

    deletePort(elemNum, portNum){
        let element = this.component.getElementByNumber(elementNum);
        element.deletePort(portNum);
        this.onComponentUpdated.notify();
    }

    addGenerator(){
        this.component.addGenerator();
        this.onComponentUpdated.notify(); 
    }

    updateGen(genObj){
        this.component.updateGen(genObj);
        this.onComponentUpdated.notify();
    }
}