package cn.com.duiba.domain.dto;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * Created by liuyao on 2017/2/3.
 * 作者注册
 */
public class WriterDto implements Serializable {

    private static final long serialVersionUID = -6558978195205733210L;

    @NotBlank
    private String name;
    @NotBlank
    @Length(min=6)
    private String account;
    @NotBlank
    @Length(min=6)
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
