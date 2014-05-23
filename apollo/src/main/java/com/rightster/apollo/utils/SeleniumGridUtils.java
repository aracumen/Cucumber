package com.rightster.apollo.utils;

import java.net.URL;

import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHttpEntityEnclosingRequest;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.openqa.selenium.remote.SessionId;

/**
 * Description - Class to return the node IP where the test was executed.
 * 
 * @author Pradeepta Swain
 */
public class SeleniumGridUtils
{
    public static String getActualTestNode(SessionId driverSession, String hubIp, int hubPort)
    {
        String proxyId = null;

        try
        {
            org.apache.http.HttpHost host = new org.apache.http.HttpHost(hubIp, hubPort);
            DefaultHttpClient client = new DefaultHttpClient();

            URL testSessionApi = new URL("http://" + hubIp + ":" + hubPort + "/grid/api/testsession?session="
                    + driverSession);

            BasicHttpEntityEnclosingRequest r = new BasicHttpEntityEnclosingRequest("POST",
                    testSessionApi.toExternalForm());

            org.apache.http.message.BasicHttpResponse response = (BasicHttpResponse) client.execute(host, r);
            JSONObject object = new JSONObject(EntityUtils.toString(((BasicHttpResponse) response).getEntity()));

            proxyId = (String) object.get("proxyId");
            proxyId = proxyId.substring(7, proxyId.length()).split(":")[0];

        } catch (Exception ex)
        {
            proxyId = "?";
        }

        return proxyId;
    }
}
