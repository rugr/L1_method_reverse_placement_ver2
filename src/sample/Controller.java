package sample;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.util.*;

public class Controller {
    @FXML
    AnchorPane anchorPaneRoot;

    @FXML
    GridPane gridPaneDistance;

    private Map<String, TextField> mapTextFieldsDistance = new HashMap<>();

    @FXML
    GridPane gridPaneDistanceEnd;

    private Map<String, TextField> mapTextFieldsDistanceEnd = new HashMap<>();

    @FXML
    GridPane gridPaneNumberConnections;

    private Map<String, TextField> mapTextFieldNumberConnections = new HashMap<>();

    @FXML
    GridPane gridPaneSumDistances;

    private Map<String, TextField> mapTextFieldSumDistances = new HashMap<>();

    @FXML
    GridPane gridPaneSumNumberConnections;

    private Map<String, TextField> mapTextFieldSumNumberConnections = new HashMap<>();

    @FXML
    TextField
            // distances:
            s_1, s_2, s_3, s_4,
    // elements order:
    element_1, element_2, element_3, element_4, element_5,
    // distances end:
    s_end_1, s_end_2, s_end_3, s_end_4,
    // elements order end:
    element_end_1, element_end_2, element_end_3, element_end_4, element_end_5;

    private List<String> listDistancesBetweenElements = new ArrayList<>();

    private List<String> listOrderElements = new ArrayList<>();

    private Map<String, TextField> mapTextFieldOrderElementsEnd = new HashMap<>();

    private List<String> listOrderElementsEnd = new ArrayList<>();

    @FXML
    public void initialize() {
        System.out.println("Controller: initialize()");
        setMapTextFieldDistances();
        setMapTextFieldsDistanceEnd();
        setMapTextFieldNumberConnections();
        setMapTextFieldSumDistances();
        setMapSumNumberConnections();
        setMapTextFieldOrderElementsEnd();
    }

    @FXML
    public void calculate() {
        System.out.println("Controller: calculate()");
        setListDistancesBetweenElements(listDistancesBetweenElements, s_1, s_2, s_3, s_4);
        setDistanceEnd();
        setListStringByTextField(listOrderElements, element_1, element_2, element_3, element_4, element_5);
        setDistancesBetweenElements(listOrderElements, listDistancesBetweenElements, mapTextFieldsDistance, "s_");
        setSumForVector(mapTextFieldsDistance, mapTextFieldSumDistances, "s_", "s_sum_");
        setSumForVector(mapTextFieldNumberConnections, mapTextFieldSumNumberConnections, "k_", "k_sum_");
        setOrderElementsEnd();
        setListStringByTextField(listOrderElementsEnd,
                element_end_1, element_end_2, element_end_3, element_end_4, element_end_5);
        setDistancesBetweenElements(listOrderElementsEnd, listDistancesBetweenElements, mapTextFieldsDistanceEnd, "s_end_");
    }

    private void setMapTextFieldDistances() {
        gridPaneDistance.getChildren().forEach(node -> mapTextFieldsDistance.put(node.getId(), (TextField) node));
    }

    private void setMapTextFieldsDistanceEnd() {
        gridPaneDistanceEnd.getChildren().forEach(node -> mapTextFieldsDistanceEnd.put(node.getId(), (TextField) node));
    }

    private void setMapTextFieldNumberConnections() {
        gridPaneNumberConnections.getChildren().forEach(node ->
                mapTextFieldNumberConnections.put(node.getId(), (TextField) node));
    }

    private void setMapTextFieldSumDistances() {
        gridPaneSumDistances.getChildren().forEach(node -> mapTextFieldSumDistances.put(node.getId(), (TextField) node));
    }

    private void setMapSumNumberConnections() {
        gridPaneSumNumberConnections.getChildren().forEach(node ->
                mapTextFieldSumNumberConnections.put(node.getId(), (TextField) node));
    }

    private void setMapTextFieldOrderElementsEnd() {
        mapTextFieldOrderElementsEnd.put(element_end_1.getId(), element_end_1);
        mapTextFieldOrderElementsEnd.put(element_end_2.getId(), element_end_2);
        mapTextFieldOrderElementsEnd.put(element_end_3.getId(), element_end_3);
        mapTextFieldOrderElementsEnd.put(element_end_4.getId(), element_end_4);
        mapTextFieldOrderElementsEnd.put(element_end_5.getId(), element_end_5);
    }

