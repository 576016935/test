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
        $("#pid").html("</b>[<a th:href=\"${#request.getContextPath()+'http://localhost:8084/sso/tologin'}\">登录</a>][<a href=\"\">注册</a>]")
    }
}