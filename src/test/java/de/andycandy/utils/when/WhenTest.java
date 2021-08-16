package de.andycandy.utils.when;

import java.util.concurrent.atomic.AtomicReference;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class WhenTest {

    private static final Condition CON_TRUE = () -> true;
    private static final Condition CON_FALSE = () -> false;

    @Test
    public void testWhen() {

        final AtomicReference<Boolean> ref = new AtomicReference<>(Boolean.FALSE);

        When.statement()
            .when(CON_FALSE, () -> Assertions.fail("Illegal State"))
            .when(CON_TRUE, () -> ref.set(Boolean.TRUE))
            .createWithFallback(() -> Assertions.fail("Illegal State"))
            .evaluate();

        Assertions.assertThat(ref.get()).isTrue();
    }

    @Test
    public void testWhenWithArg() {

        final AtomicReference<Boolean> ref = new AtomicReference<>(Boolean.FALSE);

        When.statement()
            .when((String a) -> "FALSE".equals(a), () -> Assertions.fail("Illegal State"))
            .when((String a) -> "TRUE".equals(a), () -> ref.set(Boolean.TRUE))
            .createWithFallback(() -> Assertions.fail("Illegal State"))
            .evaluate("TRUE");

        Assertions.assertThat(ref.get()).isTrue();
    }

    @Test
    public void testWhenEqWithArg() {

        final AtomicReference<Boolean> ref = new AtomicReference<>(Boolean.FALSE);

        When.statement()
            .whenEq("FALSE", () -> Assertions.fail("Illegal State"))
            .whenEq("TRUE", () -> ref.set(Boolean.TRUE))
            .createWithFallback(() -> Assertions.fail("Illegal State"))
            .evaluate("TRUE");

        Assertions.assertThat(ref.get()).isTrue();
    }

    @Test
    public void testWhenWithFallback() {

        final AtomicReference<Boolean> ref = new AtomicReference<>(Boolean.FALSE);

        When.statement()
            .when(CON_FALSE, () -> Assertions.fail("Illegal State"))
            .when(CON_FALSE, () -> Assertions.fail("Illegal State"))
            .createWithFallback(() -> ref.set(Boolean.TRUE))
            .evaluate();

        Assertions.assertThat(ref.get()).isTrue();
    }

    @Test
    public void testWhenWithoutFallback() {

        final AtomicReference<Boolean> ref = new AtomicReference<>(Boolean.FALSE);

        When.statement()
            .when(CON_FALSE, () -> Assertions.fail("Illegal State"))
            .when(CON_FALSE, () -> Assertions.fail("Illegal State"))
            .create()
            .evaluate();

        Assertions.assertThat(ref.get()).isFalse();
    }

    @Test
    public void testWhenWithArgWithoutFallback() {

        final AtomicReference<Boolean> ref = new AtomicReference<>(Boolean.FALSE);

        When.statement()
            .when((String a) -> "FALSE".equals(a), () -> Assertions.fail("Illegal State"))
            .create()
            .evaluate("TRUE");

        Assertions.assertThat(ref.get()).isFalse();
    }

    @Test
    public void testWhenMixed() {

        final AtomicReference<Boolean> ref = new AtomicReference<>(Boolean.FALSE);

        When.statement().whenEq("FALSE", () -> Assertions.fail("Illegal State"))
            .when(CON_FALSE, () -> Assertions.fail("Illegal State"))
            .when(CON_TRUE, () -> ref.set(Boolean.TRUE))
            .create()
            .evaluate("TRUE");

        Assertions.assertThat(ref.get()).isTrue();
    }

    @Test
    public void testWhenWithReturn() {

        final Boolean result = When.statementWithReturn(Boolean.class)
            .when(CON_FALSE, () -> Boolean.FALSE)
            .when(CON_TRUE, () -> Boolean.TRUE)
            .createWithIllegalState()
            .evaluate();

        Assertions.assertThat(result).isTrue();
    }

    @Test
    public void testWhenWithReturnWithArg() {

        final Boolean result = When.statementWithReturn(Boolean.class)
            .when((a) -> "FALSE".equals(a), Boolean.FALSE)
            .when((a) -> "TRUE".equals(a), Boolean.TRUE)
            .createWithFallback(Boolean.FALSE)
            .evaluate("TRUE");

        Assertions.assertThat(result).isTrue();
    }

    @Test
    public void testWhenEqWithReturnWithArg() {

        final Boolean result = When.statementWithReturn(Boolean.class)
            .whenEq("FALSE", Boolean.FALSE)
            .whenEq("TRUE", Boolean.TRUE)
            .createWithFallback(Boolean.FALSE)
            .evaluate("TRUE");

        Assertions.assertThat(result).isTrue();
    }

    @Test
    public void testWhenWithReturnMixed() {

        final Boolean result = When.statementWithReturn(Boolean.class)
            .when(CON_FALSE, Boolean.FALSE)
            .when(CON_FALSE, () -> Boolean.FALSE)
            .whenEq("FALSE", () -> Boolean.FALSE)
            .when((String a) -> false, () -> Boolean.FALSE)
            .when(CON_TRUE, () -> Boolean.TRUE)
            .createWithIllegalState()
            .evaluate("ANYSTRING");

        Assertions.assertThat(result).isTrue();
    }

    @Test
    public void testWhenWithReturnMixed2() {

        final Boolean result = When.statementWithReturn(Boolean.class)
            .when((String a) -> false, () -> Boolean.FALSE)
            .when(CON_TRUE, () -> Boolean.TRUE)
            .createWithIllegalState()
            .evaluate("ANYSTRING");

        Assertions.assertThat(result).isTrue();
    }

    @Test
    public void testWhenWithReturnWithFallback() {

        final Boolean result = When.statementWithReturn(Boolean.class)
            .when(CON_FALSE, () -> Boolean.FALSE)
            .createWithFallback(Boolean.TRUE)
            .evaluate();

        Assertions.assertThat(result).isTrue();
    }

    @Test
    public void testWhenWithReturnWithFallback2() {

        final Boolean result = When.statementWithReturn(Boolean.class)
            .when(CON_FALSE, () -> Boolean.FALSE)
            .createWithFallback(() -> Boolean.TRUE)
            .evaluate();

        Assertions.assertThat(result).isTrue();
    }

    @Test
    public void testWhenWithReturnWithIlleagalState() {

        Assertions.assertThatThrownBy(() -> {
            When.statementWithReturn(Boolean.class)
                .when(CON_FALSE, () -> Boolean.FALSE)
                .createWithIllegalState()
                .evaluate();
        }).isInstanceOf(IllegalStateException.class);
    }

    @Test
    public void testWhenWithReturnWithIlleagalStateWithMsg() {

        Assertions.assertThatThrownBy(() -> {
            When.statementWithReturn(Boolean.class)
                .when(CON_FALSE, () -> Boolean.FALSE)
                .createWithIllegalState("AnyMessage")
                .evaluate();
        }).isInstanceOf(IllegalStateException.class).hasMessage("AnyMessage");
    }

    @Test
    public void testWhenEqWithReturnWithArg2() {

        final Boolean result = When.statementWithReturn(Boolean.class)
            .whenEq("FALSE", Boolean.FALSE)
            .when(() -> true, Boolean.TRUE)
            .createWithFallback(Boolean.FALSE)
            .evaluate("TRUE");

        Assertions.assertThat(result).isTrue();
    }

    @Test
    public void testWhenEqWithReturnWithArgWithFallback() {

        final Boolean result = When.statementWithReturn(Boolean.class)
            .whenEq("FALSE", Boolean.FALSE)
            .when(() -> false, Boolean.FALSE)
            .createWithFallback(() -> Boolean.TRUE)
            .evaluate("ANYSTRING");

        Assertions.assertThat(result).isTrue();
    }

    @Test
    public void testWhenEqWithReturnWithArgWithFallback2() {

        final Boolean result = When.statementWithReturn(Boolean.class)
            .whenEq("FALSE", Boolean.FALSE)
            .when(() -> false, Boolean.FALSE)
            .createWithFallback(Boolean.TRUE)
            .evaluate("ANYSTRING");

        Assertions.assertThat(result).isTrue();
    }

    @Test
    public void testWhenEqWithReturnWithArgWithIllegalState() {

        Assertions.assertThatThrownBy(() -> {
            When.statementWithReturn(Boolean.class)
                .whenEq("FALSE", Boolean.FALSE)
                .when(() -> false, Boolean.FALSE)
                .createWithIllegalState()
                .evaluate("ANYSTRING");
        }).isInstanceOf(IllegalStateException.class);
    }

    @Test
    public void testWhenEqWithReturnWithArgWithIllegalStateWithMsg() {

        Assertions.assertThatThrownBy(() -> {
            When.statementWithReturn(Boolean.class)
                .whenEq("FALSE", Boolean.FALSE)
                .when(() -> false, Boolean.FALSE)
                .createWithIllegalState("AnyMessage")
                .evaluate("ANYSTRING");
        }).isInstanceOf(IllegalStateException.class).hasMessage("AnyMessage");
    }
}
