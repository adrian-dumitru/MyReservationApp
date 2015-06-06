'use strict';

angular.module('myreservationappApp')
    .controller('RestaurantController', function ($scope, Principal,Restaurant, $stateParams) {

        $scope.restaurant = {};
        $scope.location = {};
        $scope.program = {};

        $scope.load = function (id) {
            Restaurant.get({id: id}, function(result) {
                $scope.restaurant = result;
                $scope.location = result.location;
                $scope.program = result.program;

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

    });
