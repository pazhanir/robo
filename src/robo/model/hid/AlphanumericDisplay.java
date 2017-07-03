package robo.model.hid;

import org.json.JSONException;
import org.json.JSONObject;

import robo.model.event.hid.HidInputEvent;

/**
 * Represents the implementation of a generic Alphanumeric Display and the basic
 * functionality. This class by itself is generic and does not represents a
 * specific hardware implementation, but rather a generic implementation.
 * 
 * NOTE: this class can (and must) be extended by specific implementation
 * classes that represents real hardware, e.g. displays that are driven by
 * HD47780 chip produced by Hitachi. The reason for which this class is not
 * abstract, is that it can be used to emulate a generic display (and provide a
 * visualization for example) in case that no decision was taken on which real
 * alphanumeric display (hardware) to be used.
 * 
 * @author Mircea Diaconescu
 * @date Apr 8, 2014, 11:04:23 PM
 * 
 */
public class AlphanumericDisplay extends Hid {
  // the number of rows of the display (starts with 0 from top)
  private int numberOfRows;
  // the number of columns of the display (starts with 0 from left)
  private int numberOfCols;
  // the text rows
  private char[][] rows;

  /**
   * Create a new instance with a known number of rows and columns.
   * 
   * @param numberOfRows
   *          the number of rows
   * @param numberOfCols
   *          the number of columns
   */
  public AlphanumericDisplay(String name, int numberOfRows, int numberOfCols) {
    super(name);
    this.numberOfRows = numberOfRows;
    this.numberOfCols = numberOfCols;
    rows = new char[numberOfRows][numberOfCols];
    for (int i = 0; i < numberOfRows; i++) {
      for (int j = 0; j < numberOfCols; j++) {
        rows[i][j] = ' ';
      }
    }
  }

  /**
   * Create a new instance with a known number of rows and columns.
   * 
   * @param numberOfRows
   *          the number of rows
   * @param numberOfCols
   *          the number of columns
   */
  public AlphanumericDisplay(int numberOfRows, int numberOfCols) {
    this.numberOfRows = numberOfRows;
    this.numberOfCols = numberOfCols;
    rows = new char[numberOfRows][numberOfCols];
    for (int i = 0; i < numberOfRows; i++) {
      for (int j = 0; j < numberOfCols; j++) {
        rows[i][j] = ' ';
      }
    }
  }

  /**
   * Update the text on the display. If the text is outside display area, then
   * will just be ignored. If the column value is a negative number, then the
   * text part until position 0 is lost, but the rest of the text will be
   * displayed.
   * 
   * @param row
   *          the row where the text will be placed
   * @param column
   *          the column where the text will start from
   * @param text
   *          a string representing the text to display
   * @param clearRowToEnd
   *          if this parameter is true, then the row text starting with the
   *          column position is cleared up to the end of the row.
   */
  public void updateText(int row, int column, String text, boolean clearRowToEnd) {
    int i = 0, n = 0;
    // outside display boundaries
    if (row < 0 || row >= this.numberOfRows) {
      return;
    }
    if (column < 0) {
      i = Math.abs(column + 1);
    }
    if (i >= this.numberOfCols) {
      return;
    }
    n = Math.min(text.length(), this.numberOfCols);
    for (; i < n; i++) {
      this.rows[row][i] = text.charAt(i);
    }

    // generate update event with the changes
    JSONObject data = new JSONObject();
    try {
      data.put("command", Command.UPDATE_TEXT.name());
      data.put("row", row);
      data.put("column", column);
      if (clearRowToEnd) {
        data.put("command", Command.UPDATE_TEXT_CLEAR_ROW_TO_END.name());
      } else {
        data.put("command", Command.UPDATE_TEXT.name());
      }
      data.put("text", text);
    } catch (JSONException e) {
      e.printStackTrace();
    }
    HidInputEvent event = new HidInputEvent(this, data);
    this.trigger(event);
  }

