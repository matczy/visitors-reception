"use strict";
(function () {
    angular.module('visitorApp').controller('DashboardController', DashboardController);

    DashboardController.$inject = ['visitCardService','registerVisitorService','allVisits','todayVisits','visitorsOnObject','activeVisitCard','last30DaysVisitData','daysLabels','lastDayByHour','hourLabels'];
    function DashboardController(visitCardService,registerVisitorService,allVisits,todayVisits,visitorsOnObject, activeVisitCard,last30DaysVisitData,daysLabels, lastDayByHour,hourLabels) {
        var vm = this;
        angular.merge(vm, {


            model: {
                allVisits:allVisits,
                todayVisits:todayVisits,
                visitorsOnObject:visitorsOnObject,
                activeVisitCard:activeVisitCard,
                last30Days:{
                    sumVisits:0,
                    maxVisits:0,
                    averageVisits:0
                },
                chartLast30DaysVisit:{
                    labels: daysLabels,
                    series: ['Wejście'],
                    data: [],
                    colours:['#459045']
                },

                chartLastDayByHourVisit:{
                    labels: hourLabels,
                    series: ['Wejście'],
                    data: [],
                    colours:['#459045']
                }
            },


            service: {
                //registerExitAction: registerExitAction,
                //filterData: filterData
            }
        });

        function _fillChartLast30DaysVisit() {
            var data = [];

            daysLabels.forEach(function(){
                data.push(0);
            });
            last30DaysVisitData.forEach(function (elem) {
                var index = daysLabels.indexOf(elem.date);

                data[index]=elem.value;
            });
            vm.model.chartLast30DaysVisit.data.push(data);
        }

        function _fillChartLastDayByHourVisit() {
            var data = [];
            hourLabels.forEach(function(){
                data.push(0);
            });
            lastDayByHour.forEach(function (elem) {
                var index = hourLabels.indexOf(elem.date.substring(11,14)+":00");
                data[index]=elem.value;
            });
            vm.model.chartLastDayByHourVisit.data.push(data);
           
        }

        (function fillChartData(){
            _fillChartLast30DaysVisit();
            _fillChartLastDayByHourVisit();
        })();


    }

})();