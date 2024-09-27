package instruction;

import processor.Operand;

public class XorInstruction implements BinaryInstruction{

    @Override
    public void execute(Operand op1, Operand op2) throws Exception
    {

        op1.setValue(op1.getValue() ^ op2.getValue());
    }
}
