package immersionIndustry.ui;

import arc.*;
import arc.func.*;
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

import static mindustry.Vars.*;

import immersionIndustry.IMColors;
import immersionIndustry.contents.blocks.IMBlocks;
import immersionIndustry.types.blocks.distribution.DockBlock.DockBlockBuild;
import mindustry.world.blocks.storage.CoreBlock.CoreBuild;
import immersionIndustry.contents.IMItems;

public class DockDialog extends BaseDialog {
  
  UpgradeData[] datas = {
    new UpgradeData("升级","从0级升到1级",ItemStack.with(Items.silicon, 25,Items.titanium,12),0,entity -> {
      entity.level = 1;
    },entity -> {
      return entity.level >= 1;
    }),
    new UpgradeData("升级","从1级升到2级",ItemStack.with(Items.silicon, 35,Items.titanium,22),1,entity -> {
      entity.level = 2;
    },entity -> {
      return entity.level >= 2;
    }),
    new UpgradeData("加速装置","增加一个装置",ItemStack.with(Items.silicon, 65,Items.titanium,18),1,entity -> {},entity -> {
      return true;
    }),
  };
  
  DockBlockBuild entity;
  
  public DockDialog(DockBlockBuild entity) {
    super("");
    this.entity = entity;
    titleTable.remove();
    margin(0f).marginBottom(8);
    shouldPause = true;
    
    setup();
    
    addCloseButton();
  }
  
  public void setup() {
    cont.clear();
    Table unlocked = new Table();
    unlocked.add(Core.bundle.get("dockdialog-unlocked"));
    unlocked.row();
    unlocked.image(Tex.whiteui, Pal.accent).growX().height(3f).pad(4f);
    unlocked.row();
    Table unlock = new Table();
    unlock.add(Core.bundle.get("dockdialog-unlock"));
    unlock.row();
    float h = Core.graphics.isPortrait() ? 90f : 80f;
    float w = Core.graphics.isPortrait() ? 330f : 600f;
    for(UpgradeData data : datas) {
      if(data.canShow()) {
        unlocked.add(new UpgradeItem(data,true)).size(w, h).padTop(5).row();
      }else if(data.b.get(entity)){
        data.color = IMColors.colorYellow;
        unlock.add(new UpgradeItem(data,false)).size(w, h).padTop(5).row();
      }
    }
    
    cont.add(unlocked).row();
    cont.add(unlock).row();
  }
  
  public class UpgradeItem extends Table {
    
    public UpgradeItem(UpgradeData data,boolean bool) {
      super(Tex.underline);
      
      float h = Core.graphics.isPortrait() ? 90f : 80f;
      float w = Core.graphics.isPortrait() ? 330f : 600f;
      
      margin(0);
      table(img -> {
        img.image().height(h - 5).width(40f).color(data.color);
        img.row();
        img.image().height(5).width(40f).color(data.color.cpy().mul(0.8f, 0.8f, 0.8f, 1f));
      }).expandY();
      
      table(i -> {
        i.background(Tex.buttonEdge3);
        i.image(Icon.info);
      }).size(h - 5, h);
      
      table(inset -> {
        inset.add("[accent]" + data.title).growX().left();
        inset.row();
        inset.labelWrap(data.description).width(w - 100f).color(Color.lightGray).growX();
        inset.row();
        inset.table(end -> {
          ItemStack[] stacks = data.stack;
          for(ItemStack stack : stacks) {
            add(new ItemImage(stack));
          }
        });
      }).padLeft(8);

      if(bool) {
        button("解锁", () -> {
          CoreBuild core = player.core();
          ItemStack[] stacks = data.stack;
          boolean unlock = true;
          for(ItemStack stack : stacks) {
            if(!core.items.has(stack.item,stack.amount)) {
              unlock = false;
            }
          }
          if(unlock) data.unlockItem();
        }).size(h - 5, h);
      }else {
        add("已解锁").size(h - 5, h);
      }
    }
    
  }
  
  public class UpgradeData {
    
    public String title,description;
    public Color color;
    public ItemStack[] stack;
    public int ask = 0;
    Cons<DockBlockBuild> p;
    public Boolf<DockBlockBuild> b;
    
    public UpgradeData(String title,String description,ItemStack[] stack,int ask,Cons<DockBlockBuild> p,Boolf<DockBlockBuild> b) {
      this.title = title;
      this.description = description;
      color = IMColors.colorPrimary;
      this.stack = stack;
      this.ask = ask;
      this.p = p;
      this.b = b;
    }
    
    public void unlockItem() {
      p.get(entity);
      setup();
    }
    
    public boolean canShow() {
      return entity.level >= ask && !b.get(entity);
    }
    
  }
  
}