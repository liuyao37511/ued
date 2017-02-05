var app = angular.module('main',['ui.bootstrap',"ngAnimate"]);
app.controller("mainCtrl",MainCtrl);
app.controller("detailsCtrl",DetailsCtrl);
app.controller("InputVoteCode",InputVoteCode);
app.controller("DoVoteCtrl",DoVoteCtrl);

MainCtrl.$inject = ["$uibModal","$http"];
function MainCtrl($uibModal,$http) {
    var mv = this;

    mv.tuiaList = [];
    mv.mailaList = [];

    mv.openDetails = openDetails;
    mv.topiao = topiao;

    function openDetails(id){
        var modalInstance = $uibModal.open({
            size:'md',
            animation: true,
            templateUrl: 'details.html',
            controller: 'detailsCtrl',
            controllerAs:'p',
            backdrop:'static',
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
            openVote();
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
            }
            $http.post("/toupiao",params).success(function(data){
                if(data.success){
                    loadData();
                }else{
                    alert(data.message);
                }
            })
        });
    }

    function openVote(){
        var modalInstance = $uibModal.open({
            size:'sm',
            animation: true,
            templateUrl: 'vote.html',
            controller: 'InputVoteCode',
            controllerAs:'p',
            backdrop:'static',
        });
        modalInstance.result.then(function(token){
            localStorage.voteCode = token;
            loadData();
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
            }else{
                alert(data.message);
            }
        });
    }

    loadData();
}

DetailsCtrl.$inject = ["$uibModalInstance","id","$http"];
function DetailsCtrl($uibModalInstance,id,$http) {
    var mv = this;

    $http.get("/getOneWorksContext?id="+id).success(function(data){
        mv.defImage = data.item.defImage;
    })

    mv.close = function () {
        $uibModalInstance.dismiss();
    }
}

InputVoteCode.$inject = ["$uibModalInstance","$http"];
function InputVoteCode($uibModalInstance,$http){
    var mv = this;
    mv.submit = submit;

    if(!!localStorage.voteCode){
        $uibModalInstance.dismiss();
    }

    function submit(){
        if(!mv.token){
            return;
        }
        console.log("woo");
        $http.get("/yangzhengToken?token="+mv.token).success(function(data){
            if(data.success){
                $uibModalInstance.close(mv.token);
            }else{
                alert(data.message);
            }
        });
    }

    mv.close = function () {
        $uibModalInstance.dismiss();
    }
}
DoVoteCtrl.$inject = ["$uibModalInstance","works"]
function DoVoteCtrl($uibModalInstance,works){
    var mv = this;
    mv.title = works.title;
    mv.submit = function(){
        $uibModalInstance.close();
    }

    mv.close = function () {
        $uibModalInstance.dismiss();
    }

}

