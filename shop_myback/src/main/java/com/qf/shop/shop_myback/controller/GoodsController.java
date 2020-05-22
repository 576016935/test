package com.qf.shop.shop_myback.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.google.gson.Gson;
import com.qf.entity.Goods;
import com.qf.service.IGoodsService;
import com.qf.utils.HttpClientUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


/**
 * 跨域的问题：
 * 同源策略原则：同协议、同ip（同域名）、同端口
 * 当前端的资源访问后端服务的时候，如果不符合同源策越就发生了所谓的跨域
 *
 * 1、浏览器规定，from表单不用遵循同源策略
 * 2、通常来说，跨域问题是浏览器的行为，不是服务器的行为。一般来说浏览器不会限制跨域的请求发出，而是限制跨域的响应返回
 * 3、浏览器的跨域限制是为了安全考虑
 *
 * 跨域的解决办法：
 * 1、前台先发送ajax到自己的controller中，然后自己的controller再通过某种方式请求到其他系统的接口数据，然后再返回到页面上
 * 2、jquery的jsonp方式实现跨域的解决（利用了浏览器的漏洞）
 * 3、直接采用springmvc的跨域解决办法 - springmvc会在响应头中设置一个参数，浏览器看到这个参数以后就不会拦截跨域请求了
 *  @CrossOrgin
 *
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {

    /**
     * 整合springboot后帮我们创建的工具类，
     *
     * 图片上传的工具类，通过工具类可以获取图片的地址
     */
    @Autowired
    private FastFileStorageClient fastFileStorageClient;

    @Reference
    private IGoodsService goodsService;

    @Value("${image.path}")
    private String path;


    @RequestMapping("/goodsList")
    public String queryAll(Model model){

        List<Goods> goods = goodsService.queryAll();
        model.addAttribute("goods",goods);
        model.addAttribute("path",path);

        return "goodsList";
    }

    @RequestMapping("/goodsadd")
    public String goodsadd(Goods goods,@RequestParam("file") MultipartFile file) throws IOException {
        //System.out.println(goods);
        //System.out.println(file.getOriginalFilename());
        System.out.println("图片上传的地址："+file);

        //文件上传  fastdfs ,获取图片的地址
        StorePath spath = fastFileStorageClient.uploadImageAndCrtThumbImage(file.getInputStream(), file.getSize(), "JPG", null);

        String fullPath = spath.getFullPath();
        System.out.println(fullPath);

        //将url放入goods中
        goods.setGimage(fullPath);

        System.out.println("图片后半部分地址:"+fullPath);
        //将数据存入数据库
       goods=goodsService.goodsAdd(goods);

        System.out.println("主键回填："+goods.getId());

        /**
         * 调用索引工程同步索引库
         * 传参：ID和对象
         */
        HttpClientUtils.sendJsonPost("http://localhost:8082/solr/add",new Gson().toJson(goods));
        HttpClientUtils.sendJsonPost("http://localhost:8083/item/createhtml", new Gson().toJson(goods));

       //http调用item系统生成静态网页

        return "redirect:goodsList";
    }

    @RequestMapping("/queryNewAll")
    @ResponseBody
    @CrossOrigin//用来解决跨域问题
    public List<Goods> queryNewAll(){

        List<Goods> goods = goodsService.queryNewAll();
        System.out.println(goods);

        return goods;
    }
}
