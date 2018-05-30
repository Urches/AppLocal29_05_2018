class Page {
    /**
     * @param {class Controller} controller
     */
    constructor(controller) {
        this.controller = controller;
        this.modalWindow = new ModalWindow();
        this.componentView = new ComponentView(this.controller, this);
        this.chartPropertiesView = new ChartPropertiesView(this.controller, this);
        this.chartView = new ChartView(this.controller);
        this.runView = new RunView(this.controller);


        let self = this;
        document.querySelector('.chart.menu-item').onclick = () => {
            this.componentView.closePropertiesView();
            this.chartPropertiesView.show();
        };

        document.querySelector('.configure-component.menu-item').onclick = function(){
            self.chartView.close();
            self.runView.close();
            self.componentView.show();
        };

        document.querySelector('.load-component.menu-item').onclick = () => {
            this.showLoadComponent();
        };

        document.querySelector('.new-component.menu-item').onclick = () => {
            if(this.controller.component){
                if(this.saveDialog()){
                    this.controller.createNewComponent();
                    this.chartView.close();
                    this.runView.close();
                    this.componentView.show();
                }
            } else {
                this.controller.createNewComponent();
                this.chartView.close();
                this.runView.close();
                this.componentView.show();
            }
        };

        document.querySelector('.structure-load.menu-item').onclick = () => {
            this.controller.loadStructure();
        };

        document.querySelector('.save-component.menu-item').onclick = () => {
            this.modalWindow.setScene(new SaveComponentScene(this.controller));
            this.modalWindow.show();
        };
    }

    showLoadComponent(){
        this.modalWindow.setScene(new LoadComponentScene(this.controller, this.modalWindow, (arg) => {
            this.controller.initFrontComponent(arg);
            this.modalWindow.close();
        }));
        this.modalWindow.show();
    }

    showLoadForeignComponent(){
        this.modalWindow.setScene(new LoadComponentScene(this.controller, this.modalWindow,  (arg) => {
            this.controller.addForeingComponent(arg);
            this.modalWindow.close();
        }));
        this.modalWindow.show();
    }

    showAlert(message){
        this.modalWindow.setScene(new Alert(this.modalWindow, message));
        this.modalWindow.show();
    }

    showChart() {
        this.componentView.close();
        this.runView.close();
        this.chartView.show();
    }

    saveDialog(){
        let result = confirm('Совершённый изменеия будут потеряны! Хотите сохранить структуру ?');
        if(result){
            this.modalWindow.setScene(new SaveComponentScene(this.controller));
            this.modalWindow.show();
        }
        return result;
    }
}

class SaveComponentScene {
    constructor(controller){
        this.controller = controller;
        this.container = document.querySelector('.save-component-scene').cloneNode(true);
        this.descriptionHtml = this.container.querySelector('.description-field');
        this.component = this.controller.component;
    }

    init(){
        this.descriptionHtml.value = this.component.description;
    }


    close(){
        console.log('close');
    }

    cancel(){
        console.log('cancel');
    }

    save(){
        console.log('save');
        this.component.description = this.descriptionHtml.value;
        this.controller.saveComponent(this.component);
    }
}

class Alert{
    constructor(modalWindow, message) {
        this.modalWindow = modalWindow;
        this.message = message;
        console.log(this.message);
        this.container = null;
        this.isShowed = false;
        this.container = document.querySelector('.alert-container').cloneNode(true);
        //smell!
        this.modalWindow.modal.querySelector(".saveBtn").style.display = 'none';
        this.modalWindow.modal.querySelector(".cancelBtn").value = "OK";
    }

    init(){
        this.container.querySelector('.alert-message').innerText = this.message;
    }

    close(){
        console.log('close');
    }

    cancel(){
        console.log('cancel');
    }

    save(){
        console.log('save');
    }
}