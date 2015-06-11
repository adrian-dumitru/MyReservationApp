/**
 * Created by Adrian.D on 5/26/2015.
 */
'use strict';

angular.module('myreservationappApp')
    .controller('ProfileController', function ($scope, Principal,Restaurant, User, Location, Program,$filter,Restaurant_tables) {

        $scope.restaurant = {};
        $scope.location = {};
        $scope.program = {};
        $scope.restaurant_tables = {};
        $scope.restaurant_types = ['restaurant','pub','club'] ;
        $scope.map = {};
        $scope.week = {};


        $scope.getTime = function(x){
            return $filter('date')(x,"HH:mm");
        };

        $scope.setClosed = function(day){

        };

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
                if($scope.restaurant.program != null)
                    $scope.program = $scope.restaurant.program;
                if($scope.restaurant.restaurant_tables != null)
                    $scope.restaurant_tables = $scope.restaurant.restaurant_tables;

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

        $scope.setWeek = function(){
            $scope.week = {
                options: [
                    {
                        name: "OPEN",
                        value: true
                    },
                    {
                        name: "CLOSED",
                        value: false
                    }
                ],
                monday: { name: "OPEN", value: true },
                tuesday: { name: "OPEN", value: true },
                wednesday: { name: "OPEN", value: true },
                thursday: { name: "OPEN", value: true },
                friday: { name: "OPEN", value: true },
                saturday: { name: "OPEN", value: true },
                sunday: { name: "OPEN", value: true }
            };

            $scope.mondayStart = null;          $scope.mondayEnd = null;
            $scope.tuesdayStart = null;         $scope.tuesdayEnd = null;
            $scope.wednesdayStart = null;       $scope.wednesdayEnd = null;
            $scope.thursdayStart = null;        $scope.thursdayEnd = null;
            $scope.fridayStart = null;          $scope.fridayEnd = null;
            $scope.saturdayStart = null;        $scope.saturdayEnd = null;
            $scope.sundayStart = null;          $scope.sundayEnd = null;


        };

        $scope.setProgram = function(){
            if($scope.week.monday.value) {
                $scope.program.mondayStart = $scope.getTime($scope.mondayStart);
                $scope.program.mondayEnd = $scope.getTime($scope.mondayEnd);
            }else{
                $scope.program.mondayStart = 'CLOSED';
                $scope.program.mondayEnd = 'CLOSED';
            }
            if($scope.week.tuesday.value) {
                $scope.program.tuesdayStart = $scope.getTime($scope.tuesdayStart);
                $scope.program.tuesdayEnd = $scope.getTime($scope.tuesdayEnd);
            }else{
                $scope.program.tuesdayStart = 'CLOSED';
                $scope.program.tuesdayEnd = 'CLOSED';
            }
            if($scope.week.wednesday.value) {
                $scope.program.wednesdayStart = $scope.getTime($scope.wednesdayStart);
                $scope.program.wednesdayEnd = $scope.getTime($scope.wednesdayEnd);
            }else{
                $scope.program.wednesdayStart = 'CLOSED';
                $scope.program.wednesdayEnd = 'CLOSED';
            }
            if($scope.week.thursday.value) {
                $scope.program.thursdayStart = $scope.getTime($scope.thursdayStart);
                $scope.program.thursdayEnd = $scope.getTime($scope.thursdayEnd);
            }else{
                $scope.program.thursdayStart = 'CLOSED';
                $scope.program.thursdayEnd = 'CLOSED';
            }
            if($scope.week.friday.value) {
                $scope.program.fridayStart = $scope.getTime($scope.fridayStart);
                $scope.program.fridayEnd = $scope.getTime($scope.fridayEnd);
            }else{
                $scope.program.fridayStart = 'CLOSED';
                $scope.program.fridayEnd = 'CLOSED';
            }
            if($scope.week.saturday.value) {
                $scope.program.saturdayStart = $scope.getTime($scope.saturdayStart);
                $scope.program.saturdayEnd = $scope.getTime($scope.saturdayEnd);
            }else{
                $scope.program.saturdayStart = 'CLOSED';
                $scope.program.saturdayEnd = 'CLOSED';
            }
            if($scope.week.sunday.value) {
                $scope.program.sundayStart = $scope.getTime($scope.sundayStart);
                $scope.program.sundayEnd = $scope.getTime($scope.sundayEnd);
            }else{
                $scope.program.sundayStart = 'CLOSED';
                $scope.program.sundayEnd = 'CLOSED';
            }
        };

        $scope.createProgram = function(){
            $scope.setProgram();
            if($scope.program.id == null) {
                Program.save($scope.program,
                    function (data) {
                        $scope.program.id = data.id;
                        $scope.restaurant.program = $scope.program;
                        $scope.updateRestaurant(true);
                        $('#saveProgramModal').modal('hide');
                        $scope.clearProgram();
                    }
                );
            }else{
                $scope.updateProgram(false);
            }
        };

        $scope.getProgram = function(){
            Program.get({id: $scope.restaurant.program.id}, function(result) {
                $scope.program = result;
                $scope.setWeek();
            });
        };

        $scope.updateProgram = function(comment){
            Program.update($scope.program,
                function(){
                    if(comment == false)
                        $('#saveProgramModal').modal('hide');
                }
            );
        };

        $scope.resetEditFormProgram = function (){
            $scope.editFormProgram.$setPristine();
            $scope.editFormProgram.$setUntouched();
            $scope.setWeek();
        };


        $scope.clearProgram = function () {
            $scope.program = {  mondayStart: null, mondayEnd: null, tuesdayStart: null, tuesdayEnd: null,
                                wednesdayStart: null, wednesdayEnd: null, thursdayStart: null, thursdayEnd: null,
                                fridayStart: null, fridayEnd: null, saturdayStart: null, saturdayEnd: null, sundayStart: null, sundayEnd: null, id: null};

            $scope.setWeek();


            $scope.editFormProgram.$setPristine();
            $scope.editFormProgram.$setUntouched();
            if($scope.restaurant.program != null)
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
