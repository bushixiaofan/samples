package com.song.samples.lambda;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Author songzeqi
 * @create 2024/4/8 15:38
 * Description:
 */
@Data
@AllArgsConstructor
public class BinOp extends Expr {

    private String opName;

    private Expr left;

    private Expr right;
}
