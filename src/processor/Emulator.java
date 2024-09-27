package processor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import instruction.*;

public class Emulator
{
    private static Emulator instance;
    private Map<String, Byte> registers;
    private Register programCounter;
    private Map<String,Byte> binaryInstructions;
    private Map<String,Byte> unaryInstructions;
    private Map<String,BinaryInstruction>binaryInstructionObjectsMap;
    private Map<String,UnaryInstruction>unaryInstructionObjectsMap;
    public static HashMap<Long, Byte> addressSpace;
    public static final long ADRESS_SPACE_START = 0x0;
    private boolean isHalted;
    private boolean isEqual;
    private boolean isGreater;
    private Register rax = new Register("RAX");
    private Register rbx = new Register("RBX");
    private Register rcx = new Register("RCX");
    private Register rdx = new Register("RDX");

    private Emulator()
    {
        registers = new HashMap<>();
        //instructions = new HashMap<>();
        addressSpace = new HashMap<>();
        binaryInstructions = new HashMap<>();
        unaryInstructions = new HashMap<>();
        binaryInstructionObjectsMap = new HashMap<>();
        unaryInstructionObjectsMap = new HashMap<>();
        programCounter = new Register("rip");
        programCounter.setValue(ADRESS_SPACE_START);
        isHalted = false;
        initializeMaps();
    }

    public static Emulator getInstance() {
        if (instance == null) {
            synchronized (Emulator.class) {
                if (instance == null) {
                    instance = new Emulator();
                }
            }
        }
        return instance;
    }

    private void initializeMaps() {
        registers.put(rax.getName(),(byte) 100);
        registers.put(rbx.getName(),(byte) 101);
        registers.put(rcx.getName(),(byte) 102);
        registers.put(rdx.getName(),(byte) 103);

        unaryInstructions.put("HALT",(byte) 0);
        binaryInstructions.put( "ADD",(byte) 1);
        binaryInstructions.put( "AND",(byte) 2);
        binaryInstructions.put("CMP", (byte) 3);
        binaryInstructions.put("DIV", (byte) 4);
        unaryInstructions.put("IN",(byte) 5);
        unaryInstructions.put("JE",(byte) 6);
        unaryInstructions.put("JGE",(byte) 7);
        unaryInstructions.put("JL",(byte) 8);
        unaryInstructions.put("JMP",(byte) 9);
        unaryInstructions.put("JNE", (byte) 10);
        binaryInstructions.put("MOV",(byte) 11);
        binaryInstructions.put("MUL",(byte) 12);
        unaryInstructions.put("NOT", (byte) 13);
        binaryInstructions.put("OR", (byte) 14);
        unaryInstructions.put("OUT",(byte) 15);
        binaryInstructions.put("SUB", (byte) 16);
        binaryInstructions.put("XOR",(byte) 17);

        //unaryInstructionObjectsMap.put("HALT", new HaltInstruction());
        binaryInstructionObjectsMap.put( "ADD", new AddInstruction());
        binaryInstructionObjectsMap.put( "AND", new AndInstruction());
        binaryInstructionObjectsMap.put( "CMP", new CMPInstruction());
        binaryInstructionObjectsMap.put( "DIV", new DivInstruction());
        unaryInstructionObjectsMap.put( "IN", new InputInstruction());
        unaryInstructionObjectsMap.put( "JE", new JEInstruction());
        unaryInstructionObjectsMap.put( "JGE", new JGEInstruction());
        unaryInstructionObjectsMap.put( "JL", new JLInstruction());
        unaryInstructionObjectsMap.put( "JMP", new JMPInstruction());
        unaryInstructionObjectsMap.put( "JNE", new JNEInstruction());
        binaryInstructionObjectsMap.put( "MOV", new MovInstruction());
        binaryInstructionObjectsMap.put( "MUL", new MulInstruction());
        unaryInstructionObjectsMap.put( "NOT", new NotInstruction());
        binaryInstructionObjectsMap.put( "OR", new OrInstruction());
        unaryInstructionObjectsMap.put( "OUT", new OutputInstruction());
        binaryInstructionObjectsMap.put( "SUB", new SubInstruction());
        binaryInstructionObjectsMap.put( "XOR", new XorInstruction());
    }
    public boolean isEqual() {
        return isEqual;
    }

    public void setEqual(boolean isEqual) {
        this.isEqual = isEqual;
    }

    public boolean isGreater() {
        return isGreater;
    }

    public void setGreater(boolean isGreater) {
        this.isGreater = isGreater;
    }

    public Register getProgramCounter() {
        return programCounter;
    }

    public void setProgramCounter(Register programCounter) {
        this.programCounter = programCounter;
    }
    public void resetEmulator()
    {
        programCounter.setValue(ADRESS_SPACE_START);
        addressSpace.clear();
        setEqual(false);
        setGreater(false);
        setHalted(false);
    }

