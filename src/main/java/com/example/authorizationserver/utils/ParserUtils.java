package com.example.authorizationserver.utils;

public class ParserUtils {

  public static  String getRealmNameFromUri(String uri){
        int beginIndex = uri.indexOf("/",uri.indexOf("realms"));
        return uri.substring(beginIndex,uri.indexOf("/",beginIndex));
    }
}
