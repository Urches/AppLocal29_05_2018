class RunView {
    /**
     * @param {Controller} controller
     */
    constructor(controller) {
        this.controller = controller;
        this.component = null;
    }

    show() {
        document.querySelector('.run-container').style.display = 'flex';
        this.component = this.controller.getComponent();
    }

    close() {
        document.querySelector('.run-container').style.display = 'none';
    }

    createObservedPortsBlock(){
        let container = document.createElement('div');
    }

    createObservdNeuronBlock(neuron){
        let container = document.createElement('div');
        container.innerHTML += '<span>' + neuron.number + '</span>';
        
        let ports = neuron.getPorts();
        ports.forEach(port => {
            
        });

    }

    generateSelectHtml(names, chooseRool) {
        let select = document.createElement('select');

        names.forEach(name => {
            let option = document.createElement('option');
            option.innerText += name;
            if (chooseRool(name)) {
                option.setAttribute('selected', 'selected');
            }
            select.appendChild(option);
        });
        return select;
    }
}