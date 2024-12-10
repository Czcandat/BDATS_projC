package me.bdats_projc;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;


public class Controller {

    public ScrollPane scrollPane;
    @FXML
    private ChoiceBox<String> prohlType;

    @FXML
    private TextField name, psc, allNo, manNo, womanNo;

    @FXML
    private TableView<Obec> obecTable;
    @FXML
    private TableColumn<Obec, String> nameColumn, pscColumn;
    @FXML
    private TableColumn<Obec, Integer> populationColumn, muziColumn, zenyColumn;

    private PriorityHeap heap;
    private ObservableList<Obec> observableList;

    public void initialize()
    {
        heap = new PriorityHeap(AbstrHeap.Priority.NAME, PriorityHeap.BY_NAME);

        // Nastavení ChoiceBox
        prohlType.setItems(FXCollections.observableArrayList("Podle názvu", "Podle počtu obyvatel"));
        prohlType.setValue("Podle názvu");

        // Nastavení sloupců v tabulce
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        pscColumn.setCellValueFactory(cellData -> cellData.getValue().pscProperty().asObject().asString());
        populationColumn.setCellValueFactory(cellData -> cellData.getValue().populationProperty().asObject());
        muziColumn.setCellValueFactory(cellData -> cellData.getValue().muziPocetProperty().asObject());
        zenyColumn.setCellValueFactory(cellData -> cellData.getValue().zenyPocetProperty().asObject());


        // ObservableList pro dynamické aktualizování tabulky
        observableList = FXCollections.observableArrayList();
        obecTable.setItems(observableList);
    }

    @FXML
    private void onLoadButtonClick()
    {
        ArrayList<Obec> importing  = new ArrayList<>();

        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(new Stage());

        try (BufferedReader br = new BufferedReader(new FileReader(file)))
        {
            String line;

            while ((line = br.readLine()) != null)
            {
                String[] fields = line.split("[;,]");
                if (fields.length != 7) {
                    continue;
                }

                int psc = Integer.parseInt(fields[2]);
                String name = fields[3];
                int muziPocet = Integer.parseInt(fields[4]);
                int zenyPocet = Integer.parseInt(fields[5]);
                int celkemPocet = Integer.parseInt(fields[6]);

                Obec novaObec = new Obec(psc, name, muziPocet, zenyPocet, celkemPocet);
                importing.add(novaObec);
            }

        }
        catch (IOException ignored){}

        heap.build(importing);
        onUpdateButtonClick();
    }

    @FXML
    private void onUpdateButtonClick() {
        if (prohlType.getValue().equals("Podle názvu")) {
            heap.setPriority(AbstrHeap.Priority.NAME);
        } else {
            heap.setPriority(AbstrHeap.Priority.POPULATION);
        }
        heap.rebuildHeap();
        observableList.clear();
        observableList.addAll(heap.getAllItems());
    }

    @FXML
    private void onRandomButtonClick() {
        Random rand = new Random();
        Obec randomObec = new Obec(
                rand.nextInt(10000), "Obec" + rand.nextInt(100),
                rand.nextInt(1000), rand.nextInt(1000),
                rand.nextInt(2000));
        heap.add(randomObec);
        observableList.add(randomObec);
    }

    @FXML
    private void onExportButtonClick()
    {
        String filePath = System.getProperty("user.home") + "/Desktop/BDATS/export.csv";

        ArrayList<Obec> exporting = heap.getAllItems();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {

            writer.write("Name;PSC;Population;Men;Women\n");

            // Write data for each Obec object
            for (Obec obec : exporting) {
                writer.write(String.format("%s;%d;%d;%d;%d\n",
                        obec.getName(),
                        obec.getPsc(),
                        obec.getPopulation(),
                        obec.getMuziPocet(),
                        obec.getZenyPocet()));
            }

            // Notify user on successful export
            System.out.println("Data exported successfully to " + filePath);

        } catch (IOException ignored) {}
    }

    @FXML
    private void onDestroyButtonClick() {
        heap.destroy();
        observableList.clear();
    }

    @FXML
    private void onAddButtonClick()
    {
        String nazev = name.getText();
        int pscValue = Integer.parseInt(psc.getText());
        int celkemObyvatel = Integer.parseInt(allNo.getText());
        int muzi = Integer.parseInt(manNo.getText());
        int zen = Integer.parseInt(womanNo.getText());

        Obec novaObec = new Obec(pscValue, nazev, muzi, zen, celkemObyvatel);
        heap.add(novaObec);
        observableList.add(novaObec);

        name.clear();
        psc.clear();
        allNo.clear();
        manNo.clear();
        womanNo.clear();
    }

    @FXML
    private void onFindButtonClick()
    {
        if (!heap.isEmpty()) {
            Obec max = heap.getMax();
            name.setText(max.getName());
            psc.setText(String.valueOf(max.getPsc()));
            allNo.setText(String.valueOf(max.getPopulation()));
            manNo.setText(String.valueOf(max.getMuziPocet()));
            womanNo.setText(String.valueOf(max.getZenyPocet()));
        }
    }

    @FXML
    private void onRemoveButtonClick()
    {
        if (!heap.isEmpty()) {
            Obec max = heap.removeMax();
            observableList.remove(max);
        }
    }
}
