package com.controller;

import com.dto.Goods;
import com.dto.GoodsType;
import com.dto.User;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.service.GoodsService;
import com.service.GoodsTypeService;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 发商品
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    UserService userService;
    @Autowired
    GoodsService goodsService;
    @Autowired
    GoodsTypeService goodsTypeService;

    /**
     * 跳转到发布商品页面
     */
    @RequestMapping("/publish")
    public String message(Model model){
        List<GoodsType> gTypeList=goodsTypeService.findAllGoodsType();
        model.addAttribute("gTypeList",gTypeList);
        return "goods/publish";
    }

    /**
     *发布商品成功,插入数据库
     * @return
     */
    @RequestMapping(value = "/success", method = RequestMethod.POST)
    @ResponseBody
    public String success(Goods goods, HttpSession session) throws IOException{
        if(goods.getGoodsPicture().length()!=0){
            String picture=goods.getGoodsPicture();
            picture=picture.substring(picture.lastIndexOf("\\")+1,picture.length());
            goods.setGoodsPicture(picture);
            copyFile("C:/Users/dreamer/Pictures/forum/"+picture, "e:/springboot+gradle/forum/src/main/resources/static/image/goodsPicture/"+picture);
        }
        Date date=new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String publishTime=df.format(date);
        goods.setPublishTime(publishTime);
        //用户信息
        String email= (String) session.getAttribute("email");
        User user=userService.findUserByEmail(email);
        goods.setUserId(user.getId());
        goodsService.insertGoods(goods);
        return "success";
    }

    /**
     *根据商品类型查看商品
     * @return
     */
    @RequestMapping(value = "/getList/{typeId}/{page}")
    public String getList(@PathVariable("typeId") int typeId,@PathVariable("page") int page, Model model){
        PageHelper.startPage(page, 4);// 每page(页)包含4条
        List<Goods> gList=goodsService.findGoodsByTypeId(typeId);
        for (Goods goods : gList) {
            User user = userService.findUserById(goods.getUserId());//用户信息(使用用户名)
            goods.setUserName(user.getNickname());
        }

        PageInfo<Goods> pageInfo = new PageInfo<>(gList);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("gList",gList);
        //商品类型信息
        GoodsType goodsType = goodsTypeService.findGoodsTypeById(typeId);
        model.addAttribute("goodsType", goodsType);
        return "goods/list";
    }

    /**
     *在商品列表里根据用户ID查询商品列表
     * @return
     */
    @RequestMapping(value = "/getMoreList/{page}")
    public String getMoreList(@PathVariable("page") int page, Model model,HttpSession session){
        PageHelper.startPage(page, 4);// 每page(页)包含4条
        //用户信息
        String email = (String) session.getAttribute("email");
        User user = userService.findUserByEmail(email);
        List<Goods> gList=goodsService.findGoodsByUserId(user.getId());
        for (Goods goods : gList) {
            goods.setUserName(user.getNickname());
        }

        PageInfo<Goods> pageInfo = new PageInfo<>(gList);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("gList",gList);
        return "goods/moreList";
    }
    /**
     * 根据商品Id查看商品详情
     * @return
     */
    @RequestMapping(value = "/getDetail/{goodsId}")
    public String getDetail(@PathVariable("goodsId") int goodsId,Model model) {
        Goods goods=goodsService.findGoodsById(goodsId);
        User user = userService.findUserById(goods.getUserId());//用户信息(使用用户名)
        goods.setUserName(user.getNickname());
        model.addAttribute("goods", goods);
        model.addAttribute("user", user);
        //商品类型信息
        GoodsType goodsType = goodsTypeService.findGoodsTypeById(goods.getTypeId());
        model.addAttribute("goodsType", goodsType);
        return "goods/detail";
    }
    /**
     * 后台帖子商品管理（所有类型）
     *
     * @return
     */
    @RequestMapping(value = "/bg/getAllGoods/{page}")
    public String getAllPosts(@PathVariable("page") int page, Model model) {
        PageHelper.startPage(page, 5);// 每page(页)包含5条
        List<Goods> gList=goodsService.findAllGoods();
        for (Goods goods : gList) {
            User user = userService.findUserById(goods.getUserId());//用户信息(使用用户名)
            goods.setUserName(user.getNickname());
        }

        PageInfo<Goods> pageInfo = new PageInfo<>(gList);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("gList",gList);
        //商品类型信息
        List<GoodsType> goodsTypes = goodsTypeService.findAllGoodsType();
        model.addAttribute("goodsTypes", goodsTypes);
        return "bg/manageGoods";
    }
    /**
     * 后台商品管理按照商品类型查询
     *
     * @return
     */
    @RequestMapping(value = "/bg/getGoodsByType/{page}")
    public String getGoodsByType(@PathVariable("page") int page,GoodsType goodsType, Model model) {
        PageHelper.startPage(page, 5);// 每page(页)包含5条
        GoodsType gType=goodsTypeService.findGoodsTypeByName(goodsType.getName());

        List<Goods> gList=goodsService.findGoodsByTypeId(gType.getId());
        for (Goods goods : gList) {
            User user = userService.findUserById(goods.getUserId());//用户信息(使用用户名)
            goods.setUserName(user.getNickname());
        }

        PageInfo<Goods> pageInfo = new PageInfo<>(gList);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("gList",gList);
        //商品类型信息
        List<GoodsType> goodsTypes = goodsTypeService.findAllGoodsType();
        model.addAttribute("goodsTypes", goodsTypes);
        return "bg/manageGoods";
    }
    /**
     * 后台管理员单个删除商品
     */
    @RequestMapping(value = "/bg/deleteGoodsById/{id}")
    public String deleteGoodsById(@PathVariable("id") int id) {
        goodsService.deleteGoodsById(id);
        return "redirect:/goods/bg/getAllGoods/1";
    }
    /**
     * 后台管理员管理商品类型
     */
    @RequestMapping(value = "/bg/getAllGoodsTypes")
    public String getAllPostTypes( Model model) {
        //商品类型信息
        List<GoodsType> goodsTypes = goodsTypeService.findAllGoodsType();
        model.addAttribute("goodsTypes", goodsTypes);
        return "bg/manageGoodsType";
    }
    /**
     * 后台管理员添加商品类型
     */
    @RequestMapping(value = "/bg/addGoodsType")
    public String addPostType(GoodsType goodsType) {
        //帖子类型信息
        goodsTypeService.insertGoodsType(goodsType);
        return "redirect:/goods/bg/getAllGoodsTypes";
    }
    /**
     * 后台管理员删除商品类型
     */
    @RequestMapping(value = "/bg/deleteGoodsType/{id}")
    public String deletePostType(@PathVariable("id") int id) {
        //帖子类型信息
        goodsTypeService.deleteGoodsTypeById(id);
        return "redirect:/goods/bg/getAllGoodsTypes";
    }
    // 批量删除
    @RequestMapping("/bg/deleteManyGoods")
    @ResponseBody
    public String deleteManyGoods(@RequestParam("delItems") String delItems) {
        String[] items = delItems.split(",");
        int item[]=new int[items.length];
        for (int i = 0; i <item.length ; i++) {
            item[i]=Integer.parseInt(items[i]);
        }
        goodsService.deleteManyGoods(item);
        return "success";
    }
    /**
     * 复制单个文件
     *
     * @return boolean
     * @throws Exception
     */
    public static void copyFile(String srcPath, String destPath) throws IOException {
        // 打开输入流
         FileInputStream fis = new FileInputStream(srcPath);
        // 打开输出流
         FileOutputStream fos = new FileOutputStream(destPath);
        // 读取和写入信息
         int len = 0;
        // 创建一个字节数组，当做缓冲区
         byte[] b = new byte[1024];
        while ((len = fis.read(b)) != -1) {
            fos.write(b, 0, len);
        }
        // 关闭流  先开后关  后开先关
         fos.close();
         fis.close();
    }
}

