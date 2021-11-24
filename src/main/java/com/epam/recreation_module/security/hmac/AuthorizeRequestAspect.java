package com.epam.recreation_module.security.hmac;

import com.epam.recreation_module.payload.ApiResponse;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class AuthorizeRequestAspect {

    private final HMACUtil hmacUtil;

    public AuthorizeRequestAspect(HMACUtil hmacUtil) {
        this.hmacUtil = hmacUtil;
    }

    @Around("@annotation(com.epam.recreation_module.security.hmac.AuthorizeRequest)")
    public Object authorizeRequest(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String keyId = request.getHeader("sm-keyId");
        String timestamp = request.getHeader("sm-timestamp");
        String action = request.getHeader("sm-action");
        String signature = request.getHeader("sm-signature");
        if (keyId == null || timestamp == null || action == null || signature == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("Headers are null!", false));
        }
        boolean hasAccess = hmacUtil.hasAccess(keyId, timestamp, action, signature);
        if (!hasAccess) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse("Unauthorized!", false));
        }
        return joinPoint.proceed();
    }
}
