package pl.sda.xmasgifts.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.sda.xmasgifts.entity.Person;
import pl.sda.xmasgifts.service.XmasGiftsService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.net.http.HttpResponse;

@Controller
public class XmasGiftController {

    //wstrzyknij JpaXmasGiftsService
    private final XmasGiftsService xmasGiftsService;

    public XmasGiftController(XmasGiftsService xmasGiftsService) {
        this.xmasGiftsService = xmasGiftsService;
    }

    @GetMapping("/")
    public String home(){
        //
        return "index";
    }

    @GetMapping("/user/add")
    public String addUserForm(){
        return "add-user-form";
    }

    @PostMapping("/user/add")
    public ModelAndView addUser(@ModelAttribute Person person, HttpServletResponse response){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("add-user-confirm");
        final Person user = xmasGiftsService.addPerson(person);
        modelAndView.getModelMap().addAttribute("person", user);
        Cookie cookie = new Cookie("xmas-user-id", user.getId().toString());
        response.addCookie(cookie);
        return modelAndView;
    }

    @GetMapping("/user/list")
    public String listUsers(Model model){
        model.addAttribute("people", xmasGiftsService.findAllPersons());
        return "users-list";
    }
}
