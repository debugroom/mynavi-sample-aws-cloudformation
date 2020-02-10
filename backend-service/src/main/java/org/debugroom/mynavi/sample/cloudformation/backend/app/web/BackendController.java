package org.debugroom.mynavi.sample.cloudformation.backend.app.web;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.debugroom.mynavi.sample.cloudformation.backend.app.model.UserResourceMapper;
import org.debugroom.mynavi.sample.cloudformation.backend.domain.service.SampleService;
import org.debugroom.mynavi.sample.cloudformation.common.model.SampleResource;
import org.debugroom.mynavi.sample.cloudformation.common.model.UserResource;

@RestController
@RequestMapping("backend/api/v1")
public class BackendController {

    private static final String HEADER_ID_KEY_NAME = "X-HostName";

    @Value("${app.host}")
    private String host;

    @ModelAttribute
    public void setResponseHeader(HttpServletResponse response){
        response.setHeader(HEADER_ID_KEY_NAME, host);
    }

    @Autowired
    SampleService sampleService;

    @GetMapping("/users")
    public List<UserResource> getUsers(){
        return UserResourceMapper.map(sampleService.getUsers());
    }

    @GetMapping("/samples")
    public List<SampleResource> getSamples(){
        return sampleService.getSamples();
    }

    @PostMapping("/samples/new")
    public SampleResource addSample(String message){
        return sampleService.addSample(message);
    }

    @GetMapping("/healthcheck")
    public String getResponse(){
        return "I'm healthy. : " + host;
    }

}
