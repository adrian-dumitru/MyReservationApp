'use strict';

angular.module('myreservationappApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('restaurant', {
                parent: 'site',
                url: '/restaurant/:id',
                data: {
                    roles: ['ROLE_OWNER','ROLE_CLIENT'],
                    pageTitle: 'restaurant.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/restaurant/restaurant.html',
                        controller: 'RestaurantController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('restaurant');
                        $translatePartialLoader.addPart('profile');
                        return $translate.refresh();
                    }]
                }
            });
    });
