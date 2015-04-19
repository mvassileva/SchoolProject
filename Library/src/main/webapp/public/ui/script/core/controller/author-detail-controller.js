(function () {
    'use strict';

    angular.module('app.core').controller('AuthorDetailController', AuthorDetailController);

    AuthorDetailController.$inject = ['$scope', '$routeParams', 'AuthorDetailService'];

    function AuthorDetailController($scope, $routeParams, AuthorDetailService) {

        AuthorDetailService.get({id: $routeParams.authorId}, function (data) {
            $scope.author = data;
        });

        $scope.submitAuthor = function submitAuthor() {
            var authorDAO = new AuthorDetailService($scope.author);
            authorDAO.$update({id: $scope.author.id});
        };
    }
}());
