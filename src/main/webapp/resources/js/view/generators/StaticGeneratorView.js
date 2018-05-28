class StaticGeneratorView {
    constructor(genView, generator) {
        this.genView = genView;
        this.container = null;
        this.buf = Object.assign({}, generator);
    }

    getContainer() {
        this.container = document.querySelector('.static-generator-container').cloneNode(true);
        this.container.querySelector('.value').value = this.buf.properties.value || 0;
        return this.container;
    }

    getGenObj() {
        this.buf.properties.value = this.container.querySelector('.value').value;
        return this.buf;
    }
}
