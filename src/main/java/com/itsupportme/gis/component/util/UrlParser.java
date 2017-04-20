package com.itsupportme.gis.component.util;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

public class UrlParser {

    public static String getRootUrl(HttpServletRequest request) {

        String rootUrl     = request.getScheme() + "://" + request.getServerName();
        Integer portNumber = request.getServerPort();
        if (!(Objects.equals(portNumber, 80) || Objects.equals(portNumber, 443))) {
            rootUrl = rootUrl + ":" + Integer.toString(portNumber);
        }
        rootUrl = rootUrl + request.getContextPath();

        return rootUrl;
    }

    public static Map<String, List<String>> splitQuery(String url) throws UnsupportedEncodingException {

        final Map<String, List<String>> queryPairs = new LinkedHashMap<>();
        final String[] pairs                        = url.split("&");
        for (String pair : pairs) {
            final int idx    = pair.indexOf("=");
            final String key = idx > 0 ? URLDecoder.decode(pair.substring(0, idx), "UTF-8") : pair;

            if (!queryPairs.containsKey(key)) {
                queryPairs.put(key, new LinkedList<>());
            }

            final String value = idx > 0 && pair.length() > idx + 1 ?
                    URLDecoder.decode(pair.substring(idx + 1), "UTF-8") : null;

            queryPairs.get(key).add(value);
        }
        return queryPairs;
    }
}
