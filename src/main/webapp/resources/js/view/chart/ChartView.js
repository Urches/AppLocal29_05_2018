class ChartView {
    /**
     * @param {Controller} controller
     */
    constructor(controller) {
        this.controller = controller;
        //this.component = null;
        this.container = document.querySelector('.chart-container');

        this.controller.onDiagramLoad.subscribe((diagramObj) => {
            this.hideLoader();
            if(diagramObj.diagram.length > 0){
                this.drawDiagram(diagramObj);
            } else {
                this.controller.page.showAlert("Диаграмма пуста!");
            }

        });

        this.controller.onDiagramCancel.subscribe(message => {
            this.hideLoader();
            let label = document.querySelector('.diagram-warrings');
            label.innerText = message;
            label.style.display = 'flex';
        });
    }

    show() {

        document.querySelector('.chart-container').style.display = 'flex';
        this.showLoader();
        this.createSignalPropertiesHtml();
    }

    clean(){
        this.container.querySelector('.diagram-warrings').style.display = 'none';
        HtmlUtiles.prototype.clean(this.container.querySelector('.lines-container'));
    }

    close() {
        this.clean();
        document.querySelector('.chart-container').style.display = 'none';
    }

    //Empty port list
    createSignalPropertiesHtml() {
        let signalPropertiesHtml = document.createElement('div');
        signalPropertiesHtml.className = 'port-properties-container';

        let span = document.createElement('span');
        span.innerText = 'Free in ports list';

        signalPropertiesHtml.appendChild(span);
    }

    showLoader(){
        this.container.querySelector('.loader-container').style.display = 'flex';
    }

    hideLoader(){
        this.container.querySelector('.loader-container').style.display = 'none';
    }

    drawDiagram(diagramObj) {
        let linesHtml = this.container.querySelector('.lines-container');
        diagramObj.diagram.forEach((diagramLine, index) => {
            let chartContainer = document.createElement('div');
            chartContainer.className = 'line' + index;
            linesHtml.appendChild(chartContainer);
            diagramLine.values.unshift(diagramLine.title);
            diagramLine.time.unshift('time');
            let chart = c3.generate({
                bindto: chartContainer,
                size: {
                    height: 200,
                    width: 700
                },
                data: {
                    x: 'time',
                    columns: [
                        diagramLine.time,
                        diagramLine.values
                    ],
                },
                grid: {
                    x: {
                        show: true
                    },
                    y: {
                        show: true
                    }
                }
            });
        });

    }
}