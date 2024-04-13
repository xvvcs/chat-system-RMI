package main.chatsystem.Model;

import java.io.Serializable;

public record Message(String message, User user) implements Serializable
{


    @Override
    public String message() {
        return message;
    }

    @Override public String toString()
    {
        return message;
    }
}
