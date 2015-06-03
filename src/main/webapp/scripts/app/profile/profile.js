'use strict';

angular.module('myreservationappApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('profile', {
                parent: 'site',
                url: '/profile',
                data: {
                    roles: ['ROLE_OWNER'],
                    pageTitle: 'myreservationappApp.restaurant.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/profile/profile.html',
                        controller: 'ProfileController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('profile');
                        $translatePartialLoader.addPart('restaurant');
                        $translatePartialLoader.addPart('location');
                        return $translate.refresh();
                    }]
                }
            })

    });
