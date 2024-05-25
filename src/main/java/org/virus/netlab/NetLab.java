package org.virus.netlab;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import jiconfont.icons.font_awesome.FontAwesome;
import jiconfont.swing.IconFontSwing;
import v4j.Component.Window.Window;
import com.formdev.flatlaf.FlatDarkLaf;
import java.awt.Component;
import java.awt.Container;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import raven.toast.Notifications;
import raven.toast.ui.ToastNotificationPanel;
import v4j.Component.Field.SmoothField;
import v4j.MessagePane.GlassPanePopup;
import v4j.Util.Draggable;

/**
 *
 * @author virus
 */
public class NetLab extends Window {

    private static long startTime;
    private static final int MEMORY_UPDATE_INTERVAL = 1000; // 1 second
    private static final int ARC = 35;
    private static final int MAX_OUI = 50956;
    

    /**
     * Creates new form NetLab
     */
    public NetLab() {
        startTime = System.currentTimeMillis();

        IconFontSwing.register(FontAwesome.getIconFont());

        initComponents();

        ip2dec_ipField.putClientProperty("JTextField.showClearButton", true);
        passwordField.putClientProperty("JComponent.showRevealButton", true);


        parentPanel.setArc(ARC + 1);
        panelBorder.setArc(ARC);
        menuContainer.setArc(ARC);
        ip2decContainer.setArc(ARC);
        networkStatisticsContainer.setArc(ARC);
        ip2cidrContainer.setArc(ARC);
        statisticsContainer.setArc(ARC);
        ip2binContainer.setArc(ARC);
        ip2decContainer.setArc(ARC);
        randomIPv4Container.setArc(ARC);
        randomMACContainer.setArc(ARC);
        IPv4ValidatorContainer.setArc(ARC);
        dec2binContainer.setArc(ARC);
        bin2decContainer.setArc(ARC);
        cidr2ipContainer.setArc(ARC);
        ip2cidrContainer.setArc(ARC);
        IPv4ValidatorContainer.setArc(ARC);
        MACValidatorContainer.setArc(ARC);
        servicePortContainer.setArc(ARC);
        statusCodeContainer.setArc(ARC);
        aclContainer.setArc(ARC);

        sourceCode.setCursor(new Cursor(Cursor.HAND_CURSOR));
        sourceCode.setIcon(IconFontSwing.buildIcon(FontAwesome.GITHUB, 30, Color.decode("#222222")));

        arrowRightLabel.setIcon(IconFontSwing.buildIcon(FontAwesome.ARROW_RIGHT, 10, Color.decode("#CFCFCF")));
        arrowRightLabel2.setIcon(IconFontSwing.buildIcon(FontAwesome.ARROW_RIGHT, 10, Color.decode("#CFCFCF")));
        arrowRightLabel4.setIcon(IconFontSwing.buildIcon(FontAwesome.ARROW_RIGHT, 10, Color.decode("#CFCFCF")));

        setLocationRelativeTo(null);

        quit.setCursor(new Cursor(Cursor.HAND_CURSOR));
        quit.setIcon(IconFontSwing.buildIcon(FontAwesome.TIMES, 15, Color.decode("#cfcfcf")));

        updateTimeInfo();
        // Schedule the task to run every 1 second
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new UpdateTimeTask(), 1000, 1000);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        startUpdateTimer();

        AddressValidator(ip2dec_ipField, "ip");
        AddressValidator(IPv4ValidatorField, "ip");
        AddressValidator(ip2bin_ipField, "ip");
        AddressValidator(randomIPv4Field, "ip");
        AddressValidator(ip2cidr_ipField, "ip");
        AddressValidator(randomMACField, "mac");
        AddressValidator(MACValidatorField, "mac");
        AddressValidator(dec2bin_decField, "decimal");
        AddressValidator(dec2bin_binField, "binary");
        AddressValidator(bin2dec_decField, "decimal");
        AddressValidator(bin2dec_binField, "binary");
        
        List<SmoothField> focusableTextFields = getFocusableTextFields(getContentPane());
        for (SmoothField textField : focusableTextFields) {
            textField.setSelectionColor(new Color(196, 196, 196, 48));
            
            if (!(textField.isEditable())) {
                textField.setCaretColor(new Color(0, 0, 0, 0));
            }
        }
        
        new Draggable(this);
        GlassPanePopup.install(this);
        Notifications.getInstance().setJFrame(this);
        
