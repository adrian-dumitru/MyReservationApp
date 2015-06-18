/**
 * Created by Adrian.D on 5/26/2015.
 */
'use strict';

angular.module('myreservationappApp')
    .controller('ProfileController', function ($scope, Principal,Restaurant, User, Location, Program,$filter,Restaurant_tables) {

        $scope.restaurant = {};
        $scope.location = {};
        $scope.program = [];
        $scope.restaurant_tables = {};
        $scope.restaurant_types = ['restaurant','pub','club'] ;
        $scope.map = {};
        $scope.week = [
                {day : 'monday',    nr : 0},
                {day : 'tuesday',   nr : 1},
                {day : 'wednesday', nr : 2},
                {day : 'thursday',  nr : 3},
                {day : 'friday',    nr : 4},
                {day : 'saturday',  nr : 5},
                {day : 'sunday',    nr : 6}
        ];
        $scope.update_day = {};
        $scope.program_status_options = [ "OPEN","CLOSED"];

        // Take data from server

        Principal.identity().then(function(account) {
            $scope.account = account;
            $scope.isAuthenticated = Principal.isAuthenticated;
            $scope.restaurant.user = $scope.account;

            $scope.getRestaurant();
        });

// --------------------------------------------------------------------------------------------------------------

        // Restaurant functions

        $scope.createRestaurant = function(){
            if($scope.restaurant.id == null) {
                $scope.restaurant.user = $scope.account;
                Restaurant.save($scope.restaurant,
                    function (data) {
                        $scope.restaurant.id = data.id;
                        $('#saveRestaurantModal').modal('hide');
                        $scope.clearRestaurant(true);
                    }
                );
            }else{
                $scope.updateRestaurant(false);
            }
        };

        $scope.updateRestaurant = function(comment){
            Restaurant.update($scope.restaurant,
                function(){
                    if(comment == false)
                        $('#saveRestaurantModal').modal('hide');
                }
            );
        };

        $scope.getRestaurant = function(){
            Restaurant.get({id: $scope.account.id}, function(result) {
                $scope.restaurant = result;

                if($scope.restaurant.location != null) {
                    $scope.location = $scope.restaurant.location;
                    $scope.setMap();
                }
                if($scope.restaurant.restaurant_tables != null)
                    $scope.restaurant_tables = $scope.restaurant.restaurant_tables;
                $scope.getProgram();
            });
        };

        $scope.resetEditFormRestaurant = function (){
            $scope.editFormRestaurant.$setPristine();
            $scope.editFormRestaurant.$setUntouched();
        };

        $scope.clearRestaurant = function () {
            $scope.restaurant = {name: null, type: null, description: null, phone: null, email: null, id: null};
            $scope.editFormRestaurant.$setPristine();
            $scope.editFormRestaurant.$setUntouched();
            $scope.getRestaurant();
        };

// --------------------------------------------------------------------------------------------------------------

        // Location function

        $scope.createLocation = function(){
            if($scope.location.id == null) {
                Location.save($scope.location,
                    function (data) {
                        $scope.location.id = data.id;
                        $scope.restaurant.location = $scope.location;
                        $scope.updateRestaurant(true);
                        $('#saveLocationModal').modal('hide');
                        $scope.clearLocation();
                    }
                );
            }else{
                $scope.updateLocation(false);
            }
        };

        $scope.setMap = function(){
                if(($scope.location.latitude != null) && ($scope.location.longitude != null)){

                    $scope.$on('mapInitialized', function(event, map) {
                    });

                    $scope.map = {
                        center: {
                            latitude: $scope.location.latitude,
                            longitude: $scope.location.longitude
                        },
                        zoom: 8
                    };

                    $scope.marker = {
                        id : 1,
                        coords:{
                            latitude: $scope.location.latitude,
                            longitude: $scope.location.longitude
                        }
                    };

                }
        };

        $scope.getLocation = function(){
            Location.get({id: $scope.restaurant.location.id}, function(result) {
                $scope.location = result;
                $scope.setMap();
            });
        };

        $scope.updateLocation = function(comment){
            Location.update($scope.location,
                function(){
                    if(comment == false)
                        $('#saveLocationModal').modal('hide');
                    $scope.setMap();
                }
            );
        };

        $scope.resetEditFormLocation = function (){
            $scope.editFormLocation.$setPristine();
            $scope.editFormLocation.$setUntouched();
        };


        $scope.clearLocation = function () {
            $scope.location = {city: null, street: null, number: null, id: null};
            $scope.editFormLocation.$setPristine();
            $scope.editFormLocation.$setUntouched();
            if($scope.restaurant.location != null)
                $scope.getLocation();
        };


// --------------------------------------------------------------------------------------------------------------

        // Program functions

        //$scope.getTime = function(x){
        //    return $filter('date')(x,"HH:mm:ss");
        //};

        $scope.createProgram = function(){
            for(var i = 0; i < 7; i++) {
                $scope.program[i].restaurant = $scope.restaurant;
                $scope.program[i].day = $scope.week[i].day;
                if($scope.program[i].status === 'OPEN'){
                    $scope.program[i].start_hour = $scope.week[i].start_hour;
                    $scope.program[i].end_hour = $scope.week[i].end_hour;
                }
            }
            Program.save($scope.program,
                function () {
                    $('#saveProgramModal').modal('hide');
                    $scope.clearProgram();
                }
            );
        };

        $scope.updateProgram = function(){
            Program.update($scope.update_day,function(){
                $('#updateProgramModal').modal('hide');
                $scope.clearProgram();
            });
        };

        $scope.beforeUpdateProgram = function(data){
            $scope.update_day.id = data.id;
            $scope.update_day.day = data.day;
            $scope.update_day.restaurant= $scope.restaurant;
        };

        $scope.getProgram = function(){
            Program.get({id: $scope.restaurant.id}, function(result) {
                $scope.program = result;
            });
        };

        $scope.resetEditFormProgram = function (){
            $scope.editFormProgram.$setPristine();
            $scope.editFormProgram.$setUntouched();
        };

        $scope.clearUpdateProgram = function(){
            $scope.update_day = {};
        };

        $scope.clearProgram = function () {
            $scope.program = [];
            $scope.update_day = {};
            $scope.editFormProgram.$setPristine();
            $scope.editFormProgram.$setUntouched();
            $scope.getProgram();
        };

// --------------------------------------------------------------------------------------------------------------

        // Restaurant tables functions

        $scope.createRestaurant_tables = function(){

            if($scope.program.id == null) {
                Restaurant_tables.save($scope.restaurant_tables,
                    function (data) {
                        $scope.restaurant_tables.id = data.id;
                        $scope.restaurant.restaurant_tables = $scope.restaurant_tables;
                        $scope.updateRestaurant(true);
                        $('#saveRestaurant_tablesModal').modal('hide');
                        $scope.clearRestaurant_tables();
                    }
                );
            }else{
                $scope.updateRestaurant_tables(false);
            }
        };

        $scope.getRestaurant_tables = function(){
            Program.get({id: $scope.restaurant.restaurant_tables.id}, function(result) {
                $scope.restaurant_tables = result;
            });
        };

        $scope.updateRestaurant_tables = function(comment){
            Restaurant_tables.update($scope.restaurant_tables,
                function(){
                    if(comment == false)
                        $('#saveRestaurant_tablesModal').modal('hide');
                }
            );
        };

        $scope.resetEditFormRestaurant_tables = function (){

            $scope.editFormRestaurant_tables.$setPristine();
            $scope.editFormRestaurant_tables.$setUntouched();
        };


        $scope.clearRestaurant_tables = function () {
            $scope.restaurant_tables = {two_persons_table: null, four_persons_table: null, six_persons_table: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
            if($scope.restaurant.restaurant_tables != null)
                $scope.getRestaurant_tables();
        };


    });
