package processor;

public class Constant implements Operand{

    private long value;

    public Constant(long value)
    {
        this.value= value;
    }

    @Override
    public long getValue()
    {
        return this.value;
    }

    @Override
    public void setValue(long value)
    {
        this.value = value;
    }
}
