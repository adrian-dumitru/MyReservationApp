'use strict';

angular.module('myreservationappApp')
    .controller('ReservationDetailController', function ($scope, $stateParams, Reservation,$filter) {
        $scope.reservation = {};
        $scope.load = function (id) {
            Reservation.get({id: id}, function(result) {
                $scope.reservation = result;
                $scope.reservation.day = $filter('date')($scope.reservation.day,"yyyy-MM-dd");
            });
        };
        $scope.load($stateParams.id);
    });
