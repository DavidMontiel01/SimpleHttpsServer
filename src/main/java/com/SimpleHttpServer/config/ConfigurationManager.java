package com.SimpleHttpServer.config;

import com.SimpleHttpServer.util.Json;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ConfigurationManager {
    //Only need one Config manager at all times
    private static ConfigurationManager myConfigurationManager;
    private static Configuration myCurrentConfiguration;

    private ConfigurationManager() {
    }


    /**
     *
     * @return
     */
    public static ConfigurationManager getInstance(){
        if(myConfigurationManager == null){
            myConfigurationManager = new ConfigurationManager();
        }
        return myConfigurationManager;
    }

    /**
     * Used to load a Configuration by the path provided
     * @param filePath
     */
    public void loadConfigurationFile(String filePath) {
        FileReader fr = null;
        try {
            fr = new FileReader(filePath);
        } catch (FileNotFoundException e) {
            throw new HttpConfigurationException(e);
        }
        StringBuffer sb = new StringBuffer();

        int i;
        try {
            while(( i = fr.read()) != -1){
                sb.append((char)i);
            }
        } catch (IOException e) {
            throw new HttpConfigurationException(e);
        }

        JsonNode conf = null;
        try {
            conf = Json.parse(sb.toString());
        } catch (IOException e) {
            throw new RuntimeException("Error parsing the configuration file");
        }

        try {
            myCurrentConfiguration = Json.fromJson(conf, Configuration.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error parsing the configuration file, Internal");
        }
    }

    /**
     *  returns the current loaded Configuration
     */
    public Configuration getCurrentConfiguration() {
        if(myCurrentConfiguration == null){
            throw new HttpConfigurationException("No Current Configuration Set");
        }
        return myCurrentConfiguration;
    }
}
