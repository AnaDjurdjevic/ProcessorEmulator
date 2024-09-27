package instruction;

import java.util.Scanner;

import processor.Operand;

public class InputInstruction implements UnaryInstruction{

    @Override
    public void execute(Operand op)
    {
        System.out.println("Unesite vrijednost:");
        Scanner scan = new Scanner(System.in);
        long input = scan.nextLong();
        op.setValue(input);
    }
}
