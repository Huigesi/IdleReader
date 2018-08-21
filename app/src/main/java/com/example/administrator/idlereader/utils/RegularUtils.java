package com.example.administrator.idlereader.utils;

import java.util.regex.Pattern;

public class RegularUtils {
    // #话题#
    public static final String REGEX_TOPIC = "#[\\p{Print}\\p{InCJKUnifiedIdeographs}&&[^#]]+#";
    // [表情]
    public static final String REGEX_EMOTION = "\\[(\\S+?)\\]";
    // url
    public static final String REGEX_URL = "http://[a-zA-Z0-9+&@#/%?=~_\\\\-|!:,\\\\.;]*[a-zA-Z0-9+&@#/%=~_|]";
    // @人
    public static final String REGEX_AT = "@[\\w\\p{InCJKUnifiedIdeographs}-]{1,26}";
    /**微博短链接正则表达式*/
    static final String regex_http = "http(s)?://([a-zA-Z|\\d]+\\.)+[a-zA-Z|\\d]+(/[a-zA-Z|\\d|\\-|\\+|_./?%=]*)?";
    // @人
    static final String regex_at = "@[\\u4e00-\\u9fa5\\w\\-]+";
    static final String regex_sharp="#([^\\#|.]+)#";
    static final String regex_emoji="\\[[a-zA-Z0-9\\u4e00-\\u9fa5]+\\]";
    public static final Pattern PATTERN_TOPIC = Pattern.compile(REGEX_TOPIC);
    public static final Pattern PATTERN_EMOTION = Pattern.compile(REGEX_EMOTION);
    public static final Pattern PATTERN_URL = Pattern.compile(REGEX_URL);
    public static final Pattern PATTERN_AT = Pattern.compile(REGEX_AT);

    public static final String SCHEME_TOPIC = "topic:";
    public static final String SCHEME_URL = "url:";
    public static final String SCHEME_AT = "at:";
}
