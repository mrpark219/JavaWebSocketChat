package me.park.javawebsocketchat.login;

import lombok.extern.slf4j.Slf4j;
import me.park.javawebsocketchat.model.Message;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@Slf4j
public class LoginController {

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(HttpServletRequest request, HttpServletResponse response) {
        return "login";
    }

    @RequestMapping(value = "/loginProcess", method = RequestMethod.POST)
    public String loginProcess(@RequestParam String nickname, HttpServletRequest request, HttpServletResponse response, Model model) {

        if(nickname == null || nickname.equals("")) {
            model.addAttribute("message", new Message("닉네임은 한 글자 이상 입력해주세요.", "/logout"));
            return "message";
        }

        String ip = (null != request.getHeader("X-FORWARDED-FOR")) ? request.getHeader("X-FORWARDED-FOR") : request.getRemoteAddr();
        log.info("\"" + nickname + "\"(" + ip + ") Enter");

        HttpSession session = request.getSession();
        session.setAttribute("nickname", nickname);
        session.setAttribute("ip", ip);

        return "redirect:/chat";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = request.getSession();

        log.info("\"" + session.getAttribute("nickname") + "\" Leave");

        session.removeAttribute("nickname");

        return "redirect:/login";
    }

}
