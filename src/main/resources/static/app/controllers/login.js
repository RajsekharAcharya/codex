angular.module('JWTDemoApp')
// Creating the Angular Controller
.controller('LoginController', ['$http', '$scope', '$state', 'AuthService', '$rootScope', function ($http, $scope, $state, AuthService, $rootScope) {

    $scope.form = {};
    // method for login
    $scope.login = function () {
        console.log($scope.form);
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
            if (res.data.token) {
                $scope.message = '';
                // setting the Authorization Bearer token with JWT token
                $http.defaults.headers.common['Authorization'] = 'Bearer ' + res.data.token;

                // setting the user in AuthService
                AuthService.user = res.data.user;
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
