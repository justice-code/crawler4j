package edu.uci.ics.crawler4j.crawler.authentication;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;

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
                new BasicNameValuePair(this.key, this.json));
        return new UrlEncodedFormEntity(formParams, StandardCharsets.UTF_8);
    }

}
