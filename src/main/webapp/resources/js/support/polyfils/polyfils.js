if (!Array.prototype.find) {
  Array.prototype.find = function (predicate) {
    if (this == null) {
      throw new TypeError('Array.prototype.find called on null or undefined');
    }
    if (typeof predicate !== 'function') {
      throw new TypeError('predicate must be a function');
    }
    var list = Object(this);
    var length = list.length >>> 0;
    var thisArg = arguments[1];
    var value;

    for (var i = 0; i < length; i++) {
      value = list[i];
      if (predicate.call(thisArg, value, i, list)) {
        return value;
      }
    }
    return undefined;
  };
}

if (!String.prototype.includes) {
  String.prototype.includes = function () {
    'use strict';
    return !!~String.prototype.indexOf.apply(this, arguments);
  };
}

if (!Array.prototype.diff) {
  Array.prototype.diff = function (a) {
    return this.filter(function (i) {
      return a.indexOf(i) < 0;
    });
  };
}

if(!Array.prototype.indexOf) {
  Array.prototype.indexOf = function(what, i) {
      i = i || 0;
      var L = this.length;
      while (i < L) {
          if(this[i] === what) return i;
          ++i;
      }
      return -1;
  };
}