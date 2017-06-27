package com.chanryma.wjzq.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.chanryma.wjzq.model.ResponseEntity;
import com.chanryma.wjzq.util.Constant;
import com.chanryma.wjzq.util.GsonUtil;
import com.chanryma.wjzq.util.LogUtil;

public class UploadServlet extends HttpServlet {
    private static final long serialVersionUID = 8382528398817665361L;
    /**
     * Name of the directory where uploaded files will be saved, relative to
     * the web application directory.
     */
    private static final String SAVE_DIR = "uploadFiles";

    /**
     * handles file upload
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ResponseEntity entity = new ResponseEntity();
        entity.setStatus(Constant.RESULT_CODE_SUCCESS);
        try {
            // gets absolute path of the web application
            String appPath = request.getServletContext().getRealPath("");
            // constructs path of the directory to save uploaded file
            String savePath = appPath + File.separator + SAVE_DIR;

            // creates the save directory if it does not exists
            File fileSaveDir = new File(savePath);
            if (!fileSaveDir.exists()) {
                fileSaveDir.mkdir();
            }

            OutputStream outStream = null;
            InputStream inStream = null;

            int bytes = 0;
            try {
                inStream = request.getInputStream();
                
                Path target = FileSystems.getDefault().getPath(savePath,  File.separator + System.currentTimeMillis() + ".png");;
                Files.copy(inStream, target);
                LogUtil.debug("Saved one file.");
//                byte[] buffer = new byte[1024];
//                int size = inStream.read(buffer);
//                if (size > 0) {
//                    String filePath = savePath + File.separator + System.currentTimeMillis();
//                    outStream = new FileOutputStream(filePath);
//                    while (size > 0) {
//                        LogUtil.debug("read bytes " + bytes);
//                        outStream.write(buffer, 0, size);
//                        buffer = new byte[1024];
//                        size = inStream.read(buffer);
//                        bytes += size;
//                    }
//
//                    LogUtil.debug("Saved one file to " + filePath);
//                }
            } catch (IOException e) {

            } finally {
                if (inStream != null) {
                    try {
                        inStream.close();
                    } catch (IOException e) {
                    }
                }

                if (outStream != null) {
                    try {
                        outStream.close();
                    } catch (IOException e) {
                    }
                }
            }
        } catch (Exception e) {
            entity.setStatus(Constant.RESULT_CODE_ERROR);
        }

        sendResponse(response, entity);
    }

    /**
     * Extracts file name from HTTP header content-disposition
     */
    private String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length() - 1);
            }
        }
        return "";
    }

    private void sendResponse(HttpServletResponse response, ResponseEntity responseEntity) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");

        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.append(GsonUtil.objectToJson(responseEntity));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
}
