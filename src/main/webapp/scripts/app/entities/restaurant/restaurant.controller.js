'use strict';

angular.module('myreservationappApp')
    .controller('RestaurantController', function ($scope, Restaurant, User, Location, Program) {
        $scope.restaurants = [];
        $scope.users = User.query();
        $scope.locations = Location.query();
        $scope.programs = Program.query();
        $scope.loadAll = function() {
            Restaurant.query(function(result) {
               $scope.restaurants = result;
            });
        };
        $scope.loadAll();

        $scope.create = function () {
            Restaurant.update($scope.restaurant,
                function () {
                    $scope.loadAll();
                    $('#saveRestaurantModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            Restaurant.get({id: id}, function(result) {
                $scope.restaurant = result;
                $('#saveRestaurantModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            Restaurant.get({id: id}, function(result) {
                $scope.restaurant = result;
                $('#deleteRestaurantConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Restaurant.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteRestaurantConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.restaurant = {name: null, type: null, description: null, phone: null, email: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
