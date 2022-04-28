angular.module('app', ['ngStorage']).controller('indexController', function ($scope, $rootScope, $http, $localStorage) {
    const contextPath = 'http://localhost:8189/app/api/v1';

    if ($localStorage.springWebUser) {
        $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.springWebUser.token;
    }

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
            $scope.ProductsPage = response.data;
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

    $scope.showCart = function () {
        $http.get(contextPath + '/carts')
            .then(function (response) {
                $scope.CartList = response.data;
            });
    };

    $scope.addToCart = function (productId) {
        $http({
            url: contextPath + '/carts/add',
            method: 'GET',
            params: {
                id: productId
            }
        }).then(function (response) {
//            console.log('id = ' + productId)
            $scope.showCart();
        });
    };

    $scope.deleteFromCart = function (productId) {
        $http({
            url: contextPath + '/carts/delete',
            method: 'GET',
            params: {
                id: productId
            }
        }).then(function (response) {
//            console.log('id = ' + productId)
            $scope.showCart();
        });
    };

    $scope.tryToAuth = function () {
        $http.post('http://localhost:8189/app/auth', $scope.user)
            .then(function successCallback(response) {
                if (response.data.token) {
                    $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                    $localStorage.springWebUser = {username: $scope.user.username, token: response.data.token};

                    $scope.user.username = null;
                    $scope.user.password = null;
                }
            }, function errorCallback(response) {
            });
    };

    $scope.tryToLogout = function () {
        $scope.clearUser();
        if ($scope.user.username) {
            $scope.user.username = null;
        }
        if ($scope.user.password) {
            $scope.user.password = null;
        }
    };

    $scope.clearUser = function () {
        delete $localStorage.springWebUser;
        $http.defaults.headers.common.Authorization = '';
    };

    $rootScope.isUserLoggedIn = function () {
        if ($localStorage.springWebUser) {
            return true;
        } else {
            return false;
        }
    };

    $scope.showCurrentUserInfo = function () {
        $http.get('http://localhost:8189/app/api/v1/profile')
            .then(function successCallback(response) {
                alert('MY NAME IS: ' + response.data.username);
            }, function errorCallback(response) {
                alert('UNAUTHORIZED');
            });
    }

    $scope.showProducts();
    $scope.showCart();
});