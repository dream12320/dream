package com.controller;

import com.dto.Comment;
import com.dto.Post;
import com.dto.PostType;
import com.dto.User;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.service.CommentService;
import com.service.PostService;
import com.service.PostTypeService;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 发帖子
 */
@Controller
@RequestMapping("/post")
public class PostController {
    @Autowired
    UserService userService;
    @Autowired
    PostService postService;
    @Autowired
    PostTypeService postTypeService;
    @Autowired
    CommentService commentService;

    /**
     * 跳转到发布帖子页面
     */
    @RequestMapping("/message")
    public String message(Model model) {
        //帖子类型
        List<PostType> pTypeList = postTypeService.findAllPostType();
        model.addAttribute("pTypeList", pTypeList);
        return "post/message";
    }

    /**
     * 发帖子成功,插入数据库
     *
     * @return
     */
    @RequestMapping(value = "/success", method = RequestMethod.POST)
    @ResponseBody
    public String success(Post post, HttpSession session) {
        //设置帖子的发布时间
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String postTime = df.format(date);
        post.setPostTime(postTime);
        //用户信息
        String email = (String) session.getAttribute("email");
        User user = userService.findUserByEmail(email);
        post.setUserId(user.getId());
        postService.insertPost(post);
        return "success";
    }
    /**
     * 首页根据帖子浏览量分为四个榜单（热帖、热议、钻石、实事新闻）
     *
     * @return
     */
    @RequestMapping(value = "/getList")
    public String getListByFlowNum(Model model) {
        //返回帖子信息
        List<Post> pList200 = postService.findPostByFlowNumMore200();
        List<Post> pList100 = postService.findPostByFlowNumMore100();
        List<Post> pList50 = postService.findPostByFlowNumMore50();
        List<Post> pList10 = postService.findPostByFlowNumMore10();

        model.addAttribute("pList200", pList200);
        model.addAttribute("pList100", pList100);
        model.addAttribute("pList50", pList50);
        model.addAttribute("pList10", pList10);
        return "home";
    }
    /**
     * 根据帖子类型查看帖子列表
     *
     * @return
     */
    @RequestMapping(value = "/getList/{typeId}/{page}")
    public String getList(@PathVariable("typeId") int typeId,@PathVariable("page") int page, Model model) {
        PageHelper.startPage(page, 10);// 每page(页)包含10条
        //返回帖子信息
        List<Post> pList = postService.findPostByTypeId(typeId);
        for (Post post : pList) {
            User user = userService.findUserById(post.getUserId());//用户信息(使用用户名)
            post.setUserName(user.getNickname());
        }
        PageInfo<Post> pageInfo = new PageInfo<>(pList);
        model.addAttribute("pList", pList);
        model.addAttribute("pageInfo", pageInfo);
        //帖子类型信息
        PostType postType = postTypeService.findPostTypeById(typeId);
        model.addAttribute("postType", postType);
        return "post/list";
    }
    /**
     * 根据帖子Id查看帖子详情
     * flag是一个标志，0表示从校园论坛跳转过来；1表示从个人中心---查看个人发布帖子跳转过来
     * @return
     */
    @RequestMapping(value = "/getDetail/{flag}/{postId}/{page}")
    public String getDetail(@PathVariable("flag") int flag,@PathVariable("page") int page,@PathVariable("postId") int postId,Model model) {
        PageHelper.startPage(page, 10);// 每page(页)包含10条
        //帖子评论列表
        List<Comment> commentList=commentService.findCommentByPostId(postId);
        model.addAttribute("commentList",commentList);
        PageInfo<Comment> pageInfo = new PageInfo<>(commentList);
        model.addAttribute("pageInfo", pageInfo);
        //帖子详情
        Post post=postService.findPostById(postId);
        User user = userService.findUserById(post.getUserId());//用户信息(使用用户名)
        post.setUserName(user.getNickname());
        post.setReplyNumber(commentList.size());//帖子的浏览量
        post.setFlowNumber(post.getFlowNumber()+1);//帖子的回复量
        model.addAttribute("post",post);
        //帖子类型信息
        PostType postType = postTypeService.findPostTypeById(post.getTypeId());
        model.addAttribute("postType", postType);
        //更新帖子(主要是更新浏览量和回复量)
        postService.updatePostById(post);
        if(flag==0){
            return "post/detail";
        }else{
            return "personal/personalPostsDetail";
        }
    }
    /**
     * 评论帖子,插入数据库
     *
     * @return
     */
    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    @ResponseBody
    public String comment(Comment comment) {
        //设置评论帖子的时间
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String commentTime = df.format(date);
        comment.setCommentTime(commentTime);
        commentService.insertComment(comment);
        return "success";
    }
    /**
     * 后台帖子管理（所有类型）
     *
     * @return
     */
    @RequestMapping(value = "/bg/getAllPosts/{page}")
    public String getAllPosts(@PathVariable("page") int page, Model model) {
        PageHelper.startPage(page, 10);// 每page(页)包含10条
        //返回帖子信息
        List<Post> pList = postService.findAllPost();
        for (Post post : pList) {
            //用户信息(使用用户名)
            User user = userService.findUserById(post.getUserId());
            if(user!=null){
                post.setUserName(user.getNickname());
            }
        }
        PageInfo<Post> pageInfo = new PageInfo<>(pList);
        model.addAttribute("pList", pList);
        model.addAttribute("pageInfo", pageInfo);
        //帖子类型信息
        List<PostType> postTypes = postTypeService.findAllPostType();
        model.addAttribute("postTypes", postTypes);
        return "bg/managePost";
    }
    /**
     * 后台帖子管理按照帖子类型查询
     *
     * @return
     */
    @RequestMapping(value = "/bg/getPostsByType/{page}")
    public String getPostsByType(@PathVariable("page") int page,PostType postType, Model model) {
        PageHelper.startPage(page, 10);// 每page(页)包含10条
        PostType pType=postTypeService.findPostTypeByName(postType.getName());
        //返回帖子信息
        List<Post> pList = postService.findPostByTypeId(pType.getId());
        for (Post post : pList) {
            //用户信息(使用用户名)
            User user = userService.findUserById(post.getUserId());
            post.setUserName(user.getNickname());
        }
        PageInfo<Post> pageInfo = new PageInfo<>(pList);
        model.addAttribute("pList", pList);
        model.addAttribute("pageInfo", pageInfo);
        //帖子类型信息
        List<PostType> postTypes = postTypeService.findAllPostType();
        model.addAttribute("postTypes", postTypes);
        return "bg/managePost";
    }
    /**
     * 后台管理员单个删除帖子
     */
    @RequestMapping(value = "/bg/deletePostById/{id}")
    public String DeletePostById(@PathVariable("id") int id) {
        postService.deletePostById(id);
        return "redirect:/post/bg/getAllPosts/1";
    }
    /**
     * 后台管理员批量删除帖子
     */
    @RequestMapping(value = "/bg/deleteManyPost")
    @ResponseBody
    public String deleteManyPost(@RequestParam("delItems") String delItems) {
        String[] items = delItems.split(",");
        int item[]=new int[items.length];
        for (int i = 0; i <item.length ; i++) {
            item[i]=Integer.parseInt(items[i]);
        }
        postService.deleteManyPost(item);
        return "success";
    }
    /**
     * 后台管理员管理帖子类型
     */
    @RequestMapping(value = "/bg/getAllPostTypes")
    public String getAllPostTypes( Model model) {
        //帖子类型信息
        List<PostType> postTypes = postTypeService.findAllPostType();
        model.addAttribute("postTypes", postTypes);
        return "bg/managePostType";
    }
    /**
     * 后台管理员添加帖子类型
     */
    @RequestMapping(value = "/bg/addPostType")
    public String addPostType(PostType postType) {
        //帖子类型信息
        postTypeService.insertPostType(postType);
        return "redirect:/post/bg/getAllPostTypes";
    }
    /**
     * 后台管理员删除帖子类型
     */
    @RequestMapping(value = "/bg/deletePostType/{id}")
    public String deletePostType(@PathVariable("id") int id) {
        //帖子类型信息
        postTypeService.deletePostTypeById(id);
        return "redirect:/post/bg/getAllPostTypes";
    }
}
