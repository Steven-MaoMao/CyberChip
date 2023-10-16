package com.CyberChip_Backend.Model;

import lombok.Data;

@Data
public class Player {
    private String name = null;
    private int bankRoll = 0;
    private int thisRoundAnte = 0;
    private int allAnte = 0;
}
