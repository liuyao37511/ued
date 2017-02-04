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
    private String description;
    @NotBlank
    private String defimage;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDefimage() {
        return defimage;
    }

    public void setDefimage(String defimage) {
        this.defimage = defimage;
    }
}
