package edu.uci.ics.crawler4j.crawler.authentication;

import javax.swing.text.html.FormSubmitEvent;
import java.net.MalformedURLException;

public class AjaxAuthInfo extends AuthInfo {

    private String key;
    private String json;

    public AjaxAuthInfo(String loginUrl, String key, String json) throws MalformedURLException {
        super(AuthenticationType.AJAX_AUTHENTICATION, FormSubmitEvent.MethodType.POST, loginUrl, null, null);
        this.key = key;
        this.json = json;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }
}
