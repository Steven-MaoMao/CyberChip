package com.CyberChip_Backend.Model;

import lombok.Data;

import java.util.List;

@Data
public class CyberChip {
    private String roomName = null;
    private String roomManager = null;
    private String roomPassword = null;
    private boolean gaming = false;
    private List<Player> memberList = null;
    private int chipCount = 0;
    private int pot = 0;
    private List<Integer> sidePotList = null;
}
