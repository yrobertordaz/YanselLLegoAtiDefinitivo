package com.llegoati.llegoati.infrastructure.concrete.utils.webservice.rest;


//import org.apache.http.HttpEntity;
//import org.apache.http.HttpResponse;
//import org.apache.http.client.ClientProtocolException;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.ResponseHandler;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.entity.StringEntity;
//import org.apache.http.impl.client.BasicResponseHandler;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.protocol.HTTP;
//import org.apache.http.util.EntityUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by krrigan on 11/26/15.
 */
public class RESTRequest {
    private JSONObject data;
    private JSONObject headers;
    private String url;

    public RESTRequest(String url) {
        this.url = url;
        data = new JSONObject();
        headers = new JSONObject();
    }


    public void addHeader(String key, String value) {
        try {
            headers.put(key, value);
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public void addParam(String key, String value) {
        try {
            data.put(key, value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void addJsonParamsAsString(String json) {
        try {
            data = new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void addJsonParamsAsJSONObject(JSONObject json) {
        data = json;
    }
//
//    public String executePost()
//    {  // If you want to use post method to hit server

//        HttpURLConnection httpClient = new DefaultHttpUrlConnection();
//        HttpPost httpPost = new HttpPost(url);
//
//        if(headers.length() > 0)
//        {
//            try
//            {
//                Iterator<String> keys = headers.keys();
//
//                while(keys.hasNext())
//                {
//                    String key = keys.next();
//                    httpPost.addHeader(key, headers.getString(key));
//                }
//            }
//            catch(Exception e)
//            {
//                e.getMessage();
//            }
//        }
//
//        HttpResponse response = null;
//        String result = null;
//        try
//        {
//            StringEntity entity = new StringEntity(data.toString(), HTTP.UTF_8);
//            httpPost.setEntity(entity);
//            response = httpClient.execute(httpPost);
//            HttpEntity entity1 = response.getEntity();
//            result = EntityUtils.toString(entity1);
//            return result;
//            //Toast.makeText(MainPage.this, result, Toast.LENGTH_LONG).show();
//        } catch(UnsupportedEncodingException e)
//        {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch(ClientProtocolException e)
//        {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch(IOException e)
//        {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        return result;
//
//
//    }
//
//    public String executeGet()
//    { //If you want to use get method to hit server
//
//        HttpClient httpClient = new DefaultHttpClient();
//
//        if(data.length() > 0)
//        {
//            url += "?";
//            try
//            {
//                Iterator<String> keys = data.keys();
//                while(keys.hasNext())
//                {
//                    String key = keys.next();
//                    url += String.format("%s=%s", key, data.getString(key));
//
//                    if(keys.hasNext())
//                        url += "&";
//                }
//            }
//            catch(Exception e)
//            {
//                e.getMessage();
//            }
//        }
//
//        HttpGet httpget = new HttpGet(url);
//
//        if(headers.length() > 0)
//        {
//            try
//            {
//                Iterator<String> keys = headers.keys();
//
//                while(keys.hasNext())
//                {
//                    String key = keys.next();
//                    httpget.addHeader(key, headers.getString(key));
//                }
//            }
//            catch(Exception e)
//            {
//                e.getMessage();
//            }
//        }
//
//        String result = null;
//        ResponseHandler<String> responseHandler = new BasicResponseHandler();
//        try
//        {
//            result = httpClient.execute(httpget, responseHandler);
//        } catch(ClientProtocolException e)
//        {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch(IOException e)
//        {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//
//        return result;
//    }
//
//
//    private static String METHOD_GET = "GET", METHOD_POST = "POST";
//
//    public static InputStream post(String url_path)
//    {
//        try
//        {
//            return consume(url_path, METHOD_POST);
//        } catch (Exception e)
//        {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    public static InputStream get(String url_path)
//    {
//        try
//        {
//            return consume(url_path, METHOD_GET);
//        } catch (Exception e)
//        {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    public static InputStream get(String url_path, File downloaded_file)
//    {
//        try
//        {
//            return consume(url_path, METHOD_GET, downloaded_file.length());
//        } catch (Exception e)
//        {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    public static InputStream consume(String url_path, String method)
//    {
//        try
//        {
//            if(url_path.toLowerCase().startsWith("https"))
//                _FakeX509TrustManager.allowAllSSL();
//
//            URL url = new URL(url_path);
//            HttpURLConnection http_url_con = (HttpURLConnection) url.openConnection();
//            http_url_con.setRequestMethod(method);
//            http_url_con.connect();
//            return  http_url_con.getInputStream();
//        } catch (Exception e)
//        {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    public static InputStream consume(String url_path, String method, long size)
//    {
//        try
//        {
//            if(url_path.toLowerCase().startsWith("https"))
//                _FakeX509TrustManager.allowAllSSL();
//
//            URL url = new URL(url_path);
//            HttpURLConnection http_url_con = (HttpURLConnection) url.openConnection();
//            http_url_con.setRequestMethod(method);
//            http_url_con.setRequestProperty("Range", "bytes="+ size +"-");
//            http_url_con.connect();
//            return  http_url_con.getInputStream();
//        } catch (Exception e)
//        {
//            e.printStackTrace();
//        }
//        return null;
//    }

}
