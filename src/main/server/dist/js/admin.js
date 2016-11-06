/**
 *
 * Created by wangjun on 8/9/16.
 */
'use strict';

var adminLteApp = angular.module("adminLteApp", ['ngRoute']);
//var adminLteApp = angular.module("adminLteApp", ['ngRoute','ngFileUpload']);
/*var adminLteApp = angular.module("adminLteApp", ['ngRoute']).directive('fileModel', ['$parse', function ($parse) {
    return {
        restrict: 'A',
        link: function(scope, element, attrs, ngModel) {
            var model = $parse(attrs.fileModel);
            var modelSetter = model.assign;
            element.bind('change', function(event){
                scope.$apply(function(){
                    modelSetter(scope, element[0].files[0]);
                });
                //附件预览
                scope.file = (event.srcElement || event.target).files[0];
                scope.getFile();
            });
        }
    };
}]);*/
adminLteApp.config(function($routeProvider) {
    $routeProvider
        .when('/', {
            templateUrl: 'views/dashboard.html',
            controller: 'DashboardCtrl'
        })
        .when('/jdAuth', {
            templateUrl: 'views/jdauth.html',
            controller: 'JdCtrl'
        })
        .when('/jdControl', {
            templateUrl: 'views/jdControl.html',
            controller: 'JdControlCtrl'
        })
        .when('/jdOrder', {
            templateUrl: 'views/jdOrder.html',
            controller: 'JdOrderCtrl'
        })
        .when('/jdParty', {
            templateUrl: 'views/jdParty.html',
            controller: 'JdPartyCtrl'
        })
        .when('/tmallControl', {
            templateUrl: 'views/tmallControl.html',
            controller: 'TmallControlCtrl'
        })
        .when('/tmallOrder', {
            templateUrl: 'views/tmallOrder.html',
            controller: 'TmallOrderCtrl'
        })
        .when('/tmallRefund/:refund_id', {
            templateUrl: 'views/tmallRefund.html',
            controller: 'TmallRefundCtrl'
        })
        .when('/z8Control', {
            templateUrl: 'views/z8Control.html',
            controller: 'Z8ControlCtrl'
        })
        .when('/z8Order', {
            templateUrl: 'views/z8Order.html',
            controller: 'Z8OrderCtrl'
        })
        .when('/configure', {
            templateUrl: 'views/configure.html',
            controller: 'ConfigureCtrl'
        })
        .when('/error', {
            templateUrl: 'views/error.html',
            controller: 'ErrorCtrl'
        })
        .otherwise({
            redirectTo: '/'
        });
});

adminLteApp.controller('MenuCtrl', function ($scope, $http) {
    $scope.loading = true;
    $http({url:"/mark/menu/policy.php",method:"GET"}).success(function(data) {
        $scope.loading = false;
        $scope.menus = data;
    });
});

adminLteApp.controller('DashboardCtrl', function ($scope, $http) {
    $scope.name = "zhangsan";
    //console.log("DashboardCtrl");
});

adminLteApp.controller('JdCtrl', function ($scope, $http) {

    //console.log("JdCtrl");
});

adminLteApp.controller('JdControlCtrl', function ($scope, $http) {
    $scope.interval = 60000;
    $scope.scheduleCount = 0;

    $http({url:"/mark/jd/jdControl.php",method:"GET"}).success(function(data) {
        if (null == data || undefined == data.jdScheduleStatus) {
            $scope.startup = false;
        } else {
            $scope.startupTime = data.jdScheduleStatus.startupTime;
            $scope.shutdownTime = data.jdScheduleStatus.shutdownTime;
            $scope.startup = data.jdScheduleStatus.startup;
        }
    });

    $scope.startListening = function(){
        $http({ method : "POST",url : '/mark/jd/startListening.php'})
            .success(function(data){
                //console.log("startListening",data);
                if(data.status == 0){
                    $('#foo').popover({
                            trigger: 'manual'
                        })
                        .on('focus', function() {
                            // 获得焦点时隐藏
                            $(this).popover('hide');
                        })
                        .on('blur', function() {
                            // 失去焦点时显示
                            $(this).popover('show');
                        });
                    $('.play').popover({content:'开启监听成功', trigger:'manual', delay:{ show: 500, hide: 100 }});
                    $('.play').popover('show');
                    $scope.startup = true;
                }else{
                    $('.play').popover({content:'开启监听失败',delay:{ show: 500, hide: 100 }});
                    $('.play').popover('show');
                }
            });
    }

    $scope.batch = function(){
        var timeRange = $("#reservationtime").val();
        if ("" == timeRange) {
            $scope.overlayStatus = false;
            $scope.status = true;
            $scope.msg = '请选择时间区间!';
            return;
        }

        var startDate;
        var endDate;
        if (timeRange !=null && timeRange != "") {
            var startStr = timeRange.substring(0,16);
            var endStr = timeRange.substring(19,timeRange.length);
            startDate = startStr+':00';
            endDate = endStr+':59';
        }
        $scope.loading = true;
        $scope.disabled = true;
        var params = {
            status:"",
            startDate:startDate,
            endDate:endDate};
        $http({ method : "POST", url : '/mark/jd/fixOrder.php', params : params})
            .success(function(data){
                $scope.loading = false;
                $scope.disabled = false;
                $scope.message = "本次补单："+data.message+" 条";

            });
    }


    $scope.stopListening = function(){
        $http({ method : "POST",url : '/mark/jd/stopListening.php'})
            .success(function(data){
                //console.log("stopListening",data);
                if(data.status == 0){
                    $('.stop').popover({content:'关闭监听成功',delay:{ show: 500, hide: 100 }});
                    $('.stop').popover('show');
                    $scope.startup = false;
                }else{
                    $('.stop').popover({content:'关闭监听失败',delay:{ show: 500, hide: 100 }});
                    $('.stop').popover('show');
                }
            });
    }

    $scope.changeScheduleTime = function(){
        $http({method : "POST", url : '/mark/jd/changeScheduleTime.php', params : {interval : $scope.interval}})
            .success(function(data){
                if(data.status == 0){
                    $scope.startup = true;
                }
            });
    }

    $scope.clean = function(){
        $http({method : "POST", url : '/mark/jd/cleanSchedule.php'})
            .success(function(data){
                $scope.scheduleCount = 0;
            });
    }

    $scope.clocl = {now: new Date()};

    var updateClock = function(){
        $('.play').popover('hide');
        $('.stop').popover('hide');
    }

    setInterval(function(){
        $scope.$apply(updateClock);
    },2000);

    updateClock();
});

