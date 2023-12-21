package com.dagbok.dagbok;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class DiaryPostsController {

    @Autowired
    private DiaryPostsRepository diaryPostsRepository;

    @GetMapping
    public String getStart(Model model) {
        model.addAttribute("posts", diaryPostsRepository.todaysDate());
        return "start";
    }

    @GetMapping("/addpost")
    public String addpost(Model model) {

        return "addpost";
    }

    @PostMapping("/new-post")
    public String newpost(@RequestParam("heading") String heading, @RequestParam("mainText") String mainText,
            @RequestParam("date") LocalDate date, RedirectAttributes r) {
        if (!heading.trim().isEmpty() || !mainText.trim().isEmpty()) {
            DiaryPosts diaryposts = new DiaryPosts();
            diaryposts.setHeading(heading);
            diaryposts.setMainText(mainText);
            diaryposts.setDate(date);
            diaryPostsRepository.save(diaryposts);
            r.addFlashAttribute("posted", "Inlägg publicerad!");

            return "redirect:/";
        }

        r.addFlashAttribute("errorMsgAdd", "Du kan inte skapa tomma inlägg, försök igen!");
        return "redirect:/addpost";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam int id, RedirectAttributes r) {

        r.addFlashAttribute("deleteMsg", "Inlägg raderad!");
        diaryPostsRepository.deleteById(id);
        return "redirect:/posts";
    }

    @GetMapping("/posts")
    public String getPosts(Model model) {

        model.addAttribute("posts", diaryPostsRepository.datenothappened()); // Här använder jag mig av en metod som
                                                                             // endast visar dagens samt äldre datums
                                                                             // inlägg
        return "posts";
    }

    @GetMapping("/post/{id}")
    String getPost(@PathVariable int id, Model model) {

        DiaryPosts diaryposts = diaryPostsRepository.findById(id).orElse(null);
        model.addAttribute("posts", diaryposts);

        return "post";
    }

    @GetMapping("/edit/{id}")
    String getEdit(@PathVariable int id, Model model) {

        DiaryPosts diaryposts = diaryPostsRepository.findById(id).orElse(null);
        model.addAttribute("post", diaryposts);

        return "edit";
    }

    @PostMapping("/edit")
    public String edit(@RequestParam("id") int id, @RequestParam("heading") String heading,
            @RequestParam("mainText") String mainText,
            @RequestParam("date") LocalDate date, RedirectAttributes r) {

        if (heading.trim().isEmpty() || mainText.trim().isEmpty()) {
            r.addFlashAttribute("editerrorMsg", "Du kan inte skapa tomma inlägg, försök igen!");
            return "redirect:/posts";
        }

        r.addFlashAttribute("editMsg", "Inlägg redigerad!");

        diaryPostsRepository.editPost(heading, mainText, date, id);

        return "redirect:/posts";
    }

    @GetMapping("/search")
    public String getSearch(Model model) {

        return "search";
    }

    @PostMapping("/search-date")
    public String search(@RequestParam("date1") LocalDate startDate, @RequestParam("date2") LocalDate endDate,
            Model model) {

        List<DiaryPosts> postsBetweenDates = diaryPostsRepository.datebetween(startDate, endDate);

        model.addAttribute("posts", postsBetweenDates);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate); // Här väljer jag att inkludera inlägg där publiceringsdatum ännu inte
                                                // har inträffat
        return "search";
    }
}