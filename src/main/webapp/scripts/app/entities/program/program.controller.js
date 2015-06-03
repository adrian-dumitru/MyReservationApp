'use strict';

angular.module('myreservationappApp')
    .controller('ProgramController', function ($scope, Program, Restaurant) {
        $scope.programs = [];
        $scope.restaurants = Restaurant.query();
        $scope.loadAll = function() {
            Program.query(function(result) {
               $scope.programs = result;
            });
        };
        $scope.loadAll();

        $scope.create = function () {
            Program.update($scope.program,
                function () {
                    $scope.loadAll();
                    $('#saveProgramModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            Program.get({id: id}, function(result) {
                $scope.program = result;
                $('#saveProgramModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            Program.get({id: id}, function(result) {
                $scope.program = result;
                $('#deleteProgramConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Program.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteProgramConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.program = {mondayStart: null, mondayEnd: null, tuesdayStart: null, tuesdayEnd: null, wednesdayStart: null, wednesdayEnd: null, thursdayStart: null, thursdayEnd: null, fridayStart: null, fridayEnd: null, saturdayStart: null, saturdayEnd: null, sundayStart: null, sundayEnd: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
