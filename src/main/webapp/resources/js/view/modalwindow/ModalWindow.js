class ModalWindow {
    constructor(){
        this.modal = document.querySelector(".modal-window");
        this.scene = null;
        this.onClose = new EventEmitter();

        this.modal.querySelector(".close").onclick = () => {
            this.scene.close();
            this.close();
        };

        this.modal.querySelector(".saveBtn").onclick = () => {
            this.scene.save();
            this.close();
        };

        this.modal.querySelector(".cancelBtn").onclick = () => {
            this.scene.cancel();
            this.close();
        };
    }

    setScene(scene){
        this.scene = scene;
        this.modal.querySelector('.scene-container').appendChild(this.scene.container);
    }

    show() {
        this.modal.style.display = "inline";
        this.scene.init();
    }

    close() {
        if(this.scene.container){
           this.modal.querySelector('.scene-container').removeChild(this.scene.container);
        }
        this.modal.style.display = "none";
        this.onClose.notify();
    }
}
