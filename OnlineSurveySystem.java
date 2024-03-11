import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class OnlineSurveySystem extends JFrame {
    private JTextField questionField;
    private JComboBox<String> questionTypeComboBox;
    private JButton addButton;

    public OnlineSurveySystem() {
        setTitle("Survey Creator");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        add(panel);
        placeComponents(panel);

        setVisible(true);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JLabel titleLabel = new JLabel("Create Survey");
        titleLabel.setBounds(150, 10, 100, 25);
        panel.add(titleLabel);

        JLabel questionLabel = new JLabel("Question:");
        questionLabel.setBounds(10, 50, 80, 25);
        panel.add(questionLabel);

        questionField = new JTextField(20);
        questionField.setBounds(100, 50, 200, 25);
        panel.add(questionField);

        JLabel typeLabel = new JLabel("Question Type:");
        typeLabel.setBounds(10, 80, 100, 25);
        panel.add(typeLabel);

        String[] types = {"Multiple Choice", "Text Input", "Rating Scale"};
        questionTypeComboBox = new JComboBox<>(types);
        questionTypeComboBox.setBounds(120, 80, 150, 25);
        panel.add(questionTypeComboBox);

        addButton = new JButton("Add Question");
        addButton.setBounds(150, 120, 120, 25);
        panel.add(addButton);

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String question = questionField.getText();
                String type = (String) questionTypeComboBox.getSelectedItem();
                saveQuestion(question, type);
                questionField.setText("");
            }
        });
    }

    private void saveQuestion(String question, String type) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/survey_system", "username", "password")) {
            String query = "INSERT INTO questions (question_text, question_type) VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, question);
            preparedStatement.setString(2, type);
            preparedStatement.executeUpdate();
            System.out.println("Question added successfully!");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new OnlineSurveySystem();
            }
        });
    }
}
