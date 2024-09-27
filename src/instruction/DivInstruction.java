package instruction;



import exception.DivisionByZeroException;
import processor.Operand;

public class DivInstruction implements BinaryInstruction{

    @Override
    public void execute(Operand op1, Operand op2) throws DivisionByZeroException
    {
        if(op2.getValue() == 0)
            throw new DivisionByZeroException();
        op1.setValue(op1.getValue() / op2.getValue());
    }
}
