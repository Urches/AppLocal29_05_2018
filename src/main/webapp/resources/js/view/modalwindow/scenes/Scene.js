function Scene(controller) {   
    this.controller = controller;
    this.onClose = new EventEmitter();
}

//abstract
Scene.prototype.clean = function () {

};

//abstract
Scene.prototype.init = function (){

};

Scene.prototype.close = function(){
    this.clean();
    this.onClose.notify();
};

Scene.prototype.save = function(){
    this.clean();
};
