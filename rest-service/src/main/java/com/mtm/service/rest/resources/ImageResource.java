package com.mtm.service.rest.resources;

import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Optional;
import org.apache.commons.io.FileUtils;

import javax.ws.rs.GET;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import javax.imageio.ImageIO;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;

/**
 * Created by Admin on 3/13/2019.
 */

@Path("/mtm/resources/images")
@Produces("image/png")
public class ImageResource {

    private static final String OWNER_IMAGE_BASE_URL = "/home/mtmuser/proj/deployed/mtm/resources/images/owners";
    private static final String CONSIGNER_IMAGE_BASE_URL = "/home/mtmuser/proj/deployed/mtm/resources/images/consigners";
    private static final String VEHICLE_IMAGE_BASE_URL = "/home/mtmuser/proj/deployed/mtm/resources/images/vehicles";
    private static final String CHALLAN_IMAGE_BASE_URL = "/home/mtmuser/proj/deployed/mtm/resources/images/challans";
   // private static  final String VEHICLE_DOCUMENTS_BASE_URL = "C:/prj/mtm/imgs/vehicledocuments";
    private static final String VEHICLE_DOCUMENTS_BASE_URL = "/home/mtmuser/proj/deployed/mtm/resources/images/vehicledocuments";
    private static final String ICON_IMAGE_BASE_URL = "/home/mtmuser/proj/deployed/mtm/resources/images/icons";
    private static final String VEHICLE_DRIVER_IMAGE_BASE_URL = "/home/mtmuser/proj/deployed/mtm/resources/images/drivers";
    //private static final String OWNER_IMAGE_BASE_URL = "D:/pics";

    @GET
    @Path("/owner/{imageurl}")
    @Timed
    public Response getOwnerImage(@PathParam("imageurl") Optional<String> imageUrl)
    {
        String fileName = OWNER_IMAGE_BASE_URL+"/"+imageUrl.get();
        System.out.println("Returning "+fileName);
        File image = new File(fileName);

        try {
            //BufferedImage in = ImageIO.read(image);

            //BufferedImage newImage = new BufferedImage(
                 //   200, 150, BufferedImage.TYPE_INT_RGB);
           // Graphics2D g = (Graphics2D) newImage.getGraphics();

           // ImageIO.write(newImage, "png", baos);
            //byte[] imageData = baos.toByteArray();
            FileInputStream fis = new FileInputStream(image);
            BufferedInputStream bis = new BufferedInputStream(fis);

            return  Response.ok(new ByteArrayInputStream(FileUtils.readFileToByteArray(image))).build();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @GET
    @Path("/consigner/{imageurl}")
    @Timed
    public Response getConsignerImage(@PathParam("imageurl") Optional<String> imageUrl)
    {
        File image = new File(CONSIGNER_IMAGE_BASE_URL+"/"+imageUrl.get());

        try {
            //BufferedImage in = ImageIO.read(image);

            //BufferedImage newImage = new BufferedImage(
            //   200, 150, BufferedImage.TYPE_INT_RGB);
            // Graphics2D g = (Graphics2D) newImage.getGraphics();

            // ImageIO.write(newImage, "png", baos);
            //byte[] imageData = baos.toByteArray();
            FileInputStream fis = new FileInputStream(image);
            BufferedInputStream bis = new BufferedInputStream(fis);

            return  Response.ok(new ByteArrayInputStream(FileUtils.readFileToByteArray(image))).build();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @GET
    @Path("/vehicle/{imageurl}")
    @Timed
    public Response getVehicleImage(@PathParam("imageurl") Optional<String> imageUrl)
    {
        File image = new File(VEHICLE_IMAGE_BASE_URL+"/"+imageUrl.get());
        try {
            //BufferedImage in = ImageIO.read(image);

            //BufferedImage newImage = new BufferedImage(
            //   200, 150, BufferedImage.TYPE_INT_RGB);
            // Graphics2D g = (Graphics2D) newImage.getGraphics();

            // ImageIO.write(newImage, "png", baos);
            //byte[] imageData = baos.toByteArray();
            FileInputStream fis = new FileInputStream(image);
            BufferedInputStream bis = new BufferedInputStream(fis);

            return  Response.ok(new ByteArrayInputStream(FileUtils.readFileToByteArray(image))).build();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    @GET
    @Path("/driver/{imageurl}")
    @Timed
    public Response getDriverImage(@PathParam("imageurl") Optional<String> imageUrl)
    {
        File image = new File(VEHICLE_DRIVER_IMAGE_BASE_URL+"/"+imageUrl.get());
        try {
            //BufferedImage in = ImageIO.read(image);

            //BufferedImage newImage = new BufferedImage(
            //   200, 150, BufferedImage.TYPE_INT_RGB);
            // Graphics2D g = (Graphics2D) newImage.getGraphics();

            // ImageIO.write(newImage, "png", baos);
            //byte[] imageData = baos.toByteArray();
            FileInputStream fis = new FileInputStream(image);
            BufferedInputStream bis = new BufferedInputStream(fis);

            return  Response.ok(new ByteArrayInputStream(FileUtils.readFileToByteArray(image))).build();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @GET
    @Path("/challan/{imageurl}")
    @Timed
    public Response getChallanImage(@PathParam("imageurl") Optional<String> imageUrl)
    {
        File image = new File(CHALLAN_IMAGE_BASE_URL+"/"+imageUrl.get());
        try {
            //BufferedImage in = ImageIO.read(image);

            //BufferedImage newImage = new BufferedImage(
            //   200, 150, BufferedImage.TYPE_INT_RGB);
            // Graphics2D g = (Graphics2D) newImage.getGraphics();

            // ImageIO.write(newImage, "png", baos);
            //byte[] imageData = baos.toByteArray();
            FileInputStream fis = new FileInputStream(image);
            BufferedInputStream bis = new BufferedInputStream(fis);

            return  Response.ok(new ByteArrayInputStream(FileUtils.readFileToByteArray(image))).build();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @GET
    @Path("/vehiclegeneraldocument/{imageurl}")
    @Timed
    public Response getVehicleDocument(@PathParam("imageurl") Optional<String> imageUrl)
    {
        File image = new File(VEHICLE_DOCUMENTS_BASE_URL+"/"+imageUrl.get());
        try {
            //BufferedImage in = ImageIO.read(image);

            //BufferedImage newImage = new BufferedImage(
            //   200, 150, BufferedImage.TYPE_INT_RGB);
            // Graphics2D g = (Graphics2D) newImage.getGraphics();

            // ImageIO.write(newImage, "png", baos);
            //byte[] imageData = baos.toByteArray();
            FileInputStream fis = new FileInputStream(image);
            BufferedInputStream bis = new BufferedInputStream(fis);

            return  Response.ok(new ByteArrayInputStream(FileUtils.readFileToByteArray(image))).build();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @GET
    @Path("/icon/{imageurl}")
    @Timed
    public Response getIconImage(@PathParam("imageurl") Optional<String> imageUrl)
    {
        File image = new File(ICON_IMAGE_BASE_URL+"/"+imageUrl.get());
        try {
            //BufferedImage in = ImageIO.read(image);

            //BufferedImage newImage = new BufferedImage(
            //   200, 150, BufferedImage.TYPE_INT_RGB);
            // Graphics2D g = (Graphics2D) newImage.getGraphics();

            // ImageIO.write(newImage, "png", baos);
            //byte[] imageData = baos.toByteArray();
            FileInputStream fis = new FileInputStream(image);
            BufferedInputStream bis = new BufferedInputStream(fis);

            return  Response.ok(new ByteArrayInputStream(FileUtils.readFileToByteArray(image))).build();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