adminLteApp.controller('JdOrderCtrl', function ($scope, $http, $location) {

    $scope.selected = {status:'所有状态',value:''};
    //$scope.orderStatus = [
    //    {status:'All',value:''},
    //    {status:'WAIT_SELLER_STOCK_OUT',value:'WAIT_SELLER_STOCK_OUT'},
    //    {status:'WAIT_GOODS_RECEIVE_CONFIRM',value:'WAIT_GOODS_RECEIVE_CONFIRM'},
    //    {status:'FINISHED_L',value:'FINISHED_L'},
    //    {status:'TRADE_CANCELED',value:'TRADE_CANCELED'},
    //    {status:'已锁定',value:'LOCKED'},
    //    {status:'暂停',value:'PAUSE'}
    //];
    $scope.orderStatus = [
        {status:'所有状态',value:''},
        {status:'等待出库',value:'WAIT_SELLER_STOCK_OUT'},
        {status:'等待确认收货',value:'WAIT_GOODS_RECEIVE_CONFIRM'},
        {status:'已完成',value:'FINISHED_L'},
        {status:'已取消',value:'TRADE_CANCELED'},
        {status:'已锁定',value:'LOCKED'},
        {status:'暂停',value:'PAUSE'}
    ];

    //当前页
    $scope.currentPage = 1;
    //每页显示条数
    $scope.pageSize = 20;
    //总数
    $scope.totalSize = 0;
    //总页数
    $scope.totalPages = 0;

    $scope.select_status = true;//显示所有状态
    $scope.showStatus = function(){
        $scope.select_status = false;//隐藏所有状态
    };
    $scope.overlayStatus = false;//查询进度条初始化
    $scope.status = false;//提示条初始化

    //分页查询
    $scope.getListByPage = function() {
        $scope.overlayStatus = true;
        $scope.select_status = false;//隐藏所有状态

        var skuId = $scope.skuId;
        var orderId = $scope.orderId;
        var orderState = $scope.selected.value;
        var productName = $scope.productName;
        var telephone = $scope.telephone;
        var timeRange = $("#reservationtime").val();
        var startDate;
        var endDate;
        if (timeRange !=null && timeRange != "") {
            var startStr = timeRange.substring(0,16);
            var endStr = timeRange.substring(19,timeRange.length);
            startDate = startStr+':00';
            endDate = endStr+':59';
        }
        var params = { currentPage:$scope.currentPage,
            pageSize:$scope.pageSize,
            skuId:skuId,
            orderId:orderId,
            orderState:orderState,
            productName:productName,
            telephone:telephone,
            startDate:startDate,
            endDate:endDate};
        //console.log(params);
        $http({url:"/mark/jd/getHistoryOrder.php", method:"POST", params:params})
         .success(function(data) {
            $scope.overlayStatus = false;
            var result = data;
            if(result.pageDataList.length == 0){
                $scope.status = true;
                $scope.msg = 'no order data!';

                $scope.currentPage = 1;
                $scope.totalSize = 0;
                $scope.totalPages = 1;
                $scope.oderList = null;
            }else{
                $scope.status = false;
                $scope.currentPage = parseInt(result.currentPage);
                $scope.totalSize = result.totalNum;
                $scope.totalPages = result.totalPage;

                $scope.oderList = result.pageDataList;
                angular.forEach($scope.oderList,function(order_info,oindex,array){
                    order_info.orderstate = order_info.order_state;
                    /*if(order_info.order_state == ''){
                        order_info.orderstate = '所有状态';
                    }
                    if(order_info.order_state == 'WAIT_SELLER_STOCK_OUT'){
                        order_info.orderstate = '等待出库';
                    }
                    if(order_info.order_state == 'WAIT_GOODS_RECEIVE_CONFIRM'){
                        order_info.orderstate = '等待确认收货';
                    }
                    if(order_info.order_state == 'FINISHED_L'){
                        order_info.orderstate = '完成';
                    }
                    if(order_info.order_state == 'TRADE_CANCELED'){
                        order_info.orderstate = '取消';
                    }*/
                    /*if(order_info.order_state == 'LOCKED'){
                     order_info.orderstate = '已锁定';
                     }
                     if(order_info.order_state == 'PAUSE'){
                     order_info.orderstate = '暂停';
                     }*/
                    var order_item = angular.fromJson(order_info.consignee_info);//收货信息
                    order_info.fullname = order_item.fullname;
                    order_info.mobile = order_item.mobile;
                    order_info.province = order_item.province;
                    order_info.city = order_item.city;
                    order_info.county = order_item.county;
                    order_info.full_address = order_item.fullAddress;
                });
            }
         })
        .error(function(data,header,config,status) {
            if(header == "777"){
                location.href="/console";
                return;
            }
            location.href="/console/main.html#/error?message=Tmall authorize callback error";
        });
    }

    //查询订单
    $scope.queryJdOrder = function (){
        $scope.currentPage = parseInt(1);//当前页
        $scope.getListByPage();
    };

    //初始化加载
    setTimeout($scope.queryJdOrder,500);

    //首页
    $scope.prevPage = function(){
        if ($scope.currentPage == 1) {
            return;
        } else {
            $scope.currentPage = parseInt(1);
            $scope.getListByPage();
        }
    };
    //上一页
    $scope.pageUp = function(){
        if ($scope.currentPage == 1) {
            return;
        } else {
            $scope.currentPage = parseInt($scope.currentPage) - 1;
            $scope.getListByPage();
        }
    };
    //下一页
    $scope.pageDown = function(){
        if ($scope.currentPage == $scope.totalPages) {
            return;
        } else {
            $scope.currentPage = parseInt($scope.currentPage) + 1;
            $scope.getListByPage();
        }
    };
    //尾页
    $scope.nextPage = function(){
        if ($scope.currentPage == $scope.totalPages) {
            return;
        } else {
            $scope.currentPage = parseInt($scope.totalPages);
            $scope.getListByPage();
        }
    };

    $scope.pageSizeNum = $scope.pageSize;//每页显示数
    $scope.pageLength = function(){
        if($scope.pageSizeNum == undefined){
            $scope.pageSize = 20;
        } else{
            $scope.pageSize = $scope.pageSizeNum;
        }
        $scope.currentPage = parseInt(1);//当前页
        $scope.getListByPage();
    }

});

