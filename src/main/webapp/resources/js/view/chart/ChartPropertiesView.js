class ChartPropertiesView {
    constructor(controller, rootPage) {
        this.controller = controller;
        this.rootPage = rootPage;
        this.container = null;
        this.isShowed = false;
        this.buf = Object.assign({}, this.controller.chartProperties);
    }

    show() {
        this.buf = Object.assign({}, this.controller.chartProperties);
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

        if (this.isShowed) {
            document.querySelector('.left-side').removeChild(this.container);
            this.container = this._containerGenerate();
            document.querySelector('.left-side').appendChild(this.container);
        } else this.container = this._containerGenerate();

    }

    _containerGenerate() {
        let htmlContainer = document.querySelector('.chart-properties-container').cloneNode(true);
        htmlContainer.querySelector('.duration-value').value += this.buf.duration;
        htmlContainer.querySelector('.step-value').value = this.buf.step;

        htmlContainer.querySelector('.chart-properties-confirm').onclick = (e) => {
            this.buf.duration = htmlContainer.querySelector('.duration-value').value;
            this.buf.step = htmlContainer.querySelector('.step-value').value;
            this.controller.chartProperties = this.buf;
            this.controller.setChartProperties();
        };
        htmlContainer.querySelector('.chart-properties-rollback').onclick = (e) => {
            this.close()
        };

        return htmlContainer;
    }
}