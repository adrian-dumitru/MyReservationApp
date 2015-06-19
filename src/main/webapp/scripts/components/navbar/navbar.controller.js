'use strict';

angular.module('myreservationappApp')
    .controller('NavbarController', function ($scope, $location, $state, Auth, Principal,Facebook) {
        $scope.isAuthenticated = Principal.isAuthenticated;
        $scope.$state = $state;

        $scope.logout = function () {

            Facebook.logout(function() {
                $scope.$apply(function() {
                });
            });

            Auth.logout();
            $state.go('home');
        };
    });
