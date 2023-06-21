package hr.rba.creditcardprint.issuing;


import java.io.File;
import java.util.Optional;

/**
 * The FileStorageService class provides utility methods for file storage operations.
 */
public final class FileStorageService {

    /**
     * Utility class no need for public constructor.
     */
    private FileStorageService() { }

    /**
     * Creates a file at the specified directory path.
     *
     * @param dir should include name.
     * @return An Optional<File> object representing the created file
     */
    public static Optional<File> createFile(final String dir) {
        try {
            return Optional.of(new File(dir));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    /**
     * Renames an existing file to a new name.
     *
     * @param existing The path (including name) of the existing file.
     * @param newName  The path (including name)  for the file.
     * */
    public static void renameFile(final String existing, final String newName) {
        File oldfile = new File(existing);
        File newfile = new File(newName);

        oldfile.renameTo(newfile);
    }
}
