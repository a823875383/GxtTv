package com.jsqix.gxt.tv.obj;

import java.util.List;

/**
 * Created by dongqing on 2016/10/21.
 */

public class HomeResult extends BaseObj {

    /**
     * model_type : 1
     * model_name :
     * model_subtitle :
     * model_icon :
     * model_link :
     */

    private List<ObjBean> obj;

    public List<ObjBean> getObj() {
        return obj;
    }

    public void setObj(List<ObjBean> obj) {
        this.obj = obj;
    }

    public static class ObjBean {
        private String model_type;
        private String model_name;
        private String model_subtitle;
        private String model_icon;
        private String model_link;

        public String getModel_type() {
            return model_type;
        }

        public void setModel_type(String model_type) {
            this.model_type = model_type;
        }

        public String getModel_name() {
            return model_name;
        }

        public void setModel_name(String model_name) {
            this.model_name = model_name;
        }

        public String getModel_subtitle() {
            return model_subtitle;
        }

        public void setModel_subtitle(String model_subtitle) {
            this.model_subtitle = model_subtitle;
        }

        public String getModel_icon() {
            return model_icon;
        }

        public void setModel_icon(String model_icon) {
            this.model_icon = model_icon;
        }

        public String getModel_link() {
            return model_link;
        }

        public void setModel_link(String model_link) {
            this.model_link = model_link;
        }
    }
}
