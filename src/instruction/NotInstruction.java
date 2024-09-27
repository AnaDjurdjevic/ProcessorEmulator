package instruction;

import processor.Operand;

public class NotInstruction implements UnaryInstruction{

    @Override
    public void execute(Operand op)
    {
        op.setValue(~op.getValue());
    }
}
