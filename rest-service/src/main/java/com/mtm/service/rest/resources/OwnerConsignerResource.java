package com.mtm.service.rest.resources;

import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Optional;
import com.mtm.beans.Status;
import com.mtm.beans.UserType;
import com.mtm.beans.dto.*;
import com.mtm.dao.*;
import io.dropwizard.jersey.PATCH;

import javax.ws.rs.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 3/13/2019.
 */
public class OwnerConsignerResource extends AbstractRestResource {

    private static Dao dao = new OwnerConsignerDao();
    public OwnerConsignerResource() {
        super(dao);
    }

    @POST
    @Path("/ownerconsigners")
    public Object addOwnerConsigner(OwnerConsigner ownerConsigner)

    {
        long ownerid = ownerConsigner.getOwnerid();
        long consignerid = ownerConsigner.getConsignerid();
        if(consignerid==0)
        {
            /*Create a provisional consigner record, the consignerid will be allocated automatically
            when consigner registers for a login
             */
            UserDao userDao = new UserDao();
            User user = new User();
            user.setName(ownerConsigner.getConsigner_name());
            user.setContact(ownerConsigner.getConsigner_contact());
            user.setUsertype(UserType.CONSIGNER.toString());
            user.setRegistered_by("OWNER");
            consignerid = userDao.addUser(user).getInsertedId()  ;
            ownerConsigner.setConsignerid(consignerid);
            //consigner.

        }
        String whereClause = " ownerid="+ownerid+" and consignerid = "+consignerid;
        if(get(whereClause).size()==0)
        return create(ownerConsigner);
        else {
            Status failedStatus = new Status();
            failedStatus.setReturnCode(1);
            failedStatus.setMessage("Owner consigner relationship already exists");
            return  failedStatus;

        }
    }

    @PATCH
    @Path("/ownerconsigners")
    public Object patchVehicleRecord(OwnerConsigner record)

    {
        if(patch(record)==1)
            return new Status("SUCCESS",0,0);
        else
            return new Status("FAILED",1,0);
    }

    @GET
    @Path("/ownerconsigners")
    public Object getRates()
    {
        return dao.getRecords("1=1");
    }

    @DELETE
    @Path("/ownerconsigners/{ownerid}/{consignerid}")
    @Timed
    public Object deleteRelation(@PathParam("consignerid") Optional<String> ownerid , @PathParam("ownerid") Optional<String> consignerid)
    {

        Status status = new Status();
        if(dao.delete(" ownerid = "+ownerid.get()+" and consignerid = "+consignerid.get())==1) {
            status.setMessage("SUCEESS");
            status.setReturnCode(0);

        }
        else
        {
            status.setReturnCode(1);
            status.setMessage("The consigner could not deleted because of active routes");
        }
        return status;

    }
    @GET
    @Path("/ownerconsigners/search")
    public List<OwnerConsigner> search(@QueryParam("where") Optional<String> whereClause) {

        List<OwnerConsigner> ownerConsigners = new ArrayList<>();
        String query = "select b.image_url, a.consignerid, a.consigner_name, a.is_company, a.consigner_contact, a.ownerid,a.billingday from (select * from owner_consigner where "+whereClause.get()+") a inner join consigner b on a.consignerid=b.consignerid";
        List<List<String>> records = dao.executeQuery(query);
        for(List<String> record : records)
        {
            OwnerConsigner ownerConsigner = new OwnerConsigner();
            ownerConsigner.setImage_url(record.get(0));
            ownerConsigner.setConsignerid(Long.parseLong(record.get(1)));
            ownerConsigner.setConsigner_name(record.get(2));
            ownerConsigner.setIs_company(record.get(3).charAt(0));
            ownerConsigner.setConsigner_contact(Long.parseLong(record.get(4)));
            ownerConsigner.setOwnerid(Long.parseLong(record.get(5)));
            ownerConsigner.setBillingday(Integer.parseInt(record.get(6)));
            ownerConsigners.add(ownerConsigner);


        }



        return ownerConsigners;

    }


    public List<OwnerConsigner> getPaginatedRecords(@QueryParam("where") Optional<String> whereClause, @QueryParam("min") Optional<String> min, @QueryParam("max") Optional<String> max, @QueryParam("recordsPerPage") Optional<String> recordsPerPage) {
        return null;
    }
}
