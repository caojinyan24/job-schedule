package swa.service.rpc.impl;

import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.AsyncHttpClientConfig;
import com.ning.http.client.ListenableFuture;
import com.ning.http.client.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.net.www.http.HttpClient;
import swa.service.rpc.JobScheduleHandler;
import swa.service.rpc.RpcService;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * Created by jinyan.cao on 2017/10/19 20:06.
 */
public class RpcServiceImpl implements RpcService {
    private static final Logger logger = LoggerFactory.getLogger(RpcServiceImpl.class);

    private static AsyncHttpClient client;

    public String remoteRequest(String param, String url) {
        AsyncHttpClient.BoundRequestBuilder requestBuilder = client.preparePost(url);
        requestBuilder.addQueryParam("param", param);
        ListenableFuture<Response> responseListenableFuture = client.executeRequest(requestBuilder.build());
        try {
            if (responseListenableFuture.get() != null) {
                return responseListenableFuture.get().getResponseBody();
            }
        } catch (Exception e) {
            logger.error("remoteRequest-error:", e);
        }
        return null;
    }

//    static {
//        AsyncHttpClientConfig.Builder builder = new AsyncHttpClientConfig.Builder();
//        builder.setAllowPoolingConnections(true);
//        builder.setConnectTimeout(10000);
//        builder.setMaxConnections(2000);
//        builder.setMaxConnectionsPerHost(1000);
//        client = new AsyncHttpClient(builder.build());
//    }
    public static void main(String[]args){
        AsyncHttpClientConfig.Builder builder = new AsyncHttpClientConfig.Builder();
        builder.setAllowPoolingConnections(true);
        builder.setConnectTimeout(10000);
        builder.setMaxConnections(2000);
        builder.setMaxConnectionsPerHost(1000);
        client = new AsyncHttpClient(builder.build());


        RpcService rpcService=new RpcServiceImpl();
        rpcService.remoteRequest("abc","http://localhost:8080");
    }
}
