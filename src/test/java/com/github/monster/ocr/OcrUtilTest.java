package com.github.monster.ocr;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import com.benjaminwan.ocrlibrary.OcrResult;
import com.benjaminwan.ocrlibrary.TextBlock;
import com.github.monster.ocr.config.HardwareConfig;
import com.github.monster.ocr.config.LibConfig;
import com.github.monster.ocr.config.ParamConfig;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.ArrayList;

/**
 * @author Monster
 */
public class OcrUtilTest {

    @Test
    public void NcnnTest() {
        // 使用NCNN引擎进行识别
        OcrResult NCNNResult = OcrUtil.runOcr(getResourcePath("/images/40.png"), LibConfig.getNcnnConfig(), ParamConfig.getDefaultConfig(), HardwareConfig.getNcnnConfig());
        Assert.assertEquals("40", NCNNResult.getStrRes().trim().toString());
    }

    @Test
    public void OnnxTest() {
        String imgPath = getResourcePath("/images/40.png");
        OcrResult ONNXResult = OcrUtil.runOcr(imgPath);
        Assert.assertEquals("40", ONNXResult.getStrRes().trim().toString());
    }

    @Test
    public void OnnxDrawTest() {
        String imgPath = getResourcePath("/images/test.png");
        String drawPath = imgPath.replace("test", "40-draw");
        File drawFile = new File(drawPath);
        // 使用ONNX推理引擎进行识别
        // 配置参数
        ParamConfig paramConfig = ParamConfig.getDefaultConfig();
        paramConfig.setDoAngle(true);
        paramConfig.setMostAngle(true);
        // 开始识别
        OcrResult ONNXResult = OcrUtil.runOcr(imgPath, LibConfig.getOnnxConfig(), paramConfig);
        // 绘制推理结果
        ArrayList<TextBlock> textBlocks = ONNXResult.getTextBlocks();
        FileUtil.copy(imgPath, drawPath, Boolean.TRUE);
        ByteArrayInputStream in = IoUtil.toStream(ImageUtil.drawImg(drawFile, textBlocks));
        FileUtil.writeFromStream(in, drawFile);
    }

    @Test
    public void paramTest() {
        // 配置参数
        ParamConfig paramConfig = ParamConfig.getDefaultConfig();
        paramConfig.setDoAngle(true);
        paramConfig.setMostAngle(true);
        // 开始识别
        OcrResult ocrResult = OcrUtil.runOcr(getResourcePath("/images/test.png"), LibConfig.getNcnnConfig(), paramConfig);
        System.out.println(ocrResult);
    }

    @Test
    public void hardWareTest() {
        // 配置可变参数
        ParamConfig paramConfig = ParamConfig.getDefaultConfig();
        paramConfig.setDoAngle(true);
        paramConfig.setMostAngle(true);
        // 配置硬件参数：4核CPU，使用GPU0
        HardwareConfig hardwareConfig = new HardwareConfig(4, 0);
        // 开始识别
        OcrResult ocrResult = OcrUtil.runOcr(getResourcePath("/images/test.png"), LibConfig.getNcnnConfig(), paramConfig, hardwareConfig);
        System.out.println(ocrResult);
    }

    @Test
    public void repeatOcr() {
        String real = "40";
        System.out.println("NCNN 1>>>>>>>> ");
        OcrResult NCNN_1 = OcrUtil.runOcr(getResourcePath("/images/40.png"));
        Assert.assertEquals(real, NCNN_1.getStrRes().trim().toString());

        System.out.println("NCNN 2>>>>>>>> ");
        OcrResult NCNN_2 = OcrUtil.runOcr(getResourcePath("/images/40.png"));
        Assert.assertEquals(real, NCNN_2.getStrRes().trim().toString());

        System.out.println("NCNN 3>>>>>>>> ");
        OcrResult NCNN_3 = OcrUtil.runOcr(getResourcePath("/images/40.png"));
        Assert.assertEquals(real, NCNN_3.getStrRes().trim().toString());

        System.out.println("NCNN 4>>>>>>>> ");
        OcrResult NCNN_4 = OcrUtil.runOcr(getResourcePath("/images/system.png"));
        Assert.assertEquals("System", NCNN_4.getStrRes().trim().toString());

        System.out.println("NCNN 5>>>>>>>> ");
        OcrResult NCNN_5 = OcrUtil.runOcr(getResourcePath("/images/40.png"));
        Assert.assertEquals(real, NCNN_5.getStrRes().trim().toString());
    }

    private static String getResourcePath(String path) {
        return new File(OcrUtilTest.class.getResource(path).getFile()).toString();
    }

}
