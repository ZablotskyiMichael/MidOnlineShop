'use strict'

angular.module('crudApp').controller('ProductController',
    ['ProductService', '$scope' , function( ProductService, $scope) {

        var self = this;
        self.user={};
        self.product = {};
        self.products=[];
        self.purchase = {};
        self.purchases = [];
        self.storage = {};
        self.storages=[];
        self.welcProduct={};
        self.category={};
        self.categories = [];
        self.newProduct={};
        self.property={};
        self.images = [];
        self.image = {};
        self.currentProduct = {};
        self.newRelatedProduct = {};
        self.relatedProduct={};
        self.relatedPurchase= {};
        self.productsByCategory=[];
        self.message = {};
        self.parentMessageId = 0;
        self.messages =[];
        self.reply={};
        self.recommendedProducts=[];

        self.getRecommendedProducts = getRecommendedProducts;
        self.getStatus = getStatus ;
        self.getAllProducts = getAllProducts;
        self.getAllPurchase = getAllPurchase;
        self.getAllStorage = getAllStorage;
        self.getAllCategories = getAllCategories;
        self.getCurrentUser = getCurrentUser;
        self.getAllImages = getAllImages ;
        self.getCurrentProduct = getCurrentProduct ;
        self.getAllCategories = getAllCategories;
        self.getPurchaseByUserInBasked = getPurchaseByUserInBasked;
        self.getRelatedProduct = getRelatedProduct;
        self.getCurrentRelatedProduct=getCurrentRelatedProduct;
        self.getDiscount = getDiscount;
        self.getRelatedDiscount = getRelatedDiscount;
        self.getAllMessages = getAllMessages;

        self.issuePurchase = issuePurchase;
        self.addToBasked = addToBasked;
        self.addToBaskedRelatedProducts = addToBaskedRelatedProducts;
        self.editStorage = editStorage;
        self.editCategory = editCategory;

        self.submitChange = submitChange;
        self.submitCategory = submitCategory;
        self.createCategory = createCategory;
        self.createProduct = createProduct;
        self.createMessage = createMessage;
        self.replyMessage = replyMessage;
        self.buyAllItemInBasked = buyAllItemInBasked;

        self.updateCategory = updateCategory;
        self.removeCategory = removeCategory;


        self.setCurrentProduct = setCurrentProduct ;
        self.loadAllProducts = loadAllProducts;
        self.filterByCategory = filterByCategory;
        self.welcomeProduct = welcomeProduct;
        self.deletePurchase = deletePurchase;
        self.reset = reset;


        self.successProductMessage = '';
        self.errorProductMessage = '';
        self.doneProduct = false;
        self.successMessage = '';
        self.errorMessage = '';
        self.done = false;
        self.successRoleMessage = '';
        self.errorRoleMessage = '';
        self.RoleDone = false;
        self.successCategoryMessage = '';
        self.errorCategoryMessage = '';
        self.doneCategory = false;
        self.successAddMessage = '';
        self.errorAddMessage = '';
        self.doneAdd = false;
        self.successToBaskedRelatedMessage = '';
        self.errorToBaskedRelatedMessage = '';
        self.doneToBaskedRelated = false;
        self.successBaskedMessage = '';
        self.errorBaskedMessage = '';
        self.doneBasked = false;
        self.notDoneBasked = false;
        self.notDoneToBaskedRelated = false;
        self.successToBaskedMessage = '';
        self.errorToBaskedMessage = '';
        self.doneToBasked = false;
        self.onlyIntegers = /^\d+$/;
        self.onlyNumbers = /^\d+([,.]\d+)?$/;

        function filterByCategory(category) {
            if (category===undefined || category===null){
                ProductService.loadAllCategories();
            }else{
                ProductService.filterByCategory(category);
            }
        }
        function getRelatedProduct() {
            for (var product in ProductService.getAllProducts()) {
                if (product.id === self.purchase.product.related_product_id) {
                    return self.relatedProduct = product;
                }
            }
        }

        function getRelatedDiscount() {
            return ProductService.getRelatedDiscount();
        }
        function getAllMessages() {
            return ProductService.getAllMessages();
        }
        function getStatus(id) {
            return self.purchase.dictionary = ProductService.getStatus(id);
        }
        function getCurrentProduct() {
            return self.purchase.product = ProductService.getCurrentProduct();
        }
        function getCurrentRelatedProduct() {
            return self.relatedProduct = ProductService.getRelatedProduct()
        }
        function getCurrentUser() {
            return self.currentUser = ProductService.getCurrentUser();
        }
        function getRecommendedProducts() {
            return self.recommendedProducts = ProductService.getRecommendedProducts();
        }
        function loadAllProducts() {
      //      ProductService.loadAllProducts();
         return ProductService.loadAllProducts();
        }
        function getAllImages() {
            return ProductService.getAllImages();
        }
        function getAllCategories() {
            return ProductService.getAllCategories();
        }
        function getAllProducts(){
            return ProductService.getAllProducts();
        }
        function getAllPurchase(){
            return ProductService.getAllPurchase();
        }
        function getAllStorage(){
            return ProductService.getAllStorage();
        }
        function getAllCategories() {
            return ProductService.getAllCategories();
        }
        function getPurchaseByUserInBasked() {
            return ProductService.getPurchaseInBasked();
        }
        function getDiscount() {
            return ProductService.getDiscount();
        }
        function setCurrentProduct(u) {
            ProductService.getProduct(u.id).then(
                function (product) {
                    /*self.currentProduct = ProductService.getProduct(id);
                    self.user = ProductService.getCurrentUser();
                    self.purchase.user = self.user;
                    self.property=ProductService.getDiscount();
                    self.purchase.status_id = 'BASKED';*/
                    self.purchase.product = ProductService.getProduct(u.id);
                    self.relatedProduct = ProductService.loadRelatedProduct(u.related_product_id);
                    self.user = ProductService.getCurrentUser();
                    ProductService.loadRecommendedProducts();
                    self.purchase.user = self.user;
                    self.property=ProductService.getDiscount();
                    self.purchase.status_id = 'BASKED';

                    ProductService.loadAllMessages(u.id);

                    self.message.user =ProductService.getCurrentUser();
                    self.message.text = 'kik';
                },
                function (errResponse) {
                    console.error('Error while loading product ' + u.id + ', Error :' + errResponse.data.errorMessage);
                }
            );
        }
        function editCategory(id) {
            self.successCategoryMessage='';
            self.errorCategoryMessage='';
            ProductService.getCategory(id).then(
                function (category) {
                    self.category = category;
                },
                function (errResponse) {
                    console.error('Error while removing category ' + id + ', Error :' + errResponse.data.errorMessage);
                }
            );
        }
        function submitCategory() {
            console.log('Submitting');
            if (self.category.id === undefined || self.category.id === null) {
                console.log('Saving New category', self.category);
                createCategory(self.category);
            } else {
                updateCategory(self.category, self.category.id);
                console.log('Category updated with id ', self.category.id);
            }
        }
        function removeCategory(id){
            console.log('About to remove Category with id '+id);
            ProductService.removeCategory(id)
                .then(
                    function(){
                        console.log('Category '+id + ' removed successfully');

                    },
                    function(errResponse){
                        console.error('Error while removing Category '+id +', Error :'+errResponse.data);
                    }
                );
        }
        function createCategory(category) {
            console.log('About to create category');
            ProductService.createCategory(category)
                .then(
                    function (response) {
                        console.log('category created successfully');
                        self.successCategoryMessage = 'category created successfully';
                        self.errorCategoryMessage='';
                        self.done = true;
                        self.category={};
                        $scope.myCategoryForm.$setPristine();
                    },
                    function (errResponse) {
                        console.error('Error while creating category');
                        self.errorCategoryMessage = 'Error while creating category: ' + errResponse.data.errorMessage;
                        self.successCategoryMessage='';
                    }
                );
        }
        function createProduct() {
            console.log('About to create Product');
            self.newProduct.related_product_id = self.newRelatedProduct.id
            ProductService.createProduct(self.newProduct)
                .then(
                    function (response) {
                        console.log('Product created successfully');
                        self.successNewProductMessage = 'Product created successfully';
                        self.errorNewProductMessage='';
                        self.doneNewProduct = true;
                        self.newProduct={};
                        $scope.myNewProductForm.$setPristine();
                    },
                    function (errResponse) {
                        console.error('Error while creating Product');
                        self.errorProductMessage = 'Error while creating Product: ' + errResponse.data.errorMessage;
                        self.successProductMessage='';
                    }
                );
        }
        function createMessage() {
            console.log('About to create Message');
            self.message.parentId = self.parentMessageId;
            self.message.product = self.purchase.product;
            ProductService.createMessage(self.message)
                .then(
                    function (response) {
                        console.log('Message created successfully');
                        self.successCommentMessage = 'Message created successfully';
                        self.errorCommentMessage='';
                        self.doneComment = true;
                        self.notDoneComment = false;
                        self.message={};
                        $scope.myCommentForm.$setPristine();
                    },
                    function (errResponse) {
                        console.error('Error while creating Comment');
                        self.errorCommentMessage = 'Error while creating Comment: ' + errResponse.data.errorMessage;
                        self.successCommentMessage='';
                        self.doneComment = false;
                        self.notDoneComment = true;
                    }
                );
        }
        function replyMessage(id) {
            console.log('About to create Message');
            self.reply.parentId = self.parentMessageId;
            self.reply.product = self.purchase.product;
            self.reply.parentId = id;
            ProductService.createMessage(self.reply)
                .then(
                    function (response) {
                        console.log('Message created successfully');
                        self.reply={};
                    },
                    function (errResponse) {
                        console.error('Error while creating Comment');
                    }
                );
        }
        function updateCategory(category, id){
            console.log('About to update Category');
            ProductService.updateCategory(category, id)
                .then(
                    function (response){
                        console.log('Category updated successfully');
                        self.successCategoryMessage='Category updated successfully';
                        self.errorCategoryMessage='';
                        self.doneCategory = true;
                        $scope.myCategoryForm.$setPristine();
                    },
                    function(errResponse){
                        console.error('Error while updating Category');
                        self.errorCategoryMessage='Error while updating Category '+errResponse.data;
                        self.successCategoryMessage='';
                    }
                );
        }

        function editStorage(id) {
            self.successMessage='';
            self.errorMessage='';
            ProductService.getStorage(id).then(
                function (storage) {
                    self.storage = storage;
                    window.scroll(0,0);
                },
                function (errResponse) {
                    console.error('Error while edit storage ' + id + ', Error :' + errResponse.data);
                }
            );
        }
        function welcomeProduct (name){
            self.welcProduct.name=name;
            return name;
        }

        function submitChange() {
            console.log('Submitting');
                ProductService.updateStorage(self.storage, self.storage.id)
                .then(
                    function (response){
                        console.log('Storage updated successfully');
                        self.successAddMessage='Storage updated successfully';
                        self.errorAddMessage='';
                        self.doneAdd = true;
                        $scope.myAddForm.$setPristine();
                        self.storage={};
                    },
                    function(errResponse){
                        console.error('Error while updating Storage');
                        self.errorAddMessage='Error while updating Storage '+errResponse.data;
                        self.successAddMessage='';
                    }
                );

        }
        function deletePurchase(id) {
            ProductService.deletePurchase(id).then(
                function(){
                    console.log('Purchase '+id + ' removed successfully');
                    self.storages=ProductService.loadAllStorage;
                },
                function(errResponse){
                    console.error('Error while removing Purchase '+id +', Error :'+errResponse.data.errorMessage);
                }
            );
        }
        function issuePurchase(id) {
            self.successMessage='';
            self.errorMessage='';
           /* ProductService.getStorage(self.purchase.product.id).then(
                function (storage) {
                    self.storage = storage;
                },
                function (errResponse) {
                    console.error('Error while removing user ' + id + ', Error :' + errResponse.data);
                }
            )*/
           self.user = ProductService.getCurrentUser();
           self.purchase.dictionary = ProductService.getBaskedStatus();

           self.purchase.user = self.user;
           self.property=ProductService.getDiscount();
            ProductService.getProduct(id).then(
                function (product) {
                    self.product = product;
                    self.purchase.product=self.product;
                    window.scroll(0,0);
                },
                function (errResponse) {
                    console.error('Error while removing user ' + id + ', Error :' + errResponse.data);
                }
            )
            ProductService.getRelatedProduct(id).then(
                function (product) {
                    self.relatedProduct = product;
                },
                function (errResponse) {
                    console.error('Error while loading related product ' + id + ', Error :' + errResponse.data);
                }
            )

            ;
        }
        function reset(){
            self.successCategoryMessage='';
            self.errorCategoryMessage='';
            self.successRoleMessage='';
            self.errorRoleMessage='';
            self.category={};
            $scope.myForm.$setPristine();
            $scope.myCategoryForm.$setPristine();//reset Form
        }

        function addToBasked() {
            console.log('Creating purchase');
            self.purchase.user = ProductService.getCurrentUser();
            self.purchase.dictionary = ProductService.getBaskedStatus();
            self.purchase.total_price = self.purchase.product.price*self.purchase.count;
            ProductService.createPurchase(self.purchase).then(
                function (product) {
                    console.log('Product in basked');
                    self.successToBaskedMessage = 'Product in basked'
                    self.errorToBaskedMessage='';
                    self.doneToBasked = true;
                    self.errorToBasked = false;

                    //  $scope.myToBaskedForm.$setPristine();
                    self.purchase.count = undefined;
                },
                function (errResponse) {
                    console.error('Error while adding product to basked');
                    self.successToBaskedMessage = ''
                    self.errorToBaskedMessage='Error while adding product to basked.'+errResponse.data.errorMessage;
                    self.doneToBasked = false;
                    self.errorToBasked = true;

                }
            )
        }
        function addToBaskedRelatedProducts() {
           var  relatedDiscount  = ProductService.getRelatedDiscount().value;
            console.log('Creating purchase');
            self.purchase.user = ProductService.getCurrentUser();
            self.purchase.dictionary = ProductService.getBaskedStatus();
            self.purchase.count = 1;
            self.purchase.total_price = self.purchase.product.price*self.purchase.count -self.purchase.product.price*relatedDiscount/100;
           createRelatedPurchase(self.purchase);
            self.relatedPurchase.user = ProductService.getCurrentUser();
            self.relatedPurchase.dictionary = ProductService.getBaskedStatus();
            self.relatedPurchase.count = 1;
            self.relatedPurchase.product = self.relatedProduct;
            self.relatedPurchase.total_price = self.relatedPurchase.product.price*self.relatedPurchase.count- self.relatedPurchase.product.price*relatedDiscount/100;
           createRelatedPurchase(self.relatedPurchase);
        }
        function createRelatedPurchase(purchase) {
            ProductService.createPurchase(purchase).then(
                function (product) {
                    console.log('Product in basked');
                    self.successToBaskedRelatedMessage = 'Products in basked'
                    self.errorToBaskedMessage='';
                    self.doneToBaskedRelated = true;
                    self.notDoneToBaskedRelated = false;

                    //  $scope.myToBaskedForm.$setPristine();
                    self.purchase.count = undefined;
                },
                function (errResponse) {
                    console.error('Error while adding product to basked' + ', Error :' + errResponse.data);
                    self.successToBaskedRelatedMessage = ''
                    self.errorToBaskedRelatedMessage='' + errResponse.data;
                    self.doneToBaskedRelated = false;
                    self.notDoneToBaskedRelated = true;

                }
            )
        }
        function buyAllItemInBasked() {
            console.log('Updating status of purchases in basked')
            ProductService.buyBasked().then(
                function (response){
                    console.log('Basked was bought successfully');
                    self.successBaskedMessage='You have successfully purchased all products';
                    self.doneBasked=true;
                    self.notDoneBasked=false;
                },
                function(errResponse){
                    console.error('Error while purchasing products');
                    self.errorBaskedMessage=''+errResponse.data.errorMessage;
                    self.successBaskedMessage='';
                    self.notDoneBasked=true;
                    self.doneBasked=false;
                }
            );
        }

}


        ]);