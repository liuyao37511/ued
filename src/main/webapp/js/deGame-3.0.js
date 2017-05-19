var app = angular.module('main',['ui.bootstrap',"ngAnimate"]);
app.run(Run);
app.controller("mainCtrl",MainCtrl);
app.controller("detailsCtrl",DetailsCtrl);
app.controller("DoVoteCtrl",DoVoteCtrl);

Run.$inject = ["$http"];
function Run($http){
    //生成token
    var token = localStorage.voteCode;
    if(!token){
        setToken();
    }

    function setToken(){
        var token = new Date().getTime();
        $http.get("/yangzhengToken?token="+token).success(function(data){
            if(data.success){
                localStorage.voteCode = data.token;
            }else{
                setToken();
            }
        });
    }
}


MainCtrl.$inject = ["$uibModal","$http"];
function MainCtrl($uibModal,$http) {
    var mv = this;

    mv.tuiaList = [];
    mv.mailaList = [];
    mv.duibaList = [];

    mv.openDetails = openDetails;
    mv.topiao = topiao;

    function openDetails(id){
        var modalInstance = $uibModal.open({
            size:'md',
            animation: true,
            templateUrl: 'details.html',
            controller: 'detailsCtrl',
            controllerAs:'p',
            windowClass:"tupian",
            resolve:{
                id:id
            }
        });
    }

    function topiao(item){
        if(item.isTou){
            return ;
        }
        var token = localStorage.voteCode;
        if(!token){
            return;
        }
        var modalInstance = $uibModal.open({
            size:'md',
            animation: true,
            templateUrl: 'doVote.html',
            controller: 'DoVoteCtrl',
            controllerAs:'p',
            backdrop:'static',
            resolve:{
                works:item
            }
        });
        modalInstance.result.then(function(){
            var params = {
                token:token,
                worksId:item.id
            };
            $http.post("/toupiao",params).success(function(data){
                if(data.success){
                    loadData();
                }else{
                    alert(data.message);
                }
            })
        });
    }

    function loadData(){
        var token = localStorage.voteCode;
        if(!token){
            token = "";
        }
        $http.post("/getWorksDate?token="+token).success(function(data){
            if(data.success){
                mv.tuiaList = [].concat(data["1"]);
                mv.mailaList = [].concat(data["2"]);
                mv.duibaList = [].concat(data["3"]);
            }else{
                alert(data.message);
            }
        });
    }
    loadData();
}

DetailsCtrl.$inject = ["$uibModalInstance","id","$http","$scope"];
function DetailsCtrl($uibModalInstance,id,$http,$scope) {
    var mv = this;



    $http.get("/getOneWorksContext?id="+id).success(function(data){
        mv.defImage = data.item.defImage;
    });

    mv.close = function () {
        $uibModalInstance.dismiss();
    };

    $uibModalInstance.rendered.then(function(){

        $(".tupian").on("click",function(){
            $uibModalInstance.dismiss();
            $scope.$digest();
        });
    });
}

DoVoteCtrl.$inject = ["$uibModalInstance","works"];
function DoVoteCtrl($uibModalInstance,works){
    var mv = this;
    mv.title = works.title;
    mv.submit = function(){
        $uibModalInstance.close();
    };

    mv.close = function () {
        $uibModalInstance.dismiss();
    }

}

