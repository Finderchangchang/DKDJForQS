package liuliu.qs.model;

/**
 * Created by Administrator on 2016/11/29.
 */

public class TagModel {
    String tag;
    String val;
    boolean click;

    public TagModel(String tag, String val, boolean click) {
        this.tag = tag;
        this.val = val;
        this.click = click;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    public boolean isClick() {
        return click;
    }

    public void setClick(boolean click) {
        this.click = click;
    }
}
