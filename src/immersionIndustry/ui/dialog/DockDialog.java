package immersionIndustry.ui;

import arc.*;
import arc.util.*;
import arc.graphics.g2d.*;
import arc.scene.ui.*;
import arc.scene.ui.layout.*;
import arc.math.Angles;
import mindustry.gen.*;
import mindustry.ui.dialogs.*;

import immersionIndustry.IMColors;
import immersionIndustry.contents.blocks.IMBlocks;
import immersionIndustry.types.blocks.units.DockBlock.DockBlockBuild;

public class DockDialog extends BaseDialog {
  
  DockBlockBuild entity;
  
  public DockDialog(DockBlockBuild entity) {
    super("");
    this.entity = entity;
    titleTable.remove();
    margin(0f).marginBottom(8);
    shouldPause = true;
    
    Table unlocked = new Table();
    unlocked.add(Core.bundle.get("dockdialog-unlocked"));
    unlocked.row();
    unlocked.image(Tex.whiteui, Pal.accent).growX().height(3f).pad(4f);
    unlocked.row();
    float h = Core.graphics.isPortrait() ? 90f : 80f;
    float w = Core.graphics.isPortrait() ? 330f : 600f;
    unlocked.add(new UpgradeItem(iMColors.colorPrimary,IMBlocks.dock.region,"升级","将此方块升级")).size(w, h).padTop(5).row();
    
    Table unlock = new Table();
    unlock.add(Core.bundle.get("dockdialog-unlock"));
    unlock.row();
    
    addCloseButton();
  }
  
  public class UpgradeItem extends Table {
    
    public UpgradeItem(Color color,TextureRegion icon,String title,String description) {
      super(Tex.underline);
      
      float h = Core.graphics.isPortrait() ? 90f : 80f;
      float w = Core.graphics.isPortrait() ? 330f : 600f;
      
      table.margin(0);
      table.table(img -> {
        img.image().height(h - 5).width(40f).color(color);
        img.row();
        img.image().height(5).width(40f).color(color.cpy().mul(0.8f, 0.8f, 0.8f, 1f));
      }).expandY();
      
      table.table(i -> {
        i.background(Tex.buttonEdge3);
        i.image(icon);
      }).size(h - 5, h);
      
      table.table(inset -> {
        inset.add("[accent]" + title).growX().left();
        inset.row();
        inset.labelWrap(description).width(w - 100f).color(Color.lightGray).growX();
      }).padLeft(8);

      table.button(Icon.link, () -> {
        
      }).size(h - 5, h);
    }
    
  }
  
}