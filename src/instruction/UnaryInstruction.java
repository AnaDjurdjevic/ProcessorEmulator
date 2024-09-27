package instruction;

import exception.DivisionByZeroException;
import processor.Operand;

public interface UnaryInstruction extends Instruction{
    public abstract void execute(Operand op);
}
