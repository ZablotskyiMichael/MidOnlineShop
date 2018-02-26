'use strict';

angular.module('crudApp').controller('UserController',
    ['UserService', '$scope' , function( UserService, $scope) {

        var self = this;
        self.user = {};
        self.users=[];
        self.role = {};
        self.roles = [];
        self.currentUser={};
        self.addMoney ={};
        self.purchases=[];
        self.property={};

        self.registrationNewUser = registrationNewUser;


        self.updateRole = updateRole;
        self.updateUser = updateUser;
        self.removeUser = removeUser;
        self.updateProperty = updateProperty;
        self.removeRole = removeRole;
        self.refreshPurchase = refreshPurchase;
        self.addUserMoney = addUserMoney;

        self.submitUser = submitUser;
        self.submitRole = submitRole;
        self.createUser = createUser;
        self.editUser = editUser;
        self.editProperty = editProperty;
        self.createRole = createRole;
        self.editRole = editRole;


        self.getAllUsers = getAllUsers;
        self.getAllRoles = getAllRoles;
        self.getAllProperties = getAllProperties;
        self.getPurchaseByUser = getPurchaseByUser;
        self.getUserMoney = getUserMoney;
        self.getAllPurchase = getAllPurchase;

        self.reset = reset;

        self.successMessage = '';
        self.errorMessage = '';
        self.done = false;

        self.successAccMessage = '';
        self.errorAccMessage = '';
        self.doneAcc = false;

        self.successRoleMessage = '';
        self.errorRoleMessage = '';
        self.RoleDone = false;

        self.successPropertyMessage = '';
        self.errorPropertyMessage = '';
        self.doneProperty = false;

        self.onlyIntegers = /^\d+$/;
        self.onlyNumbers = /^\d+([,.]\d+)?$/;
        function refreshPurchase() {
            self.purchases = UserService.loadAllPurchase();
        }
        function getPurchaseByUser() {
            return UserService.getPurchaseByUser();
        }
        function getAllPurchase(){
            return UserService.getAllPurchase();
        }
        function getAllProperties() {
            return UserService.getAllProperties();
        }
        function getUserMoney() {
            return UserService.getUserMoney();
        }
        function addUserMoney() {
            console.log ('add money');
            UserService.addMoney(self.addMoney.money)
                .then(
                    function (response) {
                        console.log('Money was added successfully');
                        self.successAccMessage='Money was added';
                        self.errorAccMessage='';
                        self.addMoney={};
                        self.doneAcc = true;
                    },
                    function (errResponse) {
                        console.error('Money was not added');
                        self.successAccMessage='';
                        self.successAccMessage='Money was not added : '  + + errResponse.data.errorMessage;
                    }
                )
        }
        function submitUser() {
            console.log('Submitting');
            if (self.user.id === undefined || self.user.id === null) {
                console.log('Saving New User', self.user);
                createUser(self.user);
            } else {
                updateUser(self.user, self.user.id);
                console.log('User updated with id ', self.user.id);
            }
        }
        function submitRole() {
            console.log('Submitting');
            if (self.role.roleId === undefined || self.role.roleId === null) {
                console.log('Saving New Role', self.role);
                createRole(self.role);
            } else {
                updateRole(self.role, self.role.roleId);
                console.log('Role updated with id ', self.role.roleId);
            }
        }

        function createUser(user) {
            console.log('About to create user');
            UserService.createUser(user)
                .then(
                    function (response) {
                        console.log('User created successfully');
                        self.successMessage = 'User created successfully';
                        self.errorMessage='';
                        self.done = true;
                        self.user={};
                        $scope.myForm.$setPristine();
                    },
                    function (errResponse) {
                        console.error('Error while creating User');
                        self.errorMessage = 'Error while creating User: ' + errResponse.data.errorMessage;
                        self.successMessage='';
                    }
                );
        }
        function createRole(role) {
            console.log('About to create role');
            UserService.createRole(role)
                .then(
                    function (response) {
                        console.log('Role created successfully');
                        self.successRoleMessage = 'Role created successfully';
                        self.errorRoleMessage='';
                        self.RoleDone = true;
                        self.role={};
                        $scope.myRoleForm.$setPristine();
                    },
                    function (errResponse) {
                        console.error('Error while creating Role');
                        self.errorRoleMessage = 'Error while creating Role: ' + errResponse.data.errorMessage;
                        self.successRoleMessage='';
                    }
                );
        }



        function updateUser(user, id){
            console.log('About to update user');
            UserService.updateUser(user, id)
                .then(
                    function (response){
                        console.log('User updated successfully');
                        self.successMessage='User updated successfully';
                        self.errorMessage='';
                        //self.done = true;
                        $scope.myForm.$setPristine();
                    },
                    function(errResponse){
                        console.error('Error while updating User');
                        self.errorMessage='Error while updating User '+errResponse.data;
                        self.successMessage='';
                    }
                );
        }
        function updateRole(role, id){
            console.log('About to update role');
            UserService.updateRole(role, id)
                .then(
                    function (response){
                        console.log('Role updated successfully');
                        self.successRoleMessage='Role updated successfully';
                        self.errorRoleMessage='';
                        //self.done = true;
                        $scope.myRoleForm.$setPristine();
                    },
                    function(errResponse){
                        console.error('Error while updating Role');
                        self.errorRoleMessage='Error while updating Role '+errResponse.data;
                        self.successRoleMessage='';
                    }
                );
        }
        function updateProperty(){
            console.log('About to update Property');
            UserService.updateProperty(self.property, self.property.id)
                .then(
                    function (response){
                        console.log('Property updated successfully');
                        self.successPropertyMessage='Property updated successfully';
                        self.errorPropertyMessage='';
                        self.doneProperty = true;
                        $scope.myPropertyForm.$setPristine();
                    },
                    function(errResponse){
                        console.error('Error while updating Property');
                        self.errorRoleMessage='Error while updating Property '+errResponse.data;
                        self.successRoleMessage='';
                    }
                );
        }


        function removeUser(id){
            console.log('About to remove User with id '+id);
            UserService.removeUser(id)
                .then(
                    function(){
                        console.log('User '+id + ' removed successfully');
                    },
                    function(errResponse){
                        console.error('Error while removing user '+id +', Error :'+errResponse.data);
                    }
                );
        }
        function removeRole(id){
            console.log('About to remove Role with id '+id);
            UserService.removeRole(id)
                .then(
                    function(){
                        console.log('Role '+id + ' removed successfully');
                        
                    },
                    function(errResponse){
                        console.error('Error while removing role '+id +', Error :'+errResponse.data);
                    }
                );
        }

        function getAllRoles(){
            return UserService.getAllRoles();
        }
        function getAllUsers(){
            return UserService.getAllUsers();
        }
        function editUser(id) {
            self.successMessage='';
            self.errorMessage='';
            UserService.getUser(id).then(
                function (user) {
                    self.user = user;
                },
                function (errResponse) {
                    console.error('Error while loading property ' + id + ', Error :' + errResponse.data);
                }
            );
        }
        function editProperty(id) {
            self.successPropertyMessage='';
            self.errorPropertyMessage='';
            UserService.getProperty(id).then(
                function (property) {
                    self.property = property;
                },
                function (errResponse) {
                    console.error('Error while loading property ' + id + ', Error :' + errResponse.data);
                }
            );
        }

        function editRole(id) {
            self.successRoleMessage='';
            self.errorRoleMessage='';
            UserService.getRole(id).then(
                function (role) {
                    self.role = role;
                },
                function (errResponse) {
                    console.error('Error while removing role ' + id + ', Error :' + errResponse.data);
                }
            );
        }
        function reset(){
            self.successMessage='';
            self.errorMessage='';
            self.successRoleMessage='';
            self.errorRoleMessage='';
            self.user={};
            self.role={};
            $scope.myForm.$setPristine();
            $scope.myRoleForm.$setPristine();//reset Form
        }

        function registrationNewUser(user) {
            console.log('About to create user');
            UserService.registrationNewUser(user)
                .then(
                    function (response) {
                        console.log('User created successfully');
                        self.successMessage = 'User created successfully';
                        self.errorMessage='';
                        self.done = true;
                        self.user={};
                       // $scope.myForm.$setPristine();
                    },
                    function (errResponse) {
                        console.error('Error while creating User');
                        self.errorMessage = 'Error while creating User: ' + errResponse.data.errorMessage;
                        self.successMessage='';
                    }
                );
        }
    }


    ]);