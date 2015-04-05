(function () {
    'use strict';

    /**
     * @module core
     *
     * @description
     * This is the core module, everything depends on this.
     *
     * @requires ngRoute
     * @requires ngResource
     */
    angular.module('app.core', [
        /* Angular modules */
        'ngRoute',
        'ngResource'
    ]);
}());
