package com.itsupportme.gis.controller;

import com.itsupportme.gis.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class WorkspaceController {

    @RequestMapping(value = {"/", "/workspace"})
    public String getWorkspace(Model model) {

        User user = (User) model.asMap().get("User");
        Date date = new Date();

        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("EEE, d MMM yyyy HH:mm");
        String today = DATE_FORMAT.format(date);


        model.addAttribute("Today", today);

        return "global/workspace";
    }
}
