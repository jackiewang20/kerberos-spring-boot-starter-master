package com.example.hdfs.kerberos.provider.controller;

import com.example.common.basic.EnumCode;
import com.example.common.basic.ResultBean;
import com.example.hdfs.starter.component.HdfsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.fs.BlockLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;


/**
 * @author jackie wang
 * @since 2021/3/10 16:37
 */
@Api(value = "hadoop kerberos认证", tags = {"hadoop kerberos认证接口"})
@RestController
@RequestMapping("/hadoop/hdfs")
public class HdfsController {

    private static Logger LOGGER = LoggerFactory.getLogger(HdfsController.class);

    @Autowired
    private HdfsService hdfsService;

    /**
     * 创建文件夹
     *
     * @param path
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "mkdir", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<Boolean> mkdir(@RequestParam("path") String path) throws Exception {
        if (StringUtils.isEmpty(path)) {
            LOGGER.info("请求参数为空");
            return new ResultBean<Boolean>(EnumCode.CODE_PARAMETER_MISSING.getCode(), "请求参数为空");
        }
        // 创建空文件夹
        boolean isOk = hdfsService.mkdir(path);
        if (isOk) {
            LOGGER.info("文件夹创建成功");
            return new ResultBean<Boolean>(EnumCode.CODE_OK.getCode(),
                    EnumCode.CODE_OK.getText() + "文件夹创建成功");
        } else {
            LOGGER.info("文件夹创建失败");
            return new ResultBean<Boolean>(EnumCode.CODE_EXCEPTION.getCode(),
                    EnumCode.CODE_EXCEPTION.getText() + "文件夹创建失败");
        }
    }

    /**
     * 读取HDFS目录信息
     *
     * @param path
     * @return
     * @throws Exception
     */
    @PostMapping("/readPathInfo")
    public ResultBean<List<Map<String, Object>>> readPathInfo(@RequestParam("path") String path) throws Exception {
        List<Map<String, Object>> list = hdfsService.readPathInfo(path);
        return new ResultBean<List<Map<String, Object>>>(EnumCode.CODE_OK.getCode(),
                "读取HDFS目录信息成功", list);
    }

    /**
     * 获取HDFS文件在集群中的位置
     *
     * @param path
     * @return
     * @throws Exception
     */
    @PostMapping("/getFileBlockLocations")
    public ResultBean<BlockLocation[]> getFileBlockLocations(@RequestParam("path") String path) throws Exception {
        BlockLocation[] blockLocations = hdfsService.getFileBlockLocations(path);
        return new ResultBean<BlockLocation[]>(EnumCode.CODE_OK.getCode(),
                "获取HDFS文件在集群中的位置", blockLocations);
    }

    /**
     * 创建/上传文件
     *
     * @param path
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/upload",consumes = "multipart/form-data")
    @ApiOperation(value = "上传文件", notes = "上传文件", httpMethod="POST" )
    public ResultBean upload(@RequestParam("path") String path, @RequestParam("file") MultipartFile file)
            throws Exception {
        if (StringUtils.isEmpty(path) || null == file.getBytes()) {
            return new ResultBean(EnumCode.CODE_PARAMETER_MISSING.getCode(), "请求参数为空");
        }
        hdfsService.uploadCreateFile(path, file);
        return new ResultBean(EnumCode.CODE_OK.getCode(), EnumCode.CODE_OK + "创建文件成功");
    }

    /**
     * 读取HDFS文件内容
     *
     * @param path
     * @return
     * @throws Exception
     */
    @PostMapping("/readFile")
    public ResultBean readFile(@RequestParam("path") String path) throws Exception {
        String content = hdfsService.readFile(path);
        LOGGER.info("读取HDFS文件内容");
        return new ResultBean(EnumCode.CODE_OK.getCode(), EnumCode.CODE_OK.getText(), content);
    }