    private void setListDistancesBetweenElements(List<String> listString, TextField textField_1, TextField textField_2,
                                                 TextField textField_3, TextField textField_4) {
        listString.clear();
        listString.add(textField_1.getText());
        listString.add(textField_2.getText());
        listString.add(textField_3.getText());
        listString.add(textField_4.getText());
    }

    private void setDistanceEnd() {
        s_end_1.setText(s_1.getText());
        s_end_2.setText(s_2.getText());
        s_end_3.setText(s_3.getText());
        s_end_4.setText(s_4.getText());
    }

    private void setListStringByTextField(List<String> list, TextField... textFields) {
        list.clear();
        for (TextField textField : textFields) {
            list.add(textField.getText());
        }
    }

    private void setDistancesBetweenElements(List<String> listOrder, List<String> listDistance,
                                             Map<String, TextField> mapTextFields, String prefixKey) {
        for (int i = 0; i < listOrder.size(); i++) {
            Integer sum = 0;
            for (int iNext = i + 1; iNext < listOrder.size(); iNext++) {
                sum = sum + getInt(listDistance.get(iNext - 1));
                String keyForMap = prefixKey + listOrder.get(i)  + "_" + listOrder.get(iNext);
                String keyForMapRevers = prefixKey + listOrder.get(iNext) + "_" + listOrder.get(i);
                mapTextFields.get(keyForMap).setText(sum.toString());
                mapTextFields.get(keyForMapRevers).setText(sum.toString());
            }
        }
    }

    private void setSumForVector(Map<String, TextField> mapWithRows, Map<String, TextField> mapForSetSum,
                                 String prefixRow, String prefixSum) {
        for (int row = 1; row < 6; row++) {
            Integer sum = 0;
            for (int column = 1; column < 6; column++) {
                sum = sum + getInt(mapWithRows.get(prefixRow + row + "_" + column).getText());
            }
            mapForSetSum.get(prefixSum + row).setText(sum.toString());
        }
    }

    private void setOrderElementsEnd() {
        Map<String, TextField> mapStringTextFieldForFindMax = new HashMap<>(mapTextFieldSumNumberConnections);
        Map<String, TextField> mapStringTextFieldForFindMin = new HashMap<>(mapTextFieldSumDistances);
        for (int i = 0; i < 5; i++) {
            Map.Entry<String, TextField> mapEntryMax = findMapEntryWithMaxValue(mapStringTextFieldForFindMax);
            String elementNumber = mapEntryMax.getKey().replace("k_sum_", "");
            mapStringTextFieldForFindMax.remove(mapEntryMax.getKey());

            Map.Entry<String, TextField> mapEntryMin = findMapEntryWithMinValue(mapStringTextFieldForFindMin);
            String elementNumberForReplace = mapEntryMin.getKey().replace("s_sum_", "");
            mapStringTextFieldForFindMin.remove(mapEntryMin.getKey());

            String placeNumber = findPlaceNumber(elementNumberForReplace);

            mapTextFieldOrderElementsEnd.get("element_end_" + placeNumber).setText(elementNumber);


        }
    }

    private String findPlaceNumber(String elementNumberForReplace) {
        String placeNumber = "0";
        for (int i = 0; i < listOrderElements.size(); i++) {
            if (listOrderElements.get(i).equals(elementNumberForReplace)) {
                return Integer.toString(i+1);
            }
        }
        return placeNumber;
    }

    private int getInt(String s) {
        return Integer.valueOf(s);
    }

    private Map.Entry<String, TextField> findMapEntryWithMaxValue(Map<String, TextField> map) {
        Map.Entry<String, TextField> mapEntry = map.entrySet().stream()
                .max(Comparator.comparing(stringTextFieldEntry -> getInt(stringTextFieldEntry.getValue().getText())))
                .orElse(null);
        System.out.println("max entry = " + mapEntry);
        return mapEntry;
    }

    private Map.Entry<String, TextField> findMapEntryWithMinValue(Map<String, TextField> map) {
        Map.Entry<String, TextField> mapEntry = map.entrySet().stream()
                .min(Comparator.comparing(stringTextFieldEntry -> getInt(stringTextFieldEntry.getValue().getText())))
                .orElse(null);
        System.out.println("min entry = " + mapEntry);
        return mapEntry;
    }
}
