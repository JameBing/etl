adminLteApp.controller('UserCtrl', function ($scope, $http) {
    $scope.loading = true;
    $http({url:"/mark/cms/findAllUser.php", method:"GET"})
        .success(function(data) {
            $scope.userList = data;
        });
    $scope.expireIn = "0";
    $scope.appKey = "";
    $scope.appSecret = "";

    $scope.addUser = function(){
        var username = $scope.username;
        if (username == null || username == "" || username == "undefined") {
            $scope.alterMsg = "The username cannot be empty";
            return;
        }
        var password = $scope.password;
        if (password == null || password == "" || password == "undefined") {
            $scope.alterMsg = "The password cannot be empty";
            return;
        }
        var expireIn = $scope.expireIn;

        var appKey = $scope.appKey;
        var appSecret = $scope.appSecret;
        if(username == "" || username == undefined || password == "" || password == undefined || expireIn == "" || expireIn == undefined) {
            $scope.alterMsg = "Please enter the complete information";
            return;
        }

        $http({url:"/mark/cms/addUser.php", method:"GET",params:{"username":username,"password":password,"expireIn":expireIn}})
            .success(function(data) {
                if (data.status != 0) {
                    $scope.errMsg = data.message;
                    $scope.loading = false;
                    return;
                }
                $('#addUser').modal('hide');
                window.top.location.reload();
            });
    }

    $scope.delUser = function(username,password,expireIn){
        $http({url:"/mark/cms/delUser.php", method:"GET",params:{"username":username,"password":password,"expireIn":expireIn}})
            .success(function(data) {
                if (data.status != 0) {
                    $scope.errMsg = data.message;
                    $scope.loading = false;
                    return;
                }
                $scope.alterMsg = "删除成功";
                window.top.location.reload();
            });
    }

});
