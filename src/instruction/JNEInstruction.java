package instruction;

import processor.Operand;
import processor.Emulator;

public class JNEInstruction implements UnaryInstruction{

    //jump if not equal
    @Override
    public void execute(Operand op) {
        Emulator emulator = Emulator.getInstance();
        if (!emulator.isEqual())
        {
            emulator.getProgramCounter().setValue(op.getValue());
        }
    }

}
