package top.zhao.rpc.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author xiaozhao
 */
@AllArgsConstructor
@Getter
public enum PackageType {

    REQUEST_PACK(0),
    RESPONSE_PACK(1);

    private final int code;

}
