package instruction;

import processor.Emulator;
import processor.Operand;

public class JEInstruction implements UnaryInstruction{

    //jump if equal
    @Override
    public void execute(Operand op) {
        Emulator emulator = Emulator.getInstance();
        if (emulator.isEqual())
        {
            emulator.getProgramCounter().setValue(op.getValue());
        }

    }

}
