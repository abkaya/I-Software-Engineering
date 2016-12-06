/**
 * Created by abdil on 27/11/2016.
 */

console.log(deviceNames);
console.log(allErrorRates);

$(function () {
    Highcharts.chart('container', {

        chart: {
            type: 'boxplot'
        },

        title: {
            text: 'All tested devices : error rate'
        },

        legend: {
            enabled: true
        },

        xAxis: {
            //Devices with results need to be populated from a query which gets appropriate difficulty data.
            categories: deviceNames,
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
            data: allErrorRates,
            tooltip: {
                headerFormat: '<em>{point.key}</em><br/>'
            }
        }]


    });
});