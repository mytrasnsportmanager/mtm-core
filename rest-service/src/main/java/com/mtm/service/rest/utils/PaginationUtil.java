package com.mtm.service.rest.utils;

import com.google.common.base.Joiner;
import com.mtm.dao.Dao;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 9/28/2019.
 */
public class PaginationUtil {

    private static  final char COMMA = ',';
    private static  final char SPACE = ' ';
    private static  final char EQUALS = '=';

    public static PageInfo getPageInfo(long minTripId, long maxTripId, int pageLimit, String baseCaseMaxRowidQuery, Dao dao)
    {
        long row_rank_max = 0;
        long row_rank_min= 0;
        PageInfo pageInfo = new PageInfo();

        if((minTripId==0 && maxTripId ==0 ))
        {
            List<List<String>> records = dao.executeQuery(baseCaseMaxRowidQuery.toString());
            if(records==null || records.size()==0 || records.get(0).get(0) == null) {
                pageInfo.setHasRecords(false);
                return pageInfo;
            }
            row_rank_max = Long.parseLong(records.get(0).get(0));
            row_rank_min = row_rank_max - pageLimit;

        }

        else if(minTripId==0) {
            //whereClauseToken.append(whereOrAnd + " d.row_rank > " + maxTripId);
            row_rank_min = maxTripId;
            row_rank_max = maxTripId + pageLimit;

        }
        else if(maxTripId==0) {
            // whereClauseToken.append(whereOrAnd + " d.row_rank < " + minTripId);
            row_rank_min = minTripId -pageLimit;
            row_rank_max = minTripId;
            // query.append(whereClauseToken+") a JOIN ( select 1 as common_group, tripid, vehicleid from trip_detailed ");
            // query.append(whereClauseToken+") b ON a.common_group = b.common_group and a.tripid <= b.tripid GROUP BY a.tripid ) d on c.tripid = d.tripid where d.row_rank <= "+row_rank_min+" and d.row_rank > "+row_rank_min +" order by c.starttime desc");
        }
        pageInfo.setMax(row_rank_max);
        pageInfo.setMin(row_rank_min);
        pageInfo.setHasRecords(true);
        return pageInfo;
    }

    public static String generateBoundaryQuery(String tableName ,  String tableIdColumn, String interestedEntityIdColumn, String dateOrderColumn, String whereClause )
    {
        if(whereClause==null)
            whereClause="";
        StringBuffer query = new StringBuffer("select max(d.row_rank) as rowid from ");
        query.append(tableName).append(" c");
        query.append(" inner join( SELECT ").append(" a.");
        query.append(tableIdColumn).append(COMMA);
        query.append(" a.common_group, count(*) as row_rank FROM ( select 1 as common_group, ");
        query.append(tableIdColumn).append(",");
        query.append(interestedEntityIdColumn).append(COMMA);
        query.append(dateOrderColumn).append(" from ");
        query.append(tableName).append(SPACE).append(whereClause).append(")").append(" a ");
        query.append(" JOIN ( select 1 as common_group, ");
        query.append(tableIdColumn).append(COMMA);
        query.append(interestedEntityIdColumn).append(COMMA);
        query.append(dateOrderColumn).append(" from ");
        query.append(tableName).append(SPACE).append(whereClause).append(")").append(" b ");
        query.append(" ON a.common_group = b.common_group and ");
        query.append("a.").append(dateOrderColumn);
        query.append(" >= ").append("b.").append(dateOrderColumn);
        query.append(" group by ").append("a.").append(tableIdColumn).append(")");
        query.append(" d ").append(" on ").append(" c.").append(tableIdColumn);
        query.append(" = ").append(" d.").append(tableIdColumn);
        query.append(" order by ").append(" c.").append(dateOrderColumn).append(" desc");

        return query.toString();

    }

    public static String generateDataQuery(PageInfo pageInfo, String tableName , List<String> selectColumns,  String tableIdColumn, String interestedEntityIdColumn, String dateOrderColumn, String whereClause )
    {
        StringBuffer dataQuery = new StringBuffer("select ");
        if(whereClause==null)
            whereClause="";
        List<String> aliasedSelectcColumns = new ArrayList<String>();
        for(String selectColumn : selectColumns)
        {
            aliasedSelectcColumns.add("c."+selectColumn);
        }

        String selectColumsPart = Joiner.on(",").join(aliasedSelectcColumns);

        dataQuery.append(selectColumsPart).append(COMMA).append(" d.row_rank").append(" from ").append(tableName).append(" c ");
        dataQuery.append(" inner join( SELECT  ").append(" a.").append(tableIdColumn).append(COMMA);
        dataQuery.append(" a.common_group").append(COMMA);
        dataQuery.append(" count(*) as row_rank from ");
        dataQuery.append(" ( select 1 as common_group, ");
        dataQuery.append(tableIdColumn).append(COMMA);
        dataQuery.append(interestedEntityIdColumn).append(COMMA);
        dataQuery.append(dateOrderColumn).append(" from ").append(tableName).append(SPACE).append(whereClause).append(" ) a ");
        dataQuery.append(" JOIN ");
        dataQuery.append(" ( select 1 as common_group, ");
        dataQuery.append(tableIdColumn).append(COMMA);
        dataQuery.append(interestedEntityIdColumn).append(COMMA);
        dataQuery.append(dateOrderColumn).append(" from ").append(tableName).append(SPACE).append(whereClause).append(" ) b ");
        dataQuery.append("  ON a.common_group = b.common_group and ");
        dataQuery.append(" a.").append(dateOrderColumn).append(" >= ").append(" b.").append(dateOrderColumn);
        dataQuery.append(" group by a.").append(tableIdColumn).append(" ) d");
        dataQuery.append(" on ").append("c.").append(tableIdColumn).append(EQUALS).append("d.").append(tableIdColumn);
        long min = pageInfo.getMin();
        long max = pageInfo.getMax();
        dataQuery.append(" where d.row_rank <=").append(max).append(" and d.row_rank > ").append(min);
        dataQuery.append(" order by ").append("c.").append(dateOrderColumn).append(" desc");

        return dataQuery.toString();

    }




    public static class PageInfo
    {
        public boolean hasRecords() {
            return hasRecords;
        }

        public void setHasRecords(boolean hasRecords) {
            this.hasRecords = hasRecords;
        }

        public long getMin() {
            return min;
        }

        public void setMin(long min) {
            this.min = min;
        }

        public long getMax() {
            return max;
        }

        public void setMax(long max) {
            this.max = max;
        }

        boolean hasRecords;
        long min;
        long max;
    }

}
