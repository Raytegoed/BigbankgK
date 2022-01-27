// Created by Deek
// Creation date 1/27/2022

package com.example.project_bigbangk.controller;

import com.example.project_bigbangk.BigBangkApplicatie;
import com.example.project_bigbangk.model.Client;
import com.example.project_bigbangk.service.MarketPlaceService;
import com.example.project_bigbangk.service.RefreshIntervalService;
import com.example.project_bigbangk.service.Security.AuthenticateService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class UpdateIntervalController {
    private final Logger logger = LoggerFactory.getLogger(MarketPlaceController.class);
    private final AuthenticateService authenticateService;
    private final MarketPlaceService marketPlaceService;
    private RefreshIntervalService refreshIntervalService;
    private final ObjectMapper MAPPER = new ObjectMapper();

    public UpdateIntervalController(AuthenticateService authenticateService, MarketPlaceService marketPlaceService, RefreshIntervalService refreshIntervalService) {
        super();
        this.authenticateService = authenticateService;
        this.marketPlaceService = marketPlaceService;
        this.refreshIntervalService = refreshIntervalService;
        logger.info("New UpdateIntervalController");
    }

    @GetMapping("/updateInterval")
    @ResponseBody
    public ResponseEntity<String> getUpdateInterval(@RequestHeader String authorization) {
        if (!authorization.split(" ")[0].equals("Bearer")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Got to login");
        }
        if (authenticateService.authenticate(authorization)) {
            long initialTimeOut = refreshIntervalService.getInitialTimeOut();
            try {
                ObjectNode jsonBody = MAPPER.createObjectNode();
                jsonBody.put("initialTimeOut", initialTimeOut)
                        .put("updateInterval", String.valueOf(BigBangkApplicatie.UPDATE_INTERVAL_PRICEUPDATESERVICE));
                return ResponseEntity.status(HttpStatus.OK).body(MAPPER.writeValueAsString(jsonBody));
            } catch (JsonProcessingException e) {
                logger.error(e.getMessage());
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("token expired");
    }

    @GetMapping("/clientName")
    @ResponseBody
    public ResponseEntity<String> getNameClient(@RequestHeader String authorization) {
        if (!authorization.split(" ")[0].equals("Bearer")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Got to login");
        }
        if (authenticateService.authenticate(authorization)) {
            try {
                Client client = authenticateService.getClientFromToken(authorization);
                String name = String.format("%s %s %s", client.getFirstName(), client.getInsertion(), client.getLastName());
                ObjectNode jsonBody = MAPPER.createObjectNode();
                jsonBody.put("clientName", name);
                return ResponseEntity.status(HttpStatus.OK).body(MAPPER.writeValueAsString(jsonBody));
            } catch (JsonProcessingException e) {
                logger.error(e.getMessage());
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("token expired");
    }
}