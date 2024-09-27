package processor;

import java.util.HashMap;

public class Memory implements Operand{

    private long address;
    private byte value;

    public Memory(long addressSpace) {
        this.address = addressSpace;
    }

    @Override
    public long getValue() {
        return this.value;
    }

    @Override
    public void setValue(long value) {
        this.value = (byte) value;
    }

    public long getAddress() {
        return this.address;
    }

    public void setAddress(long addressSpace) {
        this.address = addressSpace;
    }
}
