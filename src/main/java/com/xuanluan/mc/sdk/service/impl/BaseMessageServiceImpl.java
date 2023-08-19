package com.xuanluan.mc.sdk.service.impl;

import com.xuanluan.mc.sdk.service.IMessageService;
import com.xuanluan.mc.sdk.service.file.BaseLoadFile;
import com.xuanluan.mc.sdk.utils.MessageUtils;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;

@Service
public class BaseMessageServiceImpl extends MessageUtils implements IMessageService {
    @PostConstruct
    void init() {
        initVietNam("message/base_vn.json");
        initEnglish("message/base_en.json");
    }

    @Override
    public void initVietNam(String pathFile) {
        Map<String, String> map = BaseLoadFile.convertInputStream(pathFile, Map.class);
        if (map != null) {
            map.forEach((k, v) -> put(k, v, MessageType.VIET_NAM));
        }
    }

    @Override
    public void initEnglish(String pathFile) {
        Map<String, String> map = BaseLoadFile.convertInputStream(pathFile, Map.class);
        if (map != null) {
            map.forEach((k, v) -> put(k, v, MessageType.ENGLISH));
        }
    }
}
