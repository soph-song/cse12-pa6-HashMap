import static org.junit.Assert.*;

import org.junit.*;

public class FileSystemTest {
    @Test
    public void testFSadd(){
        FileSystem FS = new FileSystem("/Users/sophisong/Documents/GitHub/cse12-pa6-HashMap/pa6-starter/src/input.txt");
        FS.add("hi.txt", "/home", "02/03/2022");
        FileData data = FS.findFile("hi.txt", "/home");
        assertEquals("hi.txt",data.name);
        assertEquals("/home",data.dir);
        assertEquals("02/03/2022",data.lastModifiedDate);
        //test add file with same directory
        boolean result = FS.add("hi.txt","/home","01/03/2022");
        assertEquals(false,result);
    }

    @Test
    public void testFS_find(){
        FileSystem FS = new FileSystem("/Users/sophisong/Documents/GitHub/cse12-pa6-HashMap/pa6-starter/src/input.txt");
        FS.add("hi.txt", "/home", "02/03/2022");
        FS.add("hi.txt","/documents","01/03/2022");
        FileData data = FS.findFile("hi.txt", "/home");
        assertEquals("hi.txt",data.name);
        assertEquals("/home",data.dir);
        assertEquals("02/03/2022",data.lastModifiedDate);
        assertEquals(true,FS.removeByName("mySample.txt"));
    }
}
