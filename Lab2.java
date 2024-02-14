import java.io.*;

class Lab2 {
    static String rem = "";

    public static void main(String args[]) throws IOException {
        String gp;
        String data;
        BufferedReader Br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("*SENDER SIDE\n");
        System.out.println("Enter Generator polynomial");
        gp = Br.readLine();
        System.out.println("Enter Data");
        data = Br.readLine();

        int lengthgp = gp.length();
        int append = lengthgp - 1;
        String data1 = data;
        for (int i = 0; i < append; i++) {
            data1 = data1 + "0";
        }
        System.out.println(data1);

        Lab2 obj = new Lab2();
        obj.div(data1, gp);

        rem = rem.substring(1, rem.length());
        System.out.println("Final Remainder: " + rem);

        String data2 = data + rem;
        System.out.println("Now the data sent to the reciever side is:  " + data2);

        System.out.println("\n*RECIEVER SIDE\n");

        rem = "";
        obj.div(data2, gp);
        System.out.println("Final Remainder: " + rem);
        System.out.println("So no error");

        System.out.println("Which bit do you want to change in receiver data?");
        int i= Integer.parseInt(Br.readLine());

        // Toggle the bit at the specified index in data2
        StringBuilder modifiedData = new StringBuilder(data2);
        char bit = modifiedData.charAt(i-1);
        if (bit == '0') {
            modifiedData.setCharAt(i-1, '1');
        } else {
            modifiedData.setCharAt(i-1, '0');
        }

        // Store the modified data in data3
        String data3 = modifiedData.toString();
        System.out.println("Modified data: " + data3);
        
        rem ="";
        obj.div(data3, gp);
        System.out.println("Final Remainder: " + rem);
        System.out.println("Error occured");
        
    }

    public void div(String data1, String gp) {
        for (int i = 0; i < gp.length(); i++) {
            if (data1.charAt(i) == gp.charAt(i)) {
                rem = rem + "0";
            } else {
                rem = rem + "1";
            }
        }
        //System.out.println("First remainder=" + rem);
        int gpl = gp.length();
        while (gpl < data1.length()) {
            if (rem.charAt(0) == '0') {
                rem = rem.substring(1) + data1.charAt(gpl);
                gpl++;
            } else {
                String temp = "";
                for (int i = 0; i < gp.length(); i++) {
                    if (rem.charAt(i) == gp.charAt(i)) {
                        temp = temp + "0";
                    } else {
                        temp = temp + "1";
                    }
                }
                rem = temp.substring(1) + data1.charAt(gpl);
                gpl++;
            }
        }
    }
}
