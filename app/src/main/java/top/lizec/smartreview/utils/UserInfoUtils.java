package top.lizec.smartreview.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import top.lizec.smartreview.entity.UserDetail;

@Component
public final class UserInfoUtils {

    public Integer getCurrentUserId() {
        Authentication au = SecurityContextHolder.getContext().getAuthentication();
        return ((UserDetail) au.getPrincipal()).getUserId();
    }


}
