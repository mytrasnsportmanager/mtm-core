package com.mtm.service.rest.resources;

import com.mtm.beans.FileUploadResponse;
import com.mtm.beans.UploadFileType;
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

/**
 * Created by Admin on 4/20/2019.
 */
@Path("/mtm/uploadFile")
@Produces(MediaType.APPLICATION_JSON)
public class FileUploadService {
    /** The path to the folder where we want to store the uploaded files */
    private static final String UPLOAD_FOLDER = "/home/mtmuser/proj/deployed/mtm/resources/images/";
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

        System.out.println("UploadType is "+uploadType);
        System.out.println("UploadType is "+associatedEntityId);


        // check if all form parameters are provided
        if (uploadedInputStream == null || fileDetail == null)
            return Response.status(400).entity("FAILED").build();
        // create our destination folder, if it not exists
        try {
            createFolderIfNotExists(UPLOAD_FOLDER);
        } catch (SecurityException se) {
            return Response.status(500)
                    .entity("FAILED")
                    .build();
        }
        UploadFileType uploadFileType = UploadFileType.getFromValue(uploadType);
        String imageDirectory = UPLOAD_FOLDER + uploadFileType.getDirectoryName();
        String uploadedFileLocation = imageDirectory + "/"+associatedEntityId +".jpg";
        try {
            saveToFile(uploadedInputStream, uploadedFileLocation);
        } catch (IOException e) {
            e.printStackTrace();
            return Response.status(500).entity("FAILED").build();
        }

        Dao recordUpdateDao = null;
        String idColumnName = "";
        String tableName = "";
        switch (uploadFileType)
        {
            case OWNER_PROFILE_PIC:
                recordUpdateDao = new OwnerDao();
                idColumnName = "ownerid";
                tableName = "owner";
                break;
            case CHALLAN_PIC:
                recordUpdateDao = new TripDao();
                idColumnName = "tripid";
                tableName = "trip";
                break;
            case CONSIGNER_PROFILE_PIC:
                recordUpdateDao = new OwnerConsignerDao();
                idColumnName = "consignerid";
                tableName = "consigner";
                break;
            case VEHICLE_PIC:
                recordUpdateDao = new VehicleDao();
                idColumnName = "vehicleid";
                tableName = "vehicle";
                break;

        }
        Column column = new Column();
        column.setName("image_url");
        column.setType(DataType.valueOf("STRING"));
        column.setTableName(tableName);
        Map<Column,String> columnStringMap = new HashMap<Column,String>();
        columnStringMap.put(column,associatedEntityId +".jpg");
        String whereClause = idColumnName +" = "+associatedEntityId;
        recordUpdateDao.update(columnStringMap,whereClause);

        FileUploadResponse fileUploadResponse = new FileUploadResponse();
        fileUploadResponse.setUploadedEntityId(associatedEntityId);
        fileUploadResponse.setUploadedFileType(uploadType);
        String baseURL = "http://34.93.251.44:8080/mtm/resources/images";
        String resourcePath = getResourcePathForURL(uploadFileType,associatedEntityId);
        String fullURL = baseURL + resourcePath;
        fileUploadResponse.setFileURL(fullURL);
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

    private String getResourcePathForURL(UploadFileType fileType, String id)
    {
        switch (fileType)
        {
            case OWNER_PROFILE_PIC:
                return "/owner/"+id;

            case CHALLAN_PIC:
                return "/challan/"+id;

            case CONSIGNER_PROFILE_PIC:
                return "/consigner/"+id;

            case VEHICLE_PIC:
                return "/vehicle/"+id;


        }
        return null;

    }
}
