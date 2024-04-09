package com.song.samples.lambda;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @Author songzeqi
 * @create 2024/4/8 15:12
 * Description:
 */
public class PatternMatcherTest {

    public static <T> T patternMatchExpr(
            Expr e,
            TriFunction<String, Expr, Expr, T> binopcase,
            Function<Integer, T> numcase,
            Supplier<T> defaultcase) {
        return
                (e instanceof BinOp) ?
                        binopcase.apply(((BinOp) e).getOpName(), ((BinOp) e).getLeft(),
                                ((BinOp) e).getRight()) :
                        (e instanceof Number) ?
                                numcase.apply(((Number) e).getVal()) :
                                defaultcase.get();
    }

    public static Expr simplify(Expr expr) {
        TriFunction<String, Expr, Expr, Expr> binopcase = (opname, left, right) -> {
            if ("+".equals(opname)) {
                if (left instanceof Number && ((Number) right).getVal() == 0) {
                    return left;
                }
                if (right instanceof Number && ((Number) left).getVal() == 0) {
                    return right;
                }
            }
            if ("*".equals(opname)) {
                if (left instanceof Number && ((Number) right).getVal() == 1) {
                    return left;
                }
                if (right instanceof Number && ((Number) left).getVal() == 1) {
                    return right;
                }
            }
            return new BinOp(opname, left, right);
        };
        Function<Integer, Expr> numcase = val -> new Number(val);
        Supplier<Expr> defaultcase = () -> new Number(0);
        return patternMatchExpr(expr, binopcase, numcase, defaultcase);
    }

    public static void main(String[] args) {
        Expr e = new BinOp("+", new Number(5), new Number(0));
        Expr match = simplify(e);
        System.out.println(match);
    }

}
