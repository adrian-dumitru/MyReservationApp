'use strict';

angular.module('myreservationappApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('location', {
                parent: 'entity',
                url: '/location',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'myreservationappApp.location.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/location/locations.html',
                        controller: 'LocationController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('location');
                        return $translate.refresh();
                    }]
                }
            })
            .state('locationDetail', {
                parent: 'entity',
                url: '/location/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'myreservationappApp.location.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/location/location-detail.html',
                        controller: 'LocationDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('location');
                        return $translate.refresh();
                    }]
                }
            });
    });
