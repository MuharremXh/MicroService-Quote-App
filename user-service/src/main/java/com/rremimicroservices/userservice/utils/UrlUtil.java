package com.rremimicroservices.userservice.utils;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UrlUtil {

    @Autowired
    private  EurekaClient eurekaClient;

    public  String getServiceUrl(String serviceId){
        Application application = eurekaClient.getApplication(serviceId);
        InstanceInfo instanceInfo = application.getInstances().get(0);
        return instanceInfo.getHomePageUrl();
    }
}
