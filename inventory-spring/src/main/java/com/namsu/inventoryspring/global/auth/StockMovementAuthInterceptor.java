package com.namsu.inventoryspring.global.auth;

import com.namsu.inventoryspring.domain.item.dao.ItemRepository;
import com.namsu.inventoryspring.domain.member.domain.Member;
import com.namsu.inventoryspring.domain.stockmovement.dao.StockMovementRepository;
import com.namsu.inventoryspring.domain.stockmovement.domain.StockMovement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class StockMovementAuthInterceptor implements HandlerInterceptor {

    private final StockMovementRepository stockMovementRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        PrincipalDetails principalDetails = (PrincipalDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Member member = principalDetails.getMember();

        Map<?, ?> pathVariables = (Map<?, ?>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        Long movementId = Long.parseLong((String) pathVariables.get("movementId"));
        
        StockMovement stockMovement = stockMovementRepository.findById(movementId).orElseThrow(() -> new IllegalStateException("입출고 로그를 조회할 수 없습니다."));
        
        if(!Objects.equals(stockMovement.getItem().getMember().getId(), member.getId())) {
            throw new AccessDeniedException("접근 권한이 없습니다.");
        }

        return true;
    }
}
