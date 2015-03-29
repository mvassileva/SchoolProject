// This file is not used anymore, but I am not deleting it yet due to a block
// of code that has not been ported over into the new format yet.

// var libraryServices = angular.module('libraryServices', ['ngResource']);
//
// /*libraryServices.factory('Book', function($resource){
//     return $resource('/libary/api/book');
// });*/
//
// libraryServices.factory('Book', ['$resource',
//     function ($resource) {
//         'use strict';
//
//         return $resource('/library/api/book', {}, {
//             query: {
//                 method: 'GET',
//                 isArray: true,
//                 headers: {Accept: 'application/json'}
//             },
//             add: {
//                 method: 'POST',
//                 isArray: false,
//                 headers: {Accept: 'application/json'}
//             }
//         });
//     }
// ]);
//
// libraryServices.factory('Book', ['$resource',
//     function ($resource) {
//         'use strict';
//
//         return $resource('/library/api/book/:id', {}, {
//             get: {
//                     method: 'GET',
//                     isArray: false,
//                     headers: {Accept: 'application/json'}
//             },
//             update: {
//                     method: 'PUT',
//                     isArray: false,
//                     headers: {Accept: 'application/json'}
//             },
//             remove: {
//                     method: 'DELETE',
//                     isArray: false,
//                     headers: {Accept: 'application/json'}
//             }
//         });
//     }
// ]);
//
//
// libraryServices.factory('Author', ['$resource',
//     function ($resource) {
//         'use strict';
//
//         return $resource('/library/api/author', {}, {
//             query: {
//                     method: 'GET',
//                     isArray: true,
//                     headers: {Accept: 'application/json'}
//             },
//             add: {
//                     method: 'POST',
//                     isArray: false,
//                     headers: {Accept: 'application/json'}
//             }
//         });
//     }
// ]);
//
//
// /*libraryInterceptor = {
//         responseError : function(rejection) {
//             alert("Unable to complete Request" + rejection.status);
//         }
// }
//
// libraryServices.factory('Book', ['$resource'],
//     function($resource) {
//         return $resource('/library/api/book/list/', {}, {
//             query: {
//                     method: "GET",
//                     isArray: true,
//                     headers: { 'Accept' : 'application/json' },
//                     interceptor: libaryInterceptor
//             },
//             add: {
//                     method: "POST",
//                     url: '/library/api/book/',
//                     isArray: false,
//                     headers: { 'Accept' : 'application/json' },
//                     interceptor: libaryInterceptor
//             },
//             remove: {
//                     method: "DELETE",
//                     url: '/library/api/book/:bookId',
//                     isArray: false,
//                     headers: { 'Accept' : 'application/json' },
//                     interceptor: libaryInterceptor
//             },
//             update: {
//                     method: "PUT",
//                     url: '/library/api/book/:bookId',
//                     isArray: false,
//                     headers: { 'Accept' : 'application/json' },
//                     interceptor: libaryInterceptor
//             }
//         })
//     }) */
