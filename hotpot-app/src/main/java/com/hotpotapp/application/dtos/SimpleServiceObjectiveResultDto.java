package com.hotpotapp.application.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SimpleServiceObjectiveResultDto {
    private String objectiveId;
    private String serviceId;
    private String status;
}
