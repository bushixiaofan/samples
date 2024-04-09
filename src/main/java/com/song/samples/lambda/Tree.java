package com.song.samples.lambda;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author songzeqi
 * @create 2024/4/3 17:39
 * Description:
 */

@Getter
@Setter
class Tree {
    private String key;
    private int val;
    private Tree left, right;

    public Tree(String k, int v, Tree l, Tree r) {
        key = k;
        val = v;
        left = l;
        right = r;
    }
}
