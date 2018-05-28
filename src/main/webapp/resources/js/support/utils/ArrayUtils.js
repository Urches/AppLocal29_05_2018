class ArrayUtils{
    constructor(){

    }
}

ArrayUtils.prototype.remove = function(arr, args) {
    let what, L = args.length, ax;
    while (L && arr.length) {
        what = args[--L];
        while ((ax = arr.indexOf(what)) !== -1) {
            arr.splice(ax, 1);
        }
    }
    return arr;
};