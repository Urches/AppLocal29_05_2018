class ForeignComponentView {
    constructor(componentView) {
        this.componentView = componentView;
        this.container = null;
        this.isShowed = false;
        this.foreingComponent = null;
    }


    setForeignComponent(foreingComponent) {
        this.foreingComponent = foreingComponent;
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
            this.foreingComponent = null;
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
        let htmlContainer = document.querySelector('.foreign-component-view').cloneNode(true);
        htmlContainer.querySelector('.foreign-component-number').innerText += this.foreingComponent.number;
        htmlContainer.querySelector('.foreign-component-description').innerText += this.foreingComponent.description;

        htmlContainer.querySelector('.upload-component-btn').onclick = () => {
            this.componentView.controller.getComponentAjax(this.foreingComponent.component.number,
                (obj) => {
                    let result = this.componentView.controller.page.saveDialog();
                    if(result){
                        this.componentView.controller.initFrontComponent(obj);
                        this.close();
                    }
                }
            );
        };

        this.foreingComponent.getPorts().forEach(port => {
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
            this.update();
        };

        return htmlPortContainer;
    }
}