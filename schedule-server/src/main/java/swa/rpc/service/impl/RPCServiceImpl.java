package swa.rpc.service.impl;

import org.eclipse.jetty.server.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import swa.rpc.service.RpcService;

/**
 * Created by jinyan on 10/12/17 3:18 PM.
 */
@Service
public class RPCServiceImpl implements RpcService {
    private static final Logger logger = LoggerFactory.getLogger(RPCServiceImpl.class);
    Server server = new Server();

    public String remoteRequest(String param, String ipAddr) {
        return null;
    }
}
