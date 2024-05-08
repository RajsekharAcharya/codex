angular.module('JWTDemoApp')
.service('TokenExpirationService', ['$interval', 'AuthService', '$rootScope', '$state', '$window', 'jwtHelper', function ($interval, AuthService, $rootScope, $state, $window, jwtHelper) {
    var tokenCheckInterval;


    // Initialize token expiration check
    startTokenExpirationCheck();
    
    // Function to check token expiration
    function checkTokenExpiration() {
        if ($state.current.name !== 'login' && $state.current.name !== 'register') {
            var token = $window.localStorage.getItem('jwtToken');
            if (token) {
                var tokenPayload = jwtHelper.decodeToken(token);
                var expirationDate = new Date(tokenPayload.exp * 1000);
                var currentDate = new Date();
                // Check if the token is expired
                if (currentDate > expirationDate) {
                    // Token is expired, logout the user
                    AuthService.subject = null;
                    AuthService.Authorities = null;
                    $window.localStorage.removeItem('jwtToken');
                    $rootScope.$broadcast('LogoutSuccessful');
                    $state.go('login');
                }
            }

        }

    }

    // Start token expiration check
    function startTokenExpirationCheck() {
        // Check if not transitioning to login or register states
        tokenCheckInterval = $interval(checkTokenExpiration, 2000); // Check every 2 seconds
    }


}]);
