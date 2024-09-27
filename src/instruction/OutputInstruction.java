package instruction;

import processor.Operand;

public class OutputInstruction implements UnaryInstruction{


    @Override
    public void execute(Operand op)
    {
        System.out.println(op.getValue());
    }
}
