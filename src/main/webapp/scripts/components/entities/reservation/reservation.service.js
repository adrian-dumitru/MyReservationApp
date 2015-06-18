'use strict';

angular.module('myreservationappApp')
    .factory('Reservation', function ($resource) {
        return $resource('api/reservations/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    var dayFrom = data.day.split("-");
                    data.day = new Date(new Date(dayFrom[0], dayFrom[1] - 1, dayFrom[2]));
                    return data;
                }
            },
            'update': { method:'PUT',
                interceptor:{
                    response: function(data){
                        return data;
                    },
                    responseError: function(err){
                        return err;
                    }
                }
            }
        });
    });
