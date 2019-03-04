package com.mtm.service.rest;

import com.mtm.service.rest.config.RestConfiguration;
import com.mtm.service.rest.resources.VehicleResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;

/**
 * Created by Admin on 2/24/2019.
 */
public class LauncherApplication extends Application<RestConfiguration> {

    public static void main(String[] args) throws Exception {
        new LauncherApplication().run(new String[] {"server", "config.yml"});
    }

    public void run(RestConfiguration configuration, Environment environment) {
        // code to register module

        final VehicleResource resource = new VehicleResource();
        environment.jersey().register(resource);
    }
}
