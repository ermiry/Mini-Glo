package com.amazon.ask.MiniGlo.model;

import java.util.Arrays;
import java.util.List;

public class Constants {
    public static  final String CORRECT_SHOW = "The item was correctly showed";
    public static  final String INCORRECT_SHOW = "The item was not correctly showed";
    public static final String CORRECT_CREATION = "The item was correctly Created";
    public static final String INCORRECT_CREATION = "The item was not correctly Created" ;
    public static final String CONTINUE = "Do you want me to continue?";
    public static String WELCOME_MESSAGE="Welcome to Mini-Glo!";
    public static String REGISTER_ACCOUNT = "If you have an account we can do greatfull things, " +
            "do you want to configure it now?";
    public static String HELP_MESSAGE="If you need help you can ask me, Mini-Glo, How to? And i will tell you all the commands you need";
    public static String START_EDIT = "You can tell me a complete full sequence of instructions without stop, if you want me to stop you just have to say, Mini-Glo Exit, or Cancel, or Stop, and i will stop";
    public static String EXIT_SKILL_MESSAGE = "Thank you for using Mini-Glo, come Soon";


    public static List<String> CORRECT_RESPONSES = Arrays.asList("Booya", "All righty", "Bam", "Bazinga", "Bingo", "Boom", "Bravo", "Cha Ching", "Cheers", "Dynomite",
            "Hip hip hooray", "Hurrah", "Hurray", "Huzzah", "Oh dear.  Just kidding.  Hurray", "Kaboom", "Kaching", "Oh snap", "Phew",
            "Righto", "Way to go", "Well done", "Whee", "Woo hoo", "Yay", "Wowza", "Yowsa");

    public static List<String> INCORRECT_RESPONSES = Arrays.asList("Argh", "Aw man", "Blarg", "Blast", "Boo", "Bummer", "Darn", "D'oh", "Dun dun dun", "Eek", "Honk", "Le sigh",
            "Mamma mia", "Oh boy", "Oh dear", "Oof", "Ouch", "Ruh roh", "Shucks", "Uh oh", "Wah wah", "Whoops a daisy", "Yikes");
}
