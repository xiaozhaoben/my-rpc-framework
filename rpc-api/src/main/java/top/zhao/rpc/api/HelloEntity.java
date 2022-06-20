package top.zhao.rpc.api;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 测试接口api实体类
 *
 *@author xiaozhao
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class HelloEntity implements Serializable {
    private Integer id;
    private String msg;
}
