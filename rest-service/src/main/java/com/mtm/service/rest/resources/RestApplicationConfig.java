package com.mtm.service.rest.resources;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;

import javax.ws.rs.ApplicationPath;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Admin on 11/16/2019.
 */
@ApplicationPath("/mtm")
public class RestApplicationConfig extends ResourceConfig {


        public RestApplicationConfig() {

            System.out.println("Configuring Rest Application");
            // Register resources and providers using package-scanning.
            packages("com.mtm.service.rest.resources");
            register(AuthorizationRequestFilter.class);

            // Register my custom provider - not needed if it's in my.package.
            //register(AuthorizationRequestFilter.class);
            // Register an instance of LoggingFilter.
            // register(new LoggingFilter(LOGGER, true));

            // Enable Tracing support.
            property(ServerProperties.TRACING, "ALL");

    }

   /* @Override
    public Map<String, Object> getProperties() {
        final Map<String, Object> properties = new HashMap<>();
        properties.put("jersey.config.server.provider.packages",
                "com.mtm.service.rest.resources");
        return properties;
    }*/
}
