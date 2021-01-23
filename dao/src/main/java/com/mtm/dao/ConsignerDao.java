package com.mtm.dao;

import com.mtm.beans.dto.Consigner;
import com.mtm.beans.dto.Owner;
import com.mtm.beans.dto.OwnerConsigner;
import com.mtm.beans.dto.Record;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 3/4/2019.
 */
public class ConsignerDao extends AbstractDao {
    private static final String TABLE_NAME = "consigner";
    private static final  Class RECORD_CLASS = Consigner.class;

    public ConsignerDao()
    {
        super(TABLE_NAME, RECORD_CLASS);
    }
    public List<Record> getConvertedRecords(String whereClause) {

        List<List<String>> records = get(whereClause);
        List<Record> consignerRecords = new ArrayList<>();
        Consigner consigner = new Consigner();
        for(List<String> record: records)
        {
            consigner.setConsignerid(Long.parseLong(record.get(0)));
            consigner.setName(record.get(1));
            consigner.setContact(Long.parseLong(record.get(2)));
            consigner.setAddress(record.get(3));

            consigner.setImage_url(record.get(4));
            consignerRecords.add(consigner);

        }




        return consignerRecords;

    }
}
