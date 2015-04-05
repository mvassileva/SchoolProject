(function () {
    'use strict';

    angular.module('app.core').config(config);

    /**
     * Route configuration
     *
     * @memberof module:core
     *
     * @param {object} $routeProvider
     * The route provider (ngRoute)
     */
    function config($routeProvider) {
        $routeProvider.
            when('/books', {
                controller: 'BookController',
                templateUrl: 'partials/book-list.html'
            }).
            when('/book/:bookId', {
                controller: 'BookDetailController',
                templateUrl: 'partials/book-detail.html'
            }).
            when('/books/new', {
                controller: 'BookController',
                templateUrl: 'partials/book-new.html'
            }).
            otherwise({
                redirectTo: '/books'
            });
    }
}());
