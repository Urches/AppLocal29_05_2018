class Test {
    constructor() {
        this.testComponentObject = {
                number: 1,
                description: 'Тестовый компонент',
                elements: [{
                    number: 2,
                    type: 'neuron',
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
                    activatedBlock: 'linear'
                },
                    {
                        number: 3,
                        type: 'neuron',
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
                        activatedBlock: 'sigmoid'
                    },
                    {
                        number: 4,
                        type: 'neuron',
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
                        activatedBlock: 'sigmoid'
                    },
                    {
                        number: 1,
                        type: 'generator',
                        properties: {
                            type: 'static',
                            value: 10
                        }
                    },
                    {
                        number: 5,
                        type: 'foreignComponent',
                        description: 'Загруженный компонент',
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
                        ]
                    }
                ],
                signals: [
                    {
                        fromElement: 2,
                        fromPort: 3,
                        toElement: 3,
                        toPort: 1,
                    }],
                coordinates: [{
                    number: 1,
                    xAxis: 600,
                    yAxis: 600
                },
                    {
                        number: 2,
                        xAxis: 400,
                        yAxis: 400
                    },
                    {
                        number: 3,
                        xAxis: 600,
                        yAxis: 400
                    },
                    {
                        number: 4,
                        xAxis: 500,
                        yAxis: 500
                    },
                    {
                        number: 5,
                        xAxis: 650,
                        yAxis: 450
                    }
                ]
            };
        this.jsonTestString = JSON.stringify(this.testComponentObject);

        this.testDiagramObj = {
        diagram: [
         {
             title: "NEURON: 3",
             type: "DIGITAL",
             time: [0, 10, 20],
             values: [ 50.0,50.0,50.0]
         },
            {
                title: "GENERATOR: 2",
                type: "DIGITAL",
                time: [0, 10, 20],
                values: [ 5.0,5.0,5.0]
            }

        ]};

        this.foreignCOmponentsTestObj = {
            components :[
                 {
                    number : 10001,
                    description: "Foreign test component 1"
                 },
                {
                    number : 10002,
                    description: "Foreign test component 2"
                }
            ]
        }
    }
}