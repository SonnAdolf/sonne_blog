package sonn.fckeditor;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.fckeditor.connector.Messages;
import net.fckeditor.handlers.CommandHandler;
import net.fckeditor.handlers.ConnectorHandler;
import net.fckeditor.handlers.ExtensionsHandler;
import net.fckeditor.handlers.RequestCycleHandler;
import net.fckeditor.handlers.ResourceTypeHandler;
import net.fckeditor.response.UploadResponse;
import net.fckeditor.response.XmlResponse;
import net.fckeditor.tool.Utils;
import net.fckeditor.tool.UtilsFile;
import net.fckeditor.tool.UtilsResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
* @ClassName: ConnectorServlet 
* @Description: ConnectorServlet
* @author 无名
* @date 2016-5-15 上午11:56:45 
* @version 1.0
 */
public class ConnectorServlet extends HttpServlet 
{

    private static final long serialVersionUID = -5742008970929377161L;
    private static final Logger logger = LoggerFactory.getLogger(ConnectorServlet.class);

    /**
     * Initialize the servlet: <code>mkdir</code> &lt;DefaultUserFilesPath&gt;
     */
    public void init() throws ServletException, IllegalArgumentException 
    {
        String realDefaultUserFilesPath = getServletContext().getRealPath(
                ConnectorHandler.getDefaultUserFilesPath());

        File defaultUserFilesDir = new File(realDefaultUserFilesPath);
        UtilsFile.checkDirAndCreate(defaultUserFilesDir);

        logger.info("ConnectorServlet successfully initialized!");
    }

