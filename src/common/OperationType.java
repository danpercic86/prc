package common;

public enum OperationType implements IOperationFunction {
    ADD("+", IOperationFunction::add), SUBTRACT("-", IOperationFunction::subtract), MULTIPLY("*", IOperationFunction::multiply), DIVIDE("/", IOperationFunction::divide);

    private final String sign;
    private final IOperationFunction function;

    OperationType(String sign, IOperationFunction function) {
        this.sign = sign;
        this.function = function;
    }

    public String getSign() {
        return sign;
    }

    @Override
    public double compute(double left, double right) {
        return function.compute(left, right);
    }
}
