'use strict';

angular.module('myreservationappApp')
    .controller('ProgramDetailController', function ($scope, $stateParams, Program, Restaurant) {
        $scope.program = {};
        $scope.load = function (id) {
            Program.get({id: id}, function(result) {
              $scope.program = result;
            });
        };
        $scope.load($stateParams.id);
    });
