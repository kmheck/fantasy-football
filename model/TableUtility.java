package model;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javafx.beans.value.ObservableValue;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableRow;
import javafx.scene.paint.Color;
import javafx.util.Callback;

public class TableUtility
{
    
    public static void formatTableColumnTableCell(TableColumn<Player, Double> tableColumn)
    {
        tableColumn.setCellFactory(new Callback<TableColumn<Player, Double>, TableCell<Player, Double>>() {
            public TableCell<Player, Double> call(TableColumn<Player, Double> p) {
                TableCell<Player, Double> cell = new TableCell<Player, Double>() {
                    @Override
                    public void updateItem(Double item, boolean empty) {
                        super.updateItem(item, empty);
                        setText(empty ? null : getString());
                        setGraphic(null);
                    }

                    private String getString() {
                        String ret = "";
                        if (getItem() != null) {
                            String gi = getItem().toString();
                            NumberFormat df = DecimalFormat.getInstance();
                            df.setMinimumFractionDigits(1);
                            df.setMaximumFractionDigits(1);
                            
                            df.setRoundingMode(RoundingMode.DOWN);

                            ret = df.format(Double.parseDouble(gi));
                        } else {
                            ret = "0.0";
                        }
                        return ret;
                    }
                };

                cell.setStyle("-fx-alignment: top-right;");
                return cell;
            }
        }); // End formatting diff column
        
    }
    
    public static void formatTableColumnDoubleTableCell(TableColumn<OffensiveStats, Double> tableColumn)
    {
        tableColumn.setCellFactory(new Callback<TableColumn<OffensiveStats, Double>, TableCell<OffensiveStats, Double>>() {
            public TableCell<OffensiveStats, Double> call(TableColumn<OffensiveStats, Double> p) {
                TableCell<OffensiveStats, Double> cell = new TableCell<OffensiveStats, Double>() {
                    @Override
                    public void updateItem(Double item, boolean empty) {
                        super.updateItem(item, empty);
                        setText(empty ? null : getString());
                        setGraphic(null);
                    }

                    private String getString() {
                        String ret = "";
                        if (getItem() != null) {
                            String gi = getItem().toString();
                            NumberFormat df = DecimalFormat.getInstance();
                            df.setMinimumFractionDigits(1);
                            df.setMaximumFractionDigits(1);
                            
                            df.setRoundingMode(RoundingMode.DOWN);

                            ret = df.format(Double.parseDouble(gi));
                        } else {
                            ret = "0.0";
                        }
                        return ret;
                    }
                };

                cell.setStyle("-fx-alignment: top-right;");
                return cell;
            }
        }); // End formatting diff column
        
    }

    public static void formatIDPTableColumnDoubleTableCell(TableColumn<IDPStats, Double> tableColumn)
    {
        tableColumn.setCellFactory(new Callback<TableColumn<IDPStats, Double>, TableCell<IDPStats, Double>>() {
            public TableCell<IDPStats, Double> call(TableColumn<IDPStats, Double> p) {
                TableCell<IDPStats, Double> cell = new TableCell<IDPStats, Double>() {
                    @Override
                    public void updateItem(Double item, boolean empty) {
                        super.updateItem(item, empty);
                        setText(empty ? null : getString());
                        setGraphic(null);
                    }

                    private String getString() {
                        String ret = "";
                        if (getItem() != null) {
                            String gi = getItem().toString();
                            NumberFormat df = DecimalFormat.getInstance();
                            df.setMinimumFractionDigits(1);
                            df.setMaximumFractionDigits(1);
                            
                            df.setRoundingMode(RoundingMode.DOWN);

                            ret = df.format(Double.parseDouble(gi));
                        } else {
                            ret = "0.0";
                        }
                        return ret;
                    }
                };

                cell.setStyle("-fx-alignment: top-right;");
                return cell;
            }
        }); // End formatting diff column
        
    }

