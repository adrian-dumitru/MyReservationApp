'use strict';

angular.module('myreservationappApp')
    .factory('Location', function ($resource) {
        return $resource('api/locations/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'save':{ method:'POST' },
            'update': { method:'PUT' }
        });
    });
