'use strict';

angular.module('myreservationappApp')
    .controller('ReservationController', function ($scope, Reservation, ReservationMore,Principal,Restaurant,$filter) {
        $scope.reservations = [];

        $scope.restaurant = {};

        $scope.toFormatDate = function(x){
            return $filter('date')(x,"yyyy-MM-dd");
        };

        Principal.identity().then(function(account) {
            $scope.account = account;
            $scope.isAuthenticated = Principal.isAuthenticated;


            if($scope.isAuthenticated()){
                Restaurant.get({id: $scope.account.id}, function(result) {
                    $scope.restaurant = result;
                    $scope.loadAll();
                });
            }
        });

        $scope.loadAll = function() {
            ReservationMore.getAllReservationsCurrentUser($scope.restaurant.id)
                .success(function (reservations) {
                    $scope.reservations = reservations;
                    console.log($scope.reservations.length);
                    for(var i = 0; i < $scope.reservations.length; i++){
                        $scope.reservations[i].day = $scope.toFormatDate($scope.reservations[i].day);
                    }
                })
                .error(function () {
                    console.log('ERROR: Reservations');
                })
        };

        $scope.changeData = function(){
            if($scope.start_hour != null)
                $scope.reservation.start_hour = $scope.start_hour;
            if($scope.end_hour != null)
                $scope.reservation.end_hour = $scope.end_hour;
            else if ($scope.start_hour != null) {
                $scope.reservation.end_hour = new Date();
                $scope.reservation.end_hour.setTime($scope.start_hour.getTime() + (2 * 60 * 60 * 1000));
            }

            $scope.reservation.day = $filter('date')($scope.day,"yyyy-MM-dd");

            if(($filter('date')($scope.reservation.start_hour,"yyyy-MM-dd")) ===
                ($filter('date')($scope.reservation.end_hour,"yyyy-MM-dd")))

                $scope.reservation.finish = $scope.reservation.day;
            else{
                $scope.finish = new Date();
                $scope.finish.setDate($scope.day.getDate() + 1);
                $scope.reservation.finish = $filter('date')($scope.finish,"yyyy-MM-dd");
            }
        };

        $scope.create = function () {

            if($scope.restaurant.type != 'club')
               $scope.changeData();
            else
                $scope.reservation.day = $filter('date')($scope.day,"yyyy-MM-dd");

            if($scope.reservation.user == null)
                $scope.reservation.user = $scope.account;

            if($scope.reservation.restaurant == null)
                $scope.reservation.restaurant = $scope.restaurant;

            Reservation.update($scope.reservation,
                function(){
                    $scope.loadAll();
                    $('#saveReservationModal').modal('hide');
                    $scope.clear();
                },function(err){
                    $scope.error_message = err.headers('Failure');
                    $scope.error_message_location = 'myreservationappApp.reservation.errors.'+ $scope.error_message;
                    $('#errorReservation').modal('show');

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
            $scope.start_hour = null;
            $scope.end_hour = null;
            $scope.reservation = {day: null, start_hour: null, end_hour: null, tables: null, comment: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
