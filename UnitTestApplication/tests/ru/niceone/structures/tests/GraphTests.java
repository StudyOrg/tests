package ru.niceone.structures.tests;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import ru.niceone.structures.BadFormatException;
import ru.niceone.structures.MatrixGraph;
import ru.niceone.structures.SearchResultTest;

import java.io.File;
import java.util.Arrays;

public class GraphTests {

    private static MatrixGraph<String> graph;
    private static SearchResultTest result;
    @Rule
    public ExpectedException exception = ExpectedException.none();

    @BeforeClass
    public static void init() {
        graph = new MatrixGraph<>();
    }

    @Test
    public void load_data() {
        exception.expect(BadFormatException.class);
        graph.setGraph(Arrays.asList(Datasets.dataset_bad), ru.niceone.util.Arrays.asListTwoDimensional(Datasets.matrix_bad));
    }

    @Test
    public void graph_searching_founded() {
        Integer[] expectedPathOne = {0, 1, 5, 2, 3, 6};
        graph.setGraph(Arrays.asList(Datasets.dataset_big), ru.niceone.util.Arrays.asListTwoDimensional(Datasets.matrix_big));

        result = graph.breadthFirstSearch("Eta");

        Assert.assertTrue(result.founded);
        Assert.assertArrayEquals(result.path.toArray(), expectedPathOne);
    }

    @Test
    public void graph_searching_not_founded() {
        Integer[] expectedPathOne = {0, 1, 5, 2, 3, 6, 4, 7, 8};
        graph.setGraph(Arrays.asList(Datasets.dataset_big), ru.niceone.util.Arrays.asListTwoDimensional(Datasets.matrix_big));

        result = graph.breadthFirstSearch("Foo");

        Assert.assertFalse(result.founded);
        Assert.assertArrayEquals(result.path.toArray(), expectedPathOne);
    }

    @Test
    public void program_nodes_visiting_founded() {
        String[] expectedNodesOne = {"A", "B", "B", "B", "C", "D", "F", "G", "H", "G", "H", "D", "F", "G", "D", "E"};
        graph.setGraph(Arrays.asList(Datasets.dataset_small), ru.niceone.util.Arrays.asListTwoDimensional(Datasets.matrix_small));

        result = graph.breadthFirstSearch("Fee");

        Assert.assertArrayEquals(result.programNodes.toArray(), expectedNodesOne);
    }

    @Test
    public void program_nodes_visiting_not_founded() {
        String[] expectedNodesTwo = {"A", "B", "B", "B", "C", "D", "F", "G", "H", "G", "H", "D", "F", "G", "D", "F", "G", "J"};
        graph.setGraph(Arrays.asList(Datasets.dataset_small), ru.niceone.util.Arrays.asListTwoDimensional(Datasets.matrix_small));

        result = graph.breadthFirstSearch("Bee");

        Assert.assertArrayEquals(result.programNodes.toArray(), expectedNodesTwo);
    }

    @Test
    public void load_from_bad_json() throws BadFormatException {
        exception.expect(BadFormatException.class);
        exception.expectMessage("Rows count in matrix doesn't equal to values count");
        graph.loadFromJson(new File("../Datasets/bad_data_long_row.json"));

        exception.expectMessage("Cols count in matrix doesn't equal to values count");
        graph.loadFromJson(new File("../Datasets/bad_data_too_much_values.json"));

        exception.expectMessage("'values' field missed or doesn't contains values list");
        graph.loadFromJson(new File("../Datasets/bad_data_bad_json.json"));

        exception.expectMessage("'matrix' field missed or doesn't contains 2d matrix");
        graph.loadFromJson(new File("../Datasets/bad_data_no_matrix.json"));
    }

    @Test
    public void load_from_good_json() {
        graph.loadFromJson(new File("../Datasets/data.json"));
        // Test case one - search in the middle of graph
        Integer[] expectedPathOne = {0, 1, 4, 2, 3, 5};
        result = graph.breadthFirstSearch("F");

        Assert.assertTrue(result.founded);
        Assert.assertArrayEquals(result.path.toArray(), expectedPathOne);

        // Test case two - search in the end of graph
        Integer[] expectedPathTwo = {0, 1, 4, 2, 3, 5, 6, 11, 12, 9, 10, 7, 8};
        result = graph.breadthFirstSearch("I");

        Assert.assertTrue(result.founded);
        Assert.assertArrayEquals(result.path.toArray(), expectedPathTwo);
    }
}
