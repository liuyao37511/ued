package cn.com.duiba.domain.enumeration;

/**
 * Created by liuyao on 2017/2/1.
 */
public enum CompanyEnum {
    TUI_A(1,"推啊"),
    MAI_LA(2,"麦啦");

    private Integer code;
    private String name;

    private CompanyEnum(Integer code,String name){
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
