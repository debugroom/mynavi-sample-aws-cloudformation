package org.debugroom.mynavi.sample.cloudformation.frontend.app.web;

import org.debugroom.mynavi.sample.cloudformation.frontend.app.model.Sample;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.awt.image.BufferedImage;
import java.util.List;

import org.debugroom.mynavi.sample.cloudformation.common.model.SampleResource;
import org.debugroom.mynavi.sample.cloudformation.frontend.app.web.helper.S3DownloadHelper;
import org.debugroom.mynavi.sample.cloudformation.frontend.app.web.model.SampleSession;
import org.debugroom.mynavi.sample.cloudformation.frontend.domain.model.PortalResult;
import org.debugroom.mynavi.sample.cloudformation.frontend.domain.service.SampleCoreographyService;
import org.debugroom.mynavi.sample.cloudformation.frontend.domain.service.SampleOrchestrationService;

@Controller
public class SampleController {

    private static final String HEADER_HOST_NAME_KEY_NAME = "X-HostName";

    @Value("${app.host}")
    private String host;

    @Autowired
    SampleSession sampleSession;

    @Autowired
    S3DownloadHelper s3DownloadHelper;

    @Autowired
    SampleOrchestrationService sampleOrchestrationService;

    @Autowired
    SampleCoreographyService sampleCoreographyService;

    @GetMapping("/portal")
    public String portal(Model model){
        PortalResult portalResult = sampleOrchestrationService.getPortalResult();
        sampleSession.setFrontendHost(host);
        sampleSession.setBackendHost(MDC.get(HEADER_HOST_NAME_KEY_NAME));
        model.addAttribute("sampleResources",
                portalResult.getSampleResourceList());
        model.addAttribute("userResources",
                portalResult.getUserResourceList());
        model.addAttribute("sampleSession", sampleSession);
        return "portal";
    }

    @GetMapping(value = "/image",
            headers = "Accept=image/jpeg, image/jpg, image/png, image/gif",
            produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_GIF_VALUE})
    @ResponseBody
    public ResponseEntity<BufferedImage> getImage(){
        return ResponseEntity.ok().body(
                s3DownloadHelper.getImage("sample.jpg"));
    }

    @PostMapping("/message")
    @ResponseBody
    public ResponseEntity<List<SampleResource>> addMessage(
            @RequestBody Sample sample){
        sampleCoreographyService.save(
            org.debugroom.mynavi.sample.cloudformation.frontend.domain.model.Sample
                        .builder().sampleText(sample.getMessage()).build());
        return ResponseEntity.ok().body(
               sampleOrchestrationService.getSamples());
    }

}
