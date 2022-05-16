import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileSystem {

    MyHashMap<String, ArrayList<FileData>> nameMap;
    MyHashMap<String, ArrayList<FileData>> dateMap;

    // TODO
    public FileSystem() {
    	this.nameMap = new MyHashMap<>();
    	this.dateMap = new MyHashMap<>();
    }

    // TODO
    public FileSystem(String inputFile) {
    	nameMap = new MyHashMap<>();
    	dateMap = new MyHashMap<>();
        try {
            File f = new File(inputFile);
            Scanner sc = new Scanner(f);
            while (sc.hasNextLine()) {
                String[] data = sc.nextLine().split(", ");
                FileData value = new FileData(data[0],data[1],data[2]);
                
                if (nameMap.containsKey(data[0])==false) {
                	ArrayList<FileData> values = new ArrayList<>();
                	values.add(value);
                	nameMap.put(data[0],values);
                }
                else if (nameMap.containsKey(data[0])==true){
                	ArrayList<FileData> existing = nameMap.get(data[0]);
                	existing.add(value);
                	nameMap.replace(data[0], existing);
                }
                if (dateMap.containsKey(data[2])==false) {
                	ArrayList<FileData> values = new ArrayList<>();
                	values.add(value);
                	dateMap.put(data[2],values);
                }
                else if (dateMap.containsKey(data[2])==true) {
                	ArrayList<FileData> existing = dateMap.get(data[2]);
                	existing.add(value);
                	dateMap.replace(data[2],existing);
                }
                
            }
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
    }

    // TODO
    public boolean add(String fileName, String directory, String modifiedDate) {
    	//Check for null
    	if (fileName == null) {
    		fileName ="";
    	}
    	if (directory == null) {
    		directory ="/";
    	}
    	if (modifiedDate == null) {
    		modifiedDate ="01/01/2021";
    	}
    	FileData val = new FileData(fileName,directory,modifiedDate);
    	//if filename already exists
    	if (nameMap.containsKey(fileName)) {
			//Check if same directory exists
    		for(FileData data:nameMap.get(fileName)) {
    			if (data.dir.equals(directory)) {
    				return false;
    			}
    		}
			//update existing key:name
    		ArrayList<FileData> existingN = nameMap.get(fileName);
        	existingN.add(val);
        	dateMap.replace(fileName,existingN);
			
    	}
		else {
			//If adding name for the first time
			ArrayList<FileData> values = new ArrayList<>();
			values.add(val);
			nameMap.put(fileName,values);
		}

		//if date already exists
		if (dateMap.containsKey(modifiedDate)) {
			ArrayList<FileData> existingD = dateMap.get(modifiedDate);
			existingD.add(val);
			dateMap.replace(modifiedDate,existingD);
		}
    	else {
			//adding date first time
			ArrayList<FileData> values = new ArrayList<>();
			values.add(val);
			dateMap.put(modifiedDate, values);
		}
    	
    	
    	return true;
    }

    // TODO
    public FileData findFile(String name, String directory) {
    	if (nameMap.containsKey(name)) {
    		for(FileData data:nameMap.get(name)) {
    			if (data.dir.equals(directory)) {
    				return data;
    			}
    		}
    	}
    	return null;
    }

    // TODO
    public ArrayList<String> findAllFilesName() {
    	ArrayList<String> list = new ArrayList<>();
    	list.addAll(nameMap.keys());
    	return list;

    }

    // TODO
    public ArrayList<FileData> findFilesByName(String name) {
		if (nameMap.containsKey(name)){
			return nameMap.get(name);
		}
		else {
			return new ArrayList<>();
		}
    }

    // TODO
    public ArrayList<FileData> findFilesByDate(String modifiedDate) {
		if (dateMap.containsKey(modifiedDate)) {
			return dateMap.get(modifiedDate);
		}
		else {
			return new ArrayList<>();
		}
    }

    // TODO
    public ArrayList<FileData> findFilesInMultDir(String modifiedDate) {
    	ArrayList<FileData> list = new ArrayList<>();
    	int n =0;
		if (dateMap.containsKey(modifiedDate)) {
			while (n<dateMap.get(modifiedDate).size()) {
			FileData first = dateMap.get(modifiedDate).get(n);
				for(FileData data:dateMap.get(modifiedDate)) {
					if (!data.dir.equals(first.dir) && data.name.equals(first.name)
					&& !list.contains(data)) {
						list.add(data);
					}
				}
			++n;
			}
	}
    	return list;

    }

    // TODO
    public boolean removeByName(String name) {
		boolean result = false;
    	if (nameMap.containsKey(name)) {
			for (FileData data:nameMap.get(name)) {
				String date = data.lastModifiedDate;
				dateMap.get(date).remove(data);
				if (dateMap.get(date).isEmpty()){
					dateMap.remove(date);
				}
			}	
		nameMap.remove(name);
		
    	result = true;
    	}
		
    	return result;

    }

    // TODO
    public boolean removeFile(String name, String directory) {
    	boolean result = false;
    	if (nameMap.containsKey(name)) {
    		for (FileData data:nameMap.get(name)) {
    			String date = data.lastModifiedDate;
    			String dir = data.dir;
    			if(dir.equals(directory)) {
    				nameMap.get(name).remove(data);
    				dateMap.get(date).remove(data);
					//Check if entry is empty and remove
					if (nameMap.get(name).isEmpty()) {
						nameMap.remove(name);
					}
					if (dateMap.get(date).isEmpty()) {
						dateMap.remove(date);
					}
    				result = true;
    			}
    		}
    	}
    	return result;
    }

}
