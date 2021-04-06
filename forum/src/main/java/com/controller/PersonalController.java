package com.controller;

import com.dto.Goods;
import com.dto.Post;
import com.dto.User;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/personal")
public class PersonalController {
    @Autowired
    UserService userService;
    @Autowired
    PostService postService;
    @Autowired
    PostTypeService postTypeService;
    @Autowired
    GoodsService goodsService;
    @Autowired
    GoodsTypeService goodsTypeService;
    @Autowired
    CommentService commentService;
    /**
     * 跳转个人信息编辑页面
     */
    @RequestMapping("/edit")
    public String message(Model model, HttpSession session) {
        System.out.println("跳转个人信息编辑页面");
        //用户信息
        String email = (String) session.getAttribute("email");
        User user = userService.findUserByEmail(email);
        model.addAttribute("user", user);
        return "personal/personalEdit";
    }
    /**
     * 个人信息编辑成功，数据库更新
     */
    @RequestMapping("/update")
    @ResponseBody
    public String update(User user,HttpSession session) {
        //用户信息
        String email = (String) session.getAttribute("email");
        User users = userService.findUserByEmail(email);
        user.setId(users.getId());
        //更新用户信息
        userService.updateUserById(user);
        return "success";
    }
    /**
     * 跳转个人发布的帖子列表页面
     */
    @RequestMapping("/personalPosts/{page}")
    public String personalPosts(@PathVariable("page") int page,HttpSession session,Model model) {
        PageHelper.startPage(page, 10);// 每page(页)包含10条
        //用户信息
        String email = (String) session.getAttribute("email");
        User user = userService.findUserByEmail(email);
        List<Post> posts = postService.findPostByUserId(user.getId());
        for (Post post:posts) {
            post.setUserName(user.getNickname());
        }
        PageInfo<Post> pageInfo = new PageInfo<>(posts);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("posts", posts);
        return "personal/personalPosts";
    }
    /**
     * 个人操作自己发布的帖子(删除)
     *
     * @return
     */
    @RequestMapping(value = "/delete/{postId}")
    public String delete(@PathVariable("postId") int postId) {
        postService.deletePostById(postId);
        return "redirect:/personal/personalPosts/1";
    }
    /**
     * 跳转个人发布商品页面
     */
    @RequestMapping("/personalGoods/{page}")
    public String personalGoods(@PathVariable("page") int page,HttpSession session,Model model) {
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
        return "personal/personalGoods";
    }
    /**
     * 个人操作自己发布的商品(删除)
     *
     * @return
     */
    @RequestMapping("/delGoods/{goodsId}")
    public String deleteGoods(@PathVariable("goodsId") int goodsId) {
        goodsService.deleteGoodsById(goodsId);
        return "redirect:/personal/personalGoods/1";
    }
}
