package cn.com.duiba.domain.param;

import cn.com.duiba.domain.dto.WorksJsonDto;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * Created by liuyao on 2017/2/3.
 */
public class WorksParams {
    private Long    writerId;
    @NotNull
    private Integer company;    // 公司编号
    @NotBlank
    private String  title;
    @NotBlank
    private String mainImage;
    @NotBlank
    private String defImage;

    public Long getWriterId() {
        return writerId;
    }

    public void setWriterId(Long writerId) {
        this.writerId = writerId;
    }

    public Integer getCompany() {
        return company;
    }

    public void setCompany(Integer company) {
        this.company = company;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMainImage() {
        return mainImage;
    }

    public void setMainImage(String mainImage) {
        this.mainImage = mainImage;
    }

    public String getDefImage() {
        return defImage;
    }

    public void setDefImage(String defImage) {
        this.defImage = defImage;
    }
}
