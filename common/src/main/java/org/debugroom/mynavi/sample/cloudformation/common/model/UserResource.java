package org.debugroom.mynavi.sample.cloudformation.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserResource implements Serializable {

    private String userId;
    private String firstName;
    private String familyName;
    private String loginId;
    private boolean isLogin;

}
