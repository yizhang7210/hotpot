package com.hotpotapp.application.dtos;


import lombok.Value;

@Value
public class SimpleServiceObjectiveResultDto {
    private String objectiveId;
    private String serviceId;
    private String status;
}