        instanceIDLabel.setText(String.valueOf(UUID.randomUUID()));
    }

    private List<SmoothField> getFocusableTextFields(Container container) {
        List<SmoothField> focusableTextFields = new ArrayList<>();
        for (Component component : container.getComponents()) {
            if (component instanceof SmoothField && component.isFocusable()) {
                focusableTextFields.add((SmoothField) component);
            } else if (component instanceof Container) {
                focusableTextFields.addAll(getFocusableTextFields((Container) component));
            }
        }
        return focusableTextFields;
    }

    public static void AddressValidator(SmoothField field, String type) {
        field.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                validate(e.getDocument());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                validate(e.getDocument());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                validate(e.getDocument());
            }

            private void validate(Document doc) {
                SwingUtilities.invokeLater(() -> {
                    Color empty = Color.decode("#84C5EE");
                    Color valid = Color.decode("#A6FF4D");
                    Color invalid = Color.decode("#FF4D52");

                    int currentLength = doc.getLength();
                    String value = field.getText();
                    boolean isValid = false;

                    if (type == "ip") {
                        isValid = new IPv4().is(value);
                    } else if (type == "mac") {
                        isValid = new MAC().is(value);
                    } else if (type == "binary") {
                        isValid = new Binary().is(value);
                    } else if (type == "decimal") {
                        isValid = new Decimal().is((Object) value);
                    }

                    if (currentLength > 0) {
                        if (isValid) {
                            field.setLineColor(valid);
                            field.setForeground(valid);
                        } else {
                            field.setLineColor(invalid);
                            field.setForeground(invalid);
                        }
                    } else {
                        field.setLineColor(empty);
                        field.setForeground(empty);
                    }
                });
            }
        });
    }

    private void startUpdateTimer() {
        javax.swing.Timer timer = new javax.swing.Timer(MEMORY_UPDATE_INTERVAL, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateStatistics();
            }
        });
        timer.start();
    }

    private void updateStatistics() {

        // Update the GUI elements on the Event Dispatch Thread
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                memoryJob.setProgress((int) Usage.memory());
            }
        });
    }

    private class UpdateTimeTask extends TimerTask {

        @Override
        public void run() {
            updateTimeInfo();
        }
    }

    private void updateTimeInfo() {
        LocalDateTime now = LocalDateTime.now();
        LocalTime currentTime = now.toLocalTime();

        String dayOfWeek = now.format(DateTimeFormatter.ofPattern("EEE"));
        int dayOfMonth = now.getDayOfMonth();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h");
        int hour = Integer.parseInt(currentTime.format(formatter));
        int minute = now.getMinute();
        String timeElapsed = getTimeElapsed();

        dayLabel.setText(String.format("%s, %s", dayOfWeek, dayOfMonth));
        timeLabel.setText(String.format("%02d : %02d", hour, minute));
        periodLabel.setText(now.getHour() >= 12 ? "PM" : "AM");
        elapsedLabel.setText(timeElapsed);
    }

    private String getTimeElapsed() {
        long elapsedTime = System.currentTimeMillis() - startTime;
        long seconds = (elapsedTime / 1000) % 60;
        long minutes = (elapsedTime / (1000 * 60)) % 60;
        long hours = (elapsedTime / (1000 * 60 * 60)) % 24;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        parentPanel = new v4j.Component.Panel.Panel();
        panelBorder = new v4j.Component.Panel.PanelBorder();
        menuContainer = new v4j.Component.Panel.Panel();
        jSeparator1 = new javax.swing.JSeparator();
        dayLabel = new javax.swing.JLabel();
        timeLabel = new javax.swing.JLabel();
        elapsedLabel = new javax.swing.JLabel();
        sourceCode = new v4j.Component.Button.Button();
        periodLabel = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        elapsedLabel4 = new javax.swing.JLabel();
        elapsedLabel22 = new javax.swing.JLabel();
        netkit = new javax.swing.JLabel();
        v4j = new javax.swing.JLabel();
        mypass = new javax.swing.JLabel();
        vendom = new javax.swing.JLabel();
        cs = new javax.swing.JLabel();
        cs1 = new javax.swing.JLabel();
        ip2decContainer = new v4j.Component.Panel.Panel();
        ip2dec_ipField = new v4j.Component.Field.SmoothField();
        elapsedLabel1 = new javax.swing.JLabel();
        ip2dec_decField = new v4j.Component.Field.SmoothField();
        elapsedLabel3 = new javax.swing.JLabel();
        arrowRightLabel4 = new javax.swing.JLabel();
        quit = new v4j.MessagePane.Button();
        networkStatisticsContainer = new v4j.Component.Panel.Panel();
        ouiField = new v4j.Component.Field.SmoothField();
        elapsedLabel17 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        ouiList = new javax.swing.JList<>();
        foundOUIResults = new v4j.Component.Progress.LineBar();
        ip2binContainer = new v4j.Component.Panel.Panel();
        jSeparator6 = new javax.swing.JSeparator();
        jSeparator7 = new javax.swing.JSeparator();
        ip2bin_ipField = new v4j.Component.Field.SmoothField();
        elapsedLabel6 = new javax.swing.JLabel();
        ip2bin_oct1 = new v4j.Component.Field.SmoothField();
        ip2bin_oct2 = new v4j.Component.Field.SmoothField();
        ip2bin_oct3 = new v4j.Component.Field.SmoothField();
        ip2bin_oct4 = new v4j.Component.Field.SmoothField();
        randomIPv4Container = new v4j.Component.Panel.Panel();
        randomIPv4Field = new v4j.Component.Field.SmoothField();
        elapsedLabel7 = new javax.swing.JLabel();
        randomIPv4Btn = new v4j.Component.Button.Button();
        statisticsContainer = new v4j.Component.Panel.Panel();
        memoryJob = new v4j.Component.Progress.Job();
        ip2cidrContainer = new v4j.Component.Panel.Panel();
        elapsedLabel9 = new javax.swing.JLabel();
        ip2cidr_ipField = new v4j.Component.Field.SmoothField();
        arrowRightLabel = new javax.swing.JLabel();
        elapsedLabel2 = new javax.swing.JLabel();
        ip2cidr_cidrField = new v4j.Component.Field.SmoothField();
        randomMACContainer = new v4j.Component.Panel.Panel();
        randomMACField = new v4j.Component.Field.SmoothField();
        elapsedLabel10 = new javax.swing.JLabel();
        randomMACBtn = new v4j.Component.Button.Button();
        dec2binContainer = new v4j.Component.Panel.Panel();
        elapsedLabel11 = new javax.swing.JLabel();
        dec2bin_decField = new v4j.Component.Field.SmoothField();
        dec2bin_binField = new v4j.Component.Field.SmoothField();
        arrowRightLabel2 = new javax.swing.JLabel();
        elapsedLabel12 = new javax.swing.JLabel();
        IPv4ValidatorContainer = new v4j.Component.Panel.Panel();
        IPv4ValidatorField = new v4j.Component.Field.SmoothField();
        elapsedLabel14 = new javax.swing.JLabel();
        servicePortContainer = new v4j.Component.Panel.Panel();
        IPv4ValidatorField1 = new v4j.Component.Field.SmoothField();
        elapsedLabel16 = new javax.swing.JLabel();
        statusCodeContainer = new v4j.Component.Panel.Panel();
        statusCodeField = new v4j.Component.Field.SmoothField();
        elapsedLabel18 = new javax.swing.JLabel();
        statusCodeServiceBox = new javax.swing.JComboBox<>();
        bin2decContainer = new v4j.Component.Panel.Panel();
        elapsedLabel13 = new javax.swing.JLabel();
        bin2dec_binField = new v4j.Component.Field.SmoothField();
        bin2dec_decField = new v4j.Component.Field.SmoothField();
        arrowRightLabel3 = new javax.swing.JLabel();
        elapsedLabel20 = new javax.swing.JLabel();
        cidr2ipContainer = new v4j.Component.Panel.Panel();
        elapsedLabel21 = new javax.swing.JLabel();
        cidr2ip_cidrField = new v4j.Component.Spinner.Spinner();
        cidr2ip_ipField = new v4j.Component.Field.SmoothField();
        arrowRightLabel1 = new javax.swing.JLabel();
        elapsedLabel5 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        MACValidatorContainer = new v4j.Component.Panel.Panel();
        MACValidatorField = new v4j.Component.Field.SmoothField();
        elapsedLabel24 = new javax.swing.JLabel();
        aclContainer = new v4j.Component.Panel.Panel();
        aclField = new v4j.Component.Field.SmoothField();
        elapsedLabel19 = new javax.swing.JLabel();
        MACValidatorField1 = new v4j.Component.Field.SmoothField();
        elapsedLabel15 = new javax.swing.JLabel();
        cidr2ip_cidrField1 = new v4j.Component.Spinner.Spinner();
        MACValidatorField2 = new v4j.Component.Field.SmoothField();
        elapsedLabel25 = new javax.swing.JLabel();
        cidr2ip_cidrField2 = new v4j.Component.Spinner.Spinner();
        jComboBox1 = new javax.swing.JComboBox<>();
        cidr2ip_cidrField3 = new v4j.Component.Spinner.Spinner();
        elapsedLabel27 = new javax.swing.JLabel();
        elapsedLabel28 = new javax.swing.JLabel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jCheckBox2 = new javax.swing.JCheckBox();
        jCheckBox3 = new javax.swing.JCheckBox();
        jCheckBox4 = new javax.swing.JCheckBox();
        jCheckBox5 = new javax.swing.JCheckBox();
        elapsedLabel29 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        securePasswordContainer = new v4j.Component.Panel.Panel();
        securePasswordBtn = new v4j.Component.Button.Button();
        passwordLength = new javax.swing.JTextField();
        passwordField = new javax.swing.JTextField();
        instanceIDLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        parentPanel.setBackground(new java.awt.Color(55, 63, 75));

        panelBorder.setArc(15);
        panelBorder.setColor(new java.awt.Color(78, 88, 107));

        menuContainer.setBackground(new java.awt.Color(78, 87, 106));

        jSeparator1.setBackground(new java.awt.Color(55, 63, 75));
        jSeparator1.setForeground(new java.awt.Color(55, 63, 75));

        dayLabel.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        dayLabel.setForeground(new java.awt.Color(249, 250, 250));
        dayLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        dayLabel.setText("DAY, 00");

        timeLabel.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        timeLabel.setForeground(new java.awt.Color(249, 250, 250));
        timeLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        timeLabel.setText("00 : 00");

        elapsedLabel.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        elapsedLabel.setForeground(new java.awt.Color(122, 131, 147));
        elapsedLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        elapsedLabel.setText("00:00:00");

        sourceCode.setBackground(new java.awt.Color(94, 103, 122));
        sourceCode.setForeground(new java.awt.Color(249, 250, 250));
        sourceCode.setText("Source Code");
        sourceCode.setArc(35);
        sourceCode.setFocusable(false);
        sourceCode.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        sourceCode.setIconTextGap(8);
        sourceCode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sourceCodeActionPerformed(evt);
            }
        });

        periodLabel.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        periodLabel.setForeground(new java.awt.Color(109, 123, 139));
        periodLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        periodLabel.setText("AM");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(249, 250, 250));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("NetLab");

        elapsedLabel4.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        elapsedLabel4.setForeground(new java.awt.Color(122, 131, 147));
        elapsedLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        elapsedLabel4.setText("Educational labs");

        elapsedLabel22.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        elapsedLabel22.setForeground(new java.awt.Color(122, 131, 147));
        elapsedLabel22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        elapsedLabel22.setText("v1.0.0 | created by virus");

        netkit.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        netkit.setForeground(new java.awt.Color(0, 170, 255));
        netkit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        netkit.setText("NetKit (library)");
        netkit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                netkitMouseClicked(evt);
            }
        });

        v4j.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        v4j.setForeground(new java.awt.Color(0, 170, 255));
        v4j.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        v4j.setText("v4j (library)");
        v4j.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                v4jMouseClicked(evt);
            }
        });

        mypass.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        mypass.setForeground(new java.awt.Color(0, 170, 255));
        mypass.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        mypass.setText("MyPass (library)");
        mypass.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                mypassMouseClicked(evt);
            }
        });

        vendom.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        vendom.setForeground(new java.awt.Color(0, 170, 255));
        vendom.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        vendom.setText("vendom (library)");
        vendom.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                vendomMouseClicked(evt);
            }
        });

        cs.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        cs.setForeground(new java.awt.Color(0, 170, 255));
        cs.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cs.setText("cs (library)");
        cs.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                csMouseClicked(evt);
            }
        });

        cs1.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        cs1.setForeground(new java.awt.Color(255, 162, 140));
        cs1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cs1.setText("currently private");

        javax.swing.GroupLayout menuContainerLayout = new javax.swing.GroupLayout(menuContainer);
        menuContainer.setLayout(menuContainerLayout);
        menuContainerLayout.setHorizontalGroup(
            menuContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addComponent(elapsedLabel22, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(menuContainerLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(menuContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(menuContainerLayout.createSequentialGroup()
                        .addComponent(timeLabel)
                        .addGap(4, 4, 4)
                        .addComponent(periodLabel))
                    .addComponent(sourceCode, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, menuContainerLayout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(menuContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(elapsedLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dayLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(elapsedLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(netkit, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(v4j, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(mypass, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(vendom, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cs, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cs1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        menuContainerLayout.setVerticalGroup(
            menuContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuContainerLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel5)
                .addGap(0, 0, 0)
                .addComponent(elapsedLabel4)
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 7, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(dayLabel)
                .addGap(3, 3, 3)
                .addGroup(menuContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(timeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(periodLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(elapsedLabel)
                .addGap(34, 34, 34)
                .addComponent(netkit)
                .addGap(34, 34, 34)
                .addComponent(v4j)
                .addGap(34, 34, 34)
                .addComponent(mypass)
                .addGap(34, 34, 34)
                .addComponent(vendom)
                .addGap(34, 34, 34)
                .addComponent(cs)
                .addGap(0, 0, 0)
                .addComponent(cs1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(sourceCode, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(elapsedLabel22)
                .addGap(5, 5, 5))
        );

        ip2decContainer.setBackground(new java.awt.Color(62, 67, 87));

        ip2dec_ipField.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        ip2dec_ipField.setArc(15);
        ip2dec_ipField.setDragEnabled(true);
        ip2dec_ipField.setFillColor(new java.awt.Color(51, 57, 68));
        ip2dec_ipField.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        ip2dec_ipField.setLineColor(new java.awt.Color(132, 197, 238));
        ip2dec_ipField.setMaximumSize(new java.awt.Dimension(37, 143));
        ip2dec_ipField.setMinimumSize(new java.awt.Dimension(37, 143));
        ip2dec_ipField.setPreferredSize(new java.awt.Dimension(37, 143));
        ip2dec_ipField.setSelectionColor(new java.awt.Color(132, 197, 238));
        ip2dec_ipField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ip2dec_ipFieldActionPerformed(evt);
            }
        });

        elapsedLabel1.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        elapsedLabel1.setForeground(new java.awt.Color(122, 131, 147));
        elapsedLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        elapsedLabel1.setText("IPv4 Address");

        ip2dec_decField.setEditable(false);
        ip2dec_decField.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        ip2dec_decField.setArc(15);
        ip2dec_decField.setDragEnabled(true);
        ip2dec_decField.setFillColor(new java.awt.Color(51, 57, 68));
        ip2dec_decField.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        ip2dec_decField.setLineColor(new java.awt.Color(132, 197, 238));
        ip2dec_decField.setMaximumSize(new java.awt.Dimension(37, 143));
        ip2dec_decField.setMinimumSize(new java.awt.Dimension(37, 143));
        ip2dec_decField.setPreferredSize(new java.awt.Dimension(37, 143));
        ip2dec_decField.setSelectionColor(new java.awt.Color(132, 197, 238));

        elapsedLabel3.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        elapsedLabel3.setForeground(new java.awt.Color(122, 131, 147));
        elapsedLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        elapsedLabel3.setText("Decimal");

        arrowRightLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        arrowRightLabel4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        arrowRightLabel4.setIconTextGap(0);

        javax.swing.GroupLayout ip2decContainerLayout = new javax.swing.GroupLayout(ip2decContainer);
        ip2decContainer.setLayout(ip2decContainerLayout);
        ip2decContainerLayout.setHorizontalGroup(
            ip2decContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ip2decContainerLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(ip2decContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(ip2dec_ipField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(elapsedLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE))
                .addGap(7, 7, 7)
                .addComponent(arrowRightLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addGroup(ip2decContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(ip2dec_decField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(elapsedLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10))
        );
        ip2decContainerLayout.setVerticalGroup(
            ip2decContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ip2decContainerLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(ip2decContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(ip2decContainerLayout.createSequentialGroup()
                        .addComponent(elapsedLabel3)
                        .addGap(5, 5, 5)
                        .addComponent(ip2dec_decField, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(ip2decContainerLayout.createSequentialGroup()
                        .addComponent(elapsedLabel1)
                        .addGap(5, 5, 5)
                        .addGroup(ip2decContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(ip2dec_ipField, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(arrowRightLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        quit.setBackground(new java.awt.Color(209, 50, 56));
        quit.setForeground(new java.awt.Color(207, 207, 207));
        quit.setText("Quit");
        quit.setIconTextGap(8);
        quit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                quitActionPerformed(evt);
            }
        });

        networkStatisticsContainer.setBackground(new java.awt.Color(62, 67, 87));

        ouiField.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        ouiField.setArc(15);
        ouiField.setDragEnabled(true);
        ouiField.setFillColor(new java.awt.Color(51, 57, 68));
        ouiField.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        ouiField.setLineColor(new java.awt.Color(132, 197, 238));
        ouiField.setMaximumSize(new java.awt.Dimension(37, 143));
        ouiField.setMinimumSize(new java.awt.Dimension(37, 143));
        ouiField.setPreferredSize(new java.awt.Dimension(37, 143));
        ouiField.setSelectionColor(new java.awt.Color(132, 197, 238));
        ouiField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ouiFieldActionPerformed(evt);
            }
        });

        elapsedLabel17.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        elapsedLabel17.setForeground(new java.awt.Color(122, 131, 147));
        elapsedLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        elapsedLabel17.setText("OUI (Database)");

        ouiList.setBackground(new java.awt.Color(62, 67, 87));
        ouiList.setFixedCellHeight(30);
        jScrollPane1.setViewportView(ouiList);

        foundOUIResults.setBackground(new java.awt.Color(78, 87, 106));
        foundOUIResults.setForeground(new java.awt.Color(132, 197, 238));
        foundOUIResults.setMaximum(MAX_OUI);

        javax.swing.GroupLayout networkStatisticsContainerLayout = new javax.swing.GroupLayout(networkStatisticsContainer);
        networkStatisticsContainer.setLayout(networkStatisticsContainerLayout);
        networkStatisticsContainerLayout.setHorizontalGroup(
            networkStatisticsContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(networkStatisticsContainerLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(networkStatisticsContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(foundOUIResults, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(elapsedLabel17, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ouiField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE))
                .addGap(10, 10, 10))
        );
        networkStatisticsContainerLayout.setVerticalGroup(
            networkStatisticsContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, networkStatisticsContainerLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(elapsedLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ouiField, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(foundOUIResults, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5))
        );

        ip2binContainer.setBackground(new java.awt.Color(62, 67, 87));

        jSeparator6.setBackground(new java.awt.Color(55, 63, 75));
        jSeparator6.setForeground(new java.awt.Color(55, 63, 75));
        jSeparator6.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jSeparator7.setBackground(new java.awt.Color(55, 63, 75));
        jSeparator7.setForeground(new java.awt.Color(55, 63, 75));
        jSeparator7.setOrientation(javax.swing.SwingConstants.VERTICAL);

        ip2bin_ipField.setArc(15);
        ip2bin_ipField.setDragEnabled(true);
        ip2bin_ipField.setFillColor(new java.awt.Color(51, 57, 68));
        ip2bin_ipField.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        ip2bin_ipField.setLineColor(new java.awt.Color(132, 197, 238));
        ip2bin_ipField.setMaximumSize(new java.awt.Dimension(37, 143));
        ip2bin_ipField.setMinimumSize(new java.awt.Dimension(37, 143));
        ip2bin_ipField.setPreferredSize(new java.awt.Dimension(37, 143));
        ip2bin_ipField.setSelectionColor(new java.awt.Color(132, 197, 238));
        ip2bin_ipField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ip2bin_ipFieldActionPerformed(evt);
            }
        });

        elapsedLabel6.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        elapsedLabel6.setForeground(new java.awt.Color(122, 131, 147));
        elapsedLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        elapsedLabel6.setText("IPv4 Address to binary");

        ip2bin_oct1.setEditable(false);
        ip2bin_oct1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        ip2bin_oct1.setArc(15);
        ip2bin_oct1.setDragEnabled(true);
        ip2bin_oct1.setFillColor(new java.awt.Color(51, 57, 68));
        ip2bin_oct1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        ip2bin_oct1.setLineColor(new java.awt.Color(132, 197, 238));
        ip2bin_oct1.setMaximumSize(new java.awt.Dimension(37, 143));
        ip2bin_oct1.setMinimumSize(new java.awt.Dimension(37, 143));
        ip2bin_oct1.setPreferredSize(new java.awt.Dimension(37, 143));
        ip2bin_oct1.setSelectionColor(new java.awt.Color(132, 197, 238));

        ip2bin_oct2.setEditable(false);
        ip2bin_oct2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        ip2bin_oct2.setArc(15);
        ip2bin_oct2.setDragEnabled(true);
        ip2bin_oct2.setFillColor(new java.awt.Color(51, 57, 68));
        ip2bin_oct2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        ip2bin_oct2.setLineColor(new java.awt.Color(132, 197, 238));
        ip2bin_oct2.setMaximumSize(new java.awt.Dimension(37, 143));
        ip2bin_oct2.setMinimumSize(new java.awt.Dimension(37, 143));
        ip2bin_oct2.setPreferredSize(new java.awt.Dimension(37, 143));
        ip2bin_oct2.setSelectionColor(new java.awt.Color(132, 197, 238));

        ip2bin_oct3.setEditable(false);
        ip2bin_oct3.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        ip2bin_oct3.setArc(15);
        ip2bin_oct3.setDragEnabled(true);
        ip2bin_oct3.setFillColor(new java.awt.Color(51, 57, 68));
        ip2bin_oct3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        ip2bin_oct3.setLineColor(new java.awt.Color(132, 197, 238));
        ip2bin_oct3.setMaximumSize(new java.awt.Dimension(37, 143));
        ip2bin_oct3.setMinimumSize(new java.awt.Dimension(37, 143));
        ip2bin_oct3.setPreferredSize(new java.awt.Dimension(37, 143));
        ip2bin_oct3.setSelectionColor(new java.awt.Color(132, 197, 238));

        ip2bin_oct4.setEditable(false);
        ip2bin_oct4.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        ip2bin_oct4.setArc(15);
        ip2bin_oct4.setDragEnabled(true);
        ip2bin_oct4.setFillColor(new java.awt.Color(51, 57, 68));
        ip2bin_oct4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        ip2bin_oct4.setLineColor(new java.awt.Color(132, 197, 238));
        ip2bin_oct4.setMaximumSize(new java.awt.Dimension(37, 143));
        ip2bin_oct4.setMinimumSize(new java.awt.Dimension(37, 143));
        ip2bin_oct4.setPreferredSize(new java.awt.Dimension(37, 143));
        ip2bin_oct4.setSelectionColor(new java.awt.Color(132, 197, 238));

        javax.swing.GroupLayout ip2binContainerLayout = new javax.swing.GroupLayout(ip2binContainer);
        ip2binContainer.setLayout(ip2binContainerLayout);
        ip2binContainerLayout.setHorizontalGroup(
            ip2binContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ip2binContainerLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(ip2binContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, ip2binContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(ip2binContainerLayout.createSequentialGroup()
                            .addComponent(ip2bin_oct1, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(5, 5, 5)
                            .addComponent(ip2bin_oct2, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(5, 5, 5)
                            .addComponent(ip2bin_oct3, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(5, 5, 5)
                            .addComponent(ip2bin_oct4, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(elapsedLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(ip2bin_ipField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, ip2binContainerLayout.createSequentialGroup()
                        .addGap(236, 236, 236)
                        .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(246, 246, 246)
                        .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 3, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 6, Short.MAX_VALUE))
        );
        ip2binContainerLayout.setVerticalGroup(
            ip2binContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ip2binContainerLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(elapsedLabel6)
                .addGap(5, 5, 5)
                .addComponent(ip2bin_ipField, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addGroup(ip2binContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ip2bin_oct1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ip2bin_oct2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ip2bin_oct3, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ip2bin_oct4, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(ip2binContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15))
        );

        randomIPv4Container.setBackground(new java.awt.Color(62, 67, 87));

        randomIPv4Field.setEditable(false);
        randomIPv4Field.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        randomIPv4Field.setArc(15);
        randomIPv4Field.setAutoscrolls(false);
        randomIPv4Field.setDragEnabled(true);
        randomIPv4Field.setFillColor(new java.awt.Color(51, 57, 68));
        randomIPv4Field.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        randomIPv4Field.setLineColor(new java.awt.Color(132, 197, 238));
        randomIPv4Field.setMaximumSize(new java.awt.Dimension(37, 143));
        randomIPv4Field.setMinimumSize(new java.awt.Dimension(37, 143));
        randomIPv4Field.setPreferredSize(new java.awt.Dimension(37, 143));
        randomIPv4Field.setSelectionColor(new java.awt.Color(132, 197, 238));

        elapsedLabel7.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        elapsedLabel7.setForeground(new java.awt.Color(122, 131, 147));
        elapsedLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        elapsedLabel7.setText("Random IPv4 Address");

        randomIPv4Btn.setBackground(new java.awt.Color(132, 197, 238));
        randomIPv4Btn.setForeground(new java.awt.Color(17, 17, 17));
        randomIPv4Btn.setText("Generate");
        randomIPv4Btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                randomIPv4BtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout randomIPv4ContainerLayout = new javax.swing.GroupLayout(randomIPv4Container);
        randomIPv4Container.setLayout(randomIPv4ContainerLayout);
        randomIPv4ContainerLayout.setHorizontalGroup(
            randomIPv4ContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(randomIPv4ContainerLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(randomIPv4ContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(randomIPv4ContainerLayout.createSequentialGroup()
                        .addComponent(randomIPv4Field, javax.swing.GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE)
                        .addGap(10, 10, 10)
                        .addComponent(randomIPv4Btn, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(elapsedLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(10, 10, 10))
        );
        randomIPv4ContainerLayout.setVerticalGroup(
            randomIPv4ContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, randomIPv4ContainerLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(elapsedLabel7)
                .addGap(10, 10, 10)
                .addGroup(randomIPv4ContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(randomIPv4Field, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(randomIPv4Btn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(10, Short.MAX_VALUE))
        );

        statisticsContainer.setBackground(new java.awt.Color(51, 57, 68));

        memoryJob.setAccent(new java.awt.Color(219, 216, 66));
        memoryJob.setTitle("Memory");

        javax.swing.GroupLayout statisticsContainerLayout = new javax.swing.GroupLayout(statisticsContainer);
        statisticsContainer.setLayout(statisticsContainerLayout);
        statisticsContainerLayout.setHorizontalGroup(
            statisticsContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statisticsContainerLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(memoryJob, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(10, 10, 10))
        );
        statisticsContainerLayout.setVerticalGroup(
            statisticsContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statisticsContainerLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(memoryJob, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(10, Short.MAX_VALUE))
        );

        ip2cidrContainer.setBackground(new java.awt.Color(92, 67, 87));

        elapsedLabel9.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        elapsedLabel9.setForeground(new java.awt.Color(122, 131, 147));
        elapsedLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        elapsedLabel9.setText("CIDR");

        ip2cidr_ipField.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        ip2cidr_ipField.setArc(15);
        ip2cidr_ipField.setDragEnabled(true);
        ip2cidr_ipField.setFillColor(new java.awt.Color(51, 57, 68));
        ip2cidr_ipField.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        ip2cidr_ipField.setLineColor(new java.awt.Color(132, 197, 238));
        ip2cidr_ipField.setMaximumSize(new java.awt.Dimension(37, 143));
        ip2cidr_ipField.setMinimumSize(new java.awt.Dimension(37, 143));
        ip2cidr_ipField.setPreferredSize(new java.awt.Dimension(37, 143));
        ip2cidr_ipField.setSelectionColor(new java.awt.Color(132, 197, 238));
        ip2cidr_ipField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ip2cidr_ipFieldActionPerformed(evt);
            }
        });

        arrowRightLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        arrowRightLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        arrowRightLabel.setIconTextGap(0);

        elapsedLabel2.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        elapsedLabel2.setForeground(new java.awt.Color(122, 131, 147));
        elapsedLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        elapsedLabel2.setText("IPv4 Address");

        ip2cidr_cidrField.setEditable(false);
        ip2cidr_cidrField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        ip2cidr_cidrField.setArc(15);
        ip2cidr_cidrField.setDragEnabled(true);
        ip2cidr_cidrField.setFillColor(new java.awt.Color(51, 57, 68));
        ip2cidr_cidrField.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        ip2cidr_cidrField.setLineColor(new java.awt.Color(132, 197, 238));
        ip2cidr_cidrField.setMaximumSize(new java.awt.Dimension(37, 143));
        ip2cidr_cidrField.setMinimumSize(new java.awt.Dimension(37, 143));
        ip2cidr_cidrField.setPreferredSize(new java.awt.Dimension(37, 143));
        ip2cidr_cidrField.setSelectionColor(new java.awt.Color(132, 197, 238));

        javax.swing.GroupLayout ip2cidrContainerLayout = new javax.swing.GroupLayout(ip2cidrContainer);
        ip2cidrContainer.setLayout(ip2cidrContainerLayout);
        ip2cidrContainerLayout.setHorizontalGroup(
            ip2cidrContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ip2cidrContainerLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(ip2cidrContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(elapsedLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE)
                    .addComponent(ip2cidr_ipField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(6, 6, 6)
                .addComponent(arrowRightLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(ip2cidrContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(ip2cidrContainerLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(ip2cidr_cidrField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(ip2cidrContainerLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(elapsedLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        ip2cidrContainerLayout.setVerticalGroup(
            ip2cidrContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ip2cidrContainerLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(ip2cidrContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(ip2cidr_ipField, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(ip2cidrContainerLayout.createSequentialGroup()
                        .addGroup(ip2cidrContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(elapsedLabel9)
                            .addComponent(elapsedLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(ip2cidrContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(arrowRightLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ip2cidr_cidrField, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(10, 10, 10))
        );

        randomMACContainer.setBackground(new java.awt.Color(62, 67, 87));

        randomMACField.setEditable(false);
        randomMACField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        randomMACField.setArc(15);
        randomMACField.setAutoscrolls(false);
        randomMACField.setDragEnabled(true);
        randomMACField.setFillColor(new java.awt.Color(51, 57, 68));
        randomMACField.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        randomMACField.setLineColor(new java.awt.Color(132, 197, 238));
        randomMACField.setMaximumSize(new java.awt.Dimension(37, 143));
        randomMACField.setMinimumSize(new java.awt.Dimension(37, 143));
        randomMACField.setPreferredSize(new java.awt.Dimension(37, 143));
        randomMACField.setSelectionColor(new java.awt.Color(132, 197, 238));

        elapsedLabel10.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        elapsedLabel10.setForeground(new java.awt.Color(122, 131, 147));
        elapsedLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        elapsedLabel10.setText("Random MAC Address");

        randomMACBtn.setBackground(new java.awt.Color(132, 197, 238));
        randomMACBtn.setForeground(new java.awt.Color(17, 17, 17));
        randomMACBtn.setText("Generate");
        randomMACBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                randomMACBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout randomMACContainerLayout = new javax.swing.GroupLayout(randomMACContainer);
        randomMACContainer.setLayout(randomMACContainerLayout);
        randomMACContainerLayout.setHorizontalGroup(
            randomMACContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(randomMACContainerLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(randomMACContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, randomMACContainerLayout.createSequentialGroup()
                        .addComponent(randomMACField, javax.swing.GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE)
                        .addGap(10, 10, 10)
                        .addComponent(randomMACBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(elapsedLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(10, 10, 10))
        );
        randomMACContainerLayout.setVerticalGroup(
            randomMACContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, randomMACContainerLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(elapsedLabel10)
                .addGap(10, 10, 10)
                .addGroup(randomMACContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(randomMACField, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(randomMACBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(10, Short.MAX_VALUE))
        );

        dec2binContainer.setBackground(new java.awt.Color(62, 67, 87));

        elapsedLabel11.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        elapsedLabel11.setForeground(new java.awt.Color(122, 131, 147));
        elapsedLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        elapsedLabel11.setText("Decimal");

        dec2bin_decField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        dec2bin_decField.setArc(15);
        dec2bin_decField.setDragEnabled(true);
        dec2bin_decField.setFillColor(new java.awt.Color(51, 57, 68));
        dec2bin_decField.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        dec2bin_decField.setLineColor(new java.awt.Color(132, 197, 238));
        dec2bin_decField.setMaximumSize(new java.awt.Dimension(37, 143));
        dec2bin_decField.setMinimumSize(new java.awt.Dimension(37, 143));
        dec2bin_decField.setPreferredSize(new java.awt.Dimension(37, 143));
        dec2bin_decField.setSelectionColor(new java.awt.Color(132, 197, 238));
        dec2bin_decField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dec2bin_decFieldActionPerformed(evt);
            }
        });

        dec2bin_binField.setEditable(false);
        dec2bin_binField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        dec2bin_binField.setArc(15);
        dec2bin_binField.setDragEnabled(true);
        dec2bin_binField.setFillColor(new java.awt.Color(51, 57, 68));
        dec2bin_binField.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        dec2bin_binField.setLineColor(new java.awt.Color(132, 197, 238));
        dec2bin_binField.setMaximumSize(new java.awt.Dimension(37, 143));
        dec2bin_binField.setMinimumSize(new java.awt.Dimension(37, 143));
        dec2bin_binField.setPreferredSize(new java.awt.Dimension(37, 143));
        dec2bin_binField.setSelectionColor(new java.awt.Color(132, 197, 238));

        arrowRightLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        arrowRightLabel2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        arrowRightLabel2.setIconTextGap(0);

        elapsedLabel12.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        elapsedLabel12.setForeground(new java.awt.Color(122, 131, 147));
        elapsedLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        elapsedLabel12.setText("Binary");

        javax.swing.GroupLayout dec2binContainerLayout = new javax.swing.GroupLayout(dec2binContainer);
        dec2binContainer.setLayout(dec2binContainerLayout);
        dec2binContainerLayout.setHorizontalGroup(
            dec2binContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dec2binContainerLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(dec2binContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(dec2bin_decField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(elapsedLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE))
                .addGap(6, 6, 6)
                .addComponent(arrowRightLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addGroup(dec2binContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(elapsedLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(dec2bin_binField, javax.swing.GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE))
                .addContainerGap(10, Short.MAX_VALUE))
        );
        dec2binContainerLayout.setVerticalGroup(
            dec2binContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dec2binContainerLayout.createSequentialGroup()
                .addContainerGap(10, Short.MAX_VALUE)
                .addGroup(dec2binContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(elapsedLabel11)
                    .addComponent(elapsedLabel12))
                .addGap(5, 5, 5)
                .addGroup(dec2binContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(dec2binContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(dec2bin_decField, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(dec2bin_binField, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(arrowRightLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(10, 10, 10))
        );

        IPv4ValidatorContainer.setBackground(new java.awt.Color(62, 67, 87));

        IPv4ValidatorField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        IPv4ValidatorField.setArc(15);
        IPv4ValidatorField.setDragEnabled(true);
        IPv4ValidatorField.setFillColor(new java.awt.Color(51, 57, 68));
        IPv4ValidatorField.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        IPv4ValidatorField.setLineColor(new java.awt.Color(132, 197, 238));
        IPv4ValidatorField.setMaximumSize(new java.awt.Dimension(37, 143));
        IPv4ValidatorField.setMinimumSize(new java.awt.Dimension(37, 143));
        IPv4ValidatorField.setPreferredSize(new java.awt.Dimension(37, 143));
        IPv4ValidatorField.setSelectionColor(new java.awt.Color(132, 197, 238));
        IPv4ValidatorField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IPv4ValidatorFieldActionPerformed(evt);
            }
        });

        elapsedLabel14.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        elapsedLabel14.setForeground(new java.awt.Color(122, 131, 147));
        elapsedLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        elapsedLabel14.setText("IPv4 Validator");

        javax.swing.GroupLayout IPv4ValidatorContainerLayout = new javax.swing.GroupLayout(IPv4ValidatorContainer);
        IPv4ValidatorContainer.setLayout(IPv4ValidatorContainerLayout);
        IPv4ValidatorContainerLayout.setHorizontalGroup(
            IPv4ValidatorContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, IPv4ValidatorContainerLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(IPv4ValidatorContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(elapsedLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(IPv4ValidatorField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(10, 10, 10))
        );
        IPv4ValidatorContainerLayout.setVerticalGroup(
            IPv4ValidatorContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(IPv4ValidatorContainerLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(elapsedLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(IPv4ValidatorField, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );

        servicePortContainer.setBackground(new java.awt.Color(92, 67, 87));

        IPv4ValidatorField1.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        IPv4ValidatorField1.setArc(15);
        IPv4ValidatorField1.setDragEnabled(true);
        IPv4ValidatorField1.setFillColor(new java.awt.Color(51, 57, 68));
        IPv4ValidatorField1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        IPv4ValidatorField1.setLineColor(new java.awt.Color(132, 197, 238));
        IPv4ValidatorField1.setMaximumSize(new java.awt.Dimension(37, 143));
        IPv4ValidatorField1.setMinimumSize(new java.awt.Dimension(37, 143));
        IPv4ValidatorField1.setPreferredSize(new java.awt.Dimension(37, 143));
        IPv4ValidatorField1.setSelectionColor(new java.awt.Color(132, 197, 238));
        IPv4ValidatorField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IPv4ValidatorField1ActionPerformed(evt);
            }
        });

        elapsedLabel16.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        elapsedLabel16.setForeground(new java.awt.Color(122, 131, 147));
        elapsedLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        elapsedLabel16.setText("Port (Database)");

        javax.swing.GroupLayout servicePortContainerLayout = new javax.swing.GroupLayout(servicePortContainer);
        servicePortContainer.setLayout(servicePortContainerLayout);
        servicePortContainerLayout.setHorizontalGroup(
            servicePortContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(servicePortContainerLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(servicePortContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(IPv4ValidatorField1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(elapsedLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE))
                .addGap(10, 10, 10))
        );
        servicePortContainerLayout.setVerticalGroup(
            servicePortContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(servicePortContainerLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(elapsedLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                .addComponent(IPv4ValidatorField1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );

        statusCodeContainer.setBackground(new java.awt.Color(62, 67, 87));

        statusCodeField.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        statusCodeField.setArc(15);
        statusCodeField.setDragEnabled(true);
        statusCodeField.setFillColor(new java.awt.Color(51, 57, 68));
        statusCodeField.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        statusCodeField.setLineColor(new java.awt.Color(132, 197, 238));
        statusCodeField.setMaximumSize(new java.awt.Dimension(37, 143));
        statusCodeField.setMinimumSize(new java.awt.Dimension(37, 143));
        statusCodeField.setPreferredSize(new java.awt.Dimension(37, 143));
        statusCodeField.setSelectionColor(new java.awt.Color(132, 197, 238));
        statusCodeField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                statusCodeFieldActionPerformed(evt);
            }
        });

        elapsedLabel18.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        elapsedLabel18.setForeground(new java.awt.Color(122, 131, 147));
        elapsedLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        elapsedLabel18.setText("Status code (Database)");

        statusCodeServiceBox.setBackground(new java.awt.Color(62, 67, 87));
        statusCodeServiceBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "HTTP", "Caching Warning", "FTP", "SMTP", "Windows Socket" }));

        javax.swing.GroupLayout statusCodeContainerLayout = new javax.swing.GroupLayout(statusCodeContainer);
        statusCodeContainer.setLayout(statusCodeContainerLayout);
        statusCodeContainerLayout.setHorizontalGroup(
            statusCodeContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statusCodeContainerLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(statusCodeContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(statusCodeField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(elapsedLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(statusCodeServiceBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(10, 10, 10))
        );
        statusCodeContainerLayout.setVerticalGroup(
            statusCodeContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statusCodeContainerLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(elapsedLabel18)
                .addGap(10, 10, 10)
                .addComponent(statusCodeServiceBox, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(9, 9, 9)
                .addComponent(statusCodeField, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );

        bin2decContainer.setBackground(new java.awt.Color(62, 67, 87));

        elapsedLabel13.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        elapsedLabel13.setForeground(new java.awt.Color(122, 131, 147));
        elapsedLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        elapsedLabel13.setText("Binary");

        bin2dec_binField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        bin2dec_binField.setArc(15);
        bin2dec_binField.setDragEnabled(true);
        bin2dec_binField.setFillColor(new java.awt.Color(51, 57, 68));
        bin2dec_binField.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        bin2dec_binField.setLineColor(new java.awt.Color(132, 197, 238));
        bin2dec_binField.setMaximumSize(new java.awt.Dimension(37, 143));
        bin2dec_binField.setMinimumSize(new java.awt.Dimension(37, 143));
        bin2dec_binField.setPreferredSize(new java.awt.Dimension(37, 143));
        bin2dec_binField.setSelectionColor(new java.awt.Color(132, 197, 238));
        bin2dec_binField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bin2dec_binFieldActionPerformed(evt);
            }
        });

        bin2dec_decField.setEditable(false);
        bin2dec_decField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        bin2dec_decField.setArc(15);
        bin2dec_decField.setDragEnabled(true);
        bin2dec_decField.setFillColor(new java.awt.Color(51, 57, 68));
        bin2dec_decField.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        bin2dec_decField.setLineColor(new java.awt.Color(132, 197, 238));
        bin2dec_decField.setMaximumSize(new java.awt.Dimension(37, 143));
        bin2dec_decField.setMinimumSize(new java.awt.Dimension(37, 143));
        bin2dec_decField.setPreferredSize(new java.awt.Dimension(37, 143));
        bin2dec_decField.setSelectionColor(new java.awt.Color(132, 197, 238));

        arrowRightLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        arrowRightLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        arrowRightLabel3.setIconTextGap(0);

        elapsedLabel20.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        elapsedLabel20.setForeground(new java.awt.Color(122, 131, 147));
        elapsedLabel20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        elapsedLabel20.setText("Decimal");

        javax.swing.GroupLayout bin2decContainerLayout = new javax.swing.GroupLayout(bin2decContainer);
        bin2decContainer.setLayout(bin2decContainerLayout);
        bin2decContainerLayout.setHorizontalGroup(
            bin2decContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bin2decContainerLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(bin2decContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(elapsedLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(bin2dec_binField, javax.swing.GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(arrowRightLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addGroup(bin2decContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(elapsedLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
                    .addComponent(bin2dec_decField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(10, Short.MAX_VALUE))
        );
        bin2decContainerLayout.setVerticalGroup(
            bin2decContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, bin2decContainerLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(bin2decContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(elapsedLabel13)
                    .addComponent(elapsedLabel20))
                .addGap(5, 5, 5)
                .addGroup(bin2decContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(bin2decContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(bin2dec_binField, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(bin2dec_decField, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(arrowRightLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(10, 10, 10))
        );

        cidr2ipContainer.setBackground(new java.awt.Color(92, 67, 87));

        elapsedLabel21.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        elapsedLabel21.setForeground(new java.awt.Color(122, 131, 147));
        elapsedLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        elapsedLabel21.setText("CIDR");

        cidr2ip_cidrField.setMAX(32);
        cidr2ip_cidrField.setArc(15);

        cidr2ip_ipField.setEditable(false);
        cidr2ip_ipField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        cidr2ip_ipField.setArc(15);
        cidr2ip_ipField.setDragEnabled(true);
        cidr2ip_ipField.setFillColor(new java.awt.Color(51, 57, 68));
        cidr2ip_ipField.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cidr2ip_ipField.setLineColor(new java.awt.Color(132, 197, 238));
        cidr2ip_ipField.setMaximumSize(new java.awt.Dimension(37, 143));
        cidr2ip_ipField.setMinimumSize(new java.awt.Dimension(37, 143));
        cidr2ip_ipField.setPreferredSize(new java.awt.Dimension(37, 143));
        cidr2ip_ipField.setSelectionColor(new java.awt.Color(132, 197, 238));

        arrowRightLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        arrowRightLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        arrowRightLabel1.setIconTextGap(0);

        elapsedLabel5.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        elapsedLabel5.setForeground(new java.awt.Color(122, 131, 147));
        elapsedLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        elapsedLabel5.setText("IPv4 Address");

        javax.swing.GroupLayout cidr2ipContainerLayout = new javax.swing.GroupLayout(cidr2ipContainer);
        cidr2ipContainer.setLayout(cidr2ipContainerLayout);
        cidr2ipContainerLayout.setHorizontalGroup(
            cidr2ipContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cidr2ipContainerLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(cidr2ipContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(cidr2ipContainerLayout.createSequentialGroup()
                        .addComponent(cidr2ip_cidrField, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(7, 7, 7)
                        .addComponent(arrowRightLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(elapsedLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(cidr2ipContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(elapsedLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE)
                    .addComponent(cidr2ip_ipField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(10, 10, 10))
        );
        cidr2ipContainerLayout.setVerticalGroup(
            cidr2ipContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cidr2ipContainerLayout.createSequentialGroup()
                .addContainerGap(13, Short.MAX_VALUE)
                .addGroup(cidr2ipContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(cidr2ip_ipField, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(cidr2ipContainerLayout.createSequentialGroup()
                        .addGroup(cidr2ipContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(elapsedLabel21)
                            .addComponent(elapsedLabel5))
                        .addGap(2, 2, 2)
                        .addGroup(cidr2ipContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(arrowRightLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cidr2ip_cidrField, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE))))
                .addGap(10, 10, 10))
        );

        jSeparator2.setBackground(new java.awt.Color(55, 63, 75));
        jSeparator2.setForeground(new java.awt.Color(78, 87, 106));

        MACValidatorContainer.setBackground(new java.awt.Color(62, 67, 87));

        MACValidatorField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        MACValidatorField.setArc(15);
        MACValidatorField.setDragEnabled(true);
        MACValidatorField.setFillColor(new java.awt.Color(51, 57, 68));
        MACValidatorField.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        MACValidatorField.setLineColor(new java.awt.Color(132, 197, 238));
        MACValidatorField.setMaximumSize(new java.awt.Dimension(37, 143));
        MACValidatorField.setMinimumSize(new java.awt.Dimension(37, 143));
        MACValidatorField.setPreferredSize(new java.awt.Dimension(37, 143));
        MACValidatorField.setSelectionColor(new java.awt.Color(132, 197, 238));
        MACValidatorField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MACValidatorFieldActionPerformed(evt);
            }
        });

        elapsedLabel24.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        elapsedLabel24.setForeground(new java.awt.Color(122, 131, 147));
        elapsedLabel24.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        elapsedLabel24.setText("MAC Validator");

        javax.swing.GroupLayout MACValidatorContainerLayout = new javax.swing.GroupLayout(MACValidatorContainer);
        MACValidatorContainer.setLayout(MACValidatorContainerLayout);
        MACValidatorContainerLayout.setHorizontalGroup(
            MACValidatorContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MACValidatorContainerLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(MACValidatorContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(MACValidatorField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(elapsedLabel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(10, 10, 10))
        );
        MACValidatorContainerLayout.setVerticalGroup(
            MACValidatorContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MACValidatorContainerLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(elapsedLabel24)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(MACValidatorField, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );

        aclContainer.setBackground(new java.awt.Color(92, 67, 87));
        aclContainer.setEnabled(false);

        aclField.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        aclField.setArc(15);
        aclField.setDragEnabled(true);
        aclField.setFillColor(new java.awt.Color(51, 57, 68));
        aclField.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        aclField.setLineColor(new java.awt.Color(132, 197, 238));
        aclField.setMaximumSize(new java.awt.Dimension(37, 143));
        aclField.setMinimumSize(new java.awt.Dimension(37, 143));
        aclField.setPreferredSize(new java.awt.Dimension(37, 143));
        aclField.setSelectionColor(new java.awt.Color(132, 197, 238));
        aclField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aclFieldActionPerformed(evt);
            }
        });

        elapsedLabel19.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        elapsedLabel19.setForeground(new java.awt.Color(122, 131, 147));
        elapsedLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        elapsedLabel19.setText("ACL command generator");

        MACValidatorField1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        MACValidatorField1.setText("255.255.255.255");
        MACValidatorField1.setArc(15);
        MACValidatorField1.setDragEnabled(true);
        MACValidatorField1.setFillColor(new java.awt.Color(51, 57, 68));
        MACValidatorField1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        MACValidatorField1.setLineColor(new java.awt.Color(132, 197, 238));
        MACValidatorField1.setMaximumSize(new java.awt.Dimension(37, 143));
        MACValidatorField1.setMinimumSize(new java.awt.Dimension(37, 143));
        MACValidatorField1.setPreferredSize(new java.awt.Dimension(37, 143));
        MACValidatorField1.setSelectionColor(new java.awt.Color(132, 197, 238));
        MACValidatorField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MACValidatorField1ActionPerformed(evt);
            }
        });

        elapsedLabel15.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        elapsedLabel15.setForeground(new java.awt.Color(122, 131, 147));
        elapsedLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        elapsedLabel15.setText("Source");

        cidr2ip_cidrField1.setMAX(32);
        cidr2ip_cidrField1.setArc(15);

        MACValidatorField2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        MACValidatorField2.setText("255.255.255.255");
        MACValidatorField2.setArc(15);
        MACValidatorField2.setDragEnabled(true);
        MACValidatorField2.setFillColor(new java.awt.Color(51, 57, 68));
        MACValidatorField2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        MACValidatorField2.setLineColor(new java.awt.Color(132, 197, 238));
        MACValidatorField2.setMaximumSize(new java.awt.Dimension(37, 143));
        MACValidatorField2.setMinimumSize(new java.awt.Dimension(37, 143));
        MACValidatorField2.setPreferredSize(new java.awt.Dimension(37, 143));
        MACValidatorField2.setSelectionColor(new java.awt.Color(132, 197, 238));
        MACValidatorField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MACValidatorField2ActionPerformed(evt);
            }
        });

        elapsedLabel25.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        elapsedLabel25.setForeground(new java.awt.Color(122, 131, 147));
        elapsedLabel25.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        elapsedLabel25.setText("Destintation");

        cidr2ip_cidrField2.setMAX(32);
        cidr2ip_cidrField2.setArc(15);

        jComboBox1.setBackground(new java.awt.Color(62, 67, 87));
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "icmp", "ip", "tcp", "udp" }));

        cidr2ip_cidrField3.setMAX(199);
        cidr2ip_cidrField3.setMIN(1);
        cidr2ip_cidrField3.setArc(15);

        elapsedLabel27.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        elapsedLabel27.setForeground(new java.awt.Color(122, 131, 147));
        elapsedLabel27.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        elapsedLabel27.setText("ID");

        elapsedLabel28.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        elapsedLabel28.setForeground(new java.awt.Color(122, 131, 147));
        elapsedLabel28.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        elapsedLabel28.setText("Protocol");

        jCheckBox1.setText("CIDR");
        jCheckBox1.setFocusable(false);
        jCheckBox1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jCheckBox2.setText("CIDR");
        jCheckBox2.setFocusable(false);
        jCheckBox2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jCheckBox3.setText("permit");
        jCheckBox3.setFocusable(false);

        jCheckBox4.setText("ANY");
        jCheckBox4.setFocusable(false);
        jCheckBox4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jCheckBox4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox4ActionPerformed(evt);
            }
        });

        jCheckBox5.setText("ANY");
        jCheckBox5.setFocusable(false);
        jCheckBox5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jCheckBox5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox5ActionPerformed(evt);
            }
        });

        elapsedLabel29.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        elapsedLabel29.setForeground(new java.awt.Color(122, 131, 147));
        elapsedLabel29.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        elapsedLabel29.setText("Port match");

        jComboBox2.setBackground(new java.awt.Color(62, 67, 87));
        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "equal to", "greater than", "less than", "not equal to", "in range" }));

        javax.swing.GroupLayout aclContainerLayout = new javax.swing.GroupLayout(aclContainer);
        aclContainer.setLayout(aclContainerLayout);
        aclContainerLayout.setHorizontalGroup(
            aclContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(aclContainerLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(aclContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(elapsedLabel19, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(aclContainerLayout.createSequentialGroup()
                        .addGroup(aclContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(aclContainerLayout.createSequentialGroup()
                                .addGap(82, 82, 82)
                                .addComponent(jCheckBox5, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jCheckBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 111, Short.MAX_VALUE)
                                .addComponent(jCheckBox4, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jCheckBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(144, 144, 144)
                                .addComponent(elapsedLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(aclContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(aclField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(aclContainerLayout.createSequentialGroup()
                                    .addGroup(aclContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(aclContainerLayout.createSequentialGroup()
                                            .addComponent(MACValidatorField1, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(15, 15, 15)
                                            .addComponent(cidr2ip_cidrField1, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(25, 25, 25)
                                            .addComponent(MACValidatorField2, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(15, 15, 15)
                                            .addComponent(cidr2ip_cidrField2, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(25, 25, 25))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, aclContainerLayout.createSequentialGroup()
                                            .addComponent(elapsedLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(217, 217, 217)
                                            .addComponent(elapsedLabel25)
                                            .addGap(203, 203, 203)))
                                    .addGroup(aclContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jComboBox1, 0, 104, Short.MAX_VALUE)
                                        .addComponent(elapsedLabel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGap(15, 15, 15)
                                    .addComponent(cidr2ip_cidrField3, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGroup(aclContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(aclContainerLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jCheckBox3))
                            .addGroup(aclContainerLayout.createSequentialGroup()
                                .addGap(25, 25, 25)
                                .addGroup(aclContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jComboBox2, 0, 114, Short.MAX_VALUE)
                                    .addComponent(elapsedLabel29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(30, 30, 30))))
        );
        aclContainerLayout.setVerticalGroup(
            aclContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(aclContainerLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(elapsedLabel19)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                .addGroup(aclContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(elapsedLabel27)
                    .addComponent(elapsedLabel29)
                    .addComponent(elapsedLabel28)
                    .addComponent(jCheckBox1)
                    .addComponent(jCheckBox4)
                    .addComponent(elapsedLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCheckBox2)
                    .addComponent(jCheckBox5)
                    .addComponent(elapsedLabel15))
                .addGap(5, 5, 5)
                .addGroup(aclContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cidr2ip_cidrField3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jComboBox1)
                    .addComponent(cidr2ip_cidrField2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(MACValidatorField2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cidr2ip_cidrField1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(MACValidatorField1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(aclContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(aclField, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCheckBox3))
                .addGap(10, 10, 10))
        );

        securePasswordContainer.setBackground(new java.awt.Color(62, 67, 87));

        securePasswordBtn.setBackground(new java.awt.Color(132, 197, 238));
        securePasswordBtn.setForeground(new java.awt.Color(17, 17, 17));
        securePasswordBtn.setText("Secure Password");
        securePasswordBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                securePasswordBtnActionPerformed(evt);
            }
        });

        passwordLength.setBackground(new java.awt.Color(51, 57, 68));
        passwordLength.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        passwordLength.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        passwordLength.setText("16");

        javax.swing.GroupLayout securePasswordContainerLayout = new javax.swing.GroupLayout(securePasswordContainer);
        securePasswordContainer.setLayout(securePasswordContainerLayout);
        securePasswordContainerLayout.setHorizontalGroup(
            securePasswordContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, securePasswordContainerLayout.createSequentialGroup()
                .addContainerGap(10, Short.MAX_VALUE)
                .addGroup(securePasswordContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(passwordField)
                    .addGroup(securePasswordContainerLayout.createSequentialGroup()
                        .addComponent(passwordLength, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(securePasswordBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(10, 10, 10))
        );
        securePasswordContainerLayout.setVerticalGroup(
            securePasswordContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, securePasswordContainerLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(securePasswordContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(securePasswordBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(passwordLength, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addComponent(passwordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5))
        );

        instanceIDLabel.setForeground(new java.awt.Color(122, 131, 147));

        javax.swing.GroupLayout panelBorderLayout = new javax.swing.GroupLayout(panelBorder);
        panelBorder.setLayout(panelBorderLayout);
        panelBorderLayout.setHorizontalGroup(
            panelBorderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorderLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(menuContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addGroup(panelBorderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBorderLayout.createSequentialGroup()
                        .addComponent(instanceIDLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 329, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(quit, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(statisticsContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelBorderLayout.createSequentialGroup()
                        .addGroup(panelBorderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelBorderLayout.createSequentialGroup()
                                .addComponent(ip2decContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(servicePortContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelBorderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(ip2binContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(panelBorderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panelBorderLayout.createSequentialGroup()
                                        .addComponent(cidr2ipContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(10, 10, 10)
                                        .addComponent(dec2binContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(panelBorderLayout.createSequentialGroup()
                                        .addComponent(ip2cidrContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(10, 10, 10)
                                        .addComponent(bin2decContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                        .addGroup(panelBorderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelBorderLayout.createSequentialGroup()
                                .addGroup(panelBorderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(randomIPv4Container, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(randomMACContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(10, 10, 10)
                                .addGroup(panelBorderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(MACValidatorContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(IPv4ValidatorContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(panelBorderLayout.createSequentialGroup()
                                .addGroup(panelBorderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(statusCodeContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(securePasswordContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(10, 10, 10)
                                .addComponent(networkStatisticsContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(panelBorderLayout.createSequentialGroup()
                        .addComponent(aclContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 1, Short.MAX_VALUE)))
                .addContainerGap(15, Short.MAX_VALUE))
        );
        panelBorderLayout.setVerticalGroup(
            panelBorderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBorderLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(panelBorderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(menuContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelBorderLayout.createSequentialGroup()
                        .addGroup(panelBorderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(quit, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(instanceIDLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(aclContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(panelBorderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelBorderLayout.createSequentialGroup()
                                .addGap(11, 11, 11)
                                .addGroup(panelBorderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(servicePortContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(ip2decContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(10, 10, 10)
                                .addGroup(panelBorderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(cidr2ipContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(dec2binContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(10, 10, 10)
                                .addGroup(panelBorderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(ip2cidrContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(bin2decContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(10, 10, 10)
                                .addComponent(ip2binContainer, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelBorderLayout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(panelBorderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(networkStatisticsContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(panelBorderLayout.createSequentialGroup()
                                        .addComponent(statusCodeContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(10, 10, 10)
                                        .addComponent(securePasswordContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGap(12, 12, 12)
                                .addGroup(panelBorderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBorderLayout.createSequentialGroup()
                                        .addComponent(randomIPv4Container, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(10, 10, 10))
                                    .addGroup(panelBorderLayout.createSequentialGroup()
                                        .addComponent(IPv4ValidatorContainer, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(11, 11, 11)))
                                .addGroup(panelBorderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(MACValidatorContainer, javax.swing.GroupLayout.PREFERRED_SIZE, 78, Short.MAX_VALUE)
                                    .addComponent(randomMACContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(10, 10, 10)
                        .addComponent(statisticsContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(15, 15, 15))
        );

        javax.swing.GroupLayout parentPanelLayout = new javax.swing.GroupLayout(parentPanel);
        parentPanel.setLayout(parentPanelLayout);
        parentPanelLayout.setHorizontalGroup(
            parentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(parentPanelLayout.createSequentialGroup()
                .addComponent(panelBorder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        parentPanelLayout.setVerticalGroup(
            parentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelBorder, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(parentPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(parentPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jCheckBox5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox5ActionPerformed

    private void jCheckBox4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox4ActionPerformed

    private void MACValidatorField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MACValidatorField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_MACValidatorField2ActionPerformed

    private void MACValidatorField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MACValidatorField1ActionPerformed
        if (new MAC().is(MACValidatorField.getText())) {
            Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Valid MAC format!");
        } else {
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Incrrect MAC format!");
        }
    }//GEN-LAST:event_MACValidatorField1ActionPerformed

    private void aclFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aclFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_aclFieldActionPerformed

    private void MACValidatorFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MACValidatorFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_MACValidatorFieldActionPerformed

    private void bin2dec_binFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bin2dec_binFieldActionPerformed
        Binary binObj = new Binary();
        String bin = bin2dec_binField.getText();
        if (binObj.is(bin)) {
            bin2dec_decField.setText(Integer.toString(binObj.toDecimal(bin)));
        }
    }//GEN-LAST:event_bin2dec_binFieldActionPerformed

    private void statusCodeFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_statusCodeFieldActionPerformed
        StatusCodePanel statusPanel = new StatusCodePanel();
        String service = (String)statusCodeServiceBox.getSelectedItem();
        String code = statusCodeField.getText();
        boolean found = statusPanel.fetch(service, code);
        
        if (found) {
            GlassPanePopup.showPopup(statusPanel);
        } else {
            JOptionPane.showMessageDialog(this, String.format("Code %s wasn't found!", code), null, JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_statusCodeFieldActionPerformed

    private void IPv4ValidatorField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IPv4ValidatorField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_IPv4ValidatorField1ActionPerformed

    private void IPv4ValidatorFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IPv4ValidatorFieldActionPerformed
        if (new IPv4().is(IPv4ValidatorField.getText())) {
            Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Valid IPv4 format!");
        } else {
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Incrrect IPv4 format!");
        }
    }//GEN-LAST:event_IPv4ValidatorFieldActionPerformed

    private void dec2bin_decFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dec2bin_decFieldActionPerformed
        Decimal decObj = new Decimal();
        String dec = dec2bin_decField.getText();
        if (decObj.is(dec)) {
            dec2bin_binField.setText(decObj.toBinary(Integer.parseInt(dec)));
        }
    }//GEN-LAST:event_dec2bin_decFieldActionPerformed

    private void randomMACBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_randomMACBtnActionPerformed
        randomMACField.setText(new MAC().random());
    }//GEN-LAST:event_randomMACBtnActionPerformed

    private void ip2cidr_ipFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ip2cidr_ipFieldActionPerformed
        String ip = ip2cidr_ipField.getText();
        if (new IPv4().is(ip)) {
            ip2cidr_cidrField.setText(String.valueOf(String.join("", new IPv4().binary(ip)).replace("0", "").length()));
        }
    }//GEN-LAST:event_ip2cidr_ipFieldActionPerformed

    private void randomIPv4BtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_randomIPv4BtnActionPerformed
        randomIPv4Field.setText(new IPv4().random());
    }//GEN-LAST:event_randomIPv4BtnActionPerformed

    private void ip2bin_ipFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ip2bin_ipFieldActionPerformed
        String ip = ip2bin_ipField.getText();
        String[] binParts = new IPv4().binary(ip);

        ip2bin_oct1.setText(binParts[0]);
        ip2bin_oct2.setText(binParts[1]);
        ip2bin_oct3.setText(binParts[2]);
        ip2bin_oct4.setText(binParts[3]);
    }//GEN-LAST:event_ip2bin_ipFieldActionPerformed

    private void ouiFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ouiFieldActionPerformed
        OUI oui = new OUI();
        String query = ouiField.getText().toLowerCase();
        if (!(query.isEmpty())) {
            ArrayList<String> orgs = oui.getMany(query);
            int found = orgs.size();

            if (found > 0) {
                //                ouiList.setListData(new String[0]);
                Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, String.format("Found %s result!", found));

                DefaultListModel ouiListModel = new DefaultListModel();

                foundOUIResults.setValue(found);

                for (String org : orgs) {
                    ouiListModel.addElement(org);
                }
                ouiList.setModel(ouiListModel);
            } else {
                Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Couldn't find any result with the provided query!");
            }
        }
    }//GEN-LAST:event_ouiFieldActionPerformed

    private void quitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_quitActionPerformed
        dispose();
        System.exit(0);
    }//GEN-LAST:event_quitActionPerformed

    private void ip2dec_ipFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ip2dec_ipFieldActionPerformed
        ip2dec_decField.setText(String.valueOf(new IPv4().decimal(ip2dec_ipField.getText())));
    }//GEN-LAST:event_ip2dec_ipFieldActionPerformed

    private void sourceCodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sourceCodeActionPerformed
        String url = "https://github.com/isecvirus/netlab";

        try {
            // Open the URL in the default web browser
            Desktop.getDesktop().browse(new URI(url));
        } catch (IOException | URISyntaxException ex) {
        }
    }//GEN-LAST:event_sourceCodeActionPerformed

    private void securePasswordBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_securePasswordBtnActionPerformed
        String length = passwordLength.getText();
        
        if (new Decimal().is(length)) {
            int fixedLength = Math.min(Math.max(4, Integer.parseInt(length)), 16);
            String password = new Password().generate(fixedLength);
            
            passwordField.setText(password);
            
            passwordLength.setText(String.valueOf(fixedLength));
        }
    }//GEN-LAST:event_securePasswordBtnActionPerformed

    private void netkitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_netkitMouseClicked
        try {Desktop.getDesktop().browse(new URI("https://github.com/isecvirus/netkit"));}catch(IOException|URISyntaxException ex){}
    }//GEN-LAST:event_netkitMouseClicked

    private void v4jMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_v4jMouseClicked
        try {Desktop.getDesktop().browse(new URI("https://github.com/isecvirus/v4j"));}catch(IOException|URISyntaxException ex){}
    }//GEN-LAST:event_v4jMouseClicked

    private void mypassMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mypassMouseClicked
        try {Desktop.getDesktop().browse(new URI("https://github.com/isecvirus/mypass"));}catch(IOException|URISyntaxException ex){}
    }//GEN-LAST:event_mypassMouseClicked

    private void vendomMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_vendomMouseClicked
        try {Desktop.getDesktop().browse(new URI("https://github.com/isecvirus/vendom"));}catch(IOException|URISyntaxException ex){}
    }//GEN-LAST:event_vendomMouseClicked

    private void csMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_csMouseClicked
        try {Desktop.getDesktop().browse(new URI("https://github.com/isecvirus/cs"));}catch(IOException|URISyntaxException ex){}
    }//GEN-LAST:event_csMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        try {
            javax.swing.UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NetLab.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NetLab().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private v4j.Component.Panel.Panel IPv4ValidatorContainer;
    private v4j.Component.Field.SmoothField IPv4ValidatorField;
    private v4j.Component.Field.SmoothField IPv4ValidatorField1;
    private v4j.Component.Panel.Panel MACValidatorContainer;
    private v4j.Component.Field.SmoothField MACValidatorField;
    private v4j.Component.Field.SmoothField MACValidatorField1;
    private v4j.Component.Field.SmoothField MACValidatorField2;
    private v4j.Component.Panel.Panel aclContainer;
    private v4j.Component.Field.SmoothField aclField;
    private javax.swing.JLabel arrowRightLabel;
    private javax.swing.JLabel arrowRightLabel1;
    private javax.swing.JLabel arrowRightLabel2;
    private javax.swing.JLabel arrowRightLabel3;
    private javax.swing.JLabel arrowRightLabel4;
    private v4j.Component.Panel.Panel bin2decContainer;
    private v4j.Component.Field.SmoothField bin2dec_binField;
    private v4j.Component.Field.SmoothField bin2dec_decField;
    private v4j.Component.Panel.Panel cidr2ipContainer;
    private v4j.Component.Spinner.Spinner cidr2ip_cidrField;
    private v4j.Component.Spinner.Spinner cidr2ip_cidrField1;
    private v4j.Component.Spinner.Spinner cidr2ip_cidrField2;
    private v4j.Component.Spinner.Spinner cidr2ip_cidrField3;
    private v4j.Component.Field.SmoothField cidr2ip_ipField;
    private javax.swing.JLabel cs;
    private javax.swing.JLabel cs1;
    private javax.swing.JLabel dayLabel;
    private v4j.Component.Panel.Panel dec2binContainer;
    private v4j.Component.Field.SmoothField dec2bin_binField;
    private v4j.Component.Field.SmoothField dec2bin_decField;
    private javax.swing.JLabel elapsedLabel;
    private javax.swing.JLabel elapsedLabel1;
    private javax.swing.JLabel elapsedLabel10;
    private javax.swing.JLabel elapsedLabel11;
    private javax.swing.JLabel elapsedLabel12;
    private javax.swing.JLabel elapsedLabel13;
    private javax.swing.JLabel elapsedLabel14;
    private javax.swing.JLabel elapsedLabel15;
    private javax.swing.JLabel elapsedLabel16;
    private javax.swing.JLabel elapsedLabel17;
    private javax.swing.JLabel elapsedLabel18;
    private javax.swing.JLabel elapsedLabel19;
    private javax.swing.JLabel elapsedLabel2;
    private javax.swing.JLabel elapsedLabel20;
    private javax.swing.JLabel elapsedLabel21;
    private javax.swing.JLabel elapsedLabel22;
    private javax.swing.JLabel elapsedLabel24;
    private javax.swing.JLabel elapsedLabel25;
    private javax.swing.JLabel elapsedLabel27;
    private javax.swing.JLabel elapsedLabel28;
    private javax.swing.JLabel elapsedLabel29;
    private javax.swing.JLabel elapsedLabel3;
    private javax.swing.JLabel elapsedLabel4;
    private javax.swing.JLabel elapsedLabel5;
    private javax.swing.JLabel elapsedLabel6;
    private javax.swing.JLabel elapsedLabel7;
    private javax.swing.JLabel elapsedLabel9;
    private v4j.Component.Progress.LineBar foundOUIResults;
    private javax.swing.JLabel instanceIDLabel;
    private v4j.Component.Panel.Panel ip2binContainer;
    private v4j.Component.Field.SmoothField ip2bin_ipField;
    private v4j.Component.Field.SmoothField ip2bin_oct1;
    private v4j.Component.Field.SmoothField ip2bin_oct2;
    private v4j.Component.Field.SmoothField ip2bin_oct3;
    private v4j.Component.Field.SmoothField ip2bin_oct4;
    private v4j.Component.Panel.Panel ip2cidrContainer;
    private v4j.Component.Field.SmoothField ip2cidr_cidrField;
    private v4j.Component.Field.SmoothField ip2cidr_ipField;
    private v4j.Component.Panel.Panel ip2decContainer;
    private v4j.Component.Field.SmoothField ip2dec_decField;
    private v4j.Component.Field.SmoothField ip2dec_ipField;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JCheckBox jCheckBox4;
    private javax.swing.JCheckBox jCheckBox5;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private v4j.Component.Progress.Job memoryJob;
    private v4j.Component.Panel.Panel menuContainer;
    private javax.swing.JLabel mypass;
    private javax.swing.JLabel netkit;
    private v4j.Component.Panel.Panel networkStatisticsContainer;
    private v4j.Component.Field.SmoothField ouiField;
    private javax.swing.JList<String> ouiList;
    private v4j.Component.Panel.PanelBorder panelBorder;
    private v4j.Component.Panel.Panel parentPanel;
    private javax.swing.JTextField passwordField;
    private javax.swing.JTextField passwordLength;
    private javax.swing.JLabel periodLabel;
    private v4j.MessagePane.Button quit;
    private v4j.Component.Button.Button randomIPv4Btn;
    private v4j.Component.Panel.Panel randomIPv4Container;
    private v4j.Component.Field.SmoothField randomIPv4Field;
    private v4j.Component.Button.Button randomMACBtn;
    private v4j.Component.Panel.Panel randomMACContainer;
    private v4j.Component.Field.SmoothField randomMACField;
    private v4j.Component.Button.Button securePasswordBtn;
    private v4j.Component.Panel.Panel securePasswordContainer;
    private v4j.Component.Panel.Panel servicePortContainer;
    private v4j.Component.Button.Button sourceCode;
    private v4j.Component.Panel.Panel statisticsContainer;
    private v4j.Component.Panel.Panel statusCodeContainer;
    private v4j.Component.Field.SmoothField statusCodeField;
    private javax.swing.JComboBox<String> statusCodeServiceBox;
    private javax.swing.JLabel timeLabel;
    private javax.swing.JLabel v4j;
    private javax.swing.JLabel vendom;
    // End of variables declaration//GEN-END:variables
}
