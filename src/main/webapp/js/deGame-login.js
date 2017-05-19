/**
 * Created by liuyao on 2017/2/4.
 */
var app = angular.module('main',['ui.bootstrap','ngFileUpload',"ngAnimate","ui.router"]);
app.config(Config);
app.controller("LoginController",LoginController);
app.controller("SignController",SignController);
app.controller("MainController",MainController);
app.factory("LoginInterceptor",LoginInterceptor);

LoginController.$inject=["$state","$http"];
function LoginController($state,$http){
    var mv = this;
    mv.account = "";
    mv.password = "";
    mv.doLoign = doLoign;

    if(isLogin()){
        $state.go("main");
    }

    function doLoign(){
        if(!mv.account || !mv.password){
            alert("账号密码填写不完整");
            return
        }
        var params = {
            account:mv.account,
            password:mv.password
        };
        $http.post("/login/doLogin",params).success(function(data){
            if(data.success){
                localStorage.loginTime = new Date().getTime();
                $state.go("main");
            }else{
                alert(data.message);
            }
        })
    }
    
    function isLogin() {
        if(!localStorage.loginTime){
            return false;
        }
        return new Date().getTime()-localStorage.loginTime<3600000;//一个小时有效
    }
}

SignController.$inject = ["$state","$http"];
function SignController($state,$http){
    var mv = this;
    mv.account = "";
    mv.name = "";
    mv.password = "";
    mv.dpassword = "";
    mv.doSign = doSign;
    function doSign(){
        if(!mv.account || mv.account.length<6){
            alert("账号必须填写,且长度必须大于6");
            return;
        }
        if(!mv.name){
            alert("请填写你的姓名");
            return;
        }
        if(!mv.password || mv.password.length<6){
            alert("密码必须填写,且长度必须大于6");
            return;
        }
        if(mv.password != mv.dpassword){
            alert("两次输入的密码不一致");
            return;
        }
        
        var params = {
            name:mv.name,
            account:mv.account,
            password:mv.password
        };

        $http.post("/login/doSign",params).success(function(data){
            if(data.success){
                alert("注册成功");
                $state.go("main");
            }else{
                alert(data.message);
            }
        })
    }
}


MainController.$inject=['$http','Upload','$q'];
function MainController($http,Upload,$q){
    var mv = this;
    mv.company=3;
    mv.selectCompany = selectCompany;
    mv.submit = submit;
    mv.uploadMainImage = uploadMainImage;
    mv.uploadDefImage = uploadDefImage;

    function selectCompany(company){
        mv.company = company;
        loadData();
    }
    
    function uploadMainImage(file) {
        uploadImage(file).then(function (url) {
            mv.mainImage = url;
        });
    }

    function uploadDefImage(file){
        uploadImage(file).then(function (url) {
            mv.defImage = url;
        });
    }

    function submit(){
        try{
            if(!mv.title) throw "作品主题不能为空";
            if(!mv.mainImage) throw "必须上传作品主图";
            if(!mv.defImage) throw "作品详图不能为空";
        }catch(e){
            alert(e);
            return;
        }
        var body = {
            company:mv.company,
            title:mv.title,
            mainImage:mv.mainImage,
            defImage:mv.defImage
        };
        $http.post("/works/saveWorks",body).success(function (data) {
            if(data.success){
                loadData();
                alert("保存成功");
            }else{
                alert(data.message);
            }
        })
    }

    function loadData(){
        $http.get("/works/details?company="+mv.company).success(function(data){
            if(data.success && !!data.details){
                var details = data.details;
                mv.title = details.title;
                mv.mainImage = details.json.mainImage;
                mv.defImage = details.json.defImage;
            }else {
                mv.title = "";
                mv.mainImage = "";
                mv.defImage = "";
            }
        })
    }

    function uploadImage(file){
        var deferred = $q.defer();
        Upload.upload({ url: '/works/uploadImage',data:{file: file}}).then(
            function (resp) {
                var data = resp.data;
                deferred.resolve(data.url);
            },
            function (resp) {console.log('Error status: ' + resp.status);}
        );
        return deferred.promise;
    }

    loadData();
}

Config.$inject = ['$httpProvider','$stateProvider','$urlRouterProvider']
function Config($httpProvider,$stateProvider,$urlRouterProvider) {
    $httpProvider.interceptors.push(LoginInterceptor);

    $urlRouterProvider.otherwise("/login");

    $stateProvider.state('login',{url:"/login",templateUrl:'login.html',controller:'LoginController',controllerAs:'mv'})
        .state('sign',{url:"/sign",templateUrl:'sign.html',controller:'SignController',controllerAs:'mv'})
        .state("main",{url:"/main",templateUrl:'main.html',controller:'MainController',controllerAs:'mv'})
}

LoginInterceptor.$inject=["$q","$injector"]
function LoginInterceptor($q,$injector){
    var responseInterceptor = {
        response: function(response) {
            var deferred = $q.defer();
            var data = response.data;
            if(!data.success && !!data.notLogin){
                $injector.invoke(goLogin);
                deferred.reject(response);
            }else{
                deferred.resolve(response);
            }
            return deferred.promise;
        }
    };
    return responseInterceptor;

    goLogin.$inject = ['$state'];
    function goLogin($state){
        $state.go("login");
    }
}