    public static void formatTeamDefenseTableColumnDoubleTableCell(TableColumn<TeamDefenseStats, Double> tableColumn)
    {
        tableColumn.setCellFactory(new Callback<TableColumn<TeamDefenseStats, Double>, TableCell<TeamDefenseStats, Double>>() {
            public TableCell<TeamDefenseStats, Double> call(TableColumn<TeamDefenseStats, Double> p) {
                TableCell<TeamDefenseStats, Double> cell = new TableCell<TeamDefenseStats, Double>() {
                    @Override
                    public void updateItem(Double item, boolean empty) {
                        super.updateItem(item, empty);
                        setText(empty ? null : getString());
                        setGraphic(null);
                    }

                    private String getString() {
                        String ret = "";
                        if (getItem() != null) {
                            String gi = getItem().toString();
                            NumberFormat df = DecimalFormat.getInstance();
                            df.setMinimumFractionDigits(1);
                            df.setMaximumFractionDigits(1);
                            
                            df.setRoundingMode(RoundingMode.DOWN);

                            ret = df.format(Double.parseDouble(gi));
                        } else {
                            ret = "0.0";
                        }
                        return ret;
                    }
                };

                cell.setStyle("-fx-alignment: top-right;");
                return cell;
            }
        }); // End formatting diff column
        
    }
    
    public static void formatTableView(TableView<Player> table)
    {
        System.out.println("formatTableView() - enter");
        table.setRowFactory(new Callback<TableView<Player>, TableRow<Player>>()
        {
            @Override
            public TableRow<Player> call(TableView<Player> tableView)
            {
                final TableRow<Player> row = new TableRow<Player>()
                {
                    @Override
                    protected void updateItem(Player player, boolean empty)
                    {
                        super.updateItem(player, empty);
                        if (empty || player == null)
                        {
                        	setText(null);
                        	setStyle("");
                        }
                        else
                        {
                            if (player.isDrafted())
                            {
//                                getStyleClass().add("draftedPlayerLightGray");
//                                setTextFill(Color.LIGHTGREY);
//                            	System.out.println("Attempting to set background to red for drafted player: " + player.getDisplayName());
//                                setStyle("-fx-text-color: #D3D3D3;");

//                            	setStyle("-fx-dark-text-color: #D3D3D3;");
//                            	setStyle("-fx-mid-text-color: #D3D3D3;");
//                            	setStyle("-fx-light-text-color: #D3D3D3;");
//                            	getStyleClass().add("strike-through");
                        	    setStyle("-fx-background-color: #FF0000;"); //red
//                        	    setStyle("-fx-strikethrough: true;");
                            }
                            else
                            {
//                                System.out.println("Not styling player: " + player.getDisplayName());
//                            	setTextFill(Color.BLACK);
//                                getStyleClass().add("undraftedPlayerBlack");
                            	setStyle("");
//	                            	getStyleClass().add("undraftedPlayerBlack");
//	                            	System.out.println("Attempting to set player text to black for undrafted player: " + player.getDisplayName());
//	                            	setStyle("-fx-dark-text-color: #000000");
//	                            	setStyle("-fx-mid-text-color: #000000");
//	                            	setStyle("-fx-light-text-color: #000000");
                            }
                        }
                    }
                };
//                row.visibleProperty().addListener(new ChangeListener() {
//                	 @Override
//                     public void changed(ObservableValue observableValue, Object t, Object t1) {
//                         Boolean oldValue = (Boolean)t;
//                         Boolean newValue = (Boolean)t1;
//                         if(oldValue && !newValue) {
//                             row.getStyleClass().remove("draftedPlayerLightGray");
//                         }
//                         if(!oldValue && newValue) {
//                             if(row.getItem().isDrafted())
//                             {
//                                 row.getStyleClass().add("draftedPlayerLightGray");
//                             }
//                         }
//                     }
//               });
                return row;
            }
        });
    }
}
