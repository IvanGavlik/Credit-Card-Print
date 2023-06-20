package hr.rba.creditcardprint.issuing;


import java.io.File;
import java.util.Optional;


public class FileStorageService {

    public static Optional<File> createFile(String dir) {
        try {
            return Optional.of(new File(dir));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public static void renameFile(String existing, String newName) {
        File oldfile = new File(existing);
        File newfile = new File(newName);

        oldfile.renameTo(newfile);
    }
}
