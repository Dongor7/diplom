package com.itsupportme.gis.controller;

import com.itsupportme.gis.component.user.ProfileUpdater;
import com.itsupportme.gis.component.response.AjaxResponse;
import com.itsupportme.gis.component.response.ValidationFailedResponse;
import com.itsupportme.gis.entity.User;
import com.itsupportme.gis.entity.UserDetails;
import com.itsupportme.gis.entity.dao.UserDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

@Controller
@RequestMapping("/user/profile")
public class ProfileController {

    @Autowired
    UserDao userDao;

    @Autowired
    ProfileUpdater profileUpdater;

    @Autowired
    SessionFactory sessionFactory;

    @RequestMapping(value = "")
    public  String main() {

        return "global/profile_page";
    }

    @RequestMapping(value = "/profile_form",method = RequestMethod.GET)
    public String profileForm(Model model){

        User user = (User) model.asMap().get("User");

        model.addAttribute("ProfileForm", user);
        return "module/user_profile/profile_form";
    }

    @RequestMapping(value = "/profile_form", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse submitForm(@Valid User form, BindingResult bindingResult, Model model){

        if (bindingResult.hasErrors()) {
            return new ValidationFailedResponse(bindingResult.getAllErrors());
        }
        //get all data from Database
        User actualUser = userDao.findUser(form.getId());

        profileUpdater.update(form, actualUser);

        return new AjaxResponse();
    }

    @RequestMapping(value = "/interface_settings")
    public String showSettings(Model model) {

        User user = (User) model.asMap().get("User");

        UserDetails userDetails;
        if(user.getUserDetails() == null) {
             userDetails = new UserDetails();
        } else {
            userDetails = user.getUserDetails();
        }

        model.addAttribute("UserDetails", userDetails);

        return "module/user_profile/profile_settings";
    }

    @RequestMapping(value = "/interface_settings", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse submitSettings(UserDetails form, Model model){

        User user = (User) model.asMap().get("User");

        Session session = sessionFactory.getCurrentSession();

        if(user.getUserDetails() == null){
            form.setUser(user);
            session.save(form);
        } else {

            UserDetails actualDetails = user.getUserDetails();

            actualDetails.setImage(form.getImage());
            actualDetails.setTheme(form.getTheme());

            session.update(actualDetails);
        }

        session.flush();

        return new AjaxResponse();
    }
}
