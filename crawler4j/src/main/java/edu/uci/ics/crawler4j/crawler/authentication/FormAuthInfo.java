package edu.uci.ics.crawler4j.crawler.authentication;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.text.html.FormSubmitEvent.MethodType;

/**
 * Created by Avi Hayun on 11/25/2014.
 *
 * FormAuthInfo contains the authentication information needed for FORM authentication (extending
 * AuthInfo which has
 * all common auth info in it)
 * Basically, this is the most common authentication, where you will get to a site and you will
 * need to enter a
 * username and password into an HTML form
 */
public class FormAuthInfo extends AuthInfo {
    protected static final Logger logger = LoggerFactory.getLogger(FormAuthInfo.class);

    private String usernameFormStr;
    private String passwordFormStr;

    /**
     * Constructor
     *
     * @param username Username to login with
     * @param password Password to login with
     * @param loginUrl Full login URL, starting with "http"... ending with the full URL
     * @param usernameFormStr "Name" attribute of the username form field
     * @param passwordFormStr "Name" attribute of the password form field
     *
     * @throws MalformedURLException Make sure your URL is valid
     */
    public FormAuthInfo(String username, String password, String loginUrl, String usernameFormStr,
                        String passwordFormStr) throws MalformedURLException {
        super(AuthenticationType.FORM_AUTHENTICATION, MethodType.POST, loginUrl, username,
              password);

        this.usernameFormStr = usernameFormStr;
        this.passwordFormStr = passwordFormStr;
    }

    public void login(HttpClient httpClient) {
        logger.info("FORM authentication for: {}", this.getLoginTarget());
        String fullUri =
                this.getProtocol() + "://" + this.getHost() + ":" + this.getPort() +
                        this.getLoginTarget();
        HttpPost httpPost = new HttpPost(fullUri);
        httpPost.setEntity(createEntity());

        try {
            httpClient.execute(httpPost);
            logger.debug("Successfully request to login in with user: {} to: {}", this.getUsername(),
                    this.getHost());
        } catch (ClientProtocolException e) {
            logger.error("While trying to login to: {} - Client protocol not supported",
                    this.getHost(), e);
        } catch (IOException e) {
            logger.error("While trying to login to: {} - Error making request", this.getHost(),
                    e);
        }
    }

    protected HttpEntity createEntity() {
        List<NameValuePair> formParams = new ArrayList<>();
        formParams.add(
                new BasicNameValuePair(this.getUsernameFormStr(), this.getUsername()));
        formParams.add(
                new BasicNameValuePair(this.getPasswordFormStr(), this.getPassword()));
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formParams, StandardCharsets.UTF_8);
        return entity;
    }

    /**
     * @return username html "name" form attribute
     */
    public String getUsernameFormStr() {
        return usernameFormStr;
    }

    /**
     * @param usernameFormStr username html "name" form attribute
     */
    public void setUsernameFormStr(String usernameFormStr) {
        this.usernameFormStr = usernameFormStr;
    }

    /**
     * @return password html "name" form attribute
     */
    public String getPasswordFormStr() {
        return passwordFormStr;
    }

    /**
     * @param passwordFormStr password html "name" form attribute
     */
    public void setPasswordFormStr(String passwordFormStr) {
        this.passwordFormStr = passwordFormStr;
    }
}