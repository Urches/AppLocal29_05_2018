class SignalPropertiesView {
    constructor(componentView){
        this.componentView = componentView;
        this.signal = null;
        this.buttonBar = null;
        this.isShowed = false;
    }

    setSignal(signal){
        this.signal = signal;
    }

    close(){
        if(this.isShowed){
            this.componentView.componentContainer.removeChild(this.buttonBar);
            this.isShowed = false;
        }
    }

    show(xAxis, yAxis){
        this.buttonBar = this._createButtonBar();
        this.buttonBar.style.left = xAxis + 5;
        this.buttonBar.style.top = yAxis + 5;
        this.componentView.componentContainer.appendChild(this.buttonBar);
        this.isShowed = true;
    }

    _createButtonBar(){
        let div = document.createElement('div');
        div.className = 'signal-button-bar';

        let deleteButton = document.createElement('input');
        deleteButton.setAttribute('type', 'button');
        deleteButton.setAttribute('value', 'delete');

        let self = this;
        deleteButton.onclick = function(){
            console.log('deleted!');
            console.log(self.signal);
            self.componentView.controller.deleteSignal(self.signal);
        };

        div.appendChild(deleteButton);
        return div;
    }
}