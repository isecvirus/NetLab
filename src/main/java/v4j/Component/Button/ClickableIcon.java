package v4j.Component.Button;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import jiconfont.icons.font_awesome.FontAwesome;
import jiconfont.swing.IconFontSwing;

/**
 *
 * @author virus
 */
public class ClickableIcon extends JLabel {
    
    private ICON icon;
    private boolean isClicked = false;
    private Color ClickedColor = new Color(255, 255, 255);
    private Color DefaultColor = new Color(255, 255, 255);
    private int size = 25;
    private Runnable onSelect, onDeselect;
    private int clickButton = MouseEvent.BUTTON1;

    public Color getClickedColor() {
        return ClickedColor;
    }

    public void setClickedColor(Color ClickedColor) {
        this.ClickedColor = ClickedColor;
    }

    public Color getDefaultColor() {
        return DefaultColor;
    }

    public void setDefaultColor(Color DefaultColor) {
        this.DefaultColor = DefaultColor;
    }
    
    public enum BUTTON {
        LEFT,
        MIDDLE,
        RIGHT,
        BUTTON4,
        BUTTON5
    }
    
    public enum ICON {
        ADDRESS_BOOK(FontAwesome.ADDRESS_BOOK_O, FontAwesome.ADDRESS_BOOK),
        ID_CARD(FontAwesome.ADDRESS_CARD_O, FontAwesome.ADDRESS_CARD),
        TOP_ARROW(FontAwesome.ARROW_CIRCLE_O_UP, FontAwesome.ARROW_CIRCLE_UP),
        RIGHT_ARROW(FontAwesome.ARROW_CIRCLE_O_RIGHT, FontAwesome.ARROW_CIRCLE_RIGHT),
        BOTTOM_ARROW(FontAwesome.ARROW_CIRCLE_O_DOWN, FontAwesome.ARROW_CIRCLE_DOWN),
        LEFT_ARROW(FontAwesome.ARROW_CIRCLE_O_LEFT, FontAwesome.ARROW_CIRCLE_LEFT),
        BATTERY(FontAwesome.BATTERY_EMPTY, FontAwesome.BATTERY_FULL),
        BELL(FontAwesome.BELL_O, FontAwesome.BELL),
        MUTED_BELL(FontAwesome.BELL_SLASH_O, FontAwesome.BELL_SLASH),
        BOOKMARK(FontAwesome.BOOKMARK_O, FontAwesome.BOOKMARK),
        BUILDING(FontAwesome.BUILDING_O, FontAwesome.BUILDING),
        CIRCLE(FontAwesome.CIRCLE_THIN, FontAwesome.CIRCLE),
        COMMENTS(FontAwesome.COMMENTS_O, FontAwesome.COMMENTS),
        TYPING(FontAwesome.COMMENTING_O, FontAwesome.COMMENTING),
        COMMENT(FontAwesome.COMMENT_O, FontAwesome.COMMENT),
        MAIL(FontAwesome.ENVELOPE_O, FontAwesome.ENVELOPE),
        MAIL_OPEN(FontAwesome.ENVELOPE_OPEN_O, FontAwesome.ENVELOPE_OPEN),
        SCRIPT(FontAwesome.FILE_TEXT_O, FontAwesome.FILE_TEXT),
        FLAG(FontAwesome.FLAG_O, FontAwesome.FLAG),
        FOLDER(FontAwesome.FOLDER_O, FontAwesome.FOLDER),
        FOLDER_OPEN(FontAwesome.FOLDER_OPEN_O, FontAwesome.FOLDER_OPEN),
        OPEN_FOLDER(FontAwesome.FOLDER, FontAwesome.FOLDER_OPEN),
        HEART(FontAwesome.HEART_O, FontAwesome.HEART),
        HOURGLASS(FontAwesome.HOURGLASS_O, FontAwesome.HOURGLASS),
        MAP(FontAwesome.MAP_O, FontAwesome.MAP),
        MICROPHONE(FontAwesome.MICROPHONE, FontAwesome.MICROPHONE_SLASH),
        PAPER_PLANE(FontAwesome.PAPER_PLANE_O, FontAwesome.PAPER_PLANE),
        PAUSE_CIRCLE(FontAwesome.PAUSE_CIRCLE_O, FontAwesome.PAUSE_CIRCLE),
        PLAY_CIRCLE(FontAwesome.PLAY_CIRCLE_O, FontAwesome.PLAY_CIRCLE),
        STOP_CIRCLE(FontAwesome.STOP_CIRCLE_O, FontAwesome.STOP_CIRCLE),
        QUESTION_CIRCLE(FontAwesome.QUESTION_CIRCLE_O, FontAwesome.QUESTION_CIRCLE),
        REPLY(FontAwesome.REPLY, FontAwesome.REPLY_ALL),
        SIGN(FontAwesome.SIGN_IN, FontAwesome.SIGN_OUT),
        STAR(FontAwesome.STAR_O, FontAwesome.STAR),
        STICKY_NOTE(FontAwesome.STICKY_NOTE_O, FontAwesome.STICKY_NOTE),
        TAG(FontAwesome.TAG, FontAwesome.TAGS),
        THERMOMETER(FontAwesome.THERMOMETER_EMPTY, FontAwesome.THERMOMETER_FULL),
        LIKE(FontAwesome.THUMBS_O_UP, FontAwesome.THUMBS_UP),
        DISLIKE(FontAwesome.THUMBS_O_DOWN, FontAwesome.THUMBS_DOWN),
        TOGGLE(FontAwesome.TOGGLE_OFF, FontAwesome.TOGGLE_ON),
        TRASH(FontAwesome.TRASH_O, FontAwesome.TRASH),
        USER_CIRCLE(FontAwesome.USER_CIRCLE_O, FontAwesome.USER_CIRCLE),
        USER(FontAwesome.USER, FontAwesome.USERS),
        USER_ADD(FontAwesome.USER_PLUS, FontAwesome.USER_TIMES),
        VOLUME(FontAwesome.VOLUME_UP, FontAwesome.VOLUME_OFF);

