/**
 * Created by andrew on 6/01/15.
 */

function MainCtrl ($scope) { 
    $scope.title = "QUT L&T Analytics";
} 

function MainTabs ($scope, $window,$sce) {
    //var content = $sce.trustAsHtml("<verb-detection />")
    $scope.tabs =[
    { title:'Assessment Terms', content:'<assessment-types />' },
    { title:'Verb Detection', content: '<verb-detection />'}
  ] ;
}

// Add controllers to the app
angular 
    .module(mainAppName) 
    .controller('MainCtrl', MainCtrl)
    .controller('MainTabs', MainTabs);
