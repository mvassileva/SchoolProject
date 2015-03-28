(function () {
    'use strict';

    angular.module('app.core').config(config);

    function config($routeProvider) {
        $routeProvider.
            when('/books', {
                templateUrl: 'partials/book-list.html',
                controller: 'BookController'
            }).
            when('/book/:bookId', {
                templateUrl: 'partials/book-detail.html',
                controller: 'BookDetailController'
            }).
            when('/books/new', {
                templateUrl: 'partials/book-new.html',
                controller: 'BookController'
            }).
            otherwise({
                redirectTo: '/books'
            });
    }
}());
