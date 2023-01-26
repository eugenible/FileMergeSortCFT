import org.junit.Assert;
import org.junit.Test;
import ru.evgeny.FileMerger;
import ru.evgeny.SortingSettings;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileMergerTest {
    public static final String path = String.join(File.separator, "src", "test", "resources",
            "fileMergingResources") + File.separator;

    @Test
    public void ascCorrectSortCorrectData() throws IOException {
        String tPath = path + "case1" + File.separator;
        String outputFileForTest = tPath + "out.txt";
        String checkFile = tPath + "check.txt";

        SortingSettings parsed = SortingSettings.getInstance(
                new String[]{"-i",
                        outputFileForTest,
                        tPath + "1.txt",
                        tPath + "2.txt",
                        tPath + "3.txt"});
        FileMerger merger = new FileMerger(parsed);
        merger.mergeFiles();
        String strFromMergedFiles = Files.readString(Paths.get(outputFileForTest), StandardCharsets.UTF_8);
        String strFromCheckFile = Files.readString(Paths.get(checkFile), StandardCharsets.UTF_8);
        Assert.assertEquals(strFromCheckFile, strFromMergedFiles);
    }

}
