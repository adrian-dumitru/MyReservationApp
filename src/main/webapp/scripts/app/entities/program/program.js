'use strict';

angular.module('myreservationappApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('program', {
                parent: 'entity',
                url: '/program',
                data: {
                    roles: ['ROLE_OWNER'],
                    pageTitle: 'myreservationappApp.program.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/program/programs.html',
                        controller: 'ProgramController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('program');
                        return $translate.refresh();
                    }]
                }
            })
            .state('programDetail', {
                parent: 'entity',
                url: '/program/:id',
                data: {
                    roles: ['ROLE_OWNER'],
                    pageTitle: 'myreservationappApp.program.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/program/program-detail.html',
                        controller: 'ProgramDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('program');
                        return $translate.refresh();
                    }]
                }
            });
    });
