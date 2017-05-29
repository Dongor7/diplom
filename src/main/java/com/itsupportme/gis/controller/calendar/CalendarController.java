package com.itsupportme.gis.controller.calendar;

import com.google.gson.Gson;
import com.itsupportme.gis.entity.Calendar;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CalendarController {

    @RequestMapping(value = "/calendar", method = RequestMethod.GET)
    public String main() {

        return "global/calendar";
    }

    @RequestMapping(value="/calendar/CalendarJsonEvent",  method= RequestMethod.GET)
    public  @ResponseBody void showCalendarDetails(HttpServletResponse response) throws IOException {

        List l = new ArrayList();

        Calendar c = new Calendar();
        c.setId(0);
        c.setStart("2017-04-23");
        c.setEnd("2017-04-27");
        c.setTitle("Task in Progress");

        Calendar d = new Calendar();
        c.setId(1);
        c.setStart("2017-04-15");
        c.setEnd("2017-04-17");
        c.setTitle("Task in Progress");

        l.add(c);
        l.add(d);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(new Gson().toJson(l));

    }
}
