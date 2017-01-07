package com.example;

import java.util.Random;

public class Joke {
    String jokes[] = {" There are 10 types of people in the world: those who understand binary, and those who don't."
    ," Why is it that programmers always confuse Halloween with Christmas? \n" +
            "Because 31 OCT = 25 DEC"
            ,"Why do Java developers wear glasses? Because they can't C#"};

    public String getJoke(){
        return jokes[new Random().nextInt(jokes.length)];
    }
}
