    class ElementHtmlRender {
        constructor() {
            this.htmlView = null;
        }
        
        setElement(element){

        }

        /**
         * Generate new html view
         */
        generateHtml() {
            throw new Exception('Non implemented method!');
        };

        /** 
         * Return before generate html view
         */
        getHtml() {
            return this.htmlView;
        };
    }