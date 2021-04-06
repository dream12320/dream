package com.controller;

import com.dto.GoodsType;
import com.dto.PostType;
import com.dto.User;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.service.GoodsTypeService;
import com.service.PostTypeService;
import com.service.UserService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    PostTypeService postTypeService;
    @Autowired
    GoodsTypeService goodsTypeService;
    @Value("${bg.email}")
    private String bgEmail;
    @Value("${bg.password}")
    private String bgPassword;

    /**
     * 跳转用户登陆页面
     */
    @RequestMapping("/login")
    public String login(){
        return "login";
    }
    /**
     * 跳转用户注册界面
     */
    @RequestMapping(value = "/register")
    public String register() {
        return "register";
    }
    /**
     * 前台验证用户登陆
     */
    @RequestMapping(value = "/validate", method = RequestMethod.POST)
    @ResponseBody
    public String validate(@RequestParam("email") String email, @RequestParam("password") String password, HttpSession session) {
        JSONObject jsonObject=new JSONObject();
        User user=null;
        if(email.replace(" ", "").length()!=0){
            user=userService.findUserByEmail(email);
        }
        if(email.replace(" ", "").length()==0){
            jsonObject.put("result", "userNameIsNull");
        }else if(password.replace(" ", "").length()==0){
            jsonObject.put("result", "passwordIsNull");
        }else if(user!=null&&email.equals(user.getEmail())&&password.equals(user.getPassword())){
            jsonObject.put("result", "success");
            session.setAttribute("email",email);
        }
        return jsonObject.toString();
    }

    /**
     * 用户登陆成功，跳转到主页面
     */
    @RequestMapping(value = "/redirect")
    public String redirect(Model model,HttpSession session) {
        //帖子类型
        List<PostType> pTypeList=postTypeService.findAllPostType();
        model.addAttribute("pTypeList",pTypeList);
        //商品类型
        List<GoodsType> gTypeList=goodsTypeService.findAllGoodsType();
        model.addAttribute("gTypeList",gTypeList);
        //用户信息
        String email= (String) session.getAttribute("email");
        User user=userService.findUserByEmail(email);
        model.addAttribute("user",user);
        return "index";
    }
    /**
     * 用户注册成功，保存数据库，跳转到登录界面
     */
    @RequestMapping(value = "/backToLogin", method = RequestMethod.POST)
    @ResponseBody
    public String backToLogin(User user) throws IOException{
        User users=userService.findUserByEmail(user.getEmail());
        //判断此邮箱是否被注册
        if(users!=null&&users.getEmail().equals(user.getEmail())){
            return "fail";
        }else{
            if(user.getImage().length()!=0){
                String image=user.getImage();
                image=image.substring(image.lastIndexOf("\\")+1,image.length());
                user.setImage(image);
                copyPicture("C:/Users/dreamer/Pictures/forum/head/"+image, "e:/springboot+gradle/forum/src/main/resources/static/image/headPicture/"+image);
            }
            userService.insertUser(user);
            return "success";
        }
    }
    /**
     *用户注销
     */
    @RequestMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "login";
    }
    /**
     * 后台验证用户登陆
     */
    @RequestMapping(value = "/bg/validate", method = RequestMethod.POST)
    @ResponseBody
    public String bgValidate(@RequestParam("email") String email, @RequestParam("password") String password, HttpSession session) {
        JSONObject jsonObject=new JSONObject();
        if(email.replace(" ", "").length()==0){
            jsonObject.put("result", "userNameIsNull");
        }else if(password.replace(" ", "").length()==0){
            jsonObject.put("result", "passwordIsNull");
        }else if(email.equals(bgEmail)&password.equals(bgPassword)){
            jsonObject.put("result", "success");
            session.setAttribute("email",email);
        }
        return jsonObject.toString();
    }
    /**
     *后台用户注销
     */
    @RequestMapping("/bg/logout")
    public String bgLogout(HttpSession session){
        session.invalidate();
        return "bgLogin";
    }
    /**
     * 后台用户登陆成功，跳转到主页面
     */
    @RequestMapping(value = "/bg/redirect")
    public String bgRedirect() {
        return "bgIndex";
    }
    /**
     * 后台管理员查询前台注册所有用户
     */
    @RequestMapping(value = "/bg/getAllUser/{page}")
    public String bgGetAllUser(@PathVariable("page") int page,Model model) {
        PageHelper.startPage(page, 10);// 每page(页)包含10条
        List<User> userList=userService.findAllUser();
        model.addAttribute("userList",userList);
        //分页信息
        PageInfo<User> pageInfo = new PageInfo<>(userList);
        model.addAttribute("pageInfo",pageInfo);
        return "bg/manageUser";
    }

    /**
     * 后台管理员按照条件搜索普通用户
    */
    @RequestMapping(value = "/bg/searchUser/{page}")
    public String bgSearchUser(User user,@PathVariable("page") int page,Model model) {
        PageHelper.startPage(page, 10);// 每page(页)包含10条
        List<User> userLists=userService.findUserBySearch(user);
        model.addAttribute("userList",userLists);
        //分页信息
        PageInfo<User> pageInfo = new PageInfo<>(userLists);
        model.addAttribute("pageInfo",pageInfo);
        //把搜索条件回显
        model.addAttribute("user",user);
        return "bg/manageUser";
    }

    /**
     * 后台管理员单个删除普通用户
     */
    @RequestMapping(value = "/bg/deleteUserById/{id}")
    public String bgDeleteUserById(@PathVariable("id") int id) {
        userService.deleteUserById(id);
        return "redirect:/user/bg/getAllUser/1";
    }

    // 批量删除
    @RequestMapping("/bg/deleteManyUser")
    @ResponseBody
    public String deleteManyPost(@RequestParam("delItems") String delItems) {
        String[] items = delItems.split(",");
        int item[]=new int[items.length];
        for (int i = 0; i <item.length ; i++) {
            item[i]=Integer.parseInt(items[i]);
        }
        userService.deleteManyUser(item);
        return "success";
    }
    /**
     * 复制单个文件
     *
     * @return boolean
     * @throws Exception
     */
    public static void copyPicture(String srcPath, String destPath) throws IOException {
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
