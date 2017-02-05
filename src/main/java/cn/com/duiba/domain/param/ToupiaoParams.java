package cn.com.duiba.domain.param;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * Created by liuyao on 2017/2/5.
 */
public class ToupiaoParams {
    @NotBlank
    private String token;
    @NotNull
    private Long worksId;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getWorksId() {
        return worksId;
    }

    public void setWorksId(Long worksId) {
        this.worksId = worksId;
    }
}
