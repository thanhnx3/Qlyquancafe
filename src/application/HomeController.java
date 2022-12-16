package application;

import java.util.List;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class HomeController {
	@FXML
	private TableView<Cafe> table = new TableView<Cafe>();
	@FXML
	private TextField textId;
	@FXML
	private TextField textName;
	@FXML
	private TextField textSize;
	@FXML
	private TextField textPrice;
	@FXML
	private TextField textNote;
	@FXML
	private Button btnAdd;
	@FXML
	private Button btnUpdate;
	@FXML
	private Button btnDelete;
	@FXML
	private Label message;
	
	HRMDB hrmDB = new HRMDB("jdbc:ucanaccess://D:/Java/Qlquancafe/database/hrm.accdb", "", "");

	@SuppressWarnings("unchecked")
	@FXML
	public void initialize() {
		Platform.runLater(() -> {
			
			TableColumn<Cafe, String> cafeIdColumn = (TableColumn<Cafe, String>) table.getVisibleLeafColumn(0);
			cafeIdColumn.setCellValueFactory(new PropertyValueFactory<Cafe, String>("id"));

			TableColumn<Cafe, String> nameColumn = (TableColumn<Cafe, String>) table.getVisibleLeafColumn(1);
			nameColumn.setCellValueFactory(new PropertyValueFactory<Cafe, String>("name"));
			
			TableColumn<Cafe, String> sizeColumn = (TableColumn<Cafe, String>) table.getVisibleLeafColumn(2);
			sizeColumn.setCellValueFactory(new PropertyValueFactory<Cafe, String>("size"));
			
			TableColumn<Cafe, String> priceColumn = (TableColumn<Cafe, String>) table.getVisibleLeafColumn(3);
			priceColumn.setCellValueFactory(new PropertyValueFactory<Cafe, String>("price"));
			
			TableColumn<Cafe, String> noteColumn = (TableColumn<Cafe, String>) table.getVisibleLeafColumn(4);
			noteColumn.setCellValueFactory(new PropertyValueFactory<Cafe, String>("note"));

			List<Cafe> cafeList = hrmDB.getCafeList(); // DB

			
			ObservableList<Cafe> obsCafeList = FXCollections.observableArrayList(cafeList);
			
			table.setItems(obsCafeList);
		});
	}

	@SuppressWarnings("unchecked")
	public void onClickAddButton(ActionEvent event) {
		Cafe cf = new Cafe(textId.getText(), textName.getText(), textSize.getText(), textPrice.getText(), textNote.getText());

		boolean addResult = hrmDB.addCafe(cf); // DB

		if (addResult) { 
			table.getItems().add(cf);
			message.setText("Add successfull!");
		} else {
			message.setText("Error add to database");
		}
	}


	public void onClickUpdateButton(ActionEvent event) {
		int selectedIndex = table.getSelectionModel().getSelectedIndex();
		Cafe selectedCafe = table.getItems().get(selectedIndex);

		selectedCafe.setId(textId.getText());
		selectedCafe.setName(textName.getText());
		selectedCafe.setSize(textSize.getText());
		selectedCafe.setPrice(textPrice.getText().toString());
		selectedCafe.setNote(textNote.getText());

		if (selectedIndex >= 0) { 
			boolean updateResult = hrmDB.updateCafe(selectedCafe);
			if (updateResult) {
				table.getItems().set(selectedIndex, selectedCafe);
				message.setText("Update successfull!");
			} else {
					message.setText("Error update to database!");
			}
		}
	}

	public void onClickDeleteButton(ActionEvent event) {
		int selectedIndex = table.getSelectionModel().getSelectedIndex();
		Cafe cf = table.getItems().get(selectedIndex);
		if (selectedIndex >= 0) {
			boolean deleteResult = hrmDB.deleteCafe(cf.getId()); // DB

			if (deleteResult) { 
				table.getItems().remove(selectedIndex);
				message.setText("Delete successfull!");
			} else {
				message.setText("Error delete to database!");
			}
		}
	}

	public void onClickRow() {
		textId.setText(table.getSelectionModel().getSelectedItem().getId());
		textName.setText(table.getSelectionModel().getSelectedItem().getName());
		textSize.setText(table.getSelectionModel().getSelectedItem().getSize());
		textPrice.setText(table.getSelectionModel().getSelectedItem().getPrice());
		textNote.setText(table.getSelectionModel().getSelectedItem().getNote());
	}
}
