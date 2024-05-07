angular.module('JWTDemoApp')
.service('TokenExpirationService', ['$interval', 'AuthService', '$rootScope', '$state', '$window', 'jwtHelper', function ($interval, AuthService, $rootScope, $state, $window, jwtHelper) {
    var tokenCheckInterval;

    // Function to check token expiration
    function checkTokenExpiration() {
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

    // Start token expiration check
    function startTokenExpirationCheck() {
        tokenCheckInterval = $interval(checkTokenExpiration, 2000); // Check every 2 seconds
    }

    // Stop token expiration check
    function stopTokenExpirationCheck() {
        if (angular.isDefined(tokenCheckInterval)) {
            $interval.cancel(tokenCheckInterval);
            tokenCheckInterval = undefined;
        }
    }

    // Initialize token expiration check
    startTokenExpirationCheck();

    // Listen for state change events
    $rootScope.$on('$stateChangeStart', function (event, toState, toParams, fromState, fromParams) {
        // Stop token expiration check when transitioning to login or register states
        if (toState.name === 'login' || toState.name === 'register') {
            stopTokenExpirationCheck();
        } else {
            // Resume token expiration check for other states
            startTokenExpirationCheck();
        }
    });

    // Cleanup when the application is destroyed
    $rootScope.$on('$destroy', function () {
        stopTokenExpirationCheck();
    });
}]);
