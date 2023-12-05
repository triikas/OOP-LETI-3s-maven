//import com.formdev.flatlaf.FlatDarculaLaf;
//import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.intellijthemes.*;
import com.itextpdf.io.font.FontProgram;
import com.itextpdf.io.font.FontProgramFactory;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import org.w3c.dom.*;
import org.xml.sax.SAXException;
//import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatNightOwlIJTheme;
import java.io.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
//import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Vector;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;



/**
 *
 * @author Kislitsin Kirill 2308
 * @version 1.2
 *
 */
public class Main extends JFrame{


    private JFrame app;
    private JButton add;
    private JButton del;
    private JButton change;
    private JButton save;
    private JButton search;
    private JTextField searchField;
    private JToolBar toolBar;
    private JScrollPane scroll;
    private JTable list;
    private JMenu menu_file;
    private JMenu menu_edit;
    private JMenu menu_help;
    private JMenuBar menu;
    private JMenuItem menuitem_new;
    private JMenuItem menuitem_open;
    private JMenuItem menuitem_save;
    private JMenuItem menuitem_save_xml;
    private JMenuItem menuitem_save_html;
    private JMenuItem menuitem_save_pdf;
    private JMenuItem menuitem_quit;
    private JComboBox author;
    String [] columns = {"ФИО заказчика", "Автомобиль", "Неисправность", "Дата приёма", "Дата выполнения", "Работник", "Специализация работника"};

