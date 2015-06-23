'use strict';

angular.module('myreservationappApp')
    .factory('Comment', function ($resource) {
        return $resource('api/comments/:id', {}, {
            'get': {
                method: 'GET',isArray: true
            },
            'update': { method:'PUT' }
        });
    });
