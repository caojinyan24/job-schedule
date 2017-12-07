package swa.rpc.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import swa.rpc.Server;
import swa.rpc.service.RpcService;

import javax.annotation.PostConstruct;

/**
 * Created by jinyan on 10/12/17 3:18 PM.
 */
public class RPCServiceImpl implements RpcService {
    private static final Logger logger = LoggerFactory.getLogger(RPCServiceImpl.class);

    @PostConstruct
    public void setUp() {
        Thread thread = new Thread(new Runnable() {
            public void run() {
                try {
                    new Server(8087).start();
                    logger.info("server start2");

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                logger.info("server start1");
            }

        });
        thread.start();
        logger.info("server start3");
    }

}
