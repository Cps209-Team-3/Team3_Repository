//-----------------------------------------------------------
//File:   CreateCommand.java
//Desc:   This program extends WorldCommand to contain a execute
// and undo method for the CreateCommand
//----------------------------------------------------------- 

package model;

public interface Serializer {
    String serialization();
    void deserialization(String data);
}