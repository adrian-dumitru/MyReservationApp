'use strict';

angular.module('myreservationappApp')
    .factory('ReservationMore',['$http', function ($http) {

        var urlBase = 'api/reservations/current_user/';
        var ReservationMore = {};

        ReservationMore.getAllReservationsCurrentUser = function(id){
           return  $http.get(urlBase + '' + id);
        };

        return ReservationMore;

    }]);
