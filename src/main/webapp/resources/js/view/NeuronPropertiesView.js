class NeuronPropertiesView {
    constructor(componentView) {
        this.componentView = componentView;
        this.runWorriesView = new RunWorriesView();
        this.container = null;
        this.isShowed = false;
        this.buf = null;
    }


    setNeuron(neuronInfoObj) {
        this.buf = $.extend(true, {}, neuronInfoObj);
        this.buf.__proto__ = Object.create(Neuron.prototype);
        this.runWorriesView.setComponent(this.componentView.component);
    }

    // getNeuronInfoObj() {
    //     if (this.container) {
    //         let index = this.container.querySelector('.af-properties-block select').selectedIndex;
    //         let optionName = this.container.querySelector('.af-properties-block select option')[index];
    //         console.log(optionName);
    //         let activatedBlock = this.componentView.properties.getKeyByValue(this.componentView.properties.afTypes, optionName);
    //         console.log(activatedBlock);
    //
    //         let ports = Array.from(this.container.querySelectorAll('.ports-properties-block .port-container')).map(portContainer =>
    //             this._getPortInfoObj(portContainer));
    //
    //         let number = parseInt(this.container.className);
    //
    //         return {
    //             number,
    //             ports,
    //             activatedBlock
    //         };
    //     } else {
    //         return Object.assign({}, this.componentView.properties.defaultNeuronInfoObj);
    //     }
    // }

    // _getPortInfoObj(portContainer) {
    //     let index = portContainer.querySelector('select').selectedIndex;
    //     let optionName = portContainer.querySelectorAll('select option')[index];
    //     console.log(optionName);
    //     let key = this.componentView.properties.getKeyByValue(this.componentView.properties.signalTypes, optionName);
    //     console.log(key);
    //
    //     return {
    //         observed: portContainer.querySelector('.observed-checkbox').checked,
    //         number: parseInt(portContainer.className),
    //         type: key,
    //         position: portContainer.className.includes('out-port') ? 'out' : 'in'
    //     }
    // }

    show() {
        this.container = this._containerGenerate();
        //change!
        document.querySelector('.left-side').appendChild(this.container);
        this.isShowed = true;

        this.runWorriesView.show();
    }

    close() {
        if (this.container) {
            //change!
            document.querySelector('.left-side').removeChild(this.container);
            this.isShowed = false;
            this.container = null;
            this.buf = null;
        }
        this.runWorriesView.close();
    }

    update() {
        if (this.isShowed) {
            document.querySelector('.left-side').removeChild(this.container);
            this.container = this._containerGenerate();
            document.querySelector('.left-side').appendChild(this.container);
        } else this.container = this._containerGenerate();

        this.runWorriesView.update();
    }

    _containerGenerate() {

        let htmlContainer = document.querySelector('.neuron-properties-container').cloneNode(true);
        htmlContainer.className = this.buf.number + '-neuron-prop neuron-properties-container container';
        htmlContainer.querySelector('.label').innerText += this.buf.number;
        //Set af type
        Array.from(htmlContainer.querySelector('.af-type-select').children).forEach((option, index) => {
            let typeKey = this.componentView.properties.getKeyByValue(this.componentView.properties.afTypes, option.innerText);

            if (typeKey.toUpperCase() == this.buf.activatedBlock.toUpperCase()) {
                htmlContainer.querySelector('.af-type-select').selectedIndex = index;
                option.setAttribute("select", "selected");
            }
        });

        htmlContainer.querySelector('.af-type-select').onchange = (e) => {
            let selectOption = Array.from(htmlContainer.querySelectorAll('.af-type-select option')).find(option => option.selected == true);
            console.log(selectOption.innerText);
            this.buf.activatedBlock = this.componentView.properties.getKeyByValue(this.componentView.properties.afTypes, selectOption.innerText);

            let type = this.componentView.properties.getKeyByValue(this.componentView.properties.afTypes, selectOption.innerText);
            switch (type){
                case 'linear' : break;
                case'sigmoid' : break;
                case 'threshold' : break;
                case 'linear_bounder' : break;
            }
        };


        htmlContainer.querySelector('.append-port-button').onclick = (e) => {
            let port = $.extend(true, {}, this.componentView.properties.defaultPortObj);
            this.buf.addPort(port);
            this.update();
        };
        htmlContainer.querySelector('.neuron-properties-confirm').onclick = (e) => {
            this.componentView.controller.addOrUpdateNeuron(this.buf.number, this.buf);
        };
        htmlContainer.querySelector('.neuron-properties-rollback').onclick = (e) => {
            this.close()
        };
        this.buf.getPorts().forEach(port => {
            let htmlPortContainer = this._portGenerate(port);
            if (port.position.toUpperCase() == 'IN') {
                HtmlUtiles.prototype.insertAfter(htmlPortContainer, htmlContainer.querySelector('.in-ports-label'));
            } else HtmlUtiles.prototype.insertAfter(htmlPortContainer, htmlContainer.querySelector('.out-port-label'));

        });
        return htmlContainer;
    }

    _portGenerate(port) {
        let htmlPortContainer = (port.position.toUpperCase() == 'IN') ?
            document.querySelector('.in-port-container').cloneNode(true) :
            document.querySelector('.out-port-container').cloneNode(true);

        htmlPortContainer.className = port.number + '-number port-container';
        if (port.position.toUpperCase() == 'OUT') htmlPortContainer.className += ' out-port';
        htmlPortContainer.querySelector('.port-number-label').innerText += port.number;

        let self = this;
        htmlPortContainer.querySelector('.port-type-select').onchange = function(){
            let options = this.querySelectorAll('option');
            let option = Array.from(options).find(option => option.selected == true);
            port.type = self.componentView.properties.getKeyByValue(self.componentView.properties.signalTypes, option.innerText);
            console.log(port.type);
        };

        //set port type
        let checkedOption = Array.from(htmlPortContainer.querySelector('.port-type-select').children)
            .find(option => this.componentView.properties.getKeyByValue(this.componentView.properties.signalTypes, option.innerText) == port.type);

        console.log(checkedOption);
        checkedOption.setAttribute('selected','selected');

        if (port.observed)
            htmlPortContainer.querySelector('.observed-checkbox').checked = true;

            htmlPortContainer.querySelector('.observed-checkbox').onclick = (e) => {
                port.observed = htmlPortContainer.querySelector('.observed-checkbox').checked;
                this.update();
            };


        if (htmlPortContainer.querySelector('.fa-times.close')) {
            htmlPortContainer.querySelector('.fa-times.close').onclick = (e) => {
                let number = parseInt(e.currentTarget.parentElement.className);
                this.buf.deletePort(number);
                console.log(this.buf);
                this.update();
            }
        }


        return htmlPortContainer;
    }
}