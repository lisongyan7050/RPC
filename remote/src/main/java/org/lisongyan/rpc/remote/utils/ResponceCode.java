package org.lisongyan.rpc.remote.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ResponceCode {

    SUCCESS(0034, "success"),
    UN_SUCCESS(0035, "unsuccess"),
    ;

    private Integer code;
    private String msg;

}
