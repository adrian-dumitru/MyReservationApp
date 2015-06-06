'use strict';

angular.module('myreservationappApp')
    .factory('Restaurant_tables', function ($resource) {
        return $resource('api/restaurant_tabless/:id', {}, {
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
