var libraryApp = angular.module('libraryApp', ['ngRoute', 'libraryAppControllers', 'libraryServices']);

libraryApp.config(['$routeProvider',
    function ($routeProvider) {
        'use strict';
        $routeProvider.
            when('/books', {
              templateUrl: 'partials/book-list.html',
              controller: 'BookCtrl'
            }).
            when('/book/:bookId', {
              templateUrl: 'partials/book-detail.html',
              controller: 'BookDetailCtrl'
            }).
            when('/books/new', {
              templateUrl: 'partials/book-new.html',
              controller: 'BookCtrl'
            }).
            otherwise({
              redirectTo: '/books'
            });
    }
]);
