'use strict';

angular.module('crudApp').factory('ProductService',
    ['$localStorage', '$http', '$q', 'urls',
        function ($localStorage, $http, $q, urls) {

            var factory = {
                loadAllProducts:loadAllProducts,
                loadBaskedStatus : loadBaskedStatus,
                loadBoughtStatus : loadBoughtStatus,
                loadPurchaseInBasked:loadPurchaseInBasked,
                loadAllPurchase:loadAllPurchase,
                loadAllStorage:loadAllStorage,
                loadAllCategories:loadAllCategories,
                getAllProducts:getAllProducts,
                getBaskedStatus : getBaskedStatus,
                getBoughtStatus : getBoughtStatus,
                getRecommendedProducts : getRecommendedProducts,
                loadRecommendedProducts : loadRecommendedProducts,
                getPurchaseInBasked:getPurchaseInBasked,
                getAllStorage:getAllStorage,
                getAllPurchase:getAllPurchase,
                getAllCategories:getAllCategories,
                createPurchase: createPurchase,
                createProduct: createProduct,
                createStorage: createStorage,
                createCategory: createCategory,
                getCurrentUser : getCurrentUser,
                loadCurrentUser : loadCurrentUser,
                getProduct:getProduct,
                getPurchase:getPurchase,
                getRelatedProduct : getRelatedProduct,
                loadRelatedProduct : loadRelatedProduct,
                getStorage:getStorage,
                getCategory:getCategory,
                updateStorage:updateStorage,
                updateCategory:updateCategory,
                deletePurchase:deletePurchase,
                removeCategory:removeCategory,
                filterByCategory:filterByCategory,
                getDiscount : getDiscount,
                loadDiscount : loadDiscount,
                loadAllImages : loadAllImages,
                getAllImages : getAllImages,
                getCurrentProduct : getCurrentProduct,
                getStatus : getStatus,
                buyBasked : buyBasked,
                loadRelatedDiscount:loadRelatedDiscount,
                getRelatedDiscount:getRelatedDiscount,
                loadAllMessages : loadAllMessages,
                getAllMessages : getAllMessages,
                createMessage : createMessage,




            };

            return factory;
            function loadAllProducts() {
                console.log('Fetching all products');
                var deferred = $q.defer();
                $http.get(urls.PRODUCT_SERVICE_API)
                    .then(
                        function (response) {
                            console.log('Fetched successfully all products');
                            $localStorage.products = response.data;
                            deferred.resolve(response);
                        },
                        function (errResponse) {
                            console.error('Error while loading products');
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;}

            function getCurrentProduct() {
                return $localStorage.product;
            }
            function getRelatedProduct() {
                return $localStorage.relatedProduct;
            }
            function removeCategory(id) {
                console.log('Removing Category with id '+id);
                var deferred = $q.defer();
                $http.delete(urls.CATEGORY_SERVICE_API + id)
                    .then(
                        function (response) {
                            loadAllCategories();
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                            console.error('Error while removing Category with id :'+id);
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }
            function loadAllCategories() {
                console.log('Fetching all categories');
                var deferred = $q.defer();
                $http.get(urls.CATEGORY_SERVICE_API)
                    .then(
                        function (response) {
                            console.log('Fetched successfully all products');
                            $localStorage.categories = response.data;
                            deferred.resolve(response);
                        },
                        function (errResponse) {
                            console.error('Error while loading categories');
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }
            function updateStorage(storage, id) {
                console.log('Updating Storage with id '+id);
                var deferred = $q.defer();
                $http.put(urls.STORAGE_SERVICE_API + id, storage)
                    .then(
                        function (response) {
                            loadAllStorage();
                            loadAllProducts();
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                            console.error('Error while updating Storage with id :'+id);
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }
            function updateCategory(category, id) {
                console.log('Updating Category with id '+id);
                var deferred = $q.defer();
                $http.put(urls.CATEGORY_SERVICE_API + id, category)
                    .then(
                        function (response) {
                            loadAllCategories();
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                            console.error('Error while updating Category with id :'+id);
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }
            function deletePurchase(id) {
                console.log('Removing Purchase with id '+id);
                var deferred = $q.defer();
                $http.delete(urls.PURCHASE_SERVICE_API + id)
                    .then(
                        function (response) {
                            loadAllPurchase();
                            loadAllStorage();
                            loadPurchaseInBasked();
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                            console.error('Error while removing Purchase with id :'+id);
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }
            function buyBasked() {
                console.log('Updating basked ');
                var deferred = $q.defer();
                $http.delete(urls.BASKED_SERVICE_API + '/')
                    .then(
                        function (response) {
                            loadPurchaseInBasked();
                            loadAllPurchase();
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                            console.error('Error while updating basked ');
                            loadPurchaseInBasked();
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }
            function loadAllStorage() {
                console.log('Fetching all products');
                var deferred = $q.defer();
                $http.get(urls.STORAGE_SERVICE_API)
                    .then(
                        function (response) {
                            console.log('Fetched successfully all products');
                            $localStorage.storages = response.data;
                            deferred.resolve(response);
                        },
                        function (errResponse) {
                            console.error('Error while loading products');
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }
            function loadAllPurchase() {
                console.log('Fetching all purchase');
                var deferred = $q.defer();
                $http.get(urls.PURCHASE_SERVICE_API)
                    .then(
                        function (response) {
                            console.log('Fetched successfully all purchase');
                            $localStorage.purchases = response.data;
                            deferred.resolve(response);
                        },
                        function (errResponse) {
                            console.error('Error while loading purchase');
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }
            function loadPurchaseInBasked() {
                console.log('Fetching all purchase in basked');
                var deferred = $q.defer();
                $http.get(urls.PURCHASE_BASKED_SERVICE_API)
                    .then(
                        function (response) {
                            console.log('Fetched successfully all purchase in basked');
                            $localStorage.purchaseInBasked = response.data;
                            deferred.resolve(response);
                        },
                        function (errResponse) {
                            console.error('Error while loading purchase in basked');
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }
            function loadRecommendedProducts() {
                console.log('Loading recommended products');
                var deferred = $q.defer();
                $http.get(urls.HISTORY_SERVICE_API)
                    .then(
                        function (response) {
                            console.log('Loading recommended products is success');
                            $localStorage.recommendedProducts = response.data;
                            deferred.resolve(response);
                        },
                        function (errResponse) {
                            console.error('Error while loading recommended products');
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }
            function loadBaskedStatus() {
                console.log('Fetching all baskets');
                var deferred = $q.defer();
                $http.get(urls.BASKED_SERVICE_API)
                    .then(
                        function (response) {
                            console.log('Fetched successfully all baskets');
                            $localStorage.basked = response.data;
                            deferred.resolve(response);
                        },
                        function (errResponse) {
                            console.error('Error while loading basked');
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }
            function loadBoughtStatus() {
                console.log('Fetching Bought ');
                var deferred = $q.defer();
                $http.get(urls.BOUGHT_SERVICE_API)
                    .then(
                        function (response) {
                            console.log('Fetched successfully Bought ');
                            $localStorage.bought = response.data;
                            deferred.resolve(response);
                        },
                        function (errResponse) {
                            console.error('Error while loading Bought ');
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }
            function loadAllImages() {
                console.log('Fetching all images');
                var deferred = $q.defer();
                $http.get(urls.IMAGE_SERVICE_API)
                    .then(
                        function (response) {
                            console.log('Fetched successfully all images');
                            $localStorage.images = response.data;
                            deferred.resolve(response);
                        },
                        function (errResponse) {
                            console.error('Error while loading images');
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }
            function createPurchase(purchase) {
                console.log('Creating Purchase');
                var deferred = $q.defer();
                $http.post(urls.PURCHASE_SERVICE_API, purchase)
                    .then(
                        function (response) {
                            loadAllPurchase();
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                            console.error('Error while creating Purchase : '+errResponse.data.errorMessage);
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }
            function filterByCategory(category) {
                var deferred = $q.defer();
                $http.get(urls.FILTER_SERVICE_API + category.id)
                    .then(
                         function (response) {
                     console.log('Fetched successfully all categories');
                     $localStorage.products = response.data;
                     deferred.resolve(response);
                     },
                        function (errResponse) {
                    console.error('Error while loading categories');
                    deferred.reject(errResponse);
                }
            );
                return deferred.promise;
            }
            function createProduct(product) {
                console.log('Creating Purchase');
                var deferred = $q.defer();
                $http.post(urls.PRODUCT_SERVICE_API, product)
                    .then(
                        function (response) {
                            loadAllProducts();
                            loadAllStorage();
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                            console.error('Error while creating Product : '+errResponse.data.errorMessage);
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }
            function createMessage(message) {
                console.log('Creating message');
                var deferred = $q.defer();
                $http.post(urls.MESSAGE_SERVICE_API, message)
                    .then(
                        function (response) {
                            loadAllMessages(message.product.id);
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                            console.error('Error while creating message : '+errResponse.data.errorMessage);
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }
            function createStorage(storage) {
                console.log('Creating Storage');
                var deferred = $q.defer();
                $http.post(urls.STORAGE_SERVICE_API, storage)
                    .then(
                        function (response) {
                            loadAllStorage();
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                            console.error('Error while creating Storage : '+errResponse.data.errorMessage);
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }
            function createCategory(category) {
                console.log('Creating Category');
                var deferred = $q.defer();
                $http.post(urls.CATEGORY_SERVICE_API, category)
                    .then(
                        function (response) {
                            loadAllCategories();
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                            console.error('Error while creating Category : '+errResponse.data.errorMessage);
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
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
            function getProduct(id) {
                console.log('Fetching Product with id :'+id);
                var deferred = $q.defer();
                $http.get(urls.PRODUCT_SERVICE_API + id)
                    .then(
                        function (response) {
                            console.log('Fetched successfully Product with id :'+id);
                            $localStorage.product = response.data;
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                            console.error('Error while loading product with id :'+id);
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }
            function loadRelatedProduct(id) {
                console.log('Fetching related Product with id :'+id);
                var deferred = $q.defer();
                $http.get(urls.PRODUCT_SERVICE_API + id)
                    .then(
                        function (response) {
                            console.log('Fetched successfully related Product with id :'+id);
                            $localStorage.relatedProduct = response.data;
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                            console.error('Error while loading related product with id :'+id);
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }
            function loadAllMessages(id) {
                console.log('Fetching all message');
                var deferred = $q.defer();
                $http.get(urls.MESSAGE_SERVICE_API + id)
                    .then(
                        function (response) {
                            console.log('Fetched successfully all message');
                            $localStorage.messages = response.data;
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                            console.error('Error while loading all message');
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }
            function getStatus(id) {
                console.log('Fetching Product with id :'+id);
                var deferred = $q.defer();
                $http.get(urls.STATUS_SERVICE_API + id)
                    .then(
                        function (response) {
                            console.log('Fetched successfully Status with id :'+id);
                            $localStorage.dictionary = response.data;
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                            console.error('Error while loading status with id :'+id);
                            deferred.reject(errResponse);
                        }
                    );
              //  return deferred.promise;
                return $localStorage.dictionary;
            }
            function getPurchase(id) {
                console.log('Fetching Purchase with id :'+id);
                var deferred = $q.defer();
                $http.get(urls.PURCHASE_SERVICE_API + id)
                    .then(
                        function (response) {
                            console.log('Fetched successfully Purchase with id :'+id);
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                            console.error('Error while loading Purchase with id :'+id);
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }
            function getStorage(id) {
                console.log('Fetching Storage with id :'+id);
                var deferred = $q.defer();
                $http.get(urls.STORAGE_SERVICE_API + id)
                    .then(
                        function (response) {
                            console.log('Fetched successfully Storage with id :'+id);
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                            console.error('Error while loading Storage with id :'+id);
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }
            function loadDiscount() {
                console.log('Fetching Discount ');
                var deferred = $q.defer();
                $http.get(urls.DISCOUNT_SERVICE_API)
                    .then(
                        function (response) {
                            console.log('Fetched successfully Discount');
                            $localStorage.discount = response.data;
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                            console.error('Error while loading Discount');
                            $localStorage.discount = 0;
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }
            function loadRelatedDiscount() {
                console.log('Fetching Discount ');
                var deferred = $q.defer();
                $http.get(urls.RELATED_DISCOUNT_SERVICE_API)
                    .then(
                        function (response) {
                            console.log('Fetched successfully Discount');
                            $localStorage.relatedDiscount = response.data;
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                            console.error('Error while loading Discount');
                            $localStorage.relatedDiscount = 0;
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }
            function getCategory(id) {
                console.log('Fetching Category with id :'+id);
                var deferred = $q.defer();
                $http.get(urls.CATEGORY_SERVICE_API + id)
                    .then(
                        function (response) {
                            console.log('Fetched successfully Category with id :'+id);
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                            console.error('Error while loading Category with id :'+id);
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }

            /*function getCurrentUser() {
                console.log('Fetching current User :');
                var deferred = $q.defer();
                $http.get(urls.CURRENT_USER)
                    .then(
                        function (response) {
                            console.log('Fetched successfully current user');
                            $localStorage.user = response.data;
                            deferred.resolve(response);
                        },
                        function (errResponse) {
                            console.error('Error while loading current user');
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }*/
            function getAllProducts(){
                return $localStorage.products;
            }
            function getAllStorage(){
                return $localStorage.storages;
            }
            function getAllPurchase() {
                return $localStorage.purchases;
            }
            function getAllCategories() {
                return $localStorage.categories;
            }
            function getAllImages() {
                return $localStorage.images;
            }
            function getBaskedStatus() {
                return $localStorage.basked;
            }
            function getBoughtStatus() {
                return $localStorage.bought;
            }
            function getDiscount() {
                return $localStorage.discount;
            }
            function getRelatedDiscount() {
                return $localStorage.relatedDiscount;
            }
            function getCurrentUser(){
                return $localStorage.currentUser;
            }
            function getPurchaseInBasked() {
                return $localStorage.purchaseInBasked;
            }
            function getRecommendedProducts() {
                return $localStorage.recommendedProducts;
            }
            function getAllMessages() {
                return $localStorage.messages;
            }
}
]);
