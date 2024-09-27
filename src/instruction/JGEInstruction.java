package instruction;

import processor.Emulator;
import processor.Operand;

public class JGEInstruction implements UnaryInstruction{

    //jump if greater than or equal
    @Override
    public void execute(Operand op) {
        Emulator emulator = Emulator.getInstance();
        if (emulator.isEqual() || emulator.isGreater())
        {
            emulator.getProgramCounter().setValue(op.getValue());
        }
    }

}
