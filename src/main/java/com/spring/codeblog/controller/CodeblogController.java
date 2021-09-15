package com.spring.codeblog.controller;


import com.spring.codeblog.model.Post;
import com.spring.codeblog.repository.CodeblogRepository;
import com.spring.codeblog.service.CodeblogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@Controller
public class CodeblogController {

    @Autowired
    CodeblogService codeblogService;

    @RequestMapping(value = "/posts", method = RequestMethod.GET)
    public ModelAndView getPosts(){
        ModelAndView mv = new ModelAndView("posts");
        List<Post> posts = codeblogService.findAll();
        mv.addObject("posts", posts);
        return mv;
    }

    @RequestMapping(value = "/posts/{id}", method = RequestMethod.GET)
    public ModelAndView getPostsDetails(@PathVariable("id") long id) {
        ModelAndView mv = new ModelAndView("postDetails");
        Post post = codeblogService.findById(id);
        mv.addObject("post", post);
        return mv;
    }

    @RequestMapping(value = "/newpost", method = RequestMethod.GET)
    public String getPostsForm(){
        return "postForm";
    }

    @RequestMapping(value = "/newpost", method = RequestMethod.POST)
    public String savePost(@Valid Post post, BindingResult result, RedirectAttributes attributes){
        if(result.hasErrors()){
            attributes.addFlashAttribute("message", "Verifique se os campos obrigatórios foram preenchidos.");
            return "redirect:/newpost";
        }
        post.setData(LocalDate.now());
        codeblogService.save(post);
        return "redirect:/posts";
    }
//
//    @RequestMapping(value = "/posts/{id}", method = RequestMethod.DELETE)
//    public String deletePosts(long id){
//        codeblogService.delete(codeblogService.findById(id));
//        return "redirect:/posts";
//    }
}
