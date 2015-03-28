/* jshint node: true, browser: false */
'use strict';

var path = require('path');

module.exports = function (grunt) {
    require('load-grunt-tasks')(grunt);
    require('time-grunt')(grunt);

    grunt.initConfig({

        jscs: {
            src: [
                /* include */
                '*.js',
                '**/*.js',

                /* exclude */
                '!**/vendor/**/*.js',
                '!.bower_components/**/*.js',
                '!node_modules/**/*.js'
            ],
            options: {reporter: 'inline'}
        },

        jshint: {
            files: {
                src: [
                    /* include */
                    '*.js',
                    '**/*.js',

                    /* exclude */
                    '!**/vendor/**/*.js',
                    '!.bower_components/**/*.js',
                    '!node_modules/**/*.js'
                ]
            },
            options: {
                jshintrc: path.normalize('.jshintrc'),
                reporter: require('jshint-stylish')
            }
        },

        copy: {
            main: {
                files: [
                    {
                        expand: true,
                        cwd: '.bower_components',
                        src: [
                            'angular/angular.min.js',
                            'angular/angular.min.js.map',
                            'angular-resource/angular-resource.min.js*',
                            'angular-route/angular-route.min.js*'
                        ],
                        dest: 'script/vendor'
                    },
                    {
                        expand: true,
                        cwd: '.bower_components',
                        src: ['normalize-css/normalize.css'],
                        dest: 'style/vendor'
                    }
                ]
            }
        }
    });

    grunt.registerTask('build', ['copy']);

    grunt.registerTask('default', ['test', 'build']);

    grunt.registerTask('test', ['jscs', 'jshint']);
};
