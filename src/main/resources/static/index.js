angular.module('app', []).controller('indexController', function ($scope, $http) {
    const contextPath = 'http://localhost:8189/app/api/v1';

    $scope.showProducts = function () {
        $http({
            url: contextPath + '/products',
            method: 'GET',
            params: {
                title_part: $scope.filter ? $scope.filter.title_part : null,
                min_price: $scope.filter ? $scope.filter.min_price : null,
                max_price: $scope.filter ? $scope.filter.max_price : null
            }
        }).then(function (response) {
            $scope.ProductsList = response.data.content;

        });
    };

    $scope.deleteProduct = function (productId) {
        $http.delete(contextPath + '/products/' + productId)
            .then(function (response) {
                $scope.showProducts();
            });
    }


    $scope.changePrice = function (productId, delta) {
        $http({
            url: contextPath + '/products/change_price',
            method: 'GET',
            params: {
                productId: productId,
                delta: delta
            }
        }).then(function (response) {
            $scope.showProducts();
        });
    }

    $scope.filterProductList = function (min, max) {
        $http({
            url: contextPath + '/products/price_between',
            method: 'GET',
            params: {
                min: min,
                max: max
            }
        }).then(function (response) {
            $scope.ProductsList = response.data;
        });
    }

    $scope.reset = function () {
        $http.get(contextPath + '/products')
            .then(function (response) {
                $scope.filter.title_part = null;
                $scope.filter.min_price = null;
                $scope.filter.max_price = null;
                $scope.showProducts();

            });
    };

    $scope.createProduct = function () {
        $http.put(contextPath + '/products', $scope.newProduct)
            .then(function (response) {
                alert('Продукт ' + response.data.title + ' успешно добавлен')
                $scope.showProducts();
                $scope.newProduct.title = null;
                $scope.newProduct.price = null;

            });
    };

    $scope.showProducts();
});