package de.mallox.datastate;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        List<String> inputs = List.of("error", "NumberFormatException", "10");

        inputs.forEach(Main::run);
    }

    private static void run(final String input) {
        try {
            Integer result = new Workflow<>()
                    .process(DataState.InputState.class, Main::validate)
                    .process(DataState.InputState.class, Main::convert)
                    .process(DataState.ValidationErrorState.class, state -> {
                throw new IllegalArgumentException();
            }).process(DataState.ErrorState.class, state -> {
                throw new RuntimeException(state.getValue());
            })
                    .execute(new DataState.InputState(input), Integer.class);

            System.out.println(result);
        } catch (Exception e) {
            System.out.println(String.format("%s: %s", e.getClass().getName(), e.getMessage()));
        }
    }


    private static DataState<?> validate(final DataState.InputState state) {
        if ("error".equals(state.getValue())) {
            return new DataState.ValidationErrorState();
        }
        return state;
    }

    private static DataState<?> convert(final DataState.InputState state) {
        try {
            return new DataState.OutputState(Integer.valueOf(state.getValue()));
        } catch (NumberFormatException e) {
            return new DataState.ErrorState(e);
        }
    }
}
