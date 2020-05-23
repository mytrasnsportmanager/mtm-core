package com.mtm.pdfgenerator;
import com.mtm.beans.dto.CreditDebit;
import com.mtm.beans.dto.OwnerLevelVehiclesWork;
import com.mtm.beans.dto.VehicleWork;
import com.mtm.dao.BillingDao;
import org.apache.batik.ext.awt.image.rendered.CachableRed;
import org.apache.commons.lang3.StringUtils;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.MimeConstants;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Admin on 7/16/2019.
 */
public class PDFGeneratorUtil {

    //private final static String PDF_GENERATION_TEMP_PATH = "/home/mtmuser/proj/deployed/mtm/resources/pdfgeneration/pdfs/";
    //private final static String CHALLAN_GENERATION_TEMP_PATH = "/home/mtmuser/proj/deployed/mtm/resources/pdfgeneration/pdfs/";
    private final static String XSLT_FILE_PATH = "/home/mtmuser/proj/deployed/mtm/resources/pdfgeneration/table.xml";
   // private final static String XSLT_OWNER_FILE_PATH = "/home/mtmuser/proj/deployed/mtm/resources/pdfgeneration/ownertable.xml";
    //private final static String XSLT_CHALLAN_FILE_PATH = "/home/mtmuser/proj/deployed/mtm/resources/pdfgeneration/challan.xml";
   // private final static String FOP_CONF_PATH = "/home/mtmuser/proj/deployed/mtm/resources/pdfgeneration/fop.xconf";

   //  For local execution
   private final static String PDF_GENERATION_TEMP_PATH = "C:/prj/pdfs";
   private final static String XSLT_OWNER_FILE_PATH = "C:/Users/Admin/IdeaProjects/mtm-core/pdfgenerator/src/main/resources/ownertable.xml";
   private final static String CHALLAN_GENERATION_TEMP_PATH = "C:/prj/mtm";
    private final static String XSLT_CHALLAN_FILE_PATH = "C:/Users/Admin/IdeaProjects/mtm-core/pdfgenerator/src/main/resources/challan.xml";
    private final static String FOP_CONF_PATH = "C:/Users/Admin/IdeaProjects/mtm-core/pdfgenerator/src/main/resources/fop.xconf";
    private final static String CHALLAN_IMAGE_LOCATION = "C:/prj/mtm/imgs/challans";

    public static String generate(long vehicleid, long consignerid, String fromDate, String toDate) throws  Exception{
/*..*/

  /*  CreditDebit creditDebit = new CreditDebit();
    creditDebit.setDate(new Date());
    creditDebit.setAmount(23033.67);
    creditDebit.setType("Earned");
        CreditDebit creditDebit2 = new CreditDebit();
        creditDebit2.setDate(new Date());
        creditDebit2.setAmount(23033.67);
        creditDebit2.setType("Received");
        List<CreditDebit> creditDebitList = new ArrayList();
        creditDebitList.add(creditDebit);
        creditDebitList.add(creditDebit2); */
        BillingDao billingDao = new BillingDao();
        VehicleWork work = billingDao.downloadStatement(vehicleid,consignerid, false, fromDate , toDate);
        long currTimeInMilliseconds = System.currentTimeMillis();
        String uniquePath = vehicleid+"_"+consignerid+"_"+currTimeInMilliseconds;
        String xmlFilePath = PDF_GENERATION_TEMP_PATH+uniquePath+"xmlfile.xml";
        String pdfFilePath = PDF_GENERATION_TEMP_PATH+uniquePath+"myfile.pdf";


        File xmlFile = new File(xmlFilePath);
        File xsltFile = new File(XSLT_FILE_PATH);
        //File pdfFile = new File("C:\\Users\\Admin\\IdeaProjects\\mtm-core\\pdfgenerator\\src\\main\\resources\\myfile.pdf");



        JAXBContext jaxbContext = JAXBContext.newInstance(VehicleWork.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

        // output pretty printed
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        //Outputstream opstream =

        jaxbMarshaller.marshal(work, xmlFile);

        FopFactory fopFactory = FopFactory.newInstance(new File(FOP_CONF_PATH));

        FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
        OutputStream out = new BufferedOutputStream(new FileOutputStream(new File(pdfFilePath)));

        try {
            // Step 3: Construct fop with desired output format
            Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, out);


            // Step 4: Setup JAXP using identity transformer
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer(new StreamSource(xsltFile)); // identity transformer
            transformer.setParameter("versionParam", "1.0");


            // Step 5: Setup input and output for XSLT transformation
            // Setup input stream
            Source src = new StreamSource(xmlFile);

            // Resulting SAX events (the generated FO) must be piped through to FOP
            Result res = new SAXResult(fop.getDefaultHandler());

            // Step 6: Start XSLT transformation and FOP processing
            transformer.transform(src, res);

        } finally {
            //Clean-up
            out.close();
            Files.delete(Paths.get(xmlFilePath));
        }

        return pdfFilePath;

    }

