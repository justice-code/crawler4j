package edu.uci.ics.crawler4j.crawler.authentication;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;

import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MultiAuthInfo extends FormAuthInfo{
    private Map<String, String> kvs;

    public MultiAuthInfo(String loginUrl, Map<String, String> kvs) throws MalformedURLException {
        super(null, null, loginUrl, null, null);
        this.kvs = kvs;
    }

    @Override
    protected HttpEntity createEntity() {
        List<NameValuePair> formParams = new ArrayList<>();
        kvs.forEach((key, value) -> formParams.add(new BasicNameValuePair(key, value)));
        return new UrlEncodedFormEntity(formParams, StandardCharsets.UTF_8);
    }
}
