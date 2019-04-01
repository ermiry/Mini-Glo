package com.amazon.ask.MiniGlo.model;

import java.util.Arrays;
import java.util.List;

public class Constants {
    public static  final String CORRECT_SHOW = "The item was correctly showed";
    public static  final String INCORRECT_SHOW = "The item was not correctly showed";
    public static final String CORRECT_CREATION = "The item was correctly Created";
    public static final String INCORRECT_CREATION = "The item was not correctly Created" ;
    public static final String CONTINUE = "Do you want me to continue?";
    public static final String WELCOME_MESSAGE="Welcome to Mini-Glo!";
    public static final String HELP_MESSAGE="If you need help you can ask me:" +
            " Mini-Glo, Help, And I will tell you all the commands you need";
    public static final String START_EDIT = "You can tell me a complete full sequence of instructions without stop, if you want me to stop you just have to say, Mini-Glo Exit, or Cancel, or Stop, and i will stop";
    public static final String EXIT_SKILL_MESSAGE = "Thank you for using Mini-Glo, come Soon";
    public static final String JSON_NULL = "{status:404}";
    public static final String TOKEN = "token";

    public static final String[] EXPRESSION  =
    {"<say-as interpret-as='interjection'>", "</say-as><break strength='strong'/>"};

    public static final List<String> CORRECT_RESPONSES = Arrays.asList("Booya", "All righty","Bazinga", "Bingo", "Boom", "Cha Ching",
            "Hip hip hooray", "Hurrah", "Hurray",  "Oh dear.  Just kidding.  Hurray");
    public static final String CORRECT_EDIT = "The item was correctly edited. ";
    public static final String INCORRECT_EDIT = "The item wasnt correctly edited. ";

    public static List<String> INCORRECT_RESPONSES = Arrays.asList("Boo", "Bummer", "Darn", "D'oh", "Eek", "Honk",
             "Oh boy", "Oh dear", "Ouch", "Uh oh");
}
