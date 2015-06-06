'use strict';

angular.module('myreservationappApp')
    .factory('RestaurantMore',['$http', function ($http) {

        var urlBase = 'api/restaurants';
        var RestaurantMore = {};

        RestaurantMore.getAllRestaurants = function(){
            return $http.get(urlBase + '/restaurants');
        };

        RestaurantMore.getAllPubs = function(){
            return $http.get(urlBase + '/pubs');
        };

        RestaurantMore.getAllClubs = function(){
            return $http.get(urlBase + '/clubs');
        };

        return RestaurantMore;

    }]);
