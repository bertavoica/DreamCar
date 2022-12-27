(function () {
    'use strict';

    angular
        .module('app')
        .controller('CarComponentController', CarComponentController);
    CarComponentController.$inject = ['$http', '$scope', '$window'];

    function CarComponentController ($http, $scope, $window) {
        let vm = this;

        vm.components = [];
        vm.addCarComponent = addCarComponent;
        vm.openAddCarComponentModal = openAddCarComponentModal;
        vm.deleteCarComponent = deleteCarComponent;
        vm.searchCarComponent = searchCarComponent;

        // View component
        vm.selectedComponent = null;
        vm.openViewComponentDetails = openViewComponentDetails;

        // Placing offers
        vm.openPlaceOfferModal = openPlaceOfferModal;
        vm.placeOffer = placeOffer;
        vm.offers = [];

        // Utils
        vm.formatDate = formatDate;

        init();

        function init() {
            $scope.carComponentName = "";
            resetAddComponentModal();
            resetPlaceOfferModal();
            searchCarComponent();
        }

        function placeOffer() {
            let url = "/component-api/offer";

            if ($scope.carComponentOfferedPriceModel === 0) {
                alert('Offered price must be bigger than 0');
                return;
            }

            url += "?name=" + encodeURIComponent(vm.selectedComponent.name);
            url += "&price=" + encodeURIComponent($scope.carComponentOfferedPriceModel);

            let request = $http.put(url);
            request.then(function(response) {
                searchCarComponent();
                resetPlaceOfferModal();
                $('#placeOfferModal').modal('hide');
            }, function errorCallback(response) {
                resetPlaceOfferModal();
                $('#placeOfferModal').modal('hide');
                alert(response.data.message)
            });
        }

        function resetPlaceOfferModal() {
            $scope.carComponentOfferedPriceModel = 0;
        }

        function openPlaceOfferModal(inputComponent) {
            vm.selectedComponent = inputComponent;
            $('#placeOfferModal').modal('show');
        }

        function openViewComponentDetails(inputComponent) {
            vm.selectedComponent = inputComponent;
            getOffers(inputComponent);
            $('#viewCarComponentDataModal').modal('show');
        }

        function getOffers(component) {
            let url = "/component-api/offer?name=" + encodeURIComponent(component.name);

            let request = $http.get(url);
            request.then(function(response){
                vm.offers = response.data;
            });
        }

        function formatDate(inputDate) {
            if (inputDate === null || inputDate === undefined) {
                return '';
            }
            return inputDate.substr(0, 10) + ' ' + inputDate.substr(11, 8);
        }

        function resetAddComponentModal() {
            $scope.carComponentNameModel = "";
            $scope.carComponentDescriptionModel = "";
            $scope.carComponentTimeoutModel = 10;
            $scope.carComponentPriceModel = 100;
            $scope.carComponentQuantityModel = 1;
        }

        function searchCarComponent() {
            let url = "/component-api";
            if ($scope.carComponentName !== '') {
                url += "?name=" + encodeURIComponent($scope.carComponentName);
            }

            let request = $http.get(url);
            request.then(function(response){
                vm.components = response.data;
            });
        }

        function addCarComponent() {
            let url = "/component-api";

            if ($scope.carComponentNameModel === '') {
                alert('Car component name is mandatory!');
                return;
            }

            if ($scope.carComponentDescriptionModel === '') {
                alert('Car component description is mandatory!');
                return;
            }

            if ($scope.carComponentTimeoutModel === 0) {
                alert('Car component timeout must be bigger than 0');
                return;
            }

            if ($scope.carComponentPriceModel === 0) {
                alert('Car component price must be bigger than 0');
                return;
            }

            if ($scope.carComponentQuantityModel === 0) {
                alert('Car components quantity must be bigger than 0');
                return;
            }

            url += "?name=" + encodeURIComponent($scope.carComponentNameModel);
            url += "&description=" + encodeURIComponent($scope.carComponentDescriptionModel);
            url += "&timeout=" + encodeURIComponent($scope.carComponentTimeoutModel);
            url += "&price=" + encodeURIComponent($scope.carComponentPriceModel);
            url += "&quantity=" + encodeURIComponent($scope.carComponentQuantityModel);

            let request = $http.put(url);
            request.then(function(response) {
                searchCarComponent();
                resetAddComponentModal();
                $('#addCarComponentDataModal').modal('hide');
            }, function errorCallback(response) {
                alert(response.data.message);
            });
        }

        function openAddCarComponentModal() {
            $('#addCarComponentDataModal').modal('show');
        }

        function deleteCarComponent(carComponentName) {
            let url = "/component-api?name=" + encodeURIComponent(carComponentName);
            let request = $http.delete(url);
            request.then(function(response){
                searchCarComponent();
            });
        }
    }
})();