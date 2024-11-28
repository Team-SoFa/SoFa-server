package com.sw19.sofa.domain.remind.service;

import com.sw19.sofa.domain.remind.repository.RemindRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RemindService {
    private final RemindRepository remindRepository;
}
