angular.module('app', []).controller('indexController', function ($scope, $http) {
    const contextPath = 'http://localhost:8189/app';

    $scope.showProducts = function () {
        $http.get(contextPath + '/products')
            .then(function (response) {
                $scope.ProductsList = response.data;

            });
    };

    $scope.deleteProduct = function (productId) {
        $http.get(contextPath + '/products/delete/' + productId)
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
                $scope.min = null;
                $scope.max = null;
                $scope.showProducts();

            });
    };

    $scope.showProducts();
});