adminLteApp.controller('JdPartyCtrl', function ($scope, $http) {
    //当前页
    $scope.currentPage = 1;
    //每页显示条数
    $scope.pageSize = 20;
    //总数
    $scope.totalSize = 0;
    //总页数
    $scope.totalPages = 0;

    $scope.overlayStatus = false;//查询进度条初始化
    $scope.status = false;//提示条初始化

    //分页查询
    $scope.getListByPage = function() {
        $scope.overlayStatus = true;
        /*var dateRange = $("#reservation").val();
        var startDate;
        var endDate;
        if (dateRange !=null && dateRange != "") {
            var startStr = dateRange.substring(0,10);
            var endStr = dateRange.substring(13,dateRange.length);
            startDate = startStr+" 00:00:00";
            endDate = endStr+" 23:59:59";
        }
        var grade=JSON.stringify($scope.grade);
        //console.log(grade);
        var minTradeCount=$scope.minTradeCount;
        var maxTradeCount=$scope.maxTradeCount;
        if(undefined != minTradeCount && undefined != maxTradeCount){
            if(parseInt(minTradeCount)>parseInt(maxTradeCount)){
                $scope.status = true;
                $scope.msg = 'MinTradeCount not great MaxTradeCount!';
                return;
            }
        }
        if(undefined != minTradeCount && undefined == maxTradeCount){
            $scope.status = true;
            $scope.msg = 'MaxTradeCount not null!';
            return;
        }
        if(undefined == minTradeCount && undefined != maxTradeCount){
            $scope.status = true;
            $scope.msg = 'MinTradeCount not null!';
            return;
        }
        var minTradeAmount=$scope.minTradeAmount;
        var avePrice=$scope.avePrice;
        var params = { currentPage:$scope.currentPage,
            pageSize:$scope.pageSize,
            customerPin:$scope.customerPin,
            grade:grade,
            minTradeTime:startDate,
            maxTradeTime:endDate,
            minTradeCount:minTradeCount,
            maxTradeCount:maxTradeCount,
            minTradeAmount:minTradeAmount,
            avePrice:avePrice
            };*/
        var params = { currentPage:$scope.currentPage,
            pageSize:$scope.pageSize
            };
        //console.log(params);
        $http({url:"/mark/jd/getCrmMember.php", method:"POST", params:params})
            .success(function(data) {
                $scope.overlayStatus = false;
                var result = data;
                if(result.pageDataList.length == 0){
                    $scope.status = true;
                    $scope.msg = 'no order data!';

                    $scope.currentPage = 1;
                    $scope.totalSize = 0;
                    $scope.totalPages = 1;
                    $scope.partyList = null;
                }else{
                    $scope.status = false;
                    $scope.currentPage = parseInt(result.currentPage);
                    $scope.totalSize = result.totalNum;
                    $scope.totalPages = result.totalPage;
                    $scope.partyList = result.pageDataList;
                    angular.forEach($scope.partyList,function(partyInfo,oindex,array){
                        if(partyInfo.grade == '1'){
                            partyInfo.grade = '一星会员';
                        }else if(partyInfo.grade == '2'){
                            partyInfo.grade = '二星会员';
                        }else if(partyInfo.grade == '3'){
                            partyInfo.grade = '三星会员';
                        }else if(partyInfo.grade == '4'){
                            partyInfo.grade = '四星会员';
                        }else if(partyInfo.grade == '5'){
                            partyInfo.grade = '五星会员';
                        }
                    });
                }
            })
            .error(function(data) {
                $scope.overlayStatus = false;
                $scope.status = true;
                $scope.msg = 'Request processing failed!';
            });
    }

    //查询会员
    $scope.queryJdParty = function (){
        $scope.currentPage = parseInt(1);//当前页
        $scope.getListByPage();
    };

    //初始化加载
    setTimeout($scope.queryJdParty,500);

    //首页
    $scope.prevPage = function(){
        if ($scope.currentPage == 1) {
            return;
        } else {
            $scope.currentPage = parseInt(1);
            $scope.getListByPage();
        }
    };
    //上一页
    $scope.pageUp = function(){
        if ($scope.currentPage == 1) {
            return;
        } else {
            $scope.currentPage = parseInt($scope.currentPage) - 1;
            $scope.getListByPage();
        }
    };
    //下一页
    $scope.pageDown = function(){
        if ($scope.currentPage == $scope.totalPages) {
            return;
        } else {
            $scope.currentPage = parseInt($scope.currentPage) + 1;
            $scope.getListByPage();
        }
    };
    //尾页
    $scope.nextPage = function(){
        if ($scope.currentPage == $scope.totalPages) {
            return;
        } else {
            $scope.currentPage = parseInt($scope.totalPages);
            $scope.getListByPage();
        }
    };

    $scope.pageSizeNum = $scope.pageSize;//每页显示数
    $scope.pageLength = function(){
        if($scope.pageSizeNum == undefined){
            $scope.pageSize = 20;
        } else{
            $scope.pageSize = $scope.pageSizeNum;
        }
        $scope.currentPage = parseInt(1);//当前页
        $scope.getListByPage();
    }
});


adminLteApp.controller('TmallOrderCtrl', function ($scope, $http) {

    $scope.selected = {status:'所有状态',value:''};
    $scope.orderStatus = [
        {status:'所有状态',value:''},
        {status:'等待买家付款',value:'WAIT_BUYER_PAY'},
        {status:'等待发货',value:'WAIT_SELLER_SEND_GOODS'},
        //{status:'卖家部分发货',value:'SELLER_CONSIGNED_PART'},
        {status:'等待买家确认收货',value:'WAIT_BUYER_CONFIRM_GOODS'},
        //{status:'买家已签收',value:'TRADE_BUYER_SIGNED'},
        {status:'交易成功',value:'TRADE_FINISHED'},
        {status:'交易自动关闭',value:'TRADE_CLOSED'},
        {status:'交易关闭',value:'TRADE_CLOSED_BY_TAOBAO'},
        //{status:'没有创建支付宝交易',value:'TRADE_NO_CREATE_PAY'},
        //{status:'0元购合约中',value:'WAIT_PRE_AUTH_CONFIRM'},
        //{status:'国际信用卡支付付款确认中',value:'PAY_PENDING'}
    ];

    //当前页
    $scope.currentPage = 1;
    //每页显示条数
    $scope.pageSize = 20;
    //总数
    $scope.totalSize = 0;
    //总页数
    $scope.totalPages = 0;

    $scope.select_status = true;//显示所有状态
    $scope.showStatus = function(){
        $scope.select_status = false;//隐藏所有状态
    };
    $scope.overlayStatus = false;//查询进度条初始化
    $scope.status = false;//提示条初始化

    //分页查询
    $scope.getListByPage = function() {
        $scope.overlayStatus = true;
        $scope.select_status = false;//隐藏所有状态

        var skuId = $scope.skuId;
        var orderId = $scope.orderId;
        var orderState = $scope.selected.value;
        var productName = $scope.productName;
        var telephone = $scope.telephone;
        var timeRange = $("#reservationtime").val();
        var startDate;
        var endDate;
        if (timeRange !=null && timeRange != "") {
            var startStr = timeRange.substring(0,16);
            var endStr = timeRange.substring(19,timeRange.length);
            startDate = startStr+':00';
            endDate = endStr+':59';
        }
        var params = { currentPage:$scope.currentPage,
            pageSize:$scope.pageSize,
            skuId:skuId,
            orderId:orderId,
            orderState:orderState,
            productName:productName,
            telephone:telephone,
            startDate:startDate,
            endDate:endDate};
        //console.log(params);
        $http({url:"/mark/tmall/getHistoryOrder.php", method:"POST", params:params})
            .success(function(data) {
                $scope.overlayStatus = false;
                var result = data;
                if(result.pageDataList.length == 0){
                    $scope.status = true;
                    $scope.msg = 'no order data!';

                    $scope.currentPage = 1;
                    $scope.totalSize = 0;
                    $scope.totalPages = 1;
                    $scope.oderList = null;
                }else{
                    $scope.status = false;
                    $scope.currentPage = parseInt(result.currentPage);
                    $scope.totalSize = result.totalNum;
                    $scope.totalPages = result.totalPage;

                    $scope.oderList = result.pageDataList;
                    angular.forEach($scope.oderList,function(order_info,oindex,array){
                        order_info.logisticsCompany = order_info.orders[0].logisticsCompany;
                        order_info.invoiceNo = order_info.orders[0].invoiceNo;
                        if(order_info.status == null || order_info.status == ""){
                            order_info.status = "";
                        }else{
                            if(order_info.status == "WAIT_BUYER_PAY" || order_info.status == "TRADE_NO_CREATE_PAY"){
                                order_info.status = "等待买家付款"
                            }else if(order_info.status == "WAIT_SELLER_SEND_GOODS"){
                                order_info.status = "等待发货"
                            }else if(order_info.status == "WAIT_BUYER_CONFIRM_GOODS"){
                                order_info.status = "等待买家确认收货"
                            }else if(order_info.status == "TRADE_FINISHED"){
                                order_info.status = "交易成功"
                            }else if(order_info.status == "TRADE_CLOSED_BY_TAOBAO"){
                                order_info.status = "交易关闭"
                            }else if(order_info.status == "TRADE_CLOSED"){
                                order_info.status = "交易自动关闭"
                            }
                        }
                    });
                }
            })
            .error(function(data) {
                $scope.overlayStatus = false;
                $scope.status = true;
                $scope.msg = 'Request processing failed!';
            });
    }

    //查询订单
    $scope.queryTmallOrder = function (){
        $scope.currentPage = parseInt(1);//当前页
        $scope.getListByPage();
    };

    //初始化加载
    setTimeout($scope.queryTmallOrder,500);

    //首页
    $scope.prevPage = function(){
        if ($scope.currentPage == 1) {
            return;
        } else {
            $scope.currentPage = parseInt(1);
            $scope.getListByPage();
        }
    };
    //上一页
    $scope.pageUp = function(){
        if ($scope.currentPage == 1) {
            return;
        } else {
            $scope.currentPage = parseInt($scope.currentPage) - 1;
            $scope.getListByPage();
        }
    };
    //下一页
    $scope.pageDown = function(){
        if ($scope.currentPage == $scope.totalPages) {
            return;
        } else {
            $scope.currentPage = parseInt($scope.currentPage) + 1;
            $scope.getListByPage();
        }
    };
    //尾页
    $scope.nextPage = function(){
        if ($scope.currentPage == $scope.totalPages) {
            return;
        } else {
            $scope.currentPage = parseInt($scope.totalPages);
            $scope.getListByPage();
        }
    };

    $scope.pageSizeNum = $scope.pageSize;//每页显示数
    $scope.pageLength = function(){
        if($scope.pageSizeNum == undefined){
            $scope.pageSize = 20;
        } else{
            $scope.pageSize = $scope.pageSizeNum;
        }
        $scope.currentPage = parseInt(1);//当前页
        $scope.getListByPage();
    }

});

