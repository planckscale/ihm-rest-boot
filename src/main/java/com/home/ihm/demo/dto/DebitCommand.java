package com.home.ihm.demo.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DebitCommand {
    private Long amount;
    private Long advertiserId;
}
