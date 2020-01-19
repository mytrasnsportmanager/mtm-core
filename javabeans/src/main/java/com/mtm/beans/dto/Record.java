package com.mtm.beans.dto;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by Admin on 2/24/2019.
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces({MediaType.APPLICATION_JSON})
public class Record {

   protected List<Object> record;
   public boolean isColumnExcludedForPersistence(String columnName)
   {
      return false;
   }
   public String getPrimaryKeyColumn()
   {
      return null;
   }
}
