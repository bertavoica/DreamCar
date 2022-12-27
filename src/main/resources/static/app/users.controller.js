(function () {
    'use strict';

    angular
        .module('app')
        .controller('UsersController', UsersController);
    UsersController.$inject = ['$http', '$scope', '$window'];

    function UsersController ($http, $scope, $window) {
        let vm = this;
    }
})();