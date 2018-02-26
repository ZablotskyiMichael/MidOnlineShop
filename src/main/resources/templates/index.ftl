<!DOCTYPE html>

<html lang="en" ng-app="crudApp">
    <head>
        <title>${title}</title>
        <link href="css/bootstrap.css" rel="stylesheet"/>
        <link href="css/app.css" rel="stylesheet"/>
    </head>
    <body>
    <a ui-sref="home" ui-sref-active="active">Administrator</a>
    <a ui-sref="storage" ui-sref-active="active">Storage</a>
    <a ui-sref="category" ui-sref-active="active">Category</a>
    <a ui-sref="properties" ui-sref-active="active">Properties</a>
    <a ui-sref="recommended" ui-sref-active="active">welcome</a>
    <a ui-sref="products" ui-sref-active="active">Products</a>
    <a ui-sref="user" ui-sref-active="active">User</a>
    <a ui-sref="basked" ui-sref-active="active">Basked</a>
    <div ui-view></div>
        <script src="js/lib/angular.min.js" ></script>
        <script src="js/lib/angular-ui-router.min.js" ></script>
        <script src="js/lib/localforage.min.js" ></script>
        <script src="js/lib/ngStorage.min.js"></script>
        <script src="js/app/app.js"></script>
        <script src="js/app/UserService.js"></script>
        <script src="js/app/UserController.js"></script>
        <script src="js/app/ProductController.js"></script>
        <script src="js/app/ProductService.js"></script>

    </body>
</html>