adminLteApp.controller('TmallControlCtrl', function ($scope, $http) {
    $scope.interval = 60000;
    $scope.scheduleCount = 0;

    $http({url:"/mark/tmall/controlStatus.php",method:"GET"}).success(function(data) {
        $scope.startup = data.tmScheduleStatus.startup;
        if($scope.startup){
            $scope.result = "启动监听成功，正在监听......."
        }else{
            $scope.result = "当前已停止监听！"
        }
    });

    $scope.startListening = function(){
        $http({ method : "POST",url : '/mark/tmall/startListening.php'})
            .success(function(data){
                //console.log("startListening",data);
                if(data.status == 0){
                    $scope.startup = true;
                    $scope.result = "启动监听成功，正在监听......."
                    $scope.startup = true;
                }else{
                    $scope.result = "启动监听失败！"
                }
            });
    }

    $scope.stopListening = function(){
        $http({ method : "POST",url : '/mark/tmall/stopListening.php'})
            .success(function(data){
                if(data.status == 0){
                    $scope.startup = false;
                    $scope.result = "当前已停止监听！"
                    $scope.startup = false;
                }else{
                    $scope.result = "停止监听失败！"
                }
            });
    }

    $scope.fixOrder = function(){
        var timeRange = $("#reservationtime").val();
        if ("" == timeRange) {
            $scope.overlayStatus = false;
            $scope.status = true;
            $scope.msg = '请选择时间区间!';
            return;
        }

        var startDate;
        var endDate;
        if (timeRange !=null && timeRange != "") {
            var startStr = timeRange.substring(0,16);
            var endStr = timeRange.substring(19,timeRange.length);
            startDate = startStr+':00';
            endDate = endStr+':59';
        }
        $scope.loading = true;
        $scope.disabled = true;
        var params = {
            status:"",
            startDate:startDate,
            endDate:endDate};
        $http({ method : "POST", url : '/mark/tmall/fixOrder.php', params : params})
            .success(function(data){
                $scope.loading = false;
                $scope.disabled = false;
                $scope.message = "本次补单："+data.message+" 条";

            });
    }

    //$scope.changeScheduleTime = function(){
    //    $http({method : "POST", url : '/mark/tmall/changeScheduleTime.php', params : {interval : $scope.interval}})
    //        .success(function(data){
    //            if(data.status == 0){
    //                $scope.startup = true;
    //            }
    //        });
    //}

    //$scope.clean = function(){
    //    $http({method : "POST", url : '/mark/tmall/cleanSchedule.php'})
    //        .success(function(data){
    //            $scope.scheduleCount = 0;
    //        });
    //}

});


