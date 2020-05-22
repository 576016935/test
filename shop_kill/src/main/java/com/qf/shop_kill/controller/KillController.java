package com.qf.shop_kill.controller;

import com.qf.entity.Kill;
import com.qf.shop_kill.service.IKillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/kill")
public class KillController {

    @Autowired
    private IKillService killService;

    /**
     * 根据商品id查询商品信息
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/queryOne")
    public String queryOne(Integer id, Model model){
        Kill kill = killService.queryOne(id);
        System.out.println("查询的商品信息："+kill);
        model.addAttribute("kill",kill);
        return "miaosha";
    }

    /**
     * 抢购下单
     * @param id
     * @param number
     * @return
     */
    @RequestMapping("/miaosha")
    @ResponseBody
    public String miaosha(Integer id,Integer number){

        for(int i=0;i<15000;i++){
            new Thread(){
                @Override
                public void run() {
                    killService.miaosha(id,number,2);
                }
            }.start();
        }


        return "ok";
    }
}
