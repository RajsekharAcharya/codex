angular.module('JWTDemoApp', ['ui.router'])

.run(['AuthService', '$transitions', function (AuthService, $transitions) {
    $transitions.onStart({}, function(transition) {
        // Checking if the user is logged in or not
        console.log('Transition started');
        if (!AuthService.user) {
            // Avoiding the infinite looping of state change
            if (transition.to().name !== 'login' && transition.to().name !== 'register') {
                return transition.router.stateService.target('login');
            }
        } else {
            // Checking if the user is authorized to view the states
            if (transition.to().data && transition.to().data.role) {
                var hasAccess = AuthService.user.roles.includes(transition.to().data.role);
                if (!hasAccess) {
                    return transition.router.stateService.target('access-denied');
                }
            }
        }
    });
}]);

