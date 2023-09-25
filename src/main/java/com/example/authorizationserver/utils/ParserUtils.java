package com.example.authorizationserver.utils;

import jakarta.servlet.http.HttpServletRequest;

public class ParserUtils {

  public static  String getRealmNameFromUri(String uri){
      int realms = uri.indexOf("/", uri.indexOf("realms"));
     return uri.substring(realms+1,uri.indexOf('/',realms+1));
    }

    public static String extractClientId(HttpServletRequest request) {
        return request.getParameter("clientId");
    }

    public static String extractGrantType(HttpServletRequest request) {
        return request.getParameter("grant_type");
    }
}
