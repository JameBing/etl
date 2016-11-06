adminLteApp.controller('LogCtrl', function ($scope, $http) {

    function requestLogin() {
        var username = $scope.username;
        if (username == null || username == "" || username == "undefined") {
            $scope.alterMsg = "用户名不能为空";
            return;
        }
        var password = $scope.password;
        if (password == null || password == "" || password == "undefined") {
            $scope.alterMsg = "用户密码不能为空";
            return;
        }

        $scope.loading = true;
        $http({url:"/mark/cms/login.php", method:"GET", params:{"username":username,"password":password}})
            .success(function(data) {
                if (data.status != 0) {
                    $scope.errMsg = data.message;
                    $scope.loading = false;
                    return;
                }
                location.href = "/console/main.html";
            })
            .error (function(data,header) {
                if (header == "403"){
                    $scope.errMsg = "非白名单，拒绝访问";
                    $scope.loading = false;
                    return;
                }
            });

    }

    $scope.submitKeyup = function(e) {
        var keycode = window.event?e.keyCode:e.which;
        if (keycode == 13)
            requestLogin();
    }

    $scope.submit = function() {
        requestLogin();
    }
});
