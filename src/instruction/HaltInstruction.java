package instruction;

import processor.Emulator;

public class HaltInstruction implements Instruction{
    public void execute()
    {
        Emulator processor = Emulator.getInstance();
        processor.setHalted(true);
    }
}
