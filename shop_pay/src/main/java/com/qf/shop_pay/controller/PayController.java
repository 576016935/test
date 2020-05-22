package com.qf.shop_pay.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.qf.entity.Orders;
import com.qf.service.IOrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/pay")
public class PayController {

    @Reference
    private IOrderService orderService;

    @RequestMapping("/alipay")
    public String alipay(String orderid, HttpServletResponse response, Model model){

        //通过订单id查询订单详情
       // Orders orders = orderService.queryByOrderId(orderid);
        //System.out.println("订单详情："+orders);

        //调用支付宝进行支付,订单号和总金额
        //pay(orders,response);

        model.addAttribute("orderid",orderid);

        return "gopay";

    }

    @RequestMapping("/goalipay")
    public void pay(String orderid,HttpServletResponse response ){
        Orders orders = orderService.queryByOrderId(orderid);
        AlipayClient alipayClient = new DefaultAlipayClient(
                //沙箱环境支付宝网关
                "https://openapi.alipaydev.com/gateway.do",


                //沙箱应用APPID
                "2016092000558602",

                //业务私钥
                "MIIEwAIBADANBgkqhkiG9w0BAQEFAASCBKowggSmAgEAAoIBAQC8MJMLBRPpe4ZCIhXvfYBHO9GHmVFXkZpoKlWcTaeo5CnWP+VA5JaLuHAyVQWSX7Ky2cq6ZKu6YJiZOXcsuMgYwE9UmS3dcO10s2ZkmGE1Ifte4PYXPWqpAPsHIkHVzxKKLK9yaV/rZZhPV4FYRgkK6S6gDfpa95/xNP6O/OnNNzlrNe1tp171wGfTgrsYQs5ZBXn7U4lGvqH6A5xtZJw9ZyatQCuvtGmR0DMHjQImTKpuGLKHyvLCZUoKoZCd8COfXcTgeer8MO5m5ZBTnMg+0f4WudUmOZdQBlKrJWGBT5tyTLyekyO5YytSJTRWcjHrQb6WOceKGgir1knp69XjAgMBAAECggEBAKXskgk6TecjkSmIEh0ZY1vrIv/Sr0RtTV2gTeFFGpkvqq+LoJ9JrG6+jLXj/sii9dT5b+RN1Je8iW83mprkxRAFpyxo7mZ7JfIqJxJ0w+hr8M0jvfaZoQwnoC0XCELyk18erQZMYFUVO2hhN9rTzS9Aj2pxSJaiE/RBDFyYI7BaBCJ6N2SyHBZr3m6B96c9kWGuxc0qC+1aNeivuSuvfwcKTiXC35DYxlrvnJ27dkdWji8qKiHIue7NPyIyxjqy6gtjayREvJV6IkO695H6OS7WAutVeAcVr9Omk7E2qz/9IvouDlPFtXUWyz9qfkeSIh9Bx+70FKh/HFQsJzs6AMECgYEA/zMYSWNj/PNh3AY0Ksp2qhuKgwK4UhhCJ58vUfmFxmzhaOg3DlrC56Wqtkefc6vASLpF7qwg6tYm8f4jmInMdWfVupWVEyV3MtgrGgoNFrVN0lWujBMze+VevE1U6mua8eFYiXUEv+78PDNGwTKkxzFPLpZkPyu6sazjrSvuoqECgYEAvMetB73S5rNsvd0M94j1p+IN93aSPBs8VXs2Y4EpvH4bc5QTVF35s+p/nKIw46Yk2JaTryV1AC0bloYdbe7pDNgE+62bhWtvwEWk27ZHbvh6e5ecC9AqmtiHygH9KBxNAnXN8P2408ltIXLaJ1YBab7aNBev6WZn3DYd8IKHLgMCgYEAunqKp63R9ZefDZkW66HQflLrrvoA2DRTuwIuINeVcj5DpFXzTTDzNTQwf0vs7yg0tLAww8vRrehYwwHy3tbqqWv7mIEPUtNQu72MGVX45HwNZsInbsHlIAamaDCV+UoyCceh26e/lcS7Wf4aGLi6Abg9ZQyOKj75CDYOlI8CrKECgYEAibYfw0X2U9UPvnFCxFBk49ND3QHiK3ayzKjVREE08QwNeoLKlwD25whpjxnCEE9jEZzGnz3z2+0L8wJi9Y8S8wuilvZOPUXif6BOYvjVo14f+4jdjMCOU+chJ3vL8Taz9td5JBPygIyvz3a5LXjQDxPqmZWt/dV2bm4L6SdO9YECgYEAna7Ftk18JqzmMVMbHYXK6zKD84kExY43ejTKDT9Q3oJBk2qUrqJepjezeezZ1sU7sPgBazV1YIi+CFJiYTiZ/ExxJmuuy57WDytlIGxr44Fgb2zkZK4DjDaypuhDGD7K9MTyJsNEvP6dhWXHl3IlxDn9E/6Jus4nOzA8/oPWGJ8=",
                "json",//格式
                "UTF-8",//编码
                //支付宝公钥
                "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA1jbrC+KMW0NY9tjcPAS00Ez6NCn7YBldnPPd5a9kZIRlk+Oh8gDLIs0plaue1Z731t/ANu6GsZhd3+5K/Ztmyixu17L8+ugND7UPmks68WQo+keus8PrwA0kucM/8zV+jlnbYrZEcrZl7LFylClosc2N5p2TxMIP4yyqkl+9m+vqSgqQN0dV1uabay7fGkpNAtBHA/eaEXWG1mumGniNthGjnKTiMxa0Gs6BAcKLikyFSIm54jQi6EB8zAV7McHAe66xLZVOQAOpOy+tQkuW/6XKqs92i1QQwuecgtYbsfvzSt84i7OzvdbhNYhe20kkTgypBzag7cwiEtIVOkvOYwIDAQAB",
                "RSA2"); //获得初始化的AlipayClient

        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();//创建API对应的request
        alipayRequest.setReturnUrl("http://www.baidu.com");//设置同步响应URL，付款成功跳转的页面
        alipayRequest.setNotifyUrl("http://www.kugou.com/");//设置异步响应URL
        alipayRequest.setBizContent("{" +
                        //订单id
                "    \"out_trade_no\":\""+orders.getOrderid()+"\"," +
                "    \"product_code\":\"FAST_INSTANT_TRADE_PAY\"," +
                        //订单金额
                "    \"total_amount\":"+orders.getOprice()+"," +
                "    \"subject\":\""+orders.getOrderid()+"\"," +
                "    \"body\":\""+orders.getOrderid()+"\"," +
                "    \"passback_params\":\"merchantBizType%3d3C%26merchantBizNo%3d2016010101111\"," +
                "    \"extend_params\":{" +
                "    \"sys_service_provider_id\":\"2088511833207846\"" +
                "    }"+
                "  }");//填充业务参数
        System.out.println("订单id:"+orders.getOrderid());
        String form="";
        try {
            form = alipayClient.pageExecute(alipayRequest).getBody(); //调用SDK生成表单
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        response.setContentType("text/html;charset=utf-8");
        try {
            response.getWriter().write(form);//直接将完整的表单html输出到页面
            response.getWriter().flush();
            response.getWriter().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 验证当前支付时候成功
     * @param orderid
     * @return
     */
    @RequestMapping("/isok")
    public String isOk(String orderid){

        //通过订单号查询支付宝是否支付成功
        AlipayClient alipayClient = new DefaultAlipayClient(
                //沙箱环境支付宝网关
                "https://openapi.alipaydev.com/gateway.do",

                //沙箱应用APPID
                "2016092000558602",

                //业务私钥
                "MIIEwAIBADANBgkqhkiG9w0BAQEFAASCBKowggSmAgEAAoIBAQC8MJMLBRPpe4ZCIhXvfYBHO9GHmVFXkZpoKlWcTaeo5CnWP+VA5JaLuHAyVQWSX7Ky2cq6ZKu6YJiZOXcsuMgYwE9UmS3dcO10s2ZkmGE1Ifte4PYXPWqpAPsHIkHVzxKKLK9yaV/rZZhPV4FYRgkK6S6gDfpa95/xNP6O/OnNNzlrNe1tp171wGfTgrsYQs5ZBXn7U4lGvqH6A5xtZJw9ZyatQCuvtGmR0DMHjQImTKpuGLKHyvLCZUoKoZCd8COfXcTgeer8MO5m5ZBTnMg+0f4WudUmOZdQBlKrJWGBT5tyTLyekyO5YytSJTRWcjHrQb6WOceKGgir1knp69XjAgMBAAECggEBAKXskgk6TecjkSmIEh0ZY1vrIv/Sr0RtTV2gTeFFGpkvqq+LoJ9JrG6+jLXj/sii9dT5b+RN1Je8iW83mprkxRAFpyxo7mZ7JfIqJxJ0w+hr8M0jvfaZoQwnoC0XCELyk18erQZMYFUVO2hhN9rTzS9Aj2pxSJaiE/RBDFyYI7BaBCJ6N2SyHBZr3m6B96c9kWGuxc0qC+1aNeivuSuvfwcKTiXC35DYxlrvnJ27dkdWji8qKiHIue7NPyIyxjqy6gtjayREvJV6IkO695H6OS7WAutVeAcVr9Omk7E2qz/9IvouDlPFtXUWyz9qfkeSIh9Bx+70FKh/HFQsJzs6AMECgYEA/zMYSWNj/PNh3AY0Ksp2qhuKgwK4UhhCJ58vUfmFxmzhaOg3DlrC56Wqtkefc6vASLpF7qwg6tYm8f4jmInMdWfVupWVEyV3MtgrGgoNFrVN0lWujBMze+VevE1U6mua8eFYiXUEv+78PDNGwTKkxzFPLpZkPyu6sazjrSvuoqECgYEAvMetB73S5rNsvd0M94j1p+IN93aSPBs8VXs2Y4EpvH4bc5QTVF35s+p/nKIw46Yk2JaTryV1AC0bloYdbe7pDNgE+62bhWtvwEWk27ZHbvh6e5ecC9AqmtiHygH9KBxNAnXN8P2408ltIXLaJ1YBab7aNBev6WZn3DYd8IKHLgMCgYEAunqKp63R9ZefDZkW66HQflLrrvoA2DRTuwIuINeVcj5DpFXzTTDzNTQwf0vs7yg0tLAww8vRrehYwwHy3tbqqWv7mIEPUtNQu72MGVX45HwNZsInbsHlIAamaDCV+UoyCceh26e/lcS7Wf4aGLi6Abg9ZQyOKj75CDYOlI8CrKECgYEAibYfw0X2U9UPvnFCxFBk49ND3QHiK3ayzKjVREE08QwNeoLKlwD25whpjxnCEE9jEZzGnz3z2+0L8wJi9Y8S8wuilvZOPUXif6BOYvjVo14f+4jdjMCOU+chJ3vL8Taz9td5JBPygIyvz3a5LXjQDxPqmZWt/dV2bm4L6SdO9YECgYEAna7Ftk18JqzmMVMbHYXK6zKD84kExY43ejTKDT9Q3oJBk2qUrqJepjezeezZ1sU7sPgBazV1YIi+CFJiYTiZ/ExxJmuuy57WDytlIGxr44Fgb2zkZK4DjDaypuhDGD7K9MTyJsNEvP6dhWXHl3IlxDn9E/6Jus4nOzA8/oPWGJ8=",
                "json",//格式
                "UTF-8",//编码
                //支付宝公钥
                "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA1jbrC+KMW0NY9tjcPAS00Ez6NCn7YBldnPPd5a9kZIRlk+Oh8gDLIs0plaue1Z731t/ANu6GsZhd3+5K/Ztmyixu17L8+ugND7UPmks68WQo+keus8PrwA0kucM/8zV+jlnbYrZEcrZl7LFylClosc2N5p2TxMIP4yyqkl+9m+vqSgqQN0dV1uabay7fGkpNAtBHA/eaEXWG1mumGniNthGjnKTiMxa0Gs6BAcKLikyFSIm54jQi6EB8zAV7McHAe66xLZVOQAOpOy+tQkuW/6XKqs92i1QQwuecgtYbsfvzSt84i7OzvdbhNYhe20kkTgypBzag7cwiEtIVOkvOYwIDAQAB",
                "RSAb2"); //获得初始化的AlipayClient
        //查询请求
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        request.setBizContent("{" +
                "\"out_trade_no\":\""+orderid+"\","+" }");
        AlipayTradeQueryResponse response = null;
        try {
            response = alipayClient.execute(request);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        if(response.isSuccess()){
            String tradeStatus = response.getTradeStatus();
            System.out.println("response返回的状态码："+tradeStatus);
            if(tradeStatus.equals("TRADE_SUCCESS")){
                //支付成功修改订单状态
                int i = orderService.updateStatusByOrderId(orderid, 1);
                System.out.println("订单状态码："+i);

            }
        } else {
            System.out.println("调用失败");
        }

        //如果支付成功修改订单状态

        return "redirect:http://localhost:8086/order/orderlist";
    }
}