adminLteApp.controller('TmallRefundCtrl', function ($scope, $http, $routeParams) {
    $scope.refundId = $routeParams.refund_id;
    $scope.selected = {status:'所有状态',value:''};
    $scope.refundStatus = [
        {status:'买家已经申请退款',value:'WAIT_SELLER_AGREE'},
        {status:'卖家已经同意退款',value:'WAIT_BUYER_RETURN_GOODS'},
        {status:'买家已经退货',value:'WAIT_SELLER_CONFIRM_GOODS'},
        {status:'退款关闭',value:'CLOSED'},
        {status:'退款成功',value:'SUCCESS'},
    ];

    //当前页
    $scope.currentPage = 1;
    //每页显示条数
    $scope.pageSize = 20;
    //总数
    $scope.totalSize = 0;
    //总页数
    $scope.totalPages = 0;

    $scope.select_status = true;//显示所有状态
    $scope.showStatus = function(){
        $scope.select_status = false;//隐藏所有状态
    };
    $scope.overlayStatus = false;//查询进度条初始化
    $scope.status = false;//提示条初始化

    //分页查询
    $scope.getListByPage = function() {
        $scope.overlayStatus = true;
        $scope.select_status = false;//隐藏所有状态

        var tId = $scope.tId;
        var refundId = $scope.refundId;
        var status = $scope.selected.value;
        var title = $scope.title;
        var sid = $scope.sid;
        var timeRange = $("#reservationtime").val();
        var startDate;
        var endDate;
        if (timeRange !=null && timeRange != "") {
            var startStr = timeRange.substring(0,16);
            var endStr = timeRange.substring(19,timeRange.length);
            startDate = startStr+':00';
            endDate = endStr+':59';
        }
        var params = { currentPage:$scope.currentPage,
            pageSize:$scope.pageSize,
            tId:tId,
            refundId:refundId,
            status:status,
            title:title,
            sid:sid,
            startDate:startDate,
            endDate:endDate};
        //console.log(params);
        $http({url:"/mark/tmall/getRefundOrder.php", method:"POST", params:params})
            .success(function(data) {
                $scope.overlayStatus = false;
                var result = data;
                if(result.pageDataList.length == 0){
                    $scope.status = true;
                    $scope.msg = '暂无退货信息!';

                    $scope.currentPage = 1;
                    $scope.totalSize = 0;
                    $scope.totalPages = 1;
                    $scope.oderList = null;
                }else{
                    $scope.status = false;
                    $scope.currentPage = parseInt(result.currentPage);
                    $scope.totalSize = result.totalNum;
                    $scope.totalPages = result.totalPage;

                    $scope.oderList = result.pageDataList;
                    angular.forEach($scope.oderList,function(refund_info,oindex,array){
                        if(refund_info.status == null || refund_info.status == ""){
                            refund_info.status = "";
                        }else{
                            if(refund_info.status == "WAIT_SELLER_AGREE"){
                                refund_info.status = "买家已经申请退款"
                            }else if(refund_info.status == "WAIT_BUYER_RETURN_GOODS"){
                                refund_info.status = "卖家已经同意退款"
                            }else if(refund_info.status == "WAIT_SELLER_CONFIRM_GOODS"){
                                refund_info.status = "买家已经退货"
                            }else if(refund_info.status == "SELLER_REFUSE_BUYER"){
                                refund_info.status = "卖家拒绝退款"
                            }else if(refund_info.status == "CLOSED"){
                                refund_info.status = "退款关闭"
                            }else if(refund_info.status == "SUCCESS"){
                                refund_info.status = "退款成功"
                            }
                        }

                        if(refund_info.good_status == null || refund_info.good_status == ""){
                            refund_info.good_status = "";
                        }else{
                            if(refund_info.good_status == "BUYER_NOT_RECEIVED"){
                                refund_info.good_status = "买家未收到货";
                            }else if(refund_info.good_status == "BUYER_RECEIVED"){
                                refund_info.good_status = "买家已收到货";
                            }else if(refund_info.good_status == "BUYER_RETURNED_GOODS"){
                                refund_info.good_status = "买家已退货";
                            }
                        }

                        if(refund_info.order_status == null || refund_info.order_status == ""){
                            refund_info.order_status = "";
                        }else{
                            if(refund_info.order_status == "WAIT_BUYER_PAY"){
                                refund_info.order_status = "等待买家付款";
                            }else if(refund_info.order_status == "WAIT_SELLER_SEND_GOODS"){
                                refund_info.order_status = "等待发货";
                            }else if(refund_info.order_status == "SELLER_CONSIGNED_PART"){
                                refund_info.order_status = "卖家部分发货";
                            }else if(refund_info.order_status == "WAIT_BUYER_CONFIRM_GOODS"){
                                refund_info.order_status = "等待买家确认收货";
                            }else if(refund_info.order_status == "TRADE_BUYER_SIGNED"){
                                refund_info.order_status = "买家已签收";
                            }else if(refund_info.order_status == "TRADE_FINISHED"){
                                refund_info.order_status = "交易成功";
                            }else if(refund_info.order_status == "TRADE_CLOSED"){
                                refund_info.order_status = "交易自动关闭";
                            }else if(refund_info.order_status == "TRADE_CLOSED_BY_TAOBAO"){
                                refund_info.order_status = "交易关闭";
                            }else if(refund_info.order_status == "TRADE_NO_CREATE_PAY"){
                                refund_info.order_status = "没有创建支付宝交易";
                            }else if(refund_info.order_status == "WAIT_PRE_AUTH_CONFIRM"){
                                refund_info.order_status = "0元购合约中";
                            }else if(refund_info.order_status == "PAY_PENDING"){
                                refund_info.order_status = "国际信用卡支付付款确认中";
                            }
                        }

                    });
                }
            })
            .error(function(data) {
                $scope.overlayStatus = false;
                $scope.status = true;
                $scope.msg = 'Request processing failed!';
            });
    }

    //查询订单
    $scope.queryTmallRefund = function (){
        $scope.currentPage = parseInt(1);//当前页
        $scope.getListByPage();
    };

    //初始化加载
    setTimeout($scope.queryTmallRefund,500);

    //首页
    $scope.prevPage = function(){
        if ($scope.currentPage == 1) {
            return;
        } else {
            $scope.currentPage = parseInt(1);
            $scope.getListByPage();
        }
    };
    //上一页
    $scope.pageUp = function(){
        if ($scope.currentPage == 1) {
            return;
        } else {
            $scope.currentPage = parseInt($scope.currentPage) - 1;
            $scope.getListByPage();
        }
    };
    //下一页
    $scope.pageDown = function(){
        if ($scope.currentPage == $scope.totalPages) {
            return;
        } else {
            $scope.currentPage = parseInt($scope.currentPage) + 1;
            $scope.getListByPage();
        }
    };
    //尾页
    $scope.nextPage = function(){
        if ($scope.currentPage == $scope.totalPages) {
            return;
        } else {
            $scope.currentPage = parseInt($scope.totalPages);
            $scope.getListByPage();
        }
    };

    $scope.pageSizeNum = $scope.pageSize;//每页显示数
    $scope.pageLength = function(){
        if($scope.pageSizeNum == undefined){
            $scope.pageSize = 20;
        } else{
            $scope.pageSize = $scope.pageSizeNum;
        }
        $scope.currentPage = parseInt(1);//当前页
        $scope.getListByPage();
    }


    //确认审核窗口
    $scope.refundAuditingDiv = function(refundId, refundPhase, refundVersion){
        //初始化各参数
        $scope.refundId5 = refundId;
        $scope.refundPhase5 = refundPhase;
        $scope.refundVersion5 = refundVersion;
        $scope.operator = null;
        $scope.message = "同意退款";
        $scope.setResult = "true";
        //查询审核人员
        $http({url:"/mark/tmall/getTmUserNick.php", method:"POST"})
            .success(function(data) {
                $scope.operator = decodeURI(data)
            })
            .error(function(data) {
                $scope.msg = 'Please try again later!';
            });
    }
    //确认审核
    $scope.refundAuditing = function(){
        $scope.refundAuditingStatus = false;
        var params = {
            refundId:$scope.refundId5,
            refundPhase:$scope.refundPhase5,
            refundVersion:$scope.refundVersion5,
            operator:$scope.operator,
            message:$scope.message,
            result:$scope.setResult
        }
        $http({url:"/mark/tmall/refundReview.php", method:"POST", params:params})
            .success(function(data) {
                //console.log(data);
                if (null != data.status) {//失败
                    $scope.refundAuditingStatus = true;
                    $scope.refundAuditingMsg = data.message;
                    return;
                }
                $scope.getListByPage();
            })
            .error(function(data) {
                $scope.status = true;
                $scope.msg = 'Request processing failed!';
            });
    }


    //同意退款
    /*$scope.refundAgree = function(refundId, refundPhase, refundVersion, amount) {
        bootbox.confirm({
            buttons : { confirm : { label : '确认'}, cancel : { label : '取消'}},
            message : '你确定要退款吗？',
            title : '退款提醒',
            callback : function(result){
                if (result){
                     *//*Metronic.blockUI({
                        target: '#storeTable_portlet_body',
                        boxed: true,
                        cenrerY: true,
                        message: '退款中...'
                    });*//*
                    $scope.status = false;
                    var  params = {
                        refundId:refundId,
                        refundPhase:refundPhase,
                        refundVersion:refundVersion,
                        amount:amount
                    };
                    console.log(params);
//                    return;
                    $http({url:"/mark/tmall/refundAgree.php", method:"POST", params:params})
                        .success(function(data) {
                           // Metronic.unblockUI('#storeTable_portlet_body');
                            if (null != data.status) {//失败
                                //Metronic.unblockUI('#storeTable_portlet_body');
                                $scope.status = true;
                                $scope.msg = data.message;
                                return;
                            }
                            $scope.getListByPage();
                        })
                        .error(function(data) {
                            //Metronic.unblockUI('#storeTable_portlet_body');
                            $scope.status = true;
                            $scope.msg = 'Request processing failed!';
                        });
                }
            }
        });
    }*/
    //同意退款窗口
    $scope.isVerification = false;
    $scope.refundAgreeDiv = function(refundId, refundPhase, refundVersion, amount){
        $scope.isVerification = false;
        //初始化各参数
        $scope.refundId6 = refundId;
        $scope.refundPhase6 = refundPhase;
        $scope.refundVersion6 = refundVersion;
        $scope.amount6 = amount;
        $scope.code6 = null;
    }
    //同意退款
    $scope.refundAgree = function(){
        $scope.agreeRefundStatus = false;
        var code = $scope.code6;
        //console.log(code);
        var params;
        if (!$scope.isVerification) {
            alert(1);
            params = {
                refundId:$scope.refundId6,
                refundPhase:$scope.refundPhase6,
                refundVersion:$scope.refundVersion6,
                amount:$scope.amount6
            }
        } else {
            if (undefined == $scope.code6 || null == $scope.code6 || "" == $scope.code6.trim()) {
                $scope.agreeRefundStatus = true;
                $scope.agreeRefundMsg = '短信验证码不能为空!';
                return;
            }
            params = {
                refundId:$scope.refundId6,
                refundPhase:$scope.refundPhase6,
                refundVersion:$scope.refundVersion6,
                amount:$scope.amount6,
                code:$scope.code6
            }
        }
        console.log(params);
        $http({url:"/mark/tmall/refundAgree.php", method:"POST", params:params})
            .success(function(data) {
                console.log("成功",data);
                if (null != data.status) {//失败
                    $scope.agreeRefundStatus = true;
                    $scope.agreeRefundMsg = data.message;
                    return;
                }
                if (data.rp_refunds_agree_response.message == "发送二次验证短信成功") {
                   $scope.isVerification = true;
                   return;
                }
                $scope.isVerification = false;
                $('#refundAgreeDiv').modal('hide');
                window.top.location.reload();
            })
            .error(function(data) {
                $scope.agreeRefundStatus = true;
                $scope.msg = 'Request processing failed!';
            });
    }
    /*//同意退款
    $scope.refundAgree = function(refundId, refundPhase, refundVersion, amount) {
        $scope.status = false;
        var  params = {
            refundId:refundId,
            refundPhase:refundPhase,
            refundVersion:refundVersion,
            amount:amount
        };
        $http({url:"/mark/tmall/refundAgree.php", method:"POST", params:params})
            .success(function(data) {
                if (null != data.status) {//失败
                    $scope.status = true;
                    $scope.msg = data.message;
                    return;
                }
                $scope.getListByPage();
            })
            .error(function(data) {
                $scope.status = true;
                $scope.msg = 'Request processing failed!';
            });
    }*/

    //同意退货窗口
    $scope.returnGoodsAgreeDiv = function(refundId, refundPhase, refundVersion) {
        //初始化各参数
        $scope.refundId2 = refundId;
        $scope.refundPhase2 = refundPhase;
        $scope.refundVersion2 = refundVersion;
        $scope.addrSelected = null;
        $scope.returnRemark = null;
        $scope.addrStatus = false;
        //查询卖家退货地址
        $http({url:"/mark/tmall/sellerAddrList.php", method:"POST"})
            .success(function(data) {
                if (null != data.status) {//失败
                    $scope.addrStatus = true;
                    $scope.addrMsg = data.message;
                    return;
                }
                $scope.addrList = data.logistics_address_search_response.addresses.address_result;
            })
            .error(function(data) {
                $scope.addrStatus = true;
                $scope.addrMsg = 'Request seller address data failed!';
            });
    }
    //确认退货
    $scope.returnGoodsAgree = function() {
        $scope.addrStatus = false;
        if (undefined == $scope.addrSelected || null == $scope.addrSelected) {
            $scope.addrStatus = true;
            $scope.addrMsg = '退货地址必选!';
            return;
        }
        var returnAddress =JSON.parse($scope.addrSelected);
        if (undefined == $scope.returnRemark || null == $scope.returnRemark || "" == $scope.returnRemark.trim()) {
            $scope.addrStatus = true;
            $scope.addrMsg = '退货说明不能为空!';
            return;
        }
        var address = returnAddress.province+returnAddress.city+returnAddress.country+returnAddress.addr;
        var params = {
            refundId:$scope.refundId2,
            refundPhase:$scope.refundPhase2,
            refundVersion:$scope.refundVersion2,
            name:returnAddress.contact_name,
            address:address,
            post:returnAddress.zip_code,
            tel:returnAddress.phone,
            mobile:returnAddress.mobile_phone,
            remark:$scope.returnRemark,
            sellerAddressId:returnAddress.contact_id
        };
        $http({url:"/mark/tmall/returnGoodsAgree.php", method:"POST", params:params})
            .success(function(data) {
                if (null != data.status) {//失败
                    $scope.addrStatus = true;
                    $scope.addrMsg = data.message;
                    return;
                }
                $('#returnGoodsAddr').modal('hide');
                window.top.location.reload();
            })
            .error(function(data) {
                $scope.addrStatus = true;
                $scope.addrMsg = 'Request processing failed!';
            });
    }


    //图片上传部分
    $scope.reader = new FileReader();   //创建一个FileReader接口
    $scope.form = {     //用于绑定提交内容，图片或其他数据
        image:{},
    };
    $scope.thumb = {};      //用于存放图片的base64
    $scope.imageData = new FormData();      //以下为后台提交图片数据
    $scope.img_upload = function(files,type) {       //单次提交图片的函数
        if (files.length < 1) {
            //$("#refundImg").attr("src","");
            //$scope.thumb = {};
            //$scope.imageData = new FormData();
            return;
        }
        //每次操作先删除之前上传的
        /*for(var p in $scope.thumb) {
         delete $scope.thumb[p];
         delete $scope.form.image[p];
         }*/
        /*if (type=='refund') {//区分退款，退货
            $("#refundImg").attr("src","");
        } else if (type='returnGoods') {
            $("#returnGoodsImg").attr("src","");
        }*/
        $scope.thumb = {};
        $scope.imageData = new FormData();

        $scope.guid = (new Date()).valueOf();   //通过时间戳创建一个随机数，作为键名使用
        $scope.reader.readAsDataURL(files[0]);  //FileReader的方法，把图片转成base64
        $scope.reader.onload = function(ev) {
            $scope.$apply(function(){
                $scope.thumb[$scope.guid] = {
                    imgSrc : ev.target.result,  //接收base64
                }
            });
        };
        $scope.imageData.append('image', files[0]);
        $scope.imageData.append('guid',$scope.guid);
    }
    //拒绝退款窗口
    $scope.refundRefuseDiv = function(refundId, refundPhase, refundVersion) {
        $("#refundImg").attr("src","");
        $scope.thumb = {};
        $scope.imageData = new FormData();
        //初始化各参数
        $scope.refundId3 = refundId;
        $scope.refundPhase3 = refundPhase;
        $scope.refundVersion3 = refundVersion;
        $scope.reasonSelected = null;
        $scope.refuseMsg = null;
        $scope.refuseStatus = false;
        $scope.returnMessage = null;
        //查询拒绝原因列表
        /*//测试数据
        var reason = '[{"reason_id":118,"reason_text":"已经影响商品完好"},{"reason_id":103,"reason_text":"买家使用到付或平邮"},{"reason_id":1040,"reason_text":"退货商品不全、空包"},{"reason_id":106,"reason_text":"与买家协商换货"},{"reason_id":1047,"reason_text":"退货商品与订单商品不一致"},{"reason_id":104,"reason_text":"未收到退货，快递还在途中"},{"reason_id":105,"reason_text":"买家退货单号错误或无走件记录"}]';
        $scope.reasonList = JSON.parse(reason);
        return;*/
        /*var params = {
            refundId:"106206407540810",
            refundPhase:"aftersale"
        };*/
        var params = {
            refundId:refundId,
            refundPhase:refundPhase
        };
        $http({url:"/mark/tmall/getRefuseReasonList.php", method:"POST", params:params})
            .success(function(data) {
                if (null != data.status) {//失败
                    $scope.refuseStatus = true;
                    $scope.refuseMsg = data.message;
                    return;
                }
                $scope.reasonList = data.refund_refusereason_get_response.reasons.reason;
            })
            .error(function(data) {
                $scope.refuseStatus = true;
                $scope.refuseMsg = 'Request refuse reason data failed!';
            });
    }
    //确认拒绝退款
    $scope.refundRefuse = function() {
        $scope.refuseStatus = false;
        if (undefined == $scope.reasonSelected || null == $scope.reasonSelected) {
            $scope.refuseStatus = true;
            $scope.refuseMsg = '拒绝原因必选!';
            return;
        }
        if (undefined == $scope.returnMessage || null == $scope.returnMessage || "" == $scope.returnMessage.trim()) {
            $scope.refuseStatus = true;
            $scope.refuseMsg = '拒绝说明不能为空!';
            return;
        }
        if (null == $("#refundImg").attr("src") || "" == $("#refundImg").attr("src")) {
            $scope.refuseStatus = true;
            $scope.refuseMsg = '必须上传凭证!';
            return;
        }
        var params = {
            refundId:$scope.refundId3,
            refundPhase:$scope.refundPhase3,
            refundVersion:$scope.refundVersion3,
            refuseMessage:$scope.refuseMessage
        };
        //console.log(params);
        $http({url:"/mark/tmall/refundRefuse.php", method:"POST", params:params, data:$scope.imageData, headers: {'Content-Type': undefined}, transformRequest: angular.identity})
            .success(function(data) {
                //console.log(data);
                if (null != data.status) {//失败
                    $scope.refuseStatus = true;
                    $scope.refuseMsg = data.message;
                    return;
                }
                $('#refundRefuseDiv').modal('hide');
                window.top.location.reload();
            })
            .error(function(data) {
                $scope.refuseStatus = true;
                $scope.refuseMsg = 'Request processing failed!';
            });
    }

    //拒绝退货窗口
    $scope.returnGoodsRefuseDiv = function(refundId, refundPhase, refundVersion) {
        $("#returnGoodsImg").attr("src","");
        $scope.thumb = {};
        $scope.imageData = new FormData();
        //初始化各参数
        $scope.refundId4 = refundId;
        $scope.refundPhase4 = refundPhase;
        $scope.refundVersion4 = refundVersion;
        $scope.reasonSelected4 = null;
        $scope.returnGoodsMsg = null;
        $scope.returnGoodsStatus = false;
        //查询拒绝原因列表
        /*//测试数据
        var reason = '[{"reason_id":118,"reason_text":"已经影响商品完好"},{"reason_id":103,"reason_text":"买家使用到付或平邮"},{"reason_id":1040,"reason_text":"退货商品不全、空包"},{"reason_id":106,"reason_text":"与买家协商换货"},{"reason_id":1047,"reason_text":"退货商品与订单商品不一致"},{"reason_id":104,"reason_text":"未收到退货，快递还在途中"},{"reason_id":105,"reason_text":"买家退货单号错误或无走件记录"}]';
        $scope.reasonList = JSON.parse(reason);
        return;*/
        /*var params = {
         refundId:"106206407540810",
         refundPhase:"aftersale"
         };*/
        var params = {
            refundId:refundId,
            refundPhase:refundPhase
        };
        $http({url:"/mark/tmall/getRefuseReasonList.php", method:"POST", params:params})
            .success(function(data) {
                if (null != data.status) {//失败
                    $scope.returnGoodsStatus = true;
                    $scope.returnGoodsMsg = data.message;
                    return;
                }
                $scope.reasonList = data.refund_refusereason_get_response.reasons.reason;
            })
            .error(function(data) {
                $scope.returnGoodsStatus = true;
                $scope.returnGoodsMsg = 'Request refuse reason data failed!';
            });
    }
    //确认拒绝退货
    $scope.returnGoodsRefuse = function() {
        $scope.returnGoodsStatus = false;
        if (undefined == $scope.reasonSelected4 || null == $scope.reasonSelected4) {
            $scope.returnGoodsStatus = true;
            $scope.returnGoodsMsg = '拒绝原因必选!';
            return;
        }
        if (null == $("#returnGoodsImg").attr("src") || "" == $("#returnGoodsImg").attr("src")) {
            $scope.returnGoodsStatus = true;
            $scope.returnGoodsMsg = '必须上传凭证!';
            return;
        }
        var params = {
            refundId:$scope.refundId4,
            refundPhase:$scope.refundPhase4,
            refundVersion:$scope.refundVersion4
        };
        //console.log(params);
        $http({url:"/mark/tmall/returnGoodsRefuse.php", method:"POST", params:params, data:$scope.imageData, headers: {'Content-Type': undefined}, transformRequest: angular.identity})
            .success(function(data) {
                //console.log(data);
                if (null != data.status) {//失败
                    $scope.returnGoodsStatus = true;
                    $scope.returnGoodsMsg = data.message;
                    return;
                }
                $('#refundRefuseDiv').modal('hide');
                window.top.location.reload();
            })
            .error(function(data) {
                $scope.returnGoodsStatus = true;
                $scope.returnGoodsMsg = 'Request processing failed!';
            });
    }


});

