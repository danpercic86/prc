package common;

public class Operation {
    private final long id;
    private final double leftHandSide;
    private final double rightHandSide;
    private final OperationType operationType;
    private final double result;

    public Operation(long id, double leftHandSide, double rightHandSide, OperationType operationType) {
        this.id = id;
        this.leftHandSide = leftHandSide;
        this.rightHandSide = rightHandSide;
        this.operationType = operationType;
        this.result = operationType.compute(leftHandSide, rightHandSide);
    }

    public long getId() {
        return id;
    }

    public double getResult() {
        return result;
    }

    @Override
    public String toString() {
        return "[" + id + "] " + leftHandSide + " " + operationType.getSign() + " " + rightHandSide + " = " + result;
    }
}
