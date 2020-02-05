package org.debugroom.mynavi.sample.cloudformation.backend.app.model;

import java.util.List;
import java.util.stream.Collectors;

import org.debugroom.mynavi.sample.cloudformation.backend.domain.model.jpa.entity.User;
import org.debugroom.mynavi.sample.cloudformation.common.model.UserResource;

public interface UserResourceMapper {

    public static UserResource map(User user){
        return UserResource.builder()
                .userId(Long.toString(user.getUserId()))
                .firstName(user.getFirstName())
                .familyName(user.getFamilyName())
                .loginId(user.getLoginId())
                .isLogin(user.getIsLogin())
                .build();
    }

    public static List<UserResource> map(List<User> users){
        return users.stream().map(UserResourceMapper::map)
                .collect(Collectors.toList());
    }

}
