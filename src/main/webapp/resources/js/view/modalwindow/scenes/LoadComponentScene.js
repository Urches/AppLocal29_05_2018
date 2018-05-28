class LoadComponentScene {
    constructor(controller, modalWindow, callback){
        this.controller = controller;
        this.callback = callback;
        this.modalWindow = modalWindow;
        this.templates = document.querySelector('.templates');
        this.container = document.querySelector('.load-component-scene').cloneNode(true);
        this.loaderHtml = this.container.querySelector('.loader');
        this.componentsGridHtml = this.container.querySelector('.components-grid');
        this.loaderHtml.style.display = 'flex';
        this.componentsGridHtml.style.display = 'none';
    }

    init(){
        this.controller.getComponents(this.displayComponents.bind(this));
    }

    displayComponents(msg){
        let componentList;
            componentList = JSON.parse(msg);
            if(componentList && componentList.length > 0) {
                componentList.forEach(component => {
                    console.log(component);
                    let div = document.createElement('div');
                    div.className = 'component-container-item';

                    let numLabel = document.createElement('label');
                    numLabel.innerText += ' ' + component.number;

                    let descLabel = document.createElement('label');
                    descLabel.innerText += (component.description) ? ' ' + component.description : " без описания";

                    div.appendChild(numLabel);
                    div.appendChild(descLabel);

                    div.onclick = () => {
                        this.controller.getComponentAjax(component.number, this.callback.bind(this.controller.component));
                    };

                    this.componentsGridHtml.appendChild(div);
                });
            } else {
                this.componentsGridHtml.innerHTML += "Компонентов нет";
            }

        this.loaderHtml.style.display = 'none';
        this.componentsGridHtml.style.display = 'flex';
    }


    close(){
        console.log('close');
        this.modalWindow.close();
    }

    cancel(){
        console.log('cancel');
    }

    save(){
        console.log('save');
    }
}