adminLteApp.controller('ConfigureCtrl', function ($scope, $http, $window) {
    $scope.loading = true;
    $http({url:"/mark/sys/findAllPlatform.php", method:"GET"})
        .success(function(data) {
            //console.log(data);
            $scope.platformList = data.platformList;
            $scope.activeMQ = data.activeMQ;
        });
    $scope.platform = "";
    $scope.appKey = "";
    $scope.appSecret = "";
    $scope.optionField = "";
    $scope.notifyType = "";
    $scope.notifyAddr = "";
    $scope.addPlatform = function(){
        var appKey = $scope.appKey;
        var appSecret = $scope.appSecret;
        var platform = $scope.platform;
        var optionField = $scope.optionField;
        var notifyType = $scope.notifyType;
        if(notifyType == "message"){
            var notifyAddr = $scope.activeMQ;
        }else{
            var notifyAddr = $scope.notifyAddr;
        }
        if(appKey == "" || appKey == undefined || appSecret == "" || appSecret == undefined || notifyAddr == "" || notifyAddr == undefined || notifyType == "" || notifyType == undefined) {
            $scope.alterMsg = "Please enter the complete information";
            return;
        }
        if(platform == "jp" && optionField == "" || optionField == undefined){
            $scope.alterMsg = "Please enter the complete information";
            return;
        }

        $http({url:"/mark/sys/addPlatform.php", method:"GET",params:{"appKey":appKey,"appSecret":appSecret,"optionField":optionField,"platform":platform,"notifyAddr":notifyAddr,"notifyType":notifyType}})
            .success(function(data) {
                if (data.status != 0) {
                    $scope.errMsg = data.errorMsg;
                    $scope.loading = false;
                    return;
                }
                $('#addPlatform').modal('hide');
                window.top.location.reload();
            });
    }

    $scope.delPlatform = function(platform,appKey,appSecret,optionField){
        var ret = $window.confirm('确定要删除这个平台吗？删除后与此平台相关的信息都将消失！！！');
        if(ret){
            $http({url:"/mark/sys/delPlatform.php", method:"GET",params:{"platform":platform,"appKey":appKey,"optionField":optionField,"appSecret":appSecret}})
                .success(function(data) {
                    if (data.status != 0) {
                        $scope.errMsg = data.errorMsg;
                        $scope.loading = false;
                        return;
                    }
                    $scope.alterMsg = "删除成功";
                    window.top.location.reload();
                });
        }
    }

    $scope.editPlat = function(platform, appKey, appSecret, optionField, event, index,type){
        if(type == "appKey"){
            var appKey = event.target.innerText;
            var oldAppKey = $scope.platformList[index].appKey;
            if(appKey == oldAppKey) return;
        }else if(type == "appSecret"){
            var appSecret = event.target.innerText;
            var oldAppSecret = $scope.platformList[index].appSecret;
            if(appSecret == oldAppSecret) return;
        }else if(type == "optionField"){
            var optionField = event.target.innerText;
            var oldOptionField = $scope.platformList[index].optionField;
            if(optionField == oldOptionField) return;
        }

        $http({ method : "POST" , params : {platform : platform, appKey : appKey, appSecret : appSecret, optionField : optionField,  editType : "edit"}, url : '/mark/sys/addPlatform.php' })
            .success(function(data) {
                if(data.status == 0){
                    $scope.successMsg = "更新成功";
                }
                else{
                    $scope.alterMsg = "更新失败";
                }
            });
    };

});


