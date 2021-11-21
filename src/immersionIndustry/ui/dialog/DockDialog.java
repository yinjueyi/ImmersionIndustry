package immersionIndustry.ui;

import arc.*;
import arc.graphics.*;
import arc.util.*;
import arc.struct.*;
import arc.graphics.g2d.*;
import arc.scene.ui.*;
import arc.scene.ui.layout.*;
import arc.math.Angles;
import mindustry.type.*;
import mindustry.graphics.*;
import mindustry.gen.*;
import mindustry.ui.*;
import mindustry.ui.dialogs.*;
import mindustry.content.*;

import immersionIndustry.IMColors;
import immersionIndustry.contents.blocks.IMBlocks;
import immersionIndustry.types.blocks.distribution.DockBlock.DockBlockBuild;
import immersionIndustry.contents.IMItems;

public class DockDialog extends BaseDialog {
  
  ItemStack[][] upgradeItems = {
    ItemStack.with(Items.silicon, 25,Items.titanium,12)
  };
  
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
    width = Core.graphics.isPortrait() ? 120f : 110f;
    height = Core.graphics.isPortrait() ? 330f : 600f;
    unlocked.add(new UpgradeItem(IMColors.colorPrimary,IMBlocks.dock.region,"升级","将此方块升级",new Table() {
      {
        add("方块").right().row();
        ItemStack[] items = upgradeItems[entity.level];
        for(ItemStack stack : items) {
          add(new ItemImage(stack)).right();
        }
      }
    })).size(width, height).padTop(5);
    
    unlocked.add(new UpgradeItem(IMColors.colorPrimary,IMBlocks.dock.region,"升级","将此单位升级",new Table() {
      {
        add("单位").right().row();
        ItemStack[] items = upgradeItems[entity.level];
        for(ItemStack stack : items) {
          add(new ItemImage(stack)).right();
        }
      }
    })).size(width, height).padTop(5).pad(width/2);
    
    Table unlock = new Table();
    unlock.add(Core.bundle.get("dockdialog-unlock"));
    unlock.row();
    
    cont.add(unlocked).row();
    cont.add(unlock).row();
    
    addCloseButton();
  }
  
  public class UpgradeItem extends Table {
    
    float width,height;
    
    public UpgradeItem(Color color,TextureRegion icon,String title,String description,Table additional) {
      super(Tex.underline);
      
      width = Core.graphics.isPortrait() ? 120f : 110f;
      height = Core.graphics.isPortrait() ? 330f : 600f;
      
      margin(0);
      table(card -> {
        card.table(title -> {
          title.add(title).growX().left();
          title.row();
          title.labelWrap(description).width(width - 100f).color(Color.lightGray).growX();
        });
        card.row();
        card.table(img -> {
          img.image().height(35).width(40f).color(color);
          img.row();
          img.image().height(5).width(40f).color(color.cpy().mul(0.8f, 0.8f, 0.8f, 1f));
          img.image(icon).size(35, 35);
          img.image().height(35).width(40f).color(color.cpy().mul(0.8f, 0.8f, 0.8f, 1f));
          img.row();
          img.image().height(5).width(40f).color(color);
        });
        card.row();
        card.add(additional);
      });
    }
    
  }
  
}