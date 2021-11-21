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
    width = Core.graphics.isPortrait() ? 120f : 110f;
    height = Core.graphics.isPortrait() ? 330f : 600f;
    unlocked.row();
    unlocked.add(new UpgradeItem(IMColors.colorPrimary,IMBlocks.dock.region,"升级","提升方块的属性",new Table() {
      {
        add("方块").right().row();
        ItemStack[] items = upgradeItems[entity.level];
        for(ItemStack stack : items) {
          add(new ItemImage(stack)).right();
        }
      }
    })).size(width, height).padTop(5);
    
    unlocked.add(new UpgradeItem(IMColors.colorPrimary,IMBlocks.dock.region,"运输船","提升单位属性",new Table() {
      {
        add("单位").right().row();
        ItemStack[] items = upgradeItems[entity.level];
        for(ItemStack stack : items) {
          add(new ItemImage(stack)).right();
        }
      }
    })).size(width, height).padTop(5);
    
    unlocked.add(new UpgradeItem(IMColors.colorPrimary,IMBlocks.dock.region,"浮游炮","增加浮游炮",new Table() {
      {
        add("武器").right().row();
        ItemStack[] items = upgradeItems[entity.level];
        for(ItemStack stack : items) {
          add(new ItemImage(stack)).right();
        }
      }
    })).size(width, height).padTop(5);
    
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
      super(Styles.defaultb);
      
      width = Core.graphics.isPortrait() ? 120f : 110f;
      height = Core.graphics.isPortrait() ? 330f : 600f;
      
      margin(0);
      table(background -> {
        background.setFillParent(true);
        background.image(icon).size(height,height);
      });
      table(card -> {
        card.setFillParent(true);
        card.table(head -> {
          head.add(title).left();
          head.row();
          head.labelWrap(description).color(Color.lightGray).left();
        }).width(width).top();
        card.row();
        card.add(additional).width(width).bottom();
      });
    }
    
  }
  
}