    public Map<String, Byte> getRegisters() {
        return registers;
    }

    public boolean isHalted() {
        return isHalted;
    }

    public void setHalted(boolean isHalted) {
        this.isHalted = isHalted;
    }

    public void loadInstructions(String filePath) throws IOException {
        List<String> lines = Files.readAllLines(Path.of(filePath));
        long address = ADRESS_SPACE_START;

        for (String line : lines) {
            String[] parts = line.split(" ");
            // identifikacija tipa instrukcije
            Byte instructionCode = null;
            if (binaryInstructions.containsKey(parts[0])) {
                instructionCode = binaryInstructions.get(parts[0]);
            } else if (unaryInstructions.containsKey(parts[0])) {
                instructionCode = unaryInstructions.get(parts[0]);
            }

            if (instructionCode == null) {
                throw new IllegalArgumentException("Instruction not recognized: " + parts[0]);
            }
            addressSpace.put(address++, instructionCode);
            for (int i = 1; i < parts.length; i++) {
                final String part = parts[i];
                Byte valueCode;
                if (registers.containsKey(part)) {
                    // registar
                    valueCode = registers.get(part);
                    //get - Returns the value to which the specified key is mapped
                    addressSpace.put(address++, valueCode);
                } else {
                    Constant const1 = new Constant(Integer.parseInt(part));
                    addressSpace.put(address++, (byte)const1.getValue());
                }
            }
        }
    }
    public void execute() {
        programCounter.setValue(ADRESS_SPACE_START);
        while (programCounter.getValue() < addressSpace.size() && !isHalted) {
            Byte instructionCode = addressSpace.get(programCounter.getValue());
            programCounter.setValue(programCounter.getValue() + 1);
            String instructionName = binaryInstructions.containsValue(instructionCode) ? getKeyFromValue(binaryInstructions,instructionCode) : getKeyFromValue(unaryInstructions,instructionCode);

            System.out.println("Executing " + instructionName);

            boolean isBinary = binaryInstructions.containsKey(instructionName);

            Operand op1 = null;
            Operand op2 = null;

            if (isBinary) {
                // Učitavanje prvog operanda
                op1 = loadOperand(programCounter);
                // Učitavanje drugog operanda
                op2 = loadOperand(programCounter);

                //System.out.println("Operating with " + op1 + " and " + op2);
                BinaryInstruction instruction = binaryInstructionObjectsMap.get(instructionName);
                if (instruction != null) {
                    try {
                        instruction.execute(op1, op2);
                    }catch(Exception ex)
                    {
                        ex.printStackTrace();
                    }
                } else {
                    System.out.println("No implementation for instruction: " + instructionName);
                }
            } else if(!isBinary & !"HALT".equals(instructionName)){
                // Učitavanje operanda
                op1 = loadOperand(programCounter);

                //System.out.println("Operating with " + op1);
                UnaryInstruction instruction = (UnaryInstruction) unaryInstructionObjectsMap.get(instructionName);
                if (instruction != null) {
                    instruction.execute(op1);
                } else {
                    System.out.println("No implementation for instruction: " + instructionName);
                }
            }
            else{//HALT instrukcija
                HaltInstruction instruction = new HaltInstruction();
                if (instruction != null) {
                    instruction.execute();
                } else {
                    System.out.println("No implementation for instruction: " + instructionName);
                }
            }
        }
    }
    public static <String, Byte>  String getKeyFromValue(Map<String, Byte> map, Byte value) {
        for (Map.Entry<String, Byte> entry : map.entrySet()) {
            if (entry.getValue().equals(value)) {
                return entry.getKey();  // Vraća ključ kada nađe odgovarajuću vrijednost
            }
        }
        return null;  // Vraća null ako nema takve vrijednosti
    }
    private Operand loadOperand(Register programCounter) {
    Byte operandCode = addressSpace.get(programCounter.getValue());
    programCounter.setValue(programCounter.getValue() + 1);

    String registerName = getKeyFromValue(registers, operandCode);
    if (registerName != null){
        // Ako je operand registar
        switch (registerName) {
            case "RAX":
                return Emulator.getInstance().rax;
            case "RBX":
                return Emulator.getInstance().rbx;
            case "RCX":
                return Emulator.getInstance().rcx;
            case "RDX":
                return Emulator.getInstance().rdx;
            default:
                throw new IllegalArgumentException("Unknown register name: " + registerName);
        }
    } else {
        // Ako je operand konstanta
        return new Constant(operandCode);
    }
}

    public static void main(String[] args) {
        Emulator emu = Emulator.getInstance();
        try {
            emu.loadInstructions("C:\\Users\\Korisnik\\Desktop\\Arh_Proj\\ProcessorEmulator\\src\\instructions.txt");
            emu.execute();
        } catch (IOException e) {
            System.out.println("Error loading instructions: " + e.getMessage());
        }
    }

}