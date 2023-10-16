package com.CyberChip_Backend.Model;

import lombok.Data;

@Data
public class Result {
    // 查询是否成功
    boolean flag = false;

    // 信息
    String message = "ERROR!";

    // 查询到的数据
    Object data = null;
}
