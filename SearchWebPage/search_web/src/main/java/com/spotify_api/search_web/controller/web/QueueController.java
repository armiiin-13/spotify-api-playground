package com.spotify_api.search_web.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.spotify_api.search_web.service.QueueService;

@Controller
public class QueueController {
    @Autowired
    private QueueService queue;

    @GetMapping("/queue")
    public String showPlayer(Model model) {
        if (!model.containsAttribute("track")) {
            model.addAttribute("track", null);
        }
        return "player";
    } 

    @GetMapping("/queue/next")
    public String next(RedirectAttributes redirect) {
        redirect.addFlashAttribute("track", this.queue.dequeue());
        return "redirect:/queue";
    } 
}
