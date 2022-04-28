angular.module('market-front').controller('ordersController', function ($scope, $http, $location, $localStorage) {
    const contextPath = 'http://localhost:5555/core/';

    $scope.loadOrders = function () {
        $http.get(contextPath + 'api/v1/orders')
            .then(function (response) {
                $scope.MyOrders = response.data;
            });
    }

    $scope.goToPay = function (orderId) {
        $http({
            url: 'http://localhost:5555/core/api/v1/orders/' + orderId,
            method: 'GET'
        }).then(function (response) {
            $scope.order = response.data;
            if ($scope.order.status != 'created'){
                alert("Вы не можете оплатить заказ в статусе: " + $scope.order.status);
                return;
            }
            $location.path('/order_pay/' + orderId);
        });
    }

    $scope.loadOrders();
});