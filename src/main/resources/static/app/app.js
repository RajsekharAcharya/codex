angular.module('JWTDemoApp', ['ui.router','angular-jwt'])

.run(['AuthService', '$transitions','jwtHelper', function (AuthService, $transitions,jwtHelper) {
    $transitions.onStart({}, function(transition) {
        // Checking if the user is logged in or not

        var token = localStorage.getItem('jwtToken');
        if (token) {
            // Decode the token to get user information
            var data = jwtHelper.decodeToken(token);
            // Set user information in AuthService
            AuthService.subject = data.sub;
            AuthService.Authorities = data.Authorities;
        }
        if (!AuthService.subject) {
            // Avoiding the infinite looping of state change
            if (transition.to().name !== 'login' && transition.to().name !== 'register') {
                return transition.router.stateService.target('login');
            }
        } else {
            // Checking if the user is authorized to view the states
            if (transition.to().data && transition.to().data.role) {
                var hasAccess = AuthService.Authorities.some(function(authority) {
                    return authority.authority === transition.to().data.role;
                });
                if (!hasAccess) {
                    return transition.router.stateService.target('access-denied');
                }
            }
        }
    });
}]);

