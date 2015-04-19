'use strict';

angular.module('myreservationappApp')
    .controller('LogoutController', function (Auth) {
        Auth.logout();
    });
