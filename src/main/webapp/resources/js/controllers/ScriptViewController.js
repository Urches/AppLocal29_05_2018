class ScriptViewController{
    constructor(scriptViewHtml){
        this.scriptViewHtml = scriptViewHtml;
    }

    showJSONScript(component){
        if(component) {
            this.scriptViewHtml.innerText = JSON.stringify(component, null, 3);
        }
    }
}

