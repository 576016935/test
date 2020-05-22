package com.qf.order.shop_order.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.entity.Address;
import com.qf.entity.Cart;
import com.qf.entity.Orders;
import com.qf.entity.User;
import com.qf.service.IAddressService;
import com.qf.service.ICartService;
import com.qf.service.IOrderService;
import com.qf.utils.IsLogin;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Reference
    private ICartService cartService;

    @Reference
    private IAddressService addressService;

    @Reference
    private IOrderService orderService;

    /**
     * 编辑订单
     * @return
     */
    @IsLogin(tologin = true)
    @RequestMapping("/orderedit")
    public String editOrder(Integer[] gid, User user, Model model){
        System.out.println("编辑订单:"+ Arrays.toString(gid));
        System.out.println("登录的用户信息："+user);

        //通过商品id和用户id去购物车列表中查询购买的信息
        List<Cart> cartList = cartService.queryCartByGids(gid, user.getId());

        //通过用户id查询用户地址信息
        List<Address> addresses = addressService.queryByUid(user.getId());
       // System.out.println("用户地址信息："+addresses);
        //System.out.println("商品信息："+cartList);
        model.addAttribute("carts",cartList);//购买的商品信息
        model.addAttribute("addresses",addresses);//用户地址
        return "orderedit";
    }

    /**
     * 添加收货地址
     * @param address
     * @param user
     * @return
     */
    @IsLogin
    @RequestMapping("/addaddress")
    @ResponseBody
    public Address address(Address address,User user){
        address.setUid(user.getId());
        addressService.addAddress(address);
        System.out.println("新添加地址："+address);
        return address;
    }

    /**
     * 下单
     * @param cid
     * @param aid
     * @return
     */
    @RequestMapping("/addorder")
    @IsLogin
    @ResponseBody
    public String addOrder(Integer[] cid,Integer aid,User user){
        System.out.println("下单的id："+Arrays.toString(cid));
        System.out.println("收货地址id："+aid);

        String orderid =null;
        try {
            //这里有可能会有问题，用一个异常捕获一下
            orderid = orderService.addOrderAndOrderDetils(cid, aid, user.getId());
        }catch (Exception e){
            e.printStackTrace();
        }

        return orderid;
    }

    /**
     *
     * 查询我的订单
     */
    @IsLogin
    @RequestMapping("/orderlist")
    public String orderList(User user, Model model){
        List<Orders> orders = orderService.queryByUid(user.getId());
        model.addAttribute("orders",orders);
        return "orderlist";
    }
}
