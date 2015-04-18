(function () {
    'use strict';

    angular.module('app.core').controller('NavigationController', NavigationController);

    NavigationController.$inject = ['$scope', '$rootScope', '$location'];


    function NavigationController($scope, $rootScope, $location) {
        $scope.menuClass = function(page) {
        var current = $location.path().substring(1);
        return page === current ? "pure-menu-active" : "";
        };
    }
}());
