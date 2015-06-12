'use strict';

angular.module('myreservationappApp')
    .controller('ReservationController', function ($scope, Reservation, ReservationMore,Principal,Restaurant,$filter) {
        $scope.reservations = [];


        $scope.getTime = function(x){
            return $filter('date')(x,"HH:mm");
        };


        Principal.identity().then(function(account) {
            $scope.account = account;
            $scope.isAuthenticated = Principal.isAuthenticated;


            if($scope.isAuthenticated()){
                Restaurant.get({id: $scope.account.id}, function(result) {
                    $scope.restaurant = result;
                });
                $scope.loadAll();
            }
        });

        $scope.loadAll = function() {
            ReservationMore.getAllReservationsCurrentUser($scope.account.id)
                .success(function (reservations) {
                    $scope.reservations = reservations;
                })
                .error(function () {
                    console.log('ERROR: Reservations');
                })
        };

        $scope.create = function () {
            if($scope.start_hour != null)
                $scope.reservation.start_hour = $scope.getTime($scope.start_hour);
            if($scope.end_hour != null)
                $scope.reservation.end_hour = $scope.getTime($scope.end_hour);
            if($scope.reservation.user == null)
                $scope.reservation.user = $scope.account;

            Reservation.update($scope.reservation,
                function(data){
                    $scope.loadAll();
                    $('#saveReservationModal').modal('hide');
                    $scope.clear();
                },function(err){
                    console.log(err);
                }
            );

        };

        $scope.update = function (id) {
            Reservation.get({id: id}, function(result) {
                $scope.reservation = result;
                $('#saveReservationModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            Reservation.get({id: id}, function(result) {
                $scope.reservation = result;
                $('#deleteReservationConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Reservation.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteReservationConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.reservation = {day: null, start_hour: null, end_hour: null, tables: null, comment: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
