CREATE or replace VIEW earned_received AS select v.registration_num AS 
registration_num,(t.work_done * r.rate) AS amount,t.starttime AS eventtime,concat('earned - ',t.work_done,' x ',r.rate,
' ',r.rate_type,', ',substring_index(r.source,',',1),' to ',substring_index(r.destination,',',1)) AS type,t.challanid AS referenceid,
0 AS work,concat('E',t.tripid) AS erid,t.vehicleid AS vehicleid,r.consignerid AS consignerid,t.billingid AS billingid 
from ((trip t join route r on((t.routeid = r.routeid))) join vehicle v on((t.vehicleid = v.vehicleid))) union all 
select v2.registration_num AS registration_num,tx.amount AS amount,tx.txn_date AS txn_Date, concat('received-',tx.remarks) AS type,tx.txnid AS 
txnid,tx.txn_type AS txn_type,concat('T',tx.txnid) AS erid,tx.vehicleid AS vehicleid,tx.consignerid AS consignerid,tx.billingid 
AS billingid from (txn tx join vehicle v2 on((tx.vehicleid = v2.vehicleid))) union all select earned_received_hist.registration_num 
AS registration_num,earned_received_hist.amount AS amount,earned_received_hist.eventtime AS eventtime,earned_received_hist.type 
AS type,earned_received_hist.referenceid AS referenceid,earned_received_hist.work AS work,earned_received_hist.erid AS 
erid,earned_received_hist.vehicleid AS vehicleid,earned_received_hist.consignerid AS consignerid,earned_received_hist.billingid AS 
billingid from earned_received_hist;