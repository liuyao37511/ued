package cn.com.duiba.domain.dto;

import java.io.Serializable;

/**
 * Created by liuyao on 2017/2/1.
 */
public class WorksJsonDto implements Serializable {

    private static final long serialVersionUID = -5644913960580336310L;
    private String mainImage;
    private String description;
    private String defimage;

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
