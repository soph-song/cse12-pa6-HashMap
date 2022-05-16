import static org.junit.Assert.*;

import org.junit.*;

public class FileDataTest {
    @Test
    public void testFileData(){
        FileData data = new FileData("file.txt","/home","01/01/2020");
        assertEquals("{Name: "+"file.txt"+", Directory: "+"/home"+", Modified Date: "+"01/01/2020"+"}",data.toString());
   }

   @Test
    public void testFileDataNull(){
        FileData data = new FileData(null,null,null);
        assertEquals("{Name: "+null+", Directory: "+null+", Modified Date: "+null+"}",data.toString());
;    }


}
