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

    @RequestMapping(value="/CalendarProject/CalendarJsonServlet",  method= RequestMethod.GET)
    public  @ResponseBody void showCalendarDetails(HttpServletResponse response) throws IOException {


        List l = new ArrayList();

        Calendar c = new Calendar();
        c.setId(1);
        c.setStart("2017-04-23");
        c.setEnd("2017-04-27");
        c.setTitle("Task in Progress");

        Calendar d = new Calendar();
        c.setId(2);
        c.setStart("2017-04-15");
        c.setEnd("2017-04-16");
        c.setTitle("Task in Progress");

        l.add(c);
        l.add(d);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(new Gson().toJson(l));


/*

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", 111);
        map.put("title", "event1");
        map.put("start", "2011-07-28");
        map.put("url", "http://yahoo.com/");

        // Convert to JSON string.
        String json = new Gson().toJson(map);
        logger.info(json);

        // Write JSON string.
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try {
            response.getWriter().write(json);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
*/
    }
}
