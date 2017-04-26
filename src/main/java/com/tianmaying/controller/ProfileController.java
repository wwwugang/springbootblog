package com.tianmaying.controller;

import com.tianmaying.form.UserRegisterForm;
import com.tianmaying.model.User;
import com.tianmaying.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Controller
public class ProfileController {

    //这里你需要根据自己的文件目录位置进行修改
    @Value("${file.dir}")
    private String ROOT;

    private final ResourceLoader resourceLoader;

    @Autowired
    public ProfileController(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }
    @Autowired
    private UserService userService;

    @GetMapping(value = "/profile/{filename:.+}")
    @ResponseBody
    public ResponseEntity<?> getFile(@PathVariable String filename, HttpSession session) {
        User user = (User)session.getAttribute("CURRENT_USER");
        System.out.println(user.getAvatar());
        if(user.getAvatar()==null){
            System.out.println(resourceLoader.getResource("classpath:/static/img/catty.jpeg"));
            return ResponseEntity.ok(resourceLoader.getResource("classpath:/static/img/catty.jpeg"));
        }else {
            try {
                return ResponseEntity.ok(resourceLoader.getResource("file:" + Paths.get(ROOT, filename).toString()));
            } catch (Exception e) {
                return ResponseEntity.notFound().build();
            }
        }
    }

    @GetMapping("/profile")
    public String profile(Model model, HttpSession session) {

        User user = (User)session.getAttribute("CURRENT_USER");
        model.addAttribute("user", user);
        return "profile";
    }

    @PostMapping("/profile")
    public String handleFileUpload(@RequestParam(value = "avatar", required = false) MultipartFile file,
                                   @ModelAttribute("user") @Valid UserRegisterForm form,
                                   BindingResult result, HttpSession session,
                                   final RedirectAttributes redirectAttributes) throws IOException {

        User user = (User)session.getAttribute("CURRENT_USER");
        User userform = form.toUser();
        userform.setId(user.getId());
        userform.setAvatar(user.getAvatar());
        System.out.println(userform.toString());

        if (userform.getName() == null){
            userform.setName(user.getName());
        }
        if (userform.getEmail() == null){
            userform.setEmail(user.getEmail());
        }
        if (userform.getPassword() == null){
            userform.setPassword(user.getPassword());
        }
        if(result.hasErrors()) {
            redirectAttributes.addFlashAttribute("message", "更新失败");
            return "redirect:/profile";
        }
        if(file!= null && !file.isEmpty()) {
            String filename = userform.getId() + ".jpg";
            // 保存图片
            Files.copy(file.getInputStream(), Paths.get(ROOT, filename), StandardCopyOption.REPLACE_EXISTING);
            userform.setAvatar(Paths.get(ROOT, filename).toString());
        }
        userService.update(userform);
        session.setAttribute("CURRENT_USER", userform);
        redirectAttributes.addFlashAttribute("message", "更新成功");
        return "redirect:/profile";
    }

}