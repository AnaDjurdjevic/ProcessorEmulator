package instruction;

import processor.Emulator;
import processor.Operand;

public class JLInstruction implements UnaryInstruction{

    //jump if lower than
    @Override
    public void execute(Operand op) {
        Emulator emulator = Emulator.getInstance();
        if (!emulator.isEqual() && !emulator.isGreater())
        {
            emulator.getProgramCounter().setValue(op.getValue());
        }
    }

}
