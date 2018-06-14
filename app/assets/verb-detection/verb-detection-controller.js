function verbDetectionControl ($scope,$http) { 
    $scope.coltitle1 = "Load document";
    $scope.coltitle2 = "Document Text";
    $scope.coltitle3 = "Document Graph";
    $scope.coltitle4 = "Word counts"
    $scope.fileUploadStatus = "Unknown";
    $scope.fileText = "No file loaded";
    $scope.fileAnalysis = "No file loaded";
    $scope.levels = [];

    $scope.pieChartData =  {
        no: 3,
        data: 3,
        available: 3
    };
    
    $scope.changeStatus = function(value) {
        console.log('changeStatus called with value: ',value);
        $scope.fileUploadStatus = value;
    };
    
    $scope.getFileText = function() {
        var httpRequest = $http({
            method: 'GET',
            url: 'fileText',
            data: null

        }).success(function(data, status) {
            $scope.fileText = data;
        });

    };

    $scope.selectedTax = "";
    $scope.getFileAnalysis = function() {
        var httpRequest = $http({
            method: 'POST',
            url: 'fileAnalysis',
            data: {"name":$scope.selectedTax}

        }).success(function(data, status) {
            $scope.fileAnalysis = data;
        });

    };

    $scope.taxonomies = ["Bloom's Taxonomy","Not Working Taxonomy"];

    $scope.getBloomsTotals = function() {
        var httpRequest = $http({
            method: 'GET',
            url: 'bloomsTotals',
            data: null

        }).success(function(data, status) {
            console.log("data: ",data);
            $scope.pieChartData = data;
            console.log("pieChartData: ", $scope.pieChartData);
        });

    };

    $scope.selectchangeTax = function(tax) {
        console.log("The selected Taxonomy is: "+tax);
        $scope.selectedTax = tax;
    }
}

angular 
    .module(mainAppName) 
    .controller('verbDetectionCtrl', verbDetectionControl)

