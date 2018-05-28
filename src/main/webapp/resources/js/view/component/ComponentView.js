class ComponentView {
    constructor(controller, page) {
        this.componentContainer = document.querySelector('.workbench');
        this.controller = controller;
        this.component = null;
        this.coordinates = [];
        this.page = page;
        this.connectionInspector = new ConnectionInspector(this);
        this.neuronPropertiesView = new NeuronPropertiesView(this);
        this.signalPropertiesView = new SignalPropertiesView(this);
        this.delayPropertiesView = new DelayPropertiesView(this);
        this.generatorPropertiesView = new GeneratorPropertiesView(this);
        this.componentHtmlRender = new ComponentHtmlRender(this);
        this.foreignComponentView = new ForeignComponentView(this);
        this.properties = new Properties();
        this.targetElement = null;

        //remove or update!
        this.scriptViewController = new ScriptViewController(document.querySelector('.json-view-field'));

        //events handlers
        let self = this;

        //Drag and drop handlers
        DragManager.onDragCancel = function (dragObject, dropElem) {
            console.log('dragCancel!');
        };

        DragManager.onDragEnd = function (dragObject, dropElem) {
            dropElem.appendChild(dragObject.avatar);
            console.log('drag end!');
            //If exsist neuron has been dragged
            //update coordinates
            if (dragObject.avatar.className.includes('ported-element')) {
                let neuronHtml = dragObject.avatar;
                let data = self.componentHtmlRender.getElementTypeAndNumber(neuronHtml);
                self.putCoordinates(
                    data.number,
                    parseInt(neuronHtml.style.left),
                    parseInt(neuronHtml.style.top)
                );
                self.update();
            }
        };

        //Click handlers
        this.componentContainer.addEventListener('click', (e) => {

            if (e.target.className.includes('ported-element')) {
                if(!HtmlUtiles.prototype.isShadowSet(e.target))
                    HtmlUtiles.prototype.addShadowOnElement(e.target);

                let data = this.componentHtmlRender.getElementTypeAndNumber(e.target);
                if(!this.targetElement || this.targetElement.number != data.number){
                    //drop view select of prevent target element
                    if(this.targetElement && this.targetElement.html){
                        HtmlUtiles.prototype.dropShadowFromElement(this.targetElement.html);
                    }

                    //set new target element
                    this.targetElement = this.component.getElement(data);
                    this.targetElement.html = e.target;
                    HtmlUtiles.prototype.addShadowOnElement(this.targetElement.html);
                    this.showElementProperties(this.targetElement);
                }
            } else {
                if (this.targetElement) {
                    HtmlUtiles.prototype.dropShadowFromElement(this.targetElement.html);
                    //temp
                    //this.targetElement = null;
                }
            }

            //out port clicked
            if (e.target.className.includes('port-out')) {
                let data = self.componentHtmlRender.getElementTypeAndNumber(e.target);
                console.log(data);
                let portNum = self.componentHtmlRender.getPortNumber(e.target);
                self.connectionInspector.setTarget('OUT', data.number, portNum);
            }
            //in port clicked
            if (e.target.className.includes('port-in')) {
                let data = self.componentHtmlRender.getElementTypeAndNumber(e.target);
                console.log(data);
                let portNum = self.componentHtmlRender.getPortNumber(e.target);
                self.connectionInspector.setTarget('IN', data.number, portNum);
            }
            //signal clicked
            if (e.target.className.includes('-signal')) {
                console.log('signal clicked!');
                let number = parseInt(e.target.className);
                let signal = self.component.getSignalByNumber(number);
                self.signalPropertiesView.setSignal(signal);
                self.signalPropertiesView.show(event.clientX, event.clientY);
            } else {
                self.signalPropertiesView.close();
            }
        });

        //add neuron button clicked in button bar
        document.querySelector('.workbench-bar .neuron-add-button').addEventListener('click', function (e) {
            self.addNeuron();
            self.update();
        });

        //drop button clicked in button bar
        document.querySelector('.workbench-bar .element-drop-button').addEventListener('click', function (e) {
            
            console.log(self.targetElement);
            if(self.targetElement){
                let number = self.targetElement.number;
            //let type = self.componentHtmlRender.getElementType(self.targetElement);
            let coords = self.getCoordinates({number});
            console.log(coords);
            ArrayUtils.prototype.remove(self.component.coordinates, coords);
            self.controller.deleteElement(self.targetElement);
           }
            
        });

        //add generator clicked in button bar
        document.querySelector('.workbench-bar .generator-add-button').addEventListener('click', (e) => {
            console.log('add generator');
            this.addGenerator();
            this.update();
        });

        //add delay clicked in button bar
        document.querySelector('.workbench-bar .delay-add-button').addEventListener('click', (e) => {
            console.log('add generator');
            this.addDelay();
            this.update();
        });


        document.querySelector('.workbench-bar .component-add-button').onclick = () => {
            this.controller.page.showLoadForeignComponent();
        };

        //Controller handlers
        this.controller.onComponentUpdated.subscribe(() => {
            this.update();
        });

        this.controller.onComponentLoad.subscribe((component) => {
            this.component = component;
            console.log("update!");
            this.update();
        });

        //this.controller.loadComponent();
    }

    show() {
        document.querySelector('.workbench').style.display = 'flex';
    }

    close() {
        this.closePropertiesView();
        document.querySelector('.workbench').style.display = 'none';
    }

    getCoordinates(data){
        return this.component.coordinates.filter(coord => coord.number == data.number);
    }

    closePropertiesView(){
        if (this.neuronPropertiesView.isShowed) {
            this.neuronPropertiesView.close();
        }
        if (this.generatorPropertiesView.isShowed) {
            this.generatorPropertiesView.close();
        }
        if (this.foreignComponentView.isShowed) {
            this.foreignComponentView.close();
        }
        if (this.delayPropertiesView.isShowed) {
            this.delayPropertiesView.close();
        }
        if(this.page.chartPropertiesView.isShowed){
            this.page.chartPropertiesView.close();
        }
    }

    showElementProperties(element){
        this.closePropertiesView();

        if (element.type == 'neuron'){
            if(!this.neuronPropertiesView.isShowed){
                this.neuronPropertiesView.setNeuron($.extend(true, {}, element));
                this.neuronPropertiesView.show();
            }
        } else if(element.type == 'generator'){
            console.log('generator-clicked!');
            if(!this.generatorPropertiesView.isShowed){
                this.generatorPropertiesView.setGenerator(element);
                this.generatorPropertiesView.show();
            }
        } else if(element.type == 'foreignComponent') {
            console.log('foreign component!');
            if (!this.foreignComponentView.isShowed) {
                this.foreignComponentView.setForeignComponent(element);
                this.foreignComponentView.show();
            }
        } else if(element.type == 'delay') {
            console.log('delay!');
            if (!this.delayPropertiesView.isShowed) {
                this.delayPropertiesView.setDelay(element);
                this.delayPropertiesView.show();
            }
        } else console.log('unknown type!');
    }

    getChoosenElement(htmlContainer) {
        let number = this.componentHtmlRender.getElementNumber(htmlContainer);
        return this.component.getElementByNumber(number);
    }

    showElementPropertiesView(element) {
        if (this.neuronPropertiesView.isShowed) {
            this.neuronPropertiesView.close();
        }
        this.neuronPropertiesView.setNeuron(Object.assign({},element));
        this.neuronPropertiesView.show();
    }
    // Delete logic
    /** 
     * Delete port of selected neuron
     */
    deletePort(portNum, elemNum = this.componentHtmlRender.getElementNumber(this.targetElement)) {
        this.controller.deletePort(elemNum, portNum);
    }

    deleteSignal(number) {
        this.controller.deleteSignal(number);
    }

    addGenerator(coord = {
        xAxis: 350,
        yAxis: 150
    }){
       let number = this.component.addGenerator(this.properties.defaultGeneratorObj.static);
        this.putCoordinates(
            number,
            this.properties.defaultGeneratorObj.type,
            coord.xAxis,
            coord.yAxis
        );
    }

    addDelay(coord = {
        xAxis: 350,
        yAxis: 150
    }) {
        console.log('add delay!');
        let number = this.component.addDelay(this.properties.defaultDelayObj);
        this.putCoordinates(
            number,
            coord.xAxis,
            coord.yAxis
        );
    }

    //Add logic
    //rework!!!
    addNeuron(coord = {
        xAxis: 350,
        yAxis: 150
    }) {
        if(this.component){
            let number = this.component.addNeuron(Object.assign({}, this.properties.defaultNeuronInfoObj));
            this.putCoordinates(
                number,
                coord.xAxis,
                coord.yAxis
            );
        } else {
            this.componentUnSettedAlert();
        }

    }

    /**
     * 
     * @param {*} elementNum 
     */
    addPort(elementNum, portInfoObject) {
        this.controller.addPort(elementNum, portInfoObject)
    }

    addOrUpdateNeuron(elementNum, neuronInfoObject) {
        this.controller.addOrUpdateNeuron(elementNum, neuronInfoObject);
    }

    updateNeuron(elementNum, neuronInfoObject) {
        console.log(neuronInfoObject);
        this.controller.updateNeuron(elementNum, neuronInfoObject);
    }


    //Coordinates logic
    putCoordinatesObj(coordObj) {
        let coordinate;
        console.log(this.component);
        coordinate = this.component.coordinates.filter(coord => coord.number == coordObj.number)[0];
        if(coordinate){
            coordinate.xAxis = coordObj.xAxis;
            coordinate.yAxis = coordObj.yAxis;
        } else this.component.coordinates.push(coordObj);
    }

    putCoordinates(number, xAxis, yAxis) {
        this.putCoordinatesObj({
            number,
            xAxis,
            yAxis
        });
    }

    update() {
        let container = document.querySelector('.workflow');
        HtmlUtiles.prototype.clean(container);
        this.componentHtmlRender.setElement(this.component);
        this.scriptViewController.showJSONScript(this.component);
        this.componentHtmlRender.showHtml(container);
    }

    componentUnSettedAlert(){
        alert(" Модель преобразователя не установлена!\n Выбирите существующую модель или создайте новую!");
    }
}