    public static String generateForOwner(long ownerid, long consignerid, String fromDate, String toDate) throws  Exception{
/*..*/
        BillingDao billingDao = new BillingDao();
        List<List<String>> vehicleIdsRecords = billingDao.executeQuery("select distinct vehicleid from trip where routeid in (select routeid from route where" +
                " consignerid =  "+consignerid+" and ownerid = "+ownerid+")");

        OwnerLevelVehiclesWork ownerLevelVehiclesWork = new OwnerLevelVehiclesWork();


        for(List<String> recordColumns : vehicleIdsRecords) {
            Long vehicleid = Long.parseLong(recordColumns.get(0));
            VehicleWork work = billingDao.downloadStatement(vehicleid, consignerid, false, fromDate, toDate);
            ownerLevelVehiclesWork.getCreditDebits().addAll(work.getBusinessDetails());
            if(StringUtils.isEmpty(ownerLevelVehiclesWork.getConsignername())) {
                ownerLevelVehiclesWork.setConsignername(work.getConsignername());
                ownerLevelVehiclesWork.setOwnername(work.getOwnername());
                ownerLevelVehiclesWork.setReportdate(work.getReportdate());

            }
            ownerLevelVehiclesWork.setCurrentUnbilled(addAndReturnFormatted(ownerLevelVehiclesWork.getCurrentUnbilled() , work.getCurrentUnbilled()));
            ownerLevelVehiclesWork.setPreviousBilled(addAndReturnFormatted(ownerLevelVehiclesWork.getPreviousBilled() , work.getPreviousBilled()));
            ownerLevelVehiclesWork.setTotalReceivables(addAndReturnFormatted(ownerLevelVehiclesWork.getTotalReceivables() ,work.getTotalReceivables()));


        }


        long currTimeInMilliseconds = System.currentTimeMillis();
        String uniquePath = ownerid+"_"+consignerid+"_"+currTimeInMilliseconds;
        String xmlFilePath = PDF_GENERATION_TEMP_PATH+uniquePath+"xmlfile.xml";
        String pdfFilePath = PDF_GENERATION_TEMP_PATH+uniquePath+"myfile.pdf";


        File xmlFile = new File(xmlFilePath);
        File xsltFile = new File(XSLT_OWNER_FILE_PATH);
        //File pdfFile = new File("C:\\Users\\Admin\\IdeaProjects\\mtm-core\\pdfgenerator\\src\\main\\resources\\myfile.pdf");



        JAXBContext jaxbContext = JAXBContext.newInstance(OwnerLevelVehiclesWork.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

        // output pretty printed
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        //Outputstream opstream =

        jaxbMarshaller.marshal(ownerLevelVehiclesWork, xmlFile);

        FopFactory fopFactory = FopFactory.newInstance(new File(FOP_CONF_PATH));

        FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
        OutputStream out = new BufferedOutputStream(new FileOutputStream(new File(pdfFilePath)));

        try {
            // Step 3: Construct fop with desired output format
            Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, out);


            // Step 4: Setup JAXP using identity transformer
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer(new StreamSource(xsltFile)); // identity transformer
            transformer.setParameter("versionParam", "1.0");


            // Step 5: Setup input and output for XSLT transformation
            // Setup input stream
            Source src = new StreamSource(xmlFile);

            // Resulting SAX events (the generated FO) must be piped through to FOP
            Result res = new SAXResult(fop.getDefaultHandler());

            // Step 6: Start XSLT transformation and FOP processing
            transformer.transform(src, res);

        } finally {
            //Clean-up
            out.close();
            Files.delete(Paths.get(xmlFilePath));
        }

        return pdfFilePath;

    }


