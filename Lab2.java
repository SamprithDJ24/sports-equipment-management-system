import java.io.*;

class Lab2 {
    public static void main(String args[]) throws IOException {
        String gp;
        String data;
        BufferedReader Br = new BufferedReader(new InputStreamReader(System.in));
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
    }

    public void div(String data1, String gp) {
        String rem = "";

        // Initial division
        for (int i = 0; i < gp.length(); i++) {
            if (data1.charAt(i) == gp.charAt(i)) {
                rem = rem + "0";
            } else {
                rem = rem + "1";
            }
        }
        System.out.println("Remainder=" + rem);

        int gpl = gp.length();
        while (gpl < data1.length()) {
            if (rem.charAt(0) == '0') {
                rem = rem.substring(1) + data1.charAt(gpl);
                gpl++;
            } else {
                // Correcting the loop for updating the remainder
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
        System.out.println("Final Remainder=" + rem);
    }
}
