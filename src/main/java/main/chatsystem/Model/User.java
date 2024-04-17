package main.chatsystem.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public record User(@SerializedName("nickname") String nickname, @SerializedName("password") String password) implements Serializable {

    @Override public String nickname()
    {
        return nickname;
    }

    @Override public String password()
    {
        return password;
    }


    @Override
    public boolean equals(Object obj) {
        return false;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override public String toString()
    {
        return "User{" + "nickname='" + nickname + '\'' + ", password='" + password + '\'' + '}';
    }

}
