package de.andycandy.utils.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class When {

    private When() { }

    public static WhenBuilder statement() {
        return new WhenBuilder();
    }

    public static <R> WhenBuilderR<R> statementWithReturn(Class<R> clazz) {
        return new WhenBuilderR<>();
    }

    public static final class WhenBuilder {

        final private InnerWhenBuilder whenBuilder;

        private WhenBuilder() {
            this.whenBuilder = new InnerWhenBuilder();
        }

        private WhenBuilder(InnerWhenBuilder whenBuilder) {
            this.whenBuilder = whenBuilder;
        }

        public WhenBuilder when(Condition condition, Execute execute) {
            return innerWhen(createInnerCondition(condition), createInnerExecute(execute));
        }

        public <A> WhenBuilderA<A> whenEq(A a, Execute execute) {
            return (new WhenBuilderA<A>(whenBuilder)).whenEq(a, execute);
        }

        public <A> WhenBuilderA<A> when(ConditionWithArg<A> condition, Execute execute) {
            return (new WhenBuilderA<A>(whenBuilder)).when(condition, execute);
        }

        public WhenEvaluator createWithFallback(Execute execute) {
            return innerCreate(createInnerExecute(execute));
        }

        public WhenEvaluator create() {
            return innerCreate(() -> null);
        }

        private WhenBuilder innerWhen(InnerCondition condition, InnerExecute execute) {
            return new WhenBuilder(whenBuilder.when(condition, execute));
        }

        private WhenEvaluator innerCreate(InnerExecute innerExecute) {
            return new WhenEvaluator(whenBuilder.createWithFallback(innerExecute));
        }
    }

    public static final class WhenBuilderA<A> {

        private final InnerWhenBuilder whenBuilder;

        private WhenBuilderA(InnerWhenBuilder whenBuilder) {
            this.whenBuilder = new InnerWhenBuilder(whenBuilder);
        }

        public WhenBuilderA<A> when(Condition condition, Execute execute) {
            return innerWhen(createInnerCondition(condition), createInnerExecute(execute));
        }

        public WhenBuilderA<A> whenEq(A a, Execute execute) {
            return innerWhen(createInnerCondition(a), createInnerExecute(execute));
        }

        public WhenBuilderA<A> when(ConditionWithArg<A> condition, Execute execute) {
            return innerWhen(createInnerCondition(condition), createInnerExecute(execute));
        }

        public WhenEvaluatorA<A> createWithFallback(Execute execute) {
            return innerCreate(createInnerExecute(execute));
        }

        public WhenEvaluatorA<A> create() {
            return innerCreate(() -> null);
        }

        private WhenBuilderA<A> innerWhen(InnerCondition condition, InnerExecute execute) {
            return new WhenBuilderA<>(whenBuilder.when(condition, execute));
        }

        private WhenEvaluatorA<A> innerCreate(InnerExecute innerExecute) {
            return new WhenEvaluatorA<>(whenBuilder.createWithFallback(innerExecute));
        }
    }

    public static final class WhenBuilderR<R> {

        private final InnerWhenBuilder whenBuilder;

        private WhenBuilderR() {
            this.whenBuilder = new InnerWhenBuilder();
        }

        private WhenBuilderR(InnerWhenBuilder whenBuilder) {
            this.whenBuilder = whenBuilder;
        }

        public WhenBuilderR<R> when(Condition condition, R r) {
            return innerWhen(createInnerCondition(condition), createInnerExecute(r));
        }

        public WhenBuilderR<R> when(Condition condition, ExecuteWithReturn<R> execute) {
            return innerWhen(createInnerCondition(condition), createInnerExecute(execute));
        }

        public <A> WhenBuilderAR<A, R> whenEq(A a, R r) {
            return (new WhenBuilderAR<A, R>(whenBuilder)).whenEq(a, r);
        }

        public <A> WhenBuilderAR<A, R> when(ConditionWithArg<A> condition, R r) {
            return (new WhenBuilderAR<A, R>(whenBuilder)).when(condition, r);
        }

        public <A> WhenBuilderAR<A, R> whenEq(A a, ExecuteWithReturn<R> execute) {
            return (new WhenBuilderAR<A, R>(whenBuilder)).whenEq(a, execute);
        }

        public <A> WhenBuilderAR<A, R> when(ConditionWithArg<A> condition, ExecuteWithReturn<R> execute) {
            return (new WhenBuilderAR<A, R>(whenBuilder)).when(condition, execute);
        }

        public WhenEvaluatorR<R> createWithFallback(ExecuteWithReturn<R> execute) {
            return innerCreate(createInnerExecute(execute));
        }

        public WhenEvaluatorR<R> createWithFallback(R fallbackR) {
            return innerCreate(createInnerExecute(fallbackR));
        }

        public WhenEvaluatorR<R> createWithIllegalState() {
            return innerCreate(() -> {
                throw new IllegalStateException();
            });
        }

        public WhenEvaluatorR<R> createWithIllegalState(String errorMessage) {
            return innerCreate(() -> {
                throw new IllegalStateException(errorMessage);
            });
        }

        private WhenBuilderR<R> innerWhen(InnerCondition condition, InnerExecute execute) {
            return new WhenBuilderR<>(whenBuilder.when(condition, execute));
        }

        private WhenEvaluatorR<R> innerCreate(InnerExecute innerExecute) {
            return new WhenEvaluatorR<>(whenBuilder.createWithFallback(innerExecute));
        }
    }

    public static final class WhenBuilderAR<A, R> {

        private final InnerWhenBuilder whenBuilder;

        private WhenBuilderAR(InnerWhenBuilder whenBuilder) {
            this.whenBuilder = new InnerWhenBuilder(whenBuilder);
        }

        public WhenBuilderAR<A, R> when(Condition condition, R r) {
            return innerWhen(createInnerCondition(condition), createInnerExecute(r));
        }

        public WhenBuilderAR<A, R> whenEq(A a, R r) {
            return innerWhen(createInnerCondition(a), createInnerExecute(r));
        }

        public WhenBuilderAR<A, R> when(ConditionWithArg<A> condition, R r) {
            return innerWhen(createInnerCondition(condition), createInnerExecute(r));
        }

        public WhenBuilderAR<A, R> when(Condition condition, ExecuteWithReturn<R> execute) {
            return innerWhen(createInnerCondition(condition), createInnerExecute(execute));
        }

        public WhenBuilderAR<A, R> whenEq(A a, ExecuteWithReturn<R> execute) {
            return innerWhen(createInnerCondition(a), createInnerExecute(execute));
        }

        public WhenBuilderAR<A, R> when(ConditionWithArg<A> condition, ExecuteWithReturn<R> execute) {
            return innerWhen(createInnerCondition(condition), createInnerExecute(execute));
        }

        public WhenEvaluatorAR<A, R> createWithFallback(ExecuteWithReturn<R> execute) {
            return innerCreate(createInnerExecute(execute));
        }

        public WhenEvaluatorAR<A, R> createWithFallback(R fallbackR) {
            return innerCreate(createInnerExecute(fallbackR));
        }

        public WhenEvaluatorAR<A, R> createWithIllegalState() {
            return innerCreate(() -> {
                throw new IllegalStateException();
            });
        }

        public WhenEvaluatorAR<A, R> createWithIllegalState(String errorMessage) {
            return innerCreate(() -> {
                throw new IllegalStateException(errorMessage);
            });
        }

        private WhenBuilderAR<A, R> innerWhen(InnerCondition condition, InnerExecute execute) {
            return new WhenBuilderAR<>(whenBuilder.when(condition, execute));
        }

        private WhenEvaluatorAR<A, R> innerCreate(InnerExecute innerExecute) {
            return new WhenEvaluatorAR<>(whenBuilder.createWithFallback(innerExecute));
        }
    }

    private static final class InnerWhenBuilder {

        private final List<WhenPair> list;

        private InnerWhenBuilder() {
            this(Collections.unmodifiableList(Collections.emptyList()));
        }

        private InnerWhenBuilder(InnerWhenBuilder other) {
            this(other.list);
        }

        private InnerWhenBuilder(List<WhenPair> list) {
            this.list = Collections.unmodifiableList(new ArrayList<>(list));
        }

        private InnerWhenBuilder when(InnerCondition condition, InnerExecute execute) {
            return new InnerWhenBuilder(copyAndAppend(list, createWhenPair(condition, execute)));
        }

        private InnerWhenEvaluator createWithFallback(InnerExecute execute) {
            return new InnerWhenEvaluator(list, execute);
        }

        private List<WhenPair> copyAndAppend(List<WhenPair> list, WhenPair newElement) {
            final List<WhenPair> newList = new ArrayList<>(list);
            newList.add(newElement);
            return Collections.unmodifiableList(newList);
        }

        private WhenPair createWhenPair(InnerCondition condition, InnerExecute execute) {
            return new WhenPair(condition, execute);
        }
    }

    public static final class WhenEvaluator {

        private final InnerWhenEvaluator whenEvaluator;

        private WhenEvaluator(InnerWhenEvaluator whenEvaluator) {
            this.whenEvaluator = whenEvaluator;
        }

        public void evaluate() {
            whenEvaluator.evaluate(null);
        }
    }

    public static final class WhenEvaluatorA<A> {

        private final InnerWhenEvaluator whenEvaluator;

        private WhenEvaluatorA(InnerWhenEvaluator whenEvaluator) {
            this.whenEvaluator = whenEvaluator;
        }

        public void evaluate(A a) {
            whenEvaluator.evaluate(a);
        }
    }

    public static final class WhenEvaluatorR<R> {

        private final InnerWhenEvaluator whenEvaluator;

        private WhenEvaluatorR(InnerWhenEvaluator whenEvaluator) {
            this.whenEvaluator = whenEvaluator;
        }

        public R evaluate() {
            return uncheckedReturn(whenEvaluator.evaluate(null));
        }
    }

    public static final class WhenEvaluatorAR<A, R> {

        private final InnerWhenEvaluator whenEvaluator;

        private WhenEvaluatorAR(InnerWhenEvaluator whenEvaluator) {
            this.whenEvaluator = whenEvaluator;
        }

        public R evaluate(A a) {
            return uncheckedReturn(whenEvaluator.evaluate(a));
        }
    }

    @SuppressWarnings("unchecked")
    private static <R> R uncheckedReturn(Object o) {
        return (R)o;
    }

    public static final class InnerWhenEvaluator {

        private final List<WhenPair> list;
        private final InnerExecute fallback;

        private InnerWhenEvaluator(List<WhenPair> list, InnerExecute fallback) {
            this.list = new ArrayList<>(list);
            this.fallback = fallback;
        }

        public Object evaluate(Object a) {
            for(WhenPair pair : list) {
                if (pair.condition.test(a)) {
                    return pair.execute.execute();
                }
            }
            return fallback.execute();
        }
    }

    @SuppressWarnings("unchecked")
    private static <T> InnerCondition createInnerCondition(ConditionWithArg<T> condition) {
        return (a) -> condition.test((T)a);
    }

    private static InnerCondition createInnerCondition(Condition condition) {
        return (a) -> condition.test();
    }

    private static <A> InnerCondition createInnerCondition(A a) {
        return (o) -> Objects.equals(o, a);
    }

    private static InnerExecute createInnerExecute(Execute execute) {
        return () -> {
            execute.execute();
            return null;
        };
    }

    private static <T> InnerExecute createInnerExecute(ExecuteWithReturn<T> execute) {
        return () -> {
            return execute.execute();
        };
    }

    private static <R> InnerExecute createInnerExecute(R r) {
        return () -> {
            return r;
        };
    }

    @FunctionalInterface
    private interface InnerCondition {
        boolean test(Object o);
    }

    @FunctionalInterface
    private interface InnerExecute {
        Object execute();
    }

    private static final class WhenPair {

        private InnerCondition condition;
        private InnerExecute execute;

        private WhenPair(InnerCondition condition, InnerExecute execute) {
            this.condition = condition;
            this.execute = execute;
        }
    }
}
