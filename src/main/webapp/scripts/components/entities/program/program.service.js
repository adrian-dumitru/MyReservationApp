'use strict';

angular.module('myreservationappApp')
    .factory('Program', function ($resource) {
        return $resource('api/programs/:id', {}, {
            'get': { method: 'GET', isArray: true},
            'save': { method: 'POST'},
            'update': { method:'PUT' }
        });
    });
