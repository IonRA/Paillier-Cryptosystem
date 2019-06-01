package paillersystem;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.Scanner;

public class PaillierEncryption {

    public static void main(String[] args)
    {
        PaillierCryptosystem pc = new PaillierCryptosystem();
        StringBuilder sb = new StringBuilder("");
        String line;

        String fileName;

        if (args.length == 1)
            fileName = args[0];
        else
        {
            Scanner s = new Scanner(System.in);
            fileName = s.nextLine();
            s.close();
        }

        try
        {
            FileReader fr = new FileReader(args[0]);
            BufferedReader br = new BufferedReader(fr);

            while ((line = br.readLine()) != null)
                sb.append(line);

            System.out.println(pc.encrypt(sb.toString()));
            System.out.println();
            List<BigInteger> l = pc.decrypt(pc.encrypt());
           
            for (BigInteger bi : l)
                System.out.print(Character.valueOf((char)bi.intValue()));
            br.close();
            fr.close();
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Error occured while opening file");
            e.printStackTrace();
        }
        catch (IOException e)
        {
            System.out.println("Error occured while reading from file");
            e.printStackTrace();
        }
    }
}