adminLteApp.controller('Z8ControlCtrl', function ($scope, $http) {
    $scope.interval = 60000;
    $scope.scheduleCount = 0;

    $http({url:"/mark/zhe800/controlStatus.php",method:"GET"}).success(function(data) {
        if (null == data || undefined == data.z8ScheduleStatus) {
            $scope.startup = false;
            $scope.result = "当前已停止监听！"
        } else {
            $scope.startup = data.z8ScheduleStatus.startup;
            if($scope.startup){
                $scope.result = "启动监听成功，正在监听......."
            }else{
                $scope.result = "当前已停止监听！"
            }
        }

    });

    $scope.startListening = function(){
        $http({ method : "POST",url : '/mark/zhe800/startListening.php'})
            .success(function(data){
                //console.log("startListening",data);
                if(data.status == 0){
                    $scope.startup = true;
                    $scope.result = "启动监听成功，正在监听......."
                    $scope.startup = true;
                }else{
                    $scope.result = "启动监听失败！"
                }
            });
    }

    $scope.stopListening = function(){
        $http({ method : "POST",url : '/mark/zhe800/stopListening.php'})
            .success(function(data){
                if(data.status == 0){
                    $scope.startup = false;
                    $scope.result = "当前已停止监听！"
                    $scope.startup = false;
                }else{
                    $scope.result = "停止监听失败！"
                }
            });
    }

    $scope.fixOrder = function(){
        var timeRange = $("#reservationtime").val();
        if ("" == timeRange) {
            $scope.overlayStatus = false;
            $scope.status = true;
            $scope.msg = '请选择时间区间!';
            return;
        }

        var startDate;
        var endDate;
        if (timeRange !=null && timeRange != "") {
            var startStr = timeRange.substring(0,16);
            var endStr = timeRange.substring(19,timeRange.length);
            startDate = startStr+':00';
            endDate = endStr+':59';
        }
        $scope.loading = true;
        $scope.disabled = true;
        var params = {
            status:"",
            startDate:startDate,
            endDate:endDate};
        $http({ method : "POST", url : '/mark/zhe800/fixOrder.php', params : params})
            .success(function(data){
                $scope.loading = false;
                $scope.disabled = false;
                $scope.message = "本次补单："+data.message+" 条";

            });
    }

});

