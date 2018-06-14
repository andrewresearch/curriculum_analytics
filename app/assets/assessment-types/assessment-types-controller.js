/**
 * Created by andrew on 10/01/15.
 */

function assessmentTypesCtrl($scope, $http) {

    $scope.assessTypes;
    $scope.heatmapData;

    $scope.loadAssessTypes = function() {
        var httpRequest = $http({
            method: 'GET',
            url: fullUrl('assessment/types'),
            data: null

        }).success(function(data, status) {
            $scope.assessTypes = data;
        });

    };

    $scope.loadHeatmapData = function(year) {
       var httpRequest = $http({
            method: 'GET',
            url: fullUrl('assessment/typeTopics/'+year),
            data: null

        }).success(function(data, status) {
            $scope.heatmapData = data;
        });
        //$scope.heatmapData = [[10,5,1,7],[2,9,3,4],[4,0,6,2],[0,1,5,8],[2,6,8,4],[2,9,3,4]];

    };



}

angular
    .module(mainAppName)
    .controller('assessmentTypesCtrl', assessmentTypesCtrl)