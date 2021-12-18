package pl.sda.xmasgifts.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.sda.xmasgifts.entity.Person;
import pl.sda.xmasgifts.entity.Wish;
import pl.sda.xmasgifts.service.XmasGiftsService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.UUID;

@Controller
public class XmasGiftController {

    public static final String XMAS_USER_ID = "xmas-user-id";
    //wstrzyknij JpaXmasGiftsService
    private final XmasGiftsService xmasGiftsService;

    public XmasGiftController(XmasGiftsService xmasGiftsService) {
        this.xmasGiftsService = xmasGiftsService;
    }

    @GetMapping("/")
    public String home() {
        //
        return "index";
    }

    @GetMapping("/user/add")
    public String addUserForm() {
        return "add-user-form";
    }

    @PostMapping("/user/add")
    public ModelAndView addUser(@ModelAttribute Person person, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("add-user-confirm");
        final Person user = xmasGiftsService.addPerson(person);
        modelAndView.getModelMap().addAttribute("person", user);
        response.addCookie(createXmasUserIdCookie(user.getId()));
        return modelAndView;
    }

    @GetMapping("/user/list")
    public String listUsers(Model model) {
        model.addAttribute("people", xmasGiftsService.findAllPersons());
        return "users-list";
    }

    @GetMapping("/wish/add")
    public String addWishForm() {
        return "add-wish-form";
    }

    @PostMapping("/wish/add")
    public String addWish(@ModelAttribute Wish wish, @CookieValue(XMAS_USER_ID) String userUUID) {
        final Optional<Wish> optionalWish = xmasGiftsService.addPersonWish(wish, UUID.fromString(userUUID));
        if (optionalWish.isPresent()) {
            return "redirect:/user/list";
        } else {
            return "add-wish-error";
        }
    }

    private Cookie createXmasUserIdCookie(UUID userUUID){
        Cookie cookie = new Cookie(XMAS_USER_ID, userUUID.toString());
        cookie.setMaxAge(1000_000_000);
        cookie.setPath("/");
        return cookie;
    }
}
