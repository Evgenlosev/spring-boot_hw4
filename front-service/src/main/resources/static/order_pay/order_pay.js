angular.module('market-front').controller('orderPayController', function ($scope, $http, $location, $localStorage, $routeParams) {

    $scope.loadOrder = function () {
        $http({
            url: 'http://localhost:5555/core/api/v1/orders/' + $routeParams.orderId,
            method: 'GET'
        }).then(function (response) {
            console.log(response.data);
            $scope.order = response.data;
            if ($scope.order.status != 'created'){
                alert("Вы не можете оплатить заказ в статусе: " + $scope.order.status);
                return;
            }
            $scope.renderPaymentButtons();
        });
    };

    $scope.renderPaymentButtons = function() {
        paypal.Buttons({
            createOrder: function(data, actions) {
                return fetch('http://localhost:5555/core/api/v1/paypal/create/' + $scope.order.id, {
                    method: 'post',
                    headers: {
                        'content-type': 'application/json'
                    }
                }).then(function(response) {
                    return response.text();
                });
            },

            onApprove: function(data, actions) {
                return fetch('http://localhost:5555/core/api/v1/paypal/capture/' + data.orderID, {
                    method: 'post',
                    headers: {
                        'content-type': 'application/json'
                    }
                }).then(function(response) {
                    response.text().then(msg => alert(msg));
                });
            },

            onCancel: function (data) {
                $http({
                    url: 'http://localhost:5555/core/api/v1/orders/cancel' + data.orderID,
                    method: 'GET'
                })
            },

            onError: function (err) {
                $http({
                    url: 'http://localhost:5555/core/api/v1/orders/cancel' + data.orderID,
                    method: 'GET'
                });
                console.log(err);
            }
        }).render('#paypal-buttons');
    }

    $scope.loadOrder();
});