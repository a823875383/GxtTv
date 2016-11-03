package com.jsqix.gxt.tv.update;

import com.jsqix.gxt.tv.obj.BaseObj;

/**
 * Created by dongqing on 2016/11/1.
 */

public class SilentUpdateObj extends BaseObj {

    /**
     * downUrl : 3
     */

    private ObjBean obj;

    public ObjBean getObj() {
        return obj;
    }

    public void setObj(ObjBean obj) {
        this.obj = obj;
    }

    public static class ObjBean {
        private String downUrl;
        private String appVersion;
        private int forced;

        public String getDownUrl() {
            return downUrl;
        }

        public void setDownUrl(String downUrl) {
            this.downUrl = downUrl;
        }

        public String getAppVersion() {
            return appVersion;
        }

        public void setAppVersion(String appVersion) {
            this.appVersion = appVersion;
        }

        public int getForced() {
            return forced;
        }

        public void setForced(int forced) {
            this.forced = forced;
        }
    }
}
