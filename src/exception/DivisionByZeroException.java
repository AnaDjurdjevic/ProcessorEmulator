package exception;

public class DivisionByZeroException extends Exception{

    public DivisionByZeroException()
    {
        super();
    }

    public DivisionByZeroException(String msg)
    {
        super(msg);
    }

}

