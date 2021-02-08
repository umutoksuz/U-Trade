package Data;

import Models.User;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class DbSet<T> {
    private ArrayList<T> objects;
    private String filePath;
    private Semaphore lock;

    public DbSet(String fileName, Class<T> cls) {
        filePath = fileName;
        lock = new Semaphore(1);
        objects = new ArrayList<T>();

        File file = new File(fileName);

        try {
            if(file.exists()){
                String content = new String(Files.readAllBytes(Paths.get(fileName)), StandardCharsets.UTF_8);
                Gson gson = new Gson();

                if (content != null && content.length() > 0){
                    JsonArray array = new JsonParser().parse(content).getAsJsonArray();
                    for(JsonElement element : array){
                        objects.add(gson.fromJson(element, cls));
                    }
                }

            } else {
                file.createNewFile();
            }
        } catch (IOException e){
            //TODO: Handle exception
        }
        System.out.println(objects.size());
    }

    public ArrayList<T> objects() {
        try{
            lock.acquire();
        } catch (InterruptedException exception) {
            //TODO: Handle exception
        }
        ArrayList<T> result = new ArrayList<>(objects);
        lock.release();
        return  result;
    }

    public boolean delete(T object) {
        try{
            lock.acquire();
        } catch (InterruptedException exception) {
            //TODO: Handle exception
        }
        boolean deleted = objects.remove(object);
        lock.release();
        return deleted;
    }

    public int add (T object) {
        try{
            lock.acquire();
        } catch (InterruptedException exception) {
            //TODO: Handle exception
        }
        Entity myObject = (Entity) object;
        int id = 1;
        if(objects.size() != 0){
            id = ((Entity)(objects.get(objects.size()-1))).id + 1;
        }
        myObject.setId(id);
        objects.add((T)myObject);
        lock.release();
        return id;
    }

    public T getObjectById(int id) {
        try{
            lock.acquire();
        } catch (InterruptedException exception) {
            //TODO: Handle exception
        }

        T result = null;
        for (T object: objects){
            if(((Entity)object).id == id){
                result = object;
                break;
            }
        }

        lock.release();
        return result;
    }

    public boolean updateObject(T object) {
        try{
            lock.acquire();
        } catch (InterruptedException exception) {
            //TODO: Handle exception
        }
        Entity myObject = (Entity) object;
        boolean isUpdated = false;
        for(T obj: objects) {
            if(((Entity)obj).id == myObject.id) {
                objects.set(objects.indexOf(obj), object);
                isUpdated = true;
                break;
            }
        }
        lock.release();
        return isUpdated;
    }

    public void saveChanges () {
        try{
            lock.acquire();
        } catch (InterruptedException exception) {
            //TODO: Handle exception
        }
        File file = new File(filePath);
        Gson gsonBuilder = new GsonBuilder().create();
        try{
            file.createNewFile();
            PrintWriter outputStream = new PrintWriter(filePath);
            outputStream.print(gsonBuilder.toJson(objects));
            outputStream.flush();
            outputStream.close();
        } catch (IOException e){
            lock.release();
        }
        lock.release();
    }
}
