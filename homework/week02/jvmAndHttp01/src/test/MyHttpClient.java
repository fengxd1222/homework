package test;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;


import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * A client for http requests
 */
public class MyHttpClient {

    /**
     *  get 方法
     * @param url 请求连接
     * @param pairs 请求参数
     * @see Optional
     * @throws URISyntaxException
     */
    public static Optional get(String url, List<NameValuePair> pairs) throws URISyntaxException {
        CloseableHttpClient client = HttpClientBuilder.create().build();

        URIBuilder uriBuilder =  new URIBuilder(url);
        if(pairs!=null && !pairs.isEmpty()){
            uriBuilder.setParameters(pairs);
        }
        CloseableHttpResponse httpResponse = null;
        HttpGet httpGet = new HttpGet(uriBuilder.build());
        try {
            httpGet.setConfig(buildRequestConfig());
            httpResponse = client.execute(httpGet);
            if(httpResponse!=null){
                HttpEntity entity = httpResponse.getEntity();
                if(entity!=null){
                    return Optional.ofNullable(EntityUtils.toString(entity));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if (client != null) {
                    client.close();
                }
                if (httpResponse != null) {
                    httpResponse.close();
                }
                httpGet.releaseConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return Optional.empty();
    }

    /**
     * post 方法
     * @param url 请求链接
     * @param paramEntityJson 对象参数json字符串
     * @see Optional
     * @throws URISyntaxException
     */
    public static Optional post(String url,String paramEntityJson) throws URISyntaxException {
        CloseableHttpClient client = HttpClientBuilder.create().build();
        URI uri = new URI(url);
        CloseableHttpResponse httpResponse = null;
        HttpPost httpPost = new HttpPost(uri);
        try {
            StringEntity stringEntity = new StringEntity(paramEntityJson,"UTF-8");
            httpPost.setConfig(buildRequestConfig());
            httpPost.setEntity(stringEntity);

            httpResponse = client.execute(httpPost);
            if(httpResponse!=null){
                HttpEntity entity = httpResponse.getEntity();
                if(entity!=null){
                    return Optional.ofNullable(EntityUtils.toString(entity));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if (client != null) {
                    client.close();
                }
                if (httpResponse != null) {
                    httpResponse.close();
                }
                httpPost.releaseConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return Optional.empty();
    }

    private static RequestConfig buildRequestConfig() {
        return RequestConfig.custom()
                // 设置连接超时时间(单位毫秒)
                .setConnectTimeout(5000)
                // 设置请求超时时间(单位毫秒)
                .setConnectionRequestTimeout(5000)
                // socket读写超时时间(单位毫秒)
                .setSocketTimeout(5000)
                .setRedirectsEnabled(true).build();
    }

    public static void main(String[] args) {
        try {
            Optional optional = MyHttpClient.get("http://localhost:8801", null);
            if(optional.isPresent()) {
                System.out.println(optional.get());
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

    }
}
