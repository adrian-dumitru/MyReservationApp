'use strict';

angular.module('myreservationappApp')
    .controller('RestaurantDetailController', function ($scope, $stateParams, Restaurant, User, Location, Program) {
        $scope.restaurant = {};
        $scope.load = function (id) {
            Restaurant.get({id: id}, function(result) {
              $scope.restaurant = result;
            });
        };
        $scope.load($stateParams.id);
    });
