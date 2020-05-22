//Ajax进行登录认证
$(function () {
    $.ajax({
        url:"http://localhost:8084/sso/islogin",
        //jsonp解决了cookie不能传递的问题
        dataType:"jsonp"
    });
})

function callback(data) {
    if(data!=null){
        $("#pid").html(data.name+"您好，欢迎来到<b><a href=\"/\">ShopCZ商城</a><a href=\"http://localhost:8084/sso/loginout\">注销</a>")
    }else{
       // $("#pid").html("</b>[<a href=\"http://localhost:8084/sso/tologin\">登录</a>][<a href=\"\">注册</a>]")
        $("#pid").html("[<a href=\"javascript:login();\">登录</a>][<a href=\"\">注册</a>]");
    }
}
function login(){
    //获取登录时的页面的URL
    var returnUrl = location.href;
    returnUrl = encodeURI(returnUrl);//解决关键字的乱码问题
    //因为&在地址栏中会当成分隔符，把&换成其他字符就就可以解决这个问题
    returnUrl = returnUrl.replace("&", "*");

    //跳转到登录页面
    location.href="http://localhost:8084/sso/tologin?returnUrl=" + returnUrl;
}