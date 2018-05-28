class Properties {
    constructor() {
        this.defaultNeuronInfoObj = {
            type:'neuron',
            ports: [{
                    position: 'in',
                    type: 'digital',
                    number: 1
                },
                {
                    position: 'in',
                    type: 'logic',
                    number: 2
                },
                {
                    position: 'out',
                    type: 'digital',
                    number: 3
                }
            ],
            activatedBlock: 'linear',
            isNew: true
        };

        this.defaultPortObj = {
            position: 'in',
            type: 'digital',
        };

        this.defaultDelayObj = {
            type: 'delay',
            delayTime: 20,
            ports: [{
                position: 'in',
                type: 'digital',
                number: 1
            },
                {
                    position: 'out',
                    type: 'digital',
                    number: 2
                }
            ]
        };

        this.defaultGeneratorObj = {
            type: 'generator',
            static: {
                type: 'static',
                properties: {
                    type: 'static',
                    value: 0
                }
            }
        };

        this.chartProperties = {
            duration: 1000,
            step: 10,
            type: 'inherit'
        };

        this.signalTypes = new Map();
        this.signalTypes.set('digital', 'цифровой');
        this.signalTypes.set('logic', 'логический');
        this.signalTypes.set('frequency', 'частотный');
        this.signalTypes.set('time_interval', 'временной интервал');


        this.afTypes = new Map();
        this.afTypes.set('linear', 'линейная');
        this.afTypes.set('linear_bounder', 'линейно-ограниченная');
        this.afTypes.set('sigmoid', 'сигмоидальная');
        this.afTypes.set('threshold', 'пороговая');


        this.generatorsTypes = new Map();
        this.generatorsTypes.set('static', 'статический');
        this.generatorsTypes.set('asymmetrical', 'асимметричный');
        this.generatorsTypes.set('frequency', 'частотный');

        this.defaultNeuronStyle = {
            top: '0px',
            left: '0px',
            position: 'absolute'
        };
    }

    getKeyByValue(map, searchableValue){
        return Array.from(map.entries()).filter(value => value[1] == searchableValue)[0][0];
    }
}