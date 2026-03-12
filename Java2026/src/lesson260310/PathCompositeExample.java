package lesson260310;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PathCompositeExample {

    public static void main(String[] args) {

		Path path = Paths.get("/Users/zaal/git.kiu/Java2026/README.md");

        for (Path part : path) {
			System.out.println(part);
        }
    }
}