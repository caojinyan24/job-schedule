package swa.service.rpc.impl;

import org.eclipse.jetty.server.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import swa.service.rpc.JobScheduleHandler;
import swa.service.rpc.RpcService;

/**
 * Created by jinyan on 10/16/17 9:36 PM.
 */
public class JettyServiceImpl implements RpcService {
    private static final Logger logger = LoggerFactory.getLogger(JettyServiceImpl.class);

    static {
        Server server = new Server(8090);//// TODO: 10/16/17 从xml配置文件中解析
        server.setHandler(new JobScheduleHandler());
        try {
            server.join();
            server.start();
        } catch (Exception e) {
            logger.error("Jetty 服务启动失败", e);
        }
    }

    public String remoteRequest(String param, String ipAddr) {
        return null;
    }
}
