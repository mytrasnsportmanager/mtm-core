package com.mtm.service.rest.resources;

import com.mtm.beans.FileUploadResponse;
import com.mtm.beans.UploadFileType;
import com.mtm.beans.dto.*;
import com.mtm.dao.*;
import com.mtm.dao.beans.DataType;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by Admin on 4/20/2019.
 */
@Path("/mtm/uploadFile")
@Produces(MediaType.APPLICATION_JSON)
public class FileUploadService {
    /** The path to the folder where we want to store the uploaded files */
    private static final String UPLOAD_FOLDER = "/home/mtmuser/proj/deployed/mtm/resources/images/";
    private static final String webserverAddress = System.getProperty("webserverAddress");
    private static final String baseURL = "http://"+webserverAddress+":8080/mtm/resources/images";
    private static final OwnerDao ownerDao = new OwnerDao();
    private static final ConsignerDao consignerDao = new ConsignerDao();
    private static final DriverDao driverDao = new DriverDao();
    private static final VehicleDao vehicleDao = new VehicleDao();
    private static final DriverDao vehicleDriverDao = new DriverDao();
    private static final TripDao tripDao = new TripDao();

    public FileUploadService() {
    }
    @Context
    private UriInfo context;
    /**
     * Returns text response to caller containing uploaded file location
     *
     * @return error response in case of missing parameters an internal
     *         exception or success response if file has been stored
     *         successfully
     */
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Object uploadFile(
            @FormDataParam("file") InputStream uploadedInputStream,
            @FormDataParam("file") FormDataContentDisposition fileDetail,
            @FormDataParam("UploadType") String uploadType, @FormDataParam("AssociatedEntityId")
            String associatedEntityId) {

        FileUploadResponse fileUploadResponse = new FileUploadResponse();
        fileUploadResponse.setUploadedEntityId(associatedEntityId);
        fileUploadResponse.setUploadedFileType(uploadType);

        System.out.println("UploadType is "+uploadType);
        System.out.println("UploadType is "+associatedEntityId);


        // check if all form parameters are provided
        if (uploadedInputStream == null || fileDetail == null) {
            // return Response.status(400).entity("FAILED").build();
            fileUploadResponse.setMessage("FAILED");
            fileUploadResponse.setStatus(1);
            return fileUploadResponse;
        }
        // create our destination folder, if it not exists
        try {
            createFolderIfNotExists(UPLOAD_FOLDER);
        } catch (SecurityException se) {
            fileUploadResponse.setMessage("FAILED");
            fileUploadResponse.setStatus(1);
            return fileUploadResponse;
        }
        UploadFileType uploadFileType = UploadFileType.getFromValue(uploadType);
        String imageDirectory = UPLOAD_FOLDER + uploadFileType.getDirectoryName();
        String uploadedFileLocation = imageDirectory + "/"+associatedEntityId;
        try {
            saveToFile(uploadedInputStream, uploadedFileLocation);
        } catch (IOException e) {
            e.printStackTrace();
            fileUploadResponse.setMessage("FAILED");
            fileUploadResponse.setStatus(1);
            return fileUploadResponse;
        }


        String fullURL = upadateRecordAndReturnURL(uploadFileType,associatedEntityId);
        //String fullURL = baseURL + resourcePath;
        fileUploadResponse.setFileURL(fullURL);
        fileUploadResponse.setMessage("SUCCESS");
        fileUploadResponse.setStatus(0);
        return fileUploadResponse;
       // return Response.status(200)
          //      .entity("SUCCESS" ).build();
    }
    /**
     * Utility method to save InputStream data to target location/file
     *
     * @param inStream
     *            - InputStream to be saved
     * @param target
     *            - full path to destination file
     */
    private void saveToFile(InputStream inStream, String target)
            throws IOException {
        OutputStream out = null;
        int read = 0;
        byte[] bytes = new byte[1024];
        out = new FileOutputStream(new File(target));
        while ((read = inStream.read(bytes)) != -1) {
            out.write(bytes, 0, read);
        }
        out.flush();
        out.close();
    }
    /**
     * Creates a folder to desired location if it not already exists
     *
     * @param dirName
     *            - full path to the folder
     * @throws SecurityException
     *             - in case you don't have permission to create the folder
     */
    private void createFolderIfNotExists(String dirName)
            throws SecurityException {
        File theDir = new File(dirName);
        if (!theDir.exists()) {
            theDir.mkdir();
        }
    }

    private String upadateRecordAndReturnURL(UploadFileType fileType, String id)
    {
        Random rand = new Random();
        double randomNumber = rand.nextDouble();
        switch (fileType)
        {
            case OWNER_PROFILE_PIC: {

                String resourcePath = "/owner/" + id+"?rand="+randomNumber;
                Owner owner = (Owner)(ownerDao.getRecords(" ownerid = "+id).get(0));
                owner.setImage_url(baseURL+resourcePath);
                ownerDao.patch(owner);
                return baseURL+resourcePath;

            }

            case CHALLAN_PIC: {
                String resourcePath =  "/challan/" + id+"?rand="+randomNumber;
                Trip trip = (Trip)(tripDao.getRecords(" tripid = "+id).get(0));
                trip.setImage_url(baseURL+resourcePath);
                tripDao.patch(trip);
                return baseURL+resourcePath;
            }

            case CONSIGNER_PROFILE_PIC: {

                String resourcePath = "/consigner/" + id+"?rand="+randomNumber;
                Consigner consigner = (Consigner)(consignerDao.getRecords(" consignerid = "+id).get(0));
                consigner.setImage_url(baseURL+resourcePath);
                consignerDao.patch(consigner);
                return baseURL+resourcePath;

            }


            case VEHICLE_PIC: {
                String resourcePath = "/vehicle/" + id+"?rand="+randomNumber;
                Vehicle vehicle = (Vehicle)(vehicleDao.getRecords(" vehicleid = "+id).get(0));
                vehicle.setImage_url(baseURL+resourcePath);
                vehicleDao.patch(vehicle);
                return baseURL + resourcePath;

            }

            case DRIVER_PIC: {
                String resourcePath = "/driver/" + id+"?rand="+randomNumber;
                VehicleDriver vehicleDriver = (VehicleDriver)(vehicleDriverDao.getRecords(" driverid = "+id).get(0));
                vehicleDriver.setImage_url(baseURL+resourcePath);
                vehicleDriverDao.patch(vehicleDriver);
                return baseURL + resourcePath;

            }


        }
        return null;

    }
}
