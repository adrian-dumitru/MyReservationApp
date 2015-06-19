'use strict';

angular.module('myreservationappApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('reservation', {
                parent: 'site',
                url: '/reservation',
                data: {
                    roles: ['ROLE_OWNER'],
                    pageTitle: 'myreservationappApp.reservation.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/reservation/reservations.html',
                        controller: 'ReservationController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('reservation');
                        return $translate.refresh();
                    }]
                }
            })
            .state('reservationDetail', {
                parent: 'site',
                url: '/reservation/:id',
                data: {
                    roles: ['ROLE_OWNER'],
                    pageTitle: 'myreservationappApp.reservation.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/reservation/reservation-detail.html',
                        controller: 'ReservationDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('reservation');
                        $translatePartialLoader.addPart('error');
                        return $translate.refresh();
                    }]
                }
            });
    });
