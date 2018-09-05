package edu.uci.ics.crawler4j.crawler.authentication;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;

import javax.swing.text.html.FormSubmitEvent;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class AjaxAuthInfo extends FormAuthInfo {

    private String key;
    private String json;

    public AjaxAuthInfo(String loginUrl, String key, String json) throws MalformedURLException {
        super(null, null, loginUrl, null, null);
        this.key = key;
        this.json = json;
    }

    @Override
    protected HttpEntity createEntity() {
        List<NameValuePair> formParams = new ArrayList<>();
        formParams.add(
                new BasicNameValuePair(this.getKey(), this.getJson()));
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formParams, StandardCharsets.UTF_8);
        return entity;
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
