package com.sofoste.greetings;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.time.LocalTime;
import java.util.*;
import java.util.prefs.Preferences;

/** Desktop entry point and the small Swing presentation layer. */
public final class GreetingsApp {
    public static final String VERSION = "2.0.0";

    /** Pure greeting logic kept independent from Swing so tests and the CLI can reuse it. */
    static String greet(String rawName, Locale locale, LocalTime time) {
        String name = rawName == null ? "" : rawName.strip();
        String lang = locale == null ? "en" : locale.getLanguage();
        if (name.isEmpty()) return switch (lang) { case "fr" -> "Entrez votre nom pour ouvrir le vortex."; case "de" -> "Gib deinen Namen ein, um das Wurmloch zu öffnen."; default -> "Enter your name to open the vortex."; };
        int hour = time.getHour();
        if ("fr".equals(lang)) return (hour < 12 ? "Bonjour, %s !" : hour < 18 ? "Bon après-midi, %s !" : "Bonsoir, %s !").formatted(name);
        if ("de".equals(lang)) return (hour < 12 ? "Guten Morgen, %s!" : hour < 18 ? "Guten Tag, %s!" : "Guten Abend, %s!").formatted(name);
        return (hour < 12 ? "Good morning, %s!" : hour < 18 ? "Good afternoon, %s!" : "Good evening, %s!").formatted(name);
    }

    public static void main(String[] args) {
        if (Arrays.asList(args).contains("--version")) { System.out.println("GreetingsApp " + VERSION); return; }
        int cli = Arrays.asList(args).indexOf("--greet");
        if (cli >= 0) { String name = cli + 1 < args.length ? args[cli + 1] : ""; System.out.println(greet(name, Locale.getDefault(), LocalTime.now())); return; }
        SwingUtilities.invokeLater(() -> { setLookAndFeel(); new Frame().setVisible(true); });
    }

