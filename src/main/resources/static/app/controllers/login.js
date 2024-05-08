angular.module('JWTDemoApp')
// Creating the Angular Controller
.controller('LoginController', ['$http', '$scope', '$state', 'AuthService', '$rootScope', 'jwtHelper', '$window', function ($http, $scope, $state, AuthService, $rootScope, jwtHelper, $window) {
    $scope.form = {};
    // method for login
        // Check if the user is already logged in
        if (AuthService.subject) {
            $state.go('home');
        }
    $scope.login = function () {
        console.log($scope.form);
        // Clear existing JWT token
        $window.localStorage.removeItem('jwtToken');
        // Clear Authorization header
        delete $http.defaults.headers.common['Authorization'];
        // requesting the token by usename and password
        $http({
            url: 'auth/login',
            method: "POST",
            data: angular.toJson($scope.form),
        }).then(function (response) {
            var res = response.data;
            $scope.password = null;
            console.log(res);
            // checking if the token is available in the response
            if (res.data) {
                $scope.message = '';
                // Storing the token in local storage
                $window.localStorage.setItem('jwtToken', res.data);
                // setting the Authorization Bearer token with JWT token
                $http.defaults.headers.common['Authorization'] = 'Bearer ' + res.data;
                var data = jwtHelper.decodeToken(res.data);
                // setting the user in AuthService
                AuthService.subject = data.sub;
                AuthService.Authorities = data.Authorities;
                $rootScope.$broadcast('LoginSuccessful');
                // going to the home page
                $state.go('home');
            } else {
                // if the token is not present in the response then the
                // authentication was not successful. Setting the error message.
                $scope.message = 'Authentication Failed!';
            }
        }).catch(function (error) {
            // if authentication was not successful. Setting the error message.
            $scope.message = 'Authentication Failed!';
        });
    };
}]);