    /**
     *
     */
    public void show() {
        UIManager.put("Component.focusWidth", 0);
        UIManager.put( "Button.innerFocusWidth", 0);
        UIManager.put( "Button.arc", 10 );
        UIManager.put( "Component.arc", 10 );
        UIManager.put( "ProgressBar.arc", 10 );
        UIManager.put( "TextComponent.arc", 10 );

        app = new JFrame("Автосервис");
        app.setSize(900, 700);
        app.setLocation(100, 100);
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ImageIcon icon = new ImageIcon("./././img/logo3.png");
        app.setIconImage(icon.getImage());

        save = new JButton(new ImageIcon("./img/save-min-w.png"));
        add = new JButton(new ImageIcon("./img/add-min-w.png"));
        del = new JButton(new ImageIcon("./img/del-min-w.png"));
        change = new JButton(new ImageIcon("./img/open-min-w.png"));
        search = new JButton("Поиск");
        searchField = new JTextField("Введите идентификатор");
//        searchField.putClientProperty( JTextField.placeholderText, "Search" );

        add.setToolTipText("Добавить");
        del.setToolTipText("Удалить");
        save.setToolTipText("Сохранить изменения");
        change.setToolTipText("Изменить");
        search.setToolTipText("Поиск");

        menu = new JMenuBar();
        menu_file = new JMenu("Файл");
        menu_edit = new JMenu("Править");
        menu_help = new JMenu("Помошь");
        menuitem_new = new JMenuItem("Создать");
        menuitem_open = new JMenuItem("Открыть");
        menuitem_save = new JMenuItem("Сохранить как txt/csv");
        menuitem_save_xml = new JMenuItem("Сохранить как xml");
        menuitem_save_html = new JMenuItem("Экспорт в html");
        menuitem_save_pdf = new JMenuItem("Экспорт в pdf");
        menu.add(menu_file);
        menu.add(menu_edit);
        menu.add(menu_help);
        menu_file.add(menuitem_new);
        menu_file.add(menuitem_open);
        menu_file.add(menuitem_save);
        menu_file.add(menuitem_save_xml);
        menu_file.add(menuitem_save_pdf);
        menu_file.add(menuitem_save_html);
        app.setJMenuBar(menu);
//        ActionListener btnAct = new ButtonsListener();
//
////        add.setActionCommand("add");
//        del.setActionCommand("del");
//
////        add.addActionListener(btnAct);
//        del.addActionListener(btnAct);


        toolBar = new JToolBar("Панель инструментов");
        toolBar.add(save);
        toolBar.add(add);
        toolBar.add(del);
        toolBar.add(change);



        app.setLayout(new BorderLayout());
        app.add(toolBar, BorderLayout.NORTH);



        String [][] data = {
                {"Иванов Иван Иванович", "Toyota Camry", "Проблемы с двигателем", "2023-10-05", "2023-10-10", "Петров Петр Петрович", "Механик по двигателю"},
                {"Петрова Анна Сергеевна", "Ford Focus", "Замена масла и фильтров", "2023-09-28", "2023-09-28", "Сидоров Игорь Александрович", "Специалист по обслуживанию"},
                {"Смирнов Сергей Павлович", "Nissan X-Trail", "Проблемы с трансмиссией", "2023-10-15", "2023-10-20", "Ковалев Андрей Николаевич", "Механик по трансмиссии"}
        };
        String [] html = {
                "<!DOCTYPE html>\n<html lang=\"ru\">\n<head>\n<meta charset=\"UTF-8\">\n<title>База автосервиса</title>\n<link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/css/bootstrap.min.css\" rel=\"stylesheet\" integrity=\"sha384-iYQeCzEYFbKjA/T2uDLTpkwGzCiq6soy8tYaI1GyVh/UjpbCx/TYkiZhlZB6+fzT\" crossorigin=\"anonymous\">\n\n</head>\n<body>\n<section class=\"d-flex justify-content-center\"><table class=\"table table-striped-columns\">\n",
                "</table></section>\n<script src=\"https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/js/bootstrap.bundle.min.js\" integrity=\"sha384-u1OknCvxWvY5kfmNBILK2hRnQC3Pr17a+RTT6rIHI7NnikvbZlHgTPOOmMi466C8\" crossorigin=\"anonymous\"></script></body>\n</html>"
        };

        TableModel tmode = new DefaultTableModel(data, columns);

        list = new JTable(tmode);
        DefaultTableModel dtm = (DefaultTableModel) list.getModel();
        scroll = new JScrollPane(list);
        menuitem_open.addActionListener(new ActionListener() {
            /**
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                FileDialog fd = new FileDialog(app, "Открыть", FileDialog.LOAD);
                fd.setDirectory("A:\\leti-projects\\OOP-LETI-3s-maven\\");
//                fd.setFile("*.txt");
                fd.setVisible(true);
                String filename = fd.getFile();
//                System.out.println(filename);
//                System.out.println(Arrays.toString("data.txt".split("\\.")));
                if (filename == null) return;
                if (Objects.equals(filename.split("\\.")[1], "txt") || Objects.equals(filename.split("\\.")[1], "csv")) {
                    try {
                        File file = new File(fd.getDirectory() + filename);
                        FileReader fr = new FileReader(file);
                        BufferedReader reader = new BufferedReader(fr);
                        Vector<String> line = new Vector<String>(Arrays.asList(reader.readLine().split(";")));
                        dtm.setRowCount(0);
                        while (line != null) {
                            dtm.addRow(line);
                            try {
                                line = new Vector<String>(Arrays.asList(reader.readLine().split(";")));
                            } catch (NullPointerException ex) {
                                line = null;
                            }

                        }


                    } catch (FileNotFoundException ex) {

                        JOptionPane.showMessageDialog(app, "Файл не найден");
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(app, "Возникли проблемы с файлом");
                    }
                } else if (Objects.equals(filename.split("\\.")[1], "xml")) {
                    try {
                        DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
                        DocumentBuilder b = f.newDocumentBuilder();
                        Document doc = b.parse(new File(fd.getDirectory() + filename));
                        doc.getDocumentElement().normalize();
                        NodeList data_list = doc.getElementsByTagName("order");
                        dtm.setRowCount(0);
                        for (int i = 0; i < data_list.getLength(); i++) {
                            Node el = data_list.item(i);
                            NamedNodeMap atr = el.getAttributes();
                            String[] lst = {atr.getNamedItem("fio").getNodeValue(), atr.getNamedItem("car").getNodeValue(), atr.getNamedItem("breakdown").getNodeValue(), atr.getNamedItem("date_in").getNodeValue(), atr.getNamedItem("date_out").getNodeValue(), atr.getNamedItem("worker").getNodeValue(),atr.getNamedItem("specialization").getNodeValue()};
                            Vector<String> line = new Vector<String>(Arrays.asList(lst));
                            dtm.addRow(line);
                        }
                    } catch (ParserConfigurationException ex) {
                        throw new RuntimeException(ex);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    } catch (SAXException ex) {
                        throw new RuntimeException(ex);
                    }
                }

            }
        });
        menuitem_save.addActionListener(new ActionListener() {

            @Override
            /**
             *
             * @param e the event to be processed
             */
            public void actionPerformed(ActionEvent e) {
                FileDialog fds = new FileDialog(app, "Сохранить как txt/csv", FileDialog.SAVE);
                fds.setDirectory("A:\\leti-projects\\OOP-LETI-3s-maven\\");
                fds.setFile("*.txt");
                fds.setVisible(true);
                String filesave = fds.getDirectory() + fds.getFile();
                if (filesave == null) return;
                try{
                    BufferedWriter writer = new BufferedWriter(new FileWriter(filesave));
                    for(int i = 0; i < list.getRowCount(); i++){
                        for(int j = 0; j < list.getColumnCount(); j++){
                            writer.write((String)list.getValueAt(i,j));
                            if(j<list.getColumnCount()-1) writer.write(";");
                        }
                        writer.write("\r\n");
                    }
                    writer.close();

                }catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        menuitem_save_xml.addActionListener(new ActionListener() {

            @Override
            /**
             *
             * @param e the event to be processed
             */
            public void actionPerformed(ActionEvent e) {
                String[] source = {"fio", "car", "breakdown", "date_in", "date_out", "worker", "specialization"};
                FileDialog fds = new FileDialog(app, "Сохранить как xml", FileDialog.SAVE);
                fds.setDirectory("A:\\leti-projects\\OOP-LETI-3s\\");
//                fds.setFile("*.txt");
                fds.setVisible(true);
                String filesave = fds.getDirectory() + fds.getFile();
                if (filesave == null) return;
                try {
                    DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
                    DocumentBuilder b = f.newDocumentBuilder();
                    Document doc = b.newDocument();
                    Node data = doc.createElement("data");
                    doc.appendChild(data);
                    for(int i = 0; i < list.getRowCount(); i++){
                        Element order = doc.createElement("order");
                        data.appendChild(order);
                        for (int j = 0; j < 7; j++) {
                            order.setAttribute(source[j], (String)list.getValueAt(i,j));

                        }
                    }
                    Transformer trans= TransformerFactory.newInstance().newTransformer();
                    trans.setOutputProperty(OutputKeys.METHOD, "xml");
                    trans.setOutputProperty(OutputKeys.INDENT, "yes");
                    trans.transform(new DOMSource(doc), new StreamResult(new FileOutputStream(filesave)));

//                    doc.getDocumentElement().normalize();
//                    NodeList data_list = doc.getElementsByTagName("order");
                } catch (ParserConfigurationException ex) {
                    throw new RuntimeException(ex);
                } catch (TransformerConfigurationException ex) {
                    throw new RuntimeException(ex);
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                } catch (TransformerException ex) {
                    throw new RuntimeException(ex);
                }


            }
        });

        menuitem_save_pdf.addActionListener(new ActionListener() {
            @Override
            /**
             *
             */
            public void actionPerformed(ActionEvent e) {
                try {

                    FileDialog fds = new FileDialog(app, "Экспорт в pdf", FileDialog.SAVE);
                    fds.setDirectory("A:\\leti-projects\\OOP-LETI-3s-maven\\");
//                    fds.setFile("*.txt");
                    fds.setVisible(true);
                    String filesave = fds.getDirectory() + fds.getFile();
                    if (filesave == null) return;

                    PdfWriter writer = new PdfWriter(filesave);
                    PdfDocument pdfDoc = new PdfDocument(writer);
                    FontProgram fp = FontProgramFactory.createFont("./././img/Roboto/Roboto-Regular.ttf");
                    PdfFont font = PdfFontFactory.createFont(fp);
                    pdfDoc.addNewPage();
                    com.itextpdf.layout.Document document = new com.itextpdf.layout.Document(pdfDoc);

//                    document.add(new Paragraph("kljlkj"));
                    float [] ColumnWidths = {100F, 100F, 100F, 100F, 100F, 100F, 100F};
                    Table table = new Table(ColumnWidths);
                    for (int i = 0; i < 7; i++) {
                        table.addCell(new Cell().add(new Paragraph(columns[i]).setFont(font).setFontSize(6)));
                    }
                    for(int i = 0; i < list.getRowCount(); i++){
                        for(int j = 0; j < list.getColumnCount(); j++){
                            table.addCell(new Cell().add(new Paragraph((String)list.getValueAt(i,j)).setFont(font).setFontSize(6)));
                        }
                    }
                    document.add(table);
                    document.close();

                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        menuitem_save_html.addActionListener(new ActionListener() {
            @Override
            /**
             *
             */
            public void actionPerformed(ActionEvent e) {
                FileDialog fds = new FileDialog(app, "Экспорт в html", FileDialog.SAVE);
                fds.setDirectory("A:\\leti-projects\\OOP-LETI-3s-maven\\");
                fds.setFile("data.html");
                fds.setVisible(true);
                String filesave = fds.getDirectory() + fds.getFile();
                if (filesave == null) return;
                try{
                    BufferedWriter writer = new BufferedWriter(new FileWriter(filesave));
                    writer.write(html[0]);
                    writer.write("<tr  style=\"background-color: chocolate;\">\n");
                    for (int i = 0; i < 7; i++) {
                        writer.write("<td>" + columns[i] + "</td>");
                    }
                    writer.write("</tr>\r\n");
                    for(int i = 0; i < list.getRowCount(); i++){
                        writer.write("<tr>\n");
                        for(int j = 0; j < list.getColumnCount(); j++){
                            writer.write("<td>" + (String)list.getValueAt(i,j) + "</td>");
                        }
                        writer.write("</tr>\r\n");
                    }
                    writer.write(html[1]);
                    writer.close();

                }catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Th(1, app, dtm, list).start();
                new Th(2, app, dtm, list).start();
                new Th(3, app, dtm, list).start();
            }
        });
        app.add(scroll, BorderLayout.CENTER);


        author = new JComboBox(new String[]{"ФИО заказчика", "Автомобиль", "Неисправность", "Дата приёма", "Дата выполнения", "Работник", "Спеуиализация работника"});

        JPanel filterPanel = new JPanel();
        filterPanel.add(author);
        filterPanel.add(searchField);
        filterPanel.add(search);


        app.add(filterPanel, BorderLayout.SOUTH);


        app.setVisible(true);
    }

    /**
     *
     */
    private class MyException extends Exception {
        public MyException() {
            super ("Вы не ввели идентификатор");
        }
    }

    /**
     *
     * @param bName
     * @throws MyException
     * @throws NullPointerException
     */
    private void check (JTextField bName) throws MyException,NullPointerException {
        String sName = bName.getText();
        if (sName.contains("Введите идентификатор")) 	throw new MyException();
        if (sName.length() == 0) throw new NullPointerException();
    }

    /**
     *
     */
    private void addListeners() {
        search.addActionListener(new ActionListener() {
            /**
             *
             * @param arg0 the event to be processed
             */
            public void actionPerformed(ActionEvent arg0) {
                try {
                    check(searchField);
                }
                catch(NullPointerException ex) {
                    JOptionPane.showMessageDialog(app, "Вы не ввели идентификатор");
                }
                catch(MyException myEx) {
                    JOptionPane.showMessageDialog(null, myEx.getMessage());
                }
            }
        });

    }




    /**
     *
     * @param args
     */
    public static void main(String[] args) {
//        FlatDarkLaf.setup();
        FlatDarkFlatIJTheme.setup();
//        FlatNightOwlIJTheme.setup();
        new Main().show();
    }

}


