(function () {
    'use strict';

    /**
     * @module app
     *
     * @description
     * Manages the application
     *
     * @requires core
     */
    angular.module('app', [
        /* Angular modules */
        'ngRoute',
        'ngResource',

        /* Custom modules */
        'app.core'
    ]);
}());