        private final FontAwesome primaryIcon;
        private final FontAwesome secondaryIcon;

        ICON(FontAwesome primaryIcon, FontAwesome secondaryIcon) {
            this.primaryIcon = primaryIcon;
            this.secondaryIcon = secondaryIcon;
        }

        public FontAwesome getPrimaryIcon() {
            return primaryIcon;
        }

        public FontAwesome getSecondaryIcon() {
            return secondaryIcon;
        }
    }

    private Map<ICON, List<FontAwesome>> ICONS = Map.ofEntries(
            Map.entry(ICON.ADDRESS_BOOK, Arrays.asList(FontAwesome.ADDRESS_BOOK_O, FontAwesome.ADDRESS_BOOK)),
            Map.entry(ICON.ID_CARD, Arrays.asList(FontAwesome.ADDRESS_CARD_O, FontAwesome.ADDRESS_CARD)),
            Map.entry(ICON.TOP_ARROW, Arrays.asList(FontAwesome.ARROW_CIRCLE_O_UP, FontAwesome.ARROW_CIRCLE_UP)),
            Map.entry(ICON.RIGHT_ARROW, Arrays.asList(FontAwesome.ARROW_CIRCLE_O_RIGHT, FontAwesome.ARROW_CIRCLE_RIGHT)),
            Map.entry(ICON.BOTTOM_ARROW, Arrays.asList(FontAwesome.ARROW_CIRCLE_O_DOWN, FontAwesome.ARROW_CIRCLE_DOWN)),
            Map.entry(ICON.LEFT_ARROW, Arrays.asList(FontAwesome.ARROW_CIRCLE_O_LEFT, FontAwesome.ARROW_CIRCLE_LEFT)),
            Map.entry(ICON.BATTERY, Arrays.asList(FontAwesome.BATTERY_EMPTY, FontAwesome.BATTERY_FULL)),
            Map.entry(ICON.BELL, Arrays.asList(FontAwesome.BELL_O, FontAwesome.BELL)),
            Map.entry(ICON.MUTED_BELL, Arrays.asList(FontAwesome.BELL_SLASH_O, FontAwesome.BELL_SLASH)),
            Map.entry(ICON.BOOKMARK, Arrays.asList(FontAwesome.BOOKMARK_O, FontAwesome.BOOKMARK)),
            Map.entry(ICON.BUILDING, Arrays.asList(FontAwesome.BUILDING_O, FontAwesome.BUILDING)),
            Map.entry(ICON.CIRCLE, Arrays.asList(FontAwesome.CIRCLE_THIN, FontAwesome.CIRCLE)),
            Map.entry(ICON.COMMENTS, Arrays.asList(FontAwesome.COMMENTS_O, FontAwesome.COMMENTS)),
            Map.entry(ICON.TYPING, Arrays.asList(FontAwesome.COMMENTING_O, FontAwesome.COMMENTING)),
            Map.entry(ICON.COMMENT, Arrays.asList(FontAwesome.COMMENT_O, FontAwesome.COMMENT)),
            Map.entry(ICON.MAIL, Arrays.asList(FontAwesome.ENVELOPE_O, FontAwesome.ENVELOPE)),
            Map.entry(ICON.MAIL_OPEN, Arrays.asList(FontAwesome.ENVELOPE_OPEN_O, FontAwesome.ENVELOPE_OPEN)),
            Map.entry(ICON.SCRIPT, Arrays.asList(FontAwesome.FILE_TEXT_O, FontAwesome.FILE_TEXT)),
            Map.entry(ICON.FLAG, Arrays.asList(FontAwesome.FLAG_O, FontAwesome.FLAG)),
            Map.entry(ICON.FOLDER, Arrays.asList(FontAwesome.FOLDER_O, FontAwesome.FOLDER)),
            Map.entry(ICON.FOLDER_OPEN, Arrays.asList(FontAwesome.FOLDER_OPEN_O, FontAwesome.FOLDER_OPEN)),
            Map.entry(ICON.OPEN_FOLDER, Arrays.asList(FontAwesome.FOLDER, FontAwesome.FOLDER_OPEN)),
            Map.entry(ICON.HEART, Arrays.asList(FontAwesome.HEART_O, FontAwesome.HEART)),
            Map.entry(ICON.HOURGLASS, Arrays.asList(FontAwesome.HOURGLASS_O, FontAwesome.HOURGLASS)),
            Map.entry(ICON.MAP, Arrays.asList(FontAwesome.MAP_O, FontAwesome.MAP)),
            Map.entry(ICON.MICROPHONE, Arrays.asList(FontAwesome.MICROPHONE, FontAwesome.MICROPHONE_SLASH)),
            Map.entry(ICON.PAPER_PLANE, Arrays.asList(FontAwesome.PAPER_PLANE_O, FontAwesome.PAPER_PLANE)),
            Map.entry(ICON.PAUSE_CIRCLE, Arrays.asList(FontAwesome.PAUSE_CIRCLE_O, FontAwesome.PAUSE_CIRCLE)),
            Map.entry(ICON.PLAY_CIRCLE, Arrays.asList(FontAwesome.PLAY_CIRCLE_O, FontAwesome.PLAY_CIRCLE)),
            Map.entry(ICON.STOP_CIRCLE, Arrays.asList(FontAwesome.STOP_CIRCLE_O, FontAwesome.STOP_CIRCLE)),
            Map.entry(ICON.QUESTION_CIRCLE, Arrays.asList(FontAwesome.QUESTION_CIRCLE_O, FontAwesome.QUESTION_CIRCLE)),
            Map.entry(ICON.REPLY, Arrays.asList(FontAwesome.REPLY, FontAwesome.REPLY_ALL)),
            Map.entry(ICON.SIGN, Arrays.asList(FontAwesome.SIGN_IN, FontAwesome.SIGN_OUT)),
            Map.entry(ICON.STAR, Arrays.asList(FontAwesome.STAR_O, FontAwesome.STAR)),
            Map.entry(ICON.STICKY_NOTE, Arrays.asList(FontAwesome.STICKY_NOTE_O, FontAwesome.STICKY_NOTE)),
            Map.entry(ICON.TAG, Arrays.asList(FontAwesome.TAG, FontAwesome.TAGS)),
            Map.entry(ICON.THERMOMETER, Arrays.asList(FontAwesome.THERMOMETER_EMPTY, FontAwesome.THERMOMETER_FULL)),
            Map.entry(ICON.LIKE, Arrays.asList(FontAwesome.THUMBS_O_UP, FontAwesome.THUMBS_UP)),
            Map.entry(ICON.DISLIKE, Arrays.asList(FontAwesome.THUMBS_O_DOWN, FontAwesome.THUMBS_DOWN)),
            Map.entry(ICON.TOGGLE, Arrays.asList(FontAwesome.TOGGLE_OFF, FontAwesome.TOGGLE_ON)),
            Map.entry(ICON.TRASH, Arrays.asList(FontAwesome.TRASH_O, FontAwesome.TRASH)),
            Map.entry(ICON.USER_CIRCLE, Arrays.asList(FontAwesome.USER_CIRCLE_O, FontAwesome.USER_CIRCLE)),
            Map.entry(ICON.USER, Arrays.asList(FontAwesome.USER, FontAwesome.USERS)),
            Map.entry(ICON.USER_ADD, Arrays.asList(FontAwesome.USER_PLUS, FontAwesome.USER_TIMES)),
            Map.entry(ICON.VOLUME, Arrays.asList(FontAwesome.VOLUME_UP, FontAwesome.VOLUME_OFF))
    );

