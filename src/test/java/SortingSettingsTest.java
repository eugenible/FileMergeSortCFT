import org.junit.Assert;
import org.junit.Test;
import ru.evgeny.DataType;
import ru.evgeny.Order;
import ru.evgeny.SortingSettings;

import java.io.File;
import java.io.IOException;

public class SortingSettingsTest {

    public static final String path = String.join(File.separator, "src", "test", "resources",
            "parsingTestResources") + File.separator;

    @Test
    public void argumentsShouldBeParsedCorrectly1Case() throws IOException {
        SortingSettings parsed = SortingSettings.getInstance(
                new String[]{"-s",
                        path + "out.txt",
                        path + "1.txt",
                        path + "2.txt"});

        Assert.assertEquals(Order.ASC, parsed.getOrder());
        Assert.assertEquals(DataType.STRING, parsed.getType());
        File[] checkFiles = new File[]{new File(path + "1.txt"), new File(path + "2.txt")};
        Assert.assertArrayEquals(checkFiles, parsed.getInputFiles().toArray(checkFiles));
        Assert.assertEquals(new File(path + "out.txt"), parsed.getOutputFile());
    }

    @Test
    public void argumentsShouldBeParsedCorrectly2Case() throws IOException {
        SortingSettings parsed = SortingSettings.getInstance(
                new String[]{"-i",
                        path + "out.txt",
                        path + "1.txt",
                        path + "2.txt"});

        Assert.assertEquals(Order.ASC, parsed.getOrder());
        Assert.assertEquals(DataType.INTEGER, parsed.getType());
        File[] checkFiles = new File[]{new File(path + "1.txt"), new File(path + "2.txt")};
        Assert.assertArrayEquals(checkFiles, parsed.getInputFiles().toArray(checkFiles));
        Assert.assertEquals(new File(path + "out.txt"), parsed.getOutputFile());
    }

    @Test
    public void argumentsShouldBeParsedCorrectly3Case() throws IOException {
        SortingSettings parsed = SortingSettings.getInstance(
                new String[]{"-a",
                        "-i",
                        path + "out.txt",
                        path + "1.txt",
                        path + "2.txt"});

        Assert.assertEquals(Order.ASC, parsed.getOrder());
        Assert.assertEquals(DataType.INTEGER, parsed.getType());
        File[] checkFiles = new File[]{new File(path + "1.txt"), new File(path + "2.txt")};
        Assert.assertArrayEquals(checkFiles, parsed.getInputFiles().toArray(checkFiles));
        Assert.assertEquals(new File(path + "out.txt"), parsed.getOutputFile());
    }

    @Test
    public void argumentsShouldBeParsedCorrectly4Case() throws IOException {
        SortingSettings parsed = SortingSettings.getInstance(
                new String[]{"-d",
                        "-s",
                        path + "out.txt",
                        path + "1.txt",
                        path + "2.txt"});

        Assert.assertEquals(Order.DESC, parsed.getOrder());
        Assert.assertEquals(DataType.STRING, parsed.getType());
        File[] checkFiles = new File[]{new File(path + "1.txt"), new File(path + "2.txt")};
        Assert.assertArrayEquals(checkFiles, parsed.getInputFiles().toArray(checkFiles));
        Assert.assertEquals(new File(path + "out.txt"), parsed.getOutputFile());
    }

    @Test
    public void exceptionShouldBeThrown1() {
        IOException exc = Assert.assertThrows(IOException.class, () ->
                SortingSettings.getInstance(
                        new String[]{"-a",
                                path + "out.txt",
                                path + "1.txt"})
        );
        Assert.assertEquals(exc.getMessage(), "Type of data to sort was not specified");
    }

    @Test
    public void exceptionShouldBeThrown2() {
        IOException exc = Assert.assertThrows(IOException.class, () -> SortingSettings.getInstance(
                new String[]{"-s",
                        path + "out.txt"}));
        Assert.assertEquals(exc.getMessage(), "Too few parameters");
    }

    @Test
    public void exceptionShouldBeThrown3() {
        IOException exc = Assert.assertThrows(IOException.class, () ->
                SortingSettings.getInstance(
                        new String[]{"-a",
                                "-i",
                                path + "out.txt"}));
        Assert.assertEquals(exc.getMessage(), "There must be at least one input file to produce output file");
    }
}
