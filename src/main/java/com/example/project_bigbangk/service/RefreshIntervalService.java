// Created by Deek
// Creation date 1/27/2022

package com.example.project_bigbangk.service;

import com.example.project_bigbangk.BigBangkApplicatie;
import com.example.project_bigbangk.repository.RootRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class RefreshIntervalService {

    private static final long SAVETY_DURATION = 10000;
    private final Logger logger = LoggerFactory.getLogger(RefreshIntervalService.class);
    private RootRepository rootRepository;

    public RefreshIntervalService(RootRepository rootRepository) {
        super();
        this.rootRepository = rootRepository;
        logger.info("New RefreshIntervalService");
    }

    public long getInitialTimeOut() {
        LocalDateTime mostRecentUpdateTime = rootRepository.getMostRecentUpdateTime();
        if (mostRecentUpdateTime != null) {
            Duration duration = Duration.between(mostRecentUpdateTime, LocalDateTime.now());
            long initialTimeOut = (BigBangkApplicatie.UPDATE_INTERVAL_PRICEUPDATESERVICE - duration.toMillis()) + SAVETY_DURATION;
            return initialTimeOut;
        }
        return 0;
    }
}