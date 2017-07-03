package robo.cfg;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Utility class that is able to Load a JSON configuration from a file.
 * 
 * @author Mircea Diaconescu
 * @date Apr 8, 2014, 10:48:46 PM
 * 
 */
public class Configuration {
  private JSONObject config = null;

  public Configuration(String pathAndFileName) {
    BufferedReader br;
    try {
      br = new BufferedReader(new FileReader(new File(pathAndFileName)));
      String line = null;
      String fileContent = "";
      while ((line = br.readLine()) != null) {
        fileContent += line;
      }
      this.config = new JSONObject(fileContent);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (JSONException e) {
      e.printStackTrace();
    }
  }

  /**
   * Get the value of a first level property from the configuration file.
   * 
   * @param propertyName
   *          the name of the first level property from the configuration file
   * @return the value of the first level property from the configuration file
   *         that was identified by the property name
   * @throws JSONException
   */
  public JSONObject getObjectPropertyValue(String propertyName)
      throws JSONException {
    return this.config.getJSONObject(propertyName);
  }
}
