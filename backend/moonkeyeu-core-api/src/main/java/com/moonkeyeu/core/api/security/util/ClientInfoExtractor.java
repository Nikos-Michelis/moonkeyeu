package com.moonkeyeu.core.api.security.util;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ua_parser.Client;
import ua_parser.Parser;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class ClientInfoExtractor {
    public Map<String, String> getClientInfo(HttpServletRequest request) {
        String remoteAddr = request.getRemoteAddr();
        String remoteHost = request.getRemoteHost();
        String remoteUser = request.getRemoteUser();
        String contentType = request.getHeader("content-type");
        String userAgent = request.getHeader("user-agent");

        Parser uaParser = new Parser();
        Client client = uaParser.parse(userAgent);

        Map<String, String> clientInfo = new HashMap<>();
        clientInfo.put("os_family", client.os.family);
        clientInfo.put("device_family", client.device.family);
        clientInfo.put("userAgent_family", client.userAgent.family);
        clientInfo.put("remote_address", remoteAddr);
        clientInfo.put("remote_host", remoteHost);
        clientInfo.put("remote_user", remoteUser);
        clientInfo.put("content_type", contentType);
        //log.info("client: {} ", clientInfo);
        return clientInfo;
    }
}
