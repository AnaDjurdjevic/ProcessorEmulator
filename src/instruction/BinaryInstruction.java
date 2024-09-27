package instruction;

import processor.Operand;

public interface BinaryInstruction extends Instruction{
    public abstract void execute(Operand op1, Operand op2) throws Exception;
}
