import java.awt.Color;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MainForm {

  private JPanel mainPanel;
  private JTextField surname;
  private JTextField name;
  private JTextField patronymic;
  private JButton collapse;
  private JLabel errorLabel;

  public MainForm() {
    surname.setText("");
    name.setText("");
    patronymic.setText("");
    collapse.addActionListener(new Action() {
      @Override
      public Object getValue(String key) {
        return null;
      }

      @Override
      public void putValue(String key, Object value) {

      }

      @Override
      public void setEnabled(boolean b) {

      }

      @Override
      public boolean isEnabled() {
        return false;
      }

      @Override
      public void addPropertyChangeListener(PropertyChangeListener listener) {

      }

      @Override
      public void removePropertyChangeListener(PropertyChangeListener listener) {

      }

      @Override
      public void actionPerformed(ActionEvent e) {
        String textButton = collapse.getText();
        if (!checkEmptyField()) {
          if (textButton.equals("Expand")) {
            collapse.setText("Collapse");
            setPropertiesFields(true);
            String[] manData = surname.getText().split(" ");
            surname.setText(manData[0]);
            name.setText(manData[1]);
            patronymic.setText(manData[2]);
          } else {
            collapse.setText("Expand");
            setPropertiesFields(false);
            String textName = name.getText();
            String textSurname = surname.getText();
            String textPatronymic = patronymic.getText();
            String newSurnameText = textSurname + " " + textName + " " + textPatronymic;
            surname.setText(newSurnameText);
          }
          errorLabel.setText("");
        }
        else  {
          errorLabel.setForeground(Color.RED);
          errorLabel.setText("Empty value of fields name or surname!");
        }
      }
    });
  }

  public JPanel getMainPanel()  {
    return mainPanel;
  }

  private boolean checkEmptyField() {
    String textName = name.getText();
    String textSurname = surname.getText();
    return textName.length() == 0 || textSurname.length() == 0;
  }

  private void setPropertiesFields (boolean property) {
    name.setVisible(property);
    patronymic.setVisible(property);
    surname.setEnabled(property);
  }
}
