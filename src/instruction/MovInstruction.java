package instruction;

import processor.Operand;

public class MovInstruction implements BinaryInstruction{

    @Override
    public void execute(Operand op1, Operand op2)
    {
        op1.setValue(op2.getValue());
    }
}
