package de.mallox.datastate;

public class DataState<T> {
    private final T value;

    public DataState(final T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public static class InputState extends DataState<String> {
        public InputState(final String value) {
            super(value);
        }
    }

    public static class OutputState extends DataState<Integer> {
        public OutputState(final Integer value) {
            super(value);
        }
    }

    public static class ErrorState extends DataState<Exception> {
        public ErrorState(final Exception value) {
            super(value);
        }
    }

    public static class ValidationErrorState extends DataState<Void> {
        public ValidationErrorState() {
            super(null);
        }
    }
}
