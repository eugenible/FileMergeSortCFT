import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.evgeny.merge.FileMerger;
import ru.evgeny.merge.SortingSettings;

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

    @Test
    public void descCorrectSortCorrectIntegerData() throws IOException {
        String tPath = path + "case5" + File.separator;
        String outputFileForTest = tPath + "out.txt";
        String checkFile = tPath + "check.txt";

        SortingSettings parsed = SortingSettings.getInstance(
                new String[]{"-d",
                        "-i",
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
    public void descCorrectSortIncorrectIntegerData() throws IOException {
        String tPath = path + "case6" + File.separator;
        String outputFileForTest = tPath + "out.txt";
        String checkFile = tPath + "check.txt";

        SortingSettings parsed = SortingSettings.getInstance(
                new String[]{"-d",
                        "-i",
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
    public void descIncorrectSortCorrectIntegerData() throws IOException {
        String tPath = path + "case7" + File.separator;
        String outputFileForTest = tPath + "out.txt";
        String checkFile = tPath + "check.txt";

        SortingSettings parsed = SortingSettings.getInstance(
                new String[]{"-d",
                        "-i",
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
    public void descIncorrectSortIncorrectIntegerData() throws IOException {
        String tPath = path + "case8" + File.separator;
        String outputFileForTest = tPath + "out.txt";
        String checkFile = tPath + "check.txt";

        SortingSettings parsed = SortingSettings.getInstance(
                new String[]{"-d",
                        "-i",
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
    public void ascCorrectSortCorrectStringData() throws IOException {
        String tPath = path + "case9" + File.separator;
        String outputFileForTest = tPath + "out.txt";
        String checkFile = tPath + "check.txt";

        SortingSettings parsed = SortingSettings.getInstance(
                new String[]{"-a",
                        "-s",
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
    public void ascCorrectSortIncorrectStringData() throws IOException {
        String tPath = path + "case10" + File.separator;
        String outputFileForTest = tPath + "out.txt";
        String checkFile = tPath + "check.txt";

        SortingSettings parsed = SortingSettings.getInstance(
                new String[]{"-a",
                        "-s",
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
    public void ascIncorrectSortCorrectStringData() throws IOException {
        String tPath = path + "case11" + File.separator;
        String outputFileForTest = tPath + "out.txt";
        String checkFile = tPath + "check.txt";

        SortingSettings parsed = SortingSettings.getInstance(
                new String[]{"-a",
                        "-s",
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
    public void ascIncorrectSortIncorrectStringData() throws IOException {
        String tPath = path + "case12" + File.separator;
        String outputFileForTest = tPath + "out.txt";
        String checkFile = tPath + "check.txt";

        SortingSettings parsed = SortingSettings.getInstance(
                new String[]{"-a",
                        "-s",
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
    public void descCorrectSortCorrectStringData() throws IOException {
        String tPath = path + "case13" + File.separator;
        String outputFileForTest = tPath + "out.txt";
        String checkFile = tPath + "check.txt";

        SortingSettings parsed = SortingSettings.getInstance(
                new String[]{"-d",
                        "-s",
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
    public void descCorrectSortIncorrectStringData() throws IOException {
        String tPath = path + "case14" + File.separator;
        String outputFileForTest = tPath + "out.txt";
        String checkFile = tPath + "check.txt";

        SortingSettings parsed = SortingSettings.getInstance(
                new String[]{"-d",
                        "-s",
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
    public void descIncorrectSortCorrectStringData() throws IOException {
        String tPath = path + "case15" + File.separator;
        String outputFileForTest = tPath + "out.txt";
        String checkFile = tPath + "check.txt";

        SortingSettings parsed = SortingSettings.getInstance(
                new String[]{"-d",
                        "-s",
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
    public void descIncorrectSortIncorrectStringData() throws IOException {
        String tPath = path + "case16" + File.separator;
        String outputFileForTest = tPath + "out.txt";
        String checkFile = tPath + "check.txt";

        SortingSettings parsed = SortingSettings.getInstance(
                new String[]{"-d",
                        "-s",
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
