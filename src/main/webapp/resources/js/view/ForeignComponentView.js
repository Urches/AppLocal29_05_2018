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
        return htmlContainer;
    }
}