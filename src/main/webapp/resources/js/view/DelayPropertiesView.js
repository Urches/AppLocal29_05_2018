class  DelayPropertiesView {
    constructor(componentView){
        this.componentView = componentView;
        this.container = null;
        this.isShowed = false;
        this.buf = null;
    }


    setDelay(delay) {
        this.buf = Object.assign({}, delay);
        this.buf.__proto__ = Object.create(Delay.prototype);
    }

    getDelayInfoObj() {
        if (this.container) {
            let ports = Array.from(this.container.querySelectorAll('.ports-properties-block .port-container')).map(portContainer =>
                this._getPortInfoObj(portContainer));

            let number = parseInt(this.container.className);
            let delayTime = this.container.querySelector('.delayTime').value;
            return {
                number,
                ports,
                delayTime
            };
        } else {
            return this.componentView.properties.defaultDelayObj;
        }
    }

    _getPortInfoObj(portContainer) {
        let index = portContainer.querySelector('select').selectedIndex;
        let optionName = portContainer.querySelectorAll('select option')[index];
        console.log(optionName);
        let key = this.componentView.properties.getKeyByValue(this.componentView.properties.signalTypes, optionName);
        console.log(key);
        return {
            observed: portContainer.querySelector('.observed-checkbox').checked,
            number: parseInt(portContainer.className),
            type: key,
            position: portContainer.className.includes('out-port') ? 'out' : 'in'
        }
    }

    show() {
        this.container = this._containerGenerate();
        //change!
        document.querySelector('.left-side').appendChild(this.container);
        this.isShowed = true;
    }

    close() {
        if (this.container) {
            //change!
            document.querySelector('.left-side').removeChild(this.container);
            this.isShowed = false;
            this.container = null;
            this.buf = null;
        }
    }

    update() {
        //console.log(this.isShowed);
        if (this.isShowed) {
            document.querySelector('.left-side').removeChild(this.container);
            this.container = this._containerGenerate();
            document.querySelector('.left-side').appendChild(this.container);
        } else this.container = this._containerGenerate();

    }

    _containerGenerate() {
        let htmlContainer = document.querySelector('.delay-properties-container').cloneNode(true);
        htmlContainer.className = this.buf.number + '-delay-prop delay-properties-container container';
        htmlContainer.querySelector('.label').innerText += this.buf.number;
        htmlContainer.querySelector('.delayTime').value = this.buf.delayTime;

        htmlContainer.querySelector('.delay-properties-confirm').onclick = (e) => {
            this.componentView.controller.addOrUpdateDelay(this.buf.number, this.buf);
        };
        htmlContainer.querySelector('.delay-properties-rollback').onclick = (e) => {
            this.close()
        };

        this.buf.getPorts().forEach(port => {
            let htmlPortContainer = this._portGenerate(port);
            if (port.position.toUpperCase() == 'IN') {
                HtmlUtiles.prototype.insertAfter(htmlPortContainer, htmlContainer.querySelector('.in-port-label'));
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

        //set port type
        Array.from(htmlPortContainer.querySelector('.port-type-select').children).forEach(option => {
            if (option.innerText.toUpperCase().includes()) {
                option.setAttribute("select", "selected");
            }
        });

        if (port.observed)
            htmlPortContainer.querySelector('.observed-checkbox').checked = true;

        htmlPortContainer.querySelector('.observed-checkbox').onclick = (e) => {
            port.observed = htmlPortContainer.querySelector('.observed-checkbox').checked;
            console.log(this.buf);
            this.update();
        };

        return htmlPortContainer;
    }
}