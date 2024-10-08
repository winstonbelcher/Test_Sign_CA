/**
 * $ $ License.
 *
 * Copyright $ L2FProd.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package teal.ui.swing.plaf.basic;

import teal.ui.swing.JLinkButton;
import teal.ui.swing.JTaskPaneGroup;
import teal.ui.swing.icons.EmptyIcon;
import teal.ui.swing.plaf.TaskPaneGroupUI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.MouseInputListener;
import javax.swing.plaf.ActionMapUIResource;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicGraphicsUtils;

/**
 * Base implementation of the <code>JTaskPaneGroup</code> UI.
 */
public class BasicTaskPaneGroupUI extends TaskPaneGroupUI {

  private static FocusListener focusListener = new RepaintOnFocus();

  public static ComponentUI createUI(JComponent c) {
    return new BasicTaskPaneGroupUI();
  }

  protected JTaskPaneGroup group;

  protected boolean mouseOver;
  protected MouseInputListener mouseListener;

  protected boolean animationRunning = false;
  protected float animationStage = 0.0f;
  
  public void installUI(JComponent c) {
    super.installUI(c);
    group = (JTaskPaneGroup)c;

    installDefaults();
    installListeners();
    installKeyboardActions();
  }

  protected void installDefaults() {
    group.setOpaque(true);
    group.setBorder(createPaneBorder());
    ((JComponent)group.getContentPane()).setBorder(createContentPaneBorder());

    LookAndFeel.installColorsAndFont(
      group,
      "TaskPaneGroup.background",
      "TaskPaneGroup.foreground",
      "TaskPaneGroup.font");

    LookAndFeel.installColorsAndFont(
      (JComponent)group.getContentPane(),
      "TaskPaneGroup.background",
      "TaskPaneGroup.foreground",
      "TaskPaneGroup.font");    
  }

  protected void installListeners() {
    mouseListener = createMouseInputListener();
    group.addMouseMotionListener(mouseListener);
    group.addMouseListener(mouseListener);

    group.addFocusListener(focusListener);
  }

  protected void installKeyboardActions() {
    InputMap inputMap = (InputMap)UIManager.get("TaskPaneGroup.focusInputMap");
    if (inputMap != null) {
      SwingUtilities.replaceUIInputMap(
        group,
        JComponent.WHEN_FOCUSED,
        inputMap);
    }

    ActionMap map = getActionMap();
    if (map != null) {
      SwingUtilities.replaceUIActionMap(group, map);
    }
  }

  ActionMap getActionMap() {
    ActionMap map = new ActionMapUIResource();
    map.put("toggleExpanded", new ToggleExpandedAction());
    return map;
  }

  public void uninstallUI(JComponent c) {
    uninstallListeners();
    super.uninstallUI(c);
  }

  protected void uninstallListeners() {
    group.removeMouseListener(mouseListener);
    group.removeMouseMotionListener(mouseListener);
    group.removeFocusListener(focusListener);
  }

  protected MouseInputListener createMouseInputListener() {
    return new ToggleListener();
  }

  protected boolean isInBorder(MouseEvent event) {
    return event.getY() < getTitleHeight();
  }

  protected int getTitleHeight() {
    return 25;
  }

  protected Border createPaneBorder() {
    return new PaneBorder();
  }

  protected Border createContentPaneBorder() {
    Color borderColor = UIManager.getColor("TaskPaneGroup.borderColor");
    return new CompoundBorder(new ContentPaneBorder(borderColor), BorderFactory
      .createEmptyBorder(10, 10, 10, 10));
  }
  
  public Component createAction(Action action) {
    JLinkButton button = new JLinkButton(action);
    button.setOpaque(false);
    button.setBorder(null);
    button.setBorderPainted(false);
    button.setFocusPainted(true);
    button.setForeground(UIManager.getColor("TaskPaneGroup.titleForeground"));
    return button;
  }