adminLteApp.controller('Z8OrderCtrl', function ($scope, $http) {

    $scope.selected = {status:'所有状态',value:''};
    $scope.orderStatus = [
        {status:'所有状态',value:''},
        {status:'等待买家付款',value:'1'},
        {status:'等待发货',value:'2'},
        {status:'等待买家确认收货',value:'3'},  //已发货
        {status:'交易成功',value:'5'},
        {status:'交易关闭',value:'7'}, //原因：交易取消/超时未支付/售后完成
    ];

    //当前页
    $scope.currentPage = 1;
    //每页显示条数
    $scope.pageSize = 20;
    //总数
    $scope.totalSize = 0;
    //总页数
    $scope.totalPages = 0;

    $scope.select_status = true;//显示所有状态
    $scope.showStatus = function(){
        $scope.select_status = false;//隐藏所有状态
    };
    $scope.overlayStatus = false;//查询进度条初始化
    $scope.status = false;//提示条初始化

    //分页查询
    $scope.getListByPage = function() {
        $scope.overlayStatus = true;
        $scope.select_status = false;//隐藏所有状态

        var skuId = $scope.skuId;
        var orderId = $scope.orderId;
        var orderState = $scope.selected.value;
        var productName = $scope.productName;
        var telephone = $scope.telephone;
        var timeRange = $("#reservationtime").val();
        var startDate;
        var endDate;
        if (timeRange !=null && timeRange != "") {
            var startStr = timeRange.substring(0,16);
            var endStr = timeRange.substring(19,timeRange.length);
            startDate = startStr+':00';
            endDate = endStr+':59';
        }
        var params = { currentPage:$scope.currentPage,
            pageSize:$scope.pageSize,
            skuId:skuId,
            orderId:orderId,
            orderState:orderState,
            productName:productName,
            telephone:telephone,
            startDate:startDate,
            endDate:endDate};
        //console.log(params);
        $http({url:"/mark/zhe800/getHistoryOrder.php", method:"POST", params:params})
            .success(function(data) {
                $scope.overlayStatus = false;
                var result = data;
                if(result.pageDataList.length == 0){
                    $scope.status = true;
                    $scope.msg = 'no order data!';

                    $scope.currentPage = 1;
                    $scope.totalSize = 0;
                    $scope.totalPages = 1;
                    $scope.oderList = null;
                }else{
                    $scope.status = false;
                    $scope.currentPage = parseInt(result.currentPage);
                    $scope.totalSize = result.totalNum;
                    $scope.totalPages = result.totalPage;

                    $scope.oderList = result.pageDataList;
                    angular.forEach($scope.oderList,function(order_info,oindex,array){
                        if(order_info.status == null || order_info.status == ""){
                            order_info.status = "";
                        }else{
                            if(order_info.status == "1"){
                                order_info.status = "等待买家付款"
                            }else if(order_info.status == "2"){
                                order_info.status = "等待发货"
                            }else if(order_info.status == "3"){
                                order_info.status = "等待买家确认收货"
                            }else if(order_info.status == "5"){
                                order_info.status = "交易成功"
                            }else if(order_info.status == "7"){
                                order_info.status = "交易关闭"
                            }
                        }
                    });
                }
            })
            .error(function(data) {
                $scope.overlayStatus = false;
                $scope.status = true;
                $scope.msg = 'Request processing failed!';
            });
    }

    //查询订单
    $scope.queryZ8Order = function (){
        $scope.currentPage = parseInt(1);//当前页
        $scope.getListByPage();
    };

    //初始化加载
    setTimeout($scope.queryZ8Order,500);

    //首页
    $scope.prevPage = function(){
        if ($scope.currentPage == 1) {
            return;
        } else {
            $scope.currentPage = parseInt(1);
            $scope.getListByPage();
        }
    };
    //上一页
    $scope.pageUp = function(){
        if ($scope.currentPage == 1) {
            return;
        } else {
            $scope.currentPage = parseInt($scope.currentPage) - 1;
            $scope.getListByPage();
        }
    };
    //下一页
    $scope.pageDown = function(){
        if ($scope.currentPage == $scope.totalPages) {
            return;
        } else {
            $scope.currentPage = parseInt($scope.currentPage) + 1;
            $scope.getListByPage();
        }
    };
    //尾页
    $scope.nextPage = function(){
        if ($scope.currentPage == $scope.totalPages) {
            return;
        } else {
            $scope.currentPage = parseInt($scope.totalPages);
            $scope.getListByPage();
        }
    };

    $scope.pageSizeNum = $scope.pageSize;//每页显示数
    $scope.pageLength = function(){
        if($scope.pageSizeNum == undefined){
            $scope.pageSize = 20;
        } else{
            $scope.pageSize = $scope.pageSizeNum;
        }
        $scope.currentPage = parseInt(1);//当前页
        $scope.getListByPage();
    }

});

adminLteApp.controller('ErrorCtrl', function ($scope, $http, $location) {
    $scope.message = $location.search().message;
    //console.log("ErrorCtrl");
});
