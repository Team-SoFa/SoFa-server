package com.sw19.sofa.domain.remind.service;

import com.sw19.sofa.domain.member.entity.Member;
import com.sw19.sofa.domain.remind.dto.response.RemindRes;
import com.sw19.sofa.domain.remind.entity.Remind;
import com.sw19.sofa.domain.remind.enums.RemindSortBy;
import com.sw19.sofa.global.common.dto.ListRes;
import com.sw19.sofa.global.common.dto.enums.SortOrder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RemindManageService {
    private final RemindService remindService;

    public ListRes<RemindRes> getRemindList(
            Member member,
            String lastId,
            int limit,
            RemindSortBy sortBy,
            SortOrder sortOrder
    ) {
        Long lastIdLong = lastId != null ? Long.parseLong(lastId) : null;
        List<Remind> reminds = remindService.searchReminds(
                member.getId(),
                lastIdLong,
                limit,
                sortBy,
                sortOrder
        );

        boolean hasNext = false;
        if (reminds.size() > limit) {
            hasNext = true;
            reminds = reminds.subList(0, limit);
        }

        List<RemindRes> responses = reminds.stream()
                .map(RemindRes::from)
                .toList();

        return new ListRes<>(responses, limit, responses.size(), hasNext);
    }
}
