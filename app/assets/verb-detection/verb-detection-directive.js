function verbDetection () { 
    return {
        restrict: "E",
        replace: true,
        templateUrl: "verb-detection/verb-detection.html"
    }; 
}

// Add directive to the app
angular 
    .module(mainAppName)
    .directive("verbDetection",verbDetection);