'use strict';

angular.module('myreservationappApp')
    .factory('Restaurant', function ($resource) {
        return $resource('api/restaurants/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'save': { method: 'POST'},
            'update': { method:'PUT' }
        });
    });
