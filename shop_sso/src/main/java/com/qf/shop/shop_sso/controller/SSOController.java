package com.qf.shop.shop_sso.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.gson.Gson;
import com.qf.entity.ResultData;
import com.qf.entity.User;
import com.qf.service.IUserService;
import com.qf.utils.Constact;
import com.qf.utils.HttpClientUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("/sso")
public class SSOController {

    @Reference
    private IUserService userService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 跳转登录页面
     * @return
     */
    @RequestMapping("/tologin")
    public String tologin(String returnUrl, Model model){
        model.addAttribute("returnUrl",returnUrl);
        return "login";
    }

    /**
     * 用户登录
     * @return
     */
    @RequestMapping("/login")
    public String login(String username,
                        String password,
                        HttpServletResponse response,
                        String returnUrl,
                        @CookieValue(value = "cart_token",required = false) String  carts){

        ResultData<User> resultData = userService.login(username, password);
        System.out.println("服务器-->"+resultData);

        switch (resultData.getCode()){
            case 0:
                if(returnUrl==null||returnUrl.equals("")){
                    returnUrl ="http://localhost:8081";
                }else {
                    //把地址栏里的*号换成&
                    returnUrl = returnUrl.replace("*", "&");
                }
                //登录成功后将数据存瑞cookie
                //1，先生成UUID
                String token = UUID.randomUUID().toString();
                //2，调用Redis将UUID作为key，user作为value放入Redis中
                redisTemplate.opsForValue().set(token,resultData.getData());
                //3，设置保存时间
                redisTemplate.expire(token,7, TimeUnit.DAYS);
                //4，将cookie写入浏览器中,并设置超时时间
                Cookie cookie=new Cookie(Constact.LOGIN_TOKEN,token);
                cookie.setMaxAge(7*24*60*60);
                //将sso工程下的路径设置为整个项目的根目录
                cookie.setPath("/");
                response.addCookie(cookie);

                //4、登录成功进行购物车的合并
                if(carts != null){
                    Map<String, String> params = new HashMap<>();
                    params.put("uid", resultData.getData().getId() + "");

                    Map<String, String> header = new HashMap<>();
                    try {
                        header.put("Cookie", "cart_token=" + URLEncoder.encode(carts, "utf-8"));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                    String result = HttpClientUtils.sendPostParamsAndHeader("http://localhost:8085/cart/hebing", params, header);
                    System.out.println("--->" + result);
                    if(result.equals("succ")){
                        //清空临时购物车
                        Cookie cookie1 = new Cookie(Constact.CART_TOKEN, null);
                        cookie1.setMaxAge(0);
                        cookie1.setPath("/");
                        response.addCookie(cookie1);
                    }
                }

                //登录成功并跳转页面
                return "redirect:"+returnUrl;
            default:
                //登录失败并跳转到登录页面
                return "login";
        }
    }


    /**
     * 登录认证
     * @CookieValue("login_token",required = false)：从cookie中取登录后的用户对象
     * required = false:不设置为false的话会必须要求有数据，不然会报错，
     */
    @RequestMapping("/islogin")
    @ResponseBody
    @CrossOrigin//解决跨域请求的问题
    public String islogin(@CookieValue(value = "login_token",required = false) String login_token){

            User user=null;
            if(login_token!=null){
                //从Redis中获取登录用户的信息
                user = (User) redisTemplate.opsForValue().get(login_token);
            }
        return "callback("+ new Gson().toJson(user) +")";
    }

    /**
     * 登录用户注销
     * @return
     */
    @RequestMapping("/loginout")
    public String loginout(@CookieValue("login_token") String login_token,HttpServletResponse response){

        //1，删除Redis
        redisTemplate.delete(login_token);
        //2，删除cookie，把cookie的保留时间设置为0就行
        Cookie cookie=new Cookie(Constact.LOGIN_TOKEN,null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        //3，将设置好的cookie写进浏览器
        response.addCookie(cookie);
        return "login";
    }
}
