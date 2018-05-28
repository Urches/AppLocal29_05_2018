class AsymmetricalGeneratorView {
    constructor(genView, generator) {
        this.genView = genView;
        this.container = null;
        this.buf = Object.assign({}, generator);
        this.buf.properties;
    }

    getContainer() {
        this.container = document.querySelector('.asymmetrical-generator-container').cloneNode(true);
        this.container.querySelector('.append-button').onclick = () => {
            let value = this._getValue();
            if (!this.buf.properties.values) {
                this.buf.properties.values = [];
            }
            this.buf.properties.values.push(value);
            this.genView.update();
        };
        if (this.buf.properties.values) {
            this.container.querySelector('.values').innerHTML +=
                this.buf.properties.values.map(v => `<span>Время от: ${v.fromTime} время до: ${v.toTime} значение: ${v.value}</span>`).join(' ');
        }
        return this.container;
    }

    _getValue() {
        return {
            fromTime: this.container.querySelector('.fromTime').value,
            toTime: this.container.querySelector('.toTime').value,
            value: this.container.querySelector('.value').value
        };
    }

    getGenObj() {
        return this.buf;
    }
}