'use strict';

angular.module('myreservationappApp')
    .controller('LocationDetailController', function ($scope, $stateParams, Location, Restaurant) {
        $scope.location = {};
        $scope.load = function (id) {
            Location.get({id: id}, function(result) {
              $scope.location = result;
            });
        };
        $scope.load($stateParams.id);
    });
