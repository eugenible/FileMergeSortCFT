import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.evgeny.FileMerger;
import ru.evgeny.SortingSettings;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class FileMergerTest {
    public static final String path = String.join(File.separator, "src", "test", "resources",
            "fileMergingResources") + File.separator;

    // Удаляет output файлы, которые будут созданы для каждого теста отдельно
    @BeforeClass
    public static void deleteAllOutputFiles() throws IOException {
        try (Stream<Path> paths = Files.walk(Paths.get(path))) {
            paths.filter(x -> x.getFileName().toString().equals("out.txt"))
                    .forEach(path1 -> {
                        try {
                            Files.delete(path1);
                        } catch (IOException e) {
                            System.out.println(e.getMessage());
                        }
                    });
        }
    }

    @Test
    public void ascCorrectSortCorrectIntegerData() throws IOException {
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

    @Test
    public void ascCorrectSortIncorrectIntegerData() throws IOException {
        String tPath = path + "case2" + File.separator;
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

    @Test
    public void ascIncorrectSortCorrectIntegerData() throws IOException {
        String tPath = path + "case3" + File.separator;
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

    @Test
    public void ascIncorrectSortIncorrectIntegerData() throws IOException {
        String tPath = path + "case4" + File.separator;
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
