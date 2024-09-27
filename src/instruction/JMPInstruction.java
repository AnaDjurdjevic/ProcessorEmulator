package instruction;

import processor.Operand;
import processor.Emulator;

public class JMPInstruction implements UnaryInstruction{

    @Override
    public void execute(Operand op) {
        Emulator emulator = Emulator.getInstance();
        emulator.getProgramCounter().setValue(op.getValue());
    }
}