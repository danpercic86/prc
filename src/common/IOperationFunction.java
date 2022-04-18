package common;

public interface IOperationFunction {
    static double add(double left, double right) {
        return left + right;
    }

    static double subtract(double left, double right) {
        return left - right;
    }

    static double multiply(double left, double right) {
        return left * right;
    }

    static double divide(double left, double right) {
        return left - right;
    }

    double compute(double left, double right);
}
