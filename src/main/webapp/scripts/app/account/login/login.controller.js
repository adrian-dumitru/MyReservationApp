'use strict';

angular.module('myreservationappApp')
    .controller('LoginController', function ($rootScope, $scope, $state, $timeout, Auth, $translate,Facebook) {
        $scope.user = {};
        $scope.errors = {};

        $scope.rememberMe = true;
        $timeout(function (){angular.element('[ng-model="username"]').focus();});
        $scope.login = function () {
            Auth.login({
                username: $scope.username,
                password: $scope.password,
                rememberMe: $scope.rememberMe
            }).then(function () {
                $scope.authenticationError = false;
                if ($rootScope.previousStateName === 'register') {
                    $state.go('home');
                } else {
                    $rootScope.back();
                }
            }).catch(function () {
                $scope.authenticationError = true;
            });
        };

        $scope.facebook_login = function() {

            Facebook.login(function(response) {

                Facebook.api('/me', function(response) {

                    $scope.user.login = 'facebookuser' + response.id;
                    $scope.user.firstName = response.first_name;
                    $scope.user.lastName = response.last_name;
                    $scope.user.password = 'facebookpassword';
                    $scope.user.email = 'facebookemail' + response.id + '@mail.com';
                    $scope.user.langKey = $translate.use();
                    $scope.user.roles = ["ROLE_CLIENT"];

                    Auth.createAccount($scope.user).then(function () {

                        $scope.username = $scope.user.login;
                        $scope.password = $scope.user.password;
                        $scope.login();
                        $scope.success = 'OK';

                    });
                });
            });
        };

    });
