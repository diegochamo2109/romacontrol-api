package com.romacontrol.romacontrol.dto;

import lombok.Data;

@Data
public class AuthRequest {
    private String dni;
    private String pin;
}
