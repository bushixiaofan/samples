package com.song.samples.lambda;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Author songzeqi
 * @create 2024/4/8 15:40
 * Description:
 */
@Data
@AllArgsConstructor
public class Number extends Expr {
    private Integer val;
}
