angular.module('JWTDemoApp')
// Creating the Angular Controller
    .controller('NavController', function ($http, $scope, AuthService, $state, $rootScope,$window) {
        $scope.$on('LoginSuccessful', function () {
            $scope.user = AuthService.subject;
        });
        $scope.user = AuthService.subject;
        $scope.$on('LogoutSuccessful', function () {
            $scope.user = null;
        });
        $scope.logout = function () {
            AuthService.user = null;
            $window.localStorage.removeItem('jwtToken')
            $rootScope.$broadcast('LogoutSuccessful');
            $state.go('login');
        };
    });
