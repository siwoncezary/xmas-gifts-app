package pl.sda.xmasgifts.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.sda.xmasgifts.entity.Person;
import pl.sda.xmasgifts.service.XmasGiftsService;

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
    public ModelAndView addUser(@ModelAttribute Person person){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("add-user-confirm");
        modelAndView.getModelMap().addAttribute("person",xmasGiftsService.addPerson(person));
        return modelAndView;
    }
}
