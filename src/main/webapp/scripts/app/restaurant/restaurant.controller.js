'use strict';

angular.module('myreservationappApp')
    .controller('RestaurantController', function ($scope, $filter, Principal, Restaurant, Reservation, Comment, Program, $stateParams) {

        $scope.restaurant = {};
        $scope.reservation = {};
        $scope.location = {};
        $scope.program = [];
        $scope.comments = [];
        $scope.user = {};

        $scope.week = [
            {day : 'monday',    nr : 0},
            {day : 'tuesday',   nr : 1},
            {day : 'wednesday', nr : 2},
            {day : 'thursday',  nr : 3},
            {day : 'friday',    nr : 4},
            {day : 'saturday',  nr : 5},
            {day : 'sunday',    nr : 6}
        ];

        $scope.loadAllComments = function() {
            Comment.get({id: $scope.restaurant.id},function(result) {
                $scope.comments = result;
            });
        };

        $scope.load = function (id) {
            Restaurant.get({id: id}, function(result) {
                $scope.restaurant = result;
                $scope.location = result.location;

                Program.get({id: $scope.restaurant.id}, function(result) {
                    $scope.program = result;
                });

                Principal.identity().then(function(account) {
                    $scope.user = account;
                });

                $scope.loadAllComments();

                if(($scope.location.latitude != null) && ($scope.location.longitude != null)){

                    $scope.$on('mapInitialized', function(event, map) {
                    });

                    $scope.map = {
                        center: {
                            latitude: $scope.location.latitude,
                            longitude: $scope.location.longitude
                        },
                        zoom: 12
                    };

                    $scope.marker = {
                        id : 1,
                        coords:{
                            latitude: $scope.location.latitude,
                            longitude: $scope.location.longitude
                        }
                    };
                }
            });
        };

        $scope.load($stateParams.id);

        $scope.createComment = function () {
            $scope.comment.user = $scope.user;
            $scope.comment.restaurant = $scope.restaurant;

            Comment.update($scope.comment,
                function () {
                    $scope.loadAllComments();
                    $('#saveCommentModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.deleteComment = function (data) {
                $scope.comment = data;
                $('#deleteCommentConfirmation').modal('show');
        };

        $scope.confirmDeleteComment = function (id) {
            Comment.delete({id: id},
                function () {
                    $scope.loadAllComments();
                    $('#deleteCommentConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clearComment = function () {
            $scope.comment = {comment: null, id: null};
            $scope.editFormComment.$setPristine();
            $scope.editFormComment.$setUntouched();
        };

    //    -------------------------------------------------------------------------------------------------

        //Reservations

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

        $scope.createReservation = function () {

            if($scope.restaurant.type != 'club')
                $scope.changeData();
            else
                $scope.reservation.day = $filter('date')($scope.day,"yyyy-MM-dd");

            if($scope.reservation.user == null)
                $scope.reservation.user = $scope.user;

            if($scope.reservation.restaurant == null)
                $scope.reservation.restaurant = $scope.restaurant;

            Reservation.update($scope.reservation,
                function(){
                    $('#saveReservationModal').modal('hide');
                    $scope.clearReservation();
                },function(err){
                    $scope.error_message = err.headers('Failure');
                    $scope.error_message_location = 'myreservationappApp.reservation.errors.'+ $scope.error_message;
                    $('#errorReservation').modal('show');

                }
            );

        };

        $scope.clearReservation = function () {
            $scope.start_hour = null;
            $scope.end_hour = null;
            $scope.reservation = {day: null, start_hour: null, end_hour: null, tables: null, comment: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };

    });
