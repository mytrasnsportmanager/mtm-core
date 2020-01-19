package com.mtm.pdfgenerator;
import com.mtm.beans.dto.CreditDebit;
import com.mtm.beans.dto.VehicleWork;
import com.mtm.dao.BillingDao;
import org.apache.batik.ext.awt.image.rendered.CachableRed;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Admin on 7/16/2019.
 */
public class PDFGeneratorUtil {

    private final static String PDF_GENERATION_TEMP_PATH = "/home/mtmuser/proj/deployed/mtm/resources/pdfgeneration/pdfs/";
    private final static String XSLT_FILE_PATH = "/home/mtmuser/proj/deployed/mtm/resources/pdfgeneration/table.xml";
    private final static String FOP_CONF_PATH = "/home/mtmuser/proj/deployed/mtm/resources/pdfgeneration/fop.xconf";

   //  For local execution
   /* private final static String PDF_GENERATION_TEMP_PATH = "C:/prj/mtm";
    private final static String XSLT_FILE_PATH = "C:/Users/Admin/IdeaProjects/mtm-core/pdfgenerator/src/main/resources/table.xml";
    private final static String FOP_CONF_PATH = "C:/Users/Admin/IdeaProjects/mtm-core/pdfgenerator/src/main/resources/fop.xconf";*/

    public static String generate(long vehicleid, long consignerid) throws  Exception{
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
        VehicleWork work = billingDao.downloadStatement(vehicleid,consignerid, false);
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
}