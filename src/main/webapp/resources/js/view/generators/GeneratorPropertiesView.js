class GeneratorPropertiesView {
    constructor(componentView){
        this.componentView = componentView;
        this.currrentStage = null;
        this.container = null;
        this.generator = null;
        this.isShowed = false;
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
            this.generator = null;
            this.currrentStage = null;
            this.genType = null;
            this.signalType = null;
        }
    }

    update() {
        if (this.isShowed) {
            document.querySelector('.left-side').removeChild(this.container);
            this.container = this._containerGenerate();
            document.querySelector('.left-side').appendChild(this.container);
        } else this.container = this._containerGenerate();
    }

    setGenerator(generator) {
        this.generator = generator;
    }

    _containerGenerate(){
        let container = document.querySelector('.generator-container').cloneNode(true);
        //let selectedOption;
        let options = container.querySelectorAll('.generator-select .generators-type option');
        container.querySelector('.generator-name').innerText += this.generator.number;
        if(!this.genType) this.genType = this.generator.properties.type;
        if(!this.signalType) this.signalType = this.generator.properties.signalType;
        let genType = this.genType;
        let signalType = this.signalType;
        if(genType == 'asymmetrical'){
            this.setStage(new AsymmetricalGeneratorView(this, this.generator));
            container.querySelector('.generator-impl-container').appendChild(this.currrentStage.getContainer());
        } else if(genType == 'static'){
            this.setStage(new StaticGeneratorView(this, this.generator));
            container.querySelector('.generator-impl-container').appendChild(this.currrentStage.getContainer());
        } else if(genType == 'frequency') {
            container.querySelector('.generator-impl-container').appendChild(this._frequencyConatinerGenerate());
        }
        let genNameRu = this.componentView.properties.generatorsTypes.get(genType);
        console.log(genNameRu);
        Array.from(options).forEach(option => {
            if(option.innerText == genNameRu){
                option.setAttribute('selected','selected');
            }
        });
        container.querySelector('select').onchange = (event) => {
            let genTypeRu = Array.from(options).find(option => option.selected == true).innerText;
            console.log(genTypeRu);
            this.genType  = this.componentView.properties.getKeyByValue(this.componentView.properties.generatorsTypes, genTypeRu);
            console.log(this.genType);
            this.update();
        };

        let signalOptions = container.querySelectorAll('.generator-select .signals-type option');
        let signNameRu = this.componentView.properties.signalTypes.get(signalType);
        console.log(signNameRu);
        Array.from(signalOptions).forEach(option => {
            if(option.innerText == signNameRu){
                option.setAttribute('selected','selected');
            }
        });

        container.querySelector('.ok-button').onclick = (event) => {
            this.save();
        };

        container.querySelector('.cancel-button').onclick = (event) => {
            this.close();
        };

        return container;
    }

    setStage(stage) {
            this.currrentStage = stage;
    }

    save() {
        let genObj = this._getGenObject();
        this.componentView.controller.updateGen(genObj);
        this.update();
    }

    _asymmetricalConatinerGenerate(){
        let asymmetricalGen = document.querySelector('.asymmetrical-generator-container.container').cloneNode(true);
        asymmetricalGen.querySelector('append-button').onclick = () => {

        };
        return asymmetricalGen;
    }

    _frequencyConatinerGenerate(){
        let freqContainer = document.querySelector('.frequency-generator-container').cloneNode(true);
        return freqContainer;
    }

    _getGenObject(){
        let obj = Object.assign({}, this.generator);
        obj = Object.assign(obj, this.currrentStage.getGenObj());

        if(!obj.properties){
            obj.properties = {};
        }
        let options = this.container.querySelectorAll('.generator-select .generators-type option');
        let signalOptions = this.container.querySelectorAll('.generator-select .signals-type option');

        let option = Array.from(options).find(option => option.selected == true);
        let signalOption = Array.from(signalOptions).find(option => option.selected == true);
        console.log(option.innerText);
        obj.properties.type = this.componentView.properties.getKeyByValue(this.componentView.properties.generatorsTypes, option.innerText);
        obj.properties.signalType = this.componentView.properties.getKeyByValue(this.componentView.properties.signalTypes, signalOption.innerText);
        return obj;
    }
}