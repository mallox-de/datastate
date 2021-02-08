package de.mallox.datastate;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Workflow<D extends DataState<?>>{

    private class WorkflowStep {
        private final Class<D> type;
        private final Function<D, D> function;

        public WorkflowStep(final Class<D> type, final Function<D, D> function) {
            this.type = type;
            this.function = function;
        }

        public Class<? extends D> getType() {
            return type;
        }

        public Function<D, D> getFunction() {
            return function;
        }
    }

    private final List<WorkflowStep> steps = new ArrayList<>();

    public <T extends D> Workflow<D> process(Class<T> type, Function<T, D> function) {
        steps.add(new WorkflowStep((Class<D>) type, (Function<D, D>) function));
        return this;
    }

    public <T> T execute(D dataState, Class<T> returnType) {
        D currentDateState = dataState;

        for (WorkflowStep step: steps) {
            if (step.getType().isInstance(currentDateState)) {
                currentDateState = step.getFunction().apply(currentDateState);
            }
        }

        if (returnType.isInstance(currentDateState.getValue())) {
            return (T) currentDateState.getValue();
        }
        throw new IllegalStateException("Last Process-Type does not fit return-type");
    }
}
