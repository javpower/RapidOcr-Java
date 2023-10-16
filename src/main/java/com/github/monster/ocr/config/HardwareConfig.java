package com.github.monster.ocr.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * OCR引擎启动硬件配置配置
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HardwareConfig implements IOcrConfig {


    /**
     * CPU 核心数量，默认 1
     */
    private int numThread = 1;

    /**
     * GPU0一般为默认GPU，参数选项：使用CPU(-1)/使用GPU0(0)/使用GPU1(1)/...
     * 重要：NCNN必须设置为0
     */
    private int gpuIndex = 0;

    public static HardwareConfig getDefaultConfig() {
        return new HardwareConfig(Runtime.getRuntime().availableProcessors() / 2, 0);
    }


}