package encryptdecrypt;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        String mode = "enc";
        String exportFileName = null;
        String text = null;
        String fileAddress = null;
        String alg = "shift";
        Boolean internalData = false;
        int key = 0;
        char[] inputText;
        char[] outputText;
        Algorithm code = null;

        for (int i = 0; i <= args.length - 1; i++) {
            switch (args[i]) {
                case "-mode":
                    mode = args[i + 1];
                    break;
                case "-key":
                    key = Integer.parseInt(args[i + 1]);
                    break;
                case "-data":
                    text = args[i + 1];
                    internalData = true;
                    break;
                case "-in":
                    fileAddress = args[i + 1];
                    break;
                case "-out":
                    exportFileName = args[i + 1];
                    break;
                case "-alg":
                    alg = args[i + 1];
                    switch (alg) {
                        case "shift":
                            code = new Shift();
                            break;
                        case "unicode":
                            code = new Unicode();
                            break;
                    }
            }
        }

        if (internalData) {
            inputText = Data.getData(text);
        } else {
            inputText = Data.getDataFromFile(fileAddress);
        }

        switch (mode) {
            case "enc":
                outputText = code.encoding(inputText, key);
                break;
            case "dec":
                outputText = code.decoding(inputText, key);
                break;
            default:
                outputText = null;
        }

        if (exportFileName == null) {
            Data.sendData(outputText);
        } else {
            Data.sendDataToFile(outputText, exportFileName);
        }
    }
}


class Data {

    static char[] input;
    public Data(char[] input) {
        this.input = input;
    }

    public static char[] getData(String text) {
        input = text.toCharArray();
        return input;
    }

    public static char[] getDataFromFile(String fileAddress) throws FileNotFoundException {
        File file = new File(fileAddress);
        Scanner scanner = new Scanner(file);
        input = scanner.nextLine().toCharArray();
        scanner.close();
        return input;
    }

    public static void sendData(char[] text) {
        System.out.println(text);
    }

    public static void sendDataToFile(char[] text, String out) throws IOException {
        File fileOut = new File(out);
        FileWriter fileWriter = new FileWriter(fileOut);
        fileWriter.write(text);
        fileWriter.close();
    }
}


interface Algorithm {
    char[] encoding(char[] input, int key);
    char[] decoding(char[] input, int key);
}
class Shift implements Algorithm {

    @Override
    public char[] encoding(char[] input, int key) {
        char[] output = new char[input.length];
        for (int i = 0; i <= input.length - 1; i++) {
            //uppercase circle
            if (input[i] >= 65 && input[i] <= 90) {
                if (input[i] <= 90 - key) {
                    output[i] = (char) (input[i] + key);
                } else {
                    output[i] = (char) (input[i] - 26 + key);
                }
                //lowercase cisrcle
            } else if (input[i] >= 97 && input[i] <= 122) {
                if (input[i] <= 122 - key) {
                    output[i] = (char) (input[i] + key);
                } else {
                    output[i] = (char) (input[i] - 26 + key);
                }
            } else {
                output[i] = input[i];
            }
        }
        return output;
    }

    @Override
    public char[] decoding(char[] input, int key) {
        char[] output = new char[input.length];
        for (int i = 0; i <= input.length - 1; i++) {
            //uppercase circle
            if (input[i] >= 65 && input[i] <= 90) {
                if (input[i] >= 65 + key) {
                    output[i] = (char) (input[i] - key);

                } else {
                    output[i] = (char) (input[i] + 26 - key);
                }
                //lowercase circle
            } else if (input[i] >= 97 && input[i] <= 122) {
                if (input[i] >= 97 + key) {
                    output[i] = (char) (input[i] - key);
                } else {
                    output[i] = (char) (input[i] + 26 - key);
                }
            } else {
                output[i] = input[i];
            }
        }
        return output;
    }
}

class Unicode implements Algorithm {

    @Override
    public char[] encoding(char[] input, int key) {
        char[] output = new char[input.length];
        for (int i = 0; i <= input.length - 1; i++) {
            if (input[i] <= 126 - key) {
                output[i] = (char) (input[i] + key);
            } else {
                output[i] = (char) (input[i] - 94 + key);
            }
        }
        return output;
    }

    @Override
    public char[] decoding(char[] input, int key) {
        char[] output = new char[input.length];
        for (int i = 0; i <= input.length - 1; i++) {
            if (input[i] >= 32 + key) {
                output[i] = (char) (input[i] - key);
            } else {
                output[i] = (char) (input[i] + 94 - key);
            }
        }
        return output;
    }
}









