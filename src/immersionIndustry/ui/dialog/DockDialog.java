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
    unlocked.add(new UpgradeItem(IMColors.colorPrimary,IMBlocks.dock.region,"升级","提升方块的属性",new Table() {
      {
        add("方块").row();
        ItemStack[] items = upgradeItems[entity.level];
        for(ItemStack stack : items) {
          add(new ItemImage(stack));
        }
      }
    })).size(width, height).padTop(5);
    
    unlocked.add(new UpgradeItem(IMColors.colorPrimary,IMBlocks.dock.region,"运输船","提升单位属性",new Table() {
      {
        add("单位").row();
        ItemStack[] items = upgradeItems[entity.level];
        for(ItemStack stack : items) {
          add(new ItemImage(stack));
        }
      }
    })).size(width, height).padTop(5).pad(width/2);
    
    unlocked.add(new UpgradeItem(IMColors.colorPrimary,IMBlocks.dock.region,"浮游炮","增加浮游炮",new Table() {
      {
        add("武器").row();
        ItemStack[] items = upgradeItems[entity.level];
        for(ItemStack stack : items) {
          add(new ItemImage(stack));
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
        card.table(head -> {
          head.add(title).growX();
          head.row();
          head.labelWrap(description).color(Color.lightGray).growX();
        });
        card.row();
        card.table(img -> {
          img.image().height(width-5).width(width).color(color.cpy().mul(0.8f, 0.8f, 0.8f, 1f));
          img.row();
          img.image().height(5).width(width).color(color);
          img.row();
          img.image(icon).size(width, width);
          img.row();
          img.image().height(5).width(width).color(color);
          img.row();
          img.image().height(width-5).width(width).color(color.cpy().mul(0.8f, 0.8f, 0.8f, 1f));
        });
        card.row();
        card.add(additional);
      });
    }
    
  }
  
}