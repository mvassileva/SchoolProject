(function () {
    'use strict';

    angular.module('app.core').directive('uiDate', function () {
        return {
            require: '?ngModel',
            link: function ($scope, element, attrs, controller) {
                var originalRender, updateModel, usersOnSelectHandler;

                if (typeof $scope.uiDate === 'undefined') {
                    $scope.uiDate = {};
                }

                if (typeof controller !== 'undefined') {
                    updateModel = function (value, picker) {
                        return $scope.$apply(function () {
                            return controller.$setViewValue(element.datepicker('getDate'));
                        });
                    };

                    if (typeof $scope.uiDate.onSelect !== 'undefined') {
                        usersOnSelectHandler = $scope.uiDate.onSelect;
                        $scope.uiDate.onSelect = function (value, picker) {
                            updateModel(value);
                            return usersOnSelectHandler(value, picker);
                        };
                    } else {
                        $scope.uiDate.onSelect = updateModel;
                    }

                    originalRender = controller.$render;

                    controller.$render = function () {
                        originalRender();
                        return element.datepicker('setDate', controller.$viewValue);
                    };
                }

                return element.datepicker($scope.uiDate);
            }
        };
    });
}());
