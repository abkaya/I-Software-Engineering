/**
 * Created by abdil on 27/11/2016.
 */
$(function () {
    Highcharts.chart('container', {

        chart: {
            type: 'boxplot'
        },

        title: {
            text: 'This is some difficulty range : e.g. 0-2 : easy'
        },

        legend: {
            enabled: true
        },

        xAxis: {
            //Devices with results need to be populated from a query which gets appropriate difficulty data.
            categories: ['Device1', 'Device2', 'Device3', 'Device4', 'Device5'],
            title: {
                text: 'Device'
            }
        },

        yAxis: {
            title: {
                text: 'Error rate (%)'
            }
        },

        series: [{
            name: 'Device',
            //These values will also need to be populated using test results for each device, given certain difficulties.
            data: [
                [760, 801, 848, 895, 965],
                [733, 853, 939, 980, 1080],
                [714, 762, 817, 870, 918],
                [724, 802, 806, 871, 950],
                [834, 836, 864, 882, 910]
            ],
            tooltip: {
                headerFormat: '<em>{point.key}</em><br/>'
            }
        }]

    });
});