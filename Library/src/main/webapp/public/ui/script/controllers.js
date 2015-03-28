var libraryAppControllers = angular.module('libraryAppControllers', []);


libraryAppControllers.controller('BookCtrl', function ($scope, Book, Author) {
        'use strict';

        $scope.books = Book.query();

        $scope.bookData = {};
        $scope.newBook = function () {
            var book = new Book($scope.bookData);
            book.$save();
        };

        $scope.authors = Author.query();
        $scope.orderProp = 'title';
    },
    function ($scope, Book) {
        'use strict';

        $scope.books = Book.query();

        $scope.bookData = {};
        $scope.newBook = function () {
            var book = new Book($scope.bookData);
            book.$save();
        };

        $scope.orderProp = 'title';
    }
);


libraryAppControllers.controller('BookDetailCtrl', function ($scope, $routeParams, Book) {
    'use strict';

    Book.get({id: $routeParams.bookId}, function (data) {
        $scope.book = data;
    });
});

libraryAppControllers.controller('AuthorCtrl', function ($scope, Author) {
    'use strict';

    $scope.authors = Author.query();

    $scope.authorData = {};
    $scope.newAuthor = function () {
        var author = new Author($scope.authorData);
        author.$save();
    };

    $scope.orderProp = 'lastName';

    $scope.author = $scope.authors[1];
});


/*
libraryAppControllers.controller('BookListCtrl', function($scope, $http) {
        $http.get('/library/api/book/list/').
        success(function(data) {
            $scope.books = data;
        });

        $scope.orderProp = 'title';
    }
);

libraryAppControllers.controller('BookDetailCtrl', ['$scope', '$routeParams', '$http' ,
  function($scope, $routeParams, $http) {
       $http.get('/library/api/book/' + $routeParams.bookId).success(function(data) {
      $scope.book = data;
       })
  }]);*/
