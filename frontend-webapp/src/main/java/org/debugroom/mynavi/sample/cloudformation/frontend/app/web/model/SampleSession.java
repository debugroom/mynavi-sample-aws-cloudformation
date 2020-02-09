package org.debugroom.mynavi.sample.cloudformation.frontend.app.web.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@SessionScope
@Component
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class SampleSession implements Serializable {

    private String backendHost;
    private String frontendHost;
    private LocalDateTime lastUpdatedAt;

}
