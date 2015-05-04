package sit.khaycake.Controller.Picture;

import com.google.gson.Gson;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import sit.khaycake.Filter.request.PictureRequest;
import sit.khaycake.database.SQL;
import sit.khaycake.model.Picture;
import sit.khaycake.util.ErrorMessage;
import sit.khaycake.util.SuccessMessage;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Falook Glico on 4/26/2015.
 */

@MultipartConfig(location = "/images",
        fileSizeThreshold=1024*1024*10,    // 10 MB
        maxFileSize=1024*1024*50,          // 50 MB
        maxRequestSize=1024*1024*100)      // 100 MB
public class PictureServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        SuccessMessage success = new SuccessMessage(session);
        ErrorMessage error = new ErrorMessage(session);
        PictureRequest pictureRequest = new PictureRequest(request);
        PrintWriter out = response.getWriter();
        if(pictureRequest.validate()) {
            if (ServletFileUpload.isMultipartContent(request)) {

                String appPath = request.getServletContext().getRealPath("");
                // constructs path of the directory to save uploaded file
                File savePath = new File/*(appPath+"\\images");*/("/usr/share/glassfish4/glassfish/domains/jsp.falook.me/applications/khaycake/images");
                List<Picture> pictures = new ArrayList<>();

                try {
                    List<FileItem> multiparts = new ServletFileUpload(
                            new DiskFileItemFactory()).parseRequest(request);

                    for (FileItem item : multiparts) {
                        if (item.getFieldName().equals("pictures")) {
                            String fileName = FilenameUtils.getName(item.getName());
                            String fileNamePrefix = FilenameUtils.getBaseName(fileName) + "_";
                            String fileNameSuffix = "." + FilenameUtils.getExtension(fileName);
                            File file = File.createTempFile(fileNamePrefix, fileNameSuffix, savePath);
                            item.write(file);

                            Picture picture = new Picture();
                            picture.setFilename(file.getName());
                            picture.save();
                            pictures.add(picture);
                        }

                    }
                    success.setMessage(pictures);

                } catch (Exception ex) {
                    error.setMessage(ex.getMessage());
                }
            } else {
                error.setMessage("มีบางอย่างไม่ถูกต้อง!");
            }
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        SuccessMessage success = new SuccessMessage(session);
        ErrorMessage error = new ErrorMessage(session);
        try {
            Gson gson = new Gson();
            String result = gson.toJson((List<Picture>)SQL.findAll(Picture.class));
            success.setMessage(result);
        } catch (Exception ex) {
            error.setMessage(ex.getMessage());
        }
    }

    private String getFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        System.out.println("content-disposition header= "+contentDisp);
        String[] tokens = contentDisp.split(";");
        for (String token : tokens) {
            if (token.trim().startsWith("filename")) {
                return token.substring(token.indexOf("=") + 2, token.length()-1);
            }
        }
        return "";
    }
}