  protected void ensureVisible() {
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
      group.scrollRectToVisible(
        new Rectangle(group.getWidth(), group.getHeight()));
      }
    });
  }
  
  static class RepaintOnFocus implements FocusListener {
    public void focusGained(FocusEvent e) {
      e.getComponent().repaint();
    }
    public void focusLost(FocusEvent e) {
      e.getComponent().repaint();
    }
  }
  
  class ToggleListener extends MouseInputAdapter {
    public void mouseEntered(MouseEvent e) {
      if (isInBorder(e)) {
        e.getComponent().setCursor(
          Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
      } else {
        mouseOver = false;
        group.repaint();
      }
    }
    public void mouseExited(MouseEvent e) {
      e.getComponent().setCursor(Cursor.getDefaultCursor());
      mouseOver = false;
      group.repaint();
    }
    public void mouseMoved(MouseEvent e) {
      if (isInBorder(e)) {
        e.getComponent().setCursor(
          Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        mouseOver = true;
        group.repaint();
      } else {
        e.getComponent().setCursor(Cursor.getDefaultCursor());
        mouseOver = false;
        group.repaint();
      }
    }
    public void mouseReleased(MouseEvent e) {
      if (isInBorder(e)) {
        group.setExpanded(!group.isExpanded());
      }
    }
  }
  
  class ToggleExpandedAction extends AbstractAction {
    public ToggleExpandedAction() {
      super("toggleExpanded");
    }
    public void actionPerformed(ActionEvent e) {
      group.setExpanded(!group.isExpanded());
    }
    public boolean isEnabled() {
      return group.isVisible();
    }
  }

  protected static class ChevronIcon implements Icon {
    boolean up = true;
    public ChevronIcon(boolean up) {
      this.up = up;
    }
    public int getIconHeight() {
      return 3;
    }
    public int getIconWidth() {
      return 6;
    }
    public void paintIcon(Component c, Graphics g, int x, int y) {
      if (up) {
        g.drawLine(x + 3, y, x, y + 3);
        g.drawLine(x + 3, y, x + 6, y + 3);
      } else {
        g.drawLine(x, y, x + 3, y + 3);
        g.drawLine(x + 3, y + 3, x + 6, y);
      }
    }
  }

  protected static int getTitleHeight(Component c) {
    return ((BasicTaskPaneGroupUI) ((JTaskPaneGroup)c).getUI())
      .getTitleHeight();
  }

  /**
   * The border around the content pane
   */
  protected static class ContentPaneBorder implements Border {
    Color color;
    public ContentPaneBorder(Color color) {
      this.color = color;
    }
    public Insets getBorderInsets(Component c) {
      return new Insets(0, 1, 1, 1);
    }
    public boolean isBorderOpaque() {
      return true;
    }
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
      g.setColor(color);
      g.drawLine(x, y, x, y + height - 1);
      g.drawLine(x, y + height - 1, x + width - 1, y + height - 1);
      g.drawLine(x + width - 1, y, x + width - 1, y + height - 1);
    }
  }
  
  /**
   * The border of the taskpane group paints the "text", the "icon", the
   * "expanded" status and the "special" type.
   *  
   */
  protected static class PaneBorder implements Border {

    protected Color borderColor;
    protected Color titleForeground;
    protected Color specialTitleBackground;
    protected Color specialTitleForeground;
    protected Color titleBackgroundGradientStart;
    protected Color titleBackgroundGradientEnd;

    protected Color titleOver;
    protected Color specialTitleOver;
    
    public PaneBorder() {
      borderColor = UIManager.getColor("TaskPaneGroup.borderColor");      

      titleForeground = UIManager.getColor("TaskPaneGroup.titleForeground");

      specialTitleBackground = UIManager
        .getColor("TaskPaneGroup.specialTitleBackground");
      specialTitleForeground = UIManager
        .getColor("TaskPaneGroup.specialTitleForeground");

      titleBackgroundGradientStart = UIManager
        .getColor("TaskPaneGroup.titleBackgroundGradientStart");
      titleBackgroundGradientEnd = UIManager
        .getColor("TaskPaneGroup.titleBackgroundGradientEnd");
      
      titleOver = UIManager.getColor("TaskPaneGroup.titleOver");
      if (titleOver == null) {
        titleOver = specialTitleBackground.brighter();
      }
      specialTitleOver = UIManager.getColor("TaskPaneGroup.specialTitleOver");
      if (specialTitleOver == null) {
        specialTitleOver = specialTitleBackground.brighter();
      }
    }
    
    public Insets getBorderInsets(Component c) {
      return new Insets(getTitleHeight(c), 0, 0, 0);
    }

    public boolean isBorderOpaque() {
      return true;
    }

    protected void paintTitleBackground(JTaskPaneGroup group, Graphics g) {
      if (group.isSpecial()) {
        g.setColor(specialTitleBackground);
      } else {
        g.setColor(titleBackgroundGradientStart);
      }
      g.fillRect(0, 0, group.getWidth(), getTitleHeight(group) - 1);
    }

    protected void paintTitle(
      JTaskPaneGroup group,
      Graphics g,
      Color textColor,
      int x,
      int y,
      int width,
      int height) {
      JLabel label = new JLabel();
      label.setOpaque(false);
      label.setForeground(textColor);
      label.setFont(g.getFont());
      label.setIconTextGap(8);
      label.setText(group.getText());
      label.setIcon(
        group.getIcon() == null ? new EmptyIcon() : group.getIcon());
      g.translate(x, y);
      label.setBounds(0, 0, width, height);
      label.paint(g);
      g.translate(-x, -y);
    }

    protected void paintExpandedControls(JTaskPaneGroup group, Graphics g) {
    }

    public void paintBorder(
      Component c,
      Graphics g,
      int x,
      int y,
      int width,
      int height) {

      JTaskPaneGroup group = (JTaskPaneGroup)c;

      // paint the title background
      paintTitleBackground(group, g);

      // paint the the toggles
      paintExpandedControls(group, g);

      // paint the title text and icon
      Color paintColor;
      if (group.isSpecial()) {
        paintColor = specialTitleForeground;
      } else {
        paintColor = titleForeground;
      }

      // focus painted same color as text
      if (group.hasFocus()) {
        g.setColor(paintColor);
        BasicGraphicsUtils.drawDashedRect(
          g,
          3,
          3,
          width - 6,
          getTitleHeight(c) - 6);
      }

      paintTitle(
        group,
        g,
        paintColor,
        3,
        0,
        c.getWidth() - getTitleHeight(c) - 3,
        getTitleHeight(c));
    }
  }

}
