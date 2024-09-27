package instruction;

import processor.Operand;
import processor.Emulator;

public class CMPInstruction implements BinaryInstruction{

    @Override
    public void execute(Operand op1, Operand op2) throws Exception {
        Emulator processor = Emulator.getInstance();
        processor.setEqual(op1.getValue() == op2.getValue());
        processor.setGreater(op1.getValue() > op2.getValue());
    }

}
