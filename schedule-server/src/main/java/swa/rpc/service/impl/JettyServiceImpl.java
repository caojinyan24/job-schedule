package swa.rpc.service.impl;

import org.eclipse.jetty.server.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import swa.Constants;
import swa.rpc.service.JettyService;
import swa.rpc.service.JobRegisterHandler;

import javax.annotation.PostConstruct;

/**
 * Created by jinyan on 10/12/17 4:01 PM.
 */
@Service
public class JettyServiceImpl implements JettyService {
    private static final Logger logger = LoggerFactory.getLogger(JettyServiceImpl.class);

    @PostConstruct
    public void setUp() throws Exception {
        Server server = new Server(Constants.LOCAL_PORT);
        server.setHandler(new JobRegisterHandler());
        server.join();
        server.start();
    }



}