    public ClickableIcon() {
        IconFontSwing.register(FontAwesome.getIconFont());
    }
    
    public ClickableIcon(int size) {
        IconFontSwing.register(FontAwesome.getIconFont());
        
        setIconSize(size);
        setPreferredSize(new Dimension(size, size));
    }
    
    public ClickableIcon(int size, Color def, Color clk) {
        IconFontSwing.register(FontAwesome.getIconFont());
        
        setIconSize(size);
        setPreferredSize(new Dimension(size, size));
        
        this.DefaultColor = def;
        this.ClickedColor = clk;
    }
    
    public ClickableIcon(Color def, Color clk) {
        IconFontSwing.register(FontAwesome.getIconFont());
        
        this.DefaultColor = def;
        this.ClickedColor = clk;
    }
    
    public void refresh() {
        repaint();
    }

    public void setIcon(ICON i) {
        this.icon = i;

        FontAwesome a;
        FontAwesome b;

        if (icon != null) {
            a = ICONS.get(icon).get(0);
            b = ICONS.get(icon).get(1);

            Icon new_icon = IconFontSwing.buildIcon(a, getIconSize(), getDefaultColor());

            setIcon(new_icon);

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getButton() == clickButton) {
                        if (isClicked == true) { // unclicked
                            Icon new_icon = IconFontSwing.buildIcon(a, getIconSize(), getDefaultColor());

                            setIcon(new_icon);
                            isClicked = false;
                            if (onDeselect != null) {
                                onDeselect.run();
                            }
                        } else { // clicked
                            Icon new_icon = IconFontSwing.buildIcon(b, getIconSize(), getClickedColor());

                            setIcon(new_icon);
                            isClicked = true;

                            if (onSelect != null) {
                                onSelect.run();
                            }
                        }
                    }
                }
            });
        }
        refresh();

    }

    public int getIconSize() {
        return size;
    }

    public void setIconSize(int size) {
        this.size = size;
    }

    public Runnable getOnSelect() {
        return onSelect;
    }

    public void setOnSelect(Runnable onSelect) {
        this.onSelect = onSelect;
    }

    public Runnable getOnDeselect() {
        return onDeselect;
    }

    public void setOnDeselect(Runnable onDeselect) {
        this.onDeselect = onDeselect;
    }

    public int getClickButton() {
        return clickButton;
    }

    public void setClickButton(BUTTON b) {
        if (b == BUTTON.LEFT) {
            this.clickButton = MouseEvent.BUTTON1;
        } else if (b == BUTTON.MIDDLE) {
            this.clickButton = MouseEvent.BUTTON2;
        } else if (b == BUTTON.RIGHT) {
            this.clickButton = MouseEvent.BUTTON3;
        } else if (b == BUTTON.BUTTON4) {
            this.clickButton = 4;
        } else if (b == BUTTON.BUTTON5) {
            this.clickButton = 5;
        }
    }
}