(function () {
    'use strict';

    angular
        .module('app.core')
        .controller('PageController', PageController);

    PageController.$inject = ['$scope'];


    function PageController($scope) {
        $scope.foo = 'bar';
    }
}());
