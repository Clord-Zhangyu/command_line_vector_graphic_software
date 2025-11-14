package hk.edu.polyu.comp.comp2021.clevis.model;

import hk.edu.polyu.comp.comp2021.clevis.controller.Clevis;
import hk.edu.polyu.comp.comp2021.clevis.controller.Console;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

public class ModelTest {
    String helpOutput = "All commands are as follows, you can enter the head to get the detailed parameters:\n" +
            "boundingbox name\n" +
            "circle      name x y r\n" +
            "delete      name\n" +
            "group       name names...\n" +
            "help\n" +
            "intersect   name name\n" +
            "line        name x1 y1 x2 y2\n" +
            "list        name\n" +
            "listAll\n" +
            "move        name x y\n" +
            "quit\n" +
            "rectangle   name x y w h\n" +
            "redo\n" +
            "shapeAt     x y\n" +
            "square      name x y l\n" +
            "undo\n" +
            "ungroup     name\n";
    String listMyGroupOutput = "Type: Group\n" +
            "Name: myGroup\n" +
            "GroupMembers:\n" +
            "\tType: Rectangle\n" +
            "\tName: testRect\n" +
            "\tX: 30.00\n" +
            "\tY: 0.00\n" +
            "\tWidth: 5.00\n" +
            "\tHeight: 5.00\n" +
            "\n" +
            "\tType: Circle\n" +
            "\tName: testCircle\n" +
            "\tX: 0.00\n" +
            "\tY: 0.00\n" +
            "\tRadius: 5.00";
    String listAllOutput = "Type: Square\n" +
            "Name: testSquare\n" +
            "X: 40.00\n" +
            "Y: 0.00\n" +
            "Length: 5.00\n" +
            "\n" +
            "Type: Rectangle\n" +
            "Name: testRect\n" +
            "X: 30.00\n" +
            "Y: 0.00\n" +
            "Width: 5.00\n" +
            "Height: 5.00\n" +
            "\n" +
            "Type: Line\n" +
            "Name: testLine\n" +
            "X1: 20.00\n" +
            "Y1: 0.00\n" +
            "X2: 25.00\n" +
            "Y2: 0.00\n" +
            "\n" +
            "Type: Circle\n" +
            "Name: testCircle\n" +
            "X: 0.00\n" +
            "Y: 0.00\n" +
            "Radius: 5.00";
    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Test
    public void testModelOperation() throws IOException {
        File htmlFile = tempFolder.newFile("test.html");
        File txtFile = tempFolder.newFile("test.txt");
        Clevis.Boost(new String[]{
                "-html", htmlFile.getAbsolutePath(),
                "-txt", txtFile.getAbsolutePath()
        });
        assertEquals("Succeed", Clevis.Execute("circle testCircle 0 0 5"));
        assertEquals("-5.0 5.0 10.0 10.0", Clevis.Execute("boundingbox testCircle"));
        assertEquals("Key \"testCircle\" already exists.", Clevis.Execute("circle testCircle 10 0 5"));
        assertEquals("Succeed", Clevis.Execute("line testLine 20 0 25 0"));
        assertEquals("Succeed", Clevis.Execute("rectangle testRect 30 0 5 5"));
        assertEquals("Succeed", Clevis.Execute("square testSquare 40 0 5"));
        assertEquals("Succeed", Clevis.Execute("circle temporary 0 0 5"));
        assertEquals("\"nonExistent\" does not exist.", Clevis.Execute("intersect testCircle nonExistent"));
        assertEquals("\"nonExistent\" does not exist.", Clevis.Execute("intersect nonExistent testCircle"));
        assertEquals("True", Clevis.Execute("intersect testCircle temporary"));
        assertEquals("False", Clevis.Execute("intersect testCircle testSquare"));
        assertEquals("False", Clevis.Execute("intersect testLine testRect"));
        assertEquals("Succeed", Clevis.Execute("move temporary 20 30"));
        assertEquals("Succeed", Clevis.Execute("delete temporary"));
        assertEquals("Succeed", Clevis.Execute("group myGroup testCircle  testRect"));
        assertEquals("\"testCircle\" unaccessible.", Clevis.Execute("move testCircle 20 30"));
        assertEquals("False", Clevis.Execute("intersect testSquare myGroup"));
        assertEquals("\"testRect\" unaccessible.", Clevis.Execute("group myGroup1 myGroup testRect"));
        assertEquals("Succeed", Clevis.Execute("group myGroup1 myGroup testSquare"));
        assertEquals("\"myGroup\" unaccessible.", Clevis.Execute("ungroup myGroup"));
        assertEquals("\"testCircle\" is not a group.", Clevis.Execute("ungroup testCircle"));
        assertEquals("\"testCircle\" unaccessible.", Clevis.Execute("delete testCircle"));
        assertEquals("Succeed", Clevis.Execute("ungroup myGroup1"));
        assertEquals("\"nonExistent\" does not exist.", Clevis.Execute("delete nonExistent"));
        assertEquals("\"nonExistent\" does not exist.", Clevis.Execute("move nonExistent 10 10"));
        assertEquals("one member can not form a group.", Clevis.Execute("group group testCircle"));
        assertEquals("\"nonExistent\" does not exist.", Clevis.Execute("group group nonExistent testCircle"));
        assertEquals("\"nonExistent\" does not exist.", Clevis.Execute("ungroup nonExistent"));
        assertEquals(helpOutput, Clevis.Execute("help"));
        assertEquals("myGroup", Clevis.Execute("shapeAt 0 0"));
        assertEquals("No result", Clevis.Execute("shapeAt -10 0"));
        assertEquals(listMyGroupOutput, Clevis.Execute("list myGroup"));
        assertEquals("Succeed", Clevis.Execute("ungroup myGroup"));
        assertEquals("testCircle", Clevis.Execute("shapeAt 0 0"));
        assertEquals("No result", Clevis.Execute("shapeAt 10 0"));
        assertEquals("testLine", Clevis.Execute("shapeAt 20 0"));
        assertEquals("testRect", Clevis.Execute("shapeAt 30 0"));
        assertEquals("testSquare", Clevis.Execute("shapeAt 40 0"));
        assertEquals("\"nonExistent\" does not exist.", Clevis.Execute("list nonExistent"));
        assertEquals(listAllOutput, Clevis.Execute("listAll"));
        assertEquals("There is no command to redo.", Clevis.Execute("redo"));
        assertEquals("Succeed", Clevis.Execute("undo"));
        assertEquals("Succeed", Clevis.Execute("undo"));
        assertEquals("Succeed", Clevis.Execute("undo"));
        assertEquals("Succeed", Clevis.Execute("undo"));
        assertEquals("Succeed", Clevis.Execute("undo"));
        assertEquals("Succeed", Clevis.Execute("undo"));
        assertEquals("Succeed", Clevis.Execute("undo"));
        assertEquals("Succeed", Clevis.Execute("undo"));
        assertEquals("Succeed", Clevis.Execute("undo"));
        assertEquals("Succeed", Clevis.Execute("undo"));
        assertEquals("Succeed", Clevis.Execute("undo"));
        assertEquals("There is no command to undo.", Clevis.Execute("undo"));
        assertEquals("Succeed", Clevis.Execute("redo"));
        Data.Clear();
        assertTrue(true);
    }
}