    private static void setLookAndFeel() {
        try { UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName()); } catch (Exception ignored) {}
    }

    static final class Frame extends JFrame {
        private final Preferences prefs = Preferences.userNodeForPackage(GreetingsApp.class);
        private final GatePanel gate = new GatePanel();
        private final JTextField name = new JTextField();
        private final JLabel result = new JLabel("", SwingConstants.CENTER);
        private final JLabel status = new JLabel();
        private Locale locale;

        Frame() {
            super("GreetingsApp · Event Horizon");
            locale = Locale.forLanguageTag(prefs.get("language", Locale.getDefault().getLanguage()));
            setDefaultCloseOperation(EXIT_ON_CLOSE); setMinimumSize(new Dimension(880, 600)); setSize(1040, 680); setLocationRelativeTo(null);
            JPanel root = new JPanel(new BorderLayout(28, 0)); root.setBackground(new Color(4, 12, 25)); root.setBorder(new EmptyBorder(24, 30, 24, 30)); setContentPane(root);
            root.add(gate, BorderLayout.CENTER); root.add(buildConsole(), BorderLayout.EAST);
            new javax.swing.Timer(80, e -> { gate.tick(); status.setText("●  " + tr("SIGNAL STABLE", "SIGNAL STABLE", "SIGNAL STABIL")); }).start();
            applyText();
        }

        private JPanel buildConsole() {
            JPanel p = new JPanel(); p.setOpaque(false); p.setPreferredSize(new Dimension(385, 0)); p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
            JLabel eyebrow = label("CHEYENNE MOUNTAIN / DEEP SPACE LINK", 12, new Color(73, 215, 255));
            JLabel title = label("FIRST CONTACT", 34, Color.WHITE); p.add(eyebrow); p.add(Box.createVerticalStrut(8)); p.add(title); p.add(Box.createVerticalStrut(12));
            JLabel copy = label("A tiny Java transmission across the stars.", 15, new Color(162, 181, 201)); p.add(copy); p.add(Box.createVerticalStrut(38));
            JLabel prompt = label("CALL SIGN", 12, new Color(115, 234, 213)); p.add(prompt); p.add(Box.createVerticalStrut(8));
            name.setMaximumSize(new Dimension(Integer.MAX_VALUE, 46)); name.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 16)); name.setBackground(new Color(11, 29, 48)); name.setForeground(Color.WHITE); name.setCaretColor(Color.WHITE); name.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(39, 102, 128)), new EmptyBorder(8, 12, 8, 12))); name.addActionListener(e -> transmit()); p.add(name); p.add(Box.createVerticalStrut(14));
            JButton send = button("OPEN VORTEX"); send.addActionListener(e -> transmit()); p.add(send); p.add(Box.createVerticalStrut(25));
            result.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20)); result.setForeground(new Color(255, 197, 92)); result.setAlignmentX(Component.LEFT_ALIGNMENT); result.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70)); p.add(result); p.add(Box.createVerticalGlue());
            status.setForeground(new Color(115, 234, 213)); status.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12)); p.add(status); p.add(Box.createVerticalStrut(14));
            JPanel actions = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0)); actions.setOpaque(false); JButton settings = button("⚙ SETTINGS"); JButton help = button("? HELP"); settings.addActionListener(e -> settings()); help.addActionListener(e -> help()); actions.add(settings); actions.add(help); p.add(actions);
            return p;
        }

        private void transmit() { result.setText("<html><div style='text-align:center'>" + greet(name.getText(), locale, LocalTime.now()) + "</div></html>"); gate.pulse(); name.selectAll(); name.requestFocusInWindow(); }
        private void settings() {
            String[] choices = {"English", "Français", "Deutsch"}; int current = switch(locale.getLanguage()){case "fr"->1; case "de"->2; default->0;};
            int selected = JOptionPane.showOptionDialog(this, tr("Choose transmission language", "Choisissez la langue", "Übertragungssprache wählen"), "Settings", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, choices, choices[current]);
            if (selected >= 0) { locale = Locale.forLanguageTag(selected == 1 ? "fr" : selected == 2 ? "de" : "en"); prefs.put("language", locale.getLanguage()); applyText(); }
        }
        private void help() { JOptionPane.showMessageDialog(this, tr("Enter a call sign, then press Enter or OPEN VORTEX.\nKeyboard: Enter transmits · Esc closes dialogs.\nYour language preference stays on this device.", "Entrez un indicatif puis appuyez sur Entrée ou OUVRIR LE VORTEX.\nClavier : Entrée transmet · Échap ferme les fenêtres.\nLa langue reste mémorisée sur cet appareil.", "Rufzeichen eingeben, dann Enter oder VORTEX ÖFFNEN drücken.\nTastatur: Enter sendet · Esc schließt Dialoge.\nDie Sprache bleibt auf diesem Gerät gespeichert."), tr("Mission help", "Aide de mission", "Missionshilfe"), JOptionPane.INFORMATION_MESSAGE); }
        private void applyText() { name.setToolTipText(tr("Enter your call sign", "Entrez votre indicatif", "Rufzeichen eingeben")); result.setText(tr("Ready for first contact.", "Prêt pour le premier contact.", "Bereit für den Erstkontakt.")); }
        private String tr(String en, String fr, String de) { return switch(locale.getLanguage()){case "fr"->fr; case "de"->de; default->en;}; }
        private static JLabel label(String text, int size, Color c) { JLabel l = new JLabel(text); l.setForeground(c); l.setFont(new Font(Font.SANS_SERIF, Font.BOLD, size)); l.setAlignmentX(Component.LEFT_ALIGNMENT); return l; }
        private static JButton button(String text) { JButton b = new JButton(text); b.setFont(new Font(Font.MONOSPACED, Font.BOLD, 13)); b.setForeground(new Color(3, 19, 30)); b.setBackground(new Color(79, 217, 235)); b.setFocusPainted(false); b.setBorder(new EmptyBorder(11, 16, 11, 16)); b.setAlignmentX(Component.LEFT_ALIGNMENT); b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); return b; }
    }

    /** A custom-painted gate avoids external image assets and demonstrates Java2D animation. */
    static final class GatePanel extends JPanel {
        private double angle; private float energy;
        GatePanel(){ setOpaque(false); setPreferredSize(new Dimension(500,500)); }
        void tick(){ angle += .018; energy *= .94f; repaint(); }
        void pulse(){ energy = 1f; }
        protected void paintComponent(Graphics raw){ super.paintComponent(raw); Graphics2D g=(Graphics2D)raw.create(); g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON); int s=Math.min(getWidth(),getHeight())-70,cx=getWidth()/2,cy=getHeight()/2; g.translate(cx,cy); g.rotate(angle);
            for(int i=0;i<9;i++){ double a=i*Math.PI*2/9; int x=(int)(Math.cos(a)*s*.43),y=(int)(Math.sin(a)*s*.43); g.setColor(i<7?new Color(255,178,61):new Color(35,71,93)); Polygon p=new Polygon(new int[]{x-9,x,x+9},new int[]{y+7,y-14,y+7},3); g.fill(p); }
            g.setStroke(new BasicStroke(18)); g.setColor(new Color(25,65,84)); g.draw(new Ellipse2D.Double(-s*.43,-s*.43,s*.86,s*.86)); g.setStroke(new BasicStroke(3)); g.setColor(new Color(70,206,230)); g.draw(new Ellipse2D.Double(-s*.37,-s*.37,s*.74,s*.74)); g.rotate(-angle);
            RadialGradientPaint paint=new RadialGradientPaint(new Point2D.Double(0,0),(float)(s*.34),new float[]{0,.7f,1},new Color[]{new Color(120,235,255,120+(int)(energy*100)),new Color(16,106,153,180),new Color(2,17,34,240)}); g.setPaint(paint); g.fill(new Ellipse2D.Double(-s*.34,-s*.34,s*.68,s*.68)); g.setColor(new Color(180,245,255,100)); for(int i=0;i<6;i++){int y=(int)(Math.sin(angle*3+i)*s*.07+i*s*.06-s*.17);g.draw(new Arc2D.Double(-s*.28,y-s*.05,s*.56,s*.1,0,180,Arc2D.OPEN));} g.dispose(); }
    }
}
