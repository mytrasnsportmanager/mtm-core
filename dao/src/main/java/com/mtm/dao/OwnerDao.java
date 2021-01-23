package com.mtm.dao;


import com.mtm.beans.dto.Consigner;
import com.mtm.beans.dto.Owner;
import com.mtm.beans.dto.OwnerConsigner;
import com.mtm.beans.dto.Record;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 2/24/2019.
 */
public class OwnerDao extends AbstractDao {

    private static final String TABLE_NAME = "owner";
    private static final  Class RECORD_CLASS = Owner.class;
    public OwnerDao()
    {
        super(TABLE_NAME, RECORD_CLASS);
    }

    public List<Record> getConvertedRecords(String whereClause) {

        List<List<String>> records = get(whereClause);
        List<Record> ownerRecords = new ArrayList<>();
        Owner owner = new Owner();
        for(List<String> record: records)
        {
            owner.setOwnerid(Long.parseLong(record.get(0)));
            owner.setName(record.get(1));
            owner.setAddress(record.get(2));
            owner.setContact(Long.parseLong(record.get(3)));
            owner.setImage_url(record.get(4));
            ownerRecords.add(owner);

        }




        return ownerRecords;
    }

    public List<OwnerConsigner> getConsigners(String ownerId)
    {
        String query = "select consignerid, consigner_name, is_company, consigner_contact, image_url from\n" +
                "consigner where consignerid  in (select consignerid from owner_consigner where ownerid ="+ownerId+") ";

        List<List<String>> consignerRecords = executeQuery(query);
        List<OwnerConsigner> consigners = new ArrayList<OwnerConsigner>();
        for(List<String> record : consignerRecords)
        {
            OwnerConsigner consigner = new OwnerConsigner();
            consigner.setConsignerid(Long.parseLong(record.get(0)));
            consigner.setConsigner_name(record.get(1));
            consigner.setIs_company(record.get(2).charAt(0));
            consigner.setConsigner_contact(Long.parseLong(record.get(3)));
            consigner.setImage_url(record.get(4));
            consigners.add(consigner);

        }

        return consigners;


    }


}
