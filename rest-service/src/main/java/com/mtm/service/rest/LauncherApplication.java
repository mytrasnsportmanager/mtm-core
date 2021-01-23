package com.mtm.service.rest;

import com.mtm.beans.dto.Trip;
import com.mtm.dao.BillingDao;
import com.mtm.scheduled.BillingService;
import com.mtm.service.rest.auth.AbstractAuthorizer;
import com.mtm.service.rest.config.RestConfiguration;
import com.mtm.service.rest.resources.*;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
import org.glassfish.jersey.media.multipart.MultiPartFeature;

import javax.servlet.*;
import java.io.IOException;
import java.util.EnumSet;

/**
 * Created by Admin on 2/24/2019.
 */
public class LauncherApplication extends Application<RestConfiguration> {

    public static void main(String[] args) throws Exception {
        new LauncherApplication().run(new String[] {"server", "config.yml"});
    }

    public void run(RestConfiguration configuration, Environment environment) {
        // code to register module

        EnumSet<SessionTrackingMode> DEFAULT_TRACKING = EnumSet.of(SessionTrackingMode.COOKIE,SessionTrackingMode.URL);
        org.eclipse.jetty.server.session.SessionHandler sessionHandler = new org.eclipse.jetty.server.session.SessionHandler();
        environment.jersey().register(sessionHandler);
        sessionHandler.setMaxInactiveInterval(129600);
        sessionHandler.setSessionTrackingModes(DEFAULT_TRACKING);
        environment.servlets().setSessionHandler(sessionHandler);

        final VehicleResource resource = new VehicleResource();
        final OwnerResource ownerResource = new OwnerResource();
        final ConsignerResource consignerResource = new ConsignerResource();
        final RateResource rateResource = new RateResource();
        final RouteResource routeResource = new RouteResource();
      //  final TripResource tripResource = new TripResource();
        final TxnResource txnResource = new TxnResource();
        final RouteAndRateResource routeAndRateResource = new RouteAndRateResource();
        final OwnerConsignerResource ownerConsignerResource = new OwnerConsignerResource();
        final ImageResource imageResource = new ImageResource();
        final LoginResource loginResource = new LoginResource();
        final UserResource userResource = new UserResource();
        final VehicleDriverResource vehicleDriverResource = new VehicleDriverResource();
        final FileUploadService fileUploadService = new FileUploadService();
        final BillingResource billingResource = new BillingResource();
        final VehicleLocationResource vehicleLocationResource = new VehicleLocationResource();
        final AuthorizationRequestFilter authorizationRequestFilter = new AuthorizationRequestFilter();

        environment.jersey().register(authorizationRequestFilter);
        //environment.jersey().
        environment.jersey().register(AbstractRestResource.class);
       environment.jersey().register(VehicleResource.class);
       environment.jersey().register(ConsignerResource.class);
       environment.jersey().register(OwnerResource.class);
       environment.jersey().register(RateResource.class);
       environment.jersey().register(RouteResource.class);

       environment.jersey().register(TxnResource.class);
       environment.jersey().register(RouteAndRateResource.class);
       environment.jersey().register(OwnerConsignerResource.class);
       environment.jersey().register(ImageResource.class);

        environment.jersey().register(VehicleDriverResource.class);
       environment.jersey().register(MultiPartFeature.class);

       environment.jersey().register(BillingResource.class);
        environment.jersey().register(VehicleLocationResource.class);
        environment.jersey().register(AbstractAuthorizer.class);

        environment.jersey().register(LoginResource.class);
        environment.jersey().register(UserResource.class);
        environment.jersey().register(fileUploadService);
        environment.jersey().register(TripResource.class);
        environment.jersey().register(NotificationResource.class);
        environment.jersey().register(AccountantResource.class);
        environment.jersey().register(AndroidVersionResource.class);
        environment.jersey().register(OwnerExpenseResource.class);

        environment.jersey().register(DisputeResource.class);

        BillingService billingService = new BillingService();
        billingService.startAsync();


      // environment.servlets().addFilter("Custom-Filter", ResponseFilter.class)
      // .addMappingForUrlPatterns(java.util.EnumSet.allOf(javax.servlet.DispatcherType.class), true, "/*");


    }


   /* public static class ResponseFilter implements ContainerResponseFilter {

        public ResponseFilter()
        {

        }
        public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
            // your filtering task
            System.out.println("Hello there , filtering");
            //res.set
            //res.get
            chain.doFilter(req, res);
        }
        public void init()
        {

        }

        public void init(FilterConfig filterConfig) {
        }

        public void destroy() {
        }
    }*/
}