    public static String generateChallans(long vehicleid, long consignerid, String fromDate, String toDate) throws  Exception{

        BillingDao billingDao = new BillingDao();
        VehicleWork work = billingDao.downloadStatement(vehicleid,consignerid, false, fromDate, toDate);
        long currTimeInMilliseconds = System.currentTimeMillis();
        String uniquePath = vehicleid+"_"+consignerid+"_"+currTimeInMilliseconds;
        String xmlFilePath = CHALLAN_GENERATION_TEMP_PATH+uniquePath+"xmlfile.xml";
        String pdfFilePath = CHALLAN_GENERATION_TEMP_PATH+uniquePath+"myfile.pdf";

        for(CreditDebit creditDebit : work.getBusinessDetails())
        {
            // Construct local image URL
            String imageURL = "url(file:////"+CHALLAN_IMAGE_LOCATION+"/"+creditDebit.getTripid()+".jpg)";
            System.out.println("URL is "+imageURL);
            creditDebit.setChallanImageURL(imageURL);
        }


        File xmlFile = new File(xmlFilePath);
        File xsltFile = new File(XSLT_CHALLAN_FILE_PATH);
        //File pdfFile = new File("C:\\Users\\Admin\\IdeaProjects\\mtm-core\\pdfgenerator\\src\\main\\resources\\myfile.pdf");



        JAXBContext jaxbContext = JAXBContext.newInstance(VehicleWork.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

        // output pretty printed
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        //Outputstream opstream =

        jaxbMarshaller.marshal(work, xmlFile);

        FopFactory fopFactory = FopFactory.newInstance(new File(FOP_CONF_PATH));

        FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
        OutputStream out = new BufferedOutputStream(new FileOutputStream(new File(pdfFilePath)));

        try {
            // Step 3: Construct fop with desired output format
            Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, out);


            // Step 4: Setup JAXP using identity transformer
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer(new StreamSource(xsltFile)); // identity transformer
            transformer.setParameter("versionParam", "1.0");


            // Step 5: Setup input and output for XSLT transformation
            // Setup input stream
            Source src = new StreamSource(xmlFile);

            // Resulting SAX events (the generated FO) must be piped through to FOP
            Result res = new SAXResult(fop.getDefaultHandler());

            // Step 6: Start XSLT transformation and FOP processing
            transformer.transform(src, res);

        } finally {
            //Clean-up
            out.close();
            Files.delete(Paths.get(xmlFilePath));
        }

        return pdfFilePath;



    }

    private static synchronized String addAndReturnFormatted(String amount1 , String amount2) throws Exception
    {
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
        double amountDbl1 = 0.0d;
        double amountDbl2 = 0.0d;
        if(StringUtils.isNotEmpty(amount1))
            amountDbl1 = numberFormat.parse(amount1).doubleValue();
        if(StringUtils.isNotEmpty(amount2))
            amountDbl2 = numberFormat.parse(amount2).doubleValue();

            return numberFormat.format(amountDbl1 + amountDbl2);

    }

    public static void main(String[] args)
    {
        try {
           // generateChallans(5,2, null, null);
            generateForOwner(8,15,null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




}