    /**
     * Manage the <code>GET</code> requests (<code>GetFolders</code>,
     * <code>GetFoldersAndFiles</code>, <code>CreateFolder</code>).<br/>
     * 
     * The servlet accepts commands sent in the following format:<br/>
     * <code>connector?Command=&lt;CommandName&gt;&Type=&lt;ResourceType&gt;&CurrentFolder=&lt;FolderPath&gt;</code>
     * <p>
     * It executes the commands and then returns the result to the client in XML
     * format.
     * </p>
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {
        logger.debug("Entering ConnectorServlet#doGet");

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/xml; charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter out = response.getWriter();

        String commandStr = request.getParameter("Command");
        String typeStr = request.getParameter("Type");
        String currentFolderStr = request.getParameter("CurrentFolder");

        logger.debug("Parameter Command: {}", commandStr);
        logger.debug("Parameter Type: {}", typeStr);
        logger.debug("Parameter CurrentFolder: {}", currentFolderStr);

        XmlResponse xr;

        if (!RequestCycleHandler.isEnabledForFileBrowsing(request))
            xr = new XmlResponse(XmlResponse.EN_ERROR, Messages.NOT_AUTHORIZED_FOR_BROWSING);
        else if (!CommandHandler.isValidForGet(commandStr))
            xr = new XmlResponse(XmlResponse.EN_ERROR, Messages.INVALID_COMMAND);
        else if (typeStr != null && !ResourceTypeHandler.isValid(typeStr))
            xr = new XmlResponse(XmlResponse.EN_ERROR, Messages.INVALID_TYPE);
        else if (!UtilsFile.isValidPath(currentFolderStr))
            xr = new XmlResponse(XmlResponse.EN_ERROR, Messages.INVALID_CURRENT_FOLDER);
        else 
        {
            CommandHandler command = CommandHandler.getCommand(commandStr);
            ResourceTypeHandler resourceType = ResourceTypeHandler.getDefaultResourceType(typeStr);

            String typePath = UtilsFile.constructServerSidePath(request, resourceType);
            String typeDirPath = getServletContext().getRealPath(typePath);

            File typeDir = new File(typeDirPath);
            UtilsFile.checkDirAndCreate(typeDir);

            File currentDir = new File(typeDir, currentFolderStr);

            if (!currentDir.exists())
                xr = new XmlResponse(XmlResponse.EN_INVALID_FOLDER_NAME);
            else 
            {

                xr = new XmlResponse(command, resourceType, currentFolderStr, UtilsResponse
                        .constructResponseUrl(request, resourceType, currentFolderStr, true,
                                ConnectorHandler.isFullUrl()));

                if (command.equals(CommandHandler.GET_FOLDERS))
                    xr.setFolders(currentDir);
                else if (command.equals(CommandHandler.GET_FOLDERS_AND_FILES))
                    xr.setFoldersAndFiles(currentDir);
                else if (command.equals(CommandHandler.CREATE_FOLDER))
                {
                    String newFolderStr = UtilsFile.sanitizeFolderName(
                            new String(request.getParameter("NewFolderName").getBytes("ISO8859-1"),"UTF-8")
                            );
                    logger.debug("Parameter NewFolderName: {}", newFolderStr);

                    File newFolder = new File(currentDir, newFolderStr);
                    int errorNumber = XmlResponse.EN_UKNOWN;

                    if (newFolder.exists())
                        errorNumber = XmlResponse.EN_ALREADY_EXISTS;
                    else 
                    {
                        try {
                            errorNumber = (newFolder.mkdir()) ? XmlResponse.EN_OK
                                    : XmlResponse.EN_INVALID_FOLDER_NAME;
                        } catch (SecurityException e) 
                        {
                            errorNumber = XmlResponse.EN_SECURITY_ERROR;
                        }
                    }
                    xr.setError(errorNumber);
                }
            }
        }

        out.print(xr);
        out.flush();
        out.close();
        logger.debug("Exiting ConnectorServlet#doGet");
    }

    /**
     * Manage the <code>POST</code> requests (<code>FileUpload</code>).<br />
     * 
     * The servlet accepts commands sent in the following format:<br />
     * <code>connector?Command=&lt;FileUpload&gt;&Type=&lt;ResourceType&gt;&CurrentFolder=&lt;FolderPath&gt;</code>
     * with the file in the <code>POST</code> body.<br />
     * <br>
     * It stores an uploaded file (renames a file if another exists with the
     * same name) and then returns the JavaScript callback.
     */
    @SuppressWarnings("unchecked")
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {
        logger.debug("Entering Connector#doPost");

        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter out = response.getWriter();

        String commandStr = request.getParameter("Command");
        String typeStr = request.getParameter("Type");
        String currentFolderStr = request.getParameter("CurrentFolder");

        logger.debug("Parameter Command: {}", commandStr);
        logger.debug("Parameter Type: {}", typeStr);
        logger.debug("Parameter CurrentFolder: {}", currentFolderStr);

        UploadResponse ur;

        // if this is a QuickUpload request, 'commandStr' and 'currentFolderStr'
        // are empty
        if (Utils.isEmpty(commandStr) && Utils.isEmpty(currentFolderStr)) 
        {
            commandStr = "QuickUpload";
            currentFolderStr = "/";
        }

        if (!RequestCycleHandler.isEnabledForFileUpload(request))
            ur = new UploadResponse(UploadResponse.SC_SECURITY_ERROR, null, null,
                    Messages.NOT_AUTHORIZED_FOR_UPLOAD);
        else if (!CommandHandler.isValidForPost(commandStr))
            ur = new UploadResponse(UploadResponse.SC_ERROR, null, null, Messages.INVALID_COMMAND);
        else if (typeStr != null && !ResourceTypeHandler.isValid(typeStr))
            ur = new UploadResponse(UploadResponse.SC_ERROR, null, null, Messages.INVALID_TYPE);
        else if (!UtilsFile.isValidPath(currentFolderStr))
            ur = UploadResponse.UR_INVALID_CURRENT_FOLDER;
        else 
        {
            ResourceTypeHandler resourceType = ResourceTypeHandler.getDefaultResourceType(typeStr);

            String typePath = UtilsFile.constructServerSidePath(request, resourceType);
            String typeDirPath = getServletContext().getRealPath(typePath);

            File typeDir = new File(typeDirPath);
            UtilsFile.checkDirAndCreate(typeDir);

            File currentDir = new File(typeDir, currentFolderStr);

            if (!currentDir.exists())
                ur = UploadResponse.UR_INVALID_CURRENT_FOLDER;
            else
            {

                String newFilename = null;
                FileItemFactory factory = new DiskFileItemFactory();
                ServletFileUpload upload = new ServletFileUpload(factory);
                //设置编码格式
                upload.setHeaderEncoding("UTF-8");
                try
                {

                    List<FileItem> items = upload.parseRequest(request);

                    // We upload only one file at the same time
                    FileItem uplFile = items.get(0);
                    String rawName = UtilsFile.sanitizeFileName(uplFile.getName());
                    String filename = FilenameUtils.getName(rawName);
                    String baseName = FilenameUtils.removeExtension(filename);
                    String extension = FilenameUtils.getExtension(filename);
                    //文件名自动替换
                    filename = UUID.randomUUID().toString() + "."+ extension;
                    
                    
                    if (!ExtensionsHandler.isAllowed(resourceType, extension))
                        ur = new UploadResponse(UploadResponse.SC_INVALID_EXTENSION);
                    else {

                        // construct an unique file name
                        File pathToSave = new File(currentDir, filename);
                        int counter = 1;
                        while (pathToSave.exists())
                        {
                            newFilename = baseName.concat("(").concat(String.valueOf(counter))
                                    .concat(")").concat(".").concat(extension);
                            pathToSave = new File(currentDir, newFilename);
                            counter++;
                        }

                        if (Utils.isEmpty(newFilename))
                            ur = new UploadResponse(UploadResponse.SC_OK, UtilsResponse
                                    .constructResponseUrl(request, resourceType, currentFolderStr,
                                            true, ConnectorHandler.isFullUrl()).concat(filename));
                        else
                            ur = new UploadResponse(UploadResponse.SC_RENAMED,
                                    UtilsResponse.constructResponseUrl(request, resourceType,
                                            currentFolderStr, true, ConnectorHandler.isFullUrl())
                                            .concat(newFilename), newFilename);

                        // secure image check
                        if (resourceType.equals(ResourceTypeHandler.IMAGE)
                                && ConnectorHandler.isSecureImageUploads()) {
                            if (UtilsFile.isImage(uplFile.getInputStream()))
                                uplFile.write(pathToSave);
                            else 
                            {
                                uplFile.delete();
                                ur = new UploadResponse(UploadResponse.SC_INVALID_EXTENSION);
                            }
                        } else
                            uplFile.write(pathToSave);

                    }
                } catch (Exception e) {
                    ur = new UploadResponse(UploadResponse.SC_SECURITY_ERROR);
                }
            }

        }

        out.print(ur);
        out.flush();
        out.close();

        logger.debug("Exiting Connector#doPost");
    }
}
