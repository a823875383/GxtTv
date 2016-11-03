package com.jsqix.gxt.tv.obj;

/**
 * Created by dongqing on 2016/10/21.
 */

public class LoginResult extends BaseObj {

    /**
     * device_id : 1
     * device_name :
     */

    private ObjBean obj;

    public ObjBean getObj() {
        return obj;
    }

    public void setObj(ObjBean obj) {
        this.obj = obj;
    }

    public static class ObjBean {
        private String device_id;
        private String device_name;
        private String device_status;
        private String device_phone;
        private String device_interval;

        public String getDevice_id() {
            return device_id;
        }

        public void setDevice_id(String device_id) {
            this.device_id = device_id;
        }

        public String getDevice_name() {
            return device_name;
        }

        public void setDevice_name(String device_name) {
            this.device_name = device_name;
        }

        public void setDevice_status(String device_status) {
            this.device_status = device_status;
        }

        public String getDevice_status() {
            return device_status;
        }

        public void setDevice_phone(String device_phone) {
            this.device_phone = device_phone;
        }

        public String getDevice_phone() {
            return device_phone;
        }

        public void setDevice_interval(String device_interval) {
            this.device_interval = device_interval;
        }

        public String getDevice_interval() {
            return device_interval;
        }
    }
}
