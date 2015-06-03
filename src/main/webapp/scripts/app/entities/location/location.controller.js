'use strict';

angular.module('myreservationappApp')
    .controller('LocationController', function ($scope, Location, Restaurant) {
        $scope.locations = [];
        $scope.restaurants = Restaurant.query();
        $scope.loadAll = function() {
            Location.query(function(result) {
               $scope.locations = result;
            });
        };
        $scope.loadAll();

        $scope.create = function () {
            Location.update($scope.location,
                function () {
                    $scope.loadAll();
                    $('#saveLocationModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            Location.get({id: id}, function(result) {
                $scope.location = result;
                $('#saveLocationModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            Location.get({id: id}, function(result) {
                $scope.location = result;
                $('#deleteLocationConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Location.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteLocationConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.location = {city: null, street: null, number: null, latitude: null, longitude: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
