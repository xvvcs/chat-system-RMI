package main.chatsystem.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Objects;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(nickname, user.nickname) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nickname, password);
    }

    @Override public String toString()
    {
        return "User{" + "nickname='" + nickname + '\'' + ", password='" + password + '\'' + '}';
    }

}
