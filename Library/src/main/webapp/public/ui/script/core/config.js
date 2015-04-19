(function () {
    'use strict';

    var checkRouting = function ($q, $rootScope, $location) {
        if ($rootScope.isAuthenticated && $rootScope.userName) {
            return true;
        } else {
            $location.path('/login');
        }
    };

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
                templateUrl: 'partials/book-detail.html',
                resolve: {
                    factory: checkRouting
                }
            }).
            when('/login', {
                controller: 'LoginController',
                templateUrl: 'partials/login.html'
            }).
            when('/logout', {
                controller: 'LogoutController',
                templateUrl: 'partials/login.html'
            }).
            when('/author', {
                controller: 'AuthorController',
                templateUrl: 'partials/author-list.html'
            }).
            when('/author/new', {
                controller: 'AuthorController',
                templateUrl: 'partials/author-detail.html',
                resolve: {
                    factory: checkRouting
                }
            }).
            when('/author/:authorId', {
                controller: 'AuthorDetailController',
                templateUrl: 'partials/author-detail.html'
            }).
            when('/user/:userId', {
                controller: 'UserDetailController',
                templateUrl: 'partials/user-detail.html',
                resolve: {
                    factory: checkRouting
                }
            }).
            when('/users', {
                controller: 'UserController',
                templateUrl: 'partials/user-list.html',
                resolve: {
                    factory: checkRouting
                }
            }).
            when('/users/new', {
                controller: 'UserController',
                templateUrl: 'partials/user-detail.html',
                resolve: {
                    factory: checkRouting
                }
            }).
            otherwise({
                redirectTo: '/books'
            });
    }
}());
