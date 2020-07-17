package com.mtm.service.rest.resources;

import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Optional;
import com.mtm.beans.dto.AndroidAppVersion;
import com.mtm.dao.AndroidVersionDao;
import com.mtm.dao.Dao;
import com.mtm.dao.VehicleLocationDao;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.util.List;

/**
 * Created by Admin on 7/9/2020.
 */
public class AndroidVersionResource extends AbstractRestResource {

    private static Dao dao = new AndroidVersionDao();
    public AndroidVersionResource() {
        super(dao);
    }

    public AndroidVersionResource(Dao dao) {
        super(dao);
    }

    @GET
    @Path("/version")
    @Timed
    public Object getVersion() {

      List<List<String>> results =   dao.executeQuery("select * from android_app_version");
      List<String> result = results.get(0);
      AndroidAppVersion androidAppVersion = new AndroidAppVersion();
      androidAppVersion.setVersion(result.get(0));
      androidAppVersion.setCode(Integer.parseInt(result.get(1)));
      return androidAppVersion;

    }

}
