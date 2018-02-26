'use strict';

angular.module('crudApp').factory('UserService',
    ['$localStorage', '$http', '$q', 'urls',
        function ($localStorage, $http, $q, urls) {
            var factory = {
                registrationNewUser:registrationNewUser,
                loadAllUsers: loadAllUsers,
                loadAllRoles:loadAllRoles,
                loadPurchaseByUser : loadPurchaseByUser,
                loadAllProperties:loadAllProperties,
                getAllUsers: getAllUsers,
                getAllRoles:getAllRoles,
                getUser: getUser,
                getRole: getRole,
                getProperty: getProperty,
                createUser: createUser,
                createRole: createRole,
                updateUser: updateUser,
                updateRole: updateRole,
                updateProperty: updateProperty,
                removeUser: removeUser,
                removeRole: removeRole,
                loadAllPurchase :loadAllPurchase,
                getAllPurchase : getAllPurchase,
                getAllProperties:getAllProperties,


                loadCurrentUser:loadCurrentUser,
                addMoney : addMoney,
                getUserMoney : getUserMoney,
                getPurchaseByUser : getPurchaseByUser,
            };

            return factory;

            function loadAllPurchase() {
                console.log('Fetching all purchase');
                var deferred = $q.defer();
                $http.get(urls.PURCHASE_SERVICE_API+1)
                    .then(
                        function (response) {
                            console.log('Fetched successfully all purchase');
                            $localStorage.purchasesp = response.data;
                            deferred.resolve(response);
                        },
                        function (errResponse) {
                            console.error('Error while loading purchase');
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }
            function getAllPurchase() {
                return $localStorage.purchasesp;
            }
            function getAllProperties() {
                return $localStorage.properties;
            }
            function loadCurrentUser() {
                console.log('Fetching current User :');
                var deferred = $q.defer();
                $http.get(urls.CURRENT_USER)
                    .then(
                        function (response) {
                            console.log('Fetched successfully current user');
                            $localStorage.currentUser= response.data;
                            deferred.resolve(response);
                        },
                        function (errResponse) {
                            console.error('Error while loading current user');
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }
            function getUserMoney() {
                return $localStorage.currentUser.account;
            }
            function addMoney(money) {
                loadCurrentUser();
                var user = $localStorage.currentUser;
                user.account = money;
                console.log('Addition of money');
                var deferred = $q.defer();
                $http.put(urls.USER_SERVICE_API_MONEY+$localStorage.currentUser.id,user)
                    .then(
                        function (response) {
                            console.log('Fetched successfully account of current user');
                            loadAllUsers();
                            loadCurrentUser();
                            deferred.resolve(response);
                        },
                        function (errResponse) {
                            console.error('Error while updating current user');
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }
            function loadAllUsers() {
                console.log('Fetching all users');
                var deferred = $q.defer();
                $http.get(urls.USER_SERVICE_API_EASY_ACCESS)
                    .then(
                        function (response) {
                            console.log('Fetched successfully all users');
                            $localStorage.users = response.data;
                            deferred.resolve(response);
                        },
                        function (errResponse) {
                            console.error('Error while loading users');
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }
            function loadAllRoles() {
                console.log('Fetching all roles');
                var deferred = $q.defer();
                $http.get(urls.ROLE_SERVICE_API)
                    .then(
                        function (response) {
                            console.log('Fetched successfully all roles');
                            $localStorage.roles = response.data;
                            deferred.resolve(response);
                        },
                        function (errResponse) {
                            console.error('Error while loading roles');
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }
            function loadAllProperties() {
                console.log('Fetching all Properties');
                var deferred = $q.defer();
                $http.get(urls.PROPERTIES_SERVICE_API)
                    .then(
                        function (response) {
                            console.log('Fetched successfully all Properties');
                            $localStorage.properties = response.data;
                            deferred.resolve(response);
                        },
                        function (errResponse) {
                            console.error('Error while loading Properties');
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }
            function loadPurchaseByUser() {
                console.log('Fetching all Purchases');
                var deferred = $q.defer();
                $http.get(urls.PURCHASE_FILTER_SERVICE_API)
                    .then(
                        function (response) {
                            console.log('Fetched successfully all Purchase');
                            $localStorage.purchaseByUser = response.data;
                            deferred.resolve(response);
                        },
                        function (errResponse) {
                            console.error('Error while loading Purchase');
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }
            function getAllRoles(){
                return $localStorage.roles;
            }
            function getPurchaseByUser() {
                return $localStorage.purchaseByUser ;
            }
            function getAllUsers(){
                return $localStorage.users;
            }

            function getUser(id) {
                console.log('Fetching User with id :'+id);
                var deferred = $q.defer();
                $http.get(urls.USER_SERVICE_API + id)
                    .then(
                        function (response) {
                            console.log('Fetched successfully User with id :'+id);
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                            console.error('Error while loading user with id :'+id);
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }
            function getRole(id) {
                console.log('Fetching Role with id :'+id);
                var deferred = $q.defer();
                $http.get(urls.ROLE_SERVICE_API + id)
                    .then(
                        function (response) {
                            console.log('Fetched successfully Role with id :'+id);
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                            console.error('Error while loading role with id :'+id);
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }
            function getProperty(id) {
                console.log('Fetching Property with id :'+id);
                var deferred = $q.defer();
                $http.get(urls.PROPERTIES_SERVICE_API + id)
                    .then(
                        function (response) {
                            console.log('Fetched successfully Property with id :'+id);
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                            console.error('Error while loading Property with id :'+id);
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }

            function createUser(user) {
                console.log('Creating User');
                var deferred = $q.defer();
                $http.post(urls.USER_SERVICE_API, user)
                    .then(
                        function (response) {
                            loadAllUsers();
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                           console.error('Error while creating User : '+errResponse.data.errorMessage);
                           deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }
            function createRole(role) {
                console.log('Creating Role');
                var deferred = $q.defer();
                $http.post(urls.ROLE_SERVICE_API, role)
                    .then(
                        function (response) {
                            loadAllRoles();
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                            console.error('Error while creating Role : '+errResponse.data.errorMessage);
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }

            function updateUser(user, id) {
                console.log('Updating User with id '+id);
                var deferred = $q.defer();
                $http.put(urls.USER_SERVICE_API + id, user)
                    .then(
                        function (response) {
                            loadAllUsers();
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                            console.error('Error while updating User with id :'+id);
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }
            function updateRole(role, id) {
                console.log('Updating Role with id '+id);
                var deferred = $q.defer();
                $http.put(urls.ROLE_SERVICE_API + id, role)
                    .then(
                        function (response) {
                            loadAllRoles();
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                            console.error('Error while updating Role with id :'+id);
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }
            function updateProperty(property, id) {
                console.log('Updating Property with id '+id);
                var deferred = $q.defer();
                $http.put(urls.PROPERTIES_SERVICE_API + id, property)
                    .then(
                        function (response) {
                            loadAllProperties();
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                            console.error('Error while updating Property with id :'+id);
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }
            function removeUser(id) {
                console.log('Removing User with id '+id);
                var deferred = $q.defer();
                $http.delete(urls.USER_SERVICE_API + id)
                    .then(
                        function (response) {
                            loadAllUsers();
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                            console.error('Error while removing User with id :'+id);
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }
            function removeRole(id) {
                console.log('Removing Role with id '+id);
                var deferred = $q.defer();
                $http.delete(urls.ROLE_SERVICE_API + id)
                    .then(
                        function (response) {
                            loadAllRoles();
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                            console.error('Error while removing Role with id :'+id);
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }
            function registrationNewUser(user) {
                console.log('Creating User');
                var deferred = $q.defer();
                user.role = 'ROLE_USER';
                $http.post(urls.USER_SERVICE_API, user)
                    .then(
                        function (response) {
                            loadAllUsers();
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                            console.error('Error while creating User : '+errResponse.data.errorMessage);
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }


        }

    ]);