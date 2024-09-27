package processor;

public class Register implements Operand{
    private String name;
    private long value;

    public Register(String name)
    {
        this.value = 0;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public long getValue() {
        return value;
    }

    @Override
    public void setValue(long value) {
        this.value = value;
    }


}
