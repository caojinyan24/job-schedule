package swa.service.rpc;

import java.util.concurrent.ExecutionException;

/**
 * Created by jinyan on 10/12/17 3:17 PM.
 */
public interface RpcService {
    String remoteRequest(String param, String ipAddr) ;
}