  /**
   * Update the text on the display. If the text is outside display area, then
   * will just be ignored. If the column value is a negative number, then the
   * text part until position 0 is lost, but the rest of the text will be
   * displayed.
   * 
   * @param row
   *          the row where the text will be placed
   * @param column
   *          the column where the text will start from
   * @param text
   *          a string representing the text to display
   */
  public void updateText(int row, int column, String text) {
    this.updateText(row, column, text, false);
  }

  /**
   * Update the text on the display. If the text is outside display area, then
   * will just be ignored. If the column value is a negative number, then the
   * text part until position 0 is lost, but the rest of the text will be
   * displayed.
   * 
   * @param row
   *          the row where the text will be placed
   * @param column
   *          the column where the text will start from
   * @param number
   *          a floating point number representing the text to display
   */
  public void updateText(int row, int column, double number) {
    this.updateText(row, column, "" + number);
  }

  /**
   * Update the text on the display. If the text is outside display area, then
   * will just be ignored. If the column value is a negative number, then the
   * text part until position 0 is lost, but the rest of the text will be
   * displayed.
   * 
   * @param row
   *          the row where the text will be placed
   * @param column
   *          the column where the text will start from
   * @param number
   *          a floating point number representing the text to display
   * @param clearRowToEnd
   *          if this parameter is true, then the row text starting with the
   *          column position is cleared up to the end of the row.
   */
  public void updateText(int row, int column, double number,
      boolean clearRowToEnd) {
    this.updateText(row, column, "" + number, clearRowToEnd);
  }

  /**
   * Update the text on the display. If the text is outside display area, then
   * will just be ignored. If the column value is a negative number, then the
   * text part until position 0 is lost, but the rest of the text will be
   * displayed.
   * 
   * @param row
   *          the row where the text will be placed
   * @param column
   *          the column where the text will start from
   * @param number
   *          an integer number representing the text to display
   */
  public void updateText(int row, int column, int number) {
    this.updateText(row, column, "" + number);
  }

  /**
   * Update the text on the display. If the text is outside display area, then
   * will just be ignored. If the column value is a negative number, then the
   * text part until position 0 is lost, but the rest of the text will be
   * displayed.
   * 
   * @param row
   *          the row where the text will be placed
   * @param column
   *          the column where the text will start from
   * @param number
   *          an integer number representing the text to display
   * @param clearRowToEnd
   *          if this parameter is true, then the row text starting with the
   *          column position is cleared up to the end of the row.
   */
  public void updateText(int row, int column, int number, boolean clearRowToEnd) {
    this.updateText(row, column, "" + number, clearRowToEnd);
  }

  /**
   * Clear the text on the specified row.
   * 
   * @param row
   *          the row to be cleared
   */
  public void clearRow(int row) {
    JSONObject data = new JSONObject();
    try {
      data.put("command", Command.CLEAR_ROW.name());
      data.put("row", row);
    } catch (JSONException e) {
      e.printStackTrace();
    }
    HidInputEvent event = new HidInputEvent(this, data);
    this.trigger(event);
  }

  /**
   * Delete all the characters shown on the display (replace them
   */
  public void clear() {
    for (int i = 0; i < numberOfRows; i++) {
      for (int j = 0; j < numberOfCols; j++) {
        rows[i][j] = ' ';
      }
    }
    JSONObject data = new JSONObject();
    try {
      data.put("command", Command.CLEAR_ALL.name());
    } catch (JSONException e) {
      e.printStackTrace();
    }
    HidInputEvent event = new HidInputEvent(this, data);
    this.trigger(event);
  }

  /**
   * Define a set of commands accepted by such a display
   * 
   * @author Mircea Diaconescu
   * 
   */
  private enum Command {
    // clear all display content
    CLEAR_ALL,
    // clear a specified row
    CLEAR_ROW,
    // update text
    UPDATE_TEXT,
    // update text and delete the content to the end of the row
    UPDATE_TEXT_CLEAR_ROW_TO_END
  }
}