    /**
     * 读取HDFS文件转换成Byte类型
     *
     * @param path
     * @return
     * @throws Exception
     */
    @PostMapping("/openFileToBytes")
    public ResultBean<byte[]> openFileToBytes(@RequestParam("path") String path) throws Exception {
        byte[] files = hdfsService.openFileToBytes(path);
        LOGGER.info("读取HDFS文件转换成Byte类型");
        return new ResultBean(files);
    }

    /**
     * 读取文件列表
     *
     * @param path
     * @return
     * @throws Exception
     */
    @PostMapping("/listFile")
    public ResultBean<List<Map<String, String>>> listFile(@RequestParam("path") String path) throws Exception {
        if (StringUtils.isEmpty(path)) {
            return new ResultBean<List<Map<String, String>>>(EnumCode.CODE_PARAMETER_MISSING.getCode(), "请求参数为空");
        }
        List<Map<String, String>> returnList = hdfsService.listFile(path);
        LOGGER.info("读取文件列表成功");
        return new ResultBean(returnList);
    }

    /**
     * 重命名文件
     *
     * @param oldFilePath
     * @param newFilePath
     * @return
     * @throws Exception
     */
    @PostMapping("/renameFile")
    public ResultBean<String> renameFile(@RequestParam("oldFilePath") String oldFilePath, @RequestParam("newFilePath") String newFilePath)
            throws Exception {
        if (StringUtils.isEmpty(oldFilePath) || StringUtils.isEmpty(newFilePath)) {
            return new ResultBean<>(EnumCode.CODE_PARAMETER_MISSING.getCode(), "请求参数为空");
        }
        boolean isOk = hdfsService.renameFile(oldFilePath, newFilePath);
        if (isOk) {
            return new ResultBean<>("文件重命名成功");
        } else {
            return new ResultBean<>(EnumCode.CODE_EXCEPTION.getCode(), "文件重命名失败");
        }
    }

    /**
     * 删除文件
     *
     * @param path
     * @return
     * @throws Exception
     */
    @PostMapping("/deleteFile")
    public ResultBean deleteFile(@RequestParam("path") String path) throws Exception {
        boolean isOk = hdfsService.deleteFile(path);
        if (isOk) {
            LOGGER.info("delete file success");
            return new ResultBean();
        } else {
            LOGGER.info("delete file fail");
            return new ResultBean(EnumCode.CODE_EXCEPTION.getCode(), "delete file fail");
        }
    }

    /**
     * 上传文件
     *
     * @param localFilePath
     * @param uploadFilePath
     * @return
     * @throws Exception
     */
    @PostMapping("/uploadFile")
    public ResultBean uploadFile(@RequestParam("localFilePath") String localFilePath, @RequestParam("uploadFilePath") String uploadFilePath)
            throws Exception {
        hdfsService.uploadOverwriteFile(localFilePath, uploadFilePath);
        LOGGER.info("upload file success.");
        return new ResultBean();
    }

    /**
     * 下载文件
     *
     * @param path
     * @param downloadPath
     * @return
     * @throws Exception
     */
    @PostMapping("/downloadFile")
    public ResultBean downloadFile(@RequestParam("path") String path, @RequestParam("downloadPath") String downloadPath)
            throws Exception {
        hdfsService.downloadFile(path, downloadPath);
        LOGGER.info("download file success");
        return new ResultBean();
    }

    /**
     * HDFS文件复制
     *
     * @param sourcePath
     * @param targetPath
     * @return
     * @throws Exception
     */
    @PostMapping("/copyFile")
    public ResultBean copyFile(@RequestParam("sourcePath") String sourcePath, @RequestParam("targetPath") String targetPath)
            throws Exception {
        hdfsService.copyFile(sourcePath, targetPath);
        LOGGER.info("copy file success");
        return new ResultBean();
    }

    /**
     * 查看文件是否已存在
     *
     * @param path
     * @return
     * @throws Exception
     */
    @PostMapping("/existFile")
    public ResultBean<Boolean> existFile(@RequestParam("path") String path) throws Exception {
        boolean isExist = hdfsService.existFile(path);
        LOGGER.info("file isExist: {}", isExist);
        return new ResultBean<Boolean>(isExist);
    }
}
