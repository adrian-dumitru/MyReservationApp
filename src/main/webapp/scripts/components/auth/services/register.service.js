'use strict';

angular.module('myreservationappApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


