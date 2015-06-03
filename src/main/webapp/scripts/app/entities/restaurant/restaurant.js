'use strict';

angular.module('myreservationappApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('restaurant', {
                parent: 'entity',
                url: '/restaurant',
                data: {
                    roles: ['ROLE_OWNER'],
                    pageTitle: 'myreservationappApp.restaurant.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/restaurant/restaurants.html',
                        controller: 'RestaurantController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('restaurant');
                        return $translate.refresh();
                    }]
                }
            })
            .state('restaurantDetail', {
                parent: 'entity',
                url: '/restaurant/:id',
                data: {
                    roles: ['ROLE_OWNER'],
                    pageTitle: 'myreservationappApp.restaurant.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/restaurant/restaurant-detail.html',
                        controller: 'RestaurantDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('restaurant');
                        return $translate.refresh();
                    }]
                }
            });
    });
